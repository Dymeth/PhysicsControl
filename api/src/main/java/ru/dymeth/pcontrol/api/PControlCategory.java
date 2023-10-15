package ru.dymeth.pcontrol.api;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.dymeth.pcontrol.text.CommonColor;
import ru.dymeth.pcontrol.text.Text;
import ru.dymeth.pcontrol.text.TextHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum PControlCategory {
    MOBS_INTERACTIONS(
        1, 4, "ZOMBIE_HEAD", "SKULL_ITEM:2"),
    ENTITIES_INTERACTIONS(
        1, 5, "PLAYER_HEAD", "SKULL_ITEM:3"),
    BUILDING(
        1, 6, "LADDER"),

    LIQUIDS(
        2, 3, "WATER_BUCKET"),
    GRAVITY_BLOCKS(
        2, 4, "SAND"),
    WORLD_DESTRUCTION(
        2, 5, "OAK_LEAVES", "LEAVES"),
    GROWING_BLOCKS_AND_SMALL_PLANTS(
        2, 6, "MELON"),
    VINES_AND_TALL_STRUCTURES(
        2, 7, "BIRCH_SAPLING", "SAPLING:2"),

    SETTINGS(
        3, 5, "COMMAND_BLOCK", "COMMAND"),
    TEST(
        3, 9, "OAK_SIGN", "SIGN");

    private final short slot;
    private final ItemStack icon;
    private final List<PControlTrigger> triggers = new ArrayList<>();

    PControlCategory(int row, int column, @Nonnull String... iconVariants) {
        this.slot = (short) ((row - 1) * 9 + column - 1);
        this.icon = MaterialUtils.matchIcon(iconVariants);
    }

    void addTrigger(@Nonnull PControlTrigger trigger) {
        this.triggers.add(trigger);
    }

    @Nonnull
    public List<PControlTrigger> getTriggers() {
        return Collections.unmodifiableList(this.triggers);
    }

    public void prepareIcon(@Nonnull PControlData data) {
        if (this.icon == null) return;

        ItemMeta meta = this.icon.getItemMeta();
        if (meta == null) throw new IllegalArgumentException();

        TextHelper helper = data.getTextHelper();

        helper.setStackName(meta,
            helper.create(data.getCategoryName(this), CommonColor.YELLOW));

        List<Text> lore = new ArrayList<>();
        for (PControlTrigger trigger : this.triggers) {
            if (!trigger.isAvailable()) continue;
            lore.add(helper.create(" - " + data.getTriggerName(trigger), CommonColor.YELLOW));
        }
        helper.setStackLore(meta, lore);

        meta.addItemFlags(ItemFlag.values());

        this.icon.setItemMeta(meta);
    }

    public short getSlot() {
        return this.slot;
    }

    @Nonnull
    public ItemStack getIcon() {
        if (this.icon == null) throw new IllegalArgumentException("Unable to find icon of category " + this);
        return this.icon;
    }
}
