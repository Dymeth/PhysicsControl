package ru.dymeth.pcontrol.modern;

import org.bukkit.Material;
import org.bukkit.Tag;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.set.CustomSet;
import ru.dymeth.pcontrol.api.set.BlocksSet;

import javax.annotation.Nonnull;

import static org.bukkit.Material.*;
import static org.bukkit.Tag.*;

public final class CustomTag {
    private final PControlData data;

    CustomTag(@Nonnull PControlData data) {
        this.data = data;
    }

    public final CustomSet<Material> WORLD_AIR = new BlocksSet(
        AIR,
        CAVE_AIR);

    public final CustomSet<Material> WOODEN_DOORS = new BlocksSet()
        .add(Tag.WOODEN_DOORS.getValues());

    public final CustomSet<Material> PRESSURE_PLATES = new BlocksSet(
        STONE_PRESSURE_PLATE,
        LIGHT_WEIGHTED_PRESSURE_PLATE,
        HEAVY_WEIGHTED_PRESSURE_PLATE)
        .add("POLISHED_BLACKSTONE_PRESSURE_PLATE")
        .add(WOODEN_PRESSURE_PLATES.getValues());

    public final CustomSet<Material> REDSTONE_PASSIVE_INPUTS = new BlocksSet(
        TRIPWIRE_HOOK,
        TRIPWIRE)
        .add(PRESSURE_PLATES.getValues())
        .add(BUTTONS.getValues());

    public final CustomSet<Material> REDSTONE_ORE_BLOCKS = new BlocksSet(
        REDSTONE_ORE)
        .add("DEEPSLATE_REDSTONE_ORE");

    public final CustomSet<Material> WATER = new BlocksSet(
        Material.WATER);

    public final CustomSet<Material> LAVA = new BlocksSet(
        Material.LAVA);

    public final CustomSet<Material> SAND = new BlocksSet()
        .add(Tag.SAND.getValues());

    public final CustomSet<Material> GRAVEL = new BlocksSet(
        Material.GRAVEL)
        .add("SUSPICIOUS_GRAVEL");

    public final CustomSet<Material> ANVIL = new BlocksSet()
        .add(Tag.ANVIL.getValues());

    public final CustomSet<Material> CONCRETE_POWDERS = new BlocksSet(
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

    public final CustomSet<Material> GRAVITY_BLOCKS = new BlocksSet()
        .add("SCAFFOLDING")
        .add(Material::hasGravity);

    public final CustomSet<Material> NATURAL_GRAVITY_BLOCKS = new BlocksSet()
        .add(SAND.getValues())
        .add(GRAVEL);

    public final CustomSet<Material> BONE_MEAL_HERBS = new BlocksSet(
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

    public final CustomSet<Material> LITTLE_MUSHROOMS = new BlocksSet(
        RED_MUSHROOM,
        BROWN_MUSHROOM);public final CustomSet<Material> UNDERWATER_BLOCKS_ONLY = new BlocksSet(
        Material.WATER,
        BUBBLE_COLUMN,
        KELP_PLANT,
        TALL_SEAGRASS,
        SEAGRASS);

    public final CustomSet<Material> GRASS_BLOCK = new BlocksSet(
        Material.GRASS_BLOCK);

    public final CustomSet<Material> DIRT_PATH_BLOCK = new BlocksSet()
        .add("DIRT_PATH", "GRASS_PATH");

    public final CustomSet<Material> FARMLAND_BLOCK = new BlocksSet(
        FARMLAND);

    public final CustomSet<Material> MYCELIUM_BLOCK = new BlocksSet(
        MYCELIUM);

    public final CustomSet<Material> SUGAR_CANE_BLOCK = new BlocksSet(
        SUGAR_CANE);

    public final CustomSet<Material> NETHER_WART_BLOCK = new BlocksSet(
        NETHER_WART);

    public final CustomSet<Material> WHEAT_BLOCK = new BlocksSet(
        WHEAT);

    public final CustomSet<Material> POTATO_BLOCK = new BlocksSet(
        POTATOES);

    public final CustomSet<Material> CARROT_BLOCK = new BlocksSet(
        CARROTS);

    public final CustomSet<Material> BEETROOT_BLOCK = new BlocksSet(
        BEETROOTS);

    public final CustomSet<Material> PUMPKIN_STEM_AND_BLOCK = new BlocksSet(
        PUMPKIN_STEM, PUMPKIN);

    public final CustomSet<Material> MELON_STEM_AND_BLOCK = new BlocksSet(
        MELON_STEM, MELON);

    // modern-specific

    public final CustomSet<Material> FENCES = new BlocksSet(
        ACACIA_FENCE,
        BIRCH_FENCE,
        DARK_OAK_FENCE,
        JUNGLE_FENCE,
        NETHER_BRICK_FENCE,
        OAK_FENCE,
        SPRUCE_FENCE)
        .add(
            "CRIMSON_FENCE",
            "WARPED_FENCE"
        );

    public final CustomSet<Material> SIGNS = new BlocksSet()
        .add(
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

    public final CustomSet<Material> WALL_SIGNS = new BlocksSet()
        .add(
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

    public final CustomSet<Material> REDSTONE_ACTIVE_INPUTS = new BlocksSet(
        LEVER,
        TRAPPED_CHEST,
        DAYLIGHT_DETECTOR,
        OBSERVER)
        .add(BUTTONS.getValues());

    public final CustomSet<Material> REDSTONE_TRANSMITTERS_AND_OUTPUTS = new BlocksSet(
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

    public final CustomSet<Material> ALL_ALIVE_CORALS = new BlocksSet()
        .add(CORAL_BLOCKS.getValues())
        .add(WALL_CORALS.getValues())
        .add(CORAL_PLANTS.getValues())
        .add(CORALS.getValues());

    public final CustomSet<Material> DEAD_CORAL_PLANTS = new BlocksSet(
        DEAD_TUBE_CORAL,
        DEAD_BRAIN_CORAL,
        DEAD_BUBBLE_CORAL,
        DEAD_FIRE_CORAL,
        DEAD_HORN_CORAL);

    public final CustomSet<Material> DEAD_CORALS = new BlocksSet(
        DEAD_TUBE_CORAL_FAN,
        DEAD_BRAIN_CORAL_FAN,
        DEAD_BUBBLE_CORAL_FAN,
        DEAD_FIRE_CORAL_FAN,
        DEAD_HORN_CORAL_FAN)
        .add(DEAD_CORAL_PLANTS.getValues());

    public final CustomSet<Material> DEAD_WALL_CORALS = new BlocksSet(
        DEAD_TUBE_CORAL_WALL_FAN,
        DEAD_BRAIN_CORAL_WALL_FAN,
        DEAD_BUBBLE_CORAL_WALL_FAN,
        DEAD_FIRE_CORAL_WALL_FAN,
        DEAD_HORN_CORAL_WALL_FAN);

    public final CustomSet<Material> DEAD_CORAL_BLOCKS = new BlocksSet(
        DEAD_TUBE_CORAL_BLOCK,
        DEAD_BRAIN_CORAL_BLOCK,
        DEAD_BUBBLE_CORAL_BLOCK,
        DEAD_FIRE_CORAL_BLOCK,
        DEAD_HORN_CORAL_BLOCK);

    public final CustomSet<Material> ALL_DEAD_CORALS = new BlocksSet()
        .add(DEAD_CORAL_BLOCKS.getValues())
        .add(DEAD_WALL_CORALS.getValues())
        .add(DEAD_CORAL_PLANTS.getValues())
        .add(DEAD_CORALS.getValues());
}
