package ru.dymeth.pcontrol;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
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
    private final short serverVersion;
    private final Enchantment fakeEnchantment;
    private final Set<EntityType> removableProjectileTypes;
    private final Map<String, String> messages = new HashMap<>();
    private final Map<World, Map<PControlTrigger, Boolean>> triggers = new HashMap<>();
    private final Map<World, Map<PControlCategory, PControlTriggerInventory>> inventories = new HashMap<>();

    PControlDataBukkit(@Nonnull Plugin plugin) {
        this.plugin = plugin;

        try {
            this.serverVersion = Short.parseShort(Bukkit.getServer().getClass().getName().split("\\.")[3].split("_")[1]);
            if (this.serverVersion < 8) throw new IllegalArgumentException();
        } catch (Exception e) {
            throw new RuntimeException("Unsupported server version. It must be Spigot 1.12 or higher");
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
        this.reloadMessages();
        this.reloadTriggers();
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
            this.messages.put(key, ChatColor.translateAlternateColorCodes('&',value ));
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
        this.createConfigFileIfNotExist("config.yml", false);
        this.plugin.reloadConfig();
        FileConfiguration baseConfig = this.plugin.getConfig();
        boolean changed = false;
        for (World world : Bukkit.getWorlds())
            if (this.updateWorldData(world, baseConfig, true))
                changed = true;
        if (changed) this.plugin.saveConfig();
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

    void updateWorldData(@Nonnull World world) {
        if (this.updateWorldData(world, this.plugin.getConfig(), true)) this.plugin.saveConfig();
    }

    private boolean updateWorldData(@Nonnull World world, @Nonnull ConfigurationSection config, boolean configPriority) {
        boolean changed = false;
        ConfigurationSection worldConfig = config.getConfigurationSection(world.getName());
        if (worldConfig == null) {
            worldConfig = config.createSection(world.getName());
            changed = true;
        }

        Map<PControlTrigger, Boolean> triggers = this.triggers.computeIfAbsent(world, k -> new HashMap<>());
        Map<PControlCategory, PControlTriggerInventory> inventories = this.inventories.computeIfAbsent(world, world1 -> {
            Map<PControlCategory, PControlTriggerInventory> result = new HashMap<>();
            for (PControlCategory category : PControlCategory.values()) {
                result.put(category, new PControlTriggerInventory(this, category, world));
            }
            return result;
        });
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
        return changed;
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
        if (this.updateWorldData(world, this.plugin.getConfig(), false)) this.plugin.saveConfig();
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
