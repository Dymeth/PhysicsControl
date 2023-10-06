package ru.dymeth.pcontrol.api.set;

import org.bukkit.Material;
import ru.dymeth.pcontrol.api.BukkitUtils;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public final class BlocksSet extends CustomEnumSet<Material> {
    public BlocksSet(@Nonnull Material... elements) {
        super(Material.class, elements);
    }

    @Nonnull
    @Override
    public CustomEnumSet<Material> add(@Nonnull Predicate<Material> filter) {
        return super.add(material -> material.isBlock() && !material.name().startsWith("LEGACY_") && filter.test(material));
    }

    @Nonnull
    @Override
    public BlocksSet add(@Nonnull String... elementNames) {
        this.add(BukkitUtils.matchBlockMaterials(null, elementNames));
        return this;
    }
}
