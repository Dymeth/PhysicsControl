package ru.dymeth.pcontrol.data.trigger;

import org.bukkit.Material;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.category.CategoriesRegistry;
import ru.dymeth.pcontrol.data.category.PControlCategory;
import ru.dymeth.pcontrol.set.material.ItemTypesSet;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TriggersRegistry {

    private final PControlData data;
    private final Map<String, PControlTrigger> valuesByName = new LinkedHashMap<>();
    private final PControlTrigger[] allValues;

    public final PControlTrigger IGNORED_STATE;

    public TriggersRegistry(@Nonnull PControlData data) {
        this.data = data;

        this.registerTriggers(data.getCategoriesRegistry());

        this.IGNORED_STATE = this.valueOf("IGNORED_STATE");

        this.allValues = new PControlTrigger[this.valuesByName.values().size()];
        int i = 0;
        for (PControlTrigger element : this.valuesByName.values()) {
            this.allValues[i++] = element;
        }
    }

    private void registerTriggers(@Nonnull CategoriesRegistry categories) {
        reg("RABBITS_EATING_CARROTS",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("CARROT_ITEM");
                } else {
                    set.addPrimitive(Material.CARROT);
                }
            });
        reg("VILLAGERS_FARMING",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                set.addPrimitive(Material.WHEAT);
            });
        reg("SHEEPS_EATING_GRASS",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("WOOL");
                } else {
                    set.addPrimitive(Material.WHITE_WOOL);
                }
            });
        reg("SNOW_GOLEMS_CREATE_SNOW",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                set.addPrimitive(Material.SNOW_BLOCK);
            });
        reg("SILVERFISHES_HIDING_IN_BLOCKS",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("MONSTER_EGGS:4");
                } else {
                    set.addPrimitive(Material.INFESTED_CRACKED_STONE_BRICKS);
                }
            });
        reg("ZOMBIES_BREAK_DOORS",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("SKULL_ITEM:2");
                } else {
                    set.addPrimitive(Material.ZOMBIE_HEAD);
                }
            });
        reg("ENDERMANS_GRIEFING",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                set.addPrimitive(Material.ENDER_PEARL);
            });
        reg("WITHERS_GRIEFING",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("SKULL_ITEM:1");
                } else {
                    set.addPrimitive(Material.WITHER_SKELETON_SKULL);
                }
            });
        reg("TURTLES_LAYING_EGGS",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 13, 0)) {
                    set.addPrimitive(Material.TURTLE_EGG);
                }
            });
        reg("FROGSPAWN_LAYING_AND_SPAWNING",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 19, 0)) {
                    set.addPrimitive(Material.FROGSPAWN);
                }
            });
        reg("WITHER_CREATE_WITHER_ROSE_BLOCKS",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 14, 0)) {
                    set.addPrimitive(Material.WITHER_ROSE);
                }
            });
        reg("FOXES_EATS_FROM_SWEET_BERRY_BUSHES",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 14, 0)) {
                    set.addPrimitive(Material.SWEET_BERRIES);
                }
            });
        reg("RAVAGERS_DESTROY_BLOCKS",
            categories.MOBS_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 14, 0)) {
                    set.addPrimitive(Material.RAVAGER_SPAWN_EGG);
                }
            });

        reg("PLAYERS_FLINT_USAGE",
            categories.PLAYERS_INTERACTIONS, false, true, set -> {
                set.addPrimitive(Material.FLINT_AND_STEEL);
            });
        reg("END_PORTAL_FRAMES_FILLING",
            categories.PLAYERS_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("ENDER_PORTAL_FRAME");
                } else {
                    set.addPrimitive(Material.END_PORTAL_FRAME);
                }
            });
        reg("PLAYERS_BONE_MEAL_USAGE",
            categories.PLAYERS_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("INK_SACK:15");
                } else {
                    set.addPrimitive(Material.BONE_MEAL);
                }
            });
        reg("GLOW_BERRIES_PICKING",
            categories.PLAYERS_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 17, 0)) {
                    set.addPrimitive(Material.GLOW_BERRIES);
                }
            });

        reg("BURNING_ARROWS_ACTIVATE_TNT",
            categories.ENTITIES_INTERACTIONS, false, true, set -> {
                set.addPrimitive(Material.TNT);
            });
        reg("FARMLANDS_TRAMPLING",
            categories.ENTITIES_INTERACTIONS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("SOIL");
                } else {
                    set.addPrimitive(Material.FARMLAND);
                }
            });
        reg("DRAGON_EGGS_TELEPORTING",
            categories.ENTITIES_INTERACTIONS, false, true, set -> {
                set.addPrimitive(Material.DRAGON_EGG);
            });
        reg("FROSTED_ICE_PHYSICS",
            categories.ENTITIES_INTERACTIONS, false, true, set -> {
                set.addPrimitive(Material.ICE);
            });
        reg("BLOCK_HIT_PROJECTILES_REMOVING",
            categories.ENTITIES_INTERACTIONS, false, false, set -> {
                set.addPrimitive(Material.ARROW);
            });
        reg("TURTLE_EGGS_TRAMPLING",
            categories.ENTITIES_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 13, 0)) {
                    set.addPrimitive(Material.TURTLE_EGG);
                }
            });
        reg("DRIPLEAFS_LOWERING",
            categories.ENTITIES_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 17, 0)) {
                    set.addPrimitive(Material.BIG_DRIPLEAF);
                }
            });
        reg("POWDER_SNOW_MELTS_FROM_BURNING_ENTITIES",
            categories.ENTITIES_INTERACTIONS, false, true, set -> {
                if (this.data.hasVersion(1, 17, 0)) {
                    set.addPrimitive(Material.POWDER_SNOW_BUCKET);
                }
            });

        reg("LADDERS_DESTROYING",
            categories.BUILDING, false, true, set -> {
                set.addPrimitive(Material.LADDER);
            });
        reg("SIGNS_DESTROYING",
            categories.BUILDING, false, true, set -> {
                if (!this.data.hasVersion(1, 14, 0)) {
                    set.add("SIGN");
                } else {
                    set.addPrimitive(Material.OAK_SIGN);
                }
            });
        reg("RAILS_DESTROYING",
            categories.BUILDING, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("RAILS");
                } else {
                    set.addPrimitive(Material.RAIL);
                }
            });
        reg("TORCHES_DESTROYING",
            categories.BUILDING, false, true, set -> {
                set.addPrimitive(Material.TORCH);
            });
        reg("REDSTONE_TORCHES_DESTROYING",
            categories.BUILDING, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("REDSTONE_TORCH_ON");
                } else {
                    set.addPrimitive(Material.REDSTONE_TORCH);
                }
            });
        reg("SOUL_TORCHES_DESTROYING",
            categories.BUILDING, false, true, set -> {
                if (this.data.hasVersion(1, 16, 0)) {
                    set.addPrimitive(Material.SOUL_TORCH);
                }
            });
        reg("SAPLINGS_DESTROYING",
            categories.BUILDING, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("SAPLING");
                } else {
                    set.addPrimitive(Material.OAK_SAPLING);
                }
            });

        reg("GRAVEL_FALLING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                set.addPrimitive(Material.GRAVEL);
            });
        reg("SAND_FALLING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                set.addPrimitive(Material.SAND);
            });
        reg("ANVILS_FALLING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                set.addPrimitive(Material.ANVIL);
            });
        reg("DRAGON_EGGS_FALLING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                set.addPrimitive(Material.DRAGON_EGG);
            });
        reg("CONCRETE_POWDERS_FALLING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("CONCRETE_POWDER:5");
                } else {
                    set.addPrimitive(Material.LIME_CONCRETE_POWDER);
                }
            });
        reg("SCAFFOLDING_FALLING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                if (this.data.hasVersion(1, 14, 0)) {
                    set.addPrimitive(Material.SCAFFOLDING);
                }
            });
        reg("POINTED_DRIPSTONES_FALLING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                if (this.data.hasVersion(1, 17, 0)) {
                    set.addPrimitive(Material.POINTED_DRIPSTONE);
                }
            });
        reg("WATER_FLOWING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                set.addPrimitive(Material.WATER_BUCKET);
            });
        reg("LAVA_FLOWING",
            categories.GRAVITY_AND_LIQUIDS, false, true, set -> {
                set.addPrimitive(Material.LAVA_BUCKET);
            });

        reg("FIRE_SPREADING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                set.addPrimitive(Material.FLINT_AND_STEEL);
            });
        reg("SNOW_MELTING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                set.addPrimitive(Material.SNOW_BLOCK);
            });
        reg("FARMLANDS_DRYING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("SOIL");
                } else {
                    set.addPrimitive(Material.FARMLAND);
                }
            });
        reg("ICE_MELTING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                set.addPrimitive(Material.ICE);
            });
        reg("LEAVES_DECAY",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("LEAVES");
                } else {
                    set.addPrimitive(Material.OAK_LEAVES);
                }
            });
        reg("GRASS_BLOCKS_FADING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("GRASS");
                } else {
                    set.addPrimitive(Material.GRASS_BLOCK);
                }
            });
        reg("CORALS_DRYING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                if (this.data.hasVersion(1, 13, 0)) {
                    set.addPrimitive(Material.FIRE_CORAL);
                }
            });
        reg("CRIMSON_NYLIUM_FADING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                if (this.data.hasVersion(1, 16, 0)) {
                    set.addPrimitive(Material.CRIMSON_NYLIUM);
                }
            });
        reg("WARPED_NYLIUM_FADING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                if (this.data.hasVersion(1, 16, 0)) {
                    set.addPrimitive(Material.WARPED_NYLIUM);
                }
            });
        reg("SCULKS_SPREADING",
            categories.WORLD_DESTRUCTION, true, true, set -> {
                if (this.data.hasVersion(1, 19, 0)) {
                    set.addPrimitive(Material.SCULK);
                }
            });

        reg("GRASS_SPREADING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("GRASS");
                } else {
                    set.addPrimitive(Material.GRASS_BLOCK);
                }
            });
        reg("MYCELIUM_SPREADING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("MYCEL");
                } else {
                    set.addPrimitive(Material.MYCELIUM);
                }
            });
        reg("LITTLE_MUSHROOMS_SPREADING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                set.addPrimitive(Material.RED_MUSHROOM);
            });
        reg("PUMPKINS_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                set.addPrimitive(Material.PUMPKIN);
            });
        reg("MELONS_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("MELON_BLOCK");
                } else {
                    set.addPrimitive(Material.MELON);
                }
            });
        reg("NETHER_WARTS_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("NETHER_STALK");
                } else {
                    set.addPrimitive(Material.NETHER_WART);
                }
            });
        reg("COCOAS_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("INK_SACK:3");
                } else {
                    set.addPrimitive(Material.COCOA_BEANS);
                }
            });
        reg("WHEAT_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                set.addPrimitive(Material.WHEAT);
            });
        reg("POTATOES_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("POTATO_ITEM");
                } else {
                    set.addPrimitive(Material.POTATO);
                }
            });
        reg("CARROTS_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("CARROT_ITEM");
                } else {
                    set.addPrimitive(Material.CARROT);
                }
            });
        reg("BEETROOTS_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (this.data.hasVersion(1, 9, 0)) {
                    set.addPrimitive(Material.BEETROOT);
                }
            });
        reg("SWEET_BERRIES_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (this.data.hasVersion(1, 14, 0)) {
                    set.addPrimitive(Material.SWEET_BERRIES);
                }
            });
        reg("AMETHYST_CLUSTERS_GROWING",
            categories.GROWING_BLOCKS_AND_SMALL_PLANTS, true, true, set -> {
                if (this.data.hasVersion(1, 17, 0)) {
                    set.addPrimitive(Material.AMETHYST_CLUSTER);
                }
            });

        reg("SUGAR_CANE_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                set.addPrimitive(Material.SUGAR_CANE);
            });
        reg("CACTUS_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                set.addPrimitive(Material.CACTUS);
            });
        reg("TREES_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("SAPLING:2");
                } else {
                    set.addPrimitive(Material.BIRCH_SAPLING);
                }
            });
        reg("VINES_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                set.addPrimitive(Material.VINE);
            });
        reg("GIANT_MUSHROOMS_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (!this.data.hasVersion(1, 13, 0)) {
                    set.add("HUGE_MUSHROOM_2");
                } else {
                    set.addPrimitive(Material.RED_MUSHROOM_BLOCK);
                }
            });
        reg("CHORUSES_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (this.data.hasVersion(1, 9, 0)) {
                    set.addPrimitive(Material.CHORUS_FLOWER);
                }
            });
        reg("KELPS_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (this.data.hasVersion(1, 13, 0)) {
                    set.addPrimitive(Material.KELP);
                }
            });
        reg("BAMBOO_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (this.data.hasVersion(1, 14, 0)) {
                    set.addPrimitive(Material.BAMBOO);
                }
            });
        reg("WEEPING_VINES_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (this.data.hasVersion(1, 16, 0)) {
                    set.addPrimitive(Material.WEEPING_VINES);
                }
            });
        reg("TWISTING_VINES_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (this.data.hasVersion(1, 16, 0)) {
                    set.addPrimitive(Material.TWISTING_VINES);
                }
            });
        reg("POINTED_DRIPSTONES_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (this.data.hasVersion(1, 17, 0)) {
                    set.addPrimitive(Material.POINTED_DRIPSTONE);
                }
            });
        reg("GLOW_BERRIES_GROWING",
            categories.VINES_AND_TALL_STRUCTURES, true, true, set -> {
                if (this.data.hasVersion(1, 17, 0)) {
                    set.addPrimitive(Material.GLOW_BERRIES);
                }
            });

        reg("DEBUG_MESSAGES",
            categories.SETTINGS, true, true, set -> {
                set.addPrimitive(Material.PAPER);
            });
        reg("ALLOW_UNRECOGNIZED_ACTIONS",
            categories.SETTINGS, true, true, set -> {
                set.addPrimitive(Material.BARRIER);
            });

        reg("IGNORED_STATE",
            categories.TEST, true, true, set -> {
                set.addPrimitive(Material.BARRIER);
            });
    }

    @Nonnull
    private PControlTrigger reg(@Nonnull String triggerName,
                                @Nonnull PControlCategory category, boolean realtime, boolean defaults,
                                @Nonnull Consumer<ItemTypesSet> iconConsumer
    ) {
        PControlTrigger result = new PControlTrigger(triggerName,
            category, realtime, defaults,
            ItemTypesSet.create(false, triggerName + " trigger icon", this.data.log(), iconConsumer)
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
    public PControlTrigger valueOf(@Nonnull String name) {
        return this.valueOf(name, true);
    }

    @Nonnull
    public PControlTrigger valueOf(@Nonnull String name, boolean markAvailable) {
        PControlTrigger trigger = this.valuesByName.get(name);
        if (trigger == null) throw new IllegalArgumentException(name);
        if (markAvailable) trigger.markAvailable();
        return trigger;
    }
}
