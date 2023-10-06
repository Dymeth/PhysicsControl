package ru.dymeth.pcontrol.modern;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.MoistureChangeEvent;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.PControlTrigger;
import ru.dymeth.pcontrol.api.PhysicsListener;

import javax.annotation.Nonnull;

@SuppressWarnings({"IsCancelled", "ClassInitializerMayBeStatic"})
public final class PhysicsListenerModern extends PhysicsListener {

    public PhysicsListenerModern(@Nonnull PControlData data) {
        super(data);
    }

    {
        PControlTrigger.FARMLANDS_DRYING.markAvailable();
    }

    @EventHandler(ignoreCancelled = true)
    private void on(MoistureChangeEvent event) {
        BlockData oldData = event.getBlock().getBlockData();
        BlockData newData = event.getNewState().getBlockData();
        if (oldData instanceof Farmland && newData instanceof Farmland) {
            int oldMoisture = ((Farmland) oldData).getMoisture();
            int newMoisture = ((Farmland) newData).getMoisture();
            if (newMoisture < oldMoisture) {
                this.data.cancelIfDisabled(event, PControlTrigger.FARMLANDS_DRYING);
            }
            return;
        }
        Material from = oldData.getMaterial();
        Material to = newData.getMaterial();
        this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
    }

    {
        if (this.data.hasVersion(13)) { // BlockPhysicsEventWorks isn't calls on 1.8-1.12 for an unknown reason
            PControlTrigger.RAILS_DESTROYING.markAvailable();
            PControlTrigger.LADDERS_DESTROYING.markAvailable();
            PControlTrigger.SIGNS_DESTROYING.markAvailable();
            PControlTrigger.TORCHES_DESTROYING.markAvailable();
            PControlTrigger.REDSTONE_TORCHES_DESTROYING.markAvailable();
            if (this.data.hasVersion(16)) {
                PControlTrigger.SOUL_TORCHES_DESTROYING.markAvailable();
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockPhysicsEvent event) {
        Block fromBlock = event.getSourceBlock();
        if (fromBlock != event.getBlock()) {
            if (fromBlock.getType() == event.getChangedType()) return;

            if (Tag.RAILS.isTagged(event.getChangedType())) {
                this.data.cancelIfDisabled(event, PControlTrigger.RAILS_DESTROYING);
            } else if (DEBUG_PHYSICS_EVENT) {
                this.debugAction(event, event.getBlock().getLocation(), ""
                    + "face=" + BlockFace.SELF.name() + ";"
                    + "changed=" + event.getChangedType() + ";"
                    + "block=" + event.getBlock().getType() + ";"
                    + "source=" + fromBlock.getType() + ";"
                );
            }
            return;
        }

        Block toBlock;
        BlockData toData;
        Material to;

        toBlock = fromBlock.getRelative(BlockFace.UP);
        to = toBlock.getType();

        if (this.tags.SIGNS.contains(to)) {
            this.data.cancelIfDisabled(event, PControlTrigger.SIGNS_DESTROYING);
        } else if (to == Material.TORCH) {
            this.data.cancelIfDisabled(event, PControlTrigger.TORCHES_DESTROYING);
        } else if (to == Material.REDSTONE_TORCH) {
            this.data.cancelIfDisabled(event, PControlTrigger.REDSTONE_TORCHES_DESTROYING);
        } else if (this.data.hasVersion(16) && to == Material.SOUL_TORCH) {
            this.data.cancelIfDisabled(event, PControlTrigger.SOUL_TORCHES_DESTROYING);
        } else {
            if (DEBUG_PHYSICS_EVENT) {
                this.debugAction(event, event.getBlock().getLocation(), ""
                    + "face=" + BlockFace.UP.name() + ";"
                    + "changed=" + event.getChangedType() + ";"
                    + "block=" + event.getBlock().getType() + ";"
                    + "source=" + fromBlock.getType() + ";"
                );
            }
            for (BlockFace face : NSWE_FACES) {
                toBlock = fromBlock.getRelative(face);
                toData = toBlock.getBlockData();
                if (!(toData instanceof Directional)) continue;
                if (((Directional) toData).getFacing() != face) continue;
                to = toBlock.getType();

                if (to == Material.LADDER) {
                    this.data.cancelIfDisabled(event, PControlTrigger.LADDERS_DESTROYING);
                } else if (this.tags.WALL_SIGNS.contains(to)) {
                    this.data.cancelIfDisabled(event, PControlTrigger.SIGNS_DESTROYING);
                } else if (to == Material.WALL_TORCH) {
                    this.data.cancelIfDisabled(event, PControlTrigger.TORCHES_DESTROYING);
                } else if (to == Material.REDSTONE_WALL_TORCH) {
                    this.data.cancelIfDisabled(event, PControlTrigger.REDSTONE_TORCHES_DESTROYING);
                } else if (this.data.hasVersion(16) && to == Material.SOUL_TORCH) {
                    this.data.cancelIfDisabled(event, PControlTrigger.SOUL_TORCHES_DESTROYING);
                } else if (DEBUG_PHYSICS_EVENT) {
                    this.debugAction(event, event.getBlock().getLocation(), ""
                        + "face=" + face.name() + ";"
                        + "changed=" + event.getChangedType() + ";"
                        + "block=" + event.getBlock().getType() + ";"
                        + "source=" + fromBlock.getType() + ";"
                    );
                }

                if (event.isCancelled()) return;
            }
        }
    }
}
