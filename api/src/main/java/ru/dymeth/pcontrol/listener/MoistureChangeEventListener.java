package ru.dymeth.pcontrol.listener;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.MoistureChangeEvent;
import ru.dymeth.pcontrol.PhysicsListener;
import ru.dymeth.pcontrol.data.PControlData;

import javax.annotation.Nonnull;

public final class MoistureChangeEventListener extends PhysicsListener {

    public MoistureChangeEventListener(@Nonnull PControlData data) {
        super(data);
    }

    {
        this.triggers.FARMLANDS_DRYING.markAvailable();
    }

    @EventHandler(ignoreCancelled = true)
    private void on(MoistureChangeEvent event) {
        BlockData oldData = event.getBlock().getBlockData();
        BlockData newData = event.getNewState().getBlockData();
        if (oldData instanceof Farmland && newData instanceof Farmland) {
            int oldMoisture = ((Farmland) oldData).getMoisture();
            int newMoisture = ((Farmland) newData).getMoisture();
            if (newMoisture < oldMoisture) {
                this.data.cancelIfDisabled(event, this.triggers.FARMLANDS_DRYING);
            }
            return;
        }
        Material from = oldData.getMaterial();
        Material to = newData.getMaterial();
        this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
    }

}
