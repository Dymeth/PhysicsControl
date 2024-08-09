package ru.dymeth.pcontrol.rules.pair;

import org.bukkit.Material;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.set.material.BlockTypesSet;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class MaterialMaterialRules extends KeysPairTriggerRules<MaterialMaterialRules, Material, Material> {
    public MaterialMaterialRules(@Nonnull PControlData data) {
        super(data);
    }

    @Nonnull
    public MaterialMaterialRules reg(@Nonnull PControlTrigger trigger,
                                     @Nonnull Iterable<Material> firstKeysSet,
                                     @Nonnull Iterable<Material> secondKeysSet
    ) {
        return this.regPair(trigger, firstKeysSet, secondKeysSet);
    }

    @Nonnull
    public MaterialMaterialRules reg(@Nonnull PControlTrigger trigger,
                                     @Nonnull Iterable<Material> firstKeysSet,
                                     @Nonnull Consumer<BlockTypesSet> secondKeysSet
    ) {
        return this.regPair(trigger, firstKeysSet, this.blocksSet(true, trigger, secondKeysSet));
    }

    @Nonnull
    public MaterialMaterialRules reg(@Nonnull PControlTrigger trigger,
                                     @Nonnull Consumer<BlockTypesSet> firstKeysSet,
                                     @Nonnull Iterable<Material> secondKeysSet
    ) {
        return this.regPair(trigger, this.blocksSet(true, trigger, firstKeysSet), secondKeysSet);
    }

    @Nonnull
    public MaterialMaterialRules reg(@Nonnull PControlTrigger trigger,
                                     @Nonnull Consumer<BlockTypesSet> firstKeysSet,
                                     @Nonnull Consumer<BlockTypesSet> secondKeysSet
    ) {
        return this.regPair(trigger, this.blocksSet(true, trigger, firstKeysSet), this.blocksSet(true, trigger, secondKeysSet));
    }
}
