package ru.dymeth.pcontrol.rules;

import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;

import javax.annotation.Nonnull;

public abstract class TriggerRules<T> {

    public static final boolean LOG_TRIGGERS_REGISTRATIONS = false;
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
            if (LOG_TRIGGERS_REGISTRATIONS) {
                this.data.log().info("Trigger " + trigger
                    + " registered " + rulesAdded + " rules");
            }
        } else {
            this.data.log().warning("Trigger " + trigger + " is unavailable at current server version");
        }

        //noinspection unchecked
        return (T) this;
    }
}
