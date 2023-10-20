package ru.dymeth.pcontrol.set;

import org.bukkit.entity.EntityType;
import ru.dymeth.pcontrol.data.PControlData;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public final class EntityTypesSet extends CustomEnumSet<EntityType> {

    @Nonnull
    public static Set<EntityType> create(@Nonnull String setName, @Nonnull PControlData data, @Nonnull Consumer<EntityTypesSet> consumer) {
        EntityTypesSet result = new EntityTypesSet();
        try {
            consumer.accept(result);
        } catch (NoSuchFieldError e) {
            data.getPlugin().getLogger().warning("Unable to fill set " + setName + ". " +
                "Entity type " + e.getMessage() + " not found. Plugin may not work correctly");
        }
        return Collections.unmodifiableSet(result.getValues());
    }

    private EntityTypesSet(@Nonnull EntityType... elements) {
        super(EntityType.class, elements);
    }
}
