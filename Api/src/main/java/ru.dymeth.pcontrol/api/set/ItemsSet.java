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
    @Override
    public ItemsSet add(@Nonnull String... elementNames) {
        this.add(BukkitUtils.matchItemMaterials(null, elementNames));
        return this;
    }
}
