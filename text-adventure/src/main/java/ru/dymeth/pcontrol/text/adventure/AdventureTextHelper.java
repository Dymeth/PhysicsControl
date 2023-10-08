package ru.dymeth.pcontrol.text.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Server;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.meta.ItemMeta;
import ru.dymeth.pcontrol.text.CommonColor;
import ru.dymeth.pcontrol.text.CommonDecoration;
import ru.dymeth.pcontrol.text.Text;
import ru.dymeth.pcontrol.text.TextHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AdventureTextHelper implements TextHelper {
    @Nonnull
    public Text create(@Nonnull String text, @Nonnull CommonColor color) {
        return new AdventureText(Component.text(text,
            NamedTextColor.NAMES.value(color.name().toLowerCase())
        ));
    }

    @Nonnull
    @Override
    public Text create(@Nonnull String text, @Nonnull CommonColor color, @Nonnull CommonDecoration decoration) {
        return new AdventureText(Component.text(text,
            NamedTextColor.NAMES.value(color.name().toLowerCase()),
            TextDecoration.NAMES.value(decoration.name().toLowerCase())
        ));
    }

    @Nonnull
    @Override
    public Text fromAmpersandFormat(@Nonnull String ampersandText) {
        return new AdventureText(LegacyComponentSerializer.legacyAmpersand().deserialize(ampersandText));
    }

    @Override
    public void setStackName(@Nonnull ItemMeta meta, @Nonnull Text name) {
        meta.displayName(((AdventureText) name).component());
    }

    @Override
    public void setStackLore(@Nonnull ItemMeta meta, @Nonnull List<Text> lore) {
        List<Component> lines = new ArrayList<>();
        for (Text line : lore) {
            lines.add(((AdventureText) line).component());
        }
        meta.lore(lines);
    }

    @Nonnull
    public Inventory createInventory(@Nonnull Server server, @Nonnull InventoryHolder holder, @Nonnull InventoryType type, @Nonnull Text title) {
        return server.createInventory(holder, type, ((AdventureText) title).component());
    }

    @Nonnull
    public Inventory createInventory(@Nonnull Server server, @Nonnull InventoryHolder holder, int size, @Nonnull Text title) {
        return server.createInventory(holder, size, ((AdventureText) title).component());
    }
}
