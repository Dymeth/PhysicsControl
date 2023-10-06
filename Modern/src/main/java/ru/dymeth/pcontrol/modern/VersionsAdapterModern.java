package ru.dymeth.pcontrol.modern;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;
import ru.dymeth.pcontrol.CustomTags;
import ru.dymeth.pcontrol.VersionsAdapter;
import ru.dymeth.pcontrol.api.PControlData;

import javax.annotation.Nonnull;

public class VersionsAdapterModern implements VersionsAdapter {

    private final CustomTags tags;

    public VersionsAdapterModern(@Nonnull PControlData data) {
        this.tags = data.getCustomTags();
    }

    @Nonnull
    @Override
    public Material getFallingBlockMaterial(@Nonnull FallingBlock fallingBlock) {
        return fallingBlock.getBlockData().getMaterial();
    }

    @Override
    public boolean isBoneMealItem(@Nonnull ItemStack stack) {
        return stack.getType() == Material.BONE_MEAL;
    }

    @Override
    public boolean isBlockContainsWater(@Nonnull Block block) {
        return (block.getBlockData() instanceof Waterlogged && ((Waterlogged) block.getBlockData()).isWaterlogged())
            || this.tags.BLOCKS_UNDER_WATER_ONLY.contains(block.getType());
    }

}
