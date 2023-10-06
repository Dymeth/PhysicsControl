package ru.dymeth.pcontrol;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public interface VersionsAdapter {

    @Nonnull
    Material getFallingBlockMaterial(@Nonnull FallingBlock fallingBlock);

    boolean isBoneMealItem(@Nonnull ItemStack stack);

    boolean isBlockContainsWater(@Nonnull Block block);

}
