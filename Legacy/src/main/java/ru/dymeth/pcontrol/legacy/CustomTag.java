package ru.dymeth.pcontrol.legacy;

import org.bukkit.Material;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.set.CustomSet;
import ru.dymeth.pcontrol.api.set.BlocksSet;

import javax.annotation.Nonnull;

import static org.bukkit.Material.*;

public final class CustomTag {
    private final PControlData data;

    CustomTag(@Nonnull PControlData data) {
        this.data = data;
    }

    public final CustomSet<Material> WORLD_AIR = new BlocksSet(
        AIR);

    public final CustomSet<Material> WOODEN_DOORS = new BlocksSet(
        ACACIA_DOOR,
        BIRCH_DOOR,
        DARK_OAK_DOOR,
        JUNGLE_DOOR,
        WOODEN_DOOR,
        SPRUCE_FENCE);

    public final CustomSet<Material> PRESSURE_PLATES = new BlocksSet(
        STONE_PLATE,
        GOLD_PLATE,
        IRON_PLATE,
        WOOD_PLATE);

    public final CustomSet<Material> REDSTONE_PASSIVE_INPUTS = new BlocksSet(
        TRIPWIRE_HOOK,
        TRIPWIRE)
        .add(PRESSURE_PLATES.getValues())
        .add(STONE_BUTTON)
        .add(WOOD_BUTTON);

    public final CustomSet<Material> REDSTONE_ORE_BLOCKS = new BlocksSet(
        REDSTONE_ORE,
        GLOWING_REDSTONE_ORE);

    public final CustomSet<Material> WATER = new BlocksSet(
        Material.WATER,
        STATIONARY_WATER);

    public final CustomSet<Material> LAVA = new BlocksSet(
        Material.LAVA,
        STATIONARY_LAVA);

    public final CustomSet<Material> SAND = new BlocksSet(
        Material.SAND);

    public final CustomSet<Material> GRAVEL = new BlocksSet(
        Material.GRAVEL);

    public final CustomSet<Material> ANVIL = new BlocksSet(
        Material.ANVIL);

    public final CustomSet<Material> CONCRETE_POWDERS = new BlocksSet()
        .add("CONCRETE_POWDER");

    public final CustomSet<Material> GRAVITY_BLOCKS = new BlocksSet()
        .add(Material::hasGravity)
        .add(DRAGON_EGG);

    public final CustomSet<Material> NATURAL_GRAVITY_BLOCKS = new BlocksSet()
        .add(SAND.getValues())
        .add(GRAVEL);

    public final CustomSet<Material> BONE_MEAL_HERBS = new BlocksSet(
        LONG_GRASS,
        YELLOW_FLOWER,
        RED_ROSE);

    public final CustomSet<Material> LITTLE_MUSHROOMS = new BlocksSet(
        RED_MUSHROOM,
        BROWN_MUSHROOM);

    public final CustomSet<Material> UNDERWATER_BLOCKS_ONLY = new BlocksSet(
        Material.WATER,
        STATIONARY_WATER);

    public final CustomSet<Material> GRASS_BLOCK = new BlocksSet(
        Material.GRASS);

    public final CustomSet<Material> DIRT_PATH_BLOCK = new BlocksSet()
        .add("GRASS_PATH");

    public final CustomSet<Material> FARMLAND_BLOCK = new BlocksSet(
        SOIL);

    public final CustomSet<Material> MYCELIUM_BLOCK = new BlocksSet(
        MYCEL);

    public final CustomSet<Material> SUGAR_CANE_BLOCK = new BlocksSet(
        Material.SUGAR_CANE_BLOCK);

    public final CustomSet<Material> NETHER_WART_BLOCK = new BlocksSet(
        NETHER_WARTS);

    public final CustomSet<Material> WHEAT_BLOCK = new BlocksSet(
        CROPS);

    public final CustomSet<Material> POTATO_BLOCK = new BlocksSet(
        POTATO);

    public final CustomSet<Material> CARROT_BLOCK = new BlocksSet(
        CARROT);

    public final CustomSet<Material> BEETROOT_BLOCK = new BlocksSet()
        .add("BEETROOT_BLOCK");

    public final CustomSet<Material> PUMPKIN_STEM_AND_BLOCK = new BlocksSet(
        PUMPKIN_STEM, PUMPKIN);

    public final CustomSet<Material> MELON_STEM_AND_BLOCK = new BlocksSet(
        MELON_STEM, MELON_BLOCK);

    // legacy-specific

    public final CustomSet<Material> RAILS = new BlocksSet(
        Material.RAILS,
        ACTIVATOR_RAIL,
        DETECTOR_RAIL,
        POWERED_RAIL);
}
