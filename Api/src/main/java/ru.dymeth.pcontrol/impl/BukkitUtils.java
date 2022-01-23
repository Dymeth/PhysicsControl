package ru.dymeth.pcontrol.impl;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BukkitUtils {
    @Nonnull
    public static Set<Material> matchBlockMaterials(@Nullable Consumer<String> onFail, @Nonnull String... names) {
        return matchMaterials(Material::isBlock, onFail, names);
    }

    @Nonnull
    public static Set<Material> matchItemMaterials(@Nullable Consumer<String> onFail, @Nonnull String... names) {
        return matchMaterials(Material::isItem, onFail, names);
    }

    @Nonnull
    public static Set<Material> matchMaterials(@Nonnull Predicate<Material> filter, @Nullable Consumer<String> onFail, @Nonnull String... names) {
        Set<Material> result = new HashSet<>();
        for (String name : names) {
            Material material = Material.matchMaterial(name);
            if (isValidMaterial(material) && filter.test(material)) {
                result.add(material);
            } else if (onFail != null) {
                onFail.accept(name);
            }
        }
        return result;
    }

    @Nonnull
    public static ItemStack matchIcon(@Nonnull String... iconVariants) {
        for (String iconVariant : iconVariants) {
            try {
                Byte data;
                int damageSplitter = iconVariant.indexOf(':');
                if (damageSplitter >= 0) {
                    data = Byte.parseByte(iconVariant.substring(damageSplitter + 1));
                    iconVariant = iconVariant.substring(0, damageSplitter);
                } else {
                    data = null;
                }
                Material iconMaterial = Material.matchMaterial(iconVariant);

                if (!isValidMaterial(iconMaterial)) continue;
                if (!iconMaterial.isItem()) continue;

                if (data == null) {
                    return new ItemStack(iconMaterial);
                } else {
                    return new ItemStack(iconMaterial, 1, (short) 0, data);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        new IllegalArgumentException("Unable to load icon from variants: " + Arrays.toString(iconVariants)).printStackTrace();
        return new ItemStack(Material.BARRIER);
    }

    @Nonnull
    public static Set<EntityType> matchEntityTypes(@Nullable Consumer<String> onFail, @Nonnull String... names) {
        Set<EntityType> result = new HashSet<>();
        for (String name : names) {
            EntityType element = JavaUtils.getEnum(EntityType.class, name);
            if (element == null) {
                if (onFail != null) onFail.accept(name);
            } else {
                result.add(element);
            }
        }
        return result;
    }

    private static boolean isValidMaterial(@Nullable Material material) {
        return material != null && material != Material.AIR && !material.name().startsWith("LEGACY_");
    }
}
