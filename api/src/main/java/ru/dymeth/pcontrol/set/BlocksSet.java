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

public final class BlocksSet extends CustomEnumSet<Material, PCMaterial> {

    @Nonnull
    public static Set<Material> createPrimitive(@Nonnull String setName, @Nonnull Logger logger, @Nonnull Consumer<BlocksSet> consumer) {
        return create(setName, logger, consumer)
            .stream()
            .map(PCMaterial::getType)
            .collect(Collectors.toSet());
    }

    @Nonnull
    public static Set<PCMaterial> create(@Nonnull String setName, @Nonnull Logger logger, @Nonnull Consumer<BlocksSet> consumer) {
        BlocksSet result = new BlocksSet();
        try {
            consumer.accept(result);
        } catch (NoSuchFieldError e) {
            String materialName = e.getMessage();
            logger.warning("Unable to fill set " + setName + ". " +
                "Block " + materialName + " not found. Plugin may not work correctly");
        } catch (NullPointerException e) {
            String tagName = e.getMessage();
            if (tagName != null && tagName.startsWith("Cannot invoke \"org.bukkit.Tag.getValues()\" because \"org.bukkit.Tag.")) {
                tagName = tagName.substring(
                    "Cannot invoke \"org.bukkit.Tag.getValues()\" because \"org.bukkit.Tag.".length(),
                    tagName.length() - "\" is null".length()
                );
            } else {
                tagName = null;
            }
            if (tagName == null) {
                logger.warning("Unable to fill set " + setName + ". " +
                    "Unknown error occurred. Plugin may not work correctly. Stack:");
                e.printStackTrace();
            } else {
                logger.warning("Unable to fill set " + setName + ". " +
                    "Tag " + tagName + " not found. Plugin may not work correctly");
            }
        } catch (Throwable t) {
            logger.warning("Unable to fill set " + setName + ". " +
                "Unknown error occurred. Plugin may not work correctly. Stack:");
            t.printStackTrace();
        }
        return Collections.unmodifiableSet(result.getValues());
    }

    private BlocksSet() {
        super(Material.class);
    }

    @Nonnull
    @Override
    public CustomEnumSet<Material, PCMaterial> add(@Nonnull Predicate<Material> filter) {
        return super.add(material -> MaterialUtils.isBlockMaterial(material, true) && filter.test(material));
    }

    @Nonnull
    public CustomSet<PCMaterial> add(@Nonnull Collection<PCMaterial> elements) {
        for (PCMaterial element : elements) {
            if (element.isBlockMaterial(true)) continue;
            throw new IllegalArgumentException("Unable to add non-block material to blocks set");
        }
        return super.add(elements);
    }

    @Nonnull
    @Override
    public BlocksSet add(@Nonnull String... elementNames) {
        this.add(MaterialUtils.getBlockMaterials(null, elementNames));
        return this;
    }

    @Nonnull
    @Override
    public PCMaterial enumToElement(@Nonnull Material enumValue, @Nullable String elementName) {
        return elementName == null ? new PCMaterial(enumValue, null) : PCMaterial.valueOf(elementName);
    }
}
