package ru.dymeth.pcontrol.listener.block;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.MoistureChangeEvent;
import ru.dymeth.pcontrol.PhysicsListener;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.EventsListenerParser;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;

import javax.annotation.Nonnull;

public class MoistureChangeEventListener extends PhysicsListener {

    private final PControlTrigger triggerFarmlandsDrying
        = this.data.getTriggersRegisty().valueOf("FARMLANDS_DRYING");

    public MoistureChangeEventListener(@Nonnull PControlData data, @Nonnull EventsListenerParser parser) {
        super(data);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(MoistureChangeEvent event) {
        BlockData oldData = event.getBlock().getBlockData();
        BlockData newData = event.getNewState().getBlockData();
        if (oldData instanceof Farmland && newData instanceof Farmland) {
            int oldMoisture = ((Farmland) oldData).getMoisture();
            int newMoisture = ((Farmland) newData).getMoisture();
            if (newMoisture < oldMoisture) {
                this.data.cancelIfDisabled(event, this.triggerFarmlandsDrying);
            }
            return;
        }
        Material from = oldData.getMaterial();
        Material to = newData.getMaterial();
        this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
    }

}
