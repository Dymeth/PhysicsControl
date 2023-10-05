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
        return super.add(material -> filter.test(material) && !material.name().startsWith("LEGACY_"));
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
