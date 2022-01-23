package ru.dymeth.pcontrol.impl;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public enum PControlCategory {
    GRAVITY_BLOCKS(
            2, 2, "SAND"),
    LIQUIDS(
            2, 3, "WATER_BUCKET"),
    BUILDING(
            2, 4, "LADDER"),
    ENTITIES_INTERACTIONS(
            2, 5, "PLAYER_HEAD", "SKULL_ITEM:3"),
    WORLD_DESTRUCTION(
            2, 6, "OAK_LEAVES", "LEAVES"),
    PLANTS(
            2, 7, "BIRCH_SAPLING", "SAPLING:2"),
    SETTINGS(
            2, 8, "COMMAND_BLOCK", "COMMAND");

    private final short slot;
    private final ItemStack icon;

    PControlCategory(int row, int column, @Nonnull String... iconVariants) {
        this.slot = (short) ((row - 1) * 9 + column - 1);
        this.icon = BukkitUtils.matchIcon(iconVariants);
        ItemMeta meta = this.icon.getItemMeta();
        if (meta == null) throw new IllegalArgumentException();
        meta.setDisplayName(ChatColor.YELLOW + this.getDisplayName());
        this.icon.setItemMeta(meta);
    }

    public short getSlot() {
        return this.slot;
    }

    @Nonnull
    public ItemStack getIcon() {
        return this.icon;
    }

    @Nonnull
    public String getDisplayName() {
        String name = this.name().toLowerCase().replace("_", " ");
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
