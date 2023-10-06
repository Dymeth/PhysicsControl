package ru.dymeth.pcontrol.legacy;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;
import ru.dymeth.pcontrol.CustomTags;
import ru.dymeth.pcontrol.VersionsAdapter;
import ru.dymeth.pcontrol.api.PControlData;

import javax.annotation.Nonnull;

public class VersionsAdapterLegacy implements VersionsAdapter {

    private final CustomTags tags;

    public VersionsAdapterLegacy(@Nonnull PControlData data) {
        this.tags = data.getCustomTags();
    }

    @Nonnull
    @Override
    public Material getFallingBlockMaterial(@Nonnull FallingBlock fallingBlock) {
        return fallingBlock.getMaterial();
    }

    @Override
    public boolean isBoneMealItem(@Nonnull ItemStack stack) {
        return stack.getData() instanceof Dye && ((Dye) stack.getData()).getColor() == DyeColor.WHITE;
    }

    @Override
    public boolean isBlockContainsWater(@Nonnull Block block) {
        return this.tags.BLOCKS_UNDER_WATER_ONLY.contains(block.getType());
    }

}
