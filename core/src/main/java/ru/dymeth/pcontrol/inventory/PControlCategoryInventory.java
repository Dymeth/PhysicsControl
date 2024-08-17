package ru.dymeth.pcontrol.inventory;

import org.bukkit.World;
import ru.dymeth.pcontrol.PControlDataBukkit;
import ru.dymeth.pcontrol.data.category.PControlCategory;

import javax.annotation.Nonnull;

public class PControlCategoryInventory extends PControlInventory {
    public static final boolean DISPLAY_TEST_CATEGORY = false;

    public PControlCategoryInventory(@Nonnull PControlDataBukkit data, @Nonnull World world) {
        super(
            data,
            world,
            3,
            data.getMessage("category-inventory-title", "%world%", world.getName())
        );
        PControlCategory TEST_CATEGORY = data.getCategoriesRegistry().TEST;
        for (PControlCategory category : data.getCategoriesRegistry().values()) {
            if (category == TEST_CATEGORY && !DISPLAY_TEST_CATEGORY) {
                continue;
            }
            this.setItem(category.getSlot(), category.getIcon(), player ->
                player.openInventory(data.getInventory(category, world).getInventory()));
        }
    }
}
