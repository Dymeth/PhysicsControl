package ru.dymeth.pcontrol.rules.single;

import org.bukkit.TreeType;
import ru.dymeth.pcontrol.api.PControlData;

import javax.annotation.Nonnull;

public class TreeRules extends SingleKeyTriggerRules<TreeRules, TreeType> {
    public TreeRules(@Nonnull PControlData data) {
        super(data);
    }
}
