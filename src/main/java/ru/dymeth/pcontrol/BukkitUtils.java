package ru.dymeth.pcontrol;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class BukkitUtils {
    @Nonnull
    public static Set<Material> matchMaterials(@Nullable Consumer<String> onFail, @Nonnull String... names) {
        Set<Material> result = new HashSet<>();
        for (String name : names) {
            Material element = Material.matchMaterial(name);
            if (element == null) {
                if (onFail != null) onFail.accept(name);
            } else {
                result.add(element);
            }
        }
        return result;
    }

    @SuppressWarnings("deprecation")
    @Nullable
    public static ItemStack matchIcon(@Nonnull String... iconVariants) {
        try {
            Byte data = null;
            Material iconMaterial = null;
            for (String iconVariant : iconVariants) {
                int damageSplitter = iconVariant.indexOf(':');
                if (damageSplitter >= 0) {
                    data = Byte.parseByte(iconVariant.substring(damageSplitter + 1));
                    iconVariant = iconVariant.substring(0, damageSplitter);
                } else {
                    data = null;
                }
                iconMaterial = Material.matchMaterial(iconVariant);
                if (iconMaterial != null) break;
            }
            if (iconMaterial == null) return null;
            if (data == null) return new ItemStack(iconMaterial);
            return new ItemStack(iconMaterial, 1, (short) 0, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
}
