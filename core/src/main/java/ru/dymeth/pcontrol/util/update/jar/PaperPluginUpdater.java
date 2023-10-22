package ru.dymeth.pcontrol.util.update.jar;

import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

// Docs: https://hangar.papermc.io/api-docs
public class PaperPluginUpdater extends PluginUpdater {
    private static final String PLATFORM = "PAPER";

    private final String hangarResourceOwner;
    private final String hangarResourceSlug;
    private String lastVersion;

    public PaperPluginUpdater(@Nonnull Plugin plugin, @Nonnull String hangarResourceOwner, @Nonnull String hangarResourceSlug) {
        super(plugin);
        this.hangarResourceOwner = hangarResourceOwner;
        this.hangarResourceSlug = hangarResourceSlug;
    }

    @Nonnull
    @Override
    protected String getLastVersion() throws Throwable {
        this.lastVersion = this.readStringFromUrl("https://hangar.papermc.io/api/v1/projects/" + this.hangarResourceSlug + "/latestrelease");
        return this.lastVersion;
    }

    @Nonnull
    @Override
    protected String getResourcePageUrl() {
        return "https://hangar.papermc.io/" + this.hangarResourceOwner + "/" + this.hangarResourceSlug;
    }

    @Nonnull
    @Override
    protected String getPluginFileUrl() {
        return "https://hangar.papermc.io/api/v1/projects/" + this.hangarResourceSlug + "/versions/" + this.lastVersion + "/" + PLATFORM + "/download";
    }
}
