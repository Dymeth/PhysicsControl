package ru.dymeth.pcontrol.modern;

import org.bukkit.Material;
import ru.dymeth.pcontrol.api.set.KeysSet;
import ru.dymeth.pcontrol.api.set.MaterialKeysSet;

import static org.bukkit.Material.*;
import static org.bukkit.Tag.*;

public final class CustomTagModern {
    public static final KeysSet<Material> WORLD_AIR = new MaterialKeysSet(
        AIR,
        CAVE_AIR);

    public static final KeysSet<Material> FENCES = new MaterialKeysSet(
        ACACIA_FENCE,
        BIRCH_FENCE,
        DARK_OAK_FENCE,
        JUNGLE_FENCE,
        NETHER_BRICK_FENCE,
        OAK_FENCE,
        SPRUCE_FENCE)
        .addBlocks(
            "CRIMSON_FENCE",
            "WARPED_FENCE"
        );

    public static final KeysSet<Material> SIGNS = new MaterialKeysSet()
        .addBlocks(
            "SIGN",
            "ACACIA_SIGN",
            "BIRCH_SIGN",
            "DARK_OAK_SIGN",
            "JUNGLE_SIGN",
            "OAK_SIGN",
            "SPRUCE_SIGN",
            "CRIMSON_SIGN",
            "WARPED_SIGN"
        );

    public static final KeysSet<Material> WALL_SIGNS = new MaterialKeysSet()
        .addBlocks(
            "WALL_SIGN",
            "ACACIA_WALL_SIGN",
            "BIRCH_WALL_SIGN",
            "DARK_OAK_WALL_SIGN",
            "JUNGLE_WALL_SIGN",
            "OAK_WALL_SIGN",
            "SPRUCE_WALL_SIGN",
            "CRIMSON_WALL_SIGN",
            "WARPED_WALL_SIGN"
        );

    public static final KeysSet<Material> PRESSURE_PLATES = new MaterialKeysSet(
        STONE_PRESSURE_PLATE,
        LIGHT_WEIGHTED_PRESSURE_PLATE,
        HEAVY_WEIGHTED_PRESSURE_PLATE)
        .addBlocks("POLISHED_BLACKSTONE_PRESSURE_PLATE")
        .add(WOODEN_PRESSURE_PLATES.getValues());

    public static final KeysSet<Material> REDSTONE_ACTIVE_INPUTS = new MaterialKeysSet(
        LEVER,
        TRAPPED_CHEST,
        DAYLIGHT_DETECTOR,
        OBSERVER)
        .add(BUTTONS.getValues());

    public static final KeysSet<Material> REDSTONE_PASSIVE_INPUTS = new MaterialKeysSet(
        TRIPWIRE_HOOK,
        TRIPWIRE)
        .add(PRESSURE_PLATES.getValues())
        .add(BUTTONS.getValues());

