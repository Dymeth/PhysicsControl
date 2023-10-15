package ru.dymeth.pcontrol.rules.pair;

import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.PControlTrigger;
import ru.dymeth.pcontrol.rules.TriggerRules;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public abstract class KeysPairTriggerRules<T, K1, K2> extends TriggerRules<T> {

    private final Map<K1, Map<K2, PControlTrigger>> pairRules = new HashMap<>();

    protected KeysPairTriggerRules(@Nonnull PControlData data) {
        super(data);
    }

    @Nonnull
    public T regPair(@Nonnull PControlTrigger trigger,
                     @Nonnull Iterable<K1> firstKeysSet,
                     @Nonnull Iterable<K2> secondKeysSet
    ) {
        int rulesAdded = 0;
        PControlTrigger previousTrigger;
        for (K1 firstKey : firstKeysSet) {
            Map<K2, PControlTrigger> map = this.pairRules.computeIfAbsent(firstKey, material1 -> new HashMap<>());
            for (K2 secondKey : secondKeysSet) {
                previousTrigger = map.put(secondKey, trigger);
                if (previousTrigger != null) {
                    this.data.getPlugin().getLogger().warning("Changed trigger for keys pair "
                        + firstKey + " and " + secondKey + ": "
                        + previousTrigger + " -> " + trigger);
                }
                rulesAdded++;
            }
        }
        return this.completeRegistration(trigger, rulesAdded);
    }

    @Nullable
    public PControlTrigger findTrigger(@Nonnull K1 firstKey, @Nonnull K2 secondKey) {
        Map<K2, PControlTrigger> map = this.pairRules.get(firstKey);
        return map == null ? null : map.get(secondKey);
    }
}