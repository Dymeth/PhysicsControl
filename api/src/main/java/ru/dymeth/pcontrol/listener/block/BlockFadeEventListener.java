package ru.dymeth.pcontrol.listener.block;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFadeEvent;
import ru.dymeth.pcontrol.PhysicsListener;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.EventsListenerParser;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.rules.pair.MaterialMaterialRules;
import ru.dymeth.pcontrol.rules.single.MaterialRules;

import javax.annotation.Nonnull;

public class BlockFadeEventListener extends PhysicsListener {

    private final MaterialMaterialRules rulesBlockFadeEventFromTo = new MaterialMaterialRules(
        this.data, BlockFadeEvent.class, "from", "to");
    private final MaterialRules rulesBlockFadeEventTo = new MaterialRules(
        this.data, BlockFadeEvent.class, "to");

    public BlockFadeEventListener(@Nonnull PControlData data, @Nonnull EventsListenerParser parser) {
        super(data);
        parser.registerParser(this.rulesBlockFadeEventFromTo);
        parser.registerParser(this.rulesBlockFadeEventTo);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockFadeEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        PControlTrigger trigger = this.rulesBlockFadeEventFromTo.findTrigger(from, to);
        if (trigger == null) trigger = this.rulesBlockFadeEventTo.findTrigger(to);

        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
        } else {
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
        }
    }
}
