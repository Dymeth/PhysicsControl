package ru.dymeth.pcontrol.text.bungee;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;

import javax.annotation.Nonnull;

public class BungeeContentText extends BungeeText {
    private final Content content;

    BungeeContentText(@Nonnull BaseComponent... components) {
        super(components);
        this.content = new Text(components);
    }

    @Nonnull
    @Override
    protected HoverEvent createHover(@Nonnull HoverEvent.Action action) {
        return new HoverEvent(action, this.content);
    }
}
