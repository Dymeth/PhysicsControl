package ru.dymeth.pcontrol.versionsadapter;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Attachable;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;
import ru.dymeth.pcontrol.VersionsAdapter;
import ru.dymeth.pcontrol.data.CustomTags;
import ru.dymeth.pcontrol.data.PControlData;

import javax.annotation.Nonnull;

public class VersionsAdapterLegacy implements VersionsAdapter {

    private final CustomTags tags;

    public VersionsAdapterLegacy(@Nonnull PControlData data) {
        this.tags = data.tags();
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

    @Override
    public boolean isFacingAt(@Nonnull Block block, @Nonnull BlockFace face) {
        MaterialData data = block.getState().getData();
        return !(data instanceof Attachable) || ((Attachable) data).getFacing() == face;
    }

}
