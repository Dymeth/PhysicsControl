package ru.dymeth.pcontrol;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;

public class LocaleUtils {

    public static String prepareLangKey(@Nonnull Class<?> clazz, @Nonnull Logger logger, @Nullable String rawLangKey) {
        if (rawLangKey == null) {
            rawLangKey = "auto";
        } else {
            rawLangKey = rawLangKey.trim().toLowerCase();
            if (rawLangKey.isEmpty()) rawLangKey = "auto";
        }

        Set<String> supportedLanguageKeys = getSupportedLanguages(clazz);
        if (rawLangKey.equals("auto")) {
            return getSystemUserLanguage(supportedLanguageKeys);
        } else if (!supportedLanguageKeys.contains(rawLangKey)) {
            String autoLangKey = getSystemUserLanguage(supportedLanguageKeys);
            logger.warning("Language \"" + rawLangKey + "\" isn't supported," +
                " switching to " + autoLangKey + " (auto)...");
            logger.warning("Supported languages: " + supportedLanguageKeys);
            return autoLangKey;
        }
        return rawLangKey;
    }

    @Nonnull
    private static Set<String> getSupportedLanguages(@Nonnull Class<?> clazz) {
        try {
            Set<String> result = new LinkedHashSet<>();
            for (String lang : FileUtils.getResourceFolderFiles(clazz, "lang/")) {
                if (lang.trim().isEmpty()) continue;
                result.add(lang);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Unable to load available languages", e);
        }
    }

    @Nonnull
    private static String getSystemUserLanguage(@Nonnull Set<String> supportedLanguageKeys) {
        String result = Locale.getDefault().getLanguage().toLowerCase();
        if (supportedLanguageKeys.contains(result)) {
            return result;
        }
        // https://ss64.com/locale.html
        switch (result) {
            case "be": // Belarusian
            case "kk": // Kazakh
            case "ky": // Kyrgyz
            case "tg": // Tajik
            case "ru": { // Russian
                return "ru";
            }
            default: {
                return "en";
            }
        }
    }

    public static void reloadLocale(@Nonnull Plugin plugin,
                                    @Nonnull String langKey,
                                    @Nonnull Function<String, String> messageProcessor,
                                    @Nonnull String simpleFileName,
                                    @Nonnull Map<String, String> targetMap
    ) {
        String systemFileName = "lang" + File.separator + simpleFileName;
        String resourceFileName = "lang/" + langKey + "/" + simpleFileName;

        File messagesFile = FileUtils.createConfigFileIfNotExist(plugin,
            systemFileName,
            resourceFileName
        );
        YamlConfiguration actualConfig = YamlConfiguration.loadConfiguration(messagesFile);
        YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(
            new InputStreamReader(Objects.requireNonNull(plugin.getResource(resourceFileName))));

        loadMessagesAndWarnAboutWrongKeys(
            actualConfig, defaultConfig, targetMap,
            plugin.getLogger(), simpleFileName,
            messageProcessor);
    }

    private static void loadMessagesAndWarnAboutWrongKeys(@Nonnull YamlConfiguration actualConfig,
                                                          @Nonnull YamlConfiguration defaultConfig,
                                                          @Nonnull Map<String, String> targetMap,
                                                          @Nonnull Logger logger,
                                                          @Nonnull String fileName,
                                                          @Nonnull Function<String, String> messageProcessor
    ) {
        Map<String, String> defaultValues = new HashMap<>();
        Map<String, String> deprecatedKeys = new HashMap<>();
        Map<String, String> unloadedKeys = new HashMap<>();

        for (String key : defaultConfig.getKeys(false)) {
            defaultValues.put(key, defaultConfig.getString(key));
        }

        for (String key : actualConfig.getKeys(false)) {
            String msg = actualConfig.getString(key);
            if (msg == null) continue;
            if (defaultValues.containsKey(key)) {
                targetMap.put(key, messageProcessor.apply(msg));
            } else {
                deprecatedKeys.put(key, msg);
            }
        }

        for (Map.Entry<String, String> defaultEntry : defaultValues.entrySet()) {
            String key = defaultEntry.getKey();
            if (targetMap.containsKey(key)) continue;
            String msg = messageProcessor.apply(defaultEntry.getValue());
            targetMap.put(key, msg);
            unloadedKeys.put(key, msg);
        }

        if (!unloadedKeys.isEmpty()) {
            logger.warning("Some localization phrases was not found in " + fileName + ". Used defaults:");
            logValues(logger, unloadedKeys);
        }
        if (!deprecatedKeys.isEmpty()) {
            logger.warning("Some deprecated localization phrases from " + fileName + " was not applied:");
            logValues(logger, deprecatedKeys);
        }
    }

    private static void logValues(@Nonnull Logger logger, @Nonnull Map<String, String> map) {
        map.forEach((key, value) -> logger.warning(key + ": \"" + value.replace("\n", "\\n") + "\""));
    }
}
