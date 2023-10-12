package ru.dymeth.pcontrol.text.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Server;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.meta.ItemMeta;
import ru.dymeth.pcontrol.text.CommonColor;
import ru.dymeth.pcontrol.text.CommonDecoration;
import ru.dymeth.pcontrol.text.Text;
import ru.dymeth.pcontrol.text.TextHelper;
import ru.dymeth.pcontrol.util.FileUtils;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BungeeTextHelper implements TextHelper {

    private static final boolean RGB_SUPPORT = FileUtils.isMethodPresent(ChatColor.class, "of", Color.class);

    @Nonnull
    public Text create(@Nonnull String text, @Nonnull CommonColor color) {
        TextComponent message = new TextComponent(text);
        message.setColor(convert(color));
        return BungeeText.of(message);
    }

    @Nonnull
    @Override
    public Text create(@Nonnull String text, @Nonnull CommonColor color, @Nonnull CommonDecoration decoration) {
        TextComponent message = new TextComponent(text);
        message.setColor(convert(color));
        applyDecoration(message, decoration);
        return BungeeText.of(message);
    }

    @Nonnull
    @Override
    public Text fromAmpersandFormat(@Nonnull String ampersandText) {
        return BungeeText.of(TextComponent.fromLegacyText(
            ChatColor.translateAlternateColorCodes('&', ampersandText)));
    }

    @Override
    public void setStackName(@Nonnull ItemMeta meta, @Nonnull Text name) {
        meta.setDisplayName(((BungeeText) name).sectionText());
    }

    @Override
    public void setStackLore(@Nonnull ItemMeta meta, @Nonnull List<Text> lore) {
        List<String> lines = new ArrayList<>();
        for (Text line : lore) {
            lines.add(((BungeeText) line).sectionText());
        }
        meta.setLore(lines);
    }

    @Nonnull
    public Inventory createInventory(@Nonnull Server server, @Nonnull InventoryHolder holder, @Nonnull InventoryType type, @Nonnull Text title) {
        return server.createInventory(holder, type, ((BungeeText) title).sectionText());
    }

    @Nonnull
    public Inventory createInventory(@Nonnull Server server, @Nonnull InventoryHolder holder, int size, @Nonnull Text title) {
        return server.createInventory(holder, size, ((BungeeText) title).sectionText());
    }

    @Nonnull
    private static ChatColor convert(@Nonnull CommonColor color) {
        if (RGB_SUPPORT) {
            String name = color.name();
            return name == null ? ChatColor.of(color.awtColor()) : ChatColor.of(name);
        } else {
            //noinspection deprecation
            return ChatColor.valueOf(color.nearestName().toUpperCase());
        }
    }

    private static void applyDecoration(@Nonnull BaseComponent component, @Nonnull CommonDecoration decoration) {
        switch (decoration) {
            case OBFUSCATED: {
                component.setObfuscated(true);
                return;
            }
            case BOLD: {
                component.setBold(true);
                return;
            }
            case STRIKETHROUGH: {
                component.setStrikethrough(true);
                return;
            }
            case UNDERLINED: {
                component.setUnderlined(true);
                return;
            }
            case ITALIC: {
                component.setItalic(true);
                return;
            }
            default: {
            }
        }
    }
}
