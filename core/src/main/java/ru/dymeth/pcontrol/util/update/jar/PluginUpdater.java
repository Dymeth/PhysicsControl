package ru.dymeth.pcontrol.util.update.jar;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class PluginUpdater {
    private final Plugin plugin;
    private BukkitTask regularChecksTask = null;
    private boolean displayCheckingFailedStack = true;
    private String lastVersionFileUrl;

    protected PluginUpdater(@Nonnull Plugin plugin) {
        this.plugin = plugin;
    }

    public final void startRegularChecking() {
        if (this.regularChecksTask == null) {
            this.regularChecksTask = this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(this.plugin,
                this::announceNewUpdates,
                TimeUnit.SECONDS.toSeconds(5) * 20L,
                TimeUnit.HOURS.toSeconds(12) * 20L
            );
        }
    }

    public final void stopRegularChecking() {
        if (this.regularChecksTask != null) {
            this.regularChecksTask.cancel();
            this.regularChecksTask = null;
        }
    }

    private void announceNewUpdates() {
        this.checkForNewVersion(
            () -> {
            },
            lastVersion -> {
                String currentVersion = this.plugin.getDescription().getVersion();
                this.plugin.getLogger().warning(
                    "There is a new update available: " + lastVersion + " (current version is " + currentVersion + ")");
                this.plugin.getLogger().warning(
                    "Use \"/pc update\" in console or visit plugin page: " + this.getResourcePageUrl());
            });
    }

    public final void startPluginUpdating() {
        Logger logger = this.plugin.getLogger();
        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            logger.info("Checking for new versions...");
            this.checkForNewVersion(
                () -> logger.info("No updates found"),
                lastVersion -> {
                    String currentVersion = this.plugin.getDescription().getVersion();
                    logger.info("New version found: " + lastVersion + " (current is " + currentVersion + ")");
                    logger.warning("Started downloading JAR from: " + this.lastVersionFileUrl);
                    try {
                        // https://bukkit.fandom.com/wiki/Installing_Plugins#Updating_Plugins
                        File dir = new File(this.plugin.getDataFolder().getParentFile(), "update");
                        //noinspection ResultOfMethodCallIgnored
                        dir.mkdirs();
                        File jarFile = new File(dir, this.plugin.getName() + "-" + lastVersion + ".jar");
                        this.readFileFromUrl(this.lastVersionFileUrl, jarFile);
                    } catch (IOException e) {
                        if (e.getMessage() != null && e.getMessage().startsWith("Server returned HTTP response code: 403 for URL: ")) {
                            String url = e.getMessage().substring("Server returned HTTP response code: 403 for URL: ".length());
                            logger.severe("Unable to download update automatically via website protection/restrictions.");
                            logger.severe("You can download JAR-file manually: " + url);
                            return;
                        }
                        logger.log(Level.SEVERE, "Unable to download JAR:", e);
                        return;
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "Unable to download JAR:", e);
                        return;
                    }
                    logger.info(
                        "JAR-file downloaded successfully, update will be applied on the next server restart");
                });
        });
    }

    private void checkForNewVersion(@Nonnull Runnable noUpdatesFound, @Nonnull Consumer<String> onNewVersionFound) {
        String lastVersion;
        try {
            lastVersion = this.getLastVersion();
        } catch (Throwable t) {
            this.plugin.getLogger().log(Level.WARNING,
                "Unable to check for updates: " + t.getMessage(),
                this.displayCheckingFailedStack ? t : null);
            this.displayCheckingFailedStack = false;
            return;
        }
        this.lastVersionFileUrl = this.getPluginFileUrl();
        if (lastVersion.equals(this.plugin.getDescription().getVersion())) {
            noUpdatesFound.run();
        } else {
            onNewVersionFound.accept(lastVersion);
        }
        this.displayCheckingFailedStack = true;
    }

    @Nonnull
    protected String readStringFromUrl(@Nonnull String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        try (
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        ) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (result.length() > 0) result.append('\n');
                result.append(line);
            }

            bufferedReader.close();
            return result.toString();
        }
    }

    protected void readFileFromUrl(@Nonnull String url, @Nonnull File targetFile) throws IOException {
        try (
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(targetFile)
        ) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
    }

    @Nonnull
    protected abstract String getLastVersion() throws Throwable;

    @Nonnull
    protected abstract String getResourcePageUrl();

    @Nonnull
    protected abstract String getPluginFileUrl();
}
