package ru.dymeth.pcontrol.legacy;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onBoneMeal(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block targetBlock = event.getClickedBlock();
        if (targetBlock == null) {
            throw new IllegalArgumentException("Block absent on PlayerInteractEvent with Action.RIGHT_CLICK_BLOCK");
        }

        ItemStack usedItem = event.getItem();
        if (usedItem == null) return;
        if (!(usedItem.getData() instanceof Dye)) return;
        if (((Dye) usedItem.getData()).getColor() != DyeColor.WHITE) return;
        this.data.cancelIfDisabled(event, targetBlock.getWorld(), PControlTrigger.BONE_MEAL_USAGE);
        if (event.isCancelled()) return;
        this.fertilizedBlocks.add(targetBlock.getLocation().toVector());
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
