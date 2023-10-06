package ru.dymeth.pcontrol;

import org.bukkit.Material;
import org.bukkit.Tag;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.set.BlocksSet;

import javax.annotation.Nonnull;
import java.util.Set;

public final class CustomTag {

    public final Set<Material>
        WORLD_AIR,
        WOODEN_DOORS,
        PRESSURE_PLATES,
        REDSTONE_PASSIVE_INPUTS,
        REDSTONE_ORE_BLOCKS,
        WATER,
        LAVA,
        SAND,
        GRAVEL,
        ANVIL,
        CONCRETE_POWDERS,
        GRAVITY_BLOCKS,
        NATURAL_GRAVITY_BLOCKS,
        BONE_MEAL_HERBS,
        LITTLE_MUSHROOMS,
        BLOCKS_UNDER_WATER_ONLY,
        GRASS_BLOCK,
        DIRT_PATH_BLOCK,
        FARMLAND_BLOCK,
        MYCELIUM_BLOCK,
        SUGAR_CANE_BLOCK,
        NETHER_WART_BLOCK,
        WHEAT_BLOCK,
        POTATO_BLOCK,
        CARROT_BLOCK,
        BEETROOT_BLOCK,
        PUMPKIN_STEM_AND_BLOCK,
        MELON_STEM_AND_BLOCK,
        FENCES,
        SIGNS,
        WALL_SIGNS,
        RAILS,
        ALL_ALIVE_CORALS,
        DEAD_CORAL_PLANTS,
        DEAD_CORALS,
        DEAD_WALL_CORALS,
        DEAD_CORAL_BLOCKS,
        ALL_DEAD_CORALS;

