package ru.dymeth.pcontrol.text;

import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class NullText implements Text {
    public static NullText INSTANCE = new NullText();

    private NullText() {
    }

    @Override
    public void send(@Nonnull CommandSender receiver) {
    }

    @Nonnull
    @Override
    public List<Text> split(@Nonnull String splitter) {
        return Collections.singletonList(this);
    }
}
