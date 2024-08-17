package ru.dymeth.pcontrol.rules.single;

import org.bukkit.TreeType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Event;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;

import javax.annotation.Nonnull;

public class TreeRules extends SingleKeyTriggerRules<TreeRules, TreeType> {
    private final @Nonnull String configKey;

    public TreeRules(@Nonnull PControlData data,
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
            this.parseTreeTypes(trigger, section, this.configKey)
        );
    }
}
