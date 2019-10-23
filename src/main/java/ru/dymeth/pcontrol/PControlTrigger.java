package ru.dymeth.pcontrol;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum PControlTrigger {
    SAND_FALLING(false, 1, 2, "SAND"),
    GRAVEL_FALLING(false, 1, 3, "GRAVEL"),
    ANVILS_FALLING(false, 1, 4, "ANVIL"),
    CONCRETE_POWDERS_FALLING(12, false, 1, 5, "LIME_CONCRETE_POWDER", "CONCRETE_POWDER:5"),
    WATER_FLOWING(false, 1, 7, "WATER_BUCKET"),
    LAVA_FLOWING(false, 1, 8, "LAVA_BUCKET"),

    LADDERS_DESTROYING(false, 2, 2, "LADDER"),
    SIGNS_DESTROYING(false, 2, 3, "SIGN"),
    RAILS_DESTROYING(13, false, 2, 4, "RAIL", "RAILS"),
    TORCHES_DESTROYING(false, 2, 5, "TORCH"),
    REDSTONE_TORCHES_DESTROYING(false, 2, 6, "REDSTONE_TORCH", "REDSTONE_TORCH_ON"),
    BLOCK_HIT_PROJECTILES_REMOVING(false, 2, 8, "ARROW"),

    FIRE_SPREADING(true, 3, 1, "FLINT_AND_STEEL"),
    LEAVES_DECAY(true, 3, 2, "OAK_LEAVES", "LEAVES"),
    CORALS_DRYING(13, true, 3, 3, "FIRE_CORAL"),
    SNOW_MELTING(true, 3, 4, "SNOW_BLOCK"),
    ICE_MELTING(true, 3, 5, "ICE"),
    FARMLANDS_DRYING(true, 3, 6, "FARMLAND", "SOIL"),
    FARMLANDS_TRAMPLING(true, 3, 7, "FARMLAND", "SOIL"),
    SHEEPS_EATING_GRASS(true, 3, 8, "WHITE_WOOL", "WOOL"),
    ENDERMANS_GRIEFING(true, 3, 9, "ENDER_PEARL"),

    GRASS_SPREADING(true, 4, 1, "GRASS_BLOCK", "GRASS"),
    MYCELIUM_SPREADING(true, 4, 2, "MYCELIUM", "MYCEL"),
    WHEAT_GROWING(true, 4, 3, "WHEAT"),
    POTATOES_GROWING(true, 4, 4, "POTATO_ITEM", "POTATO"),
    CARROTS_GROWING(true, 4, 5, "CARROT_ITEM", "CARROT"),
    BEETROOTS_GROWING(9, true, 4, 6, "BEETROOT"),
    PUMPKINS_GROWING(true, 4, 7, "PUMPKIN"),
    MELONS_GROWING(true, 4, 8, "MELON_BLOCK", "MELON"),
    NETHER_WARTS_GROWING(true, 4, 9, "NETHER_WART", "NETHER_STALK"),

    KELPS_GROWING(13, true, 5, 1, "KELP"),
    SUGAR_CANE_GROWING(true, 5, 2, "SUGAR_CANE"),
    CACTUS_GROWING(true, 5, 3, "CACTUS"),
    TREES_GROWING(true, 5, 4, "BIRCH_SAPLING", "SAPLING:2"),
    VINES_GROWING(true, 5, 5, "VINE"),
    COCOAS_GROWING(true, 5, 6, "COCOA_BEANS", "INK_SACK:3"),
    LITTLE_MUSHROOMS_SPREADING(true, 5, 7, "RED_MUSHROOM"),
    GIANT_MUSHROOMS_GROWING(true, 5, 8, "RED_MUSHROOM_BLOCK", "HUGE_MUSHROOM_2"),
    CHORUSES_GROWING(9, true, 5, 9, "CHORUS_FLOWER"),

    DEBUG_MESSAGES(true, 6, 4, true, "COMMAND_BLOCK", "COMMAND"),
    ALLOW_UNRECOGNIZED_ACTIONS(true, 6, 6, "COMMAND_BLOCK", "COMMAND");

    private final short minVersion;
    private final ItemStack icon;
    private final boolean realtime;
    private final short slot;
    private final boolean defaults;

    PControlTrigger(boolean realtime, int row, int column, @Nonnull String... iconVariants) {
        this(-1, realtime, row, column, false, iconVariants);
    }

    PControlTrigger(int minVersion, boolean realtime, int row, int column, @Nonnull String... iconVariants) {
        this(minVersion, realtime, row, column, false, iconVariants);
    }

    PControlTrigger(boolean realtime, int row, int column, boolean defaults, @Nonnull String... iconVariants) {
        this(-1, realtime, row, column, defaults, iconVariants);
    }

    PControlTrigger(int minVersion, boolean realtime, int row, int column, boolean defaults, @Nonnull String... iconVariants) {
        this.minVersion = (short) minVersion;
        this.realtime = realtime;
        this.slot = (short) ((row - 1) * 9 + column - 1);
        this.defaults = defaults;
        this.icon = this.createIcon(iconVariants);
    }

    @Nullable
    private ItemStack createIcon(@Nonnull String... iconVariants) {
        try {
            Byte data = null;
            Material iconMaterial = null;
            for (String iconVariant : iconVariants) {
                int damageSplitter = iconVariant.indexOf(':');
                if (damageSplitter >= 0) {
                    data = Byte.parseByte(iconVariant.substring(damageSplitter + 1));
                    iconVariant = iconVariant.substring(0, damageSplitter);
                } else {
                    data = null;
                }
                iconMaterial = Material.matchMaterial(iconVariant);
                if (iconMaterial != null) break;
            }
            if (iconMaterial == null) return null;
            if (data == null) return new ItemStack(iconMaterial);
            return new ItemStack(iconMaterial, 1, (short) 0, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public short getMinVersion() {
        return this.minVersion;
    }

    public String getDisplayName() {
        String name = this.name().toLowerCase().replace("_", " ");
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    @Nullable
    public ItemStack getIcon() {
        return this.icon;
    }

    public boolean isRealtime() {
        return this.realtime;
    }

    public short getSlot() {
        return this.slot;
    }

    public boolean getDefaultValue() {
        return this.defaults;
    }
}
