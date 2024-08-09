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
        world_air,
        gravity_blocks;

    // Config tags
    public final Set<Material>
        wooden_doors,
        pressure_plates,
        redstone_passive_inputs,
        redstone_ore_blocks,
        water,
        lava,
        sand,
        gravel,
        anvil,
        concrete_powders,
        natural_gravity_blocks,
        bone_meal_herbs,
        little_mushrooms,
        blocks_under_water_only,
        grass_block,
        dirt_path_block,
        farmland_block,
        mycelium_block,
        sugar_cane_block,
        nether_wart_block,
        wheat_block,
        potato_block,
        carrot_block,
        beetroot_block,
        pumpkin_stem_and_block,
        melon_stem_and_block,
        fences,
        saplings,
        ladders,
        signs,
        rails,
        torches,
        redstone_torches,
        soul_torches,
        all_alive_corals,
        dead_coral_plants,
        dead_corals,
        dead_wall_corals,
        dead_coral_blocks,
        all_dead_corals,
        end_portal_frames;

    public CustomTags(@Nonnull PControlData data) {
        this.parser = new TypesSetsParser(data, this);

        InputStream configStream = data.getPlugin().getResource("logics/tags.yml");
        if (configStream == null) {
            throw new IllegalArgumentException("Unable to find " + "logics/tags.yml" + " in plugin JAR");
        }
        this.config = YamlConfiguration.loadConfiguration(new InputStreamReader(configStream, Charsets.UTF_8));

        world_air = initBlocksSet(data, "world_air", set -> {
            set.addPrimitive(Material.AIR);
            if (data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.CAVE_AIR);
            }
        });
        gravity_blocks = initBlocksSet(data, "gravity_blocks", set -> {
            set.add(Material::hasGravity);
            if (!data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.DRAGON_EGG);
            }
            if (data.hasVersion(1, 14, 0)) {
                set.addPrimitive(Material.SCAFFOLDING);
            }
        });

        wooden_doors = parseBlocksSet(data, "wooden_doors");
        pressure_plates = parseBlocksSet(data, "pressure_plates");
        redstone_passive_inputs = parseBlocksSet(data, "redstone_passive_inputs");
        redstone_ore_blocks = parseBlocksSet(data, "redstone_ore_blocks");
        water = parseBlocksSet(data, "water");
        lava = parseBlocksSet(data, "lava");
        sand = parseBlocksSet(data, "sand");
        gravel = parseBlocksSet(data, "gravel");
        anvil = parseBlocksSet(data, "anvil");
        concrete_powders = parseBlocksSet(data, "concrete_powders");
        natural_gravity_blocks = parseBlocksSet(data, "natural_gravity_blocks");
        bone_meal_herbs = parseBlocksSet(data, "bone_meal_herbs");
        little_mushrooms = parseBlocksSet(data, "little_mushrooms");
        blocks_under_water_only = parseBlocksSet(data, "underwater_blocks_only");
        grass_block = parseBlocksSet(data, "grass_block");
        dirt_path_block = parseBlocksSet(data, "dirt_path_block");
        farmland_block = parseBlocksSet(data, "farmland_block");
        mycelium_block = parseBlocksSet(data, "mycelium_block");
        sugar_cane_block = parseBlocksSet(data, "sugar_cane_block");
        nether_wart_block = parseBlocksSet(data, "nether_wart_block");
        wheat_block = parseBlocksSet(data, "wheat_block");
        potato_block = parseBlocksSet(data, "potato_block");
        carrot_block = parseBlocksSet(data, "carrot_block");
        beetroot_block = parseBlocksSet(data, "beetroot_block");
        pumpkin_stem_and_block = parseBlocksSet(data, "pumpkin_stem_and_block");
        melon_stem_and_block = parseBlocksSet(data, "melon_stem_and_block");
        fences = parseBlocksSet(data, "fences");
        saplings = parseBlocksSet(data, "saplings");
        ladders = parseBlocksSet(data, "ladders");
        signs = parseBlocksSet(data, "signs");
        rails = parseBlocksSet(data, "rails");
        torches = parseBlocksSet(data, "torches");
        redstone_torches = parseBlocksSet(data, "redstone_torches");
        soul_torches = parseBlocksSet(data, "soul_torches");
        all_alive_corals = parseBlocksSet(data, "all_alive_corals");
        dead_coral_plants = parseBlocksSet(data, "dead_coral_plants");
        dead_corals = parseBlocksSet(data, "dead_corals");
        dead_wall_corals = parseBlocksSet(data, "dead_wall_corals");
        dead_coral_blocks = parseBlocksSet(data, "dead_coral_blocks");
        all_dead_corals = parseBlocksSet(data, "all_dead_corals");
        end_portal_frames = parseBlocksSet(data, "end_portal_frames");
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
            return (Set<T>) this.materialTagsByName.get(tagName.toLowerCase());
        } else {
            throw new IllegalArgumentException("Unsupported tag type");
        }
    }
}
