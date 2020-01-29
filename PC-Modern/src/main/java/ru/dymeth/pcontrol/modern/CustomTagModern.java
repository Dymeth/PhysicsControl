package ru.dymeth.pcontrol.modern;

import org.bukkit.Tag;
import ru.dymeth.pcontrol.CustomMaterialSet;

import static org.bukkit.Material.*;
import static org.bukkit.Tag.*;

public final class CustomTagModern {
    public static final CustomMaterialSet WORLD_AIR = new CustomMaterialSet(
            AIR,
            CAVE_AIR);

    public static final CustomMaterialSet FENCES = new CustomMaterialSet(
            ACACIA_FENCE,
            BIRCH_FENCE,
            DARK_OAK_FENCE,
            JUNGLE_FENCE,
            NETHER_BRICK_FENCE,
            OAK_FENCE,
            SPRUCE_FENCE);

    public static final CustomMaterialSet SIGNS = new CustomMaterialSet()
            .addBlocks(
                    "SIGN",
                    "ACACIA_SIGN",
                    "BIRCH_SIGN",
                    "DARK_OAK_SIGN",
                    "JUNGLE_SIGN",
                    "OAK_SIGN",
                    "SPRUCE_SIGN"
            );

    public static final CustomMaterialSet WALL_SIGNS = new CustomMaterialSet()
            .addBlocks(
                    "WALL_SIGN",
                    "ACACIA_WALL_SIGN",
                    "BIRCH_WALL_SIGN",
                    "DARK_OAK_WALL_SIGN",
                    "JUNGLE_WALL_SIGN",
                    "OAK_WALL_SIGN",
                    "SPRUCE_WALL_SIGN"
            );

    public static final CustomMaterialSet PRESSURE_PLATES = new CustomMaterialSet(
            STONE_PRESSURE_PLATE,
            LIGHT_WEIGHTED_PRESSURE_PLATE,
            HEAVY_WEIGHTED_PRESSURE_PLATE)
            .add(WOODEN_PRESSURE_PLATES.getValues());

    public static final CustomMaterialSet REDSTONE_ACTIVE_INPUTS = new CustomMaterialSet(
            LEVER,
            TRAPPED_CHEST,
            DAYLIGHT_DETECTOR,
            OBSERVER)
            .add(BUTTONS.getValues());

    public static final CustomMaterialSet REDSTONE_PASSIVE_INPUTS = new CustomMaterialSet(
            TRIPWIRE_HOOK,
            TRIPWIRE)
            .add(PRESSURE_PLATES.getValues());

    public static final CustomMaterialSet REDSTONE_TRANSMITTERS_AND_OUTPUTS = new CustomMaterialSet(
            DISPENSER,
            STICKY_PISTON,
            PISTON,
            PISTON_HEAD,
            MOVING_PISTON,
            TNT,
            REDSTONE_TORCH,
            REDSTONE_WALL_TORCH,
            REDSTONE_LAMP,
            REDSTONE_BLOCK,
            HOPPER,
            DROPPER,
            REPEATER,
            COMPARATOR,
            REDSTONE_WIRE)
            .add(TRAPDOORS.getValues())
            .add(FENCES.getValues())
            .add(DOORS.getValues());

    public static final CustomMaterialSet CONCRETES = new CustomMaterialSet(
            BLACK_CONCRETE,
            BLUE_CONCRETE,
            BROWN_CONCRETE,
            CYAN_CONCRETE,
            GRAY_CONCRETE,
            GREEN_CONCRETE,
            LIGHT_BLUE_CONCRETE,
            LIGHT_GRAY_CONCRETE,
            LIME_CONCRETE,
            MAGENTA_CONCRETE,
            ORANGE_CONCRETE,
            PINK_CONCRETE,
            PURPLE_CONCRETE,
            RED_CONCRETE,
            WHITE_CONCRETE,
            YELLOW_CONCRETE
    );

