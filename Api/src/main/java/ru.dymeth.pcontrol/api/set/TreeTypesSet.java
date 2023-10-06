package ru.dymeth.pcontrol.api.set;

import org.bukkit.TreeType;
import ru.dymeth.pcontrol.api.PControlData;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;

public final class TreeTypesSet extends CustomEnumSet<TreeType> {

    @Nonnull
    public static Set<TreeType> create(@Nonnull String setName, @Nonnull PControlData data, @Nonnull Consumer<TreeTypesSet> consumer) {
        TreeTypesSet result = new TreeTypesSet();
        try {
            consumer.accept(result);
        } catch (NoSuchFieldError e) {
            data.getPlugin().getLogger().warning("Unable to fill set " + setName + ". " +
                "Tree type " + e.getMessage() + " not found. Plugin may not work correctly");
        }
        return Collections.unmodifiableSet(result.getValues());
    }

    private TreeTypesSet(@Nonnull TreeType... elements) {
        super(TreeType.class, elements);
    }
}
