package ru.dymeth.pcontrol.modern;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.MoistureChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.dymeth.pcontrol.PhysicsListenerCommon;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.PControlTrigger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings({"IsCancelled"})
public final class PhysicsListenerModern extends PhysicsListenerCommon {

    public PhysicsListenerModern(@Nonnull PControlData data) {
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
        if (usedItem.getType() != Material.BONE_MEAL) return;
        this.data.cancelIfDisabled(event, targetBlock.getWorld(), PControlTrigger.BONE_MEAL_USAGE);
        if (event.useItemInHand() == Event.Result.DENY) return;
        this.fertilizedBlocks.add(targetBlock.getLocation().toVector());
    }

    @Nullable
    @Override
    protected PControlTrigger getBlockFromToEventTrigger(@Nonnull Block block, @Nonnull Material from, @Nonnull Material to) {
        PControlTrigger trigger = super.getBlockFromToEventTrigger(block, from, to);
        if (trigger == null) {
            if (block.getBlockData() instanceof Waterlogged
                && ((Waterlogged) block.getBlockData()).isWaterlogged()) {
                trigger = PControlTrigger.WATER_FLOWING;
            }
        }
        return trigger;
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

        if (this.customTag.SIGNS.contains(to)) {
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
                } else if (this.customTag.WALL_SIGNS.contains(to)) {
                    this.data.cancelIfDisabled(event, PControlTrigger.SIGNS_DESTROYING);
                } else if (to == Material.WALL_TORCH) {
                    this.data.cancelIfDisabled(event, PControlTrigger.TORCHES_DESTROYING);
                } else if (to == Material.REDSTONE_WALL_TORCH) {
                    this.data.cancelIfDisabled(event, PControlTrigger.REDSTONE_TORCHES_DESTROYING);
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
