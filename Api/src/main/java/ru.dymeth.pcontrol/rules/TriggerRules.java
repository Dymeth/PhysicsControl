package ru.dymeth.pcontrol.rules;

import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.PControlTrigger;

import javax.annotation.Nonnull;

public abstract class TriggerRules<T> {

    public static final boolean LOG_SUPPORTED_TRIGGERS = false;
    private static int TOTAL_RULES_REGISTERED = 0;

    public static int getTotalRulesRegistered() {
        return TOTAL_RULES_REGISTERED;
    }

    protected final PControlData data;

    protected TriggerRules(@Nonnull PControlData data) {
        this.data = data;
    }

    @Nonnull
    protected T completeRegistration(@Nonnull PControlTrigger trigger, int rulesAdded) {
        if (rulesAdded > 0) {
            TOTAL_RULES_REGISTERED += rulesAdded;
            trigger.markAvailable();
            if (LOG_SUPPORTED_TRIGGERS) {
                this.data.getPlugin().getLogger().info("Trigger " + trigger
                    + " registered " + rulesAdded + " rules");
            }
        } else {
            this.data.getPlugin().getLogger().warning("Trigger " + trigger.name()
                + " is unavailable at current server version");
        }
        if (trigger.isAvailable() != this.data.isTriggerSupported(trigger)) {
            this.data.getPlugin().getLogger().warning("Different trigger availabilities: " + trigger);
        }

        //noinspection unchecked
        return (T) this;
    }
}