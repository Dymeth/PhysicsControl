package ru.dymeth.pcontrol.rules;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.set.EntityTypesSet;
import ru.dymeth.pcontrol.set.TreeTypesSet;
import ru.dymeth.pcontrol.set.material.BlockTypesSet;
import ru.dymeth.pcontrol.set.material.ItemTypesSet;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public abstract class TriggerRules<T> {

    public static final boolean LOG_TRIGGERS_REGISTRATIONS = false;
    private static int TOTAL_RULES_REGISTERED = 0;

    public static int getTotalRulesRegistered() {
        return TOTAL_RULES_REGISTERED;
    }

    protected final PControlData data;
    private final @Nonnull List<String> parameterNames;

    protected TriggerRules(@Nonnull PControlData data, @Nonnull String... parameterNames) {
        this.data = data;
        this.parameterNames = Arrays.asList(parameterNames);
    }

    @Nonnull
    public List<String> getParameterNames() {
        return this.parameterNames;
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
    protected Set<Material> loadBlockTypes(@Nonnull PControlTrigger trigger, @Nonnull Consumer<BlockTypesSet> consumer, boolean allowAir) {
        return BlockTypesSet.createPrimitive(
            allowAir,
            trigger + " trigger",
            this.data.log(),
            consumer
        );
    }

    @Nonnull
    protected Set<Material> parseBlockTypes(@Nonnull ConfigurationSection section, @Nonnull String configKey, boolean allowAir) {
        return BlockTypesSet.createPrimitive(
            allowAir,
            "config list \"" + configKey + "\"",
            this.data.log(),
            this.data.getTypesSetsParser().createBlockTypesParser(listOf(section, configKey), false)
        );
    }

    @Nonnull
    protected Set<EntityType> loadEntityTypes(@Nonnull PControlTrigger trigger, @Nonnull Consumer<EntityTypesSet> consumer) {
        return EntityTypesSet.createPrimitive(
            trigger + " trigger",
            this.data.log(),
            consumer
        );
    }

    @Nonnull
    protected Set<EntityType> parseEntityTypes(@Nonnull ConfigurationSection section, @Nonnull String configKey) {
        return EntityTypesSet.createPrimitive(
            "config list \"" + configKey + "\"",
            this.data.log(),
            this.data.getTypesSetsParser().createEntityTypesParser(listOf(section, configKey), false)
        );
    }

    @Nonnull
    protected Set<Material> loadItemTypes(@Nonnull PControlTrigger trigger, @Nonnull Consumer<ItemTypesSet> consumer, boolean allowAir) {
        return ItemTypesSet.createPrimitive(
            allowAir,
            trigger + " trigger",
            this.data.log(),
            consumer
        );
    }

    @Nonnull
    protected Set<Material> parseItemTypes(@Nonnull ConfigurationSection section, @Nonnull String configKey, boolean allowAir) {
        return ItemTypesSet.createPrimitive(
            allowAir,
            "config list \"" + configKey + "\"",
            this.data.log(),
            this.data.getTypesSetsParser().createItemTypesParser(listOf(section, configKey), false)
        );
    }

    @Nonnull
    protected Set<TreeType> loadTreeTypes(@Nonnull PControlTrigger trigger, @Nonnull Consumer<TreeTypesSet> consumer) {
        return TreeTypesSet.createPrimitive(
            trigger + " trigger",
            this.data.log(),
            consumer
        );
    }

    @Nonnull
    protected Set<TreeType> parseTreeTypes(@Nonnull ConfigurationSection section, @Nonnull String configKey) {
        return TreeTypesSet.createPrimitive(
            "config list \"" + configKey + "\"",
            this.data.log(),
            this.data.getTypesSetsParser().createTreeTypesParser(listOf(section, configKey), false)
        );
    }

    @Nonnull
    private List<String> listOf(@Nonnull ConfigurationSection section, @Nonnull String configKey) {
        if (section.isString(configKey)) {
            return Collections.singletonList(section.getString(configKey));
        }
        if (section.isList(configKey)) {
            List<String> list = section.getStringList(configKey);
            if (list.isEmpty()) throw new IllegalArgumentException("List is empty");
            return list;
        }
        throw new IllegalArgumentException("Not a list or string");
    }

    public abstract void parse(@Nonnull ConfigurationSection section);

    @Nonnull
    protected PControlTrigger parseTrigger(@Nonnull ConfigurationSection section) {
        String triggerName = section.getString("trigger");
        if (triggerName == null) throw new IllegalArgumentException("Trigger name not found");
        return this.data.getTriggersRegisty().valueOf(triggerName);
    }
}
