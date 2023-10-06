package ru.dymeth.pcontrol.legacy;

import org.bukkit.Material;
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

    // legacy-specific
    public final CustomSet<Material>
        RAILS;

    CustomTag(@Nonnull PControlData data) {
        this.data = data;

        WORLD_AIR = new BlocksSet(
            Material.AIR);

        WOODEN_DOORS = new BlocksSet(
            Material.ACACIA_DOOR,
            Material.BIRCH_DOOR,
            Material.DARK_OAK_DOOR,
            Material.JUNGLE_DOOR,
            Material.WOODEN_DOOR,
            Material.SPRUCE_FENCE);

        PRESSURE_PLATES = new BlocksSet(
            Material.STONE_PLATE,
            Material.GOLD_PLATE,
            Material.IRON_PLATE,
            Material.WOOD_PLATE);

        REDSTONE_PASSIVE_INPUTS = new BlocksSet(
            Material.TRIPWIRE_HOOK,
            Material.TRIPWIRE,
            Material.STONE_BUTTON,
            Material.WOOD_BUTTON)
            .add(this.PRESSURE_PLATES.getValues());

        REDSTONE_ORE_BLOCKS = new BlocksSet(
            Material.REDSTONE_ORE,
            Material.GLOWING_REDSTONE_ORE);

        WATER = new BlocksSet(
            Material.WATER,
            Material.STATIONARY_WATER);

        LAVA = new BlocksSet(
            Material.LAVA,
            Material.STATIONARY_LAVA);

        SAND = new BlocksSet(
            Material.SAND);

        GRAVEL = new BlocksSet(
            Material.GRAVEL);

        ANVIL = new BlocksSet(
            Material.ANVIL);

        CONCRETE_POWDERS = new BlocksSet()
            .add("CONCRETE_POWDER");

        GRAVITY_BLOCKS = new BlocksSet(
            Material.DRAGON_EGG)
            .add(Material::hasGravity);

        NATURAL_GRAVITY_BLOCKS = new BlocksSet()
            .add(this.SAND.getValues())
            .add(this.GRAVEL.getValues());

        BONE_MEAL_HERBS = new BlocksSet(
            Material.LONG_GRASS,
            Material.YELLOW_FLOWER,
            Material.RED_ROSE);

        LITTLE_MUSHROOMS = new BlocksSet(
            Material.RED_MUSHROOM,
            Material.BROWN_MUSHROOM);

        UNDERWATER_BLOCKS_ONLY = new BlocksSet(
            Material.WATER,
            Material.STATIONARY_WATER);

        GRASS_BLOCK = new BlocksSet(
            Material.GRASS);

        DIRT_PATH_BLOCK = new BlocksSet()
            .add("GRASS_PATH");

        FARMLAND_BLOCK = new BlocksSet(
            Material.SOIL);

        MYCELIUM_BLOCK = new BlocksSet(
            Material.MYCEL);

        SUGAR_CANE_BLOCK = new BlocksSet(
            Material.SUGAR_CANE_BLOCK);

        NETHER_WART_BLOCK = new BlocksSet(
            Material.NETHER_WARTS);

        WHEAT_BLOCK = new BlocksSet(
            Material.CROPS);

        POTATO_BLOCK = new BlocksSet(
            Material.POTATO);

        CARROT_BLOCK = new BlocksSet(
            Material.CARROT);

        BEETROOT_BLOCK = new BlocksSet()
            .add("BEETROOT_BLOCK");

        PUMPKIN_STEM_AND_BLOCK = new BlocksSet(
            Material.PUMPKIN_STEM,
            Material.PUMPKIN);

        MELON_STEM_AND_BLOCK = new BlocksSet(
            Material.MELON_STEM,
            Material.MELON_BLOCK);

        // legacy-specific

        RAILS = new BlocksSet(
            Material.RAILS,
            Material.ACTIVATOR_RAIL,
            Material.DETECTOR_RAIL,
            Material.POWERED_RAIL);
    }
}
