package ru.dymeth.pcontrol.inventory;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.dymeth.pcontrol.PControlDataBukkit;
import ru.dymeth.pcontrol.api.BukkitUtils;
import ru.dymeth.pcontrol.api.PControlCategory;
import ru.dymeth.pcontrol.api.PControlTrigger;
import ru.dymeth.pcontrol.text.CommonColor;
import ru.dymeth.pcontrol.text.CommonDecoration;
import ru.dymeth.pcontrol.text.Text;
import ru.dymeth.pcontrol.text.TextHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PControlTriggerInventory extends PControlInventory {
    private static final ItemStack DISALLOWED_TRIGGER = new ItemStack(Material.BARRIER);
    private final PControlDataBukkit data;
    private final Map<PControlTrigger, Short> slotByTrigger;

    public PControlTriggerInventory(@Nonnull PControlDataBukkit data, @Nonnull PControlCategory category, @Nonnull World world) {
        super(
            data,
            world,
            4,
            data.getMessage("inventory-title",
                "%category%", data.getCategoryName(category),
                "%world%", world.getName())
        );
        this.data = data;
        this.slotByTrigger = new HashMap<>();
        for (PControlTrigger trigger : category.getTriggers()) {
            this.slotByTrigger.put(trigger, trigger.getSlot());
        }
        ItemStack back = BukkitUtils.matchIcon("RED_WOOL", "WOOL:14");
        if (back == null) throw new IllegalArgumentException();
        ItemMeta meta = back.getItemMeta();
        if (meta == null) throw new IllegalArgumentException();
        data.getTextHelper().setStackName(meta, data.getMessage("select-another-category-item"));
        back.setItemMeta(meta);
        this.setItem((short) (3 * 9 + 4), back, player ->
            player.openInventory(new PControlCategoryInventory(data, world).getInventory()));
    }

    public void updateTriggerStack(@Nonnull PControlTrigger trigger) {
        if (trigger == PControlTrigger.IGNORED_STATE) return;
        boolean available = trigger.isAvailable();
        boolean enabled = this.data.isActionAllowed(this.world, trigger);

        ItemStack icon = available ? trigger.getIcon() : DISALLOWED_TRIGGER;
        icon = icon.clone();

        ItemMeta meta = icon.getItemMeta();
        if (meta == null) {
            throw new IllegalArgumentException("Item meta could not be null");
        }

        TextHelper helper = this.data.getTextHelper();

        Text name;
        if (available) {
            name = helper.create(this.data.getTriggerName(trigger), (enabled ? CommonColor.GREEN : CommonColor.RED));
        } else {
            name = helper.create(this.data.getTriggerName(trigger), CommonColor.RED, CommonDecoration.STRIKETHROUGH);
        }
        helper.setStackName(meta, name);

        List<Text> lore = new ArrayList<>();
        if (available) {
            lore.add(this.data.getMessage(enabled
                ? "trigger-enabled-state"
                : "trigger-disabled-state"
            ));
            lore.addAll(this.data.getMessage(trigger.isRealtime()
                ? "trigger-realtime"
                : "trigger-on-update"
            ).split("\n"));
        } else {
            lore.add(this.data.getMessage("trigger-unsupported-state", "%min_version%", "?"));
        }
        helper.setStackLore(meta, lore);

        if (available && enabled) {
            meta.addEnchant(this.data.getFakeEnchantment(), 1, true);
        }
        icon.setItemMeta(meta);
        short slot = this.slotByTrigger.get(trigger);
        this.setItem(slot, icon, player -> this.switchTrigger(player, trigger));
    }

    public void switchTrigger(@Nonnull CommandSender sender, @Nonnull PControlTrigger trigger) {
        if (trigger == PControlTrigger.IGNORED_STATE) return;
        if (!trigger.isAvailable()) {
            return;
        }
        if (!sender.isOp()
            && !sender.hasPermission("physicscontrol.trigger.*")
            && !sender.hasPermission("physicscontrol.trigger." + trigger.name().toLowerCase())) {
            this.data.getMessage("bad-perms-trigger", "%trigger%", this.data.getTriggerName(trigger)).send(sender);
            return;
        }
        this.data.switchTrigger(this.world, trigger);
        this.updateTriggerStack(trigger);

        Text msg = this.data.getMessage(
            this.data.isActionAllowed(this.world, trigger) ? "trigger-enabled" : "trigger-disabled",
            "%player%", sender.getName(),
            "%trigger%", this.data.getTriggerName(trigger),
            "%world%", this.world.getName());

        this.data.announce(this.world, msg);
    }
}
