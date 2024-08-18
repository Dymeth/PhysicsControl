package ru.dymeth.pcontrol.versionsadapter;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Attachable;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;
import ru.dymeth.pcontrol.VersionsAdapter;
import ru.dymeth.pcontrol.data.PControlData;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class VersionsAdapter_1_8_to_1_12_2 implements VersionsAdapter {

    final Map<Material, Boolean> underwaterMaterials; // isWaterlogged

    public VersionsAdapter_1_8_to_1_12_2(@Nonnull PControlData data) {
        this.underwaterMaterials = new HashMap<>();
        for (Material material : data.getCustomTags().getTag("blocks_under_water_only", Material.class)) {
            this.underwaterMaterials.put(material, false);
        }
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
        return this.underwaterMaterials.containsKey(block.getType());
    }

    @Override
    public boolean isFacingAt(@Nonnull Block block, @Nonnull BlockFace face) {
        MaterialData data = block.getState().getData();
        return !(data instanceof Attachable) || ((Attachable) data).getFacing() == face;
    }

    @Override
    public void setItemMetaGlowing(@Nonnull ItemMeta meta) {
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
    }
}
