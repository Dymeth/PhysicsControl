package ru.dymeth.pcontrol;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.world.StructureGrowEvent;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.PControlTrigger;
import ru.dymeth.pcontrol.api.PhysicsListener;

import javax.annotation.Nonnull;

class PhysicsListenerCommon extends PhysicsListener {
    PhysicsListenerCommon(@Nonnull PControlData data) {
        super(data);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(StructureGrowEvent event) {
        World world = event.getWorld();
        if (event.getPlayer() != null) {
            this.data.cancelIfDisabled(event, world, PControlTrigger.BONE_MEAL_USAGE);
            return;
        }
        Material from = event.getLocation().getBlock().getType();
        switch (event.getSpecies()) {
            case TREE:
            case BIG_TREE:
            case REDWOOD:
            case TALL_REDWOOD:
            case BIRCH:
            case JUNGLE:
            case SMALL_JUNGLE:
            case COCOA_TREE:
            case JUNGLE_BUSH:
            case SWAMP:
            case ACACIA:
            case DARK_OAK:
            case MEGA_REDWOOD:
            case TALL_BIRCH:
                this.data.cancelIfDisabled(event, world, PControlTrigger.TREES_GROWING);
                break;
            case RED_MUSHROOM:
            case BROWN_MUSHROOM:
                this.data.cancelIfDisabled(event, world, PControlTrigger.GIANT_MUSHROOMS_GROWING);
                break;
            case CHORUS_PLANT:
                this.data.cancelIfDisabled(event, world, PControlTrigger.CHORUSES_GROWING);
                break;
            default:
                this.unrecognizedAction(event, event.getLocation(), from + " > " + event.getSpecies());
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL)
            this.data.cancelIfDisabled(event, PControlTrigger.PLAYERS_FLINT_USAGE);
        else
            this.data.cancelIfDisabled(event, PControlTrigger.FIRE_SPREADING);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(ProjectileHitEvent event) {
        if (event.getHitBlock() == null) return;
        Entity entity = event.getEntity();
        if (!this.data.getRemovableProjectileTypes().contains(entity.getType())) return;
        if (!this.data.isActionAllowed(entity.getWorld(), PControlTrigger.BLOCK_HIT_PROJECTILES_REMOVING)) return;
        entity.remove();
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockBurnEvent event) {
        this.data.cancelIfDisabled(event, PControlTrigger.FIRE_SPREADING);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(LeavesDecayEvent event) {
        this.data.cancelIfDisabled(event, PControlTrigger.LEAVES_DECAY);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(EntityBlockFormEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        if (from == Material.WATER && to == Material.FROSTED_ICE)
            this.data.cancelIfDisabled(event, PControlTrigger.FROSTED_ICE_PHYSICS);
        else
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
    }
}
