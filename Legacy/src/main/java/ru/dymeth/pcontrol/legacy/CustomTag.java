package ru.dymeth.pcontrol.legacy;

import org.bukkit.Material;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.set.KeysSet;
import ru.dymeth.pcontrol.api.set.MaterialKeysSet;

import javax.annotation.Nonnull;

import static org.bukkit.Material.*;

public final class CustomTag {
    private final PControlData data;

    CustomTag(@Nonnull PControlData data) {
        this.data = data;
    }

    public final KeysSet<Material> WORLD_AIR = new MaterialKeysSet(
        AIR);

    public final KeysSet<Material> RAILS = new MaterialKeysSet(
        Material.RAILS,
        ACTIVATOR_RAIL,
        DETECTOR_RAIL,
        POWERED_RAIL);

    public final KeysSet<Material> WOODEN_DOORS = new MaterialKeysSet(
        ACACIA_DOOR,
        BIRCH_DOOR,
        DARK_OAK_DOOR,
        JUNGLE_DOOR,
        WOODEN_DOOR,
        SPRUCE_FENCE);

    public final KeysSet<Material> PRESSURE_PLATES = new MaterialKeysSet(
        STONE_PLATE,
        GOLD_PLATE,
        IRON_PLATE,
        WOOD_PLATE);

    public final KeysSet<Material> REDSTONE_PASSIVE_INPUTS = new MaterialKeysSet(
        TRIPWIRE_HOOK,
        TRIPWIRE)
        .add(PRESSURE_PLATES.getValues())
        .add(STONE_BUTTON)
        .add(WOOD_BUTTON);

    public final KeysSet<Material> REDSTONE_ORE_BLOCKS = new MaterialKeysSet(
        REDSTONE_ORE,
        GLOWING_REDSTONE_ORE);

    public final KeysSet<Material> WATER = new MaterialKeysSet(
        Material.WATER,
        STATIONARY_WATER);

    public final KeysSet<Material> LAVA = new MaterialKeysSet(
        Material.LAVA,
        STATIONARY_LAVA);

    public final KeysSet<Material> SAND = new MaterialKeysSet(
        Material.SAND);

    public final KeysSet<Material> GRAVEL = new MaterialKeysSet(
        Material.GRAVEL);

    public final KeysSet<Material> ANVIL = new MaterialKeysSet(
        Material.ANVIL);

    public final KeysSet<Material> CONCRETE_POWDERS = new MaterialKeysSet()
        .addBlocks("CONCRETE_POWDER");

    public final KeysSet<Material> GRAVITY_BLOCKS = new MaterialKeysSet()
        .add(Material::hasGravity)
        .add(DRAGON_EGG);

    public final KeysSet<Material> NATURAL_GRAVITY_BLOCKS = new MaterialKeysSet()
        .add(SAND.getValues())
        .add(GRAVEL);

    public final KeysSet<Material> BONE_MEAL_HERBS = new MaterialKeysSet(
        LONG_GRASS,
        YELLOW_FLOWER,
        RED_ROSE);

    public final KeysSet<Material> LITTLE_MUSHROOMS = new MaterialKeysSet(
        RED_MUSHROOM,
        BROWN_MUSHROOM);

    public final KeysSet<Material> UNDERWATER_BLOCKS_ONLY = new MaterialKeysSet(
        Material.WATER,
        STATIONARY_WATER);

    public final KeysSet<Material> GRASS_BLOCK = new MaterialKeysSet(
        Material.GRASS);

    public final KeysSet<Material> DIRT_PATH_BLOCK = new MaterialKeysSet()
        .addBlocks("GRASS_PATH");

    public final KeysSet<Material> FARMLAND_BLOCK = new MaterialKeysSet(
        SOIL);

    public final KeysSet<Material> MYCELIUM_BLOCK = new MaterialKeysSet(
        MYCEL);

    public final KeysSet<Material> SUGAR_CANE_BLOCK = new MaterialKeysSet(
        Material.SUGAR_CANE_BLOCK);

    public final KeysSet<Material> NETHER_WART_BLOCK = new MaterialKeysSet(
        NETHER_WARTS);

    public final KeysSet<Material> WHEAT_BLOCK = new MaterialKeysSet(
        CROPS);

    public final KeysSet<Material> POTATO_BLOCK = new MaterialKeysSet(
        POTATO);

    public final KeysSet<Material> CARROT_BLOCK = new MaterialKeysSet(
        CARROT);

    public final KeysSet<Material> BEETROOT_BLOCK = new MaterialKeysSet()
        .addBlocks("BEETROOT_BLOCK");

    public final KeysSet<Material> PUMPKIN_STEM_AND_BLOCK = new MaterialKeysSet(
        PUMPKIN_STEM, PUMPKIN);

    public final KeysSet<Material> MELON_STEM_AND_BLOCK = new MaterialKeysSet(
        MELON_STEM, MELON_BLOCK);
}
