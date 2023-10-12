package ru.dymeth.pcontrol.text.bungee;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.dymeth.pcontrol.text.Text;
import ru.dymeth.pcontrol.util.FileUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BungeeText implements Text {

    private static final boolean CONTENT_SUPPORT = FileUtils.isClassPresent("net.md_5.bungee.api.chat.hover.content.Content");

    private final BaseComponent[] components;
    private String sectionText = null;

    @Nonnull
    static BungeeText of(@Nonnull BaseComponent... components) {
        if (CONTENT_SUPPORT) {
            return new BungeeContentText(components);
        } else {
            return new BungeeText(components);
        }
    }

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
            result.add(BungeeText.of(TextComponent.fromLegacyText(element)));
        }
        return result;
    }

    @Nonnull
    @Override
    public BungeeText setClickCommand(@Nonnull String command) {
        for (BaseComponent component : this.components) {
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        }
        return this;
    }

    @Nonnull
    @Override
    public BungeeText setHoverText(@Nonnull Text text) {
        HoverEvent hoverEvent = ((BungeeText) text).createHover(HoverEvent.Action.SHOW_TEXT);
        for (BaseComponent component : this.components) {
            component.setHoverEvent(hoverEvent);
        }
        return this;
    }

    @Nonnull
    protected HoverEvent createHover(@SuppressWarnings("SameParameterValue") @Nonnull HoverEvent.Action action) {
        //noinspection deprecation
        return new HoverEvent(action, this.components);
    }

}
