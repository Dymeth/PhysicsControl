package ru.dymeth.pcontrol.rules.single;

import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.rules.TriggerRules;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public abstract class SingleKeyTriggerRules<T, K1> extends TriggerRules<T> {

    private final Map<K1, PControlTrigger> singleRules = new HashMap<>();

    protected SingleKeyTriggerRules(@Nonnull PControlData data) {
        super(data);
    }

    @Nonnull
    public T regSingle(@Nonnull PControlTrigger trigger,
                       @Nonnull Iterable<K1> keysSet
    ) {
        int rulesAdded = 0;
        PControlTrigger previousTrigger;
        for (K1 key : keysSet) {
            previousTrigger = this.singleRules.put(key, trigger);
            if (previousTrigger != null) {
                this.data.log().warning("Changed trigger for key "
                    + key + ": "
                    + previousTrigger + " -> " + trigger);
            }
            rulesAdded++;
        }
        return this.completeRegistration(trigger, rulesAdded);
    }

    @Nullable
    public PControlTrigger findTrigger(@Nonnull K1 key) {
        return this.singleRules.get(key);
    }
}
