package ru.dymeth.pcontrol.v12;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Attachable;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;
import ru.dymeth.pcontrol.PControlData;
import ru.dymeth.pcontrol.PControlTrigger;
import ru.dymeth.pcontrol.PhysicsListener;

import javax.annotation.Nonnull;

import static org.bukkit.Material.CONCRETE_POWDER;

public final class PhysicsListener12 extends PhysicsListener {
    public PhysicsListener12(@Nonnull PControlData data) {
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

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onBoneMeal(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        ItemStack usedItem = event.getItem();
        if (usedItem == null) return;
        if (!(usedItem.getData() instanceof Dye)) return;
        if (((Dye) usedItem.getData()).getColor() != DyeColor.WHITE) return;
        this.data.cancelIfDisabled(event, event.getClickedBlock().getWorld(), PControlTrigger.BONE_MEAL_USAGE);
        if (event.isCancelled()) return;
        this.fertilizedBlocks.add(event.getClickedBlock().getLocation().toVector());
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockGrowEvent event) {
        if (this.fertilizedBlocks.remove(event.getBlock().getLocation().toVector())) {
            this.data.cancelIfDisabled(event, PControlTrigger.BONE_MEAL_USAGE);
            return;
        }
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        if (to == Material.SUGAR_CANE_BLOCK)
            this.data.cancelIfDisabled(event, PControlTrigger.SUGAR_CANE_GROWING);
        else if (to == Material.CACTUS)
            this.data.cancelIfDisabled(event, PControlTrigger.CACTUS_GROWING);
        else if (to == Material.CROPS)
            this.data.cancelIfDisabled(event, PControlTrigger.WHEAT_GROWING);
        else if (to == Material.POTATO)
            this.data.cancelIfDisabled(event, PControlTrigger.POTATOES_GROWING);
        else if (to == Material.CARROT)
            this.data.cancelIfDisabled(event, PControlTrigger.CARROTS_GROWING);
        else if (to == Material.BEETROOT_BLOCK)
            this.data.cancelIfDisabled(event, PControlTrigger.BEETROOTS_GROWING);
        else if (to == Material.PUMPKIN_STEM || to == Material.PUMPKIN)
            this.data.cancelIfDisabled(event, PControlTrigger.PUMPKINS_GROWING);
        else if (to == Material.MELON_STEM || to == Material.MELON_BLOCK)
            this.data.cancelIfDisabled(event, PControlTrigger.MELONS_GROWING);
        else if (to == Material.COCOA && from == Material.COCOA)
            this.data.cancelIfDisabled(event, PControlTrigger.COCOAS_GROWING);
        else if (to == Material.NETHER_WART_BLOCK)
            this.data.cancelIfDisabled(event, PControlTrigger.NETHER_WARTS_GROWING);
        else if (CustomTag12.BONE_MEAL_HERBS.isTagged(to))
            this.data.cancelIfDisabled(event, PControlTrigger.BONE_MEAL_USAGE);
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

            if (from == Material.DIRT && to == Material.SOIL)
                this.data.cancelIfDisabled(event, world, PControlTrigger.FARMLANDS_TRAMPLING);
            else if (to == Material.GLOWING_REDSTONE_ORE)
                return; // Redstone ore activation
            else if (entityType == EntityType.SHEEP)
                this.data.cancelIfDisabled(event, world, PControlTrigger.SHEEPS_EATING_GRASS);
            else if (entityType == EntityType.ENDERMAN)
                this.data.cancelIfDisabled(event, world, PControlTrigger.ENDERMANS_GRIEFING);
            else if (entityType == EntityType.VILLAGER)
                return; // Villagers plant/harvest crops
            else
                this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to + " (" + event.getEntityType() + ")");

            return;
        }

        if (from == Material.SAND)
            this.data.cancelIfDisabled(event, world, PControlTrigger.SAND_FALLING);
        else if (from == Material.GRAVEL)
            this.data.cancelIfDisabled(event, world, PControlTrigger.GRAVEL_FALLING);
        else if (from == Material.ANVIL)
            this.data.cancelIfDisabled(event, world, PControlTrigger.ANVILS_FALLING);
        else if (from == CONCRETE_POWDER)
            this.data.cancelIfDisabled(event, world, PControlTrigger.CONCRETE_POWDERS_FALLING);
        else if (CustomTag12.GRAVITY_BLOCKS.isTagged(to))
            return; // Already existing falling blocks
        else
            this.unrecognizedAction(event, event.getBlock().getLocation(), "falling block " + from + " > " + to);

        if (event.isCancelled()) event.getBlock().getState().update(false, false);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockFromToEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getToBlock().getType();

