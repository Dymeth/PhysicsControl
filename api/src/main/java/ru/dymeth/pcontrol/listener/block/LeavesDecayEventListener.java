package ru.dymeth.pcontrol.listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.LeavesDecayEvent;
import ru.dymeth.pcontrol.PhysicsListener;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.EventsListenerParser;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;

import javax.annotation.Nonnull;

public class LeavesDecayEventListener extends PhysicsListener {

    private final PControlTrigger triggerLeavesDecay
        = this.data.getTriggersRegisty().valueOf("LEAVES_DECAY");

    public LeavesDecayEventListener(@Nonnull PControlData data, @Nonnull EventsListenerParser parser) {
        super(data);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(LeavesDecayEvent event) {
        this.data.cancelIfDisabled(event, this.triggerLeavesDecay);
    }
}
