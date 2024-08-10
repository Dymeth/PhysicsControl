package ru.dymeth.pcontrol.rules.single;

import org.bukkit.TreeType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Event;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.set.TreeTypesSet;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class TreeRules extends SingleKeyTriggerRules<TreeRules, TreeType> {
    private final @Nonnull String configKey;

    public TreeRules(@Nonnull PControlData data,
                     @Nonnull Class<? extends Event> eventClass,
                     @Nonnull String configKey
    ) {
        super(data, eventClass, configKey);
        this.configKey = configKey;
    }

    @Nonnull
    public TreeRules reg(@Nonnull PControlTrigger trigger,
                         @Nonnull Iterable<TreeType> keysSet
    ) {
        return this.regSingle(
            trigger,
            keysSet
        );
    }

    @Nonnull
    public TreeRules reg(@Nonnull PControlTrigger trigger,
                         @Nonnull Consumer<TreeTypesSet> keysSet
    ) {
        return this.regSingle(
            trigger,
            this.loadTreeTypes(trigger, keysSet)
        );
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
