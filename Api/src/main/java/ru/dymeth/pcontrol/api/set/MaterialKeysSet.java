package ru.dymeth.pcontrol.api.set;

import org.bukkit.Material;
import ru.dymeth.pcontrol.api.BukkitUtils;

import javax.annotation.Nonnull;

public final class MaterialKeysSet extends EnumKeysSet<Material> {
    public MaterialKeysSet(@Nonnull Material... elements) {
        super(Material.class, elements);
    }

    @Nonnull
    public MaterialKeysSet addBlocks(@Nonnull String... materialNames) {
        this.elements.addAll(BukkitUtils.matchBlockMaterials(null, materialNames));
        return this;
    }

    @Nonnull
    public MaterialKeysSet addItems(@Nonnull String... materialNames) {
        this.elements.addAll(BukkitUtils.matchItemMaterials(null, materialNames));
        return this;
    }
}
