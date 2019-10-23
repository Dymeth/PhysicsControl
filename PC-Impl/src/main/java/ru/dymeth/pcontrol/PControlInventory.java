package ru.dymeth.pcontrol;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class PControlInventory implements InventoryHolder {
    private static final ItemStack DISALLOWED_TRIGGER = new ItemStack(Material.BARRIER);
    private final PControlDataBukkit data;
    private final World world;
    private final Inventory inventory;
    private final HashMap<Short, PControlTrigger> triggerBySlot;
    private final HashMap<PControlTrigger, Short> slotByTrigger;

    PControlInventory(@Nonnull PControlDataBukkit data, @Nonnull World world) {
        this.data = data;
        this.world = world;
        this.inventory = Bukkit.createInventory(this, 6 * 9, this.data.getMessage("inventory-title", "%world%", world.getName()));
        this.triggerBySlot = new HashMap<>();
        this.slotByTrigger = new HashMap<>();
        for (PControlTrigger trigger : PControlTrigger.values()) {
            this.triggerBySlot.put(trigger.getSlot(), trigger);
            this.slotByTrigger.put(trigger, trigger.getSlot());
            updateTriggerStack(trigger);
        }
    }

    void updateTriggerStack(@Nonnull PControlTrigger trigger) {
        boolean allowed = this.data.getServerVersion() >= trigger.getMinVersion();
        boolean enabled = this.data.isActionAllowed(this.world, trigger);
        ItemStack icon = allowed ? trigger.getIcon() : DISALLOWED_TRIGGER;
        if (icon == null) {
            this.data.getLogger().severe("Could not find material icon for trigger " + trigger.name() + ". Server version: " + this.data.getServerVersion() + ". Contact with plugin developer");
            icon = DISALLOWED_TRIGGER;
        }
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
            lore.add(this.data.getMessage("trigger-unsupported-state"));
        }
        meta.setLore(lore);
        if (allowed && enabled)
            meta.addEnchant(this.data.getFakeEnchantment(), 1, true);
        icon.setItemMeta(meta);

        this.inventory.setItem(this.slotByTrigger.get(trigger), icon);
    }

    @Nonnull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    void performAction(@Nonnull Player player, int rawSlot) {
        PControlTrigger trigger = this.triggerBySlot.get((short) rawSlot);
        if (trigger == null) return;
        this.switchTrigger(player, trigger);
    }

    private void switchTrigger(@Nonnull CommandSender sender, @Nonnull PControlTrigger trigger) {
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

        if (!msg.isEmpty()) Bukkit.broadcast(msg, "physicscontrol.announce");
    }

    void close() {
        new ArrayList<>(this.inventory.getViewers()).forEach(HumanEntity::closeInventory);
    }
}
