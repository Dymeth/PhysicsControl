package ru.dymeth.pcontrol.rules.single;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.set.material.BlockTypesSet;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class MaterialRules extends SingleKeyTriggerRules<MaterialRules, Material> {
    private final @Nonnull String configKey;

    public MaterialRules(@Nonnull PControlData data, @Nonnull String configKey) {
        super(data, configKey);
        this.configKey = configKey;
    }

    @Nonnull
    public MaterialRules reg(@Nonnull PControlTrigger trigger,
                             @Nonnull Iterable<Material> keysSet
    ) {
        return this.regSingle(
            trigger,
            keysSet
        );
    }

    @Nonnull
    public MaterialRules reg(@Nonnull PControlTrigger trigger,
                             @Nonnull Consumer<BlockTypesSet> keysSet
    ) {
        return this.regSingle(
            trigger,
            this.loadBlockTypes(trigger, keysSet, true)
        );
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
