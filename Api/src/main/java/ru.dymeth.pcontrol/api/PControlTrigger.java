package ru.dymeth.pcontrol.api;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

import static ru.dymeth.pcontrol.api.PControlCategory.*;

public enum PControlTrigger {
    GRAVEL_FALLING(
            GRAVITY_BLOCKS, false, 2, 2, false, "GRAVEL"),
    SAND_FALLING(
            GRAVITY_BLOCKS, false, 2, 3, false, "SAND"),
    ANVILS_FALLING(
            GRAVITY_BLOCKS, false, 2, 4, false, "ANVIL"),
    DRAGON_EGGS_FALLING(
            GRAVITY_BLOCKS, false, 2, 5, false, "DRAGON_EGG"),
    CONCRETE_POWDERS_FALLING(
            GRAVITY_BLOCKS, false, 2, 6, false, 12, "LIME_CONCRETE_POWDER", "CONCRETE_POWDER:5"),
    SCAFFOLDING_FALLING(
            GRAVITY_BLOCKS, false, 2, 7, false, 14, "SCAFFOLDING"),
    POINTED_DRIPSTONES_FALLING(
            GRAVITY_BLOCKS, false, 2, 8, false, 17, "POINTED_DRIPSTONE"),

    WATER_FLOWING(
            LIQUIDS, false, 2, 4, false, "WATER_BUCKET"),
    LAVA_FLOWING(
            LIQUIDS, false, 2, 6, false, "LAVA_BUCKET"),

    LADDERS_DESTROYING(
            BUILDING, false, 2, 3, false, 13, "LADDER"),
    SIGNS_DESTROYING(
            BUILDING, false, 2, 4, false, 13, "OAK_SIGN", "SIGN"),
    TORCHES_DESTROYING(
            BUILDING, false, 2, 5, false, 13, "TORCH"),
    REDSTONE_TORCHES_DESTROYING(
            BUILDING, false, 2, 6, false, 13, "REDSTONE_TORCH", "REDSTONE_TORCH_ON"),
    RAILS_DESTROYING(
            BUILDING, false, 2, 7, false, 13, "RAIL", "RAILS"),

    BLOCK_HIT_PROJECTILES_REMOVING(
            ENTITIES_INTERACTIONS, false, 1, 3, false, "ARROW"),
    BURNING_ARROWS_ACTIVATE_TNT(
            ENTITIES_INTERACTIONS, false, 1, 4, true, "ARROW"),
    FARMLANDS_TRAMPLING(
            ENTITIES_INTERACTIONS, false, 1, 5, false, "FARMLAND", "SOIL"),
    TURTLE_EGGS_TRAMPLING(
            ENTITIES_INTERACTIONS, false, 1, 6, false, 13, "TURTLE_EGG"),
    WITHERS_GRIEFING(
            ENTITIES_INTERACTIONS, false, 1, 7, false, "WITHER_SKELETON_SKULL", "SKULL_ITEM:1"),
    ENDERMANS_GRIEFING(
            ENTITIES_INTERACTIONS, false, 2, 2, false, "ENDER_PEARL"),
    SILVERFISHES_HIDING_IN_BLOCKS(
            ENTITIES_INTERACTIONS, false, 2, 3, false, "STONE"),
    ZOMBIES_BREAK_DOORS(
            ENTITIES_INTERACTIONS, false, 2, 4, false, "ZOMBIE_HEAD", "SKULL_ITEM:2"),
    DRIPLEAFS_LOWERING(
            ENTITIES_INTERACTIONS, false, 2, 5, false, 17, "BIG_DRIPLEAF"),
    VILLAGERS_FARMING(
            ENTITIES_INTERACTIONS, false, 2, 6, false, "WHEAT"),
    RABBITS_EATING_CARROTS(
            ENTITIES_INTERACTIONS, false, 2, 7, false, "CARROT_ITEM", "CARROT"),
    SHEEPS_EATING_GRASS(
            ENTITIES_INTERACTIONS, false, 2, 8, false, "WHITE_WOOL", "WOOL"),
    TURTLES_LAYING_EGGS(
            ENTITIES_INTERACTIONS, false, 3, 3, false, 13, "TURTLE_EGG"),
    PLAYERS_FLINT_USAGE(
            ENTITIES_INTERACTIONS, false, 3, 4, true, "FLINT_AND_STEEL"),
    BONE_MEAL_USAGE(
            ENTITIES_INTERACTIONS, false, 3, 5, true, "BONE_MEAL", "INK_SACK:15"),
    DRAGON_EGGS_TELEPORTING(
            ENTITIES_INTERACTIONS, false, 3, 6, false, "DRAGON_EGG"),
    FROSTED_ICE_PHYSICS(
            ENTITIES_INTERACTIONS, false, 3, 7, true, 9, "ICE"),

    FIRE_SPREADING(
            WORLD_DESTRUCTION, true, 1, 4, false, "FLINT_AND_STEEL"),
    SNOW_MELTING(
            WORLD_DESTRUCTION, true, 1, 5, false, "SNOW_BLOCK"),
    FARMLANDS_DRYING(
            WORLD_DESTRUCTION, true, 1, 6, false, "FARMLAND", "SOIL"),

