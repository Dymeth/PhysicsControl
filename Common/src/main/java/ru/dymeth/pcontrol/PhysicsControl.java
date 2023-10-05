package ru.dymeth.pcontrol;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ru.dymeth.pcontrol.api.PControlTrigger;
import ru.dymeth.pcontrol.api.PhysicsListener;
import ru.dymeth.pcontrol.inventory.PControlCategoryInventory;
import ru.dymeth.pcontrol.inventory.PControlInventory;
import ru.dymeth.pcontrol.legacy.PhysicsListenerLegacy;
import ru.dymeth.pcontrol.modern.PhysicsListenerModern;

import javax.annotation.Nonnull;
import java.util.StringJoiner;

public final class PhysicsControl extends JavaPlugin implements Listener {
    @SuppressWarnings("FieldCanBeLocal")
    private final String resourceId = "%%__RESOURCE__%%";
    private PControlDataBukkit data;

    @Override
    public void onEnable() {
        this.data = new PControlDataBukkit(this, this.resourceId);
        PhysicsListener listener;

        if (this.data.hasVersion(13)) {
            listener = new PhysicsListenerModern(this.data);
        } else {
            listener = new PhysicsListenerLegacy(this.data);
        }

        this.getServer().getPluginManager().registerEvents(listener, this);
        this.getServer().getPluginManager().registerEvents(new PhysicsListenerCommon(this.data), this);
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        if (this.data == null) return; // Plugin was not loaded
        HandlerList.unregisterAll((Plugin) this);
        this.data.unloadData();
        this.data = null;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(this.data.getMessage("only-players-menu"));
                return true;
            }
            if (!sender.isOp() && !sender.hasPermission("physicscontrol.open-menu")) {
                sender.sendMessage(this.data.getMessage("bad-perms-inventory"));
                return true;
            }
            ((Player) sender).openInventory(new PControlCategoryInventory(this.data, ((Player) sender).getWorld()).getInventory());
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.isOp() && !sender.hasPermission("physicscontrol.reload")) {
                sender.sendMessage(this.data.getMessage("bad-perms-reload"));
                return true;
            }
            this.data.reloadConfigs();
            sender.sendMessage(this.data.getMessage("config-reloaded"));
            return true;
        }
        World world;
        if (sender instanceof Player && args.length < 2) {
            world = ((Player) sender).getWorld();
        } else {
            if (args.length < 2) {
                sender.sendMessage(this.data.getMessage("world-or-key-not-specified"));
                return true;
            }
            world = this.data.getPlugin().getServer().getWorld(args[0]);
            if (world == null) {
                sender.sendMessage(this.data.getMessage("world-not-found", "%world%", args[0]));
                return true;
            }
        }
        String key = join("_", 1, args).toUpperCase();
        try {
            PControlTrigger trigger = PControlTrigger.valueOf(key);
            this.data.getInventory(trigger.getCategory(), world).switchTrigger(sender, trigger);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(this.data.getMessage("key-not-found", "%key%", key));
        }
        return true;
    }

    @SuppressWarnings("SameParameterValue")
    private static String join(@Nonnull CharSequence delimiter, int firstElementIndex, @Nonnull String[] elements) {
        StringJoiner joiner = new StringJoiner(delimiter);
        while (firstElementIndex < elements.length) {
            joiner.add(elements[firstElementIndex++]);
        }
        return joiner.toString();
    }

    @EventHandler(ignoreCancelled = true)
    private void on(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().getHolder() instanceof PControlInventory) {
            ((PControlInventory) event.getClickedInventory().getHolder()).handle(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void on(InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof PControlInventory) {
            ((PControlInventory) event.getInventory().getHolder()).handle(event);
        }
    }

    @EventHandler
    private void on(PluginDisableEvent event) {
        if (event.getPlugin() != this) return;
        for (Player player : this.getServer().getOnlinePlayers()) {
            if (player.getOpenInventory().getTopInventory().getHolder() instanceof PControlInventory) {
                player.closeInventory();
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void on(WorldLoadEvent event) {
        this.data.updateWorldData(event.getWorld(), true);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(WorldUnloadEvent event) {
        this.data.unloadWorldData(event.getWorld());
    }
}
