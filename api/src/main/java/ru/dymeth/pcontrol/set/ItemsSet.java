package ru.dymeth.pcontrol.set;

import org.bukkit.Material;
import ru.dymeth.pcontrol.util.MaterialUtils;
import ru.dymeth.pcontrol.util.PCMaterial;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public final class ItemsSet extends CustomEnumSet<Material, PCMaterial> {

    @Nonnull
    public static Set<Material> createPrimitive(@Nonnull String setName, @Nonnull Logger logger, @Nonnull Consumer<ItemsSet> consumer) {
        return create(setName, logger, consumer)
            .stream()
            .map(PCMaterial::getType)
            .collect(Collectors.toSet());
    }

    @Nonnull
    public static Set<PCMaterial> create(@Nonnull String setName, @Nonnull Logger logger, @Nonnull Consumer<ItemsSet> consumer) {
        ItemsSet result = new ItemsSet();
        try {
            consumer.accept(result);
        } catch (NoSuchFieldError e) {
            logger.warning("Unable to fill set " + setName + ". " +
                "Item " + e.getMessage() + " not found. Plugin may not work correctly");
        }
        return Collections.unmodifiableSet(result.getValues());
    }

    private ItemsSet() {
        super(Material.class);
    }

    @Nonnull
    @Override
    public CustomEnumSet<Material, PCMaterial> add(@Nonnull Predicate<Material> filter) {
        return super.add(material -> MaterialUtils.isItemMaterial(material, true) && filter.test(material));
    }

    @Nonnull
    public CustomSet<PCMaterial> add(@Nonnull Collection<PCMaterial> elements) {
        for (PCMaterial element : elements) {
            if (element.isItemMaterial(true)) continue;
            throw new IllegalArgumentException("Unable to add non-item material to items set");
        }
        return super.add(elements);
    }

    @Nonnull
    @Override
    public ItemsSet add(@Nonnull String... elementNames) {
        this.add(MaterialUtils.getItemMaterials(null, elementNames));
        return this;
    }

    @Nonnull
    @Override
    public PCMaterial enumToElement(@Nonnull Material enumValue, @Nullable String elementName) {
        return elementName == null ? new PCMaterial(enumValue, null) : PCMaterial.valueOf(elementName);
    }
}
