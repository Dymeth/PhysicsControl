package ru.dymeth.pcontrol.rules.single;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;

import javax.annotation.Nonnull;

public class EntityRules extends SingleKeyTriggerRules<EntityRules, EntityType> {
    private final @Nonnull String configKey;

    public EntityRules(@Nonnull PControlData data,
                       @Nonnull Class<? extends Event> eventClass,
                       @Nonnull String configKey
    ) {
        super(data, eventClass, configKey);
        this.configKey = configKey;
    }

    @Override
    public void parse(@Nonnull ConfigurationSection section) {
        PControlTrigger trigger = this.parseTrigger(section);
        this.regSingle(
            trigger,
            this.parseEntityTypes(trigger, section, this.configKey)
        );
    }
}
