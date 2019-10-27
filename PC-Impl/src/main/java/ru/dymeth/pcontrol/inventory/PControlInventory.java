package ru.dymeth.pcontrol.inventory;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class PControlInventory implements InventoryHolder {
    final World world;
    private final Inventory inventory;
    private final Map<Short, Consumer<Player>> slotActions;

    PControlInventory(@Nonnull World world, int rows, @Nonnull String title) {
        this.world = world;
        this.inventory = Bukkit.createInventory(this, rows * 9, title);
        this.slotActions = new HashMap<>();
    }

    final void setItem(short slot, @Nullable ItemStack stack, @Nullable Consumer<Player> action) {
        if (stack == null) {
            this.inventory.setItem(slot, null);
            this.slotActions.remove(slot);
            return;
        }
        this.inventory.setItem(slot, stack);
        this.slotActions.put(slot, action);
    }

    @Nullable
    final ItemStack getItem(int slot) {
        return this.inventory.getItem(slot);
    }

    public final void handleEvent(@Nonnull InventoryClickEvent event) {
        event.setCancelled(true);
        if (event.getClick() != ClickType.LEFT) return;
        Consumer<Player> action = this.slotActions.get((short) event.getRawSlot());
        if (action == null) return;
        action.accept((Player) event.getWhoClicked());
    }

    @Override
    @Nonnull
    public final Inventory getInventory() {
        return this.inventory;
    }
}
