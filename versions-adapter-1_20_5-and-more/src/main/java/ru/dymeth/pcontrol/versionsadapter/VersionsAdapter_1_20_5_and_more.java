package ru.dymeth.pcontrol.versionsadapter;

import org.bukkit.inventory.meta.ItemMeta;
import ru.dymeth.pcontrol.data.PControlData;

import javax.annotation.Nonnull;

public class VersionsAdapter_1_20_5_and_more extends VersionsAdapter_1_13_to_1_20_4 {

    public VersionsAdapter_1_20_5_and_more(@Nonnull PControlData data) {
        super(data);
    }

    @Override
    public void setItemMetaGlowing(@Nonnull ItemMeta meta) {
        meta.setEnchantmentGlintOverride(true);
    }
}
