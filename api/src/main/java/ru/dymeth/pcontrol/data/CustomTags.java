package ru.dymeth.pcontrol.data;

import org.bukkit.Material;
import org.bukkit.Tag;
import ru.dymeth.pcontrol.set.BlocksSet;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.Consumer;

public final class CustomTags {

    public final Set<Material>
        WORLD_AIR,
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
        GRAVITY_BLOCKS,
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

    @SuppressWarnings("CodeBlock2Expr")
    public CustomTags(@Nonnull PControlData data) {
        WORLD_AIR = blocksSet(data, "WORLD_AIR", set -> {
            set.addPrimitive(Material.AIR);
            if (data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.CAVE_AIR);
            }
        });
        WOODEN_DOORS = blocksSet(data, "WOODEN_DOORS", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("WOODEN_DOOR");
                set.addPrimitive(Material.ACACIA_DOOR);
                set.addPrimitive(Material.BIRCH_DOOR);
                set.addPrimitive(Material.DARK_OAK_DOOR);
                set.addPrimitive(Material.JUNGLE_DOOR);
                set.addPrimitive(Material.SPRUCE_DOOR);
            } else {
                set.addPrimitive(Tag.WOODEN_DOORS.getValues());
            }
        });
        PRESSURE_PLATES = blocksSet(data, "PRESSURE_PLATES", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("STONE_PLATE");
                set.add("GOLD_PLATE");
                set.add("IRON_PLATE");
                set.add("WOOD_PLATE");
            } else {
                set.addPrimitive(Material.STONE_PRESSURE_PLATE);
                set.addPrimitive(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
                set.addPrimitive(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
                set.addPrimitive(Tag.WOODEN_PRESSURE_PLATES.getValues());
            }
            if (data.hasVersion(1, 16, 0)) {
                set.addPrimitive(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE);
            }
        });
        REDSTONE_PASSIVE_INPUTS = blocksSet(data, "REDSTONE_PASSIVE_INPUTS", set -> {
            set.addPrimitive(Material.TRIPWIRE_HOOK);
            set.addPrimitive(Material.TRIPWIRE);
            set.addPrimitive(this.PRESSURE_PLATES);
            if (!data.hasVersion(1, 13, 0)) {
                set.add("WOOD_BUTTON");
                set.addPrimitive(Material.STONE_BUTTON);
            } else {
                set.addPrimitive(Tag.BUTTONS.getValues());
            }
        });
        REDSTONE_ORE_BLOCKS = blocksSet(data, "REDSTONE_ORE_BLOCKS", set -> {
            set.addPrimitive(Material.REDSTONE_ORE);
            if (!data.hasVersion(1, 13, 0)) {
                set.add("GLOWING_REDSTONE_ORE");
            }
            if (data.hasVersion(1, 17, 0)) {
                set.addPrimitive(Material.DEEPSLATE_REDSTONE_ORE);
            }
        });
        WATER = blocksSet(data, "WATER", set -> {
            set.addPrimitive(Material.WATER);
            if (!data.hasVersion(1, 13, 0)) {
                set.add("STATIONARY_WATER");
            }
        });
        LAVA = blocksSet(data, "LAVA", set -> {
            set.addPrimitive(Material.LAVA);
            if (!data.hasVersion(1, 13, 0)) {
                set.add("STATIONARY_LAVA");
            }
        });
        SAND = blocksSet(data, "SAND", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.SAND);
            } else {
                set.addPrimitive(Tag.SAND.getValues());
            }
        });
        GRAVEL = blocksSet(data, "GRAVEL", set -> {
            set.addPrimitive(Material.GRAVEL);
            if (data.hasVersion(1, 20, 0)) {
                set.addPrimitive(Material.SUSPICIOUS_GRAVEL);
            }
        });
        ANVIL = blocksSet(data, "ANVIL", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.ANVIL);
            } else {
                set.addPrimitive(Tag.ANVIL.getValues());
            }
        });
        CONCRETE_POWDERS = blocksSet(data, "CONCRETE_POWDERS", set -> {
            if (data.hasVersion(1, 12, 0)) {
                if (!data.hasVersion(1, 13, 0)) {
                    set.add("CONCRETE_POWDER");
                } else {
                    set.addPrimitive(Material.BLACK_CONCRETE_POWDER);
                    set.addPrimitive(Material.BLUE_CONCRETE_POWDER);
                    set.addPrimitive(Material.BROWN_CONCRETE_POWDER);
                    set.addPrimitive(Material.CYAN_CONCRETE_POWDER);
                    set.addPrimitive(Material.GRAY_CONCRETE_POWDER);
                    set.addPrimitive(Material.GREEN_CONCRETE_POWDER);
                    set.addPrimitive(Material.LIGHT_BLUE_CONCRETE_POWDER);
                    set.addPrimitive(Material.LIGHT_GRAY_CONCRETE_POWDER);
                    set.addPrimitive(Material.LIME_CONCRETE_POWDER);
                    set.addPrimitive(Material.MAGENTA_CONCRETE_POWDER);
                    set.addPrimitive(Material.ORANGE_CONCRETE_POWDER);
                    set.addPrimitive(Material.PINK_CONCRETE_POWDER);
                    set.addPrimitive(Material.PURPLE_CONCRETE_POWDER);
                    set.addPrimitive(Material.RED_CONCRETE_POWDER);
                    set.addPrimitive(Material.WHITE_CONCRETE_POWDER);
                    set.addPrimitive(Material.YELLOW_CONCRETE_POWDER);
                }
            }
        });
        GRAVITY_BLOCKS = blocksSet(data, "GRAVITY_BLOCKS", set -> {
            set.add(Material::hasGravity);
            if (!data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.DRAGON_EGG);
            }
            if (data.hasVersion(1, 14, 0)) {
                set.addPrimitive(Material.SCAFFOLDING);
            }
        });
        NATURAL_GRAVITY_BLOCKS = blocksSet(data, "NATURAL_GRAVITY_BLOCKS", set -> {
            set.addPrimitive(this.SAND);
            set.addPrimitive(this.GRAVEL);
        });
        BONE_MEAL_HERBS = blocksSet(data, "BONE_MEAL_HERBS", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("LONG_GRASS");
            } else if (!data.hasVersion(1, 20, 3)) {
                set.add("GRASS");
            } else {
                set.addPrimitive(Material.SHORT_GRASS);
            }
            if (!data.hasVersion(1, 13, 0)) {
                set.add("YELLOW_FLOWER");
                set.add("RED_ROSE");
            } else {
                set.addPrimitive(Material.DANDELION);
                set.addPrimitive(Material.POPPY);
                set.addPrimitive(Material.BLUE_ORCHID);
                set.addPrimitive(Material.ALLIUM);
                set.addPrimitive(Material.AZURE_BLUET);
                set.addPrimitive(Material.RED_TULIP);
                set.addPrimitive(Material.ORANGE_TULIP);
                set.addPrimitive(Material.WHITE_TULIP);
                set.addPrimitive(Material.PINK_TULIP);
                set.addPrimitive(Material.OXEYE_DAISY);
                set.addPrimitive(Material.SUNFLOWER);
                set.addPrimitive(Material.LILAC);
                set.addPrimitive(Material.ROSE_BUSH);
                set.addPrimitive(Material.PEONY);
            }
            if (data.hasVersion(1, 14, 0)) {
                set.addPrimitive(Material.CORNFLOWER);
                set.addPrimitive(Material.LILY_OF_THE_VALLEY);
            }
        });
        LITTLE_MUSHROOMS = blocksSet(data, "LITTLE_MUSHROOMS", set -> {
            set.addPrimitive(Material.RED_MUSHROOM);
            set.addPrimitive(Material.BROWN_MUSHROOM);
        });
        BLOCKS_UNDER_WATER_ONLY = blocksSet(data, "UNDERWATER_BLOCKS_ONLY", set -> {
            set.addPrimitive(this.WATER);
            if (data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.BUBBLE_COLUMN);
                set.addPrimitive(Material.KELP_PLANT);
                set.addPrimitive(Material.TALL_SEAGRASS);
                set.addPrimitive(Material.SEAGRASS);
            }
        });
        GRASS_BLOCK = blocksSet(data, "GRASS_BLOCK", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("GRASS");
            } else {
                set.addPrimitive(Material.GRASS_BLOCK);
            }
        });
        DIRT_PATH_BLOCK = blocksSet(data, "DIRT_PATH_BLOCK", set -> {
            if (data.hasVersion(1, 9, 0)) {
                if (!data.hasVersion(1, 17, 0)) {
                    set.add("GRASS_PATH");
                } else {
                    set.addPrimitive(Material.DIRT_PATH);
                }
            }
        });
        FARMLAND_BLOCK = blocksSet(data, "FARMLAND_BLOCK", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("SOIL");
            } else {
                set.addPrimitive(Material.FARMLAND);
            }
        });
        MYCELIUM_BLOCK = blocksSet(data, "MYCELIUM_BLOCK", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("MYCEL");
            } else {
                set.addPrimitive(Material.MYCELIUM);
            }
        });
        SUGAR_CANE_BLOCK = blocksSet(data, "SUGAR_CANE_BLOCK", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("SUGAR_CANE_BLOCK");
            } else {
                set.addPrimitive(Material.SUGAR_CANE);
            }
        });
        NETHER_WART_BLOCK = blocksSet(data, "NETHER_WART_BLOCK", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("NETHER_WARTS");
            } else {
                set.addPrimitive(Material.NETHER_WART);
            }
        });
        WHEAT_BLOCK = blocksSet(data, "WHEAT_BLOCK", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("CROPS");
            } else {
                set.addPrimitive(Material.WHEAT);
            }
        });
        POTATO_BLOCK = blocksSet(data, "POTATO_BLOCK", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("POTATO");
            } else {
                set.addPrimitive(Material.POTATOES);
            }
        });
        CARROT_BLOCK = blocksSet(data, "CARROT_BLOCK", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("CARROT");
            } else {
                set.addPrimitive(Material.CARROTS);
            }
        });
        BEETROOT_BLOCK = blocksSet(data, "BEETROOT_BLOCK", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                if (data.hasVersion(1, 9, 0)) {
                    set.add("BEETROOT_BLOCK");
                }
            } else {
                set.addPrimitive(Material.BEETROOTS);
            }
        });
        PUMPKIN_STEM_AND_BLOCK = blocksSet(data, "PUMPKIN_STEM_AND_BLOCK", set -> {
            set.addPrimitive(Material.PUMPKIN_STEM);
            set.addPrimitive(Material.PUMPKIN);
        });
        MELON_STEM_AND_BLOCK = blocksSet(data, "MELON_STEM_AND_BLOCK", set -> {
            set.addPrimitive(Material.MELON_STEM);
            if (!data.hasVersion(1, 13, 0)) {
                set.add("MELON_BLOCK");
            } else {
                set.addPrimitive(Material.MELON);
            }
        });
        FENCES = blocksSet(data, "FENCES", set -> {
            if (!data.hasVersion(1, 14, 0)) {
                set.addPrimitive(Material.ACACIA_FENCE);
                set.addPrimitive(Material.BIRCH_FENCE);
                set.addPrimitive(Material.DARK_OAK_FENCE);
                set.addPrimitive(Material.JUNGLE_FENCE);
                set.addPrimitive(Material.SPRUCE_FENCE);
                if (!data.hasVersion(1, 13, 0)) {
                    set.add("FENCE");
                    set.add("NETHER_FENCE");
                    // set.add("IRON_FENCE");
                } else {
                    set.addPrimitive(Material.OAK_FENCE);
                    set.addPrimitive(Material.NETHER_BRICK_FENCE);
                    // set.add(Material.IRON_BARS);
                }
            } else {
                // set.add(Material.IRON_BARS);
                set.addPrimitive(Tag.FENCES.getValues());
            }
        });
        SAPLINGS = blocksSet(data, "SAPLINGS", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("SAPLING");
            } else {
                set.addPrimitive(Tag.SAPLINGS.getValues());
            }
        });
        LADDERS = blocksSet(data, "LADDERS", set -> {
            set.addPrimitive(Material.LADDER);
        });
        SIGNS = blocksSet(data, "SIGNS", set -> {
            if (!data.hasVersion(1, 14, 0)) {
                set.add("SIGN");
                set.add("SIGN_POST");
                set.add("WALL_SIGN");
            } else {
                set.addPrimitive(Tag.STANDING_SIGNS.getValues());
                set.addPrimitive(Tag.WALL_SIGNS.getValues());
                set.addPrimitive(Tag.SIGNS.getValues());
            }
            if (data.hasVersion(1, 20, 0)) {
                set.addPrimitive(Tag.CEILING_HANGING_SIGNS.getValues());
                set.addPrimitive(Tag.WALL_HANGING_SIGNS.getValues());
                set.addPrimitive(Tag.ALL_HANGING_SIGNS.getValues());
                set.addPrimitive(Tag.ALL_SIGNS.getValues());
            }
        });
        RAILS = blocksSet(data, "RAILS", set -> {
            set.addPrimitive(Material.ACTIVATOR_RAIL);
            set.addPrimitive(Material.DETECTOR_RAIL);
            set.addPrimitive(Material.POWERED_RAIL);
            if (!data.hasVersion(1, 13, 0)) {
                set.add("RAILS");
            } else {
                set.addPrimitive(Material.RAIL);
            }
        });
        TORCHES = blocksSet(data, "TORCHES", set -> {
            set.addPrimitive(Material.TORCH);
            if (data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.TORCH);
                set.addPrimitive(Material.WALL_TORCH);
            }
        });
        REDSTONE_TORCHES = blocksSet(data, "REDSTONE_TORCHES", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("REDSTONE_TORCH_ON");
                set.add("REDSTONE_TORCH_OFF");
            } else {
                set.addPrimitive(Material.REDSTONE_TORCH);
                set.addPrimitive(Material.REDSTONE_WALL_TORCH);
            }
        });
        SOUL_TORCHES = blocksSet(data, "SOUL_TORCHES", set -> {
            if (data.hasVersion(1, 16, 0)) {
                set.addPrimitive(Material.SOUL_TORCH);
                set.addPrimitive(Material.SOUL_WALL_TORCH);
            }
        });
        ALL_ALIVE_CORALS = blocksSet(data, "ALL_ALIVE_CORALS", set -> {
            if (data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Tag.CORAL_BLOCKS.getValues());
                set.addPrimitive(Tag.WALL_CORALS.getValues());
                set.addPrimitive(Tag.CORALS.getValues()); // Contains Tag.CORAL_PLANTS
            }
        });
        DEAD_CORAL_PLANTS = blocksSet(data, "DEAD_CORAL_PLANTS", set -> {
            if (data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.DEAD_TUBE_CORAL);
                set.addPrimitive(Material.DEAD_BRAIN_CORAL);
                set.addPrimitive(Material.DEAD_BUBBLE_CORAL);
                set.addPrimitive(Material.DEAD_FIRE_CORAL);
                set.addPrimitive(Material.DEAD_HORN_CORAL);
            }
        });
        DEAD_CORALS = blocksSet(data, "DEAD_CORALS", set -> {
            if (data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.DEAD_TUBE_CORAL_FAN);
                set.addPrimitive(Material.DEAD_BRAIN_CORAL_FAN);
                set.addPrimitive(Material.DEAD_BUBBLE_CORAL_FAN);
                set.addPrimitive(Material.DEAD_FIRE_CORAL_FAN);
                set.addPrimitive(Material.DEAD_HORN_CORAL_FAN);
                set.addPrimitive(this.DEAD_CORAL_PLANTS);
            }
        });
        DEAD_WALL_CORALS = blocksSet(data, "DEAD_WALL_CORALS", set -> {
            if (data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.DEAD_TUBE_CORAL_WALL_FAN);
                set.addPrimitive(Material.DEAD_BRAIN_CORAL_WALL_FAN);
                set.addPrimitive(Material.DEAD_BUBBLE_CORAL_WALL_FAN);
                set.addPrimitive(Material.DEAD_FIRE_CORAL_WALL_FAN);
                set.addPrimitive(Material.DEAD_HORN_CORAL_WALL_FAN);
            }
        });
        DEAD_CORAL_BLOCKS = blocksSet(data, "DEAD_CORAL_BLOCKS", set -> {
            if (data.hasVersion(1, 13, 0)) {
                set.addPrimitive(Material.DEAD_TUBE_CORAL_BLOCK);
                set.addPrimitive(Material.DEAD_BRAIN_CORAL_BLOCK);
                set.addPrimitive(Material.DEAD_BUBBLE_CORAL_BLOCK);
                set.addPrimitive(Material.DEAD_FIRE_CORAL_BLOCK);
                set.addPrimitive(Material.DEAD_HORN_CORAL_BLOCK);
            }
        });
        ALL_DEAD_CORALS = blocksSet(data, "ALL_DEAD_CORALS", set -> {
            if (data.hasVersion(1, 13, 0)) {
                set.addPrimitive(this.DEAD_CORAL_BLOCKS);
                set.addPrimitive(this.DEAD_WALL_CORALS);
                set.addPrimitive(this.DEAD_CORALS); // Contains this.DEAD_CORAL_PLANTS
            }
        });
        END_PORTAL_FRAMES = blocksSet(data, "END_PORTAL_FRAMES", set -> {
            if (!data.hasVersion(1, 13, 0)) {
                set.add("ENDER_PORTAL_FRAME");
            } else {
                set.addPrimitive(Material.END_PORTAL_FRAME);
            }
        });
    }

    @Nonnull
    public Set<Material> blocksSet(@Nonnull PControlData data, @Nonnull String setName, @Nonnull Consumer<BlocksSet> consumer) {
        return BlocksSet.createPrimitive(setName, data.log(), consumer);
    }
}
