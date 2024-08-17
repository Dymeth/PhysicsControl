package ru.dymeth.pcontrol.versionsadapter;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.dymeth.pcontrol.VersionsAdapter;
import ru.dymeth.pcontrol.data.PControlData;

import javax.annotation.Nonnull;
import java.util.Set;

public class VersionsAdapter_1_13 implements VersionsAdapter {

    private final Set<Material> blocksUnderWaterOnly;

    public VersionsAdapter_1_13(@Nonnull PControlData data) {
        this.blocksUnderWaterOnly = data.getCustomTags().getTag("blocks_under_water_only", Material.class);
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
            || this.blocksUnderWaterOnly.contains(block.getType());
    }

    @Override
    public boolean isFacingAt(@Nonnull Block block, @Nonnull BlockFace face) {
        BlockData data = block.getBlockData();
        return !(data instanceof Directional) || ((Directional) data).getFacing() == face;
    }

    @Override
    public void setItemMetaGlowing(@Nonnull ItemMeta meta) {
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
    }

}
