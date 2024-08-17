package ru.dymeth.pcontrol.versionsadapter;

import org.bukkit.inventory.meta.ItemMeta;
import ru.dymeth.pcontrol.data.PControlData;

import javax.annotation.Nonnull;

public class VersionsAdapter_1_20_5 extends VersionsAdapter_1_13 {

    public VersionsAdapter_1_20_5(@Nonnull PControlData data) {
        super(data);
    }

    @Override
    public void setItemMetaGlowing(@Nonnull ItemMeta meta) {
        meta.setEnchantmentGlintOverride(true);
    }

}
