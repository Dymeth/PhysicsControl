package ru.dymeth.pcontrol.rules.single;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.set.EntityTypesSet;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class EntityRules extends SingleKeyTriggerRules<EntityRules, EntityType> {
    private final @Nonnull String configKey;

    public EntityRules(@Nonnull PControlData data, @Nonnull String configKey) {
        super(data, configKey);
        this.configKey = configKey;
    }

    @Nonnull
    public EntityRules reg(@Nonnull PControlTrigger trigger,
                           @Nonnull Iterable<EntityType> keysSet
    ) {
        return this.regSingle(
            trigger,
            keysSet
        );
    }

    @Nonnull
    public EntityRules reg(@Nonnull PControlTrigger trigger,
                           @Nonnull Consumer<EntityTypesSet> keysSet
    ) {
        return this.regSingle(
            trigger,
            this.loadEntityTypes(trigger, keysSet)
        );
    }

    @Override
    public void parse(@Nonnull ConfigurationSection section) {
        this.regSingle(
            this.parseTrigger(section),
            this.parseEntityTypes(section, this.configKey)
        );
    }
}
