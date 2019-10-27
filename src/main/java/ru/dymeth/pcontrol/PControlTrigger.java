package ru.dymeth.pcontrol;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

import static ru.dymeth.pcontrol.PControlCategory.*;

public enum PControlTrigger {
    SAND_FALLING(
            GRAVITY_BLOCKS, false, 2, 2, "SAND"),
    GRAVEL_FALLING(
            GRAVITY_BLOCKS, false, 2, 4, "GRAVEL"),
    ANVILS_FALLING(
            GRAVITY_BLOCKS, false, 2, 6, "ANVIL"),
    CONCRETE_POWDERS_FALLING(
            GRAVITY_BLOCKS, false, 2, 8, 12, "LIME_CONCRETE_POWDER", "CONCRETE_POWDER:5"),

    WATER_FLOWING(
            LIQUIDS, false, 2, 4, "WATER_BUCKET"),
    LAVA_FLOWING(
            LIQUIDS, false, 2, 6, "LAVA_BUCKET"),

    LADDERS_DESTROYING(
            BUILDING, false, 2, 3, "LADDER"),
    SIGNS_DESTROYING(
            BUILDING, false, 2, 4, "OAK_SIGN", "SIGN"),
    TORCHES_DESTROYING(
            BUILDING, false, 2, 5, "TORCH"),
    REDSTONE_TORCHES_DESTROYING(
            BUILDING, false, 2, 6, "REDSTONE_TORCH", "REDSTONE_TORCH_ON"),
    RAILS_DESTROYING(
            BUILDING, false, 2, 7, 13, "RAIL", "RAILS"),

    SHEEPS_EATING_GRASS(
            ENTITIES_INTERACTIONS, false, 2, 2, "WHITE_WOOL", "WOOL"),
    ENDERMANS_GRIEFING(
            ENTITIES_INTERACTIONS, false, 2, 3, "ENDER_PEARL"),
    FARMLANDS_TRAMPLING(
            ENTITIES_INTERACTIONS, false, 2, 4, "FARMLAND", "SOIL"),
    BLOCK_HIT_PROJECTILES_REMOVING(
            ENTITIES_INTERACTIONS, false, 2, 5, "ARROW"),
    PLAYERS_FLINT_USAGE(
            ENTITIES_INTERACTIONS, false, 2, 6, true, "FLINT_AND_STEEL"),
    BONE_MEAL_USAGE(
            ENTITIES_INTERACTIONS, false, 2, 7, true, "BONE_MEAL", "INK_SACK:15"),
    FROSTED_ICE_PHYSICS(
            ENTITIES_INTERACTIONS, false, 2, 8, true, 9, "ICE"),

    FIRE_SPREADING(
            WORLD_DESTRUCTION, true, 2, 2, "FLINT_AND_STEEL"),
    SNOW_MELTING(
            WORLD_DESTRUCTION, true, 2, 3, "SNOW_BLOCK"),
    FARMLANDS_DRYING(
            WORLD_DESTRUCTION, true, 2, 4, "FARMLAND", "SOIL"),
    ICE_MELTING(
            WORLD_DESTRUCTION, true, 2, 5, "ICE"),
    LEAVES_DECAY(
            WORLD_DESTRUCTION, true, 2, 6, "OAK_LEAVES", "LEAVES"),
    CORALS_DRYING(
            WORLD_DESTRUCTION, true, 2, 7, 13, "FIRE_CORAL"),

    SUGAR_CANE_GROWING(
            PLANTS, true, 1, 2, "SUGAR_CANE"),
    CACTUS_GROWING(
            PLANTS, true, 1, 3, "CACTUS"),
    TREES_GROWING(
            PLANTS, true, 1, 4, "BIRCH_SAPLING", "SAPLING:2"),
    VINES_GROWING(
            PLANTS, true, 1, 5, "VINE"),
    GIANT_MUSHROOMS_GROWING(
            PLANTS, true, 1, 6, "RED_MUSHROOM_BLOCK", "HUGE_MUSHROOM_2"),
    CHORUSES_GROWING(
            PLANTS, true, 1, 7, 9, "CHORUS_FLOWER"),
    KELPS_GROWING(
            PLANTS, true, 1, 8, 13, "KELP"),

