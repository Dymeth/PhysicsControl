package ru.dymeth.pcontrol.versionsadapter;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;
import ru.dymeth.pcontrol.data.PControlData;

import javax.annotation.Nonnull;

public class VersionsAdapter_1_13_to_1_20_4 extends VersionsAdapter_1_8_to_1_12_2 {
    public VersionsAdapter_1_13_to_1_20_4(@Nonnull PControlData data) {
        super(data);

        for (Material material : Material.values()) {
            if (!material.isBlock()) continue;
            if (!(material.createBlockData() instanceof Waterlogged)) continue;
            this.underwaterMaterials.put(material, true);
        }
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
        Boolean waterlogged = this.underwaterMaterials.get(block.getType());
        if (waterlogged == null) return false;
        return !waterlogged || ((Waterlogged) block.getBlockData()).isWaterlogged();
    }

    @Override
    public boolean isFacingAt(@Nonnull Block block, @Nonnull BlockFace face) {
        BlockData data = block.getBlockData();
        return !(data instanceof Directional) || ((Directional) data).getFacing() == face;
    }
}
