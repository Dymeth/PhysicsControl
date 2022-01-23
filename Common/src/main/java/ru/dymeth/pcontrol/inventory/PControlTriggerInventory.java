package ru.dymeth.pcontrol.inventory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.dymeth.pcontrol.api.BukkitUtils;
import ru.dymeth.pcontrol.api.PControlCategory;
import ru.dymeth.pcontrol.PControlDataBukkit;
import ru.dymeth.pcontrol.api.PControlTrigger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class PControlTriggerInventory extends PControlInventory {
    private static final ItemStack DISALLOWED_TRIGGER = new ItemStack(Material.BARRIER);
    private final PControlDataBukkit data;
    private final HashMap<PControlTrigger, Short> slotByTrigger;

    public PControlTriggerInventory(@Nonnull PControlDataBukkit data, @Nonnull PControlCategory category, @Nonnull World world) {
        super(world, 3, data.getMessage("inventory-title", "%category%", category.getDisplayName(), "%world%", world.getName()));
        this.data = data;
        this.slotByTrigger = new HashMap<>();
        for (PControlTrigger trigger : PControlTrigger.values()) {
            if (trigger.getCategory() != category) continue;
            this.slotByTrigger.put(trigger, trigger.getSlot());
            this.updateTriggerStack(trigger);
        }
        ItemStack back = BukkitUtils.matchIcon("RED_WOOL", "WOOL:14");
        if (back == null) throw new IllegalArgumentException();
        ItemMeta meta = back.getItemMeta();
        if (meta == null) throw new IllegalArgumentException();
        meta.setDisplayName(data.getMessage("select-another-category-item"));
        back.setItemMeta(meta);
        this.setItem((short) (3 * 9 - 1), back, player ->
                player.openInventory(new PControlCategoryInventory(data, world).getInventory()));
    }

    public void updateTriggerStack(@Nonnull PControlTrigger trigger) {
        boolean allowed = this.data.getServerVersion() >= trigger.getMinVersion();
        boolean enabled = this.data.isActionAllowed(this.world, trigger);

        ItemStack icon = allowed ? trigger.getIcon() : DISALLOWED_TRIGGER;
        icon = icon.clone();

        ItemMeta meta = icon.getItemMeta();
        if (meta == null)
            throw new IllegalArgumentException("Item meta could not be null");
        if (allowed)
            meta.setDisplayName((enabled ? ChatColor.GREEN : ChatColor.RED) + trigger.getDisplayName());
        else
            meta.setDisplayName(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + trigger.getDisplayName());
        List<String> lore = new ArrayList<>();
        if (allowed) {
            lore.add(enabled ? this.data.getMessage("trigger-enabled-state") : this.data.getMessage("trigger-disabled-state"));
            lore.addAll(Arrays.asList(this.data.getMessage(trigger.isRealtime() ? "trigger-realtime" : "trigger-on-update").split("\\n")));
        } else {
            lore.add(this.data.getMessage("trigger-unsupported-state", "%min_version%", "1." + trigger.getMinVersion()));
        }
        meta.setLore(lore);
        if (allowed && enabled)
            meta.addEnchant(this.data.getFakeEnchantment(), 1, true);
        icon.setItemMeta(meta);
        short slot = this.slotByTrigger.get(trigger);
        this.setItem(slot, icon, player -> this.switchTrigger(player, trigger));
    }

    public void switchTrigger(@Nonnull CommandSender sender, @Nonnull PControlTrigger trigger) {
        if (this.data.getServerVersion() < trigger.getMinVersion())
            return;
        if (!sender.isOp()
                && !sender.hasPermission("physicscontrol.trigger.*")
                && !sender.hasPermission("physicscontrol.trigger." + trigger.name().toLowerCase())) {
            sender.sendMessage(this.data.getMessage("bad-perms-trigger", "%trigger%", trigger.getDisplayName()));
            return;
        }
        this.data.switchTrigger(this.world, trigger);
        this.updateTriggerStack(trigger);

        String msg = this.data.getMessage(
                this.data.isActionAllowed(this.world, trigger) ? "trigger-enabled" : "trigger-disabled",
                "%player%", sender.getName(),
                "%trigger%", trigger.getDisplayName(),
                "%world%", this.world.getName());

        if (msg.isEmpty()) return;
        this.data.announce(this.world, msg, null);
    }
}
