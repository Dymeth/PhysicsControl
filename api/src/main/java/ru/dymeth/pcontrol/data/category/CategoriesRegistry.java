package ru.dymeth.pcontrol.data.category;

import org.bukkit.Material;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.set.material.ItemTypesSet;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CategoriesRegistry {

    public final PControlCategory
        MOBS_INTERACTIONS,
        PLAYERS_INTERACTIONS,
        ENTITIES_INTERACTIONS,
        BUILDING,
        GRAVITY_AND_LIQUIDS,
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
            if (!data.hasVersion(1, 13, 0)) {
                set.add("SKULL_ITEM:2");
            } else {
                set.addPrimitive(Material.ZOMBIE_HEAD);
            }
        });
        PLAYERS_INTERACTIONS = reg("PLAYERS_INTERACTIONS", 1, 5, set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("SKULL_ITEM:3");
            } else {
                set.addPrimitive(Material.PLAYER_HEAD);
            }
        });
        ENTITIES_INTERACTIONS = reg("ENTITIES_INTERACTIONS", 1, 6, set -> {
            set.addPrimitive(Material.ARROW);
        });
        BUILDING = reg("BUILDING", 2, 3, set -> {
            set.addPrimitive(Material.LADDER);
        });
        GRAVITY_AND_LIQUIDS = reg("GRAVITY_AND_LIQUIDS", 2, 4, set -> {
            set.addPrimitive(Material.SAND);
        });
        WORLD_DESTRUCTION = reg("WORLD_DESTRUCTION", 2, 5, set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("LEAVES");
            } else {
                set.addPrimitive(Material.OAK_LEAVES);
            }
        });
        GROWING_BLOCKS_AND_SMALL_PLANTS = reg("GROWING_BLOCKS_AND_SMALL_PLANTS", 2, 6, set -> {
            set.addPrimitive(Material.MELON);
        });
        VINES_AND_TALL_STRUCTURES = reg("VINES_AND_TALL_STRUCTURES", 2, 7, set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("SAPLING:2");
            } else {
                set.addPrimitive(Material.BIRCH_SAPLING);
            }
        });
        SETTINGS = reg("SETTINGS", 3, 5, set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("COMMAND");
            } else {
                set.addPrimitive(Material.COMMAND_BLOCK);
            }
        });
        TEST = reg("TEST", 3, 9, set -> {
            if (!data.hasVersion(1, 14, 0)) {
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
                                 @Nonnull Consumer<ItemTypesSet> iconConsumer
    ) {
        PControlCategory result = new PControlCategory(categoryName,
            row, column,
            ItemTypesSet.create(false, categoryName + " category icon", this.data.log(), iconConsumer)
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
