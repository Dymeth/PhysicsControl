package ru.dymeth.pcontrol.legacy;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Attachable;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;
import ru.dymeth.pcontrol.PhysicsListenerCommon;
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.PControlTrigger;
import ru.dymeth.pcontrol.api.set.BlocksSet;

import javax.annotation.Nonnull;
import java.util.Collections;

@SuppressWarnings({"IsCancelled"})
public final class PhysicsListenerLegacy extends PhysicsListenerCommon {

    public PhysicsListenerLegacy(@Nonnull PControlData data) {
        super(data);
        this.initBlockGrowEvent();
        this.initEntityChangeBlockEvent();
        this.initBlockFromToEvent();
        this.initBlockFadeEvent();
        this.initBlockSpreadEvent();
        this.initEntityInteractEvent();
        this.initEntityBlockFormEvent();
    }

    private void initBlockGrowEvent() {
        if (this.data.hasVersion(0)) {
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.SUGAR_CANE_GROWING,
                this.customTag.SUGAR_CANE_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.CACTUS_GROWING,
                Collections.singleton(Material.CACTUS));
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.WHEAT_GROWING,
                this.customTag.WHEAT_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.POTATOES_GROWING,
                this.customTag.POTATO_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.CARROTS_GROWING,
                this.customTag.CARROT_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.PUMPKINS_GROWING,
                this.customTag.PUMPKIN_STEM_AND_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.MELONS_GROWING,
                this.customTag.MELON_STEM_AND_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.COCOAS_GROWING,
                Collections.singleton(Material.COCOA));
            this.rulesBlockGrowEventFromTo.regPair(PControlTrigger.VINES_GROWING,
                Collections.singleton(Material.VINE),
                Collections.singleton(Material.VINE));
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.NETHER_WARTS_GROWING,
                this.customTag.NETHER_WART_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.BONE_MEAL_USAGE,
                this.customTag.BONE_MEAL_HERBS.getValues());
        }
        if (this.data.hasVersion(9)) {
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.BEETROOTS_GROWING,
                this.customTag.BEETROOT_BLOCK.getValues());
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.CHORUSES_GROWING,
                Collections.singleton(Material.CHORUS_FLOWER));
        }
    }

    private void initEntityChangeBlockEvent() {
        if (this.data.hasVersion(0)) {
            this.rulesEntityChangeBlockEventFromTo.regPair(PControlTrigger.FARMLANDS_TRAMPLING,
                this.customTag.FARMLAND_BLOCK.getValues(),
                Collections.singleton(Material.DIRT));
            this.rulesEntityChangeBlockEventTo.regSingle(PControlTrigger.IGNORED_STATE, // Redstone ore activation
                this.customTag.REDSTONE_ORE_BLOCKS.getValues());
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.SAND_FALLING,
                this.customTag.SAND.getValues());
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.GRAVEL_FALLING,
                this.customTag.GRAVEL.getValues());
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.ANVILS_FALLING,
                this.customTag.ANVIL.getValues());
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.DRAGON_EGGS_FALLING,
                Collections.singleton(Material.DRAGON_EGG));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.SAND_FALLING,
                this.customTag.SAND.getValues());
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.GRAVEL_FALLING,
                this.customTag.GRAVEL.getValues());
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.ANVILS_FALLING,
                this.customTag.ANVIL.getValues());
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.DRAGON_EGGS_FALLING,
                Collections.singleton(Material.DRAGON_EGG));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.IGNORED_STATE, // On custom falling blocks fall (created by third-party plugins like WoodCutter)
                this.customTag.WORLD_AIR.getValues());
            this.rulesNonFallingEntityChangeBlockEventByFrom.regPair(PControlTrigger.BURNING_ARROWS_ACTIVATE_TNT,
                Collections.singleton(EntityType.ARROW),
                Collections.singleton(Material.TNT));
            this.rulesNonFallingEntityChangeBlockEventByFrom.regPair(PControlTrigger.ZOMBIES_BREAK_DOORS,
                Collections.singleton(EntityType.ZOMBIE),
                this.customTag.WOODEN_DOORS.getValues());
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.IGNORED_STATE, // Boats destroys lilies. TODO It is necessary to implement a smart system of destruction and restoration of water lilies so that there are no problems with movement
                Collections.singleton(EntityType.BOAT));
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.SHEEPS_EATING_GRASS,
                Collections.singleton(EntityType.SHEEP));
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.ENDERMANS_GRIEFING,
                Collections.singleton(EntityType.ENDERMAN));
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.WITHERS_GRIEFING,
                Collections.singleton(EntityType.WITHER));
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.SILVERFISHES_HIDING_IN_BLOCKS,
                Collections.singleton(EntityType.SILVERFISH));
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.VILLAGERS_FARMING,
                Collections.singleton(EntityType.VILLAGER));
        }
        if (this.data.hasVersion(8)) {
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.RABBITS_EATING_CARROTS,
                Collections.singleton(EntityType.RABBIT));
        }
        if (this.data.hasVersion(11)) {
            this.rulesNonFallingEntityChangeBlockEventByFrom.regPair(PControlTrigger.ZOMBIES_BREAK_DOORS,
                Collections.singleton(EntityType.ZOMBIE_VILLAGER),
                this.customTag.WOODEN_DOORS.getValues());
        }
        if (this.data.hasVersion(12)) {
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.CONCRETE_POWDERS_FALLING,
                this.customTag.CONCRETE_POWDERS.getValues());
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.CONCRETE_POWDERS_FALLING,
                this.customTag.CONCRETE_POWDERS.getValues());
        }
    }

    private void initBlockFromToEvent() {
        if (this.data.hasVersion(0)) {
            this.rulesBlockFromToEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Strange thing from FluidTypeFlowing
                this.customTag.WORLD_AIR.getValues(),
                this.customTag.WORLD_AIR.getValues());
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.LAVA_FLOWING,
                this.customTag.LAVA.getValues());
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.WATER_FLOWING,
                this.customTag.UNDERWATER_BLOCKS_ONLY.getValues());
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.IGNORED_STATE, // Seems bug while chunks generation (water near gravity blocks?): "Action BlockFromTo (GRAVEL > GRAVEL) was detected"
                this.customTag.NATURAL_GRAVITY_BLOCKS.getValues());
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.DRAGON_EGGS_TELEPORTING,
                Collections.singleton(Material.DRAGON_EGG));
        }
    }

    private void initBlockFadeEvent() {
        if (this.data.hasVersion(0)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.GRASS_BLOCKS_FADING,
                new BlocksSet().add(this.customTag.GRASS_BLOCK).add(this.customTag.DIRT_PATH_BLOCK).getValues(),
                Collections.singleton(Material.DIRT));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.MYCELIUM_SPREADING,
                this.customTag.MYCELIUM_BLOCK.getValues(),
                Collections.singleton(Material.DIRT));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.FARMLANDS_DRYING,
                this.customTag.FARMLAND_BLOCK.getValues(),
                Collections.singleton(Material.DIRT));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.SNOW_MELTING,
                Collections.singleton(Material.SNOW),
                this.customTag.WORLD_AIR.getValues());
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.ICE_MELTING,
                Collections.singleton(Material.ICE),
                new BlocksSet().add(this.customTag.WATER).add(this.customTag.WORLD_AIR).getValues());
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.FIRE_SPREADING,
                Collections.singleton(Material.FIRE),
                this.customTag.WORLD_AIR.getValues());
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Redstone ore deactivation
                this.customTag.REDSTONE_ORE_BLOCKS.getValues(),
                this.customTag.REDSTONE_ORE_BLOCKS.getValues());
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Strange server action. Perhaps this is due to the fall of blocks without a base (torches for example) during generation (only in mineshafts?)
                this.customTag.WORLD_AIR.getValues(),
                this.customTag.WORLD_AIR.getValues());
        }
        if (this.data.hasVersion(9)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.FROSTED_ICE_PHYSICS,
                Collections.singleton(Material.FROSTED_ICE),
                this.customTag.WATER.getValues());
        }
    }

    private void initBlockSpreadEvent() {
        if (this.data.hasVersion(0)) {
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.GRASS_SPREADING,
                Collections.singleton(Material.DIRT),
                this.customTag.GRASS_BLOCK.getValues());
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.MYCELIUM_SPREADING,
                Collections.singleton(Material.DIRT),
                this.customTag.MYCELIUM_BLOCK.getValues());
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.VINES_GROWING,
                new BlocksSet(Material.VINE).add(this.customTag.WORLD_AIR).getValues(),
                Collections.singleton(Material.VINE));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.LITTLE_MUSHROOMS_SPREADING,
                this.customTag.WORLD_AIR.getValues(),
                this.customTag.LITTLE_MUSHROOMS.getValues());
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.FIRE_SPREADING,
                this.customTag.WORLD_AIR.getValues(),
                Collections.singleton(Material.FIRE));
        }
        if (this.data.hasVersion(9)) {
            this.rulesBlockSpreadEventTo.regSingle(PControlTrigger.CHORUSES_GROWING,
                Collections.singleton(Material.CHORUS_FLOWER));
        }
    }

    private void initEntityInteractEvent() {
        if (this.data.hasVersion(0)) {
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.FARMLANDS_TRAMPLING,
                this.customTag.FARMLAND_BLOCK.getValues());
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.IGNORED_STATE, // Redstone activators
                this.customTag.REDSTONE_PASSIVE_INPUTS.getValues());
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.IGNORED_STATE, // Redstone ore activation
                this.customTag.REDSTONE_ORE_BLOCKS.getValues());
        }
    }

    private void initEntityBlockFormEvent() {
        if (this.data.hasVersion(0)) {
            this.rulesEntityBlockFormEventFromTo.regPair(PControlTrigger.SNOW_GOLEMS_CREATE_SNOW,
                this.customTag.WORLD_AIR.getValues(),
                Collections.singleton(Material.SNOW));
        }
        if (this.data.hasVersion(9)) {
            this.rulesEntityBlockFormEventFromTo.regPair(PControlTrigger.FROSTED_ICE_PHYSICS,
                this.customTag.WATER.getValues(),
                Collections.singleton(Material.FROSTED_ICE));
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
        if (!(usedItem.getData() instanceof Dye)) return;
        if (((Dye) usedItem.getData()).getColor() != DyeColor.WHITE) return;
        this.data.cancelIfDisabled(event, targetBlock.getWorld(), PControlTrigger.BONE_MEAL_USAGE);
        if (event.isCancelled()) return;
        this.fertilizedBlocks.add(targetBlock.getLocation().toVector());
    }

    @EventHandler(ignoreCancelled = true)
    private void on(EntityInteractEvent event) {
        if (event.getEntityType() == EntityType.VILLAGER && this.customTag.WOODEN_DOORS.isTagged(event.getBlock().getType())) {
            return;
        }
        this.handleInteraction(event, event.getBlock(), event.getEntity());
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
