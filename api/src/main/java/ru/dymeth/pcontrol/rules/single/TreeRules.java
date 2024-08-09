package ru.dymeth.pcontrol.rules.single;

import org.bukkit.TreeType;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.set.TreeTypesSet;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class TreeRules extends SingleKeyTriggerRules<TreeRules, TreeType> {
    public TreeRules(@Nonnull PControlData data) {
        super(data);
    }

    @Nonnull
    public TreeRules reg(@Nonnull PControlTrigger trigger,
                         @Nonnull Iterable<TreeType> keysSet
    ) {
        return this.regSingle(trigger, keysSet);
    }

    @Nonnull
    public TreeRules reg(@Nonnull PControlTrigger trigger,
                         @Nonnull Consumer<TreeTypesSet> keysSet
    ) {
        return this.regSingle(trigger, this.treesSet(trigger, keysSet));
    }
}
