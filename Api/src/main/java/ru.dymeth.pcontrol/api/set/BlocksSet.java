package ru.dymeth.pcontrol.api.set;

import org.bukkit.Material;
import ru.dymeth.pcontrol.api.BukkitUtils;
import ru.dymeth.pcontrol.api.PControlData;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class BlocksSet extends CustomEnumSet<Material> {

    @Nonnull
    public static Set<Material> create(@Nonnull String setName, @Nonnull PControlData data, @Nonnull Consumer<BlocksSet> consumer) {
        BlocksSet result = new BlocksSet();
        try {
            consumer.accept(result);
        } catch (NoSuchFieldError e) {
            data.getPlugin().getLogger().warning("Unable to fill set " + setName + ". " +
                "Block " + e.getMessage() + " not found. Plugin may not work correctly");
        }
        return Collections.unmodifiableSet(result.getValues());
    }

    private BlocksSet() {
        super(Material.class);
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
