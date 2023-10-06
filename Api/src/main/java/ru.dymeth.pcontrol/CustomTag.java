package ru.dymeth.pcontrol;

import org.bukkit.Material;
import org.bukkit.Tag;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.set.BlocksSet;

import javax.annotation.Nonnull;

public final class CustomTag {

    public final BlocksSet
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
        UNDERWATER_BLOCKS_ONLY,
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
        REDSTONE_ACTIVE_INPUTS,
        RAILS,
        ALL_ALIVE_CORALS,
        DEAD_CORAL_PLANTS,
        DEAD_CORALS,
        DEAD_WALL_CORALS,
        DEAD_CORAL_BLOCKS,
        ALL_DEAD_CORALS;

    public CustomTag(@Nonnull PControlData data) {
        WORLD_AIR = new BlocksSet(
            Material.AIR);
        if (data.hasVersion(13)) {
            WORLD_AIR.add(Material.CAVE_AIR);
        }

        WOODEN_DOORS = new BlocksSet();
        if (data.hasVersion(13)) {
            WOODEN_DOORS.add(Tag.WOODEN_DOORS.getValues());
        }

        PRESSURE_PLATES = new BlocksSet();
        if (!data.hasVersion(13)) {
            PRESSURE_PLATES.add("STONE_PLATE");
            PRESSURE_PLATES.add("GOLD_PLATE");
            PRESSURE_PLATES.add("IRON_PLATE");
            PRESSURE_PLATES.add("WOOD_PLATE");
        } else {
            PRESSURE_PLATES.add(Material.STONE_PRESSURE_PLATE);
            PRESSURE_PLATES.add(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
            PRESSURE_PLATES.add(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
            PRESSURE_PLATES.add(Tag.WOODEN_PRESSURE_PLATES.getValues());
        }
        if (data.hasVersion(16)) {
            PRESSURE_PLATES.add(Material.CRIMSON_PRESSURE_PLATE);
            PRESSURE_PLATES.add(Material.WARPED_PRESSURE_PLATE);
            PRESSURE_PLATES.add(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE);
        }
        if (data.hasVersion(19)) {
            PRESSURE_PLATES.add(Material.MANGROVE_PRESSURE_PLATE);
        }
        if (data.hasVersion(20)) {
            PRESSURE_PLATES.add(Material.BAMBOO_PRESSURE_PLATE);
            PRESSURE_PLATES.add(Material.CHERRY_PRESSURE_PLATE);
        }

        REDSTONE_PASSIVE_INPUTS = new BlocksSet(
            Material.TRIPWIRE_HOOK,
            Material.TRIPWIRE);
        REDSTONE_PASSIVE_INPUTS.add(this.PRESSURE_PLATES.getValues());
        if (data.hasVersion(13)) {
            REDSTONE_PASSIVE_INPUTS.add(Tag.BUTTONS.getValues());
        }

        REDSTONE_ORE_BLOCKS = new BlocksSet(
            Material.REDSTONE_ORE);
        if (data.hasVersion(17)) {
            REDSTONE_ORE_BLOCKS.add(Material.DEEPSLATE_REDSTONE_ORE);
        }

        WATER = new BlocksSet(
            Material.WATER);

        LAVA = new BlocksSet(
            Material.LAVA);

        SAND = new BlocksSet();
        if (data.hasVersion(13)) {
            SAND.add(Tag.SAND.getValues());
        }

        GRAVEL = new BlocksSet(
            Material.GRAVEL);
        if (data.hasVersion(20)) {
            GRAVEL.add(Material.SUSPICIOUS_GRAVEL);
        }

        ANVIL = new BlocksSet();
        if (data.hasVersion(13)) {
            ANVIL.add(Tag.ANVIL.getValues());
        }

        CONCRETE_POWDERS = new BlocksSet();
        if (data.hasVersion(12)) {
            if (!data.hasVersion(13)) {
                CONCRETE_POWDERS.add("CONCRETE_POWDER");
            } else {
                CONCRETE_POWDERS.add(Material.BLACK_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.BLUE_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.BROWN_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.CYAN_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.GRAY_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.GREEN_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.LIGHT_BLUE_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.LIGHT_GRAY_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.LIME_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.MAGENTA_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.ORANGE_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.PINK_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.PURPLE_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.RED_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.WHITE_CONCRETE_POWDER);
                CONCRETE_POWDERS.add(Material.YELLOW_CONCRETE_POWDER);
            }
        }

        GRAVITY_BLOCKS = new BlocksSet();
        GRAVITY_BLOCKS.add(Material::hasGravity);
        if (data.hasVersion(14)) {
            GRAVITY_BLOCKS.add(Material.SCAFFOLDING);
        }

        NATURAL_GRAVITY_BLOCKS = new BlocksSet();
        NATURAL_GRAVITY_BLOCKS.add(this.SAND.getValues());
        NATURAL_GRAVITY_BLOCKS.add(this.GRAVEL.getValues());

        BONE_MEAL_HERBS = new BlocksSet();
        if (!data.hasVersion(13)) {
            BONE_MEAL_HERBS.add("LONG_GRASS");
            BONE_MEAL_HERBS.add("YELLOW_FLOWER");
            BONE_MEAL_HERBS.add("RED_ROSE");
        } else {
            BONE_MEAL_HERBS.add(Material.GRASS);
            BONE_MEAL_HERBS.add(Material.DANDELION);
            BONE_MEAL_HERBS.add(Material.POPPY);
            BONE_MEAL_HERBS.add(Material.BLUE_ORCHID);
            BONE_MEAL_HERBS.add(Material.ALLIUM);
            BONE_MEAL_HERBS.add(Material.AZURE_BLUET);
            BONE_MEAL_HERBS.add(Material.RED_TULIP);
            BONE_MEAL_HERBS.add(Material.ORANGE_TULIP);
            BONE_MEAL_HERBS.add(Material.WHITE_TULIP);
            BONE_MEAL_HERBS.add(Material.PINK_TULIP);
            BONE_MEAL_HERBS.add(Material.OXEYE_DAISY);
            BONE_MEAL_HERBS.add(Material.SUNFLOWER);
            BONE_MEAL_HERBS.add(Material.LILAC);
            BONE_MEAL_HERBS.add(Material.ROSE_BUSH);
            BONE_MEAL_HERBS.add(Material.PEONY);
        }
        if (data.hasVersion(14)) {
            BONE_MEAL_HERBS.add(Material.CORNFLOWER);
            BONE_MEAL_HERBS.add(Material.LILY_OF_THE_VALLEY);
        }

        LITTLE_MUSHROOMS = new BlocksSet(
            Material.RED_MUSHROOM,
            Material.BROWN_MUSHROOM);

        UNDERWATER_BLOCKS_ONLY = new BlocksSet(
            Material.WATER);
        if (!data.hasVersion(13)) {
            UNDERWATER_BLOCKS_ONLY.add("STATIONARY_WATER");
        } else {
            UNDERWATER_BLOCKS_ONLY.add(Material.BUBBLE_COLUMN);
            UNDERWATER_BLOCKS_ONLY.add(Material.KELP_PLANT);
            UNDERWATER_BLOCKS_ONLY.add(Material.TALL_SEAGRASS);
            UNDERWATER_BLOCKS_ONLY.add(Material.SEAGRASS);
        }

        GRASS_BLOCK = new BlocksSet();
        if (!data.hasVersion(13)) {
            GRASS_BLOCK.add("GRASS");
        } else {
            GRASS_BLOCK.add(Material.GRASS_BLOCK);
        }

        DIRT_PATH_BLOCK = new BlocksSet();
        if (data.hasVersion(9)) {
            if (!data.hasVersion(17)) {
                DIRT_PATH_BLOCK.add("GRASS_PATH");
            } else {
                DIRT_PATH_BLOCK.add(Material.DIRT_PATH);
            }
        }

        FARMLAND_BLOCK = new BlocksSet();
        if (!data.hasVersion(13)) {
            FARMLAND_BLOCK.add("SOIL");
        } else {
            FARMLAND_BLOCK.add(Material.FARMLAND);
        }

        MYCELIUM_BLOCK = new BlocksSet();
        if (!data.hasVersion(13)) {
            MYCELIUM_BLOCK.add("MYCEL");
        } else {
            MYCELIUM_BLOCK.add(Material.MYCELIUM);
        }

        SUGAR_CANE_BLOCK = new BlocksSet();
        if (!data.hasVersion(13)) {
            SUGAR_CANE_BLOCK.add("SUGAR_CANE_BLOCK");
        } else {
            SUGAR_CANE_BLOCK.add(Material.SUGAR_CANE);
        }

        NETHER_WART_BLOCK = new BlocksSet();
        if (!data.hasVersion(13)) {
            NETHER_WART_BLOCK.add("NETHER_WARTS");
        } else {
            NETHER_WART_BLOCK.add(Material.NETHER_WART);
        }

        WHEAT_BLOCK = new BlocksSet();
        if (!data.hasVersion(13)) {
            WHEAT_BLOCK.add("CROPS");
        } else {
            WHEAT_BLOCK.add(Material.WHEAT);
        }

        POTATO_BLOCK = new BlocksSet();
        if (!data.hasVersion(13)) {
            POTATO_BLOCK.add("POTATO");
        } else {
            POTATO_BLOCK.add(Material.POTATOES);
        }

        CARROT_BLOCK = new BlocksSet();
        if (!data.hasVersion(13)) {
            CARROT_BLOCK.add("CARROT");
        } else {
            CARROT_BLOCK.add(Material.CARROTS);
        }

        BEETROOT_BLOCK = new BlocksSet();
        if (!data.hasVersion(13)) {
            if (data.hasVersion(9)) {
                BEETROOT_BLOCK.add("BEETROOT_BLOCK");
            }
        } else {
            BEETROOT_BLOCK.add(Material.BEETROOTS);
        }

        PUMPKIN_STEM_AND_BLOCK = new BlocksSet(
            Material.PUMPKIN_STEM,
            Material.PUMPKIN);

        MELON_STEM_AND_BLOCK = new BlocksSet(
            Material.MELON_STEM);
        if (!data.hasVersion(13)) {
            MELON_STEM_AND_BLOCK.add("MELON_BLOCK");
        } else {
            MELON_STEM_AND_BLOCK.add(Material.MELON);
        }

        FENCES = new BlocksSet(
            Material.ACACIA_FENCE,
            Material.BIRCH_FENCE,
            Material.DARK_OAK_FENCE,
            Material.JUNGLE_FENCE,
            Material.NETHER_BRICK_FENCE,
            Material.OAK_FENCE,
            Material.SPRUCE_FENCE);
        if (data.hasVersion(16)) {
            FENCES.add(Material.CRIMSON_FENCE);
            FENCES.add(Material.WARPED_FENCE);
        }
        if (data.hasVersion(19)) {
            FENCES.add(Material.MANGROVE_FENCE);
        }
        if (data.hasVersion(20)) {
            FENCES.add(Material.BAMBOO_FENCE);
            FENCES.add(Material.CHERRY_FENCE);
        }

        // TODO Possibly add SIGN_POST? (1.8-1.12.2)
        SIGNS = new BlocksSet();
        if (!data.hasVersion(14)) {
            SIGNS.add("SIGN");
        } else {
            SIGNS.add(Material.ACACIA_SIGN);
            SIGNS.add(Material.BIRCH_SIGN);
            SIGNS.add(Material.DARK_OAK_SIGN);
            SIGNS.add(Material.JUNGLE_SIGN);
            SIGNS.add(Material.OAK_SIGN);
            SIGNS.add(Material.SPRUCE_SIGN);
        }
        if (data.hasVersion(16)) {
            SIGNS.add(Material.CRIMSON_SIGN);
            SIGNS.add(Material.WARPED_SIGN);
        }
        if (data.hasVersion(19)) {
            SIGNS.add(Material.MANGROVE_SIGN);
        }
        if (data.hasVersion(20)) {
            SIGNS.add(Material.BAMBOO_SIGN);
            SIGNS.add(Material.CHERRY_SIGN);
        }

        WALL_SIGNS = new BlocksSet();
        if (!data.hasVersion(14)) {
            WALL_SIGNS.add("WALL_SIGN");
        } else {
            WALL_SIGNS.add(Material.ACACIA_WALL_SIGN);
            WALL_SIGNS.add(Material.BIRCH_WALL_SIGN);
            WALL_SIGNS.add(Material.DARK_OAK_WALL_SIGN);
            WALL_SIGNS.add(Material.JUNGLE_WALL_SIGN);
            WALL_SIGNS.add(Material.OAK_WALL_SIGN);
            WALL_SIGNS.add(Material.SPRUCE_WALL_SIGN);
        }
        if (data.hasVersion(16)) {
            WALL_SIGNS.add(Material.CRIMSON_WALL_SIGN);
            WALL_SIGNS.add(Material.WARPED_WALL_SIGN);
        }
        if (data.hasVersion(19)) {
            WALL_SIGNS.add(Material.MANGROVE_WALL_SIGN);
        }
        if (data.hasVersion(20)) {
            WALL_SIGNS.add(Material.BAMBOO_WALL_SIGN);
            WALL_SIGNS.add(Material.CHERRY_WALL_SIGN);
        }

        REDSTONE_ACTIVE_INPUTS = new BlocksSet(
            Material.LEVER,
            Material.TRAPPED_CHEST,
            Material.DAYLIGHT_DETECTOR,
            Material.OBSERVER);
        if (data.hasVersion(13)) {
            REDSTONE_ACTIVE_INPUTS.add(Tag.BUTTONS.getValues());
        }

        RAILS = new BlocksSet(
            Material.ACTIVATOR_RAIL,
            Material.DETECTOR_RAIL,
            Material.POWERED_RAIL);
        if (data.hasVersion(13)) {
            RAILS.add("RAILS");
        } else {
            RAILS.add(Material.RAIL);
        }

        ALL_ALIVE_CORALS = new BlocksSet();
        if (data.hasVersion(13)) {
            ALL_ALIVE_CORALS.add(Tag.CORAL_BLOCKS.getValues());
            ALL_ALIVE_CORALS.add(Tag.WALL_CORALS.getValues());
            ALL_ALIVE_CORALS.add(Tag.CORAL_PLANTS.getValues());
            ALL_ALIVE_CORALS.add(Tag.CORALS.getValues());
        }

        DEAD_CORAL_PLANTS = new BlocksSet();
        if (data.hasVersion(13)) {
            DEAD_CORAL_PLANTS.add(Material.DEAD_TUBE_CORAL);
            DEAD_CORAL_PLANTS.add(Material.DEAD_BRAIN_CORAL);
            DEAD_CORAL_PLANTS.add(Material.DEAD_BUBBLE_CORAL);
            DEAD_CORAL_PLANTS.add(Material.DEAD_FIRE_CORAL);
            DEAD_CORAL_PLANTS.add(Material.DEAD_HORN_CORAL);
        }

        DEAD_CORALS = new BlocksSet();
        if (data.hasVersion(13)) {
            DEAD_CORALS.add(Material.DEAD_TUBE_CORAL_FAN);
            DEAD_CORALS.add(Material.DEAD_BRAIN_CORAL_FAN);
            DEAD_CORALS.add(Material.DEAD_BUBBLE_CORAL_FAN);
            DEAD_CORALS.add(Material.DEAD_FIRE_CORAL_FAN);
            DEAD_CORALS.add(Material.DEAD_HORN_CORAL_FAN);
            DEAD_CORALS.add(this.DEAD_CORAL_PLANTS.getValues());
        }

        DEAD_WALL_CORALS = new BlocksSet();
        if (data.hasVersion(13)) {
            DEAD_WALL_CORALS.add(Material.DEAD_TUBE_CORAL_WALL_FAN);
            DEAD_WALL_CORALS.add(Material.DEAD_BRAIN_CORAL_WALL_FAN);
            DEAD_WALL_CORALS.add(Material.DEAD_BUBBLE_CORAL_WALL_FAN);
            DEAD_WALL_CORALS.add(Material.DEAD_FIRE_CORAL_WALL_FAN);
            DEAD_WALL_CORALS.add(Material.DEAD_HORN_CORAL_WALL_FAN);
        }

        DEAD_CORAL_BLOCKS = new BlocksSet();
        if (data.hasVersion(13)) {
            DEAD_CORAL_BLOCKS.add(Material.DEAD_TUBE_CORAL_BLOCK);
            DEAD_CORAL_BLOCKS.add(Material.DEAD_BRAIN_CORAL_BLOCK);
            DEAD_CORAL_BLOCKS.add(Material.DEAD_BUBBLE_CORAL_BLOCK);
            DEAD_CORAL_BLOCKS.add(Material.DEAD_FIRE_CORAL_BLOCK);
            DEAD_CORAL_BLOCKS.add(Material.DEAD_HORN_CORAL_BLOCK);
        }

        ALL_DEAD_CORALS = new BlocksSet();
        if (data.hasVersion(13)) {
            ALL_DEAD_CORALS.add(this.DEAD_CORAL_BLOCKS.getValues());
            ALL_DEAD_CORALS.add(this.DEAD_WALL_CORALS.getValues());
            ALL_DEAD_CORALS.add(this.DEAD_CORAL_PLANTS.getValues());
            ALL_DEAD_CORALS.add(this.DEAD_CORALS.getValues());
        }
    }
}
