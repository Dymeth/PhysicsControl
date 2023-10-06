package ru.dymeth.pcontrol.api.set;

import org.bukkit.entity.EntityType;

import javax.annotation.Nonnull;

public final class EntityTypeKeysSet extends EnumKeysSet<EntityType> {
    public EntityTypeKeysSet(@Nonnull EntityType... elements) {
        super(EntityType.class, elements);
    }
}
