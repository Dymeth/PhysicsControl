package ru.dymeth.pcontrol.data.trigger;

import org.bukkit.Material;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.category.CategoriesRegistry;
import ru.dymeth.pcontrol.data.category.PControlCategory;
import ru.dymeth.pcontrol.set.ItemsSet;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TriggersRegistry {

    private final PControlData data;
    private final Map<String, PControlTrigger> valuesByName = new LinkedHashMap<>();
    private final PControlTrigger[] allValues;

    public final PControlTrigger
        RABBITS_EATING_CARROTS,
        VILLAGERS_FARMING,
        SHEEPS_EATING_GRASS,
        SNOW_GOLEMS_CREATE_SNOW,
        SILVERFISHES_HIDING_IN_BLOCKS,
        ZOMBIES_BREAK_DOORS,
        ENDERMANS_GRIEFING,
        WITHERS_GRIEFING,
        TURTLES_LAYING_EGGS,
        FROGSPAWN_LAYING_AND_SPAWNING,
        WITHER_CREATE_WITHER_ROSE_BLOCKS,
        FOXES_EATS_FROM_SWEET_BERRY_BUSHES,
        RAVAGERS_DESTROY_BLOCKS,

    PLAYERS_FLINT_USAGE,
        END_PORTAL_FRAMES_FILLING,
        PLAYERS_BONE_MEAL_USAGE,
        GLOW_BERRIES_PICKING,

    BURNING_ARROWS_ACTIVATE_TNT,
        FARMLANDS_TRAMPLING,
        DRAGON_EGGS_TELEPORTING,
        FROSTED_ICE_PHYSICS,
        BLOCK_HIT_PROJECTILES_REMOVING,
        TURTLE_EGGS_TRAMPLING,
        DRIPLEAFS_LOWERING,
        POWDER_SNOW_MELTS_FROM_BURNING_ENTITIES,

    LADDERS_DESTROYING,
        SIGNS_DESTROYING,
        RAILS_DESTROYING,
        TORCHES_DESTROYING,
        REDSTONE_TORCHES_DESTROYING,
        SOUL_TORCHES_DESTROYING,
        SAPLINGS_DESTROYING,

    GRAVEL_FALLING,
        SAND_FALLING,
        ANVILS_FALLING,
        DRAGON_EGGS_FALLING,
        CONCRETE_POWDERS_FALLING,
        SCAFFOLDING_FALLING,
        POINTED_DRIPSTONES_FALLING,
        WATER_FLOWING,
        LAVA_FLOWING,

    FIRE_SPREADING,
        SNOW_MELTING,
        FARMLANDS_DRYING,
        ICE_MELTING,
        LEAVES_DECAY,
        GRASS_BLOCKS_FADING,
        CORALS_DRYING,
        CRIMSON_NYLIUM_FADING,
        WARPED_NYLIUM_FADING,
        SCULKS_SPREADING,

    GRASS_SPREADING,
        MYCELIUM_SPREADING,
        LITTLE_MUSHROOMS_SPREADING,
        PUMPKINS_GROWING,
        MELONS_GROWING,
        NETHER_WARTS_GROWING,
        COCOAS_GROWING,
        WHEAT_GROWING,
        POTATOES_GROWING,
        CARROTS_GROWING,
        BEETROOTS_GROWING,
        SWEET_BERRIES_GROWING,
        AMETHYST_CLUSTERS_GROWING,

    SUGAR_CANE_GROWING,
        CACTUS_GROWING,
        TREES_GROWING,
        VINES_GROWING,
        GIANT_MUSHROOMS_GROWING,
        CHORUSES_GROWING,
        KELPS_GROWING,
        BAMBOO_GROWING,
        WEEPING_VINES_GROWING,
        TWISTING_VINES_GROWING,
        POINTED_DRIPSTONES_GROWING,
        GLOW_BERRIES_GROWING,

    DEBUG_MESSAGES,
        ALLOW_UNRECOGNIZED_ACTIONS,

    IGNORED_STATE;

    public TriggersRegistry(@Nonnull PControlData data) {
        this.data = data;
        CategoriesRegistry categories = data.categories();

        RABBITS_EATING_CARROTS = reg("RABBITS_EATING_CARROTS",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("CARROT_ITEM");
                } else {
                    set.addPrimitive(Material.CARROT);
                }
            });
        VILLAGERS_FARMING = reg("VILLAGERS_FARMING",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                set.addPrimitive(Material.WHEAT);
            });
        SHEEPS_EATING_GRASS = reg("SHEEPS_EATING_GRASS",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("WOOL");
                } else {
                    set.addPrimitive(Material.WHITE_WOOL);
                }
            });
        SNOW_GOLEMS_CREATE_SNOW = reg("SNOW_GOLEMS_CREATE_SNOW",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                set.addPrimitive(Material.SNOW_BLOCK);
            });
        SILVERFISHES_HIDING_IN_BLOCKS = reg("SILVERFISHES_HIDING_IN_BLOCKS",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("MONSTER_EGGS:4");
                } else {
                    set.addPrimitive(Material.INFESTED_CRACKED_STONE_BRICKS);
                }
            });
        ZOMBIES_BREAK_DOORS = reg("ZOMBIES_BREAK_DOORS",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("SKULL_ITEM:2");
                } else {
                    set.addPrimitive(Material.ZOMBIE_HEAD);
                }
            });
        ENDERMANS_GRIEFING = reg("ENDERMANS_GRIEFING",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                set.addPrimitive(Material.ENDER_PEARL);
            });
        WITHERS_GRIEFING = reg("WITHERS_GRIEFING",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("SKULL_ITEM:1");
                } else {
                    set.addPrimitive(Material.WITHER_SKELETON_SKULL);
                }
            });
        TURTLES_LAYING_EGGS = reg("TURTLES_LAYING_EGGS",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 13, 0)) {
                    set.addPrimitive(Material.TURTLE_EGG);
                }
            });
        FROGSPAWN_LAYING_AND_SPAWNING = reg("FROGSPAWN_LAYING_AND_SPAWNING",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 19, 0)) {
                    set.addPrimitive(Material.FROGSPAWN);
                }
            });
        WITHER_CREATE_WITHER_ROSE_BLOCKS = reg("WITHER_CREATE_WITHER_ROSE_BLOCKS",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 14, 0)) {
                    set.addPrimitive(Material.WITHER_ROSE);
                }
            });
        FOXES_EATS_FROM_SWEET_BERRY_BUSHES = reg("FOXES_EATS_FROM_SWEET_BERRY_BUSHES",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 14, 0)) {
                    set.addPrimitive(Material.SWEET_BERRIES);
                }
            });
        RAVAGERS_DESTROY_BLOCKS = reg("RAVAGERS_DESTROY_BLOCKS",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 14, 0)) {
                    set.addPrimitive(Material.RAVAGER_SPAWN_EGG);
                }
            });

        PLAYERS_FLINT_USAGE = reg("PLAYERS_FLINT_USAGE",
            categories.PLAYERS_INTERACTIONS, false, true, set -> {
                set.addPrimitive(Material.FLINT_AND_STEEL);
            });
        END_PORTAL_FRAMES_FILLING = reg("END_PORTAL_FRAMES_FILLING",
            categories.PLAYERS_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("ENDER_PORTAL_FRAME");
                } else {
                    set.addPrimitive(Material.END_PORTAL_FRAME);
                }
            });
        PLAYERS_BONE_MEAL_USAGE = reg("PLAYERS_BONE_MEAL_USAGE",
            categories.PLAYERS_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("INK_SACK:15");
                } else {
                    set.addPrimitive(Material.BONE_MEAL);
                }
            });
        GLOW_BERRIES_PICKING = reg("GLOW_BERRIES_PICKING",
            categories.PLAYERS_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 17, 0)) {
                    set.addPrimitive(Material.GLOW_BERRIES);
                }
            });

        BURNING_ARROWS_ACTIVATE_TNT = reg("BURNING_ARROWS_ACTIVATE_TNT",
            categories.ENTITIES_INTERACTIONS, false, true, set -> {
                set.addPrimitive(Material.TNT);
            });
        FARMLANDS_TRAMPLING = reg("FARMLANDS_TRAMPLING",
            categories.ENTITIES_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("SOIL");
                } else {
                    set.addPrimitive(Material.FARMLAND);
                }
            });
        DRAGON_EGGS_TELEPORTING = reg("DRAGON_EGGS_TELEPORTING",
            categories.ENTITIES_INTERACTIONS, false, true, set -> {
                set.addPrimitive(Material.DRAGON_EGG);
            });
        FROSTED_ICE_PHYSICS = reg("FROSTED_ICE_PHYSICS",
            categories.ENTITIES_INTERACTIONS, false, true, set -> {
                set.addPrimitive(Material.ICE);
            });
        BLOCK_HIT_PROJECTILES_REMOVING = reg("BLOCK_HIT_PROJECTILES_REMOVING",
            categories.ENTITIES_INTERACTIONS, false, false, set -> {
                set.addPrimitive(Material.ARROW);
            });
        TURTLE_EGGS_TRAMPLING = reg("TURTLE_EGGS_TRAMPLING",
            categories.ENTITIES_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 13, 0)) {
                    set.addPrimitive(Material.TURTLE_EGG);
                }
            });
        DRIPLEAFS_LOWERING = reg("DRIPLEAFS_LOWERING",
            categories.ENTITIES_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 17, 0)) {
                    set.addPrimitive(Material.BIG_DRIPLEAF);
                }
            });
        POWDER_SNOW_MELTS_FROM_BURNING_ENTITIES = reg("POWDER_SNOW_MELTS_FROM_BURNING_ENTITIES",
            categories.ENTITIES_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 17, 0)) {
                    set.addPrimitive(Material.POWDER_SNOW_BUCKET);
                }
            });

        LADDERS_DESTROYING = reg("LADDERS_DESTROYING",
            categories.BUILDING, false, true, set -> {
                set.addPrimitive(Material.LADDER);
            });
        SIGNS_DESTROYING = reg("SIGNS_DESTROYING",
            categories.BUILDING, false, true, set -> {
                if (!this.data.hasVersion(1, 14, 0)) {
                    set.add("SIGN");
                } else {
                    set.addPrimitive(Material.OAK_SIGN);
                }
            });
        RAILS_DESTROYING = reg("RAILS_DESTROYING",
            categories.BUILDING, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("RAILS");
                } else {
                    set.addPrimitive(Material.RAIL);
                }
            });
        TORCHES_DESTROYING = reg("TORCHES_DESTROYING",
            categories.BUILDING, false, true, set -> {
                set.addPrimitive(Material.TORCH);
            });
        REDSTONE_TORCHES_DESTROYING = reg("REDSTONE_TORCHES_DESTROYING",
            categories.BUILDING, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("REDSTONE_TORCH_ON");
                } else {
                    set.addPrimitive(Material.REDSTONE_TORCH);
                }
            });
        SOUL_TORCHES_DESTROYING = reg("SOUL_TORCHES_DESTROYING",
            categories.BUILDING, false, true, set -> {
                if (this.data.hasVersion(1, 16, 0)) {
                    set.addPrimitive(Material.SOUL_TORCH);
                }
            });
        SAPLINGS_DESTROYING = reg("SAPLINGS_DESTROYING",
            categories.BUILDING, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("SAPLING");
                } else {
                    set.addPrimitive(Material.OAK_SAPLING);
                }
            });

        GRAVEL_FALLING = reg("GRAVEL_FALLING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                set.addPrimitive(Material.GRAVEL);
            });
        SAND_FALLING = reg("SAND_FALLING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                set.addPrimitive(Material.SAND);
            });
        ANVILS_FALLING = reg("ANVILS_FALLING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                set.addPrimitive(Material.ANVIL);
            });
        DRAGON_EGGS_FALLING = reg("DRAGON_EGGS_FALLING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                set.addPrimitive(Material.DRAGON_EGG);
            });
        CONCRETE_POWDERS_FALLING = reg("CONCRETE_POWDERS_FALLING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("CONCRETE_POWDER:5");
                } else {
                    set.addPrimitive(Material.LIME_CONCRETE_POWDER);
                }
            });
        SCAFFOLDING_FALLING = reg("SCAFFOLDING_FALLING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                if (this.data.hasVersion(1, 14, 0)) {
                    set.addPrimitive(Material.SCAFFOLDING);
                }
            });
        POINTED_DRIPSTONES_FALLING = reg("POINTED_DRIPSTONES_FALLING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                if (this.data.hasVersion(1, 17, 0)) {
                    set.addPrimitive(Material.POINTED_DRIPSTONE);
                }
            });
        WATER_FLOWING = reg("WATER_FLOWING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                set.addPrimitive(Material.WATER_BUCKET);
            });
        LAVA_FLOWING = reg("LAVA_FLOWING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                set.addPrimitive(Material.LAVA_BUCKET);
            });

        FIRE_SPREADING = reg("FIRE_SPREADING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                set.addPrimitive(Material.FLINT_AND_STEEL);
            });
        SNOW_MELTING = reg("SNOW_MELTING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                set.addPrimitive(Material.SNOW_BLOCK);
            });
        FARMLANDS_DRYING = reg("FARMLANDS_DRYING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("SOIL");
                } else {
                    set.addPrimitive(Material.FARMLAND);
                }
            });
        ICE_MELTING = reg("ICE_MELTING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                set.addPrimitive(Material.ICE);
            });
        LEAVES_DECAY = reg("LEAVES_DECAY",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("LEAVES");
                } else {
                    set.addPrimitive(Material.OAK_LEAVES);
                }
            });
        GRASS_BLOCKS_FADING = reg("GRASS_BLOCKS_FADING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("GRASS");
                } else {
                    set.addPrimitive(Material.GRASS_BLOCK);
                }
            });
        CORALS_DRYING = reg("CORALS_DRYING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                if (this.data.hasVersion(1, 13, 0)) {
                    set.addPrimitive(Material.FIRE_CORAL);
                }
            });
        CRIMSON_NYLIUM_FADING = reg("CRIMSON_NYLIUM_FADING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                if (this.data.hasVersion(1, 16, 0)) {
                    set.addPrimitive(Material.CRIMSON_NYLIUM);
                }
            });
        WARPED_NYLIUM_FADING = reg("WARPED_NYLIUM_FADING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                if (this.data.hasVersion(1, 16, 0)) {
                    set.addPrimitive(Material.WARPED_NYLIUM);
                }
            });
        SCULKS_SPREADING = reg("SCULKS_SPREADING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                if (this.data.hasVersion(1, 19, 0)) {
                    set.addPrimitive(Material.SCULK);
                }
            });

        GRASS_SPREADING = reg("GRASS_SPREADING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("GRASS");
                } else {
                    set.addPrimitive(Material.GRASS_BLOCK);
                }
            });
        MYCELIUM_SPREADING = reg("MYCELIUM_SPREADING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("MYCEL");
                } else {
                    set.addPrimitive(Material.MYCELIUM);
                }
            });
        LITTLE_MUSHROOMS_SPREADING = reg("LITTLE_MUSHROOMS_SPREADING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                set.addPrimitive(Material.RED_MUSHROOM);
            });
        PUMPKINS_GROWING = reg("PUMPKINS_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                set.addPrimitive(Material.PUMPKIN);
            });
        MELONS_GROWING = reg("MELONS_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("MELON_BLOCK");
                } else {
                    set.addPrimitive(Material.MELON);
                }
            });
        NETHER_WARTS_GROWING = reg("NETHER_WARTS_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("NETHER_STALK");
                } else {
                    set.addPrimitive(Material.NETHER_WART);
                }
            });
        COCOAS_GROWING = reg("COCOAS_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("INK_SACK:3");
                } else {
                    set.addPrimitive(Material.COCOA_BEANS);
                }
            });
        WHEAT_GROWING = reg("WHEAT_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                set.addPrimitive(Material.WHEAT);
            });
        POTATOES_GROWING = reg("POTATOES_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("POTATO_ITEM");
                } else {
                    set.addPrimitive(Material.POTATO);
                }
            });
        CARROTS_GROWING = reg("CARROTS_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("CARROT_ITEM");
                } else {
                    set.addPrimitive(Material.CARROT);
                }
            });
        BEETROOTS_GROWING = reg("BEETROOTS_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (this.data.hasVersion(1, 9, 0)) {
                    set.addPrimitive(Material.BEETROOT);
                }
            });
        SWEET_BERRIES_GROWING = reg("SWEET_BERRIES_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (this.data.hasVersion(1, 14, 0)) {
                    set.addPrimitive(Material.SWEET_BERRIES);
                }
            });
        AMETHYST_CLUSTERS_GROWING = reg("AMETHYST_CLUSTERS_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (this.data.hasVersion(1, 17, 0)) {
                    set.addPrimitive(Material.AMETHYST_CLUSTER);
                }
            });

        SUGAR_CANE_GROWING = reg("SUGAR_CANE_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                set.addPrimitive(Material.SUGAR_CANE);
            });
        CACTUS_GROWING = reg("CACTUS_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                set.addPrimitive(Material.CACTUS);
            });
        TREES_GROWING = reg("TREES_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("SAPLING:2");
                } else {
                    set.addPrimitive(Material.BIRCH_SAPLING);
                }
            });
        VINES_GROWING = reg("VINES_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                set.addPrimitive(Material.VINE);
            });
        GIANT_MUSHROOMS_GROWING = reg("GIANT_MUSHROOMS_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("HUGE_MUSHROOM_2");
                } else {
                    set.addPrimitive(Material.RED_MUSHROOM_BLOCK);
                }
            });
        CHORUSES_GROWING = reg("CHORUSES_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (this.data.hasVersion(1, 9, 0)) {
                    set.addPrimitive(Material.CHORUS_FLOWER);
                }
            });
        KELPS_GROWING = reg("KELPS_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (this.data.hasVersion(1, 13, 0)) {
                    set.addPrimitive(Material.KELP);
                }
            });
        BAMBOO_GROWING = reg("BAMBOO_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (this.data.hasVersion(1, 14, 0)) {
                    set.addPrimitive(Material.BAMBOO);
                }
            });
        WEEPING_VINES_GROWING = reg("WEEPING_VINES_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (this.data.hasVersion(1, 16, 0)) {
                    set.addPrimitive(Material.WEEPING_VINES);
                }
            });
        TWISTING_VINES_GROWING = reg("TWISTING_VINES_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (this.data.hasVersion(1, 16, 0)) {
                    set.addPrimitive(Material.TWISTING_VINES);
                }
            });
        POINTED_DRIPSTONES_GROWING = reg("POINTED_DRIPSTONES_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (this.data.hasVersion(1, 17, 0)) {
                    set.addPrimitive(Material.POINTED_DRIPSTONE);
                }
            });
        GLOW_BERRIES_GROWING = reg("GLOW_BERRIES_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (this.data.hasVersion(1, 17, 0)) {
                    set.addPrimitive(Material.GLOW_BERRIES);
                }
            });

        DEBUG_MESSAGES = reg("DEBUG_MESSAGES",
            categories.SETTINGS, true, true, set -> {
                set.addPrimitive(Material.PAPER);
            });
        ALLOW_UNRECOGNIZED_ACTIONS = reg("ALLOW_UNRECOGNIZED_ACTIONS",
            categories.SETTINGS, true, true, set -> {
                set.addPrimitive(Material.BARRIER);
            });

        IGNORED_STATE = reg("IGNORED_STATE",
            categories.TEST, true, true, set -> {
                set.addPrimitive(Material.BARRIER);
            });

        this.allValues = new PControlTrigger[this.valuesByName.values().size()];
        int i = 0;
        for (PControlTrigger element : this.valuesByName.values()) {
            this.allValues[i++] = element;
        }
    }

    @Nonnull
    private PControlTrigger reg(@Nonnull String triggerName,
                                @Nonnull PControlCategory category, boolean realtime, boolean defaults,
                                @Nonnull Consumer<ItemsSet> iconConsumer
    ) {
        PControlTrigger result = new PControlTrigger(triggerName,
            category, realtime, defaults,
            ItemsSet.create(triggerName + " trigger icon", this.data.log(), iconConsumer)
        );
        category.addTrigger(result);
        this.valuesByName.put(triggerName, result);
        return result;
    }

    @Nonnull
    public PControlTrigger[] values() {
        return this.allValues;
    }

    @Nonnull
    public PControlTrigger valueOf(String name) {
        PControlTrigger result = this.valuesByName.get(name);
        if (result == null) throw new IllegalArgumentException(name);
        return result;
    }
}
