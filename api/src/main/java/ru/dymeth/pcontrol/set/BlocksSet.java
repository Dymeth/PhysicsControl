package ru.dymeth.pcontrol.set;

import org.bukkit.Material;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.util.MaterialUtils;

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
            String materialName = e.getMessage();
            data.getPlugin().getLogger().warning("Unable to fill set " + setName + ". " +
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
                data.getPlugin().getLogger().warning("Unable to fill set " + setName + ". " +
                    "Unknown error occurred. Plugin may not work correctly. Stack:");
                e.printStackTrace();
            } else {
                data.getPlugin().getLogger().warning("Unable to fill set " + setName + ". " +
                    "Tag " + tagName + " not found. Plugin may not work correctly");
            }
        } catch (Throwable t) {
            data.getPlugin().getLogger().warning("Unable to fill set " + setName + ". " +
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
    public CustomEnumSet<Material> add(@Nonnull Predicate<Material> filter) {
        return super.add(material -> MaterialUtils.isBlockMaterial(material, true) && filter.test(material));
    }

    @Nonnull
    @Override
    public BlocksSet add(@Nonnull String... elementNames) {
        this.add(MaterialUtils.matchBlockMaterials(null, elementNames));
        return this;
    }
}
