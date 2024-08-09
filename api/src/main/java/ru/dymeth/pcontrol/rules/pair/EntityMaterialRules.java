package ru.dymeth.pcontrol.rules.pair;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.set.material.BlockTypesSet;
import ru.dymeth.pcontrol.set.EntityTypesSet;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class EntityMaterialRules extends KeysPairTriggerRules<EntityMaterialRules, EntityType, Material> {
    public EntityMaterialRules(@Nonnull PControlData data) {
        super(data);
    }

    @Nonnull
    public EntityMaterialRules reg(@Nonnull PControlTrigger trigger,
                                   @Nonnull Iterable<EntityType> firstKeysSet,
                                   @Nonnull Iterable<Material> secondKeysSet
    ) {
        return this.regPair(trigger, firstKeysSet, secondKeysSet);
    }

    @Nonnull
    public EntityMaterialRules reg(@Nonnull PControlTrigger trigger,
                                   @Nonnull Iterable<EntityType> firstKeysSet,
                                   @Nonnull Consumer<BlockTypesSet> secondKeysSet
    ) {
        return this.regPair(trigger, firstKeysSet, this.blocksSet(true, trigger, secondKeysSet));
    }

    @Nonnull
    public EntityMaterialRules reg(@Nonnull PControlTrigger trigger,
                                   @Nonnull Consumer<EntityTypesSet> firstKeysSet,
                                   @Nonnull Iterable<Material> secondKeysSet
    ) {
        return this.regPair(trigger, this.entitiesSet(trigger, firstKeysSet), secondKeysSet);
    }

    @Nonnull
    public EntityMaterialRules reg(@Nonnull PControlTrigger trigger,
                                   @Nonnull Consumer<EntityTypesSet> firstKeysSet,
                                   @Nonnull Consumer<BlockTypesSet> secondKeysSet
    ) {
        return this.regPair(trigger, this.entitiesSet(trigger, firstKeysSet), this.blocksSet(true, trigger, secondKeysSet));
    }
}
