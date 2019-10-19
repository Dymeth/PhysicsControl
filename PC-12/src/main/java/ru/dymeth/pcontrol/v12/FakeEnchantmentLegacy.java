package ru.dymeth.pcontrol.v12;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public final class FakeEnchantmentLegacy extends EnchantmentWrapper {
    public static Enchantment getInstance() {
        for (Enchantment e : Enchantment.values()) {
            if (e.getName().equals("_")) {
                return e;
            }
        }
        Enchantment instance = new FakeEnchantmentLegacy();
        try {
            Field e = Enchantment.class.getDeclaredField("acceptingNew");
            e.setAccessible(true);
            e.set(null, true);
            Enchantment.registerEnchantment(instance);
            e.set(null, false);
            e.setAccessible(false);
        } catch (Exception ignored) {
        }
        return instance;
    }

    private FakeEnchantmentLegacy() {
        super(Enchantment.values().length + 1);
    }

    @Override
    public boolean canEnchantItem(@Nonnull ItemStack item) {
        return true;
    }

    @Override
    public boolean conflictsWith(@Nonnull Enchantment other) {
        return false;
    }

    @Nonnull
    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Nonnull
    @Override
    public String getName() {
        return "_";
    }

    @Override
    public int getStartLevel() {
        return 1;
    }
}