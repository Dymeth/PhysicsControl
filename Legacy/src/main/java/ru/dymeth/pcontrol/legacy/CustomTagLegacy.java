package ru.dymeth.pcontrol.legacy;

import org.bukkit.Material;
import ru.dymeth.pcontrol.api.set.KeysSet;
import ru.dymeth.pcontrol.api.set.MaterialKeysSet;

import static org.bukkit.Material.*;

public final class CustomTagLegacy {
    public static final KeysSet<Material> RAILS = new MaterialKeysSet(
        Material.RAILS,
        ACTIVATOR_RAIL,
        DETECTOR_RAIL,
        POWERED_RAIL);

    public static final KeysSet<Material> WOODEN_DOORS = new MaterialKeysSet(
        ACACIA_DOOR,
        BIRCH_DOOR,
        DARK_OAK_DOOR,
        JUNGLE_DOOR,
        WOODEN_DOOR,
        SPRUCE_FENCE);

    public static final KeysSet<Material> PRESSURE_PLATES = new MaterialKeysSet(
        STONE_PLATE,
        GOLD_PLATE,
        IRON_PLATE,
        WOOD_PLATE);

    public static final KeysSet<Material> REDSTONE_PASSIVE_INPUTS = new MaterialKeysSet(
        TRIPWIRE_HOOK,
        TRIPWIRE)
        .add(PRESSURE_PLATES.getValues())
        .add(STONE_BUTTON)
        .add(WOOD_BUTTON);

    public static final KeysSet<Material> GRAVITY_BLOCKS = new MaterialKeysSet()
        .add(Material::hasGravity)
        .add(DRAGON_EGG);

    public static final KeysSet<Material> BONE_MEAL_HERBS = new MaterialKeysSet(
        LONG_GRASS,
        YELLOW_FLOWER,
        RED_ROSE);

    public static final KeysSet<Material> LITTLE_MUSHROOMS = new MaterialKeysSet(
        RED_MUSHROOM,
        BROWN_MUSHROOM);

    public static final KeysSet<Material> GRASS_AND_PATH_BLOCKS = new MaterialKeysSet(
        GRASS)
        .addBlocks(
            "GRASS_PATH"
        );
}
