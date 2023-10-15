package ru.dymeth.pcontrol;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import ru.dymeth.pcontrol.api.EntityTypeUtils;
import ru.dymeth.pcontrol.api.PControlCategory;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.PControlTrigger;
import ru.dymeth.pcontrol.inventory.PControlInventory;
import ru.dymeth.pcontrol.inventory.PControlTriggerInventory;
import ru.dymeth.pcontrol.legacy.VersionsAdapterLegacy;
import ru.dymeth.pcontrol.modern.VersionsAdapterModern;
import ru.dymeth.pcontrol.text.CommonColor;
import ru.dymeth.pcontrol.text.NullText;
import ru.dymeth.pcontrol.text.Text;
import ru.dymeth.pcontrol.text.TextHelper;
import ru.dymeth.pcontrol.text.adventure.AdventureTextHelper;
import ru.dymeth.pcontrol.text.bungee.BungeeTextHelper;
import ru.dymeth.pcontrol.util.FileUtils;
import ru.dymeth.pcontrol.util.LocaleUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public final class PControlDataBukkit implements PControlData {
    private final Plugin plugin;
    private final int resourceId;
    private final short serverVersion;
    private final Set<EntityType> removableProjectileTypes;

    private final Map<String, String> messages = new HashMap<>();
    private final Map<PControlCategory, String> categoriesNames = new HashMap<>();
    private final Map<PControlTrigger, String> triggersNames = new HashMap<>();

    private final Map<World, Map<PControlTrigger, Boolean>> triggers = new HashMap<>();
    private final Map<World, Map<PControlCategory, PControlTriggerInventory>> inventories = new HashMap<>();
    private Metrics metrics = null;
    private String langKey = null;
    private final CustomTags customTags;
    private final VersionsAdapter versionsAdapter;
    private final TextHelper textHelper;

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
            this.plugin.getLogger().warning("Unable to update config from previous plugin version:");
            t.printStackTrace();
        }

        String serverVersion = "unknown";
        try {
            serverVersion = plugin.getServer().getClass().getName().split("\\.")[3];
            String[] versionArgs = serverVersion.split("_");

            if (versionArgs.length != 3) {
                throw new IllegalArgumentException("Wrong version format: " + Arrays.toString(versionArgs));
            }
            if (!versionArgs[0].equals("v1")) {
                throw new IllegalArgumentException("Wrong primary version: " + versionArgs[0]);
            }
            try {
                this.serverVersion = Short.parseShort(versionArgs[1]);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Non-numeric server version: " + versionArgs[1]);
            }
            if (!this.hasVersion(8)) {
                throw new IllegalArgumentException("Too old version: " + this.serverVersion);
            }
            if (this.serverVersion == 13 && !versionArgs[2].equals("R2")) {
                throw new IllegalArgumentException("1.13.0 and 1.13.1 are unsupported");
            }

        } catch (Exception e) {
            throw new RuntimeException("Unsupported server version (" + serverVersion + "). "
                + "It must be Spigot 1.8-1.12.2 or 1.13.2 and newer", e);
        }

        this.removableProjectileTypes = EntityTypeUtils.matchEntityTypes(null,
            "ARROW",
            "SPECTRAL_ARROW",
            "TIPPED_ARROW",
            "TRIDENT");

        this.customTags = new CustomTags(this);

        if (this.hasVersion(13)) {
            this.versionsAdapter = new VersionsAdapterModern(this);
        } else {
            this.versionsAdapter = new VersionsAdapterLegacy(this);
        }

        if (FileUtils.isClassPresent(
            "net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer")) {
            this.textHelper = new AdventureTextHelper();
        } else {
            this.textHelper = new BungeeTextHelper();
        }
    }

    @Override
    @Nonnull
    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    @Nonnull
    public Set<EntityType> getRemovableProjectileTypes() {
        return this.removableProjectileTypes;
    }

    public void reloadConfigs() {
        this.unloadData();
        this.reloadConfig();
        this.reloadLocale();
        this.reloadTriggers();
    }

    private void reloadConfig() {
        File configFile = FileUtils.createConfigFileIfNotExist(this.plugin,
            "config.yml",
            "config.yml"
        );
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
                this.plugin.getLogger().warning("There is a new update available: "
                    + newVersion + " (current version is " + oldVersion + ")");
            });
        }

        if (config.getBoolean("metrics", true)) {
            if (this.metrics == null) {
                try {
                    this.metrics = new Metrics(this.plugin, 15320);
                } catch (Throwable t) {
                    this.plugin.getLogger().warning("Unable to init metrics:");
                    t.printStackTrace();
                }
            }
        } else {
            if (this.metrics != null) {
                this.metrics = null;
                // Unable to shutdown metrics to disable data sending:

                // [Dymeth#0909] — 28/05/2022
                // Can I suggest PR with metrics shutdown feature?

                // [Bastian#0310] — 29/05/2022
                // I don't think it's worth it. Especially since it wouldn't work with plugins that use an older bStats version.
                // So you then have some plugins that respond to config changes and some plugins that ignore it.
            }
        }

        this.langKey = LocaleUtils.prepareLangKey(this.getClass(), this.plugin.getLogger(), config.getString("language"));
    }

    @Nullable
    private static <T extends Enum<T>> T getEnum(@Nonnull Class<T> enumType, @Nonnull String key) {
        try {
            return Enum.valueOf(enumType, key);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private void reloadLocale() {

        Function<String, String> messageProcessor = msg ->
            msg.replace("%plugin%", this.plugin.getName());

        LocaleUtils.reloadLocale(this.plugin, this.langKey,
            key -> getEnum(PControlCategory.class, key), messageProcessor,
            "categories.yml", this.categoriesNames);

        LocaleUtils.reloadLocale(this.plugin, this.langKey,
            key -> key, messageProcessor,
            "messages.yml", this.messages);

        LocaleUtils.reloadLocale(this.plugin, this.langKey,
            key -> getEnum(PControlTrigger.class, key), messageProcessor,
            "triggers.yml", this.triggersNames);

        for (PControlCategory category : PControlCategory.values()) {
            if (this.categoriesNames.containsKey(category)) continue;
            this.categoriesNames.put(category, category.name());
            if (category == PControlCategory.TEST) continue;
            this.plugin.getLogger().warning("Unable to load name of category " + category);
        }
        for (PControlTrigger trigger : PControlTrigger.values()) {
            if (this.triggersNames.containsKey(trigger)) continue;
            if (trigger == PControlTrigger.IGNORED_STATE) continue;
            this.triggersNames.put(trigger, trigger.name());
            this.plugin.getLogger().warning("Unable to load name of trigger " + trigger);
        }
    }

    private void reloadTriggers() {
        for (World world : this.plugin.getServer().getWorlds()) {
            this.updateWorldData(world, true);
        }
    }

    void unloadData() {
        this.messages.clear();
        this.triggers.clear();
        this.plugin.getServer().getOnlinePlayers().forEach(player -> {
            InventoryHolder holder = player.getOpenInventory().getTopInventory().getHolder();
            if (!(holder instanceof PControlInventory)) return;
            player.closeInventory();
        });
        this.inventories.clear();
    }

    @Override
    @Nonnull
    public Text getMessage(@Nonnull String key, @Nonnull String... placeholders) {
        String result = this.messages.get(key);
        if (result == null) {
            if (this.messages.containsKey(key)) return NullText.INSTANCE;
            return this.textHelper.create(key + " " + Arrays.toString(placeholders), CommonColor.RED);
        }
        for (int i = 0; i < placeholders.length; i++) {
            result = result.replace(placeholders[i], placeholders[++i]);
        }
        return this.textHelper.fromAmpersandFormat(result);
    }

    @Override
    @Nonnull
    public String getTriggerName(@Nonnull PControlTrigger trigger) {
        return this.triggersNames.get(trigger);
    }

    @Override
    @Nonnull
    public String getCategoryName(@Nonnull PControlCategory category) {
        return this.categoriesNames.get(category);
    }

    @Nonnull
    public PControlTriggerInventory getInventory(@Nonnull PControlCategory category, @Nonnull World world) {
        return this.inventories.get(world).get(category);
    }

    void updateWorldData(@Nonnull World world, boolean configPriority) {
        File file = FileUtils.createConfigFileIfNotExist(this.plugin,
            "triggers" + File.separator + world.getName() + ".yml",
            null
        );
        YamlConfiguration worldConfig = YamlConfiguration.loadConfiguration(file);

        Map<PControlTrigger, Boolean> configTriggers = new HashMap<>();
        for (String key : worldConfig.getKeys(false)) {
            try {
                PControlTrigger trigger;
                try {
                    trigger = PControlTrigger.valueOf(key.toUpperCase().replace(" ", "_"));
                    if (trigger == PControlTrigger.IGNORED_STATE) throw new IllegalArgumentException();
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Unknown trigger type");
                }
                if (!worldConfig.isBoolean(key)) {
                    throw new IllegalArgumentException("Is not a boolean value, "
                        + "but \"" + worldConfig.get(key).getClass().getSimpleName() + "\"");
                }
                boolean value = worldConfig.getBoolean(key);
                configTriggers.put(trigger, value);
            } catch (Exception ex) {
                if (configPriority) {
                    this.plugin.getLogger().warning("Unable to load trigger \"" + key + "\" "
                        + "of world \"" + world.getName() + "\": " + ex.getMessage());
                }
            }
        }

        Map<PControlTrigger, Boolean> memoryTriggers = this.triggers.computeIfAbsent(world, k -> new HashMap<>());
        Map<PControlCategory, PControlTriggerInventory> inventories = this.inventories.computeIfAbsent(world, world1 -> {
            Map<PControlCategory, PControlTriggerInventory> result = new HashMap<>();
            for (PControlCategory category : PControlCategory.values()) {
                category.prepareIcon(this);
                result.put(category, new PControlTriggerInventory(this, category, world));
            }
            return result;
        });

        boolean firstInit = configTriggers.isEmpty();
        boolean changed = false;
        for (PControlTrigger trigger : PControlTrigger.values()) {
            Boolean memoryValue = memoryTriggers.get(trigger);
            Boolean configValue = configTriggers.get(trigger);
            boolean currentValue;
            if (configPriority) {
                currentValue = configValue == null ? trigger.getDefaultValue() : configValue;
            } else {
                currentValue = memoryValue == null ? trigger.getDefaultValue() : memoryValue;
            }
            if ((configValue == null || configValue != currentValue) && trigger != PControlTrigger.IGNORED_STATE) {
                worldConfig.set(trigger.name(), currentValue);
                changed = true;
                if (!firstInit) {
                    this.plugin.getLogger().info("Added trigger \"" + this.getTriggerName(trigger) + "\" "
                        + "(" + currentValue + ") for world \"" + world.getName() + "\"");
                }
            }
            if (memoryValue == null || memoryValue != currentValue) {
                memoryTriggers.put(trigger, currentValue);
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
    public void cancelIfDisabled(@Nonnull BlockEvent event, @Nonnull PControlTrigger trigger) {
        if (trigger == PControlTrigger.IGNORED_STATE) return;
        if (!this.getWorldTriggers(event.getBlock().getWorld()).getOrDefault(trigger, false)) {
            ((Cancellable) event).setCancelled(true);
        }
    }

    @Override
    public void cancelIfDisabled(@Nonnull Cancellable event, @Nonnull World world, @Nonnull PControlTrigger trigger) {
        if (trigger == PControlTrigger.IGNORED_STATE) return;
        if (!this.getWorldTriggers(world).getOrDefault(trigger, false)) {
            event.setCancelled(true);
        }
    }

    @Override
    public boolean isActionAllowed(@Nonnull World world, @Nonnull PControlTrigger trigger) {
        if (trigger == PControlTrigger.IGNORED_STATE) throw new IllegalArgumentException();
        return this.getWorldTriggers(world).getOrDefault(trigger, false);
    }

    public void switchTrigger(@Nonnull World world, @Nonnull PControlTrigger trigger) {
        if (trigger == PControlTrigger.IGNORED_STATE) return;
        Map<PControlTrigger, Boolean> worldTriggers = this.getWorldTriggers(world);
        worldTriggers.put(trigger, !worldTriggers.get(trigger));
        this.updateWorldData(world, false);
    }

    @Nonnull
    private Map<PControlTrigger, Boolean> getWorldTriggers(@Nonnull World world) {
        Map<PControlTrigger, Boolean> worldTriggers = this.triggers.get(world);
        if (worldTriggers == null) {
            throw new IllegalArgumentException("Synchronisation error. World " + world.getName() + " not found in cache");
        }
        return worldTriggers;
    }

    @Override
    public void announce(@Nullable World world, @Nonnull Text text) {
        text.send(this.plugin.getServer().getConsoleSender());
        this.plugin.getServer().getOnlinePlayers().stream()
            .filter(player -> player.isOp() || player.hasPermission("physicscontrol.announce"))
            .filter(player -> world == null || player.getWorld() == world)
            .forEach(text::send);
    }

    @Nonnull
    public CustomTags getCustomTags() {
        return this.customTags;
    }

    @Nonnull
    public VersionsAdapter getVersionsAdapter() {
        return this.versionsAdapter;
    }

    @Nonnull
    public TextHelper getTextHelper() {
        return this.textHelper;
    }
}