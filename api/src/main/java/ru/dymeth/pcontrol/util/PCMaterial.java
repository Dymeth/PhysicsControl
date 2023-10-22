package ru.dymeth.pcontrol.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class PCMaterial {
    private static final boolean LEGACY_MODE = ReflectionUtils.isConstructorPresent(ItemStack.class, int.class);

    @Nullable
    public static PCMaterial getMaterial(@Nonnull String name) {
        Byte data;
        int damageSplitter = name.indexOf(':');
        if (damageSplitter >= 0) {
            data = Byte.parseByte(name.substring(damageSplitter + 1));
            name = name.substring(0, damageSplitter);
        } else {
            data = null;
        }
        Material result = Material.getMaterial(name);

        if (result == null) return null;

        if (data == null) {
            return new PCMaterial(result, null);
        } else {
            return new PCMaterial(result, data);
        }
    }

    @Nonnull
    public static PCMaterial valueOf(@Nonnull String name) {
        PCMaterial result = getMaterial(name);
        if (result == null) throw new IllegalArgumentException(name);
        return result;
    }

    @Nonnull
    public static PCMaterial ofLegacyOrModern(@Nonnull String legacyName, @Nonnull String modernName) {
        String name = LEGACY_MODE ? legacyName : modernName;
        PCMaterial result = getMaterial(name);
        if (result == null) {
            new IllegalArgumentException(
                "Unable to find " + (LEGACY_MODE ? "legacy" : "modern") + " material " + name + ", " +
                    "trying to use BARRIER..."
            ).printStackTrace();
            result = new PCMaterial(Material.BARRIER, null);
        }
        return result;
    }

    private final Material material;
    private final Byte data;
    private final int hashCode;
    private final String toString;

    public PCMaterial(@Nonnull Material material, @Nullable Byte data) {
        this.material = material;

        if (data != null && !LEGACY_MODE) {
            new IllegalArgumentException("Data is unsupported in modern mode").printStackTrace();
            data = null;
        }
        this.data = data;

        this.hashCode = this.material.ordinal() * (Byte.MAX_VALUE + 1) + (this.data == null ? Byte.MAX_VALUE : this.data);

        if (this.data != null) {
            this.toString = this.material.name() + ":" + this.data;
        } else if (LEGACY_MODE) {
            this.toString = this.material.name() + ":" + "*";
        } else {
            this.toString = this.material.name();
        }
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public boolean equals(@Nonnull Object other) {
        if (other instanceof PCMaterial) return equals((PCMaterial) other);
        if (other instanceof Material) return equals((Material) other);
        if (other instanceof Block) return equals((Block) other);
        if (other instanceof ItemStack) return equals((ItemStack) other);
        return false;
    }

    public boolean equals(@Nonnull PCMaterial other) {
        return other.material == this.material && Objects.equals(other.data, this.data);
    }

    public boolean equals(@Nonnull Material material) {
        return this.material == material;
    }

    public boolean equals(@Nonnull Block block) {
        if (block.getType() != this.material) return false;
        if (this.data == null) return true;
        return this.data == LegacyMaterialsHelper.getBlockData(block);
    }

    public boolean equals(@Nonnull ItemStack stack) {
        if (stack.getType() != this.material) return false;
        if (this.data == null) return true;
        return ((short) this.data) == LegacyMaterialsHelper.getStackDurability(stack);
    }

    @Override
    public String toString() {
        return this.toString;
    }

    @Nonnull
    public ItemStack createStack(int amount) {
        if (this.data == null) {
            return new ItemStack(this.material, amount);
        } else {
            return LegacyMaterialsHelper.createStack(this.material, amount, this.data);
        }
    }

    public boolean isItemMaterial(boolean allowAir) {
        return MaterialUtils.isItemMaterial(this.material, allowAir);
    }

    public boolean isBlockMaterial(boolean allowAir) {
        return MaterialUtils.isBlockMaterial(this.material, allowAir);
    }

    public boolean isAirMaterial() {
        return MaterialUtils.isAirMaterial(this.material);
    }

    public boolean isLegacyMaterial() {
        return MaterialUtils.isLegacyMaterial(this.material);
    }

    @Nonnull
    public Material getType() {
        return this.material;
    }
}
