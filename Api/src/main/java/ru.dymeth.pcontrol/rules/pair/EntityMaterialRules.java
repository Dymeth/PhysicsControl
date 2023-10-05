package ru.dymeth.pcontrol.rules.pair;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import ru.dymeth.pcontrol.api.PControlData;

import javax.annotation.Nonnull;

public class EntityMaterialRules extends KeysPairTriggerRules<EntityMaterialRules, EntityType, Material> {
    public EntityMaterialRules(@Nonnull PControlData data) {
        super(data);
    }
}