    LITTLE_MUSHROOMS_SPREADING(
            PLANTS, true, 2, 1, "RED_MUSHROOM"),
    PUMPKINS_GROWING(
            PLANTS, true, 2, 2, "PUMPKIN"),
    MELONS_GROWING(
            PLANTS, true, 2, 3, "MELON_BLOCK", "MELON"),
    NETHER_WARTS_GROWING(
            PLANTS, true, 2, 4, "NETHER_WART", "NETHER_STALK"),
    COCOAS_GROWING(
            PLANTS, true, 2, 5, "COCOA_BEANS", "INK_SACK:3"),
    WHEAT_GROWING(
            PLANTS, true, 2, 6, "WHEAT"),
    POTATOES_GROWING(
            PLANTS, true, 2, 7, "POTATO_ITEM", "POTATO"),
    CARROTS_GROWING(
            PLANTS, true, 2, 8, "CARROT_ITEM", "CARROT"),
    BEETROOTS_GROWING(
            PLANTS, true, 2, 9, 9, "BEETROOT"),

    GRASS_SPREADING(
            PLANTS, true, 3, 4, "GRASS_BLOCK", "GRASS"),
    MYCELIUM_SPREADING(
            PLANTS, true, 3, 6, "MYCELIUM", "MYCEL"),

    DEBUG_MESSAGES(
            SETTINGS, true, 2, 4, true, "COMMAND_BLOCK", "COMMAND"),
    ALLOW_UNRECOGNIZED_ACTIONS(
            SETTINGS, true, 2, 6, "COMMAND_BLOCK", "COMMAND");

    private final PControlCategory category;
    private final short minVersion;
    private final ItemStack icon;
    private final boolean realtime;
    private final short slot;
    private final boolean defaults;

    PControlTrigger(@Nonnull PControlCategory category, boolean realtime, int row, int column, @Nonnull String... iconVariants) {
        this(category, realtime, row, column, false, -1, iconVariants);
    }

    PControlTrigger(@Nonnull PControlCategory category, boolean realtime, int row, int column, int minVersion, @Nonnull String... iconVariants) {
        this(category, realtime, row, column, false, minVersion, iconVariants);
    }

    PControlTrigger(@Nonnull PControlCategory category, boolean realtime, int row, int column, boolean defaults, @Nonnull String... iconVariants) {
        this(category, realtime, row, column, defaults, -1, iconVariants);
    }

    PControlTrigger(@Nonnull PControlCategory category, boolean realtime, int row, int column, boolean defaults, int minVersion, @Nonnull String... iconVariants) {
        this.category = category;
        this.minVersion = (short) minVersion;
        this.realtime = realtime;
        this.slot = (short) ((row - 1) * 9 + column - 1);
        if (this.slot < 0 || this.slot >= 3 * 9)
            throw new IllegalArgumentException("Invalid slot of trigger " + this);
        this.defaults = defaults;
        this.icon = BukkitUtils.matchIcon(iconVariants);
        ItemMeta meta = category.getIcon().getItemMeta();
        if (meta == null) throw new IllegalArgumentException();
        List<String> lore = meta.getLore();
        if (lore == null) lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + " - " + this.getDisplayName());
        meta.setLore(lore);
        category.getIcon().setItemMeta(meta);
    }

    @Nonnull
    public PControlCategory getCategory() {
        return this.category;
    }

    public short getMinVersion() {
        return this.minVersion;
    }

    @Nonnull
    public String getDisplayName() {
        String name = this.name().toLowerCase().replace("_", " ");
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    @Nonnull
    public ItemStack getIcon() {
        return this.icon;
    }

    public boolean isRealtime() {
        return this.realtime;
    }

    public short getSlot() {
        return this.slot;
    }

    public boolean getDefaultValue() {
        return this.defaults;
    }
}
