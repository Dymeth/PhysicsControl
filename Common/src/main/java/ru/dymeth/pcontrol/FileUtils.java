package ru.dymeth.pcontrol;

import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtils {
    /**
     * <a href="https://www.uofr.net/~greg/java/get-resource-listing.html">Source</a>
     * List directory contents for a resource folder. Not recursive.
     * This is basically a brute-force implementation.
     * Works for regular files and also JARs.
     *
     * @param clazz Any java class that lives in the same place as the resources you want.
     * @param path  Should end with "/", but not start with one.
     * @return Just the name of each member item, not the full paths.
     * @author Greg Briggs
     */
    public static String[] getResourceFolderFiles(@Nonnull Class<?> clazz, @Nonnull String path) throws URISyntaxException, IOException {
        URL dirURL = clazz.getClassLoader().getResource(path);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
            /* A file path: easy enough */
            return new File(dirURL.toURI()).list();
        }

        if (dirURL == null) {
            /*
             * In case of a jar file, we can't actually find a directory.
             * Have to assume the same jar as clazz.
             */
            String me = clazz.getName().replace(".", "/") + ".class";
            dirURL = clazz.getClassLoader().getResource(me);

            if (dirURL == null) {
                throw new IllegalArgumentException("Specified resource not found");
            }
        }

        if (dirURL.getProtocol().equals("jar")) {
            /* A JAR path */
            String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            Set<String> result = new HashSet<>(); //avoid duplicates in case it is a subdirectory
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (name.startsWith(path)) { //filter according to the path
                    String entry = name.substring(path.length());
                    int checkSubdir = entry.indexOf("/");
                    if (checkSubdir >= 0) {
                        // if it is a subdirectory, we just return the directory name
                        entry = entry.substring(0, checkSubdir);
                    }
                    result.add(entry);
                }
            }
            return result.toArray(new String[0]);
        }

        throw new UnsupportedOperationException("Cannot list files for URL " + dirURL);
    }

    private static void removeDirToRootIfEmpty(@Nonnull File dir) {
        if (!dir.isDirectory()) return;
        final File[] files = dir.listFiles();
        if (files == null) return;
        if (files.length > 0) return;
        if (!dir.delete()) return;
        removeDirToRootIfEmpty(dir.getParentFile());
    }

    @Nonnull
    public static File createConfigFileIfNotExist(@Nonnull Plugin plugin, @Nonnull String name, @Nullable String resourceFile) {
        File configFile = new File(plugin.getDataFolder(), name);
        if (configFile.isFile()) return configFile;
        if (resourceFile != null) {
            plugin.saveResource(resourceFile, false);
            if (!name.equals(resourceFile)) {
                File from = new File(plugin.getDataFolder(), resourceFile);
                try {
                    Files.move(from.toPath(), configFile.toPath());
                    FileUtils.removeDirToRootIfEmpty(from.getParentFile());
                } catch (Exception e) {
                    throw new RuntimeException("Error moving created config file from " + from + " to " + configFile, e);
                }
            }
            return configFile;
        }
        if (!configFile.getParentFile().isDirectory() && !configFile.getParentFile().mkdirs()) {
            throw new RuntimeException("Could not create plugin config directory");
        }
        try {
            if (!configFile.createNewFile()) {
                throw new RuntimeException("Unable to create config file");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creating config file " + name, e);
        }
        return configFile;
    }
}
