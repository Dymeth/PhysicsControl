package ru.dymeth.pcontrol.rules.single;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Event;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;

import javax.annotation.Nonnull;

public class MaterialRules extends SingleKeyTriggerRules<MaterialRules, Material> {
    private final @Nonnull String configKey;

    public MaterialRules(@Nonnull PControlData data,
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
            this.parseBlockTypes(trigger, section, this.configKey, true)
        );
    }
}
