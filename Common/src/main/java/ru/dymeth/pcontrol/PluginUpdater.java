package ru.dymeth.pcontrol;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

class PluginUpdater {
    @SuppressWarnings("unused")
    @Nullable
    private String updateAndReturnNewVersion(@Nonnull String previousVersion) {
        if (previousVersion.toLowerCase().contains("beta")) {
            return "1.0.0";
        }
        String currentVersion = this.getCurrentVersion();
        int[] argsPrevious = parseVersion(previousVersion);
        int[] argsCurrent = parseVersion(currentVersion);
        for (int i = 0; i < argsPrevious.length; i++) {
            if (argsPrevious[i] < argsCurrent[i]) break;
            if (argsPrevious[i] > argsCurrent[i]) return null;
        }
        int majorVersion = argsPrevious[0];
        int minorVersion = argsPrevious[1];
        int patchVersion = argsPrevious[2];
        if (majorVersion == 1 && minorVersion == 0) {
            this.updateTo_1_1_0();
            return "1.1.0";
        }
        if (majorVersion == 1 && minorVersion == 1) {
            this.updateTo_1_2_0();
            return "1.2.0";
        }
        return currentVersion;
    }

    private void updateTo_1_1_0() {
        File oldFile = new File(this.plugin.getDataFolder(), "config.yml");
        if (oldFile.isFile()) {
            ConfigurationSection oldConfig = YamlConfiguration.loadConfiguration(oldFile);
            File newDir = new File(this.plugin.getDataFolder(), "triggers");
            //noinspection ResultOfMethodCallIgnored
            newDir.mkdirs();
            for (String worldName : oldConfig.getKeys(false)) {
                ConfigurationSection worldConfig = oldConfig.getConfigurationSection(worldName);
                if (worldConfig == null) continue;
                FileConfiguration newConfig = new YamlConfiguration();
                for (String key : worldConfig.getKeys(false)) {
                    if (!worldConfig.isBoolean(key)) continue;
                    newConfig.set(key, worldConfig.getBoolean(key));
                }
                File newFile = new File(newDir, worldName + ".yml");
                try {
                    newConfig.save(newFile);
                } catch (Exception e) {
                    throw new RuntimeException("Unable to save config file " + newFile, e);
                }
            }
            //noinspection ResultOfMethodCallIgnored
            oldFile.delete();
        }
    }

    private void updateTo_1_2_0() {
        File oldFile = new File(this.plugin.getDataFolder(), "messages.yml");
        File newDir = new File(this.plugin.getDataFolder(), "lang");
        File newFile = new File(newDir, "messages.yml");
        if (oldFile.isFile() && !newFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            newDir.mkdirs();
            try {
                Files.move(oldFile.toPath(), newFile.toPath());
                //noinspection ResultOfMethodCallIgnored
                oldFile.delete();
            } catch (Exception e) {
                throw new RuntimeException("Unable to move lang file " + oldFile + " to " + newFile, e);
            }
        }

        File configFile = new File(this.plugin.getDataFolder(), "config.yml");
        if (configFile.isFile() && !YamlConfiguration.loadConfiguration(configFile).isBoolean("lang")) {
            this.appendDataToFile(configFile,
                "\r\n" +
                    "language: \"auto\" # auto/en/ru" +
                    "\r\n"
            );
        }
    }

    private static int[] parseVersion(String versionName) {
        String[] args = versionName.split("\\.");
        if (args.length != 3) throw new IllegalArgumentException("Unable to parse version: " + versionName);
        int majorVersion = Integer.parseInt(args[0]);
        if (majorVersion != 1) {
            throw new IllegalArgumentException("Wrong major version of " + versionName + ": " + majorVersion);
        }
        int[] result = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            result[i] = Integer.parseInt(args[i]);
        }
        return result;
    }

    private final Plugin plugin;

    PluginUpdater(@Nonnull Plugin plugin) {
        this.plugin = plugin;
        String currentVersion = this.getCurrentVersion();
        File versionFile = this.getVersionFile();
        if (!versionFile.isFile()) {
            this.writeVersionFile();
            return;
        }
        String previousVersion;
        try {
            previousVersion = Files.lines(versionFile.toPath(), StandardCharsets.UTF_8).iterator().next();
        } catch (Exception e) {
            throw new RuntimeException("Could not read " + versionFile.getName() + " file", e);
        }
        if (currentVersion.equals(previousVersion)) return;
        String sourceVersion = previousVersion;
        while (true) {
            String nextVersion = this.updateAndReturnNewVersion(previousVersion);
            if (nextVersion == null) return; // config is newer than plugin
            if (nextVersion.equals(currentVersion)) break;
            previousVersion = nextVersion;
        }
        this.writeVersionFile();
        this.plugin.getLogger().info("Plugin updated successfully: " + sourceVersion + " -> " + currentVersion);
    }

    private String getCurrentVersion() {
        return this.plugin.getDescription().getVersion();
    }

    private File getVersionFile() {
        return new File(this.plugin.getDataFolder(), "plugin-version");
    }

    private void writeVersionFile() {
        File f = getVersionFile();
        if (f.exists() && !f.delete()) {
            throw new RuntimeException("Could not delete " + f.getName() + " file");
        }
        try {
            //noinspection ResultOfMethodCallIgnored
            f.getParentFile().mkdirs();
            if (f.createNewFile()) {
                Files.write(f.toPath(), Arrays.asList(
                    getCurrentVersion(),
                    "^ Do NOT change this! This is necessary for the plugin to work correctly."
                ), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not create " + f.getName() + " file", e);
        }
    }

    private void appendDataToFile(@Nonnull File file,
                                  @SuppressWarnings("SameParameterValue") @Nonnull String data
    ) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException("Unable to append data to file " + file);
        }
    }
}
