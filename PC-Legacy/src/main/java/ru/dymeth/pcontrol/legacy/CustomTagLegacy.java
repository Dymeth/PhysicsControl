package ru.dymeth.pcontrol.legacy;

import org.bukkit.Material;
import ru.dymeth.pcontrol.CustomMaterialSet;

import static org.bukkit.Material.*;

public final class CustomTagLegacy {
    public static final CustomMaterialSet RAILS = new CustomMaterialSet(
            Material.RAILS,
            ACTIVATOR_RAIL,
            DETECTOR_RAIL,
            POWERED_RAIL);

    public static final CustomMaterialSet WOODEN_DOORS = new CustomMaterialSet(
            ACACIA_DOOR,
            BIRCH_DOOR,
            DARK_OAK_DOOR,
            JUNGLE_DOOR,
            WOODEN_DOOR,
            SPRUCE_FENCE);

    public static final CustomMaterialSet PRESSURE_PLATES = new CustomMaterialSet(
            STONE_PLATE,
            GOLD_PLATE,
            IRON_PLATE,
            WOOD_PLATE);

    public static final CustomMaterialSet REDSTONE_PASSIVE_INPUTS = new CustomMaterialSet(
            TRIPWIRE_HOOK,
            TRIPWIRE)
            .add(PRESSURE_PLATES.getValues());

    public static final CustomMaterialSet GRAVITY_BLOCKS = new CustomMaterialSet(
            SAND,
            GRAVEL,
            ANVIL)
            .add(
                    "CONCRETE_POWDER"
            );

    public static final CustomMaterialSet BONE_MEAL_HERBS = new CustomMaterialSet(
            LONG_GRASS,
            YELLOW_FLOWER,
            RED_ROSE);

    public static final CustomMaterialSet LITTLE_MUSHROOMS = new CustomMaterialSet(
            RED_MUSHROOM,
            BROWN_MUSHROOM);
}
