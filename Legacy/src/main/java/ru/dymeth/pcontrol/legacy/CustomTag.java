package ru.dymeth.pcontrol.legacy;

import org.bukkit.Material;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.set.BlocksSet;
import ru.dymeth.pcontrol.api.set.CustomSet;

import javax.annotation.Nonnull;

import static org.bukkit.Material.*;

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
            AIR);

        WOODEN_DOORS = new BlocksSet(
            ACACIA_DOOR,
            BIRCH_DOOR,
            DARK_OAK_DOOR,
            JUNGLE_DOOR,
            WOODEN_DOOR,
            SPRUCE_FENCE);

        PRESSURE_PLATES = new BlocksSet(
            STONE_PLATE,
            GOLD_PLATE,
            IRON_PLATE,
            WOOD_PLATE);

        REDSTONE_PASSIVE_INPUTS = new BlocksSet(
            TRIPWIRE_HOOK,
            TRIPWIRE)
            .add(PRESSURE_PLATES.getValues())
            .add(STONE_BUTTON)
            .add(WOOD_BUTTON);

        REDSTONE_ORE_BLOCKS = new BlocksSet(
            REDSTONE_ORE,
            GLOWING_REDSTONE_ORE);

        WATER = new BlocksSet(
            Material.WATER,
            STATIONARY_WATER);

        LAVA = new BlocksSet(
            Material.LAVA,
            STATIONARY_LAVA);

        SAND = new BlocksSet(
            Material.SAND);

        GRAVEL = new BlocksSet(
            Material.GRAVEL);

        ANVIL = new BlocksSet(
            Material.ANVIL);

        CONCRETE_POWDERS = new BlocksSet()
            .add("CONCRETE_POWDER");

        GRAVITY_BLOCKS = new BlocksSet()
            .add(Material::hasGravity)
            .add(DRAGON_EGG);

        NATURAL_GRAVITY_BLOCKS = new BlocksSet()
            .add(SAND.getValues())
            .add(GRAVEL.getValues());

        BONE_MEAL_HERBS = new BlocksSet(
            LONG_GRASS,
            YELLOW_FLOWER,
            RED_ROSE);

        LITTLE_MUSHROOMS = new BlocksSet(
            RED_MUSHROOM,
            BROWN_MUSHROOM);

        UNDERWATER_BLOCKS_ONLY = new BlocksSet(
            Material.WATER,
            STATIONARY_WATER);

        GRASS_BLOCK = new BlocksSet(
            Material.GRASS);

        DIRT_PATH_BLOCK = new BlocksSet()
            .add("GRASS_PATH");

        FARMLAND_BLOCK = new BlocksSet(
            SOIL);

        MYCELIUM_BLOCK = new BlocksSet(
            MYCEL);

        SUGAR_CANE_BLOCK = new BlocksSet(
            Material.SUGAR_CANE_BLOCK);

        NETHER_WART_BLOCK = new BlocksSet(
            NETHER_WARTS);

        WHEAT_BLOCK = new BlocksSet(
            CROPS);

        POTATO_BLOCK = new BlocksSet(
            POTATO);

        CARROT_BLOCK = new BlocksSet(
            CARROT);

        BEETROOT_BLOCK = new BlocksSet()
            .add("BEETROOT_BLOCK");

        PUMPKIN_STEM_AND_BLOCK = new BlocksSet(
            PUMPKIN_STEM, PUMPKIN);

        MELON_STEM_AND_BLOCK = new BlocksSet(
            MELON_STEM, MELON_BLOCK);

        // legacy-specific

        RAILS = new BlocksSet(
            Material.RAILS,
            ACTIVATOR_RAIL,
            DETECTOR_RAIL,
            POWERED_RAIL);
    }
}