    public CustomTag(@Nonnull PControlData data) {
        WORLD_AIR = BlocksSet.create("WORLD_AIR", data, set -> {
            set.add(Material.AIR);
            if (data.hasVersion(13)) {
                set.add(Material.CAVE_AIR);
            }
        });
        WOODEN_DOORS = BlocksSet.create("WOODEN_DOORS", data, set -> {
            if (!data.hasVersion(13)) {
                set.add("WOODEN_DOOR");
                set.add(Material.ACACIA_DOOR);
                set.add(Material.BIRCH_DOOR);
                set.add(Material.DARK_OAK_DOOR);
                set.add(Material.JUNGLE_DOOR);
                set.add(Material.SPRUCE_FENCE);
            } else {
                set.add(Tag.WOODEN_DOORS.getValues());
            }
        });
        PRESSURE_PLATES = BlocksSet.create("PRESSURE_PLATES", data, set -> {
            if (!data.hasVersion(13)) {
                set.add("STONE_PLATE");
                set.add("GOLD_PLATE");
                set.add("IRON_PLATE");
                set.add("WOOD_PLATE");
            } else {
                set.add(Material.STONE_PRESSURE_PLATE);
                set.add(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
                set.add(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
                set.add(Tag.WOODEN_PRESSURE_PLATES.getValues());
            }
            if (data.hasVersion(16)) {
                set.add(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE);
            }
        });
        REDSTONE_PASSIVE_INPUTS = BlocksSet.create("REDSTONE_PASSIVE_INPUTS", data, set -> {
            set.add(Material.TRIPWIRE_HOOK);
            set.add(Material.TRIPWIRE);
            set.add(this.PRESSURE_PLATES);
            if (!data.hasVersion(13)) {
                set.add("WOOD_BUTTON");
                set.add(Material.STONE_BUTTON);
            } else {
                set.add(Tag.BUTTONS.getValues());
            }
        });
        REDSTONE_ORE_BLOCKS = BlocksSet.create("REDSTONE_ORE_BLOCKS", data, set -> {
            set.add(Material.REDSTONE_ORE);
            if (!data.hasVersion(13)) {
                set.add("GLOWING_REDSTONE_ORE");
            }
            if (data.hasVersion(17)) {
                set.add(Material.DEEPSLATE_REDSTONE_ORE);
            }
        });
        WATER = BlocksSet.create("WATER", data, set -> {
            set.add(Material.WATER);
            if (!data.hasVersion(13)) {
                set.add("STATIONARY_WATER");
            }
        });
        LAVA = BlocksSet.create("LAVA", data, set -> {
            set.add(Material.LAVA);
            if (!data.hasVersion(13)) {
                set.add("STATIONARY_LAVA");
            }
        });
        SAND = BlocksSet.create("SAND", data, set -> {
            if (!data.hasVersion(13)) {
                set.add(Material.SAND);
            } else {
                set.add(Tag.SAND.getValues());
            }
        });
        GRAVEL = BlocksSet.create("GRAVEL", data, set -> {
            set.add(Material.GRAVEL);
            if (data.hasVersion(20)) {
                set.add(Material.SUSPICIOUS_GRAVEL);
            }
        });
        ANVIL = BlocksSet.create("ANVIL", data, set -> {
            if (!data.hasVersion(13)) {
                set.add(Material.ANVIL);
            } else {
                set.add(Tag.ANVIL.getValues());
            }
        });
        CONCRETE_POWDERS = BlocksSet.create("CONCRETE_POWDERS", data, set -> {
            if (data.hasVersion(12)) {
                if (!data.hasVersion(13)) {
                    set.add("CONCRETE_POWDER");
                } else {
                    set.add(Material.BLACK_CONCRETE_POWDER);
                    set.add(Material.BLUE_CONCRETE_POWDER);
                    set.add(Material.BROWN_CONCRETE_POWDER);
                    set.add(Material.CYAN_CONCRETE_POWDER);
                    set.add(Material.GRAY_CONCRETE_POWDER);
                    set.add(Material.GREEN_CONCRETE_POWDER);
                    set.add(Material.LIGHT_BLUE_CONCRETE_POWDER);
                    set.add(Material.LIGHT_GRAY_CONCRETE_POWDER);
                    set.add(Material.LIME_CONCRETE_POWDER);
                    set.add(Material.MAGENTA_CONCRETE_POWDER);
                    set.add(Material.ORANGE_CONCRETE_POWDER);
                    set.add(Material.PINK_CONCRETE_POWDER);
                    set.add(Material.PURPLE_CONCRETE_POWDER);
                    set.add(Material.RED_CONCRETE_POWDER);
                    set.add(Material.WHITE_CONCRETE_POWDER);
                    set.add(Material.YELLOW_CONCRETE_POWDER);
                }
            }
        });
        GRAVITY_BLOCKS = BlocksSet.create("GRAVITY_BLOCKS", data, set -> {
            set.add(Material::hasGravity);
            if (!data.hasVersion(13)) {
                set.add(Material.DRAGON_EGG);
            }
            if (data.hasVersion(14)) {
                set.add(Material.SCAFFOLDING);
            }
        });
        NATURAL_GRAVITY_BLOCKS = BlocksSet.create("GRAVITY_BLOCKS", data, set -> {
            set.add(this.SAND);
            set.add(this.GRAVEL);
        });
        BONE_MEAL_HERBS = BlocksSet.create("BONE_MEAL_HERBS", data, set -> {
            if (!data.hasVersion(13)) {
                set.add("LONG_GRASS");
                set.add("YELLOW_FLOWER");
                set.add("RED_ROSE");
            } else {
                set.add(Material.GRASS);
                set.add(Material.DANDELION);
                set.add(Material.POPPY);
                set.add(Material.BLUE_ORCHID);
                set.add(Material.ALLIUM);
                set.add(Material.AZURE_BLUET);
                set.add(Material.RED_TULIP);
                set.add(Material.ORANGE_TULIP);
                set.add(Material.WHITE_TULIP);
                set.add(Material.PINK_TULIP);
                set.add(Material.OXEYE_DAISY);
                set.add(Material.SUNFLOWER);
                set.add(Material.LILAC);
                set.add(Material.ROSE_BUSH);
                set.add(Material.PEONY);
            }
            if (data.hasVersion(14)) {
                set.add(Material.CORNFLOWER);
                set.add(Material.LILY_OF_THE_VALLEY);
            }
        });
        LITTLE_MUSHROOMS = BlocksSet.create("LITTLE_MUSHROOMS", data, set -> {
            set.add(Material.RED_MUSHROOM);
            set.add(Material.BROWN_MUSHROOM);
        });
        BLOCKS_UNDER_WATER_ONLY = BlocksSet.create("UNDERWATER_BLOCKS_ONLY", data, set -> {
            set.add(this.WATER);
            if (data.hasVersion(13)) {
                set.add(Material.BUBBLE_COLUMN);
                set.add(Material.KELP_PLANT);
                set.add(Material.TALL_SEAGRASS);
                set.add(Material.SEAGRASS);
            }
        });
        GRASS_BLOCK = BlocksSet.create("GRASS_BLOCK", data, set -> {
            if (!data.hasVersion(13)) {
                set.add(Material.GRASS);
            } else {
                set.add(Material.GRASS_BLOCK);
            }
        });
        DIRT_PATH_BLOCK = BlocksSet.create("DIRT_PATH_BLOCK", data, set -> {
            if (data.hasVersion(9)) {
                if (!data.hasVersion(17)) {
                    set.add("GRASS_PATH");
                } else {
                    set.add(Material.DIRT_PATH);
                }
            }
        });
        FARMLAND_BLOCK = BlocksSet.create("FARMLAND_BLOCK", data, set -> {
            if (!data.hasVersion(13)) {
                set.add("SOIL");
            } else {
                set.add(Material.FARMLAND);
            }
        });
        MYCELIUM_BLOCK = BlocksSet.create("MYCELIUM_BLOCK", data, set -> {
            if (!data.hasVersion(13)) {
                set.add("MYCEL");
            } else {
                set.add(Material.MYCELIUM);
            }
        });
        SUGAR_CANE_BLOCK = BlocksSet.create("SUGAR_CANE_BLOCK", data, set -> {
            if (!data.hasVersion(13)) {
                set.add("SUGAR_CANE_BLOCK");
            } else {
                set.add(Material.SUGAR_CANE);
            }
        });
        NETHER_WART_BLOCK = BlocksSet.create("NETHER_WART_BLOCK", data, set -> {
            if (!data.hasVersion(13)) {
                set.add("NETHER_WARTS");
            } else {
                set.add(Material.NETHER_WART);
            }
        });
        WHEAT_BLOCK = BlocksSet.create("WHEAT_BLOCK", data, set -> {
            if (!data.hasVersion(13)) {
                set.add("CROPS");
            } else {
                set.add(Material.WHEAT);
            }
        });
        POTATO_BLOCK = BlocksSet.create("POTATO_BLOCK", data, set -> {
            if (!data.hasVersion(13)) {
                set.add("POTATO");
            } else {
                set.add(Material.POTATOES);
            }
        });
        CARROT_BLOCK = BlocksSet.create("CARROT_BLOCK", data, set -> {
            if (!data.hasVersion(13)) {
                set.add("CARROT");
            } else {
                set.add(Material.CARROTS);
            }
        });
        BEETROOT_BLOCK = BlocksSet.create("BEETROOT_BLOCK", data, set -> {
            if (!data.hasVersion(13)) {
                if (data.hasVersion(9)) {
                    set.add("BEETROOT_BLOCK");
                }
            } else {
                set.add(Material.BEETROOTS);
            }
        });
        PUMPKIN_STEM_AND_BLOCK = BlocksSet.create("PUMPKIN_STEM_AND_BLOCK", data, set -> {
            set.add(Material.PUMPKIN_STEM);
            set.add(Material.PUMPKIN);
        });
        MELON_STEM_AND_BLOCK = BlocksSet.create("MELON_STEM_AND_BLOCK", data, set -> {
            set.add(Material.MELON_STEM);
            if (!data.hasVersion(13)) {
                set.add("MELON_BLOCK");
            } else {
                set.add(Material.MELON);
            }
        });
        FENCES = BlocksSet.create("FENCES", data, set -> {
            if (!data.hasVersion(14)) {
                set.add(Material.ACACIA_FENCE);
                set.add(Material.BIRCH_FENCE);
                set.add(Material.DARK_OAK_FENCE);
                set.add(Material.JUNGLE_FENCE);
                set.add(Material.SPRUCE_FENCE);
                if (!data.hasVersion(13)) {
                    set.add("FENCE");
                    set.add("NETHER_FENCE");
                    // set.add("IRON_FENCE");
                } else {
                    set.add(Material.OAK_FENCE);
                    set.add(Material.NETHER_BRICK_FENCE);
                    // set.add(Material.IRON_BARS);
                }
            } else {
                // set.add(Material.IRON_BARS);
                set.add(Tag.FENCES.getValues());
            }
        });
        // TODO Possibly add SIGN_POST? (1.8-1.12.2)
        SIGNS = BlocksSet.create("SIGNS", data, set -> {
            if (!data.hasVersion(14)) {
                set.add("SIGN");
            } else {
                set.add(Tag.SIGNS.getValues());
            }
        });
        WALL_SIGNS = BlocksSet.create("WALL_SIGNS", data, set -> {
            if (!data.hasVersion(14)) {
                set.add("WALL_SIGN");
            } else {
                set.add(Tag.WALL_SIGNS.getValues());
            }
        });
        RAILS = BlocksSet.create("RAILS", data, set -> {
            set.add(Material.ACTIVATOR_RAIL);
            set.add(Material.DETECTOR_RAIL);
            set.add(Material.POWERED_RAIL);
            if (!data.hasVersion(13)) {
                set.add("RAILS");
            } else {
                set.add(Material.RAIL);
            }
        });
        ALL_ALIVE_CORALS = BlocksSet.create("ALL_ALIVE_CORALS", data, set -> {
            if (data.hasVersion(13)) {
                set.add(Tag.CORAL_BLOCKS.getValues());
                set.add(Tag.WALL_CORALS.getValues());
                set.add(Tag.CORALS.getValues()); // Contains Tag.CORAL_PLANTS
            }
        });
        DEAD_CORAL_PLANTS = BlocksSet.create("DEAD_CORAL_PLANTS", data, set -> {
            if (data.hasVersion(13)) {
                set.add(Material.DEAD_TUBE_CORAL);
                set.add(Material.DEAD_BRAIN_CORAL);
                set.add(Material.DEAD_BUBBLE_CORAL);
                set.add(Material.DEAD_FIRE_CORAL);
                set.add(Material.DEAD_HORN_CORAL);
            }
        });
        DEAD_CORALS = BlocksSet.create("DEAD_CORALS", data, set -> {
            if (data.hasVersion(13)) {
                set.add(Material.DEAD_TUBE_CORAL_FAN);
                set.add(Material.DEAD_BRAIN_CORAL_FAN);
                set.add(Material.DEAD_BUBBLE_CORAL_FAN);
                set.add(Material.DEAD_FIRE_CORAL_FAN);
                set.add(Material.DEAD_HORN_CORAL_FAN);
                set.add(this.DEAD_CORAL_PLANTS);
            }
        });
        DEAD_WALL_CORALS = BlocksSet.create("DEAD_WALL_CORALS", data, set -> {
            if (data.hasVersion(13)) {
                set.add(Material.DEAD_TUBE_CORAL_WALL_FAN);
                set.add(Material.DEAD_BRAIN_CORAL_WALL_FAN);
                set.add(Material.DEAD_BUBBLE_CORAL_WALL_FAN);
                set.add(Material.DEAD_FIRE_CORAL_WALL_FAN);
                set.add(Material.DEAD_HORN_CORAL_WALL_FAN);
            }
        });
        DEAD_CORAL_BLOCKS = BlocksSet.create("DEAD_CORAL_BLOCKS", data, set -> {
            if (data.hasVersion(13)) {
                set.add(Material.DEAD_TUBE_CORAL_BLOCK);
                set.add(Material.DEAD_BRAIN_CORAL_BLOCK);
                set.add(Material.DEAD_BUBBLE_CORAL_BLOCK);
                set.add(Material.DEAD_FIRE_CORAL_BLOCK);
                set.add(Material.DEAD_HORN_CORAL_BLOCK);
            }
        });
        ALL_DEAD_CORALS = BlocksSet.create("ALL_DEAD_CORALS", data, set -> {
            if (data.hasVersion(13)) {
                set.add(this.DEAD_CORAL_BLOCKS);
                set.add(this.DEAD_WALL_CORALS);
                set.add(this.DEAD_CORALS); // Contains this.DEAD_CORAL_PLANTS
            }
        });
    }
}