        if (from == Material.LAVA || from == Material.STATIONARY_LAVA)
            this.data.cancelIfDisabled(event, PControlTrigger.LAVA_FLOWING);
        else if (from == Material.WATER || from == Material.STATIONARY_WATER)
            this.data.cancelIfDisabled(event, PControlTrigger.WATER_FLOWING);
        else
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockFadeEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        if ((from == Material.GRASS || from == Material.GRASS_PATH) && to == Material.DIRT)
            this.data.cancelIfDisabled(event, PControlTrigger.GRASS_SPREADING);
        else if (from == Material.MYCEL && to == Material.DIRT)
            this.data.cancelIfDisabled(event, PControlTrigger.MYCELIUM_SPREADING);
        else if (from == Material.SOIL && to == Material.DIRT)
            this.data.cancelIfDisabled(event, PControlTrigger.FARMLANDS_DRYING);
        else if (from == Material.SNOW && to == Material.AIR)
            this.data.cancelIfDisabled(event, PControlTrigger.SNOW_MELTING);
        else if (from == Material.ICE && to == Material.WATER)
            this.data.cancelIfDisabled(event, PControlTrigger.ICE_MELTING);
        else if (from == Material.FIRE && to == Material.AIR)
            this.data.cancelIfDisabled(event, PControlTrigger.FIRE_SPREADING);
        else if (from == Material.GLOWING_REDSTONE_ORE && to == Material.REDSTONE_ORE)
            return; // Redstone ore deactivation
        else
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockSpreadEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        if (to == Material.GRASS && from == Material.DIRT)
            this.data.cancelIfDisabled(event, PControlTrigger.GRASS_SPREADING);
        else if (to == Material.MYCEL && from == Material.DIRT)
            this.data.cancelIfDisabled(event, PControlTrigger.MYCELIUM_SPREADING);
        else if (to == Material.VINE && (from == Material.AIR || from == Material.VINE))
            this.data.cancelIfDisabled(event, PControlTrigger.VINES_GROWING);
        else if (CustomTag12.LITTLE_MUSHROOMS.isTagged(to) && from == Material.AIR)
            this.data.cancelIfDisabled(event, PControlTrigger.LITTLE_MUSHROOMS_SPREADING);
        else if (to == Material.FIRE && from == Material.AIR)
            this.data.cancelIfDisabled(event, PControlTrigger.FIRE_SPREADING);
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

        if (source == Material.SOIL)
            this.data.cancelIfDisabled(event, world, PControlTrigger.FARMLANDS_TRAMPLING);
        else if (CustomTag12.REDSTONE_PASSIVE_INPUTS.isTagged(source))
            return; // Redstone activators
        else if (source == Material.REDSTONE_ORE || source == Material.GLOWING_REDSTONE_ORE)
            return; // Redstone ore activation
        else
            this.unrecognizedAction(event, event.getClickedBlock().getLocation(), source);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockPhysicsEvent event) {
        // Block fromBlock = event.getSourceBlock(); // 1.13
        Block fromBlock = event.getBlock();
        Block toBlock;
        MaterialData toData;
        Material to;

        toBlock = fromBlock.getRelative(BlockFace.UP);
        to = toBlock.getType();

        if (to == Material.SIGN)
            this.data.cancelIfDisabled(event, PControlTrigger.SIGNS_DESTROYING);
        else if (to == Material.TORCH)
            this.data.cancelIfDisabled(event, PControlTrigger.TORCHES_DESTROYING);
        else if (to == Material.REDSTONE_TORCH_ON || to == Material.REDSTONE_TORCH_OFF)
            this.data.cancelIfDisabled(event, PControlTrigger.REDSTONE_TORCHES_DESTROYING);

        else {
            if (this.debugPhysicsEvent)
                this.debugAction(event, fromBlock.getLocation(), "face=" + "up" + ";changed=" + event.getChangedType() + ";block=" + fromBlock.getType());
            for (BlockFace face : this.nsweFaces) {
                toBlock = fromBlock.getRelative(face);
                toData = toBlock.getState().getData();
                if (!(toData instanceof Attachable)) continue;
                if (((Attachable) toData).getFacing() != face) continue;
                to = toBlock.getType();

                if (to == Material.LADDER)
                    this.data.cancelIfDisabled(event, PControlTrigger.LADDERS_DESTROYING);
                else if (to == Material.WALL_SIGN)
                    this.data.cancelIfDisabled(event, PControlTrigger.SIGNS_DESTROYING);
                else if (to == Material.TORCH)
                    this.data.cancelIfDisabled(event, PControlTrigger.TORCHES_DESTROYING);
                else if (to == Material.REDSTONE_TORCH_ON || to == Material.REDSTONE_TORCH_OFF)
                    this.data.cancelIfDisabled(event, PControlTrigger.REDSTONE_TORCHES_DESTROYING);
                else if (this.debugPhysicsEvent)
                    this.debugAction(event, fromBlock.getLocation(), "face=" + face.name() + ";changed=" + event.getChangedType() + ";block=" + fromBlock.getType());

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
