package ru.dymeth.pcontrol.legacy;

import org.bukkit.Material;

import static org.bukkit.Material.*;

public final class CustomTagLegacy {
    public static final MaterialSetLegacy RAILS = new MaterialSetLegacy(
            Material.RAILS,
            ACTIVATOR_RAIL,
            DETECTOR_RAIL,
            POWERED_RAIL);

    public static final MaterialSetLegacy PRESSURE_PLATES = new MaterialSetLegacy(
            STONE_PLATE,
            GOLD_PLATE,
            IRON_PLATE,
            WOOD_PLATE);

    public static final MaterialSetLegacy REDSTONE_PASSIVE_INPUTS = new MaterialSetLegacy(
            TRIPWIRE_HOOK,
            TRIPWIRE)
            .add(PRESSURE_PLATES.getValues());

    public static final MaterialSetLegacy GRAVITY_BLOCKS = new MaterialSetLegacy(
            SAND,
            GRAVEL,
            ANVIL)
            .add(
                    "CONCRETE_POWDER"
            );

    public static final MaterialSetLegacy BONE_MEAL_HERBS = new MaterialSetLegacy(
            LONG_GRASS,
            YELLOW_FLOWER,
            RED_ROSE);

    public static final MaterialSetLegacy LITTLE_MUSHROOMS = new MaterialSetLegacy(
            RED_MUSHROOM,
            BROWN_MUSHROOM);
}
