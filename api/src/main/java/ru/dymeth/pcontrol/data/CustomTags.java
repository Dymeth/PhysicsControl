package ru.dymeth.pcontrol.data;

import com.google.common.base.Charsets;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.dymeth.pcontrol.set.material.BlockTypesSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public final class CustomTags {

    private final PControlData data;
    private final ConfigurationSection config;
    private final Map<String, Set<Material>> materialTagsByName = new HashMap<>();

    // System tags
    public final Set<Material>
        world_air,
        gravity_blocks;

    // Config tags
    public Set<Material>
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
        this.data = data;

        InputStream configStream = data.getPlugin().getResource("logics/tags.yml");
        if (configStream == null) {
            throw new IllegalArgumentException("Unable to find " + "logics/tags.yml" + " in plugin JAR");
        }
        this.config = YamlConfiguration.loadConfiguration(new InputStreamReader(configStream, Charsets.UTF_8));

        this.world_air = initBlocksSet("world_air", set -> {
            set.addPrimitive(Material.AIR);
            if (data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.CAVE_AIR);
            }
        });
        this.gravity_blocks = initBlocksSet("gravity_blocks", set -> {
            set.add(Material::hasGravity);
            if (!data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.DRAGON_EGG);
            }
            if (data.hasVersion(1, 14, 0)) {
                set.addPrimitive(Material.SCAFFOLDING);
            }
        });
    }

    public void parseTags() {
        this.wooden_doors = parseBlocksSet("wooden_doors");
        this.pressure_plates = parseBlocksSet("pressure_plates");
        this.redstone_passive_inputs = parseBlocksSet("redstone_passive_inputs");
        this.redstone_ore_blocks = parseBlocksSet("redstone_ore_blocks");
        this.water = parseBlocksSet("water");
        this.lava = parseBlocksSet("lava");
        this.sand = parseBlocksSet("sand");
        this.gravel = parseBlocksSet("gravel");
        this.anvil = parseBlocksSet("anvil");
        this.concrete_powders = parseBlocksSet("concrete_powders");
        this.natural_gravity_blocks = parseBlocksSet("natural_gravity_blocks");
        this.bone_meal_herbs = parseBlocksSet("bone_meal_herbs");
        this.little_mushrooms = parseBlocksSet("little_mushrooms");
        this.blocks_under_water_only = parseBlocksSet("blocks_under_water_only");
        this.grass_block = parseBlocksSet("grass_block");
        this.dirt_path_block = parseBlocksSet("dirt_path_block");
        this.farmland_block = parseBlocksSet("farmland_block");
        this.mycelium_block = parseBlocksSet("mycelium_block");
        this.sugar_cane_block = parseBlocksSet("sugar_cane_block");
        this.nether_wart_block = parseBlocksSet("nether_wart_block");
        this.wheat_block = parseBlocksSet("wheat_block");
        this.potato_block = parseBlocksSet("potato_block");
        this.carrot_block = parseBlocksSet("carrot_block");
        this.beetroot_block = parseBlocksSet("beetroot_block");
        this.pumpkin_stem_and_block = parseBlocksSet("pumpkin_stem_and_block");
        this.melon_stem_and_block = parseBlocksSet("melon_stem_and_block");
        this.fences = parseBlocksSet("fences");
        this.saplings = parseBlocksSet("saplings");
        this.ladders = parseBlocksSet("ladders");
        this.signs = parseBlocksSet("signs");
        this.rails = parseBlocksSet("rails");
        this.torches = parseBlocksSet("torches");
        this.redstone_torches = parseBlocksSet("redstone_torches");
        this.soul_torches = parseBlocksSet("soul_torches");
        this.all_alive_corals = parseBlocksSet("all_alive_corals");
        this.dead_coral_plants = parseBlocksSet("dead_coral_plants");
        this.dead_corals = parseBlocksSet("dead_corals");
        this.dead_wall_corals = parseBlocksSet("dead_wall_corals");
        this.dead_coral_blocks = parseBlocksSet("dead_coral_blocks");
        this.all_dead_corals = parseBlocksSet("all_dead_corals");
        this.end_portal_frames = parseBlocksSet("end_portal_frames");
    }

    @Nonnull
    private Set<Material> initBlocksSet(@Nonnull String tagName, @Nonnull Consumer<BlockTypesSet> consumer) {
        return this.registerMaterialTag(tagName, BlockTypesSet.createPrimitive(true, tagName, this.data.log(), consumer));
    }

    @Nonnull
    private Set<Material> parseBlocksSet(@Nonnull String tagName) {
        return this.registerMaterialTag(tagName, BlockTypesSet.createPrimitive(
            true,
            tagName,
            this.data.log(),
            this.data.getTypesSetsParser().createBlockTypesParser(this.config.getStringList(tagName), true)
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
