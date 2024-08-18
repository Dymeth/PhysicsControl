package ru.dymeth.pcontrol.listener.block;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockSpreadEvent;
import ru.dymeth.pcontrol.PhysicsListener;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.EventsListenerParser;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.rules.pair.MaterialMaterialRules;
import ru.dymeth.pcontrol.rules.single.MaterialRules;

import javax.annotation.Nonnull;

public class BlockSpreadEventListener extends PhysicsListener {

    private final MaterialMaterialRules rulesBlockSpreadEventFromTo = new MaterialMaterialRules(
        this.data, BlockSpreadEvent.class, "from", "to");
    private final MaterialRules rulesBlockSpreadEventTo = new MaterialRules(
        this.data, BlockSpreadEvent.class, "to");

    public BlockSpreadEventListener(@Nonnull PControlData data, @Nonnull EventsListenerParser parser) {
        super(data);
        parser.registerParser(this.rulesBlockSpreadEventFromTo);
        parser.registerParser(this.rulesBlockSpreadEventTo);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockSpreadEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        PControlTrigger trigger = this.rulesBlockSpreadEventFromTo.findTrigger(from, to);
        if (trigger == null) trigger = this.rulesBlockSpreadEventTo.findTrigger(to);

        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
        } else {
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
        }
    }
}
