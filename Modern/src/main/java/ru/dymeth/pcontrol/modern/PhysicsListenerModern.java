package ru.dymeth.pcontrol.modern;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.dymeth.pcontrol.impl.PControlData;
import ru.dymeth.pcontrol.impl.PControlTrigger;
import ru.dymeth.pcontrol.impl.PhysicsListener;

import javax.annotation.Nonnull;

@SuppressWarnings({"IsCancelled", "UnnecessaryReturnStatement"})
public final class PhysicsListenerModern extends PhysicsListener {
    public PhysicsListenerModern(@Nonnull PControlData data) {
        super(data);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onBoneMeal(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block targetBlock = event.getClickedBlock();
        if (targetBlock == null) throw new IllegalArgumentException("Block absent on PlayerInteractEvent with Action.RIGHT_CLICK_BLOCK");

        ItemStack usedItem = event.getItem();
        if (usedItem == null) return;
        if (usedItem.getType() != Material.BONE_MEAL) return;
        this.data.cancelIfDisabled(event, targetBlock.getWorld(), PControlTrigger.BONE_MEAL_USAGE);
        if (event.useItemInHand() == Event.Result.DENY) return;
        this.fertilizedBlocks.add(targetBlock.getLocation().toVector());
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockGrowEvent event) {
        if (this.fertilizedBlocks.remove(event.getBlock().getLocation().toVector())) {
            this.data.cancelIfDisabled(event, PControlTrigger.BONE_MEAL_USAGE);
            return;
        }
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
        else if (CustomTagModern.BONE_MEAL_HERBS.isTagged(to))
            this.data.cancelIfDisabled(event, PControlTrigger.BONE_MEAL_USAGE);
        else if (to == Material.TURTLE_EGG)
            this.data.cancelIfDisabled(event, PControlTrigger.TURTLES_LAYING_EGGS);
        else if (to == Material.CHORUS_FLOWER)
            this.data.cancelIfDisabled(event, PControlTrigger.CHORUSES_GROWING);
        else if (this.data.hasVersion(14) && to == Material.SWEET_BERRY_BUSH)
            this.data.cancelIfDisabled(event, PControlTrigger.SWEET_BERRIES_GROWING);
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

            if (from == Material.FARMLAND && to == Material.DIRT)
                this.data.cancelIfDisabled(event, world, PControlTrigger.FARMLANDS_TRAMPLING);
            else if (this.data.hasVersion(17) && from == Material.BIG_DRIPLEAF && to == Material.BIG_DRIPLEAF)
                this.data.cancelIfDisabled(event, world, PControlTrigger.DRIPLEAFS_LOWERING);
            else if (to == Material.REDSTONE_ORE)
                return; // Redstone ore activation
            else if (this.data.hasVersion(17) && to == Material.DEEPSLATE_REDSTONE_ORE)
                return; // Redstone ore activation
            else if (entityType == EntityType.BOAT)
                return; // Boats destroys lilies. TODO It is necessary to implement a smart system of destruction and restoration of water lilies so that there are no problems with movement
            else if (entityType == EntityType.RABBIT)
                this.data.cancelIfDisabled(event, world, PControlTrigger.RABBITS_EATING_CARROTS);
            else if (entityType == EntityType.SHEEP)
                this.data.cancelIfDisabled(event, world, PControlTrigger.SHEEPS_EATING_GRASS);
            else if (entityType == EntityType.TURTLE)
                this.data.cancelIfDisabled(event, world, PControlTrigger.TURTLES_LAYING_EGGS);
            else if (entityType == EntityType.ENDERMAN)
                this.data.cancelIfDisabled(event, world, PControlTrigger.ENDERMANS_GRIEFING);
            else if (entityType == EntityType.WITHER)
                this.data.cancelIfDisabled(event, world, PControlTrigger.WITHERS_GRIEFING);
            else if (entityType == EntityType.SILVERFISH)
                this.data.cancelIfDisabled(event, world, PControlTrigger.SILVERFISHES_HIDING_IN_BLOCKS);
            else if (entityType == EntityType.ARROW && from == Material.TNT)
                this.data.cancelIfDisabled(event, world, PControlTrigger.BURNING_ARROWS_ACTIVATE_TNT);
            else if (entityType == EntityType.VILLAGER)
                this.data.cancelIfDisabled(event, world, PControlTrigger.VILLAGERS_FARMING);
            else if ((entityType == EntityType.ZOMBIE || entityType == EntityType.ZOMBIE_VILLAGER) && Tag.WOODEN_DOORS.isTagged(from))
                this.data.cancelIfDisabled(event, world, PControlTrigger.ZOMBIES_BREAK_DOORS);
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
        else if (from == Material.DRAGON_EGG)
            this.data.cancelIfDisabled(event, world, PControlTrigger.DRAGON_EGGS_FALLING);
        else if (CustomTagModern.CONCRETE_POWDERS.isTagged(from))
            this.data.cancelIfDisabled(event, world, PControlTrigger.CONCRETE_POWDERS_FALLING);
        else if (this.data.hasVersion(14) && from == Material.SCAFFOLDING)
            this.data.cancelIfDisabled(event, world, PControlTrigger.SCAFFOLDING_FALLING);
        else if (CustomTagModern.GRAVITY_BLOCKS.isTagged(to))
            return; // Already existing falling blocks
        else if (this.data.hasVersion(17) && from == Material.POINTED_DRIPSTONE)
            this.data.cancelIfDisabled(event, world, PControlTrigger.POINTED_DRIPSTONES_FALLING);
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
        else if (CustomTagModern.UNDERWATER_BLOCKS_ONLY.isTagged(from)
                || (event.getBlock().getBlockData() instanceof Waterlogged
                && ((Waterlogged) event.getBlock().getBlockData()).isWaterlogged()))
            this.data.cancelIfDisabled(event, PControlTrigger.WATER_FLOWING);
        else if (from == Material.DRAGON_EGG)
            this.data.cancelIfDisabled(event, PControlTrigger.DRAGON_EGGS_TELEPORTING);
        else if (CustomTagModern.GRAVITY_BLOCKS.isTagged(from))
            return; // Seems bug while chunks generation (water near gravity blocks?): "Action BlockFromTo (GRAVEL > GRAVEL) was detected"
        else
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockFadeEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        if (CustomTagModern.GRASS_AND_PATH_BLOCKS.isTagged(from) && to == Material.DIRT)
            this.data.cancelIfDisabled(event, PControlTrigger.GRASS_BLOCKS_FADING);
        else if (from == Material.CRIMSON_NYLIUM && to == Material.NETHERRACK)
            this.data.cancelIfDisabled(event, PControlTrigger.CRIMSON_NYLIUM_FADING);
        else if (from == Material.WARPED_NYLIUM && to == Material.NETHERRACK)
            this.data.cancelIfDisabled(event, PControlTrigger.WARPED_NYLIUM_FADING);
        else if (from == Material.MYCELIUM && to == Material.DIRT)
            this.data.cancelIfDisabled(event, PControlTrigger.MYCELIUM_SPREADING);
        else if (from == Material.FARMLAND && to == Material.DIRT)
            this.data.cancelIfDisabled(event, PControlTrigger.FARMLANDS_DRYING);
        else if (from == Material.SNOW && CustomTagModern.WORLD_AIR.isTagged(to))
            this.data.cancelIfDisabled(event, PControlTrigger.SNOW_MELTING);
        else if (from == Material.ICE && to == Material.WATER)
            this.data.cancelIfDisabled(event, PControlTrigger.ICE_MELTING);
        else if (from == Material.FROSTED_ICE && to == Material.WATER)
            this.data.cancelIfDisabled(event, PControlTrigger.FROSTED_ICE_PHYSICS);
        else if (CustomTagModern.ALL_ALIVE_CORALS.isTagged(from) && CustomTagModern.ALL_DEAD_CORALS.isTagged(to))
            this.data.cancelIfDisabled(event, PControlTrigger.CORALS_DRYING);
        else if (from == Material.FIRE && CustomTagModern.WORLD_AIR.isTagged(to))
            this.data.cancelIfDisabled(event, PControlTrigger.FIRE_SPREADING);
        else if (this.data.hasVersion(14) && from == Material.SCAFFOLDING && to == Material.AIR)
            this.data.cancelIfDisabled(event, PControlTrigger.SCAFFOLDING_FALLING);
        else if (from == Material.REDSTONE_ORE && to == Material.REDSTONE_ORE)
            return; // Redstone ore deactivation
        else if (this.data.hasVersion(17) && from == Material.DEEPSLATE_REDSTONE_ORE && to == Material.DEEPSLATE_REDSTONE_ORE)
            return; // Redstone ore deactivation
        else if (from == Material.AIR && to == Material.AIR)
            return; // Strange server action. Perhaps this is due to the fall of blocks without a base (torches for example) during generation (only in mineshafts?)
        else
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(MoistureChangeEvent event) {
        BlockData oldData = event.getBlock().getBlockData();
        BlockData newData = event.getNewState().getBlockData();
        if (oldData instanceof Farmland && newData instanceof Farmland) {
            int oldMoisture = ((Farmland) oldData).getMoisture();
            int newMoisture = ((Farmland) newData).getMoisture();
            if (newMoisture < oldMoisture)
                this.data.cancelIfDisabled(event, PControlTrigger.FARMLANDS_DRYING);
            return;
        }
        Material from = oldData.getMaterial();
        Material to = newData.getMaterial();
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
        else if (to == Material.VINE && (CustomTagModern.WORLD_AIR.isTagged(from) || from == Material.VINE))
            this.data.cancelIfDisabled(event, PControlTrigger.VINES_GROWING);
        else if (CustomTagModern.LITTLE_MUSHROOMS.isTagged(to) && CustomTagModern.WORLD_AIR.isTagged(from))
            this.data.cancelIfDisabled(event, PControlTrigger.LITTLE_MUSHROOMS_SPREADING);
        else if (to == Material.KELP && from == Material.WATER)
            this.data.cancelIfDisabled(event, PControlTrigger.KELPS_GROWING);
        else if (to == Material.FIRE && CustomTagModern.WORLD_AIR.isTagged(from))
            this.data.cancelIfDisabled(event, PControlTrigger.FIRE_SPREADING);
        else if (to == Material.CHORUS_FLOWER)
            this.data.cancelIfDisabled(event, PControlTrigger.CHORUSES_GROWING);
        else if (this.data.hasVersion(14) && to == Material.BAMBOO && from == Material.AIR)
            this.data.cancelIfDisabled(event, PControlTrigger.BAMBOO_GROWING);
        else if (this.data.hasVersion(17)) {
            if (to == Material.CAVE_VINES && (CustomTagModern.WORLD_AIR.isTagged(from) || from == Material.CAVE_VINES))
                this.data.cancelIfDisabled(event, PControlTrigger.GLOW_BERRIES_GROWING);
            else if (to == Material.POINTED_DRIPSTONE && (CustomTagModern.WORLD_AIR.isTagged(from) || from == Material.POINTED_DRIPSTONE))
                this.data.cancelIfDisabled(event, PControlTrigger.POINTED_DRIPSTONES_GROWING);
            else if (to == Material.HANGING_ROOTS && from == Material.AIR)
                return; // BONE_MEAL_USAGE
            else if (to == Material.GLOW_LICHEN && (from == Material.AIR || from == Material.GLOW_LICHEN))
                return; // BONE_MEAL_USAGE
            else if (to == Material.SMALL_AMETHYST_BUD && from == Material.AIR)
                this.data.cancelIfDisabled(event, PControlTrigger.AMETHYST_CLUSTERS_GROWING);
            else if (to == Material.MEDIUM_AMETHYST_BUD && from == Material.SMALL_AMETHYST_BUD)
                this.data.cancelIfDisabled(event, PControlTrigger.AMETHYST_CLUSTERS_GROWING);
            else if (to == Material.LARGE_AMETHYST_BUD && from == Material.MEDIUM_AMETHYST_BUD)
                this.data.cancelIfDisabled(event, PControlTrigger.AMETHYST_CLUSTERS_GROWING);
            else if (to == Material.AMETHYST_CLUSTER && from == Material.LARGE_AMETHYST_BUD)
                this.data.cancelIfDisabled(event, PControlTrigger.AMETHYST_CLUSTERS_GROWING);
            else
                this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
        } else {
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void on(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) return;
        if (event.getBlockFace() != BlockFace.SELF) return;
        if (event.getClickedBlock() == null) return;
        this.handleInteraction(event, event.getClickedBlock());
    }

    @EventHandler(ignoreCancelled = true)
    private void on(EntityInteractEvent event) {
        if (event.getEntityType() == EntityType.VILLAGER && Tag.WOODEN_DOORS.isTagged(event.getBlock().getType()))
            return;
        this.handleInteraction(event, event.getBlock());
    }

    private void handleInteraction(@Nonnull Cancellable event, @Nonnull Block source) {
        World world = source.getWorld();
        Material material = source.getType();

        if (material == Material.FARMLAND)
            this.data.cancelIfDisabled(event, world, PControlTrigger.FARMLANDS_TRAMPLING);
        else if (CustomTagModern.REDSTONE_PASSIVE_INPUTS.isTagged(material))
            return; // Redstone activators
        else if (material == Material.REDSTONE_ORE)
            return; // Redstone ore activation
        else if (this.data.hasVersion(17) && material == Material.DEEPSLATE_REDSTONE_ORE)
            return; // Redstone ore activation
        else if (this.data.hasVersion(17) && material == Material.BIG_DRIPLEAF)
            return;
        else if (material == Material.TURTLE_EGG)
            this.data.cancelIfDisabled(event, world, PControlTrigger.TURTLE_EGGS_TRAMPLING);
        else
            this.unrecognizedAction(event, source.getLocation(), material);
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

        if (CustomTagModern.SIGNS.isTagged(to))
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
                else if (CustomTagModern.WALL_SIGNS.isTagged(to))
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
}
