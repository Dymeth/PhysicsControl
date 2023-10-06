package ru.dymeth.pcontrol.api.set;

import org.bukkit.Material;
import ru.dymeth.pcontrol.api.BukkitUtils;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public final class ItemsSet extends CustomEnumSet<Material> {
    public ItemsSet(@Nonnull Material... elements) {
        super(Material.class, elements);
    }

    @Nonnull
    @Override
    public CustomEnumSet<Material> add(@Nonnull Predicate<Material> filter) {
        return super.add(material -> material.isItem() && !material.name().startsWith("LEGACY_") && filter.test(material));
    }

    @Nonnull
    public ItemsSet add(@Nonnull String... itemNames) {
        this.elements.addAll(BukkitUtils.matchItemMaterials(null, itemNames));
        return this;
    }
}