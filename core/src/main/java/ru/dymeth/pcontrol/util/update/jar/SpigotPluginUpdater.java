package ru.dymeth.pcontrol.util.update.jar;

import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

// Docs: https://github.com/SpigotMC/XenforoResourceManagerAPI
public class SpigotPluginUpdater extends PluginUpdater {
    private final int spigotResourceId;

    public SpigotPluginUpdater(@Nonnull Plugin plugin, int spigotResourceId) {
        super(plugin);
        this.spigotResourceId = spigotResourceId;
    }

    @Nonnull
    @Override
    protected String getLastVersion() throws Throwable {
        // JSON: "https://api.spigotmc.org/simple/0.2/index.php?action=getResource&id=" + this.spigotResourceId
        return this.readStringFromUrl("https://api.spigotmc.org/legacy/update.php?resource=" + this.spigotResourceId);
    }

    @Nonnull
    @Override
    protected String getResourcePageUrl() {
        return "https://spigotmc.org/resources/" + this.spigotResourceId;
    }

    @Nonnull
    @Override
    protected String getPluginFileUrl() {
        return "https://api.spiget.org/v2/resources/" + this.spigotResourceId + "/versions/latest/download";
    }
}
