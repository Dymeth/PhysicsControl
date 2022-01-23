package ru.dymeth.pcontrol.api;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BukkitUtils {
    private static final boolean MATERIAL_IS_ITEM_SUPPORT = isMethodExist(Material.class, "isItem");
    private static final boolean MATERIAL_IS_BLOCK_SUPPORT = isMethodExist(Material.class, "isBlock");

    @SuppressWarnings("SameParameterValue")
    private static boolean isMethodExist(Class<?> clazz, String methodName, Class<?>... methodParameters) {
        try {
            clazz.getDeclaredMethod(methodName, methodParameters);
            return true;
        } catch (NoSuchMethodException exception) {
            return false;
        }
    }

    @Nonnull
    public static Set<Material> matchBlockMaterials(@Nullable Consumer<String> onFail, @Nonnull String... names) {
        return matchMaterials(MATERIAL_IS_BLOCK_SUPPORT ? Material::isBlock : material -> material != Material.AIR, onFail, names);
    }

    @Nonnull
    public static Set<Material> matchItemMaterials(@Nullable Consumer<String> onFail, @Nonnull String... names) {
        return matchMaterials(MATERIAL_IS_ITEM_SUPPORT ? Material::isItem : material -> material != Material.AIR, onFail, names);
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

    @Nullable
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
                if (MATERIAL_IS_ITEM_SUPPORT && !iconMaterial.isItem()) continue;

                if (data == null) {
                    return new ItemStack(iconMaterial);
                } else {
                    //noinspection deprecation
                    return new ItemStack(iconMaterial, 1, (short) 0, data);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return null;
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