    ICE_MELTING(
            WORLD_DESTRUCTION, true, 2, 4, false, "ICE"),
    LEAVES_DECAY(
            WORLD_DESTRUCTION, true, 2, 5, false, "OAK_LEAVES", "LEAVES"),
    CORALS_DRYING(
            WORLD_DESTRUCTION, true, 2, 6, false, 13, "FIRE_CORAL"),

    GRASS_BLOCKS_FADING(
            WORLD_DESTRUCTION, true, 3, 4, false, "GRASS_BLOCK", "GRASS"),
    CRIMSON_NYLIUM_FADING(
            WORLD_DESTRUCTION, true, 3, 5, false, 16, "CRIMSON_NYLIUM"),
    WARPED_NYLIUM_FADING(
            WORLD_DESTRUCTION, true, 3, 6, false, 16, "WARPED_NYLIUM"),

    SWEET_BERRIES_GROWING(
            GROWING_BLOCKS_AND_SMALL_PLANTS, true, 1, 3, false, 14, "SWEET_BERRIES"),
    LITTLE_MUSHROOMS_SPREADING(
            GROWING_BLOCKS_AND_SMALL_PLANTS, true, 1, 4, false, "RED_MUSHROOM"),
    PUMPKINS_GROWING(
            GROWING_BLOCKS_AND_SMALL_PLANTS, true, 1, 5, false, "PUMPKIN"),
    MELONS_GROWING(
            GROWING_BLOCKS_AND_SMALL_PLANTS, true, 1, 6, false, "MELON_BLOCK", "MELON"),
    NETHER_WARTS_GROWING(
            GROWING_BLOCKS_AND_SMALL_PLANTS, true, 1, 7, false, "NETHER_WART", "NETHER_STALK"),

    COCOAS_GROWING(
            GROWING_BLOCKS_AND_SMALL_PLANTS, true, 2, 3, false, "COCOA_BEANS", "INK_SACK:3"),
    WHEAT_GROWING(
            GROWING_BLOCKS_AND_SMALL_PLANTS, true, 2, 4, false, "WHEAT"),
    POTATOES_GROWING(
            GROWING_BLOCKS_AND_SMALL_PLANTS, true, 2, 5, false, "POTATO_ITEM", "POTATO"),
    CARROTS_GROWING(
            GROWING_BLOCKS_AND_SMALL_PLANTS, true, 2, 6, false, "CARROT_ITEM", "CARROT"),
    BEETROOTS_GROWING(
            GROWING_BLOCKS_AND_SMALL_PLANTS, true, 2, 7, false, 9, "BEETROOT"),

    GRASS_SPREADING(
            GROWING_BLOCKS_AND_SMALL_PLANTS, true, 3, 4, false, "GRASS_BLOCK", "GRASS"),
    MYCELIUM_SPREADING(
            GROWING_BLOCKS_AND_SMALL_PLANTS, true, 3, 5, false, "MYCELIUM", "MYCEL"),
    AMETHYST_CLUSTERS_GROWING(
            GROWING_BLOCKS_AND_SMALL_PLANTS, true, 3, 6, false, 17, "AMETHYST_CLUSTER"),

    SUGAR_CANE_GROWING(
            VINES_AND_TALL_STRUCTURES, true, 1, 3, false, "SUGAR_CANE"),
    CACTUS_GROWING(
            VINES_AND_TALL_STRUCTURES, true, 1, 4, false, "CACTUS"),
    TREES_GROWING(
            VINES_AND_TALL_STRUCTURES, true, 1, 5, false, "BIRCH_SAPLING", "SAPLING:2"),
    VINES_GROWING(
            VINES_AND_TALL_STRUCTURES, true, 1, 6, false, "VINE"),
    GLOW_BERRIES_GROWING(
            VINES_AND_TALL_STRUCTURES, true, 1, 7, false, 17, "GLOW_BERRIES"),

    CHORUSES_GROWING(
            VINES_AND_TALL_STRUCTURES, true, 2, 2, false, 9, "CHORUS_FLOWER"),
    KELPS_GROWING(
            VINES_AND_TALL_STRUCTURES, true, 2, 3, false, 13, "KELP"),
    BAMBOO_GROWING(
            VINES_AND_TALL_STRUCTURES, true, 2, 4, false, 14, "BAMBOO"),
    GIANT_MUSHROOMS_GROWING(
            VINES_AND_TALL_STRUCTURES, true, 2, 5, false, "RED_MUSHROOM_BLOCK", "HUGE_MUSHROOM_2"),
    POINTED_DRIPSTONES_GROWING(
            VINES_AND_TALL_STRUCTURES, true, 2, 6, false, 17, "POINTED_DRIPSTONE"),
    WEEPING_VINES_GROWING(
            VINES_AND_TALL_STRUCTURES, true, 2, 7, false, 16, "WEEPING_VINES"),
    TWISTING_VINES_GROWING(
            VINES_AND_TALL_STRUCTURES, true, 2, 8, false, 16, "TWISTING_VINES"),

    DEBUG_MESSAGES(
            SETTINGS, true, 2, 4, true, "COMMAND_BLOCK", "COMMAND"),
    ALLOW_UNRECOGNIZED_ACTIONS(
            SETTINGS, true, 2, 6, false, "COMMAND_BLOCK", "COMMAND");

    private final PControlCategory category;
    private final short minVersion;
    private final ItemStack icon;
    private final boolean realtime;
    private final short slot;
    private final boolean defaults;

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
        if (this.icon == null) throw new IllegalArgumentException("Unable to find icon of trigger " + this);
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
