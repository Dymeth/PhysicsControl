package ru.pcontrol.text.bungee;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.dymeth.pcontrol.text.Text;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BungeeText implements Text {

    private final BaseComponent[] components;

    BungeeText(@Nonnull BaseComponent... components) {
        this.components = components;
    }

    @Nonnull
    public String sectionText() {
        return BaseComponent.toLegacyText(this.components);
    }

    @Nonnull
    public BaseComponent[] components() {
        return this.components;
    }

    @Override
    public void send(@Nonnull CommandSender receiver) {
        if (receiver instanceof Player) {
            ((Player) receiver).spigot().sendMessage(this.components);
        } else {
            receiver.sendMessage(this.sectionText());
        }
    }

    @Nonnull
    @Override
    // TODO Support for non-plain-text components
    public List<Text> split(@Nonnull String splitter) {
        List<Text> result = new ArrayList<>();
        String[] elements = BaseComponent.toLegacyText(this.components).split(splitter);
        if (elements.length <= 1) return Collections.singletonList(this);
        for (String element : elements) {
            result.add(new BungeeText(TextComponent.fromLegacyText(element)));
        }
        return result;
    }
}
