package ru.dymeth.pcontrol.inventory;

import org.bukkit.World;
import ru.dymeth.pcontrol.PControlCategory;
import ru.dymeth.pcontrol.PControlDataBukkit;

import javax.annotation.Nonnull;

public class PControlCategoryInventory extends PControlInventory {
    public PControlCategoryInventory(@Nonnull PControlDataBukkit data, @Nonnull World world) {
        super(world, 3, data.getMessage("category-inventory-title", "%world%", world.getName()));
        for (PControlCategory category : PControlCategory.values()) {
            this.setItem(category.getSlot(), category.getIcon(), player ->
                    player.openInventory(data.getInventory(category, world).getInventory()));
        }
    }
}
