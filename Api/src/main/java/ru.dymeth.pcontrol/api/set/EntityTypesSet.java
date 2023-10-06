package ru.dymeth.pcontrol.api.set;

import org.bukkit.entity.EntityType;

import javax.annotation.Nonnull;

public final class EntityTypesSet extends CustomEnumSet<EntityType> {
    public EntityTypesSet(@Nonnull EntityType... elements) {
        super(EntityType.class, elements);
    }
}
