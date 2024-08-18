package ru.dymeth.pcontrol.listener.block;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;
import ru.dymeth.pcontrol.PhysicsListener;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.EventsListenerParser;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.rules.pair.MaterialMaterialRules;
import ru.dymeth.pcontrol.rules.single.MaterialRules;

import javax.annotation.Nonnull;

public class BlockFromToEventListener extends PhysicsListener {

    private final PControlTrigger triggerWaterFlowing
        = this.data.getTriggersRegisty().valueOf("WATER_FLOWING");
    private final MaterialMaterialRules rulesBlockFromToEventFromTo = new MaterialMaterialRules(
        this.data, BlockFromToEvent.class, "from", "to");
    private final MaterialRules rulesBlockFromToEventFrom = new MaterialRules(
        this.data, BlockFromToEvent.class, "from");

    public BlockFromToEventListener(@Nonnull PControlData data, @Nonnull EventsListenerParser parser) {
        super(data);
        parser.registerParser(this.rulesBlockFromToEventFromTo);
        parser.registerParser(this.rulesBlockFromToEventFrom);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockFromToEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getToBlock().getType();

        PControlTrigger trigger = this.rulesBlockFromToEventFromTo.findTrigger(from, to);
        if (trigger == null) {
            trigger = this.rulesBlockFromToEventFrom.findTrigger(from);
        }
        if (trigger == null) {
            if (this.versionsAdapter.isBlockContainsWater(event.getBlock())) {
                trigger = this.triggerWaterFlowing;
            }
        }

        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
        } else {
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
        }
    }
}
