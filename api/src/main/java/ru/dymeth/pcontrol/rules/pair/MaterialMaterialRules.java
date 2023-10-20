package ru.dymeth.pcontrol.rules.pair;

import org.bukkit.Material;
import ru.dymeth.pcontrol.data.PControlData;

import javax.annotation.Nonnull;

public class MaterialMaterialRules extends KeysPairTriggerRules<MaterialMaterialRules, Material, Material> {
    public MaterialMaterialRules(@Nonnull PControlData data) {
        super(data);
    }
}
