package ru.dymeth.pcontrol.api;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public enum PControlCategory {
    MOBS_INTERACTIONS(
        1, 4, "ZOMBIE_HEAD", "SKULL_ITEM:2"),
    ENTITIES_INTERACTIONS(
        1, 5, "PLAYER_HEAD", "SKULL_ITEM:3"),
    BUILDING(
        1, 6, "LADDER"),

    LIQUIDS(
        2, 3, "WATER_BUCKET"),
    GRAVITY_BLOCKS(
        2, 4, "SAND"),
    WORLD_DESTRUCTION(
        2, 5, "OAK_LEAVES", "LEAVES"),
    GROWING_BLOCKS_AND_SMALL_PLANTS(
        2, 6, "MELON"),
    VINES_AND_TALL_STRUCTURES(
        2, 7, "BIRCH_SAPLING", "SAPLING:2"),

    SETTINGS(
        3, 5, "COMMAND_BLOCK", "COMMAND"),
    TEST(
        3, 9, "OAK_SIGN", "SIGN");

    private final short slot;
    private final ItemStack icon;

    PControlCategory(int row, int column, @Nonnull String... iconVariants) {
        this.slot = (short) ((row - 1) * 9 + column - 1);
        this.icon = BukkitUtils.matchIcon(iconVariants);
        if (this.icon != null) {
            ItemMeta meta = this.icon.getItemMeta();
            if (meta == null) throw new IllegalArgumentException();
            meta.setDisplayName(ChatColor.YELLOW + this.getDisplayName());
            this.icon.setItemMeta(meta);
        }
    }

    public short getSlot() {
        return this.slot;
    }

    @Nonnull
    public ItemStack getIcon() {
        if (this.icon == null) throw new IllegalArgumentException("Unable to find icon of category " + this);
        return this.icon;
    }

    @Nonnull
    public String getDisplayName() {
        String name = this.name().toLowerCase().replace("_", " ");
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
