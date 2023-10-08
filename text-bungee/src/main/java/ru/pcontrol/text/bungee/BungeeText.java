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
    private String sectionText = null;

    BungeeText(@Nonnull BaseComponent... components) {
        this.components = components;
    }

    @Nonnull
    public String sectionText() {
        if (this.sectionText == null) {
            this.sectionText = BaseComponent.toLegacyText(this.components);
            if (this.components.length > 0
                && this.components[0].getColorRaw() == null
                && this.sectionText.length() >= 2
            ) {
                this.sectionText = this.sectionText.substring(2);
            }
        }
        return this.sectionText;
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
    public List<Text> split(@Nonnull String regex) {
        List<Text> result = new ArrayList<>();
        String[] elements = this.sectionText().split(regex);
        if (elements.length <= 1) return Collections.singletonList(this);
        for (String element : elements) {
            result.add(new BungeeText(TextComponent.fromLegacyText(element)));
        }
        return result;
    }
}
