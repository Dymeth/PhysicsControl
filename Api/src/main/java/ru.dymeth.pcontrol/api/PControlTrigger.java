package ru.dymeth.pcontrol.api;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

import static ru.dymeth.pcontrol.api.PControlCategory.*;

public enum PControlTrigger {
    GRAVEL_FALLING(
        GRAVITY_BLOCKS, false, 2, 2, true, "GRAVEL"),
    SAND_FALLING(
        GRAVITY_BLOCKS, false, 2, 3, true, "SAND"),
    ANVILS_FALLING(
        GRAVITY_BLOCKS, false, 2, 4, true, "ANVIL"),
    DRAGON_EGGS_FALLING(
        GRAVITY_BLOCKS, false, 2, 5, true, "DRAGON_EGG"),
    CONCRETE_POWDERS_FALLING(
        GRAVITY_BLOCKS, false, 2, 6, true, 12, "LIME_CONCRETE_POWDER", "CONCRETE_POWDER:5"),
    SCAFFOLDING_FALLING(
        GRAVITY_BLOCKS, false, 2, 7, true, 14, "SCAFFOLDING"),
    POINTED_DRIPSTONES_FALLING(
        GRAVITY_BLOCKS, false, 2, 8, true, 17, "POINTED_DRIPSTONE"),


    WATER_FLOWING(
        LIQUIDS, false, 2, 4, true, "WATER_BUCKET"),
    LAVA_FLOWING(
        LIQUIDS, false, 2, 6, true, "LAVA_BUCKET"),


    LADDERS_DESTROYING(
        BUILDING, false, 2, 4, true, 13, "LADDER"),
    SIGNS_DESTROYING(
        BUILDING, false, 2, 5, true, 13, "OAK_SIGN", "SIGN"),
    RAILS_DESTROYING(
        BUILDING, false, 2, 6, true, 13, "RAIL", "RAILS"),

    TORCHES_DESTROYING(
        BUILDING, false, 3, 4, true, 13, "TORCH"),
    REDSTONE_TORCHES_DESTROYING(
        BUILDING, false, 3, 5, true, 13, "REDSTONE_TORCH", "REDSTONE_TORCH_ON"),
    SOUL_TORCHES_DESTROYING(
        BUILDING, false, 3, 6, true, 16, "SOUL_TORCH"),


    RABBITS_EATING_CARROTS(
        MOBS_INTERACTIONS, false, 1, 2, true, 8, "CARROT_ITEM", "CARROT"),
    VILLAGERS_FARMING(
        MOBS_INTERACTIONS, false, 1, 3, true, "WHEAT"),
    SHEEPS_EATING_GRASS(
        MOBS_INTERACTIONS, false, 1, 4, true, "WHITE_WOOL", "WOOL"),
    SNOW_GOLEMS_CREATE_SNOW(
        MOBS_INTERACTIONS, false, 1, 5, true, "SNOW_BLOCK"),
    SILVERFISHES_HIDING_IN_BLOCKS(
        MOBS_INTERACTIONS, false, 1, 6, true, "INFESTED_CRACKED_STONE_BRICKS", "MONSTER_EGGS:4"),
    ZOMBIES_BREAK_DOORS(
        MOBS_INTERACTIONS, false, 1, 7, true, "ZOMBIE_HEAD", "SKULL_ITEM:2"),
    ENDERMANS_GRIEFING(
        MOBS_INTERACTIONS, false, 1, 8, true, "ENDER_PEARL"),

    WITHERS_GRIEFING(
        MOBS_INTERACTIONS, false, 2, 3, true, "WITHER_SKELETON_SKULL", "SKULL_ITEM:1"),
    TURTLES_LAYING_EGGS(
        MOBS_INTERACTIONS, false, 2, 4, true, 13, "TURTLE_EGG"),
    WITHER_CREATE_WITHER_ROSE_BLOCKS(
        MOBS_INTERACTIONS, false, 2, 5, true, 14, "WITHER_ROSE"),
    FOXES_EATS_FROM_SWEET_BERRY_BUSHES(
        MOBS_INTERACTIONS, false, 2, 6, true, 14, "SWEET_BERRIES"),
    RAVAGERS_DESTROY_BLOCKS(
        MOBS_INTERACTIONS, false, 2, 7, true, 14, "RAVAGER_SPAWN_EGG"),


    BURNING_ARROWS_ACTIVATE_TNT(
        ENTITIES_INTERACTIONS, false, 1, 4, true, "TNT"),
    FARMLANDS_TRAMPLING(
        ENTITIES_INTERACTIONS, false, 1, 5, true, "FARMLAND", "SOIL"),
    PLAYERS_FLINT_USAGE(
        ENTITIES_INTERACTIONS, false, 1, 6, true, "FLINT_AND_STEEL"),

    BONE_MEAL_USAGE(
        ENTITIES_INTERACTIONS, false, 2, 3, true, "BONE_MEAL", "INK_SACK:15"),
    DRAGON_EGGS_TELEPORTING(
        ENTITIES_INTERACTIONS, false, 2, 4, true, "DRAGON_EGG"),
    FROSTED_ICE_PHYSICS(
        ENTITIES_INTERACTIONS, false, 2, 5, true, 9, "ICE"),
    BLOCK_HIT_PROJECTILES_REMOVING(
        ENTITIES_INTERACTIONS, false, 2, 6, false, 11, "ARROW"),
    TURTLE_EGGS_TRAMPLING(
        ENTITIES_INTERACTIONS, false, 2, 7, true, 13, "TURTLE_EGG"),

