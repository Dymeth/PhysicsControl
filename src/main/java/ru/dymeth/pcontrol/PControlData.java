package ru.dymeth.pcontrol;

import org.bukkit.World;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;

import javax.annotation.Nonnull;

public interface PControlData {
    @Nonnull
    String getMessage(@Nonnull String key, @Nonnull String... placeholders);

    void cancelIfDisabled(@Nonnull BlockEvent event, @Nonnull PControlTrigger trigger);

    void cancelIfDisabled(@Nonnull Cancellable event, @Nonnull World world, @Nonnull PControlTrigger trigger);

    boolean isActionAllowed(@Nonnull World world, @Nonnull PControlTrigger trigger);
}
