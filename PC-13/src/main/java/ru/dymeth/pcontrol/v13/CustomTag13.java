package ru.dymeth.pcontrol.v13;

import org.bukkit.Tag;

import static org.bukkit.Material.*;
import static org.bukkit.Tag.*;

public final class CustomTag13 {
    public static final MaterialSetModern WORLD_AIR = new MaterialSetModern("world_air",
            AIR,
            CAVE_AIR);

    public static final MaterialSetModern FENCES = new MaterialSetModern("fences",
            ACACIA_FENCE,
            BIRCH_FENCE,
            DARK_OAK_FENCE,
            JUNGLE_FENCE,
            NETHER_BRICK_FENCE,
            OAK_FENCE,
            SPRUCE_FENCE);

    public static final MaterialSetModern PRESSURE_PLATES = new MaterialSetModern("pressure_plates",
            STONE_PRESSURE_PLATE,
            LIGHT_WEIGHTED_PRESSURE_PLATE,
            HEAVY_WEIGHTED_PRESSURE_PLATE)
            .add(WOODEN_PRESSURE_PLATES.getValues());

    public static final MaterialSetModern REDSTONE_ACTIVE_INPUTS = new MaterialSetModern("redstone_active_inputs",
            LEVER,
            TRAPPED_CHEST,
            DAYLIGHT_DETECTOR,
            OBSERVER)
            .add(BUTTONS.getValues());

    public static final MaterialSetModern REDSTONE_PASSIVE_INPUTS = new MaterialSetModern("redstone_passive_inputs",
            TRIPWIRE_HOOK,
            TRIPWIRE)
            .add(PRESSURE_PLATES.getValues());

    public static final MaterialSetModern REDSTONE_TRANSMITTERS_AND_OUTPUTS = new MaterialSetModern("redstone_transmitters_and_outputs",
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

    public static final MaterialSetModern CONCRETES = new MaterialSetModern("concretes",
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

    public static final MaterialSetModern CONCRETE_POWDERS = new MaterialSetModern("concrete_powders",
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

    public static final MaterialSetModern GRAVITY_BLOCKS = new MaterialSetModern("gravity_blocks",
            GRAVEL)
            .add(Tag.SAND.getValues())
            .add(Tag.ANVIL.getValues())
            .add(CONCRETES.getValues());

    public static final MaterialSetModern BONE_MEAL_HERBS = new MaterialSetModern("flowers",
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
            .add(
                    "CORNFLOWER",
                    "LILY_OF_THE_VALLEY"
            );

    public static final MaterialSetModern LITTLE_MUSHROOMS = new MaterialSetModern("little_mushrooms",
            RED_MUSHROOM,
            BROWN_MUSHROOM);

    public static final MaterialSetModern ALL_ALIVE_CORALS = new MaterialSetModern("all_alive_corals")
            .add(CORAL_BLOCKS.getValues())
            .add(WALL_CORALS.getValues())
            .add(CORAL_PLANTS.getValues())
            .add(CORALS.getValues());

    public static final MaterialSetModern DEAD_CORAL_PLANTS = new MaterialSetModern("dead_coral_plants",
            DEAD_TUBE_CORAL,
            DEAD_BRAIN_CORAL,
            DEAD_BUBBLE_CORAL,
            DEAD_FIRE_CORAL,
            DEAD_HORN_CORAL);

    public static final MaterialSetModern DEAD_CORALS = new MaterialSetModern("dead_corals",
            DEAD_TUBE_CORAL_FAN,
            DEAD_BRAIN_CORAL_FAN,
            DEAD_BUBBLE_CORAL_FAN,
            DEAD_FIRE_CORAL_FAN,
            DEAD_HORN_CORAL_FAN)
            .add(DEAD_CORAL_PLANTS.getValues());

    public static final MaterialSetModern DEAD_WALL_CORALS = new MaterialSetModern("dead_wall_corals",
            DEAD_TUBE_CORAL_WALL_FAN,
            DEAD_BRAIN_CORAL_WALL_FAN,
            DEAD_BUBBLE_CORAL_WALL_FAN,
            DEAD_FIRE_CORAL_WALL_FAN,
            DEAD_HORN_CORAL_WALL_FAN);

    public static final MaterialSetModern DEAD_CORAL_BLOCKS = new MaterialSetModern("dead_coral_blocks",
            DEAD_TUBE_CORAL_BLOCK,
            DEAD_BRAIN_CORAL_BLOCK,
            DEAD_BUBBLE_CORAL_BLOCK,
            DEAD_FIRE_CORAL_BLOCK,
            DEAD_HORN_CORAL_BLOCK);

    public static final MaterialSetModern ALL_DEAD_CORALS = new MaterialSetModern("all_dead_corals")
            .add(DEAD_CORAL_BLOCKS.getValues())
            .add(DEAD_WALL_CORALS.getValues())
            .add(DEAD_CORAL_PLANTS.getValues())
            .add(DEAD_CORALS.getValues());

    public static final MaterialSetModern UNDERWATER_BLOCKS_ONLY = new MaterialSetModern("underwater_blocks_only",
            WATER,
            BUBBLE_COLUMN,
            KELP_PLANT,
            SEAGRASS);
}
