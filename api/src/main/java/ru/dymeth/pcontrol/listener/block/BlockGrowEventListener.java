package ru.dymeth.pcontrol.listener.block;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockGrowEvent;
import ru.dymeth.pcontrol.PhysicsListener;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.EventsListenerParser;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.rules.pair.MaterialMaterialRules;
import ru.dymeth.pcontrol.rules.single.MaterialRules;

import javax.annotation.Nonnull;

public class BlockGrowEventListener extends PhysicsListener {

    private final PControlTrigger triggerPlayersBoneMealUsage
        = this.data.getTriggersRegisty().valueOf("PLAYERS_BONE_MEAL_USAGE");
    private final MaterialMaterialRules rulesBlockGrowEventFromTo = new MaterialMaterialRules(
        this.data, BlockGrowEvent.class, "from", "to");
    private final MaterialRules rulesBlockGrowEventTo = new MaterialRules(
        this.data, BlockGrowEvent.class, "to");

    public BlockGrowEventListener(@Nonnull PControlData data, @Nonnull EventsListenerParser parser) {
        super(data);
        parser.registerParser(this.rulesBlockGrowEventFromTo);
        parser.registerParser(this.rulesBlockGrowEventTo);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockGrowEvent event) {
        if (this.fertilizedBlocks.remove(event.getBlock().getLocation().toVector())) {
            this.data.cancelIfDisabled(event, this.triggerPlayersBoneMealUsage);
            return;
        }
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        PControlTrigger trigger = this.rulesBlockGrowEventFromTo.findTrigger(from, to);
        if (trigger == null) trigger = this.rulesBlockGrowEventTo.findTrigger(to);

        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
        } else {
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
        }
    }
}
