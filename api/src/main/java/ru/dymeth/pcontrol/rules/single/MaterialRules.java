package ru.dymeth.pcontrol.rules.single;

import org.bukkit.Material;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.set.material.BlockTypesSet;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class MaterialRules extends SingleKeyTriggerRules<MaterialRules, Material> {
    public MaterialRules(@Nonnull PControlData data) {
        super(data);
    }

    @Nonnull
    public MaterialRules reg(@Nonnull PControlTrigger trigger,
                             @Nonnull Iterable<Material> keysSet
    ) {
        return this.regSingle(trigger, keysSet);
    }

    @Nonnull
    public MaterialRules reg(@Nonnull PControlTrigger trigger,
                             @Nonnull Consumer<BlockTypesSet> keysSet
    ) {
        return this.regSingle(trigger, this.blocksSet(true, trigger, keysSet));
    }
}