    public static final CustomMaterialSet CONCRETE_POWDERS = new CustomMaterialSet(
            BLACK_CONCRETE_POWDER,
            BLUE_CONCRETE_POWDER,
            BROWN_CONCRETE_POWDER,
            CYAN_CONCRETE_POWDER,
            GRAY_CONCRETE_POWDER,
            GREEN_CONCRETE_POWDER,
            LIGHT_BLUE_CONCRETE_POWDER,
            LIGHT_GRAY_CONCRETE_POWDER,
            LIME_CONCRETE_POWDER,
            MAGENTA_CONCRETE_POWDER,
            ORANGE_CONCRETE_POWDER,
            PINK_CONCRETE_POWDER,
            PURPLE_CONCRETE_POWDER,
            RED_CONCRETE_POWDER,
            WHITE_CONCRETE_POWDER,
            YELLOW_CONCRETE_POWDER
    );

    public static final CustomMaterialSet GRAVITY_BLOCKS = new CustomMaterialSet(
            GRAVEL)
            .add(Tag.SAND.getValues())
            .add(Tag.ANVIL.getValues())
            .add(CONCRETES.getValues());

    public static final CustomMaterialSet BONE_MEAL_HERBS = new CustomMaterialSet(
            GRASS,
            DANDELION,
            POPPY,
            BLUE_ORCHID,
            ALLIUM,
            AZURE_BLUET,
            RED_TULIP,
            ORANGE_TULIP,
            WHITE_TULIP,
            PINK_TULIP,
            OXEYE_DAISY,
            SUNFLOWER,
            LILAC,
            ROSE_BUSH,
            PEONY)
            .addBlocks(
                    "CORNFLOWER",
                    "LILY_OF_THE_VALLEY"
            );

    public static final CustomMaterialSet LITTLE_MUSHROOMS = new CustomMaterialSet(
            RED_MUSHROOM,
            BROWN_MUSHROOM);

    public static final CustomMaterialSet ALL_ALIVE_CORALS = new CustomMaterialSet()
            .add(CORAL_BLOCKS.getValues())
            .add(WALL_CORALS.getValues())
            .add(CORAL_PLANTS.getValues())
            .add(CORALS.getValues());

    public static final CustomMaterialSet DEAD_CORAL_PLANTS = new CustomMaterialSet(
            DEAD_TUBE_CORAL,
            DEAD_BRAIN_CORAL,
            DEAD_BUBBLE_CORAL,
            DEAD_FIRE_CORAL,
            DEAD_HORN_CORAL);

    public static final CustomMaterialSet DEAD_CORALS = new CustomMaterialSet(
            DEAD_TUBE_CORAL_FAN,
            DEAD_BRAIN_CORAL_FAN,
            DEAD_BUBBLE_CORAL_FAN,
            DEAD_FIRE_CORAL_FAN,
            DEAD_HORN_CORAL_FAN)
            .add(DEAD_CORAL_PLANTS.getValues());

    public static final CustomMaterialSet DEAD_WALL_CORALS = new CustomMaterialSet(
            DEAD_TUBE_CORAL_WALL_FAN,
            DEAD_BRAIN_CORAL_WALL_FAN,
            DEAD_BUBBLE_CORAL_WALL_FAN,
            DEAD_FIRE_CORAL_WALL_FAN,
            DEAD_HORN_CORAL_WALL_FAN);

    public static final CustomMaterialSet DEAD_CORAL_BLOCKS = new CustomMaterialSet(
            DEAD_TUBE_CORAL_BLOCK,
            DEAD_BRAIN_CORAL_BLOCK,
            DEAD_BUBBLE_CORAL_BLOCK,
            DEAD_FIRE_CORAL_BLOCK,
            DEAD_HORN_CORAL_BLOCK);

    public static final CustomMaterialSet ALL_DEAD_CORALS = new CustomMaterialSet()
            .add(DEAD_CORAL_BLOCKS.getValues())
            .add(DEAD_WALL_CORALS.getValues())
            .add(DEAD_CORAL_PLANTS.getValues())
            .add(DEAD_CORALS.getValues());

    public static final CustomMaterialSet UNDERWATER_BLOCKS_ONLY = new CustomMaterialSet(
            WATER,
            BUBBLE_COLUMN,
            KELP_PLANT,
            TALL_SEAGRASS,
            SEAGRASS);
}
