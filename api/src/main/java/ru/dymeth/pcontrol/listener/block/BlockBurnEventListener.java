package ru.dymeth.pcontrol.listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBurnEvent;
import ru.dymeth.pcontrol.PhysicsListener;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.EventsListenerParser;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;

import javax.annotation.Nonnull;

public class BlockBurnEventListener extends PhysicsListener {

    private final PControlTrigger triggerFireSpreading
        = this.data.getTriggersRegisty().valueOf("FIRE_SPREADING");

    public BlockBurnEventListener(@Nonnull PControlData data, @Nonnull EventsListenerParser parser) {
        super(data);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockBurnEvent event) {
        this.data.cancelIfDisabled(event, this.triggerFireSpreading);
    }
}
