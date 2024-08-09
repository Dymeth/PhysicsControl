package ru.dymeth.pcontrol.data;

import com.google.common.base.Charsets;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.dymeth.pcontrol.set.material.BlockTypesSet;
import ru.dymeth.pcontrol.set.parser.TypesSetsParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public final class CustomTags {

    private final TypesSetsParser parser;
    private final ConfigurationSection config;
    private final Map<String, Set<Material>> materialTagsByName = new HashMap<>();

    // System tags
    public final Set<Material>
        WORLD_AIR,
        GRAVITY_BLOCKS;

    // Config tags
    public final Set<Material>
        WOODEN_DOORS,
        PRESSURE_PLATES,
        REDSTONE_PASSIVE_INPUTS,
        REDSTONE_ORE_BLOCKS,
        WATER,
        LAVA,
        SAND,
        GRAVEL,
        ANVIL,
        CONCRETE_POWDERS,
        NATURAL_GRAVITY_BLOCKS,
        BONE_MEAL_HERBS,
        LITTLE_MUSHROOMS,
        BLOCKS_UNDER_WATER_ONLY,
        GRASS_BLOCK,
        DIRT_PATH_BLOCK,
        FARMLAND_BLOCK,
        MYCELIUM_BLOCK,
        SUGAR_CANE_BLOCK,
        NETHER_WART_BLOCK,
        WHEAT_BLOCK,
        POTATO_BLOCK,
        CARROT_BLOCK,
        BEETROOT_BLOCK,
        PUMPKIN_STEM_AND_BLOCK,
        MELON_STEM_AND_BLOCK,
        FENCES,
        SAPLINGS,
        LADDERS,
        SIGNS,
        RAILS,
        TORCHES,
        REDSTONE_TORCHES,
        SOUL_TORCHES,
        ALL_ALIVE_CORALS,
        DEAD_CORAL_PLANTS,
        DEAD_CORALS,
        DEAD_WALL_CORALS,
        DEAD_CORAL_BLOCKS,
        ALL_DEAD_CORALS,
        END_PORTAL_FRAMES;

    public CustomTags(@Nonnull PControlData data) {
        this.parser = new TypesSetsParser(data, this);

        InputStream configStream = data.getPlugin().getResource("logics/tags.yml");
        if (configStream == null) {
            throw new IllegalArgumentException("Unable to find " + "logics/tags.yml" + " in plugin JAR");
        }
        this.config = YamlConfiguration.loadConfiguration(new InputStreamReader(configStream, Charsets.UTF_8));

        WORLD_AIR = initBlocksSet(data, "WORLD_AIR", set -> {
            set.addPrimitive(Material.AIR);
            if (data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.CAVE_AIR);
            }
        });
        GRAVITY_BLOCKS = initBlocksSet(data, "GRAVITY_BLOCKS", set -> {
            set.add(Material::hasGravity);
            if (!data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.DRAGON_EGG);
            }
            if (data.hasVersion(1, 14, 0)) {
                set.addPrimitive(Material.SCAFFOLDING);
            }
        });

        WOODEN_DOORS = parseBlocksSet(data, "WOODEN_DOORS");
        PRESSURE_PLATES = parseBlocksSet(data, "PRESSURE_PLATES");
        REDSTONE_PASSIVE_INPUTS = parseBlocksSet(data, "REDSTONE_PASSIVE_INPUTS");
        REDSTONE_ORE_BLOCKS = parseBlocksSet(data, "REDSTONE_ORE_BLOCKS");
        WATER = parseBlocksSet(data, "WATER");
        LAVA = parseBlocksSet(data, "LAVA");
        SAND = parseBlocksSet(data, "SAND");
        GRAVEL = parseBlocksSet(data, "GRAVEL");
        ANVIL = parseBlocksSet(data, "ANVIL");
        CONCRETE_POWDERS = parseBlocksSet(data, "CONCRETE_POWDERS");
        NATURAL_GRAVITY_BLOCKS = parseBlocksSet(data, "NATURAL_GRAVITY_BLOCKS");
        BONE_MEAL_HERBS = parseBlocksSet(data, "BONE_MEAL_HERBS");
        LITTLE_MUSHROOMS = parseBlocksSet(data, "LITTLE_MUSHROOMS");
        BLOCKS_UNDER_WATER_ONLY = parseBlocksSet(data, "UNDERWATER_BLOCKS_ONLY");
        GRASS_BLOCK = parseBlocksSet(data, "GRASS_BLOCK");
        DIRT_PATH_BLOCK = parseBlocksSet(data, "DIRT_PATH_BLOCK");
        FARMLAND_BLOCK = parseBlocksSet(data, "FARMLAND_BLOCK");
        MYCELIUM_BLOCK = parseBlocksSet(data, "MYCELIUM_BLOCK");
        SUGAR_CANE_BLOCK = parseBlocksSet(data, "SUGAR_CANE_BLOCK");
        NETHER_WART_BLOCK = parseBlocksSet(data, "NETHER_WART_BLOCK");
        WHEAT_BLOCK = parseBlocksSet(data, "WHEAT_BLOCK");
        POTATO_BLOCK = parseBlocksSet(data, "POTATO_BLOCK");
        CARROT_BLOCK = parseBlocksSet(data, "CARROT_BLOCK");
        BEETROOT_BLOCK = parseBlocksSet(data, "BEETROOT_BLOCK");
        PUMPKIN_STEM_AND_BLOCK = parseBlocksSet(data, "PUMPKIN_STEM_AND_BLOCK");
        MELON_STEM_AND_BLOCK = parseBlocksSet(data, "MELON_STEM_AND_BLOCK");
        FENCES = parseBlocksSet(data, "FENCES");
        SAPLINGS = parseBlocksSet(data, "SAPLINGS");
        LADDERS = parseBlocksSet(data, "LADDERS");
        SIGNS = parseBlocksSet(data, "SIGNS");
        RAILS = parseBlocksSet(data, "RAILS");
        TORCHES = parseBlocksSet(data, "TORCHES");
        REDSTONE_TORCHES = parseBlocksSet(data, "REDSTONE_TORCHES");
        SOUL_TORCHES = parseBlocksSet(data, "SOUL_TORCHES");
        ALL_ALIVE_CORALS = parseBlocksSet(data, "ALL_ALIVE_CORALS");
        DEAD_CORAL_PLANTS = parseBlocksSet(data, "DEAD_CORAL_PLANTS");
        DEAD_CORALS = parseBlocksSet(data, "DEAD_CORALS");
        DEAD_WALL_CORALS = parseBlocksSet(data, "DEAD_WALL_CORALS");
        DEAD_CORAL_BLOCKS = parseBlocksSet(data, "DEAD_CORAL_BLOCKS");
        ALL_DEAD_CORALS = parseBlocksSet(data, "ALL_DEAD_CORALS");
        END_PORTAL_FRAMES = parseBlocksSet(data, "END_PORTAL_FRAMES");
    }

    @Nonnull
    private Set<Material> initBlocksSet(@Nonnull PControlData data, @Nonnull String tagName, @Nonnull Consumer<BlockTypesSet> consumer) {
        return this.registerMaterialTag(tagName, BlockTypesSet.createPrimitive(true, tagName, data.log(), consumer));
    }

    @Nonnull
    private Set<Material> parseBlocksSet(@Nonnull PControlData data, @Nonnull String tagName) {
        return this.registerMaterialTag(tagName, BlockTypesSet.createPrimitive(
            true,
            tagName,
            data.log(),
            this.parser.createBlockTypesParser(this.config.getStringList(tagName))
        ));
    }

    @Nonnull
    private Set<Material> registerMaterialTag(@Nonnull String tagName, @Nonnull Set<Material> materials) {
        tagName = tagName.toLowerCase();
        if (this.materialTagsByName.put(tagName, materials) != null) {
            throw new IllegalArgumentException("Duplicate tag: " + tagName);
        }
        return materials;
    }

    @Nullable
    public <T> Set<T> getTag(@Nonnull String tagName, @Nonnull Class<T> clazz) {
        if (clazz == Material.class) {
            //noinspection unchecked
            return (Set<T>) this.materialTagsByName.get(tagName);
        } else {
            throw new IllegalArgumentException("Unsupported tag type");
        }
    }
}
