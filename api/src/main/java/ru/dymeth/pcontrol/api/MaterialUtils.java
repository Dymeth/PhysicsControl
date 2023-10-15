package ru.dymeth.pcontrol.api;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.dymeth.pcontrol.util.FileUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MaterialUtils {

    private static final boolean MATERIAL_IS_ITEM_SUPPORT = FileUtils.isMethodPresent(Material.class, "isItem");
    private static final Set<Material> AIR_MATERIALS = new HashSet<>();

    static {
        AIR_MATERIALS.add(Material.AIR);
        if (Material.getMaterial("CAVE_AIR") != null) AIR_MATERIALS.add(Material.CAVE_AIR);
        if (Material.getMaterial("VOID_AIR") != null) AIR_MATERIALS.add(Material.VOID_AIR);
    }

    @Nonnull
    public static Set<Material> matchItemMaterials(@Nullable Consumer<String> onFail, @Nonnull String... names) {
        return matchMaterials(material -> isItemMaterial(material, false), onFail, names);
    }

    @Nonnull
    public static Set<Material> matchBlockMaterials(@Nullable Consumer<String> onFail, @Nonnull String... names) {
        return matchMaterials(material -> isBlockMaterial(material, false), onFail, names);
    }

    public static boolean isItemMaterial(@Nullable Material material, boolean allowAir) {
        if (material == null || isLegacyMaterial(material)) return false;
        if (MATERIAL_IS_ITEM_SUPPORT && !material.isItem()) return false;
        return allowAir || !isAirMaterial(material);
    }

    public static boolean isBlockMaterial(@Nullable Material material, boolean allowAir) {
        if (material == null || isLegacyMaterial(material)) return false;
        if (!material.isBlock()) return false;
        return allowAir || !isAirMaterial(material);
    }

    public static boolean isAirMaterial(@Nullable Material material) {
        return material == null || AIR_MATERIALS.contains(material);
    }

    public static boolean isLegacyMaterial(@Nonnull Material material) {
        return material.name().startsWith("LEGACY_");
    }

    @Nonnull
    public static Set<Material> matchMaterials(@Nonnull Predicate<Material> filter, @Nullable Consumer<String> onFail, @Nonnull String... names) {
        Set<Material> result = new HashSet<>();
        for (String name : names) {
            Material material = Material.matchMaterial(name);
            if (!isAirMaterial(material) && !isLegacyMaterial(material) && filter.test(material)) {
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

                if (!isItemMaterial(iconMaterial, false)) continue;

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

}
