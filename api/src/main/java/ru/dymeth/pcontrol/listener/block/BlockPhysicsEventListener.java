package ru.dymeth.pcontrol.listener.block;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPhysicsEvent;
import ru.dymeth.pcontrol.PhysicsListener;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.EventsListenerParser;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.rules.single.MaterialRules;
import ru.dymeth.pcontrol.util.ReflectionUtils;

import javax.annotation.Nonnull;

public class BlockPhysicsEventListener extends PhysicsListener {

    private final MaterialRules rulesBlockPhysicsEventFrom = new MaterialRules(
        this.data, BlockPhysicsEvent.class, "from");

    private final boolean legacyBlockPhysicsEvent = !this.data.hasVersion(1, 13, 0);
    private final boolean modernBlockPhysicsEvent = ReflectionUtils.isMethodPresent(BlockPhysicsEvent.class, "getSourceBlock");

    public BlockPhysicsEventListener(@Nonnull PControlData data, @Nonnull EventsListenerParser parser) {
        super(data);
        parser.registerParser(this.rulesBlockPhysicsEventFrom);
    }

    @Override
    protected void unregisterUnavailableTriggers() {
        if (!this.legacyBlockPhysicsEvent && !this.modernBlockPhysicsEvent) {
            this.rulesBlockPhysicsEventFrom.unregisterAll();
        }
    }

    @SuppressWarnings("ConcatenationWithEmptyString")
    @EventHandler(ignoreCancelled = true)
    private void modernListener(BlockPhysicsEvent event) {
        Block block = event.getBlock();

        if (this.legacyBlockPhysicsEvent) {
            if (PhysicsListener.DEBUG_BLOCK_PHYSICS_EVENT) {
                this.debugAction(event, block.getLocation(), ""
                    + "block=" + block.getType() + ";"
                    + "changed=" + event.getChangedType() + ";"
                );
            }
            PControlTrigger trigger = this.rulesBlockPhysicsEventFrom.findTrigger(block.getType());
            if (trigger != null) {
                this.data.cancelIfDisabled(event, trigger);
            }
            return;
        }

        if (!this.modernBlockPhysicsEvent) {
            return;
        }

        Block source = event.getSourceBlock(); // Not supported on Spigot 1.13 and 1.13.1

        if (PhysicsListener.DEBUG_BLOCK_PHYSICS_EVENT) {
            this.debugAction(event, block.getLocation(), ""
                + "block=" + block.getType() + ";"
                + "source=" + source.getType() + ";"
                + "changed=" + event.getChangedType() + ";"
            );
        }

        PControlTrigger trigger;
        Block sourceRelative;

        BlockFace faceUp = BlockFace.UP;
        sourceRelative = source.getRelative(faceUp);
        if (PhysicsListener.DEBUG_BLOCK_PHYSICS_EVENT) {
            this.debugAction(event, sourceRelative.getLocation(), ""
                + "face=" + faceUp + ";"
                + "sourceRelative=" + sourceRelative.getType() + ";"
            );
        }
        trigger = this.rulesBlockPhysicsEventFrom.findTrigger(sourceRelative.getType());
        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
            return;
        }

        for (BlockFace faceNSWE : PhysicsListener.NSWE_FACES) {
            sourceRelative = source.getRelative(faceNSWE);
            if (PhysicsListener.DEBUG_BLOCK_PHYSICS_EVENT) {
                this.debugAction(event, sourceRelative.getLocation(), ""
                    + "face=" + faceNSWE + ";"
                    + "sourceRelative=" + sourceRelative.getType() + ";"
                );
            }
            if (!this.versionsAdapter.isFacingAt(sourceRelative, faceNSWE)) continue;
            trigger = this.rulesBlockPhysicsEventFrom.findTrigger(sourceRelative.getType());
            if (trigger != null) {
                this.data.cancelIfDisabled(event, trigger);
                return;
            }
        }
    }
}
