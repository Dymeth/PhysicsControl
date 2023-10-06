package ru.dymeth.pcontrol.legacy;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Attachable;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;
import ru.dymeth.pcontrol.PhysicsListenerCommon;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.PControlTrigger;

import javax.annotation.Nonnull;

@SuppressWarnings({"IsCancelled"})
public final class PhysicsListenerLegacy extends PhysicsListenerCommon {

    public PhysicsListenerLegacy(@Nonnull PControlData data) {
        super(data);
    }

    @Override
    protected boolean isBoneMealItem(@Nonnull ItemStack stack) {
        return stack.getData() instanceof Dye && ((Dye) stack.getData()).getColor() == DyeColor.WHITE;
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockPhysicsEvent event) {
        Block fromBlock = event.getBlock();
        Block toBlock;
        MaterialData toData;
        Material to;

        toBlock = fromBlock.getRelative(BlockFace.UP);
        to = toBlock.getType();

        if (to == Material.SIGN) {
            this.data.cancelIfDisabled(event, PControlTrigger.SIGNS_DESTROYING);
        } else if (to == Material.TORCH) {
            this.data.cancelIfDisabled(event, PControlTrigger.TORCHES_DESTROYING);
        } else if (to == Material.REDSTONE_TORCH_ON || to == Material.REDSTONE_TORCH_OFF) {
            this.data.cancelIfDisabled(event, PControlTrigger.REDSTONE_TORCHES_DESTROYING);
        } else {
            if (DEBUG_PHYSICS_EVENT) {
                this.debugAction(event, fromBlock.getLocation(), ""
                    + "face=" + BlockFace.UP.name() + ";"
                    + "changed=" + event.getChangedType() + ";"
                    + "block=" + fromBlock.getType() + ";"
                );
            }
            for (BlockFace face : NSWE_FACES) {
                toBlock = fromBlock.getRelative(face);
                toData = toBlock.getState().getData();
                if (!(toData instanceof Attachable)) continue;
                if (((Attachable) toData).getFacing() != face) continue;
                to = toBlock.getType();

                if (to == Material.LADDER) {
                    this.data.cancelIfDisabled(event, PControlTrigger.LADDERS_DESTROYING);
                } else if (to == Material.WALL_SIGN) {
                    this.data.cancelIfDisabled(event, PControlTrigger.SIGNS_DESTROYING);
                } else if (to == Material.TORCH) {
                    this.data.cancelIfDisabled(event, PControlTrigger.TORCHES_DESTROYING);
                } else if (to == Material.REDSTONE_TORCH_ON || to == Material.REDSTONE_TORCH_OFF) {
                    this.data.cancelIfDisabled(event, PControlTrigger.REDSTONE_TORCHES_DESTROYING);
                } else if (DEBUG_PHYSICS_EVENT) {
                    this.debugAction(event, fromBlock.getLocation(), ""
                        + "face=" + face.name() + ";"
                        + "changed=" + event.getChangedType() + ";"
                        + "block=" + fromBlock.getType() + ";"
                    );
                }

                if (event.isCancelled()) return;
            }
        }
    }
}
