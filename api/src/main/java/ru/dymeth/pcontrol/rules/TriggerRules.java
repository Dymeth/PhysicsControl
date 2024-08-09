package ru.dymeth.pcontrol.rules;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.entity.EntityType;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.set.material.BlockTypesSet;
import ru.dymeth.pcontrol.set.EntityTypesSet;
import ru.dymeth.pcontrol.set.material.ItemTypesSet;
import ru.dymeth.pcontrol.set.TreeTypesSet;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.Consumer;

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
                    + " registered with " + rulesAdded + " " + (rulesAdded == 1 ? "rule" : "rules"));
            }
        } else {
            this.data.log().warning("Trigger " + trigger + " is unavailable at current server version");
        }

        //noinspection unchecked
        return (T) this;
    }

    @Nonnull
    protected Set<Material> blocksSet(boolean allowAir, @Nonnull PControlTrigger trigger, @Nonnull Consumer<BlockTypesSet> consumer) {
        return BlockTypesSet.createPrimitive(allowAir, trigger + " trigger", this.data.log(), consumer);
    }

    @Nonnull
    protected Set<Material> itemsSet(boolean allowAir, @Nonnull PControlTrigger trigger, @Nonnull Consumer<ItemTypesSet> consumer) {
        return ItemTypesSet.createPrimitive(allowAir, trigger + " trigger", this.data.log(), consumer);
    }

    @Nonnull
    protected Set<EntityType> entitiesSet(@Nonnull PControlTrigger trigger, @Nonnull Consumer<EntityTypesSet> consumer) {
        return EntityTypesSet.create(trigger + " trigger", this.data.log(), consumer);
    }

    @Nonnull
    protected Set<TreeType> treesSet(@Nonnull PControlTrigger trigger, @Nonnull Consumer<TreeTypesSet> consumer) {
        return TreeTypesSet.create(trigger + " trigger", this.data.log(), consumer);
    }
}
