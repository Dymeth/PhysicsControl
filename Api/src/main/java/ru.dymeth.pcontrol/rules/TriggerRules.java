package ru.dymeth.pcontrol.rules;

import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.PControlTrigger;

import javax.annotation.Nonnull;

public abstract class TriggerRules<T> {

    private static final boolean LOG_SUPPORTED_TRIGGERS = false;
    private static final boolean LOG_UNSUPPORTED_TRIGGERS = false;
    protected static final boolean LOG_TRIGGER_OVERRIDES = false;

    protected final PControlData data;

    protected TriggerRules(@Nonnull PControlData data) {
        this.data = data;
    }

    @Nonnull
    protected T completeRegistration(@Nonnull PControlTrigger trigger, boolean triggerAvailable) {
        if (triggerAvailable) {
            trigger.markAvailable();
        }
        if (trigger.isAvailable() != this.data.isTriggerSupported(trigger)) {
            throw new IllegalStateException("Different trigger availabilities: " + trigger);
        }

        if (triggerAvailable) {
            if (LOG_SUPPORTED_TRIGGERS) {
                this.data.getPlugin().getLogger().info("Trigger " + trigger
                    + " registered successfully");
            }
        } else {
            if (LOG_UNSUPPORTED_TRIGGERS) {
                this.data.getPlugin().getLogger().warning("Trigger " + trigger.name()
                    + " is unavailable at current server version");
            }
        }

        //noinspection unchecked
        return (T) this;
    }
}
