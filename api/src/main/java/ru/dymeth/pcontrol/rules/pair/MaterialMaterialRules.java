package ru.dymeth.pcontrol.rules.pair;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Event;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.set.material.BlockTypesSet;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class MaterialMaterialRules extends KeysPairTriggerRules<MaterialMaterialRules, Material, Material> {
    private final @Nonnull String configKey1;
    private final @Nonnull String configKey2;

    public MaterialMaterialRules(@Nonnull PControlData data,
                                 @Nonnull Class<? extends Event> eventClass,
                                 @Nonnull String configKey1,
                                 @Nonnull String configKey2
    ) {
        super(data, eventClass, configKey1, configKey2);
        this.configKey1 = configKey1;
        this.configKey2 = configKey2;
    }

    @Nonnull
    public MaterialMaterialRules reg(@Nonnull PControlTrigger trigger,
                                     @Nonnull Iterable<Material> firstKeysSet,
                                     @Nonnull Iterable<Material> secondKeysSet
    ) {
        return this.regPair(
            trigger,
            firstKeysSet,
            secondKeysSet
        );
    }

    @Nonnull
    public MaterialMaterialRules reg(@Nonnull PControlTrigger trigger,
                                     @Nonnull Iterable<Material> firstKeysSet,
                                     @Nonnull Consumer<BlockTypesSet> secondKeysSet
    ) {
        return this.regPair(
            trigger,
            firstKeysSet,
            this.loadBlockTypes(trigger, secondKeysSet, true)
        );
    }

    @Nonnull
    public MaterialMaterialRules reg(@Nonnull PControlTrigger trigger,
                                     @Nonnull Consumer<BlockTypesSet> firstKeysSet,
                                     @Nonnull Iterable<Material> secondKeysSet
    ) {
        return this.regPair(
            trigger,
            this.loadBlockTypes(trigger, firstKeysSet, true),
            secondKeysSet
        );
    }

    @Nonnull
    public MaterialMaterialRules reg(@Nonnull PControlTrigger trigger,
                                     @Nonnull Consumer<BlockTypesSet> firstKeysSet,
                                     @Nonnull Consumer<BlockTypesSet> secondKeysSet
    ) {
        return this.regPair(
            trigger,
            this.loadBlockTypes(trigger, firstKeysSet, true),
            this.loadBlockTypes(trigger, secondKeysSet, true)
        );
    }

    @Override
    public void parse(@Nonnull ConfigurationSection section) {
        PControlTrigger trigger = this.parseTrigger(section);
        this.regPair(
            trigger,
            this.parseBlockTypes(trigger, section, this.configKey1, true),
            this.parseBlockTypes(trigger, section, this.configKey2, true)
        );
    }
}
