package ru.dymeth.pcontrol.rules.single;

import org.bukkit.entity.EntityType;
import ru.dymeth.pcontrol.data.PControlData;

import javax.annotation.Nonnull;

public class EntityRules extends SingleKeyTriggerRules<EntityRules, EntityType> {
    public EntityRules(@Nonnull PControlData data) {
        super(data);
    }
}
