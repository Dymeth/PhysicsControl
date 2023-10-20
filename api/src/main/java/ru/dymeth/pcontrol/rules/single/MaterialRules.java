package ru.dymeth.pcontrol.rules.single;

import org.bukkit.Material;
import ru.dymeth.pcontrol.data.PControlData;

import javax.annotation.Nonnull;

public class MaterialRules extends SingleKeyTriggerRules<MaterialRules, Material> {
    public MaterialRules(@Nonnull PControlData data) {
        super(data);
    }
}
