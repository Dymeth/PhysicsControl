package ru.dymeth.pcontrol.data.category;

import org.bukkit.Material;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.set.ItemsSet;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CategoriesRegistry {

    public final PControlCategory
        MOBS_INTERACTIONS,
        ENTITIES_INTERACTIONS,
        BUILDING,
        LIQUIDS,
        GRAVITY_BLOCKS,
        WORLD_DESTRUCTION,
        GROWING_BLOCKS_AND_SMALL_PLANTS,
        VINES_AND_TALL_STRUCTURES,
        SETTINGS,
        TEST;

    private final PControlData data;
    private final Map<String, PControlCategory> valuesByName = new LinkedHashMap<>();
    private final PControlCategory[] allValues;

    public CategoriesRegistry(@Nonnull PControlData data) {
        this.data = data;

        MOBS_INTERACTIONS = reg("MOBS_INTERACTIONS", 1, 4, set -> {
            if (!data.hasVersion(13)) {
                set.add("SKULL_ITEM:2");
            } else {
                set.addPrimitive(Material.ZOMBIE_HEAD);
            }
        });
        ENTITIES_INTERACTIONS = reg("ENTITIES_INTERACTIONS", 1, 5, set -> {
            if (!data.hasVersion(13)) {
                set.add("SKULL_ITEM:3");
            } else {
                set.addPrimitive(Material.PLAYER_HEAD);
            }
        });
        BUILDING = reg("BUILDING", 1, 6, set -> {
            set.addPrimitive(Material.LADDER);
        });
        LIQUIDS = reg("LIQUIDS", 2, 3, set -> {
            set.addPrimitive(Material.WATER_BUCKET);
        });
        GRAVITY_BLOCKS = reg("GRAVITY_BLOCKS", 2, 4, set -> {
            set.addPrimitive(Material.SAND);
        });
        WORLD_DESTRUCTION = reg("WORLD_DESTRUCTION", 2, 5, set -> {
            if (!data.hasVersion(13)) {
                set.add("LEAVES");
            } else {
                set.addPrimitive(Material.OAK_LEAVES);
            }
        });
        GROWING_BLOCKS_AND_SMALL_PLANTS = reg("GROWING_BLOCKS_AND_SMALL_PLANTS", 2, 6, set -> {
            set.addPrimitive(Material.MELON);
        });
        VINES_AND_TALL_STRUCTURES = reg("VINES_AND_TALL_STRUCTURES", 2, 7, set -> {
            if (!data.hasVersion(13)) {
                set.add("SAPLING:2");
            } else {
                set.addPrimitive(Material.BIRCH_SAPLING);
            }
        });
        SETTINGS = reg("SETTINGS", 3, 5, set -> {
            if (!data.hasVersion(13)) {
                set.add("COMMAND");
            } else {
                set.addPrimitive(Material.COMMAND_BLOCK);
            }
        });
        TEST = reg("TEST", 3, 9, set -> {
            if (!data.hasVersion(14)) {
                set.add("SIGN");
            } else {
                set.addPrimitive(Material.OAK_SIGN);
            }
        });

        this.allValues = new PControlCategory[this.valuesByName.values().size()];
        int i = 0;
        for (PControlCategory element : this.valuesByName.values()) {
            this.allValues[i++] = element;
        }
    }

    @Nonnull
    private PControlCategory reg(@Nonnull String categoryName,
                                 int row, int column,
                                 @Nonnull Consumer<ItemsSet> iconConsumer
    ) {
        PControlCategory result = new PControlCategory(categoryName,
            row, column,
            ItemsSet.create(categoryName + " category icon", this.data.log(), iconConsumer)
        );
        this.valuesByName.put(categoryName, result);
        return result;
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

}