    DRIPLEAFS_LOWERING(
        ENTITIES_INTERACTIONS, false, 3, 4, true, 17, "BIG_DRIPLEAF"),
    POWDER_SNOW_MELTS_FROM_BURNING_ENTITIES(
        ENTITIES_INTERACTIONS, false, 3, 5, true, 17, "POWDER_SNOW_BUCKET"),
    GLOW_BERRIES_PICKING(
        ENTITIES_INTERACTIONS, false, 3, 6, true, 17, "GLOW_BERRIES"),


    FIRE_SPREADING(
        WORLD_DESTRUCTION, true, 1, 3, true, "FLINT_AND_STEEL"),
    SNOW_MELTING(
        WORLD_DESTRUCTION, true, 1, 4, true, "SNOW_BLOCK"),
    FARMLANDS_DRYING(
        WORLD_DESTRUCTION, true, 1, 5, true, "FARMLAND", "SOIL"),
    ICE_MELTING(
        WORLD_DESTRUCTION, true, 1, 6, true, "ICE"),
    LEAVES_DECAY(
        WORLD_DESTRUCTION, true, 1, 7, true, "OAK_LEAVES", "LEAVES"),

    GRASS_BLOCKS_FADING(
        WORLD_DESTRUCTION, true, 2, 3, true, "GRASS_BLOCK", "GRASS"),
    CORALS_DRYING(
        WORLD_DESTRUCTION, true, 2, 4, true, 13, "FIRE_CORAL"),
    CRIMSON_NYLIUM_FADING(
        WORLD_DESTRUCTION, true, 2, 5, true, 16, "CRIMSON_NYLIUM"),
    WARPED_NYLIUM_FADING(
        WORLD_DESTRUCTION, true, 2, 6, true, 16, "WARPED_NYLIUM"),
    SCULKS_SPREADING(
        WORLD_DESTRUCTION, true, 2, 7, true, 19, "SCULK"),


    GRASS_SPREADING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 1, 3, true, "GRASS_BLOCK", "GRASS"),
    MYCELIUM_SPREADING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 1, 4, true, "MYCELIUM", "MYCEL"),
    LITTLE_MUSHROOMS_SPREADING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 1, 5, true, "RED_MUSHROOM"),
    PUMPKINS_GROWING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 1, 6, true, "PUMPKIN"),
    MELONS_GROWING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 1, 7, true, "MELON_BLOCK", "MELON"),

    NETHER_WARTS_GROWING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 2, 3, true, "NETHER_WART", "NETHER_STALK"),
    COCOAS_GROWING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 2, 4, true, "COCOA_BEANS", "INK_SACK:3"),
    WHEAT_GROWING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 2, 5, true, "WHEAT"),
    POTATOES_GROWING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 2, 6, true, "POTATO_ITEM", "POTATO"),
    CARROTS_GROWING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 2, 7, true, "CARROT_ITEM", "CARROT"),

    BEETROOTS_GROWING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 3, 4, true, 9, "BEETROOT"),
    SWEET_BERRIES_GROWING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 3, 5, true, 14, "SWEET_BERRIES"),
    AMETHYST_CLUSTERS_GROWING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 3, 6, true, 17, "AMETHYST_CLUSTER"),


    SUGAR_CANE_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 1, 3, true, "SUGAR_CANE"),
    CACTUS_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 1, 4, true, "CACTUS"),
    TREES_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 1, 5, true, "BIRCH_SAPLING", "SAPLING:2"),
    VINES_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 1, 6, true, "VINE"),
    GIANT_MUSHROOMS_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 1, 7, true, "RED_MUSHROOM_BLOCK", "HUGE_MUSHROOM_2"),

    CHORUSES_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 2, 2, true, 9, "CHORUS_FLOWER"),
    KELPS_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 2, 3, true, 13, "KELP"),
    BAMBOO_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 2, 4, true, 14, "BAMBOO"),
    WEEPING_VINES_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 2, 5, true, 16, "WEEPING_VINES"),
    TWISTING_VINES_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 2, 6, true, 16, "TWISTING_VINES"),
    POINTED_DRIPSTONES_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 2, 7, true, 17, "POINTED_DRIPSTONE"),
    GLOW_BERRIES_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 2, 8, true, 17, "GLOW_BERRIES"),


    DEBUG_MESSAGES(
        SETTINGS, true, 2, 4, true, "PAPER"),
    ALLOW_UNRECOGNIZED_ACTIONS(
        SETTINGS, true, 2, 6, true, "BARRIER"),


    IGNORED_STATE(
        TEST, true, 1, 1, true, "BARRIER");

    private final PControlCategory category;
    @Deprecated // TODO Mark trigger as available while filling rules on plugin startup
    private final short minVersion;
    private boolean triggerAvailable = false;
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
        if (this.slot < 0 || this.slot >= 3 * 9) {
            throw new IllegalArgumentException("Invalid slot of trigger " + this);
        }
        this.defaults = defaults;
        this.category.addTrigger(this);
        this.icon = BukkitUtils.matchIcon(iconVariants);
    }

    @Nonnull
    public PControlCategory getCategory() {
        return this.category;
    }

    @Deprecated // TODO Mark trigger as available while filling rules on plugin startup
    public short getMinVersion() {
        return this.minVersion;
    }

    public void markAvailable() {
        this.triggerAvailable = true;
    }

    public boolean isAvailable() {
        return this.triggerAvailable;
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
