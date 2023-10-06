package ru.dymeth.pcontrol.modern;

import org.bukkit.Material;
import org.bukkit.Tag;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.set.BlocksSet;
import ru.dymeth.pcontrol.api.set.CustomSet;

import javax.annotation.Nonnull;

public final class CustomTag {

    private final PControlData data;

    public final CustomSet<Material>
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
        MELON_STEM_AND_BLOCK;

    // modern-specific
    public final CustomSet<Material>
        FENCES,
        SIGNS,
        WALL_SIGNS,
        REDSTONE_ACTIVE_INPUTS,
        REDSTONE_TRANSMITTERS_AND_OUTPUTS,
        ALL_ALIVE_CORALS,
        DEAD_CORAL_PLANTS,
        DEAD_CORALS,
        DEAD_WALL_CORALS,
        DEAD_CORAL_BLOCKS,
        ALL_DEAD_CORALS;

    CustomTag(@Nonnull PControlData data) {
        this.data = data;

        WORLD_AIR = new BlocksSet(
            Material.AIR,
            Material.CAVE_AIR);

        WOODEN_DOORS = new BlocksSet()
            .add(Tag.WOODEN_DOORS.getValues());

        PRESSURE_PLATES = new BlocksSet(
            Material.STONE_PRESSURE_PLATE,
            Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
            Material.HEAVY_WEIGHTED_PRESSURE_PLATE)
            .add("POLISHED_BLACKSTONE_PRESSURE_PLATE")
            .add(Tag.WOODEN_PRESSURE_PLATES.getValues());

        REDSTONE_PASSIVE_INPUTS = new BlocksSet(
            Material.TRIPWIRE_HOOK,
            Material.TRIPWIRE)
            .add(this.PRESSURE_PLATES.getValues())
            .add(Tag.BUTTONS.getValues());

        REDSTONE_ORE_BLOCKS = new BlocksSet(
            Material.REDSTONE_ORE)
            .add("DEEPSLATE_REDSTONE_ORE");

        WATER = new BlocksSet(
            Material.WATER);

        LAVA = new BlocksSet(
            Material.LAVA);

        SAND = new BlocksSet()
            .add(Tag.SAND.getValues());

        GRAVEL = new BlocksSet(
            Material.GRAVEL)
            .add("SUSPICIOUS_GRAVEL");

        ANVIL = new BlocksSet()
            .add(Tag.ANVIL.getValues());

        CONCRETE_POWDERS = new BlocksSet(
            Material.BLACK_CONCRETE_POWDER,
            Material.BLUE_CONCRETE_POWDER,
            Material.BROWN_CONCRETE_POWDER,
            Material.CYAN_CONCRETE_POWDER,
            Material.GRAY_CONCRETE_POWDER,
            Material.GREEN_CONCRETE_POWDER,
            Material.LIGHT_BLUE_CONCRETE_POWDER,
            Material.LIGHT_GRAY_CONCRETE_POWDER,
            Material.LIME_CONCRETE_POWDER,
            Material.MAGENTA_CONCRETE_POWDER,
            Material.ORANGE_CONCRETE_POWDER,
            Material.PINK_CONCRETE_POWDER,
            Material.PURPLE_CONCRETE_POWDER,
            Material.RED_CONCRETE_POWDER,
            Material.WHITE_CONCRETE_POWDER,
            Material.YELLOW_CONCRETE_POWDER
        );

        GRAVITY_BLOCKS = new BlocksSet()
            .add("SCAFFOLDING")
            .add(Material::hasGravity);

        NATURAL_GRAVITY_BLOCKS = new BlocksSet()
            .add(this.SAND.getValues())
            .add(this.GRAVEL.getValues());

        BONE_MEAL_HERBS = new BlocksSet(
            Material.GRASS,
            Material.DANDELION,
            Material.POPPY,
            Material.BLUE_ORCHID,
            Material.ALLIUM,
            Material.AZURE_BLUET,
            Material.RED_TULIP,
            Material.ORANGE_TULIP,
            Material.WHITE_TULIP,
            Material.PINK_TULIP,
            Material.OXEYE_DAISY,
            Material.SUNFLOWER,
            Material.LILAC,
            Material.ROSE_BUSH,
            Material.PEONY)
            .add(
                "CORNFLOWER",
                "LILY_OF_THE_VALLEY"
            );

        LITTLE_MUSHROOMS = new BlocksSet(
            Material.RED_MUSHROOM,
            Material.BROWN_MUSHROOM);

        UNDERWATER_BLOCKS_ONLY = new BlocksSet(
            Material.WATER,
            Material.BUBBLE_COLUMN,
            Material.KELP_PLANT,
            Material.TALL_SEAGRASS,
            Material.SEAGRASS);

        GRASS_BLOCK = new BlocksSet(
            Material.GRASS_BLOCK);

        DIRT_PATH_BLOCK = new BlocksSet()
            .add("DIRT_PATH", "GRASS_PATH");

        FARMLAND_BLOCK = new BlocksSet(
            Material.FARMLAND);

        MYCELIUM_BLOCK = new BlocksSet(
            Material.MYCELIUM);

        SUGAR_CANE_BLOCK = new BlocksSet(
            Material.SUGAR_CANE);

        NETHER_WART_BLOCK = new BlocksSet(
            Material.NETHER_WART);

        WHEAT_BLOCK = new BlocksSet(
            Material.WHEAT);

        POTATO_BLOCK = new BlocksSet(
            Material.POTATOES);

        CARROT_BLOCK = new BlocksSet(
            Material.CARROTS);

        BEETROOT_BLOCK = new BlocksSet(
            Material.BEETROOTS);

        PUMPKIN_STEM_AND_BLOCK = new BlocksSet(
            Material.PUMPKIN_STEM,
            Material.PUMPKIN);

        MELON_STEM_AND_BLOCK = new BlocksSet(
            Material.MELON_STEM,
            Material.MELON);

        // modern-specific

        FENCES = new BlocksSet(
            Material.ACACIA_FENCE,
            Material.BIRCH_FENCE,
            Material.DARK_OAK_FENCE,
            Material.JUNGLE_FENCE,
            Material.NETHER_BRICK_FENCE,
            Material.OAK_FENCE,
            Material.SPRUCE_FENCE)
            .add(
                "CRIMSON_FENCE",
                "WARPED_FENCE"
            );

        SIGNS = new BlocksSet()
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

        WALL_SIGNS = new BlocksSet()
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

        REDSTONE_ACTIVE_INPUTS = new BlocksSet(
            Material.LEVER,
            Material.TRAPPED_CHEST,
            Material.DAYLIGHT_DETECTOR,
            Material.OBSERVER)
            .add(Tag.BUTTONS.getValues());

        REDSTONE_TRANSMITTERS_AND_OUTPUTS = new BlocksSet(
            Material.DISPENSER,
            Material.STICKY_PISTON,
            Material.PISTON,
            Material.PISTON_HEAD,
            Material.MOVING_PISTON,
            Material.TNT,
            Material.REDSTONE_TORCH,
            Material.REDSTONE_WALL_TORCH,
            Material.REDSTONE_LAMP,
            Material.REDSTONE_BLOCK,
            Material.HOPPER,
            Material.DROPPER,
            Material.REPEATER,
            Material.COMPARATOR,
            Material.REDSTONE_WIRE)
            .add(Tag.TRAPDOORS.getValues())
            .add(this.FENCES.getValues())
            .add(Tag.DOORS.getValues());

        ALL_ALIVE_CORALS = new BlocksSet()
            .add(Tag.CORAL_BLOCKS.getValues())
            .add(Tag.WALL_CORALS.getValues())
            .add(Tag.CORAL_PLANTS.getValues())
            .add(Tag.CORALS.getValues());

        DEAD_CORAL_PLANTS = new BlocksSet(
            Material.DEAD_TUBE_CORAL,
            Material.DEAD_BRAIN_CORAL,
            Material.DEAD_BUBBLE_CORAL,
            Material.DEAD_FIRE_CORAL,
            Material.DEAD_HORN_CORAL);

        DEAD_CORALS = new BlocksSet(
            Material.DEAD_TUBE_CORAL_FAN,
            Material.DEAD_BRAIN_CORAL_FAN,
            Material.DEAD_BUBBLE_CORAL_FAN,
            Material.DEAD_FIRE_CORAL_FAN,
            Material.DEAD_HORN_CORAL_FAN)
            .add(this.DEAD_CORAL_PLANTS.getValues());

        DEAD_WALL_CORALS = new BlocksSet(
            Material.DEAD_TUBE_CORAL_WALL_FAN,
            Material.DEAD_BRAIN_CORAL_WALL_FAN,
            Material.DEAD_BUBBLE_CORAL_WALL_FAN,
            Material.DEAD_FIRE_CORAL_WALL_FAN,
            Material.DEAD_HORN_CORAL_WALL_FAN);

        DEAD_CORAL_BLOCKS = new BlocksSet(
            Material.DEAD_TUBE_CORAL_BLOCK,
            Material.DEAD_BRAIN_CORAL_BLOCK,
            Material.DEAD_BUBBLE_CORAL_BLOCK,
            Material.DEAD_FIRE_CORAL_BLOCK,
            Material.DEAD_HORN_CORAL_BLOCK);

        ALL_DEAD_CORALS = new BlocksSet()
            .add(this.DEAD_CORAL_BLOCKS.getValues())
            .add(this.DEAD_WALL_CORALS.getValues())
            .add(this.DEAD_CORAL_PLANTS.getValues())
            .add(this.DEAD_CORALS.getValues());
    }
}
