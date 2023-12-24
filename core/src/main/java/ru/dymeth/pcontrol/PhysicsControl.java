package ru.dymeth.pcontrol;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.inventory.PControlCategoryInventory;
import ru.dymeth.pcontrol.inventory.PControlInventory;
import ru.dymeth.pcontrol.listener.MoistureChangeEventListener;
import ru.dymeth.pcontrol.listener.PhysicsListenerCommon;
import ru.dymeth.pcontrol.rules.TriggerRules;
import ru.dymeth.pcontrol.util.ReflectionUtils;

import javax.annotation.Nonnull;
import java.util.StringJoiner;
import java.util.function.Supplier;

public final class PhysicsControl extends JavaPlugin implements Listener {
    private PControlDataBukkit data;

    @Override
    public void onEnable() {
        this.data = new PControlDataBukkit(this,
            72124,
            "Dymeth", "PhysicsControl",
            15320
        );

        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new PhysicsListenerCommon(this.data), this);

        this.reg("org.bukkit.event.block.MoistureChangeEvent", () -> new MoistureChangeEventListener(this.data));

        for (PControlTrigger trigger : this.data.categories().SETTINGS.getTriggers()) {
            trigger.markAvailable();
        }

        if (TriggerRules.LOG_TRIGGERS_REGISTRATIONS) {
            this.getLogger().info("Total rules registered: " + TriggerRules.getTotalRulesRegistered());
        }

        this.data.reloadConfigs();
    }

    @SuppressWarnings("SameParameterValue")
    private void reg(@Nonnull String eventClassName, @Nonnull Supplier<Listener> listenerCreator) {
        if (ReflectionUtils.isClassPresent(eventClassName)) {
            this.getServer().getPluginManager().registerEvents(listenerCreator.get(), this);
        }
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
            this.openGui(sender);
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "reload": {
                this.reload(sender);
                return true;
            }
            case "update": {
                this.update(sender);
                return true;
            }
            case "tp": {
                this.teleport(sender, args);
                return true;
            }
            default: {
                this.switchTrigger(sender, args);
                return true;
            }
        }
    }

    private void openGui(@Nonnull CommandSender sender) {
        if (!(sender instanceof Player)) {
            this.data.getMessage("only-players-menu").send(sender);
            return;
        }
        if (!sender.isOp() && !sender.hasPermission("physicscontrol.open-menu")) {
            this.data.getMessage("bad-perms-inventory").send(sender);
            return;
        }
        ((Player) sender).openInventory(new PControlCategoryInventory(this.data, ((Player) sender).getWorld()).getInventory());
    }

    private void reload(@Nonnull CommandSender sender) {
        if (!sender.isOp() && !sender.hasPermission("physicscontrol.reload")) {
            this.data.getMessage("bad-perms-reload").send(sender);
            return;
        }
        this.data.reloadConfigs();
        this.data.getMessage("config-reloaded").send(sender);
    }

    private void update(@Nonnull CommandSender sender) {
        if (!(sender instanceof ConsoleCommandSender) || !sender.hasPermission("physicscontrol.update")) {
            this.data.getMessage("bad-perms-reload").send(sender);
            return;
        }
        this.data.updatePlugin();
    }

    private void teleport(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (!sender.isOp() && !sender.hasPermission("minecraft.command.tp")) {
            this.data.getMessage("bad-perms-reload").send(sender);
            return;
        }
        if (!(sender instanceof Player)) {
            this.data.getMessage("only-players-menu").send(sender);
            return;
        }
        if (args.length != 5) return;

        World world = this.data.server().getWorld(args[1]);
        if (world == null) return;

        int x, y, z;
        try {
            x = Integer.parseInt(args[2]);
            y = Integer.parseInt(args[3]);
            z = Integer.parseInt(args[4]);
        } catch (NumberFormatException ex) {
            return;
        }

        ((Player) sender).teleport(new Location(world, x, y, z));
    }

    private void switchTrigger(@Nonnull CommandSender sender, @Nonnull String[] args) {
        World world;
        if (sender instanceof Player && args.length < 2) {
            world = ((Player) sender).getWorld();
        } else {
            if (args.length < 2) {
                this.data.getMessage("world-or-key-not-specified").send(sender);
                return;
            }
            world = this.data.server().getWorld(args[0]);
            if (world == null) {
                this.data.getMessage("world-not-found", "%world%", args[0]).send(sender);
                return;
            }
        }
        String key = join("_", 1, args).toUpperCase();
        try {
            PControlTrigger trigger = this.data.triggers().valueOf(key);
            if (trigger == this.data.triggers().IGNORED_STATE) throw new IllegalArgumentException();
            this.data.getInventory(trigger.getCategory(), world).switchTrigger(sender, trigger);
        } catch (IllegalArgumentException e) {
            this.data.getMessage("key-not-found", "%key%", key).send(sender);
        }
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
