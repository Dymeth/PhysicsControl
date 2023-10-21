package ru.dymeth.pcontrol.data;

import org.bukkit.inventory.ItemStack;
import ru.dymeth.pcontrol.util.MaterialUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static ru.dymeth.pcontrol.data.PControlCategory.*;

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
        GRAVITY_BLOCKS, false, 2, 6, true, "LIME_CONCRETE_POWDER", "CONCRETE_POWDER:5"),
    SCAFFOLDING_FALLING(
        GRAVITY_BLOCKS, false, 2, 7, true, "SCAFFOLDING"),
    POINTED_DRIPSTONES_FALLING(
        GRAVITY_BLOCKS, false, 2, 8, true, "POINTED_DRIPSTONE"),


    WATER_FLOWING(
        LIQUIDS, false, 2, 4, true, "WATER_BUCKET"),
    LAVA_FLOWING(
        LIQUIDS, false, 2, 6, true, "LAVA_BUCKET"),


    LADDERS_DESTROYING(
        BUILDING, false, 1, 4, true, "LADDER"),
    SIGNS_DESTROYING(
        BUILDING, false, 1, 5, true, "OAK_SIGN", "SIGN"),
    RAILS_DESTROYING(
        BUILDING, false, 1, 6, true, "RAIL", "RAILS"),

    TORCHES_DESTROYING(
        BUILDING, false, 2, 4, true, "TORCH"),
    REDSTONE_TORCHES_DESTROYING(
        BUILDING, false, 2, 5, true, "REDSTONE_TORCH", "REDSTONE_TORCH_ON"),
    SOUL_TORCHES_DESTROYING(
        BUILDING, false, 2, 6, true, "SOUL_TORCH"),

    SAPLINGS_DESTROYING(
        BUILDING, false, 3, 5, true, "SAPLING", "OAK_SAPLING"),


    RABBITS_EATING_CARROTS(
        MOBS_INTERACTIONS, false, 1, 3, true, "CARROT_ITEM", "CARROT"),
    VILLAGERS_FARMING(
        MOBS_INTERACTIONS, false, 1, 4, true, "WHEAT"),
    SHEEPS_EATING_GRASS(
        MOBS_INTERACTIONS, false, 1, 5, true, "WHITE_WOOL", "WOOL"),
    SNOW_GOLEMS_CREATE_SNOW(
        MOBS_INTERACTIONS, false, 1, 6, true, "SNOW_BLOCK"),
    SILVERFISHES_HIDING_IN_BLOCKS(
        MOBS_INTERACTIONS, false, 1, 7, true, "INFESTED_CRACKED_STONE_BRICKS", "MONSTER_EGGS:4"),

    ZOMBIES_BREAK_DOORS(
        MOBS_INTERACTIONS, false, 2, 3, true, "ZOMBIE_HEAD", "SKULL_ITEM:2"),
    ENDERMANS_GRIEFING(
        MOBS_INTERACTIONS, false, 2, 4, true, "ENDER_PEARL"),
    WITHERS_GRIEFING(
        MOBS_INTERACTIONS, false, 2, 5, true, "WITHER_SKELETON_SKULL", "SKULL_ITEM:1"),
    TURTLES_LAYING_EGGS(
        MOBS_INTERACTIONS, false, 2, 6, true, "TURTLE_EGG"),
    FROGSPAWN_LAYING_AND_SPAWNING(
        MOBS_INTERACTIONS, false, 2, 7, true, "FROGSPAWN"),

    WITHER_CREATE_WITHER_ROSE_BLOCKS(
        MOBS_INTERACTIONS, false, 3, 4, true, "WITHER_ROSE"),
    FOXES_EATS_FROM_SWEET_BERRY_BUSHES(
        MOBS_INTERACTIONS, false, 3, 5, true, "SWEET_BERRIES"),
    RAVAGERS_DESTROY_BLOCKS(
        MOBS_INTERACTIONS, false, 3, 6, true, "RAVAGER_SPAWN_EGG"),


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
        ENTITIES_INTERACTIONS, false, 2, 5, true, "ICE"),
    BLOCK_HIT_PROJECTILES_REMOVING(
        ENTITIES_INTERACTIONS, false, 2, 6, false, "ARROW"),
    TURTLE_EGGS_TRAMPLING(
        ENTITIES_INTERACTIONS, false, 2, 7, true, "TURTLE_EGG"),

    DRIPLEAFS_LOWERING(
        ENTITIES_INTERACTIONS, false, 3, 4, true, "BIG_DRIPLEAF"),
    POWDER_SNOW_MELTS_FROM_BURNING_ENTITIES(
        ENTITIES_INTERACTIONS, false, 3, 5, true, "POWDER_SNOW_BUCKET"),
    GLOW_BERRIES_PICKING(
        ENTITIES_INTERACTIONS, false, 3, 6, true, "GLOW_BERRIES"),


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
        WORLD_DESTRUCTION, true, 2, 4, true, "FIRE_CORAL"),
    CRIMSON_NYLIUM_FADING(
        WORLD_DESTRUCTION, true, 2, 5, true, "CRIMSON_NYLIUM"),
    WARPED_NYLIUM_FADING(
        WORLD_DESTRUCTION, true, 2, 6, true, "WARPED_NYLIUM"),
    SCULKS_SPREADING(
        WORLD_DESTRUCTION, true, 2, 7, true, "SCULK"),


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
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 3, 4, true, "BEETROOT"),
    SWEET_BERRIES_GROWING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 3, 5, true, "SWEET_BERRIES"),
    AMETHYST_CLUSTERS_GROWING(
        GROWING_BLOCKS_AND_SMALL_PLANTS, true, 3, 6, true, "AMETHYST_CLUSTER"),


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
        VINES_AND_TALL_STRUCTURES, true, 2, 2, true, "CHORUS_FLOWER"),
    KELPS_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 2, 3, true, "KELP"),
    BAMBOO_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 2, 4, true, "BAMBOO"),
    WEEPING_VINES_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 2, 5, true, "WEEPING_VINES"),
    TWISTING_VINES_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 2, 6, true, "TWISTING_VINES"),
    POINTED_DRIPSTONES_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 2, 7, true, "POINTED_DRIPSTONE"),
    GLOW_BERRIES_GROWING(
        VINES_AND_TALL_STRUCTURES, true, 2, 8, true, "GLOW_BERRIES"),


    DEBUG_MESSAGES(
        SETTINGS, true, 2, 4, true, "PAPER"),
    ALLOW_UNRECOGNIZED_ACTIONS(
        SETTINGS, true, 2, 6, true, "BARRIER"),


    IGNORED_STATE(
        TEST, true, 1, 1, true, "BARRIER");

    private final PControlCategory category;
    private boolean triggerAvailable = false;
    private final ItemStack icon;
    private final boolean realtime;
    private final short slot;
    private final boolean defaults;

    PControlTrigger(@Nonnull PControlCategory category, boolean realtime, int row, int column, boolean defaults, @Nonnull String... iconVariants) {
        this.category = category;
        this.realtime = realtime;
        this.slot = (short) ((row - 1) * 9 + column - 1);
        if (this.slot < 0 || this.slot >= 3 * 9) {
            throw new IllegalArgumentException("Invalid slot of trigger " + this);
        }
        this.defaults = defaults;
        this.category.addTrigger(this);
        this.icon = MaterialUtils.matchIcon(iconVariants);
    }

    @Nonnull
    public PControlCategory getCategory() {
        return this.category;
    }

    public void markAvailable() {
        this.triggerAvailable = true;
    }

    public boolean isAvailable() {
        return this.triggerAvailable;
    }

    @Nullable
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
