package ru.dymeth.pcontrol.modern;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.MoistureChangeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.dymeth.pcontrol.PhysicsListenerCommon;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.PControlTrigger;
import ru.dymeth.pcontrol.api.set.MaterialKeysSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;

@SuppressWarnings({"IsCancelled"})
public final class PhysicsListenerModern extends PhysicsListenerCommon {
    public PhysicsListenerModern(@Nonnull PControlData data) {
        super(data);
    }

    {
        if (this.data.hasVersion(0)) {
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.SUGAR_CANE_GROWING,
                CustomTag.SUGAR_CANE_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.CACTUS_GROWING,
                Collections.singleton(Material.CACTUS));
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.WHEAT_GROWING,
                CustomTag.WHEAT_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.POTATOES_GROWING,
                CustomTag.POTATO_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.CARROTS_GROWING,
                CustomTag.CARROT_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.PUMPKINS_GROWING,
                CustomTag.PUMPKIN_STEM_AND_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.MELONS_GROWING,
                CustomTag.MELON_STEM_AND_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.COCOAS_GROWING,
                Collections.singleton(Material.COCOA));
            this.rulesBlockGrowEventFromTo.regPair(PControlTrigger.VINES_GROWING,
                Collections.singleton(Material.VINE),
                Collections.singleton(Material.VINE));
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.NETHER_WARTS_GROWING,
                CustomTag.NETHER_WART_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.BONE_MEAL_USAGE,
                CustomTag.BONE_MEAL_HERBS.getValues());
        }
        if (this.data.hasVersion(9)) {
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.BEETROOTS_GROWING,
                CustomTag.BEETROOT_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.CHORUSES_GROWING,
                Collections.singleton(Material.CHORUS_FLOWER));
        }
        // modern versions only
        if (this.data.hasVersion(13)) {
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.TURTLES_LAYING_EGGS,
                Collections.singleton(Material.TURTLE_EGG));
        }
        if (this.data.hasVersion(14)) {
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.SWEET_BERRIES_GROWING,
                Collections.singleton(Material.SWEET_BERRY_BUSH));
        }
    }

    {
        if (this.data.hasVersion(0)) {
            this.rulesEntityChangeBlockEventFromTo.regPair(PControlTrigger.FARMLANDS_TRAMPLING,
                CustomTag.FARMLAND_BLOCK.getValues(),
                Collections.singleton(Material.DIRT));
            this.rulesEntityChangeBlockEventTo.regSingle(PControlTrigger.IGNORED_STATE, // Redstone ore activation
                CustomTag.REDSTONE_ORE_BLOCKS.getValues());
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.SAND_FALLING,
                CustomTag.SAND.getValues());
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.GRAVEL_FALLING,
                Collections.singleton(Material.GRAVEL));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.ANVILS_FALLING,
                CustomTag.ANVIL.getValues());
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.DRAGON_EGGS_FALLING,
                Collections.singleton(Material.DRAGON_EGG));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.IGNORED_STATE, // On custom falling blocks fall (created by third-party plugins like WoodCutter)
                CustomTag.WORLD_AIR.getValues());
            this.rulesFallingEntityChangeBlockEventByFrom.regPair(PControlTrigger.BURNING_ARROWS_ACTIVATE_TNT,
                Collections.singleton(EntityType.ARROW),
                Collections.singleton(Material.TNT));
            this.rulesFallingEntityChangeBlockEventByFrom.regPair(PControlTrigger.ZOMBIES_BREAK_DOORS,
                Collections.singleton(EntityType.ZOMBIE),
                CustomTag.WOODEN_DOORS.getValues());
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.IGNORED_STATE, // Boats destroys lilies. TODO It is necessary to implement a smart system of destruction and restoration of water lilies so that there are no problems with movement
                Collections.singleton(EntityType.BOAT));
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.SHEEPS_EATING_GRASS,
                Collections.singleton(EntityType.SHEEP));
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.ENDERMANS_GRIEFING,
                Collections.singleton(EntityType.ENDERMAN));
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.WITHERS_GRIEFING,
                Collections.singleton(EntityType.WITHER));
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.SILVERFISHES_HIDING_IN_BLOCKS,
                Collections.singleton(EntityType.SILVERFISH));
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.VILLAGERS_FARMING,
                Collections.singleton(EntityType.VILLAGER));
        }
        if (this.data.hasVersion(8)) {
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.RABBITS_EATING_CARROTS,
                Collections.singleton(EntityType.RABBIT));
        }
        if (this.data.hasVersion(11)) {
            this.rulesFallingEntityChangeBlockEventByFrom.regPair(PControlTrigger.ZOMBIES_BREAK_DOORS,
                Collections.singleton(EntityType.ZOMBIE_VILLAGER),
                CustomTag.WOODEN_DOORS.getValues());
        }
        if (this.data.hasVersion(12)) {
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.CONCRETE_POWDERS_FALLING,
                CustomTag.CONCRETE_POWDERS.getValues());
        }
        // modern versions only
        if (this.data.hasVersion(13)) {
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.TURTLES_LAYING_EGGS,
                Collections.singleton(EntityType.TURTLE));
        }
        if (this.data.hasVersion(14)) {
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.SCAFFOLDING_FALLING,
                Collections.singleton(Material.SCAFFOLDING));
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.RAVAGERS_DESTROY_BLOCKS,
                Collections.singleton(EntityType.RAVAGER));
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.FOXES_EATS_FROM_SWEET_BERRY_BUSHES,
                Collections.singleton(EntityType.FOX));
        }
        if (this.data.hasVersion(17)) {
            this.rulesEntityChangeBlockEventFromTo.regPair(PControlTrigger.DRIPLEAFS_LOWERING,
                Collections.singleton(Material.BIG_DRIPLEAF),
                Collections.singleton(Material.BIG_DRIPLEAF));
            this.rulesEntityChangeBlockEventFromTo.regPair(PControlTrigger.POWDER_SNOW_MELTS_FROM_BURNING_ENTITIES,
                Collections.singleton(Material.POWDER_SNOW),
                CustomTag.WORLD_AIR.getValues());
            this.rulesEntityChangeBlockEventFromTo.regPair(PControlTrigger.GLOW_BERRIES_PICKING,
                Collections.singleton(Material.CAVE_VINES),
                Collections.singleton(Material.CAVE_VINES));
            this.rulesEntityChangeBlockEventFromTo.regPair(PControlTrigger.GLOW_BERRIES_PICKING,
                Collections.singleton(Material.CAVE_VINES_PLANT),
                Collections.singleton(Material.CAVE_VINES_PLANT));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.POINTED_DRIPSTONES_FALLING,
                Collections.singleton(Material.POINTED_DRIPSTONE));
        }
    }

    {
        if (this.data.hasVersion(0)) {
            this.rulesBlockFromToEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Strange thing from FluidTypeFlowing
                CustomTag.WORLD_AIR.getValues(),
                CustomTag.WORLD_AIR.getValues());
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.LAVA_FLOWING,
                CustomTag.LAVA.getValues());
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.WATER_FLOWING,
                CustomTag.UNDERWATER_BLOCKS_ONLY.getValues());
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.IGNORED_STATE, // Seems bug while chunks generation (water near gravity blocks?): "Action BlockFromTo (GRAVEL > GRAVEL) was detected"
                CustomTag.NATURAL_GRAVITY_BLOCKS.getValues());
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.DRAGON_EGGS_TELEPORTING,
                Collections.singleton(Material.DRAGON_EGG));
        }
        // modern versions only
        if (this.data.hasVersion(13)) {
            this.rulesBlockFromToEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Seems bug while chunks generation (kelp near caves?): "Action BlockFromTo (KELP > AIR) was detected"
                Collections.singleton(Material.KELP),
                CustomTag.WORLD_AIR.getValues());
        }
    }

    {
        if (this.data.hasVersion(0)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.GRASS_BLOCKS_FADING,
                new MaterialKeysSet().add(CustomTag.GRASS_BLOCK).add(CustomTag.DIRT_PATH_BLOCK).getValues(),
                Collections.singleton(Material.DIRT));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.MYCELIUM_SPREADING,
                CustomTag.MYCELIUM_BLOCK.getValues(),
                Collections.singleton(Material.DIRT));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.FARMLANDS_DRYING,
                CustomTag.FARMLAND_BLOCK.getValues(),
                Collections.singleton(Material.DIRT));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.SNOW_MELTING,
                Collections.singleton(Material.SNOW),
                CustomTag.WORLD_AIR.getValues());
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.ICE_MELTING,
                Collections.singleton(Material.ICE),
                new MaterialKeysSet().add(CustomTag.WATER).add(CustomTag.WORLD_AIR).getValues());
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.FIRE_SPREADING,
                Collections.singleton(Material.FIRE),
                CustomTag.WORLD_AIR.getValues());
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Redstone ore deactivation
                CustomTag.REDSTONE_ORE_BLOCKS.getValues(),
                CustomTag.REDSTONE_ORE_BLOCKS.getValues());
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Strange server action. Perhaps this is due to the fall of blocks without a base (torches for example) during generation (only in mineshafts?)
                CustomTag.WORLD_AIR.getValues(),
                CustomTag.WORLD_AIR.getValues());
        }
        if (this.data.hasVersion(9)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.FROSTED_ICE_PHYSICS,
                Collections.singleton(Material.FROSTED_ICE),
                CustomTag.WATER.getValues());
        }
        // modern versions only
        if (this.data.hasVersion(13)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.CORALS_DRYING,
                CustomTag.ALL_ALIVE_CORALS.getValues(),
                CustomTag.ALL_DEAD_CORALS.getValues());
        }
        if (this.data.hasVersion(14)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.SCAFFOLDING_FALLING,
                Collections.singleton(Material.SCAFFOLDING),
                CustomTag.WORLD_AIR.getValues());
        }
        if (this.data.hasVersion(16)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.CRIMSON_NYLIUM_FADING,
                Collections.singleton(Material.CRIMSON_NYLIUM),
                Collections.singleton(Material.NETHERRACK));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.WARPED_NYLIUM_FADING,
                Collections.singleton(Material.WARPED_NYLIUM),
                Collections.singleton(Material.NETHERRACK));
        }
    }

    {
        if (this.data.hasVersion(0)) {
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.GRASS_SPREADING,
                Collections.singleton(Material.DIRT),
                CustomTag.GRASS_BLOCK.getValues());
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.MYCELIUM_SPREADING,
                Collections.singleton(Material.DIRT),
                CustomTag.MYCELIUM_BLOCK.getValues());
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.VINES_GROWING,
                new MaterialKeysSet(Material.VINE).add(CustomTag.WORLD_AIR).getValues(),
                Collections.singleton(Material.VINE));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.LITTLE_MUSHROOMS_SPREADING,
                CustomTag.WORLD_AIR.getValues(),
                CustomTag.LITTLE_MUSHROOMS.getValues());
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.FIRE_SPREADING,
                CustomTag.WORLD_AIR.getValues(),
                Collections.singleton(Material.FIRE));
        }
        if (this.data.hasVersion(9)) {
            this.rulesBlockSpreadEventTo.regSingle(PControlTrigger.CHORUSES_GROWING,
                Collections.singleton(Material.CHORUS_FLOWER));
        }
        // modern versions only
        if (this.data.hasVersion(13)) {
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.KELPS_GROWING,
                CustomTag.WATER.getValues(),
                Collections.singleton(Material.KELP));
        }
        if (this.data.hasVersion(14)) {
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.BAMBOO_GROWING,
                CustomTag.WORLD_AIR.getValues(),
                Collections.singleton(Material.BAMBOO));
        }
        if (this.data.hasVersion(16)) {
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.WEEPING_VINES_GROWING,
                CustomTag.WORLD_AIR.getValues(),
                Collections.singleton(Material.WEEPING_VINES));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.TWISTING_VINES_GROWING,
                CustomTag.WORLD_AIR.getValues(),
                Collections.singleton(Material.TWISTING_VINES));
        }
        if (this.data.hasVersion(17)) {
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.GLOW_BERRIES_GROWING,
                new MaterialKeysSet(Material.CAVE_VINES).add(CustomTag.WORLD_AIR).getValues(),
                Collections.singleton(Material.CAVE_VINES));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.POINTED_DRIPSTONES_GROWING,
                new MaterialKeysSet(Material.POINTED_DRIPSTONE).add(CustomTag.WORLD_AIR).getValues(),
                Collections.singleton(Material.POINTED_DRIPSTONE));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // BONE_MEAL_USAGE
                CustomTag.WORLD_AIR.getValues(),
                Collections.singleton(Material.HANGING_ROOTS));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // BONE_MEAL_USAGE
                new MaterialKeysSet(Material.GLOW_LICHEN).add(CustomTag.WORLD_AIR).getValues(),
                Collections.singleton(Material.GLOW_LICHEN));
            this.rulesBlockSpreadEventTo.regSingle(PControlTrigger.AMETHYST_CLUSTERS_GROWING, // from = AIR, CAVE_AIR, WATER, etc
                Collections.singleton(Material.SMALL_AMETHYST_BUD));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.AMETHYST_CLUSTERS_GROWING,
                Collections.singleton(Material.SMALL_AMETHYST_BUD),
                Collections.singleton(Material.MEDIUM_AMETHYST_BUD));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.AMETHYST_CLUSTERS_GROWING,
                Collections.singleton(Material.MEDIUM_AMETHYST_BUD),
                Collections.singleton(Material.LARGE_AMETHYST_BUD));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.AMETHYST_CLUSTERS_GROWING,
                Collections.singleton(Material.LARGE_AMETHYST_BUD),
                Collections.singleton(Material.AMETHYST_CLUSTER));
        }
        if (this.data.hasVersion(19)) {
            this.rulesBlockSpreadEventTo.regSingle(PControlTrigger.SCULKS_SPREADING,
                Arrays.asList(Material.SCULK, Material.SCULK_VEIN));
        }
    }

    {
        if (this.data.hasVersion(0)) {
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.FARMLANDS_TRAMPLING,
                CustomTag.FARMLAND_BLOCK.getValues());
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.IGNORED_STATE, // Redstone activators
                CustomTag.REDSTONE_PASSIVE_INPUTS.getValues());
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.IGNORED_STATE, // Redstone ore activation
                CustomTag.REDSTONE_ORE_BLOCKS.getValues());
        }
        // modern versions only
        if (this.data.hasVersion(13)) {
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.TURTLE_EGGS_TRAMPLING,
                Collections.singleton(Material.TURTLE_EGG));
        }
        if (this.data.hasVersion(17)) {
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.IGNORED_STATE, // Control by DRIPLEAFS_LOWERING
                Collections.singleton(Material.BIG_DRIPLEAF));
        }
    }

    {
        if (this.data.hasVersion(0)) {
            this.rulesEntityBlockFormEventFromTo.regPair(PControlTrigger.SNOW_GOLEMS_CREATE_SNOW,
                CustomTag.WORLD_AIR.getValues(),
                Collections.singleton(Material.SNOW));
        }
        if (this.data.hasVersion(9)) {
            this.rulesEntityBlockFormEventFromTo.regPair(PControlTrigger.FROSTED_ICE_PHYSICS,
                CustomTag.WATER.getValues(),
                Collections.singleton(Material.FROSTED_ICE));
        }
        // modern versions only
        if (this.data.hasVersion(14)) {
            this.rulesEntityBlockFormEventFromTo.regPair(PControlTrigger.WITHER_CREATE_WITHER_ROSE_BLOCKS,
                CustomTag.WORLD_AIR.getValues(),
                Collections.singleton(Material.WITHER_ROSE));
        }
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
        PControlTrigger trigger = this.rulesBlockFromToEventFromTo.findTrigger(from, to);
        if (trigger == null) trigger = this.rulesBlockFromToEventFrom.findTrigger(to);
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
    private void on(EntityInteractEvent event) {
        if (event.getEntityType() == EntityType.VILLAGER && CustomTag.WOODEN_DOORS.isTagged(event.getBlock().getType())) {
            return;
        }
        this.handleInteraction(event, event.getBlock(), event.getEntity());
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

        if (CustomTag.SIGNS.isTagged(to)) {
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
                } else if (CustomTag.WALL_SIGNS.isTagged(to)) {
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
