package ru.dymeth.pcontrol.api.set;

import org.bukkit.Material;
import ru.dymeth.pcontrol.api.BukkitUtils;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public final class MaterialKeysSet extends EnumKeysSet<Material> {
    public MaterialKeysSet(@Nonnull Material... elements) {
        super(Material.class, elements);
    }

    @Nonnull
    @Override
    public EnumKeysSet<Material> add(@Nonnull Predicate<Material> filter) {
        return super.add(material -> material.isBlock() && !material.name().startsWith("LEGACY_") && filter.test(material));
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
