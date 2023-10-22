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
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.rules.pair.EntityMaterialRules;
import ru.dymeth.pcontrol.rules.pair.MaterialMaterialRules;
import ru.dymeth.pcontrol.rules.single.EntityRules;
import ru.dymeth.pcontrol.rules.single.MaterialRules;
import ru.dymeth.pcontrol.rules.single.TreeRules;
import ru.dymeth.pcontrol.util.ReflectionUtils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;

@SuppressWarnings({"ConcatenationWithEmptyString", "IsCancelled"})
public final class PhysicsListenerCommon extends PhysicsListener {

    private static final boolean BLOCK_PHYSICS_EVENT_GET_SOURCE_BLOCK_SUPPORT
        = ReflectionUtils.isMethodPresent(BlockPhysicsEvent.class, "getSourceBlock");

    public PhysicsListenerCommon(@Nonnull PControlData data) {
        super(data);
    }

    private final MaterialMaterialRules rulesBlockGrowEventFromTo = new MaterialMaterialRules(this.data);
    private final MaterialRules rulesBlockGrowEventTo = new MaterialRules(this.data);

    {
        if (this.data.hasVersion(0)) {
            this.rulesBlockGrowEventTo.regSingle(this.triggers.SUGAR_CANE_GROWING,
                this.tags.SUGAR_CANE_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(this.triggers.CACTUS_GROWING,
                blocksSet(this.triggers.CACTUS_GROWING, set ->
                    set.addPrimitive(Material.CACTUS)));
            this.rulesBlockGrowEventTo.regSingle(this.triggers.WHEAT_GROWING,
                this.tags.WHEAT_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(this.triggers.POTATOES_GROWING,
                this.tags.POTATO_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(this.triggers.CARROTS_GROWING,
                this.tags.CARROT_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(this.triggers.PUMPKINS_GROWING,
                this.tags.PUMPKIN_STEM_AND_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(this.triggers.MELONS_GROWING,
                this.tags.MELON_STEM_AND_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(this.triggers.COCOAS_GROWING,
                blocksSet(this.triggers.COCOAS_GROWING, set ->
                    set.addPrimitive(Material.COCOA)));
            this.rulesBlockGrowEventFromTo.regPair(this.triggers.VINES_GROWING,
                blocksSet(this.triggers.VINES_GROWING, set ->
                    set.addPrimitive(Material.VINE)),
                blocksSet(this.triggers.VINES_GROWING, set ->
                    set.addPrimitive(Material.VINE)));
            this.rulesBlockGrowEventTo.regSingle(this.triggers.NETHER_WARTS_GROWING,
                this.tags.NETHER_WART_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(this.triggers.BONE_MEAL_USAGE,
                this.tags.BONE_MEAL_HERBS);
        }
        if (this.data.hasVersion(9)) {
            this.rulesBlockGrowEventTo.regSingle(this.triggers.BEETROOTS_GROWING,
                this.tags.BEETROOT_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(this.triggers.CHORUSES_GROWING,
                blocksSet(this.triggers.CHORUSES_GROWING, set ->
                    set.addPrimitive(Material.CHORUS_FLOWER)));
        }
        if (this.data.hasVersion(13)) {
            this.rulesBlockGrowEventTo.regSingle(this.triggers.TURTLES_LAYING_EGGS,
                blocksSet(this.triggers.TURTLES_LAYING_EGGS, set ->
                    set.addPrimitive(Material.TURTLE_EGG)));
        }
        if (this.data.hasVersion(14)) {
            this.rulesBlockGrowEventTo.regSingle(this.triggers.SWEET_BERRIES_GROWING,
                blocksSet(this.triggers.SWEET_BERRIES_GROWING, set ->
                    set.addPrimitive(Material.SWEET_BERRY_BUSH)));
        }

        if (this.data.hasVersion(17)) {
            this.rulesBlockGrowEventTo.regSingle(this.triggers.AMETHYST_CLUSTERS_GROWING, // from = AIR, CAVE_AIR, WATER, etc
                blocksSet(this.triggers.AMETHYST_CLUSTERS_GROWING, set ->
                    set.addPrimitive(Material.SMALL_AMETHYST_BUD)));
            this.rulesBlockGrowEventFromTo.regPair(this.triggers.AMETHYST_CLUSTERS_GROWING,
                blocksSet(this.triggers.AMETHYST_CLUSTERS_GROWING, set ->
                    set.addPrimitive(Material.SMALL_AMETHYST_BUD)),
                blocksSet(this.triggers.AMETHYST_CLUSTERS_GROWING, set ->
                    set.addPrimitive(Material.MEDIUM_AMETHYST_BUD)));
            this.rulesBlockGrowEventFromTo.regPair(this.triggers.AMETHYST_CLUSTERS_GROWING,
                blocksSet(this.triggers.AMETHYST_CLUSTERS_GROWING, set ->
                    set.addPrimitive(Material.MEDIUM_AMETHYST_BUD)),
                blocksSet(this.triggers.AMETHYST_CLUSTERS_GROWING, set ->
                    set.addPrimitive(Material.LARGE_AMETHYST_BUD)));
            this.rulesBlockGrowEventFromTo.regPair(this.triggers.AMETHYST_CLUSTERS_GROWING,
                blocksSet(this.triggers.AMETHYST_CLUSTERS_GROWING, set ->
                    set.addPrimitive(Material.LARGE_AMETHYST_BUD)),
                blocksSet(this.triggers.AMETHYST_CLUSTERS_GROWING, set ->
                    set.addPrimitive(Material.AMETHYST_CLUSTER)));
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockGrowEvent event) {
        if (this.fertilizedBlocks.remove(event.getBlock().getLocation().toVector())) {
            this.data.cancelIfDisabled(event, this.triggers.BONE_MEAL_USAGE);
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

    private final MaterialMaterialRules rulesEntityChangeBlockEventFromTo = new MaterialMaterialRules(this.data);
    private final MaterialRules rulesEntityChangeBlockEventTo = new MaterialRules(this.data);

    private final MaterialRules rulesFallingEntityChangeBlockEventBy = new MaterialRules(this.data);
    private final MaterialRules rulesFallingEntityChangeBlockEventFrom = new MaterialRules(this.data);

    private final EntityMaterialRules rulesNonFallingEntityChangeBlockEventByFrom = new EntityMaterialRules(this.data);
    private final EntityRules rulesNonFallingEntityChangeBlockEventBy = new EntityRules(this.data);

    {
        if (this.data.hasVersion(0)) {
            this.rulesEntityChangeBlockEventFromTo.regPair(this.triggers.FARMLANDS_TRAMPLING,
                this.tags.FARMLAND_BLOCK,
                blocksSet(this.triggers.FARMLANDS_TRAMPLING, set ->
                    set.addPrimitive(Material.DIRT)));
            this.rulesEntityChangeBlockEventTo.regSingle(this.triggers.IGNORED_STATE, // Redstone ore activation
                this.tags.REDSTONE_ORE_BLOCKS);
            this.rulesFallingEntityChangeBlockEventBy.regSingle(this.triggers.SAND_FALLING,
                this.tags.SAND);
            this.rulesFallingEntityChangeBlockEventBy.regSingle(this.triggers.GRAVEL_FALLING,
                this.tags.GRAVEL);
            this.rulesFallingEntityChangeBlockEventBy.regSingle(this.triggers.ANVILS_FALLING,
                this.tags.ANVIL);
            this.rulesFallingEntityChangeBlockEventBy.regSingle(this.triggers.DRAGON_EGGS_FALLING,
                blocksSet(this.triggers.DRAGON_EGGS_FALLING, set ->
                    set.addPrimitive(Material.DRAGON_EGG)));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(this.triggers.SAND_FALLING,
                this.tags.SAND);
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(this.triggers.GRAVEL_FALLING,
                this.tags.GRAVEL);
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(this.triggers.ANVILS_FALLING,
                this.tags.ANVIL);
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(this.triggers.DRAGON_EGGS_FALLING,
                blocksSet(this.triggers.DRAGON_EGGS_FALLING, set ->
                    set.addPrimitive(Material.DRAGON_EGG)));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(this.triggers.IGNORED_STATE, // On custom falling blocks fall (created by third-party plugins like WoodCutter)
                this.tags.WORLD_AIR);
            this.rulesNonFallingEntityChangeBlockEventByFrom.regPair(this.triggers.BURNING_ARROWS_ACTIVATE_TNT,
                Collections.singleton(EntityType.ARROW),
                blocksSet(this.triggers.BURNING_ARROWS_ACTIVATE_TNT, set ->
                    set.addPrimitive(Material.TNT)));
            this.rulesNonFallingEntityChangeBlockEventByFrom.regPair(this.triggers.ZOMBIES_BREAK_DOORS,
                Collections.singleton(EntityType.ZOMBIE),
                this.tags.WOODEN_DOORS);
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(this.triggers.IGNORED_STATE, // Boats destroys lilies. TODO It is necessary to implement a smart system of destruction and restoration of water lilies so that there are no problems with movement
                Collections.singleton(EntityType.BOAT));
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(this.triggers.SHEEPS_EATING_GRASS,
                Collections.singleton(EntityType.SHEEP));
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(this.triggers.ENDERMANS_GRIEFING,
                Collections.singleton(EntityType.ENDERMAN));
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(this.triggers.WITHERS_GRIEFING,
                Collections.singleton(EntityType.WITHER));
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(this.triggers.SILVERFISHES_HIDING_IN_BLOCKS,
                Collections.singleton(EntityType.SILVERFISH));
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(this.triggers.VILLAGERS_FARMING,
                Collections.singleton(EntityType.VILLAGER));
        }
        if (this.data.hasVersion(8)) {
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(this.triggers.RABBITS_EATING_CARROTS,
                Collections.singleton(EntityType.RABBIT));
        }
        if (this.data.hasVersion(11)) {
            this.rulesNonFallingEntityChangeBlockEventByFrom.regPair(this.triggers.ZOMBIES_BREAK_DOORS,
                Collections.singleton(EntityType.ZOMBIE_VILLAGER),
                this.tags.WOODEN_DOORS);
        }
        if (this.data.hasVersion(12)) {
            this.rulesFallingEntityChangeBlockEventBy.regSingle(this.triggers.CONCRETE_POWDERS_FALLING,
                this.tags.CONCRETE_POWDERS);
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(this.triggers.CONCRETE_POWDERS_FALLING,
                this.tags.CONCRETE_POWDERS);
        }
        if (this.data.hasVersion(13)) {
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(this.triggers.TURTLES_LAYING_EGGS,
                Collections.singleton(EntityType.TURTLE));
        }
        if (this.data.hasVersion(14)) {
            this.rulesFallingEntityChangeBlockEventBy.regSingle(this.triggers.SCAFFOLDING_FALLING,
                blocksSet(this.triggers.SCAFFOLDING_FALLING, set ->
                    set.addPrimitive(Material.SCAFFOLDING)));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(this.triggers.SCAFFOLDING_FALLING,
                blocksSet(this.triggers.SCAFFOLDING_FALLING, set ->
                    set.addPrimitive(Material.SCAFFOLDING)));
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(this.triggers.RAVAGERS_DESTROY_BLOCKS,
                Collections.singleton(EntityType.RAVAGER));
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(this.triggers.FOXES_EATS_FROM_SWEET_BERRY_BUSHES,
                Collections.singleton(EntityType.FOX));
        }
        if (this.data.hasVersion(17)) {
            this.rulesEntityChangeBlockEventFromTo.regPair(this.triggers.DRIPLEAFS_LOWERING,
                blocksSet(this.triggers.DRIPLEAFS_LOWERING, set ->
                    set.addPrimitive(Material.BIG_DRIPLEAF)),
                blocksSet(this.triggers.DRIPLEAFS_LOWERING, set ->
                    set.addPrimitive(Material.BIG_DRIPLEAF)));
            this.rulesEntityChangeBlockEventFromTo.regPair(this.triggers.POWDER_SNOW_MELTS_FROM_BURNING_ENTITIES,
                blocksSet(this.triggers.POWDER_SNOW_MELTS_FROM_BURNING_ENTITIES, set ->
                    set.addPrimitive(Material.POWDER_SNOW)),
                this.tags.WORLD_AIR);
            this.rulesEntityChangeBlockEventFromTo.regPair(this.triggers.GLOW_BERRIES_PICKING,
                blocksSet(this.triggers.GLOW_BERRIES_PICKING, set ->
                    set.addPrimitive(Material.CAVE_VINES)),
                blocksSet(this.triggers.GLOW_BERRIES_PICKING, set ->
                    set.addPrimitive(Material.CAVE_VINES)));
            this.rulesEntityChangeBlockEventFromTo.regPair(this.triggers.GLOW_BERRIES_PICKING,
                blocksSet(this.triggers.GLOW_BERRIES_PICKING, set ->
                    set.addPrimitive(Material.CAVE_VINES_PLANT)),
                blocksSet(this.triggers.GLOW_BERRIES_PICKING, set ->
                    set.addPrimitive(Material.CAVE_VINES_PLANT)));
            this.rulesFallingEntityChangeBlockEventBy.regSingle(this.triggers.POINTED_DRIPSTONES_FALLING,
                blocksSet(this.triggers.POINTED_DRIPSTONES_FALLING, set ->
                    set.addPrimitive(Material.POINTED_DRIPSTONE)));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(this.triggers.POINTED_DRIPSTONES_FALLING,
                blocksSet(this.triggers.POINTED_DRIPSTONES_FALLING, set ->
                    set.addPrimitive(Material.POINTED_DRIPSTONE)));
        }
        if (this.data.hasVersion(19)) {
            this.rulesEntityChangeBlockEventFromTo.regPair(this.triggers.FROGSPAWN_LAYING_AND_SPAWNING,
                blocksSet(this.triggers.FROGSPAWN_LAYING_AND_SPAWNING, set ->
                    set.addPrimitive(this.tags.WORLD_AIR)),
                blocksSet(this.triggers.FROGSPAWN_LAYING_AND_SPAWNING, set ->
                    set.addPrimitive(Material.FROGSPAWN)));
        }
    }

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
                updateBlockOnCancel = true;
            } else {
                EntityType by = event.getEntity().getType();

                trigger = this.rulesNonFallingEntityChangeBlockEventByFrom.findTrigger(by, from);
                if (trigger == null) trigger = this.rulesNonFallingEntityChangeBlockEventBy.findTrigger(by);

                if (trigger == null) {
                    this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to + " (by " + by + ")");
                }
            }
        }

        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
        }

        // Prevent client bug with disappearing blocks on start falling (fixed on paper 1.16.5, spigot 1.19.4 and client 1.18.2)
        if (event.isCancelled() && updateBlockOnCancel && !this.data.hasVersion(19)) {
            event.getBlock().getState().update(false, false);
        }
    }

    private final MaterialMaterialRules rulesBlockFromToEventFromTo = new MaterialMaterialRules(this.data);
    private final MaterialRules rulesBlockFromToEventFrom = new MaterialRules(this.data);

    {
        if (this.data.hasVersion(0)) {
            this.rulesBlockFromToEventFromTo.regPair(this.triggers.IGNORED_STATE, // Strange thing from FluidTypeFlowing
                this.tags.WORLD_AIR,
                this.tags.WORLD_AIR);
            this.rulesBlockFromToEventFrom.regSingle(this.triggers.LAVA_FLOWING,
                this.tags.LAVA);
            this.rulesBlockFromToEventFrom.regSingle(this.triggers.WATER_FLOWING,
                this.tags.BLOCKS_UNDER_WATER_ONLY);
            this.rulesBlockFromToEventFrom.regSingle(this.triggers.IGNORED_STATE, // Seems bug while chunks generation (water near gravity blocks?): "Action BlockFromTo (GRAVEL > GRAVEL) was detected"
                this.tags.NATURAL_GRAVITY_BLOCKS);
            this.rulesBlockFromToEventFrom.regSingle(this.triggers.DRAGON_EGGS_TELEPORTING,
                blocksSet(this.triggers.DRAGON_EGGS_TELEPORTING, set ->
                    set.addPrimitive(Material.DRAGON_EGG)));
        }
        if (this.data.hasVersion(13)) {
            this.rulesBlockFromToEventFromTo.regPair(this.triggers.IGNORED_STATE, // Seems bug while chunks generation (kelp near caves?): "Action BlockFromTo (KELP > AIR) was detected"
                blocksSet(this.triggers.IGNORED_STATE, set ->
                    set.addPrimitive(Material.KELP)),
                this.tags.WORLD_AIR);
        }
    }

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

    private final MaterialMaterialRules rulesBlockFadeEventFromTo = new MaterialMaterialRules(this.data);
    private final MaterialRules rulesBlockFadeEventTo = new MaterialRules(this.data);

    {
        if (this.data.hasVersion(0)) {
            this.rulesBlockFadeEventFromTo.regPair(this.triggers.GRASS_BLOCKS_FADING,
                blocksSet(this.triggers.GRASS_BLOCKS_FADING, set -> {
                    set.addPrimitive(this.tags.GRASS_BLOCK);
                    set.addPrimitive(this.tags.DIRT_PATH_BLOCK);
                }),
                blocksSet(this.triggers.GRASS_BLOCKS_FADING, set ->
                    set.addPrimitive(Material.DIRT)));
            this.rulesBlockFadeEventFromTo.regPair(this.triggers.MYCELIUM_SPREADING,
                this.tags.MYCELIUM_BLOCK,
                blocksSet(this.triggers.MYCELIUM_SPREADING, set ->
                    set.addPrimitive(Material.DIRT)));
            this.rulesBlockFadeEventFromTo.regPair(this.triggers.FARMLANDS_DRYING,
                this.tags.FARMLAND_BLOCK,
                blocksSet(this.triggers.FARMLANDS_DRYING, set ->
                    set.addPrimitive(Material.DIRT)));
            this.rulesBlockFadeEventFromTo.regPair(this.triggers.SNOW_MELTING,
                blocksSet(this.triggers.SNOW_MELTING, set ->
                    set.addPrimitive(Material.SNOW)),
                this.tags.WORLD_AIR);
            this.rulesBlockFadeEventFromTo.regPair(this.triggers.ICE_MELTING,
                blocksSet(this.triggers.ICE_MELTING, set ->
                    set.addPrimitive(Material.ICE)),
                blocksSet(this.triggers.ICE_MELTING, set -> {
                    set.addPrimitive(this.tags.WATER);
                    set.addPrimitive(this.tags.WORLD_AIR);
                }));
            this.rulesBlockFadeEventFromTo.regPair(this.triggers.FIRE_SPREADING,
                blocksSet(this.triggers.FIRE_SPREADING, set ->
                    set.addPrimitive(Material.FIRE)),
                this.tags.WORLD_AIR);
            this.rulesBlockFadeEventFromTo.regPair(this.triggers.IGNORED_STATE, // Redstone ore deactivation
                this.tags.REDSTONE_ORE_BLOCKS,
                this.tags.REDSTONE_ORE_BLOCKS);
            this.rulesBlockFadeEventFromTo.regPair(this.triggers.IGNORED_STATE, // Strange server action. Perhaps this is due to the fall of blocks without a base (torches for example) during generation (only in mineshafts?)
                this.tags.WORLD_AIR,
                this.tags.WORLD_AIR);
        }
        if (this.data.hasVersion(9)) {
            this.rulesBlockFadeEventFromTo.regPair(this.triggers.FROSTED_ICE_PHYSICS,
                blocksSet(this.triggers.FROSTED_ICE_PHYSICS, set ->
                    set.addPrimitive(Material.FROSTED_ICE)),
                this.tags.WATER);
        }
        if (this.data.hasVersion(13)) {
            this.rulesBlockFadeEventFromTo.regPair(this.triggers.CORALS_DRYING,
                this.tags.ALL_ALIVE_CORALS,
                this.tags.ALL_DEAD_CORALS);
        }
        if (this.data.hasVersion(14)) {
            this.rulesBlockFadeEventFromTo.regPair(this.triggers.SCAFFOLDING_FALLING,
                blocksSet(this.triggers.SCAFFOLDING_FALLING, set ->
                    set.addPrimitive(Material.SCAFFOLDING)),
                this.tags.WORLD_AIR);
        }
        if (this.data.hasVersion(16)) {
            this.rulesBlockFadeEventFromTo.regPair(this.triggers.CRIMSON_NYLIUM_FADING,
                blocksSet(this.triggers.CRIMSON_NYLIUM_FADING, set ->
                    set.addPrimitive(Material.CRIMSON_NYLIUM)),
                blocksSet(this.triggers.CRIMSON_NYLIUM_FADING, set ->
                    set.addPrimitive(Material.NETHERRACK)));
            this.rulesBlockFadeEventFromTo.regPair(this.triggers.WARPED_NYLIUM_FADING,
                blocksSet(this.triggers.WARPED_NYLIUM_FADING, set ->
                    set.addPrimitive(Material.WARPED_NYLIUM)),
                blocksSet(this.triggers.WARPED_NYLIUM_FADING, set ->
                    set.addPrimitive(Material.NETHERRACK)));
        }
        if (this.data.hasVersion(19)) {
            this.rulesBlockFadeEventFromTo.regPair(this.triggers.FROGSPAWN_LAYING_AND_SPAWNING,
                blocksSet(this.triggers.FROGSPAWN_LAYING_AND_SPAWNING, set ->
                    set.addPrimitive(Material.FROGSPAWN)),
                blocksSet(this.triggers.FROGSPAWN_LAYING_AND_SPAWNING, set ->
                    set.addPrimitive(this.tags.WORLD_AIR)));
        }
    }

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

    private final MaterialMaterialRules rulesBlockSpreadEventFromTo = new MaterialMaterialRules(this.data);
    private final MaterialRules rulesBlockSpreadEventTo = new MaterialRules(this.data);

    {
        if (this.data.hasVersion(0)) {
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.GRASS_SPREADING,
                blocksSet(this.triggers.GRASS_SPREADING, set ->
                    set.addPrimitive(Material.DIRT)),
                this.tags.GRASS_BLOCK);
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.MYCELIUM_SPREADING,
                blocksSet(this.triggers.MYCELIUM_SPREADING, set ->
                    set.addPrimitive(Material.DIRT)),
                this.tags.MYCELIUM_BLOCK);
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.VINES_GROWING,
                blocksSet(this.triggers.VINES_GROWING, set -> {
                    set.addPrimitive(Material.VINE);
                    set.addPrimitive(this.tags.WORLD_AIR);
                }),
                blocksSet(this.triggers.VINES_GROWING, set ->
                    set.addPrimitive(Material.VINE)));
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.LITTLE_MUSHROOMS_SPREADING,
                this.tags.WORLD_AIR,
                this.tags.LITTLE_MUSHROOMS);
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.FIRE_SPREADING,
                this.tags.WORLD_AIR,
                blocksSet(this.triggers.FIRE_SPREADING, set ->
                    set.addPrimitive(Material.FIRE)));
        }
        if (this.data.hasVersion(9)) {
            this.rulesBlockSpreadEventTo.regSingle(this.triggers.CHORUSES_GROWING,
                blocksSet(this.triggers.CHORUSES_GROWING, set ->
                    set.addPrimitive(Material.CHORUS_FLOWER)));
        }
        if (this.data.hasVersion(13)) {
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.KELPS_GROWING,
                this.tags.WATER,
                blocksSet(this.triggers.KELPS_GROWING, set ->
                    set.addPrimitive(Material.KELP)));
        }
        if (this.data.hasVersion(14)) {
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.BAMBOO_GROWING,
                this.tags.WORLD_AIR,
                blocksSet(this.triggers.BAMBOO_GROWING, set ->
                    set.addPrimitive(Material.BAMBOO)));
        }
        if (this.data.hasVersion(16)) {
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.WEEPING_VINES_GROWING,
                this.tags.WORLD_AIR,
                blocksSet(this.triggers.WEEPING_VINES_GROWING, set ->
                    set.addPrimitive(Material.WEEPING_VINES)));
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.TWISTING_VINES_GROWING,
                this.tags.WORLD_AIR,
                blocksSet(this.triggers.TWISTING_VINES_GROWING, set ->
                    set.addPrimitive(Material.TWISTING_VINES)));
        }
        if (this.data.hasVersion(17)) {
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.GLOW_BERRIES_GROWING,
                blocksSet(this.triggers.GLOW_BERRIES_GROWING, set -> {
                    set.addPrimitive(Material.CAVE_VINES);
                    set.addPrimitive(this.tags.WORLD_AIR);
                }),
                blocksSet(this.triggers.GLOW_BERRIES_GROWING, set ->
                    set.addPrimitive(Material.CAVE_VINES)));
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.POINTED_DRIPSTONES_GROWING,
                blocksSet(this.triggers.POINTED_DRIPSTONES_GROWING, set -> {
                    set.addPrimitive(Material.POINTED_DRIPSTONE);
                    set.addPrimitive(this.tags.WORLD_AIR);
                }),
                blocksSet(this.triggers.POINTED_DRIPSTONES_GROWING, set ->
                    set.addPrimitive(Material.POINTED_DRIPSTONE)));
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.IGNORED_STATE, // BONE_MEAL_USAGE
                this.tags.WORLD_AIR,
                blocksSet(this.triggers.IGNORED_STATE, set ->
                    set.addPrimitive(Material.HANGING_ROOTS)));
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.IGNORED_STATE, // BONE_MEAL_USAGE
                blocksSet(this.triggers.IGNORED_STATE, set -> {
                    set.addPrimitive(Material.GLOW_LICHEN);
                    set.addPrimitive(this.tags.WORLD_AIR);
                }),
                blocksSet(this.triggers.IGNORED_STATE, set ->
                    set.addPrimitive(Material.GLOW_LICHEN)));
            this.rulesBlockSpreadEventTo.regSingle(this.triggers.AMETHYST_CLUSTERS_GROWING, // from = AIR, CAVE_AIR, WATER, etc
                blocksSet(this.triggers.AMETHYST_CLUSTERS_GROWING, set ->
                    set.addPrimitive(Material.SMALL_AMETHYST_BUD)));
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.AMETHYST_CLUSTERS_GROWING,
                blocksSet(this.triggers.AMETHYST_CLUSTERS_GROWING, set ->
                    set.addPrimitive(Material.SMALL_AMETHYST_BUD)),
                blocksSet(this.triggers.AMETHYST_CLUSTERS_GROWING, set ->
                    set.addPrimitive(Material.MEDIUM_AMETHYST_BUD)));
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.AMETHYST_CLUSTERS_GROWING,
                blocksSet(this.triggers.AMETHYST_CLUSTERS_GROWING, set ->
                    set.addPrimitive(Material.MEDIUM_AMETHYST_BUD)),
                blocksSet(this.triggers.AMETHYST_CLUSTERS_GROWING, set ->
                    set.addPrimitive(Material.LARGE_AMETHYST_BUD)));
            this.rulesBlockSpreadEventFromTo.regPair(this.triggers.AMETHYST_CLUSTERS_GROWING,
                blocksSet(this.triggers.AMETHYST_CLUSTERS_GROWING, set ->
                    set.addPrimitive(Material.LARGE_AMETHYST_BUD)),
                blocksSet(this.triggers.AMETHYST_CLUSTERS_GROWING, set ->
                    set.addPrimitive(Material.AMETHYST_CLUSTER)));
        }
        if (this.data.hasVersion(19)) {
            this.rulesBlockSpreadEventTo.regSingle(this.triggers.SCULKS_SPREADING,
                Arrays.asList(Material.SCULK, Material.SCULK_VEIN));
        }
    }

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

    private final MaterialRules rulesEntityInteractEventMaterial = new MaterialRules(this.data);

    {
        if (this.data.hasVersion(0)) {
            this.rulesEntityInteractEventMaterial.regSingle(this.triggers.FARMLANDS_TRAMPLING,
                this.tags.FARMLAND_BLOCK);
            this.rulesEntityInteractEventMaterial.regSingle(this.triggers.IGNORED_STATE, // Redstone activators
                this.tags.REDSTONE_PASSIVE_INPUTS);
            this.rulesEntityInteractEventMaterial.regSingle(this.triggers.IGNORED_STATE, // Redstone ore activation
                this.tags.REDSTONE_ORE_BLOCKS);
        }
        if (this.data.hasVersion(13)) {
            this.rulesEntityInteractEventMaterial.regSingle(this.triggers.TURTLE_EGGS_TRAMPLING,
                blocksSet(this.triggers.TURTLE_EGGS_TRAMPLING, set ->
                    set.addPrimitive(Material.TURTLE_EGG)));
        }
        if (this.data.hasVersion(17)) {
            this.rulesEntityInteractEventMaterial.regSingle(this.triggers.IGNORED_STATE, // Control by DRIPLEAFS_LOWERING
                blocksSet(this.triggers.IGNORED_STATE, set ->
                    set.addPrimitive(Material.BIG_DRIPLEAF)));
            this.rulesEntityInteractEventMaterial.regSingle(this.triggers.IGNORED_STATE, // Any entities stay on block
                blocksSet(this.triggers.IGNORED_STATE, set ->
                    set.addPrimitive(Material.SCULK_SENSOR)));
        }
        if (this.data.hasVersion(19)) {
            this.rulesEntityInteractEventMaterial.regSingle(this.triggers.IGNORED_STATE, // Players stay on block
                blocksSet(this.triggers.IGNORED_STATE, set ->
                    set.addPrimitive(Material.SCULK_SHRIEKER)));
        }
        if (this.data.hasVersion(20)) {
            this.rulesEntityInteractEventMaterial.regSingle(this.triggers.IGNORED_STATE, // Any entities stay on block
                blocksSet(this.triggers.IGNORED_STATE, set ->
                    set.addPrimitive(Material.CALIBRATED_SCULK_SENSOR)));
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void on(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) return;
        if (event.getBlockFace() != BlockFace.SELF) return;
        if (event.getClickedBlock() == null) return;
        this.handleInteraction(event, event.getClickedBlock(), event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    private void on(EntityInteractEvent event) {
        if (event.getEntityType() == EntityType.VILLAGER && this.tags.WOODEN_DOORS.contains(event.getBlock().getType())) {
            return;
        }
        this.handleInteraction(event, event.getBlock(), event.getEntity());
    }

    private void handleInteraction(@Nonnull Cancellable event, @Nonnull Block source, @Nonnull Entity entity) {
        World world = source.getWorld();
        Material material = source.getType();

        PControlTrigger trigger = this.rulesEntityInteractEventMaterial.findTrigger(material);

        if (trigger != null) {
            this.data.cancelIfDisabled(event, world, trigger);
        } else {
            this.unrecognizedAction(event, source.getLocation(), material + " (by " + entity + ")");
        }
    }

    private final MaterialMaterialRules rulesEntityBlockFormEventFromTo = new MaterialMaterialRules(this.data);
    private final MaterialRules rulesEntityBlockFormEventTo = new MaterialRules(this.data);

    {
        if (this.data.hasVersion(0)) {
            this.rulesEntityBlockFormEventFromTo.regPair(this.triggers.SNOW_GOLEMS_CREATE_SNOW,
                this.tags.WORLD_AIR,
                blocksSet(this.triggers.SNOW_GOLEMS_CREATE_SNOW, set ->
                    set.addPrimitive(Material.SNOW)));
        }
        if (this.data.hasVersion(9)) {
            this.rulesEntityBlockFormEventFromTo.regPair(this.triggers.FROSTED_ICE_PHYSICS,
                this.tags.WATER,
                blocksSet(this.triggers.FROSTED_ICE_PHYSICS, set ->
                    set.addPrimitive(Material.FROSTED_ICE)));
        }
        if (this.data.hasVersion(14)) {
            this.rulesEntityBlockFormEventFromTo.regPair(this.triggers.WITHER_CREATE_WITHER_ROSE_BLOCKS,
                this.tags.WORLD_AIR,
                blocksSet(this.triggers.WITHER_CREATE_WITHER_ROSE_BLOCKS, set ->
                    set.addPrimitive(Material.WITHER_ROSE)));
        }
    }

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

    private final TreeRules rulesStructureGrowEventTo = new TreeRules(this.data);

    {
        this.triggers.BONE_MEAL_USAGE.markAvailable();
        this.rulesStructureGrowEventTo.regSingle(this.triggers.TREES_GROWING,
            treesSet(this.triggers.TREES_GROWING, set -> {
                set.addPrimitive(TreeType.TREE);
                set.addPrimitive(TreeType.TREE);
                set.addPrimitive(TreeType.BIG_TREE);
                set.addPrimitive(TreeType.REDWOOD);
                set.addPrimitive(TreeType.TALL_REDWOOD);
                set.addPrimitive(TreeType.BIRCH);
                set.addPrimitive(TreeType.JUNGLE);
                set.addPrimitive(TreeType.SMALL_JUNGLE);
                set.addPrimitive(TreeType.COCOA_TREE);
                set.addPrimitive(TreeType.JUNGLE_BUSH);
                set.addPrimitive(TreeType.SWAMP);
                set.addPrimitive(TreeType.ACACIA);
                set.addPrimitive(TreeType.DARK_OAK);
                set.addPrimitive(TreeType.MEGA_REDWOOD);
                set.addPrimitive(TreeType.TALL_BIRCH);
            }));
        this.rulesStructureGrowEventTo.regSingle(this.triggers.GIANT_MUSHROOMS_GROWING,
            treesSet(this.triggers.GIANT_MUSHROOMS_GROWING, set -> {
                set.addPrimitive(TreeType.RED_MUSHROOM);
                set.addPrimitive(TreeType.BROWN_MUSHROOM);
            }));
        if (this.data.hasVersion(9)) {
            this.rulesStructureGrowEventTo.regSingle(this.triggers.CHORUSES_GROWING,
                treesSet(this.triggers.CHORUSES_GROWING,
                    set -> set.addPrimitive(TreeType.CHORUS_PLANT)));
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void on(StructureGrowEvent event) {
        if (event.getPlayer() != null) {
            this.data.cancelIfDisabled(event, this.triggers.BONE_MEAL_USAGE);
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


    {
        this.triggers.PLAYERS_FLINT_USAGE.markAvailable();
        this.triggers.FIRE_SPREADING.markAvailable();
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            this.data.cancelIfDisabled(event, this.triggers.PLAYERS_FLINT_USAGE);
        } else {
            this.data.cancelIfDisabled(event, this.triggers.FIRE_SPREADING);
        }
    }

    {
        if (this.data.hasVersion(11)) { // ProjectileHitEvent.getHitBlock() since 1.11
            this.triggers.BLOCK_HIT_PROJECTILES_REMOVING.markAvailable();
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void on(ProjectileHitEvent event) {
        if (!this.data.hasVersion(11)) return;
        if (event.getHitBlock() == null) return; // Since 1.11
        Entity entity = event.getEntity();
        if (!this.data.getRemovableProjectileTypes().contains(entity.getType())) return;
        if (!this.data.isActionAllowed(entity.getWorld(), this.triggers.BLOCK_HIT_PROJECTILES_REMOVING)) return;
        entity.remove();
    }

    {
        this.triggers.FIRE_SPREADING.markAvailable();
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockBurnEvent event) {
        this.data.cancelIfDisabled(event, this.triggers.FIRE_SPREADING);
    }

    {
        this.triggers.LEAVES_DECAY.markAvailable();
    }

    @EventHandler(ignoreCancelled = true)
    private void on(LeavesDecayEvent event) {
        this.data.cancelIfDisabled(event, this.triggers.LEAVES_DECAY);
    }

    {
        this.triggers.BONE_MEAL_USAGE.markAvailable();
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
        if (!this.versionsAdapter.isBoneMealItem(usedItem)) return;

        this.data.cancelIfDisabled(event, targetBlock.getWorld(), this.triggers.BONE_MEAL_USAGE);
        if (event.useItemInHand() == Event.Result.DENY) return;
        this.fertilizedBlocks.add(targetBlock.getLocation().toVector());
    }

    private final MaterialRules rulesBlockPhysicsEventFrom = new MaterialRules(this.data);

    {
        if (BLOCK_PHYSICS_EVENT_GET_SOURCE_BLOCK_SUPPORT || !this.data.hasVersion(13)) {
            this.rulesBlockPhysicsEventFrom.regSingle(this.triggers.SAPLINGS_DESTROYING,
                blocksSet(this.triggers.SAPLINGS_DESTROYING, set ->
                    set.addPrimitive(this.tags.SAPLINGS)));
            this.rulesBlockPhysicsEventFrom.regSingle(this.triggers.LADDERS_DESTROYING,
                blocksSet(this.triggers.LADDERS_DESTROYING, set ->
                    set.addPrimitive(this.tags.LADDERS)));
            this.rulesBlockPhysicsEventFrom.regSingle(this.triggers.SIGNS_DESTROYING,
                blocksSet(this.triggers.SIGNS_DESTROYING, set ->
                    set.addPrimitive(this.tags.SIGNS)));
            this.rulesBlockPhysicsEventFrom.regSingle(this.triggers.RAILS_DESTROYING,
                blocksSet(this.triggers.RAILS_DESTROYING, set ->
                    set.addPrimitive(this.tags.RAILS)));
            this.rulesBlockPhysicsEventFrom.regSingle(this.triggers.TORCHES_DESTROYING,
                blocksSet(this.triggers.TORCHES_DESTROYING, set ->
                    set.addPrimitive(this.tags.TORCHES)));
            this.rulesBlockPhysicsEventFrom.regSingle(this.triggers.REDSTONE_TORCHES_DESTROYING,
                blocksSet(this.triggers.REDSTONE_TORCHES_DESTROYING, set ->
                    set.addPrimitive(this.tags.REDSTONE_TORCHES)));
        }
        if (this.data.hasVersion(16)) {
            this.rulesBlockPhysicsEventFrom.regSingle(this.triggers.SOUL_TORCHES_DESTROYING,
                blocksSet(this.triggers.SOUL_TORCHES_DESTROYING, set ->
                    set.addPrimitive(this.tags.SOUL_TORCHES)));
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void modernListener(BlockPhysicsEvent event) {
        Block block = event.getBlock();

        if (!this.data.hasVersion(13)) {
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

        if (!BLOCK_PHYSICS_EVENT_GET_SOURCE_BLOCK_SUPPORT) {
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
