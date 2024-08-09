package ru.dymeth.pcontrol.rules.single;

import org.bukkit.entity.EntityType;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.set.EntityTypesSet;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class EntityRules extends SingleKeyTriggerRules<EntityRules, EntityType> {
    public EntityRules(@Nonnull PControlData data) {
        super(data);
    }

    @Nonnull
    public EntityRules reg(@Nonnull PControlTrigger trigger,
                           @Nonnull Iterable<EntityType> keysSet
    ) {
        return this.regSingle(trigger, keysSet);
    }

    @Nonnull
    public EntityRules reg(@Nonnull PControlTrigger trigger,
                           @Nonnull Consumer<EntityTypesSet> keysSet
    ) {
        return this.regSingle(trigger, this.entitiesSet(trigger, keysSet));
    }
}
