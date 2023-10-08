package ru.dymeth.pcontrol.text;

import org.bukkit.Server;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.List;

@SuppressWarnings("unused")
public interface TextHelper {
    @Nonnull
    Text create(@Nonnull String text, @Nonnull CommonColor color);

    @Nonnull
    Text create(@Nonnull String text, @Nonnull CommonColor color, @Nonnull CommonDecoration decoration);

    @Nonnull
    Text createClickable(@Nonnull String text, @Nonnull String command);

    @Nonnull
    Text fromAmpersandFormat(@Nonnull String ampersandText);

    void setStackName(@Nonnull ItemMeta meta, @Nonnull Text name);

    void setStackLore(@Nonnull ItemMeta meta, @Nonnull List<Text> lore);

    @Nonnull
    Inventory createInventory(@Nonnull Server server, @Nonnull InventoryHolder holder, @Nonnull InventoryType type, @Nonnull Text title);

    @Nonnull
    Inventory createInventory(@Nonnull Server server, @Nonnull InventoryHolder holder, int size, @Nonnull Text title);
}