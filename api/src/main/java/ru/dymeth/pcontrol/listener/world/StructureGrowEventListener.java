package ru.dymeth.pcontrol.listener.world;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.StructureGrowEvent;
import ru.dymeth.pcontrol.PhysicsListener;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.EventsListenerParser;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.rules.single.TreeRules;

import javax.annotation.Nonnull;

public class StructureGrowEventListener extends PhysicsListener {

    private final TreeRules rulesStructureGrowEventTo = new TreeRules(
        this.data, StructureGrowEvent.class, "to");

    public StructureGrowEventListener(@Nonnull PControlData data, @Nonnull EventsListenerParser parser) {
        super(data);
        parser.registerParser(this.rulesStructureGrowEventTo);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(StructureGrowEvent event) {
        Material from = event.getLocation().getBlock().getType();
        TreeType to = event.getSpecies();

        PControlTrigger trigger = this.rulesStructureGrowEventTo.findTrigger(to);

        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
        } else {
            this.unrecognizedAction(event, event.getLocation(), from + " > " + to);
        }
    }
}
