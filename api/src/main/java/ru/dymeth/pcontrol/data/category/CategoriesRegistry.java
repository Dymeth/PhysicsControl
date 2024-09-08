package ru.dymeth.pcontrol.data.category;

import com.google.common.base.Charsets;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.set.material.ItemTypesSet;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CategoriesRegistry {
    private final PControlData data;
    private final Map<String, PControlCategory> valuesByName = new LinkedHashMap<>();
    private final PControlCategory[] allValues;
    private final PControlCategory settingsCategory;
    private final PControlCategory testCategory;

    public CategoriesRegistry(@Nonnull PControlData data) {
        this.data = data;

        this.parseCategories();

        this.allValues = new PControlCategory[this.valuesByName.values().size()];
        int i = 0;
        for (PControlCategory element : this.valuesByName.values()) {
            this.allValues[i++] = element;
        }

        this.settingsCategory = valueOf("SETTINGS");
        this.testCategory = valueOf("TEST");

        for (PControlCategory category : this.values()) {
            try {
                category.getIcon();
            } catch (Throwable t) {
                this.data.log().severe("Unable to find icon of category " + category.name());
            }
        }
    }

    private void parseCategories() {
        InputStream configStream = this.data.getPlugin().getResource("logics/categories.yml");
        if (configStream == null) {
            throw new IllegalArgumentException("Unable to find " + "logics/categories.yml" + " in plugin JAR");
        }
        ConfigurationSection rootSection = YamlConfiguration.loadConfiguration(new InputStreamReader(configStream, Charsets.UTF_8));
        for (String categoryName : rootSection.getKeys(false)) {
            ConfigurationSection categorySection = rootSection.getConfigurationSection(categoryName);
            if (categorySection == null) {
                throw new IllegalArgumentException("Not a section: " + categoryName);
            }

            int row = categorySection.getInt("row");
            int column = categorySection.getInt("column");
            List<String> iconNames = categorySection.isString("icon")
                ? Collections.singletonList(categorySection.getString("icon"))
                : categorySection.getStringList("icon");

            PControlCategory result = new PControlCategory(categoryName,
                row, column,
                ItemTypesSet.create(false, categoryName + " category icon", this.data.log(),
                    this.data.getTypesSetsParser().createItemTypesParser(iconNames, true))
            );
            this.valuesByName.put(categoryName, result);
        }
    }

    @Nonnull
    public PControlCategory[] values() {
        return this.allValues;
    }

    @Nonnull
    public PControlCategory valueOf(String name) {
        PControlCategory result = this.valuesByName.get(name);
        if (result == null) throw new IllegalArgumentException(name);
        return result;
    }

    @Nonnull
    public PControlCategory getSettingsCategory() {
        return this.settingsCategory;
    }

    @Nonnull
    public PControlCategory getTestCategory() {
        return this.testCategory;
    }
}