    public static final KeysSet<Material> REDSTONE_TRANSMITTERS_AND_OUTPUTS = new MaterialKeysSet(
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

    public static final KeysSet<Material> REDSTONE_ORE_BLOCKS = new MaterialKeysSet(
        REDSTONE_ORE)
        .addBlocks("DEEPSLATE_REDSTONE_ORE");

    public static final KeysSet<Material> WATER = new MaterialKeysSet(
        Material.WATER);

    public static final KeysSet<Material> LAVA = new MaterialKeysSet(
        Material.LAVA);

    public static final KeysSet<Material> CONCRETE_POWDERS = new MaterialKeysSet(
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

    public static final KeysSet<Material> GRAVITY_BLOCKS = new MaterialKeysSet()
        .addBlocks("SCAFFOLDING")
        .add(Material::hasGravity);

    public static final KeysSet<Material> BONE_MEAL_HERBS = new MaterialKeysSet(
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

    public static final KeysSet<Material> LITTLE_MUSHROOMS = new MaterialKeysSet(
        RED_MUSHROOM,
        BROWN_MUSHROOM);

    public static final KeysSet<Material> ALL_ALIVE_CORALS = new MaterialKeysSet()
        .add(CORAL_BLOCKS.getValues())
        .add(WALL_CORALS.getValues())
        .add(CORAL_PLANTS.getValues())
        .add(CORALS.getValues());

    public static final KeysSet<Material> DEAD_CORAL_PLANTS = new MaterialKeysSet(
        DEAD_TUBE_CORAL,
        DEAD_BRAIN_CORAL,
        DEAD_BUBBLE_CORAL,
        DEAD_FIRE_CORAL,
        DEAD_HORN_CORAL);

    public static final KeysSet<Material> DEAD_CORALS = new MaterialKeysSet(
        DEAD_TUBE_CORAL_FAN,
        DEAD_BRAIN_CORAL_FAN,
        DEAD_BUBBLE_CORAL_FAN,
        DEAD_FIRE_CORAL_FAN,
        DEAD_HORN_CORAL_FAN)
        .add(DEAD_CORAL_PLANTS.getValues());

    public static final KeysSet<Material> DEAD_WALL_CORALS = new MaterialKeysSet(
        DEAD_TUBE_CORAL_WALL_FAN,
        DEAD_BRAIN_CORAL_WALL_FAN,
        DEAD_BUBBLE_CORAL_WALL_FAN,
        DEAD_FIRE_CORAL_WALL_FAN,
        DEAD_HORN_CORAL_WALL_FAN);

    public static final KeysSet<Material> DEAD_CORAL_BLOCKS = new MaterialKeysSet(
        DEAD_TUBE_CORAL_BLOCK,
        DEAD_BRAIN_CORAL_BLOCK,
        DEAD_BUBBLE_CORAL_BLOCK,
        DEAD_FIRE_CORAL_BLOCK,
        DEAD_HORN_CORAL_BLOCK);

    public static final KeysSet<Material> ALL_DEAD_CORALS = new MaterialKeysSet()
        .add(DEAD_CORAL_BLOCKS.getValues())
        .add(DEAD_WALL_CORALS.getValues())
        .add(DEAD_CORAL_PLANTS.getValues())
        .add(DEAD_CORALS.getValues());

    public static final KeysSet<Material> UNDERWATER_BLOCKS_ONLY = new MaterialKeysSet(
        Material.WATER,
        BUBBLE_COLUMN,
        KELP_PLANT,
        TALL_SEAGRASS,
        SEAGRASS);

    public static final KeysSet<Material> GRASS_BLOCK = new MaterialKeysSet(
        Material.GRASS_BLOCK);

    public static final KeysSet<Material> DIRT_PATH_BLOCK = new MaterialKeysSet()
        .addBlocks("DIRT_PATH", "GRASS_PATH");

    public static final KeysSet<Material> FARMLAND_BLOCK = new MaterialKeysSet(
        FARMLAND);

    public static final KeysSet<Material> MYCELIUM_BLOCK = new MaterialKeysSet(
        MYCELIUM);

    public static final KeysSet<Material> SUGAR_CANE_BLOCK = new MaterialKeysSet(
        SUGAR_CANE);

    public static final KeysSet<Material> NETHER_WART_BLOCK = new MaterialKeysSet(
        NETHER_WART);

    public static final KeysSet<Material> WHEAT_BLOCK = new MaterialKeysSet(
        WHEAT);

    public static final KeysSet<Material> POTATO_BLOCK = new MaterialKeysSet(
        POTATOES);

    public static final KeysSet<Material> CARROT_BLOCK = new MaterialKeysSet(
        CARROTS);

    public static final KeysSet<Material> BEETROOT_BLOCK = new MaterialKeysSet(
        BEETROOTS);

    public static final KeysSet<Material> PUMPKIN_STEM_AND_BLOCK = new MaterialKeysSet(
        PUMPKIN_STEM, PUMPKIN);

    public static final KeysSet<Material> MELON_STEM_AND_BLOCK = new MaterialKeysSet(
        MELON_STEM, MELON);
}
