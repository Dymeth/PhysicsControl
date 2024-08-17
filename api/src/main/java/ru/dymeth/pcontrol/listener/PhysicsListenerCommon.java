package ru.dymeth.pcontrol.listener;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;
import ru.dymeth.pcontrol.PhysicsListener;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.EventsListenerParser;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.rules.TriggerRules;
import ru.dymeth.pcontrol.rules.pair.EntityMaterialRules;
import ru.dymeth.pcontrol.rules.pair.MaterialMaterialRules;
import ru.dymeth.pcontrol.rules.single.EntityRules;
import ru.dymeth.pcontrol.rules.single.MaterialRules;
import ru.dymeth.pcontrol.rules.single.TreeRules;
import ru.dymeth.pcontrol.util.ReflectionUtils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public final class PhysicsListenerCommon extends PhysicsListener {

    public PhysicsListenerCommon(@Nonnull PControlData data) {
        super(data);

        EventsListenerParser parser = new EventsListenerParser(this.data);
        for (TriggerRules<?> rules : this.getAllTriggersRules()) {
            parser.registerParser(rules);
        }
        parser.parseAllEvents();
        this.unregisterUnavailableTriggers();
        this.forceTriggersAvailable();
    }

    @Nonnull
    private List<TriggerRules<?>> getAllTriggersRules() {
        return Arrays.asList(
            this.rulesBlockGrowEventFromTo,
            this.rulesBlockGrowEventTo,

            this.rulesEntityChangeBlockEventFromTo,
            this.rulesEntityChangeBlockEventTo,
            this.rulesFallingEntityChangeBlockEventBy,
            this.rulesFallingEntityChangeBlockEventFrom,
            this.rulesNonFallingEntityChangeBlockEventByFrom,
            this.rulesNonFallingEntityChangeBlockEventBy,

            this.rulesBlockFromToEventFromTo,
            this.rulesBlockFromToEventFrom,

            this.rulesBlockFadeEventFromTo,
            this.rulesBlockFadeEventTo,

            this.rulesBlockSpreadEventFromTo,
            this.rulesBlockSpreadEventTo,

            this.rulesEntityInteractEventMaterial,

            this.rulesPlayerInteractEventMaterial,

            this.rulesEntityBlockFormEventFromTo,
            this.rulesEntityBlockFormEventTo,

            this.rulesStructureGrowEventTo,

            this.rulesBlockPhysicsEventFrom
        );
    }

    private void unregisterUnavailableTriggers() {
        if (!this.legacyBlockPhysicsEvent && !this.modernBlockPhysicsEvent) {
            this.rulesBlockPhysicsEventFrom.unregisterAll();
        }
    }

    private void forceTriggersAvailable() {
        if (this.data.hasVersion(1, 0, 0)) {
            this.triggers.PLAYERS_FLINT_USAGE.markAvailable();
            this.triggers.FIRE_SPREADING.markAvailable();
            this.triggers.PLAYERS_BONE_MEAL_USAGE.markAvailable();
            this.triggers.LEAVES_DECAY.markAvailable();
        }
        if (this.data.hasVersion(1, 11, 0)) { // ProjectileHitEvent.getHitBlock() since 1.11
            this.triggers.BLOCK_HIT_PROJECTILES_REMOVING.markAvailable();
        }
    }

    private final MaterialMaterialRules rulesBlockGrowEventFromTo = new MaterialMaterialRules(
        this.data, BlockGrowEvent.class, "from", "to");
    private final MaterialRules rulesBlockGrowEventTo = new MaterialRules(
        this.data, BlockGrowEvent.class, "to");

    @EventHandler(ignoreCancelled = true)
    private void on(BlockGrowEvent event) {
        if (this.fertilizedBlocks.remove(event.getBlock().getLocation().toVector())) {
            this.data.cancelIfDisabled(event, this.triggers.PLAYERS_BONE_MEAL_USAGE);
            return;
        }
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        PControlTrigger trigger = this.rulesBlockGrowEventFromTo.findTrigger(from, to);
        if (trigger == null) trigger = this.rulesBlockGrowEventTo.findTrigger(to);

        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
        } else {
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
        }
    }

    private final MaterialMaterialRules rulesEntityChangeBlockEventFromTo = new MaterialMaterialRules(
        this.data, EntityChangeBlockEvent.class, "from", "to");
    private final MaterialRules rulesEntityChangeBlockEventTo = new MaterialRules(
        this.data, EntityChangeBlockEvent.class, "to");
    private final MaterialRules rulesFallingEntityChangeBlockEventBy = new MaterialRules(
        this.data, EntityChangeBlockEvent.class, "falling-by");
    private final MaterialRules rulesFallingEntityChangeBlockEventFrom = new MaterialRules(
        this.data, EntityChangeBlockEvent.class, "falling-from");
    private final EntityMaterialRules rulesNonFallingEntityChangeBlockEventByFrom = new EntityMaterialRules(
        this.data, EntityChangeBlockEvent.class, "non-falling-by", "non-falling-from");
    private final EntityRules rulesNonFallingEntityChangeBlockEventBy = new EntityRules(
        this.data, EntityChangeBlockEvent.class, "non-falling-by");

    // Prevent client bug with disappearing blocks on start falling (fixed on paper 1.16.5, spigot 1.19.4 and client 1.18.2)
    private final boolean fixBlocksGravity = !this.data.hasVersion(1, 19, 4);

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void on(EntityChangeBlockEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getTo();

        boolean updateBlockOnCancel = false;

        PControlTrigger trigger = this.rulesEntityChangeBlockEventFromTo.findTrigger(from, to);
        if (trigger == null) trigger = this.rulesEntityChangeBlockEventTo.findTrigger(to);

        if (trigger == null) {
            if (event.getEntity() instanceof FallingBlock) {
                Material by = this.versionsAdapter.getFallingBlockMaterial((FallingBlock) event.getEntity());

                trigger = this.rulesFallingEntityChangeBlockEventBy.findTrigger(from);
                if (trigger == null) trigger = this.rulesFallingEntityChangeBlockEventFrom.findTrigger(by);

                if (trigger == null) {
                    this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to + " (by falling " + by + ")");
                    return;
                }
                if (this.fixBlocksGravity) {
                    updateBlockOnCancel = true;
                }
            } else {
                EntityType by = event.getEntity().getType();

                trigger = this.rulesNonFallingEntityChangeBlockEventByFrom.findTrigger(by, from);
                if (trigger == null) trigger = this.rulesNonFallingEntityChangeBlockEventBy.findTrigger(by);
                // TODO It is necessary to implement a smart system of destruction and restoration
                //  of water lilies so that there are no problems with movement

                if (trigger == null) {
                    this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to + " (by " + by + ")");
                }
            }
        }

        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
        }

        if (event.isCancelled() && updateBlockOnCancel) {
            event.getBlock().getState().update(false, false);
        }
    }

    private final MaterialMaterialRules rulesBlockFromToEventFromTo = new MaterialMaterialRules(
        this.data, BlockFromToEvent.class, "from", "to");
    private final MaterialRules rulesBlockFromToEventFrom = new MaterialRules(
        this.data, BlockFromToEvent.class, "from");

    @EventHandler(ignoreCancelled = true)
    private void on(BlockFromToEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getToBlock().getType();

        PControlTrigger trigger = this.rulesBlockFromToEventFromTo.findTrigger(from, to);
        if (trigger == null) trigger = this.rulesBlockFromToEventFrom.findTrigger(from);
        if (trigger == null && this.versionsAdapter.isBlockContainsWater(event.getBlock()))
            trigger = this.triggers.WATER_FLOWING;

        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
        } else {
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
        }
    }

    private final MaterialMaterialRules rulesBlockFadeEventFromTo = new MaterialMaterialRules(
        this.data, BlockFadeEvent.class, "from", "to");
    private final MaterialRules rulesBlockFadeEventTo = new MaterialRules(
        this.data, BlockFadeEvent.class, "to");

    @EventHandler(ignoreCancelled = true)
    private void on(BlockFadeEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        PControlTrigger trigger = this.rulesBlockFadeEventFromTo.findTrigger(from, to);
        if (trigger == null) trigger = this.rulesBlockFadeEventTo.findTrigger(to);

        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
        } else {
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
        }
    }

    private final MaterialMaterialRules rulesBlockSpreadEventFromTo = new MaterialMaterialRules(
        this.data, BlockSpreadEvent.class, "from", "to");
    private final MaterialRules rulesBlockSpreadEventTo = new MaterialRules(
        this.data, BlockSpreadEvent.class, "to");

    @EventHandler(ignoreCancelled = true)
    private void on(BlockSpreadEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        PControlTrigger trigger = this.rulesBlockSpreadEventFromTo.findTrigger(from, to);
        if (trigger == null) trigger = this.rulesBlockSpreadEventTo.findTrigger(to);

        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
        } else {
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
        }
    }


    private final MaterialRules rulesPlayerInteractEventMaterial = new MaterialRules(
        this.data, PlayerInteractEvent.class, "material");
    private final Set<Material> tagEndPortalFrames = this.data.getCustomTags().getTag("end_portal_frames", Material.class);

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void on(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) return;
        if (event.getAction() == Action.PHYSICAL) {
            if (event.getBlockFace() != BlockFace.SELF) return;
            this.handleInteraction(this.rulesPlayerInteractEventMaterial, event, clickedBlock, event.getPlayer());
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (this.tagEndPortalFrames.contains(clickedBlock.getType())) {
                this.data.cancelIfDisabled(event, clickedBlock.getWorld(), this.triggers.END_PORTAL_FRAMES_FILLING);
            }
        }
    }

    private final MaterialRules rulesEntityInteractEventMaterial = new MaterialRules(
        this.data, EntityInteractEvent.class, "material");
    private final Set<Material> tagWoodenDoors = this.data.getCustomTags().getTag("wooden_doors", Material.class);

    @EventHandler(ignoreCancelled = true)
    private void on(EntityInteractEvent event) {
        if (event.getEntityType() == EntityType.VILLAGER && this.tagWoodenDoors.contains(event.getBlock().getType())) {
            return;
        }
        this.handleInteraction(this.rulesEntityInteractEventMaterial, event, event.getBlock(), event.getEntity());
    }

    private void handleInteraction(@Nonnull MaterialRules rules, @Nonnull Cancellable event, @Nonnull Block source, @Nonnull Entity entity) {
        World world = source.getWorld();
        Material material = source.getType();

        PControlTrigger trigger = rules.findTrigger(material);

        if (trigger != null) {
            this.data.cancelIfDisabled(event, world, trigger);
        } else {
            this.unrecognizedAction(event, source.getLocation(), material + " (by " + entity + ")");
        }
    }

    private final MaterialMaterialRules rulesEntityBlockFormEventFromTo = new MaterialMaterialRules(
        this.data, EntityBlockFormEvent.class, "from", "to");
    private final MaterialRules rulesEntityBlockFormEventTo = new MaterialRules(
        this.data, EntityBlockFormEvent.class, "to");

    @EventHandler(ignoreCancelled = true)
    private void on(EntityBlockFormEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getNewState().getType();

        PControlTrigger trigger = this.rulesEntityBlockFormEventFromTo.findTrigger(from, to);
        if (trigger == null) trigger = this.rulesEntityBlockFormEventTo.findTrigger(to);

        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
        } else {
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
        }
    }

    private final TreeRules rulesStructureGrowEventTo = new TreeRules(
        this.data, StructureGrowEvent.class, "to");

    @EventHandler(ignoreCancelled = true)
    private void on(StructureGrowEvent event) {
        if (event.getPlayer() != null) {
            this.data.cancelIfDisabled(event, this.triggers.PLAYERS_BONE_MEAL_USAGE);
            return;
        }
        Material from = event.getLocation().getBlock().getType();
        TreeType to = event.getSpecies();

        PControlTrigger trigger = this.rulesStructureGrowEventTo.findTrigger(to);

        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
        } else {
            this.unrecognizedAction(event, event.getLocation(), from + " > " + to);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            this.data.cancelIfDisabled(event, this.triggers.PLAYERS_FLINT_USAGE);
        } else {
            this.data.cancelIfDisabled(event, this.triggers.FIRE_SPREADING);
        }
    }

    private final boolean supportProjectileHitEvent = this.data.hasVersion(1, 11, 0);

    @EventHandler(ignoreCancelled = true)
    private void on(ProjectileHitEvent event) {
        if (!this.supportProjectileHitEvent) return;
        if (event.getHitBlock() == null) return; // Since 1.11
        Entity entity = event.getEntity();
        if (!this.data.getRemovableProjectileTypes().contains(entity.getType())) return;
        if (!this.data.isActionAllowed(entity.getWorld(), this.triggers.BLOCK_HIT_PROJECTILES_REMOVING)) return;
        entity.remove();
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockBurnEvent event) {
        this.data.cancelIfDisabled(event, this.triggers.FIRE_SPREADING);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(LeavesDecayEvent event) {
        this.data.cancelIfDisabled(event, this.triggers.LEAVES_DECAY);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void onBoneMeal(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block targetBlock = event.getClickedBlock();
        if (targetBlock == null) {
            throw new IllegalArgumentException("Block absent on PlayerInteractEvent with Action.RIGHT_CLICK_BLOCK");
        }

        ItemStack usedItem = event.getItem();
        if (usedItem == null) return;
        if (!this.versionsAdapter.isBoneMealItem(usedItem)) return;

        if (!this.data.isActionAllowed(targetBlock.getWorld(), this.triggers.PLAYERS_BONE_MEAL_USAGE)) {
            event.setUseItemInHand(Event.Result.DENY);
        }
        if (event.useItemInHand() == Event.Result.DENY) return;
        this.fertilizedBlocks.add(targetBlock.getLocation().toVector());
    }

    private final MaterialRules rulesBlockPhysicsEventFrom = new MaterialRules(
        this.data, BlockPhysicsEvent.class, "from");

    private final boolean legacyBlockPhysicsEvent = !this.data.hasVersion(1, 13, 0);
    private final boolean modernBlockPhysicsEvent = ReflectionUtils.isMethodPresent(BlockPhysicsEvent.class, "getSourceBlock");

    @SuppressWarnings("ConcatenationWithEmptyString")
    @EventHandler(ignoreCancelled = true)
    private void modernListener(BlockPhysicsEvent event) {
        Block block = event.getBlock();

        if (this.legacyBlockPhysicsEvent) {
            if (PhysicsListener.DEBUG_BLOCK_PHYSICS_EVENT) {
                this.debugAction(event, block.getLocation(), ""
                    + "block=" + block.getType() + ";"
                    + "changed=" + event.getChangedType() + ";"
                );
            }
            PControlTrigger trigger = this.rulesBlockPhysicsEventFrom.findTrigger(block.getType());
            if (trigger != null) {
                this.data.cancelIfDisabled(event, trigger);
            }
            return;
        }

        if (!this.modernBlockPhysicsEvent) {
            return;
        }

        Block source = event.getSourceBlock(); // Not supported on Spigot 1.13 and 1.13.1

        if (PhysicsListener.DEBUG_BLOCK_PHYSICS_EVENT) {
            this.debugAction(event, block.getLocation(), ""
                + "block=" + block.getType() + ";"
                + "source=" + source.getType() + ";"
                + "changed=" + event.getChangedType() + ";"
            );
        }

        PControlTrigger trigger;
        Block sourceRelative;

        BlockFace faceUp = BlockFace.UP;
        sourceRelative = source.getRelative(faceUp);
        if (PhysicsListener.DEBUG_BLOCK_PHYSICS_EVENT) {
            this.debugAction(event, sourceRelative.getLocation(), ""
                + "face=" + faceUp + ";"
                + "sourceRelative=" + sourceRelative.getType() + ";"
            );
        }
        trigger = this.rulesBlockPhysicsEventFrom.findTrigger(sourceRelative.getType());
        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
            return;
        }

        for (BlockFace faceNSWE : PhysicsListener.NSWE_FACES) {
            sourceRelative = source.getRelative(faceNSWE);
            if (PhysicsListener.DEBUG_BLOCK_PHYSICS_EVENT) {
                this.debugAction(event, sourceRelative.getLocation(), ""
                    + "face=" + faceNSWE + ";"
                    + "sourceRelative=" + sourceRelative.getType() + ";"
                );
            }
            if (!this.versionsAdapter.isFacingAt(sourceRelative, faceNSWE)) continue;
            trigger = this.rulesBlockPhysicsEventFrom.findTrigger(sourceRelative.getType());
            if (trigger != null) {
                this.data.cancelIfDisabled(event, trigger);
                return;
            }
        }
    }
}
