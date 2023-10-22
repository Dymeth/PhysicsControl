package ru.dymeth.pcontrol.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class LegacyMaterialsHelper {

    private LegacyMaterialsHelper() {
    }

    public static byte getBlockData(@Nonnull Block block) {
        return block.getData();
    }

    public static short getStackDurability(@Nonnull ItemStack stack) {
        return stack.getDurability();
    }

    @Nonnull
    public static ItemStack createStack(@Nonnull Material material, int amount, short durability) {
        return new ItemStack(material, amount, durability);
    }

}
