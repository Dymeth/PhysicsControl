package ru.dymeth.pcontrol.text.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.dymeth.pcontrol.text.Text;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdventureText implements Text {

    private final Component component;

    AdventureText(@Nonnull Component component) {
        this.component = component;
    }

    @Nonnull
    public Component component() {
        return this.component;
    }

    @Override
    public void send(@NotNull CommandSender receiver) {
        receiver.sendMessage(this.component);
    }

    @NotNull
    @Override
    // TODO Support for non-plain-text components
    public List<Text> split(@NotNull String splitter) {
        LegacyComponentSerializer serializer = LegacyComponentSerializer.legacyAmpersand();
        List<Text> result = new ArrayList<>();
        String[] elements = serializer.serialize(this.component).split(splitter);
        if (elements.length <= 1) return Collections.singletonList(this);
        for (String element : elements) {
            result.add(new AdventureText(serializer.deserialize(element)));
        }
        return result;
    }
}
