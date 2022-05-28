package ru.dymeth.pcontrol;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import ru.dymeth.pcontrol.api.BukkitUtils;
import ru.dymeth.pcontrol.api.PControlCategory;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.PControlTrigger;
import ru.dymeth.pcontrol.inventory.PControlInventory;
import ru.dymeth.pcontrol.inventory.PControlTriggerInventory;
import ru.dymeth.pcontrol.legacy.FakeEnchantmentLegacy;
import ru.dymeth.pcontrol.modern.FakeEnchantmentModern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;

public final class PControlDataBukkit implements PControlData {
    private final Plugin plugin;
    private final int resourceId;
    private final short serverVersion;
    private final Enchantment fakeEnchantment;
    private final Set<EntityType> removableProjectileTypes;
    private final Map<String, String> messages = new HashMap<>();
    private final Map<World, Map<PControlTrigger, Boolean>> triggers = new HashMap<>();
    private final Map<World, Map<PControlCategory, PControlTriggerInventory>> inventories = new HashMap<>();
    private Metrics metrics = null;

    PControlDataBukkit(@Nonnull Plugin plugin, @Nonnull String resourceIdString) {
        this.plugin = plugin;

        int resourceId;
        try {
            resourceId = Integer.parseInt(resourceIdString);
        } catch (NumberFormatException ex) {
            resourceId = 72124;
        }
        this.resourceId = resourceId;

        try {
            new PluginUpdater(plugin);
        } catch (Throwable t) {
            this.plugin.getLogger().warning("Unable to update config from previous plugin version: "
                + t.getClass().getName() + ": " + t.getMessage());
        }

        String serverVersion = "unknown";
        try {
            serverVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];
            String[] versionArgs = serverVersion.split("_");
            if (versionArgs.length != 3) throw new IllegalArgumentException();
            if (!versionArgs[0].equals("v1")) throw new IllegalArgumentException();
            this.serverVersion = Short.parseShort(versionArgs[1]);
            if (this.serverVersion < 8) throw new IllegalArgumentException();
            if (this.serverVersion == 13 && !versionArgs[2].equals("R2")) throw new IllegalArgumentException();
        } catch (Exception e) {
            throw new RuntimeException("Unsupported server version (" + serverVersion + "). "
                + "It must be Spigot 1.8-1.12.2 or 1.13.2 and newer");
        }

        if (this.serverVersion >= 13)
            this.fakeEnchantment = FakeEnchantmentModern.getInstance();
        else
            this.fakeEnchantment = FakeEnchantmentLegacy.getInstance();

        this.removableProjectileTypes = BukkitUtils.matchEntityTypes(null,
            "ARROW",
            "SPECTRAL_ARROW",
            "TIPPED_ARROW",
            "TRIDENT");

