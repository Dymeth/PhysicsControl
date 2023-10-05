package ru.dymeth.pcontrol.rules.single;

import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.PControlTrigger;
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
        boolean triggerAvailable = false;
        PControlTrigger previousTrigger;
        for (K1 key : keysSet) {
            previousTrigger = this.singleRules.put(key, trigger);
            if (previousTrigger != null && LOG_TRIGGER_OVERRIDES) {
                this.data.getPlugin().getLogger().warning("Changed trigger for key "
                    + key + ": "
                    + previousTrigger + " -> " + trigger);
            }
            triggerAvailable = true;
        }
        return this.completeRegistration(trigger, triggerAvailable);
    }

    @Nullable
    public PControlTrigger findTrigger(@Nonnull K1 key) {
        return this.singleRules.get(key);
    }
}
