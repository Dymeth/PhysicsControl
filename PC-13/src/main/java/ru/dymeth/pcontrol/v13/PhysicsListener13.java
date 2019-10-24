package ru.dymeth.pcontrol.v13;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;
import ru.dymeth.pcontrol.PControlData;
import ru.dymeth.pcontrol.PControlTrigger;
import ru.dymeth.pcontrol.PhysicsListener;

import javax.annotation.Nonnull;

public final class PhysicsListener13 extends PhysicsListener {
    public PhysicsListener13(@Nonnull PControlData data) {
        super(data);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(StructureGrowEvent event) {
        if (event.getPlayer() != null) return;
        World world = event.getWorld();
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
    private void on(BlockGrowEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        if (to == Material.SUGAR_CANE)
            this.data.cancelIfDisabled(event, PControlTrigger.SUGAR_CANE_GROWING);
        else if (to == Material.CACTUS)
            this.data.cancelIfDisabled(event, PControlTrigger.CACTUS_GROWING);
        else if (to == Material.WHEAT)
            this.data.cancelIfDisabled(event, PControlTrigger.WHEAT_GROWING);
        else if (to == Material.POTATOES)
            this.data.cancelIfDisabled(event, PControlTrigger.POTATOES_GROWING);
        else if (to == Material.CARROTS)
            this.data.cancelIfDisabled(event, PControlTrigger.CARROTS_GROWING);
        else if (to == Material.BEETROOTS)
            this.data.cancelIfDisabled(event, PControlTrigger.BEETROOTS_GROWING);
        else if (to == Material.PUMPKIN_STEM || to == Material.PUMPKIN)
            this.data.cancelIfDisabled(event, PControlTrigger.PUMPKINS_GROWING);
        else if (to == Material.MELON_STEM || to == Material.MELON)
            this.data.cancelIfDisabled(event, PControlTrigger.MELONS_GROWING);
        else if (to == Material.COCOA)
            this.data.cancelIfDisabled(event, PControlTrigger.COCOAS_GROWING);
        else if (to == Material.NETHER_WART_BLOCK)
            this.data.cancelIfDisabled(event, PControlTrigger.NETHER_WARTS_GROWING);
        else if (to == Material.GRASS || CustomTag13.FLOWERS.isTagged(to))
            return; // Bone meal
        else
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(EntityChangeBlockEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getTo();
        World world = event.getEntity().getWorld();
        if (!(event.getEntity() instanceof FallingBlock)) {
            EntityType entityType = event.getEntity().getType();

            if (entityType == EntityType.SHEEP)
                this.data.cancelIfDisabled(event, world, PControlTrigger.SHEEPS_EATING_GRASS);
            else if (entityType == EntityType.ENDERMAN)
                this.data.cancelIfDisabled(event, world, PControlTrigger.ENDERMANS_GRIEFING);
            else if (to == Material.REDSTONE_ORE)
                return; // Redstone ore activation
            else
                this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to + " (" + event.getEntityType() + ")");

            return;
        }

        if (Tag.SAND.isTagged(from))
            this.data.cancelIfDisabled(event, world, PControlTrigger.SAND_FALLING);
        else if (from == Material.GRAVEL)
            this.data.cancelIfDisabled(event, world, PControlTrigger.GRAVEL_FALLING);
        else if (Tag.ANVIL.isTagged(from))
            this.data.cancelIfDisabled(event, world, PControlTrigger.ANVILS_FALLING);
        else if (CustomTag13.CONCRETE_POWDERS.isTagged(from))
            this.data.cancelIfDisabled(event, world, PControlTrigger.CONCRETE_POWDERS_FALLING);
        else if (CustomTag13.GRAVITY_BLOCKS.isTagged(to))
            return; // Already existing falling blocks
        else
            this.unrecognizedAction(event, event.getBlock().getLocation(), "falling block from " + from + " to " + to);

        if (event.isCancelled()) event.getBlock().getState().update(false, false);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockFromToEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getToBlock().getType();

        if (from == Material.LAVA)
            this.data.cancelIfDisabled(event, PControlTrigger.LAVA_FLOWING);
        else if (from == Material.WATER
                || (event.getBlock().getBlockData() instanceof Waterlogged
                && ((Waterlogged) event.getBlock().getBlockData()).isWaterlogged()))
            this.data.cancelIfDisabled(event, PControlTrigger.WATER_FLOWING);
        else
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockFadeEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        if ((from == Material.GRASS_BLOCK || from == Material.GRASS_PATH) && to == Material.DIRT)
            this.data.cancelIfDisabled(event, PControlTrigger.GRASS_SPREADING);
        else if (from == Material.MYCELIUM && to == Material.DIRT)
            this.data.cancelIfDisabled(event, PControlTrigger.MYCELIUM_SPREADING);
        else if (from == Material.FARMLAND && to == Material.DIRT)
            this.data.cancelIfDisabled(event, PControlTrigger.FARMLANDS_DRYING);
        else if (from == Material.SNOW && to == Material.AIR)
            this.data.cancelIfDisabled(event, PControlTrigger.SNOW_MELTING);
        else if (from == Material.ICE && to == Material.WATER)
            this.data.cancelIfDisabled(event, PControlTrigger.ICE_MELTING);
        else if (CustomTag13.ALL_ALIVE_CORALS.isTagged(from) && CustomTag13.ALL_DEAD_CORALS.isTagged(to))
            this.data.cancelIfDisabled(event, PControlTrigger.CORALS_DRYING);
        else if (from == Material.FIRE && to == Material.AIR)
            this.data.cancelIfDisabled(event, PControlTrigger.FIRE_SPREADING);
        else if (from == Material.REDSTONE_ORE && to == Material.REDSTONE_ORE)
            return; // Redstone ore deactivation
        else
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockSpreadEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        if (to == Material.GRASS_BLOCK && from == Material.DIRT)
            this.data.cancelIfDisabled(event, PControlTrigger.GRASS_SPREADING);
        else if (to == Material.MYCELIUM && from == Material.DIRT)
            this.data.cancelIfDisabled(event, PControlTrigger.MYCELIUM_SPREADING);
        else if (to == Material.VINE && (from == Material.AIR || from == Material.VINE))
            this.data.cancelIfDisabled(event, PControlTrigger.VINES_GROWING);
        else if (CustomTag13.LITTLE_MUSHROOMS.isTagged(to) && from == Material.AIR)
            this.data.cancelIfDisabled(event, PControlTrigger.LITTLE_MUSHROOMS_SPREADING);
        else if (to == Material.KELP && from == Material.WATER)
            this.data.cancelIfDisabled(event, PControlTrigger.KELPS_GROWING);
        else
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void on(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) return;
        if (event.getBlockFace() != BlockFace.SELF) return;
        if (event.getClickedBlock() == null) return;
        World world = event.getClickedBlock().getWorld();
        Material source = event.getClickedBlock().getType();

        if (source == Material.FARMLAND)
            this.data.cancelIfDisabled(event, world, PControlTrigger.FARMLANDS_TRAMPLING);
        else if (CustomTag13.REDSTONE_PASSIVE_INPUTS.isTagged(source))
            return; // Redstone activators
        else if (source == Material.REDSTONE_ORE)
            return; // Redstone ore activation
        else
            this.unrecognizedAction(event, event.getClickedBlock().getLocation(), source);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockPhysicsEvent event) {
        Block fromBlock = event.getSourceBlock();
        if (fromBlock != event.getBlock()) {
            if (fromBlock.getType() == event.getChangedType()) return;

            if (Tag.RAILS.isTagged(event.getChangedType()))
                this.data.cancelIfDisabled(event, PControlTrigger.RAILS_DESTROYING);
            else if (this.debugPhysicsEvent)
                this.debugAction(event, event.getBlock().getLocation(), "face=" + "self" + ";source=" + fromBlock.getType() + ";changed=" + event.getChangedType() + ";block=" + event.getBlock().getType());

            return;
        }

        Block toBlock;
        BlockData toData;
        Material to;

        toBlock = fromBlock.getRelative(BlockFace.UP);
        to = toBlock.getType();

        if (to == Material.SIGN)
            this.data.cancelIfDisabled(event, PControlTrigger.SIGNS_DESTROYING);
        else if (to == Material.TORCH)
            this.data.cancelIfDisabled(event, PControlTrigger.TORCHES_DESTROYING);
        else if (to == Material.REDSTONE_TORCH)
            this.data.cancelIfDisabled(event, PControlTrigger.REDSTONE_TORCHES_DESTROYING);

        else {
            if (this.debugPhysicsEvent)
                this.debugAction(event, event.getBlock().getLocation(), "face=" + "up" + ";source=" + fromBlock.getType() + ";changed=" + event.getChangedType() + ";block=" + event.getBlock().getType());
            for (BlockFace face : this.nsweFaces) {
                toBlock = fromBlock.getRelative(face);
                toData = toBlock.getBlockData();
                if (!(toData instanceof Directional)) continue;
                if (((Directional) toData).getFacing() != face) continue;
                to = toBlock.getType();

                if (to == Material.LADDER)
                    this.data.cancelIfDisabled(event, PControlTrigger.LADDERS_DESTROYING);
                else if (to == Material.WALL_SIGN)
                    this.data.cancelIfDisabled(event, PControlTrigger.SIGNS_DESTROYING);
                else if (to == Material.WALL_TORCH)
                    this.data.cancelIfDisabled(event, PControlTrigger.TORCHES_DESTROYING);
                else if (to == Material.REDSTONE_WALL_TORCH)
                    this.data.cancelIfDisabled(event, PControlTrigger.REDSTONE_TORCHES_DESTROYING);
                else if (this.debugPhysicsEvent)
                    this.debugAction(event, event.getBlock().getLocation(), "face=" + face.name() + ";source=" + fromBlock.getType() + ";changed=" + event.getChangedType() + ";block=" + event.getBlock().getType());

                if (event.isCancelled()) return;
            }
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
        this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
    }
}
