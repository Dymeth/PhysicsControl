package ru.dymeth.pcontrol.rules.pair;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.set.EntityTypesSet;
import ru.dymeth.pcontrol.set.material.BlockTypesSet;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class EntityMaterialRules extends KeysPairTriggerRules<EntityMaterialRules, EntityType, Material> {
    private final @Nonnull String configKey1;
    private final @Nonnull String configKey2;

    public EntityMaterialRules(@Nonnull PControlData data, @Nonnull String configKey1, @Nonnull String configKey2) {
        super(data, configKey1, configKey2);
        this.configKey1 = configKey1;
        this.configKey2 = configKey2;
    }

    @Nonnull
    public EntityMaterialRules reg(@Nonnull PControlTrigger trigger,
                                   @Nonnull Iterable<EntityType> firstKeysSet,
                                   @Nonnull Iterable<Material> secondKeysSet
    ) {
        return this.regPair(
            trigger,
            firstKeysSet,
            secondKeysSet
        );
    }

    @Nonnull
    public EntityMaterialRules reg(@Nonnull PControlTrigger trigger,
                                   @Nonnull Iterable<EntityType> firstKeysSet,
                                   @Nonnull Consumer<BlockTypesSet> secondKeysSet
    ) {
        return this.regPair(
            trigger,
            firstKeysSet,
            this.loadBlockTypes(trigger, secondKeysSet, true)
        );
    }

    @Nonnull
    public EntityMaterialRules reg(@Nonnull PControlTrigger trigger,
                                   @Nonnull Consumer<EntityTypesSet> firstKeysSet,
                                   @Nonnull Iterable<Material> secondKeysSet
    ) {
        return this.regPair(
            trigger,
            this.loadEntityTypes(trigger, firstKeysSet),
            secondKeysSet
        );
    }

    @Nonnull
    public EntityMaterialRules reg(@Nonnull PControlTrigger trigger,
                                   @Nonnull Consumer<EntityTypesSet> firstKeysSet,
                                   @Nonnull Consumer<BlockTypesSet> secondKeysSet
    ) {
        return this.regPair(
            trigger,
            this.loadEntityTypes(trigger, firstKeysSet),
            this.loadBlockTypes(trigger, secondKeysSet, true)
        );
    }

    @Override
    public void parse(@Nonnull ConfigurationSection section) {
        PControlTrigger trigger = this.parseTrigger(section);
        this.regPair(
            trigger,
            this.parseEntityTypes(trigger, section, this.configKey1),
            this.parseBlockTypes(trigger, section, this.configKey2, true)
        );
    }
}