        this.reloadConfigs();
    }

    @Override
    @Nonnull
    public Plugin getPlugin() {
        return this.plugin;
    }

    public short getServerVersion() {
        return this.serverVersion;
    }

    @Nonnull
    public Enchantment getFakeEnchantment() {
        return this.fakeEnchantment;
    }

    @Override
    @Nonnull
    public Set<EntityType> getRemovableProjectileTypes() {
        return this.removableProjectileTypes;
    }

    void reloadConfigs() {
        this.unloadData();
        this.reloadConfig();
        this.reloadMessages();
        this.reloadTriggers();
    }

    private void reloadConfig() {
        File configFile = this.createConfigFileIfNotExist("config.yml", true);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (config.getBoolean("check-for-updates", true)) {
            this.plugin.getLogger().info("Checking for updates...");
            new UpdateChecker(this.plugin, this.resourceId).getVersionAsync(newVersion -> {
                if (newVersion == null) {
                    this.plugin.getLogger().info("Unable to check for updates");
                    return;
                }
                String oldVersion = this.plugin.getDescription().getVersion();
                if (oldVersion.equals(newVersion)) return;
                this.plugin.getLogger().info("There is a new update available: "
                    + newVersion + " (current version is " + oldVersion + ")");
            });
        }
        if (config.getBoolean("metrics", true)) {
            if (this.metrics == null) {
                this.metrics = new Metrics(this.plugin, 15320);
            }
        } else {
            if (this.metrics != null) {
                this.metrics.shutdown();
                this.metrics = null;
            }
        }
    }

    private void reloadMessages() {
        File messagesFile = this.createConfigFileIfNotExist("messages.yml", true);
        YamlConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(
            new InputStreamReader(Objects.requireNonNull(this.plugin.getResource("messages.yml"))));
        Map<String, String> defaultValues = new HashMap<>();
        Map<String, String> deprecatedKeys = new HashMap<>();
        Map<String, String> unloadedKeys = new HashMap<>();

        for (String key : defaultConfig.getKeys(false)) {
            defaultValues.put(key, defaultConfig.getString(key));
        }

        for (String key : messagesConfig.getKeys(false)) {
            String msg = messagesConfig.getString(key);
            if (msg == null) continue;
            if (defaultValues.containsKey(key)) {
                msg = ChatColor.translateAlternateColorCodes('&', msg);
                msg = msg.replace("%plugin%", this.plugin.getName());
                this.messages.put(key, msg);
            } else {
                deprecatedKeys.put(key, msg);
            }
        }

        for (Map.Entry<String, String> defaultEntry : defaultValues.entrySet()) {
            String key = defaultEntry.getKey();
            if (this.messages.containsKey(key)) continue;
            String value = defaultEntry.getValue();
            this.messages.put(key, ChatColor.translateAlternateColorCodes('&', value));
            unloadedKeys.put(key, value);
        }

        Logger logger = this.plugin.getLogger();
        if (unloadedKeys.size() > 0) {
            logger.warning("Some localization phrases was not found. Used defaults:");
            unloadedKeys.forEach((key, value) -> logger.warning(key + ": \"" + value + "\""));
        }
        if (deprecatedKeys.size() > 0) {
            logger.warning("Some deprecated localization phrases was not applied:");
            deprecatedKeys.forEach((key, value) -> logger.warning(key + ": \"" + value + "\""));
        }
    }

    private void reloadTriggers() {
        for (World world : Bukkit.getWorlds()) {
            this.updateWorldData(world, true);
        }
    }

    void unloadData() {
        this.messages.clear();
        this.triggers.clear();
        Bukkit.getOnlinePlayers().forEach(player -> {
            InventoryHolder holder = player.getOpenInventory().getTopInventory().getHolder();
            if (!(holder instanceof PControlInventory)) return;
            player.closeInventory();
        });
        this.inventories.clear();
    }

    @Nonnull
    private File createConfigFileIfNotExist(@Nonnull String name, boolean fromResource) {
        File configFile = new File(this.plugin.getDataFolder(), name);
        if (configFile.isFile()) return configFile;
        if (fromResource) {
            this.plugin.saveResource(name, false);
            return configFile;
        }
        if (!configFile.getParentFile().isDirectory() && !configFile.getParentFile().mkdirs())
            throw new RuntimeException("Could not create plugin config directory");
        try {
            if (!configFile.createNewFile())
                throw new RuntimeException("Unable to create config file");
        } catch (IOException e) {
            throw new RuntimeException("Error creating config file " + name, e);
        }
        return configFile;
    }

    @Override
    @Nonnull
    public String getMessage(@Nonnull String key, @Nonnull String... placeholders) {
        String result = this.messages.get(key);
        if (result == null)
            return ChatColor.RED + key + " " + Arrays.toString(placeholders);
        for (int i = 0; i < placeholders.length; i++)
            result = result.replace(placeholders[i], placeholders[++i]);
        return result;
    }

    @Nonnull
    public PControlTriggerInventory getInventory(@Nonnull PControlCategory category, @Nonnull World world) {
        return this.inventories.get(world).get(category);
    }

    void updateWorldData(@Nonnull World world, boolean configPriority) {
        File file = createConfigFileIfNotExist("triggers" + File.separator + world.getName() + ".yml", false);
        YamlConfiguration worldConfig = YamlConfiguration.loadConfiguration(file);

        Map<PControlTrigger, Boolean> triggers = this.triggers.computeIfAbsent(world, k -> new HashMap<>());
        Map<PControlCategory, PControlTriggerInventory> inventories = this.inventories.computeIfAbsent(world, world1 -> {
            Map<PControlCategory, PControlTriggerInventory> result = new HashMap<>();
            for (PControlCategory category : PControlCategory.values()) {
                result.put(category, new PControlTriggerInventory(this, category, world));
            }
            return result;
        });
        boolean changed = false;
        for (PControlTrigger trigger : PControlTrigger.values()) {
            Boolean memoryValue = triggers.get(trigger);
            Boolean configValue = worldConfig.contains(trigger.name()) ? worldConfig.getBoolean(trigger.name()) : null;
            boolean currentValue;
            if (configPriority)
                currentValue = configValue == null ? trigger.getDefaultValue() : configValue;
            else
                currentValue = memoryValue == null ? trigger.getDefaultValue() : memoryValue;
            if (configValue == null || configValue != currentValue) {
                worldConfig.set(trigger.name(), currentValue);
                changed = true;
            }
            if (memoryValue == null || memoryValue != currentValue) {
                triggers.put(trigger, currentValue);
                inventories.get(trigger.getCategory()).updateTriggerStack(trigger);
            }
        }
        if (!changed) return;
        try {
            worldConfig.save(file);
        } catch (Exception e) {
            this.plugin.getLogger().severe("Unable to save config file " + file);
        }
    }

    void unloadWorldData(@Nonnull World world) {
        this.triggers.remove(world);
        this.inventories.remove(world);
    }

    @Override
    public boolean hasVersion(int version) {
        return this.serverVersion >= version;
    }

    @Override
    public boolean isTriggerSupported(@Nonnull PControlTrigger trigger) {
        return this.serverVersion >= trigger.getMinVersion();
    }

    @Override
    public void cancelIfDisabled(@Nonnull BlockEvent event, @Nonnull PControlTrigger trigger) {
        if (!this.getWorldTriggers(event.getBlock().getWorld()).getOrDefault(trigger, false))
            ((Cancellable) event).setCancelled(true);
    }

    @Override
    public void cancelIfDisabled(@Nonnull Cancellable event, @Nonnull World world, @Nonnull PControlTrigger trigger) {
        if (!this.getWorldTriggers(world).getOrDefault(trigger, false))
            event.setCancelled(true);
    }

    @Override
    public boolean isActionAllowed(@Nonnull World world, @Nonnull PControlTrigger trigger) {
        return this.getWorldTriggers(world).getOrDefault(trigger, false);
    }

    public void switchTrigger(@Nonnull World world, @Nonnull PControlTrigger trigger) {
        Map<PControlTrigger, Boolean> worldTriggers = this.getWorldTriggers(world);
        worldTriggers.put(trigger, !worldTriggers.get(trigger));
        this.updateWorldData(world, false);
    }

    @Nonnull
    private Map<PControlTrigger, Boolean> getWorldTriggers(@Nonnull World world) {
        Map<PControlTrigger, Boolean> worldTriggers = this.triggers.get(world);
        if (worldTriggers == null)
            throw new IllegalArgumentException("Synchronisation error. World " + world.getName() + " not found in cache");
        return worldTriggers;
    }

    @Override
    public void announce(@Nullable World world, @Nonnull String plain, @Nullable BaseComponent component) {
        Bukkit.getConsoleSender().sendMessage(plain);
        Bukkit.getOnlinePlayers().stream()
            .filter(player -> player.isOp() || player.hasPermission("physicscontrol.announce"))
            .filter(player -> world == null || player.getWorld() == world)
            .forEach(component == null
                ? player -> player.sendMessage(plain)
                : player -> player.spigot().sendMessage(component));
    }
}
