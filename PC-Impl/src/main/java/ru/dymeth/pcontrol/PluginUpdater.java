package ru.dymeth.pcontrol;

import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

class PluginUpdater {
    private static int getVersionNumberByVersionName(@Nonnull String name) {
        switch (name) {
            case "Beta-3":
                return 3;
        }
        throw new RuntimeException("Unknown plugin version: " + name);
    }

    private static void updateFrom(int version) {
    }

    PluginUpdater(@Nonnull Plugin plugin) {
        int currentVersion = getVersionNumberByVersionName(plugin.getDescription().getVersion());
        File versionFile = getVersionFile(plugin);
        int previousVersion;
        if (versionFile.isFile()) {
            try {
                previousVersion = getVersionNumberByVersionName(Files.lines(versionFile.toPath(), StandardCharsets.UTF_8).iterator().next());
            } catch (Exception e) {
                throw new RuntimeException("Could not read plugin-version file", e);
            }
        } else {
            this.writeVersionFile(plugin);
            if (new File(plugin.getDataFolder(), "messages.yml").isFile()) {
                previousVersion = 2; // Legacy version
            } else {
                previousVersion = currentVersion;
            }
        }
        if (previousVersion > currentVersion)
            throw new RuntimeException("Unknown plugin version number: " + previousVersion);
        if (previousVersion == currentVersion) return;
        while (previousVersion < currentVersion) {
            updateFrom(previousVersion++);
        }
        this.writeVersionFile(plugin);
    }

    private File getVersionFile(@Nonnull Plugin plugin) {
        return new File(plugin.getDataFolder(), "plugin-version");
    }

    private void writeVersionFile(@Nonnull Plugin plugin) {
        File f = getVersionFile(plugin);
        if (f.exists() && !f.delete()) {
            throw new RuntimeException("Could not delete plugin-version file");
        }
        try {
            if (f.createNewFile()) {
                Files.write(f.toPath(), Arrays.asList(
                        plugin.getDescription().getVersion(),
                        "^ Do NOT change this! This is necessary for the plugin to work correctly."
                ), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not create plugin-version file", e);
        }
    }
}
