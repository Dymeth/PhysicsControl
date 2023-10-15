package ru.dymeth.pcontrol.api.set;

import org.bukkit.Material;
import ru.dymeth.pcontrol.api.MaterialUtils;
import ru.dymeth.pcontrol.api.PControlData;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public final class ItemsSet extends CustomEnumSet<Material> {

    @Nonnull
    public static Set<Material> create(@Nonnull String setName, @Nonnull PControlData data, @Nonnull Consumer<ItemsSet> consumer) {
        ItemsSet result = new ItemsSet();
        try {
            consumer.accept(result);
        } catch (NoSuchFieldError e) {
            data.getPlugin().getLogger().warning("Unable to fill set " + setName + ". " +
                "Item " + e.getMessage() + " not found. Plugin may not work correctly");
        }
        return Collections.unmodifiableSet(result.getValues());
    }

    private ItemsSet(@Nonnull Material... elements) {
        super(Material.class, elements);
    }

    @Nonnull
    @Override
    public CustomEnumSet<Material> add(@Nonnull Predicate<Material> filter) {
        return super.add(material -> MaterialUtils.isItemMaterial(material, true) && filter.test(material));
    }

    @Nonnull
    @Override
    public ItemsSet add(@Nonnull String... elementNames) {
        this.add(MaterialUtils.matchItemMaterials(null, elementNames));
        return this;
    }
}
