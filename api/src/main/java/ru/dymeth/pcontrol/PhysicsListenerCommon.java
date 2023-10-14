package ru.dymeth.pcontrol;

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
import ru.dymeth.pcontrol.api.PControlData;
import ru.dymeth.pcontrol.api.PControlTrigger;
import ru.dymeth.pcontrol.api.PhysicsListener;
import ru.dymeth.pcontrol.api.set.BlocksSet;
import ru.dymeth.pcontrol.api.set.TreeTypesSet;
import ru.dymeth.pcontrol.rules.pair.EntityMaterialRules;
import ru.dymeth.pcontrol.rules.pair.MaterialMaterialRules;
import ru.dymeth.pcontrol.rules.single.EntityRules;
import ru.dymeth.pcontrol.rules.single.MaterialRules;
import ru.dymeth.pcontrol.rules.single.TreeRules;
import ru.dymeth.pcontrol.util.FileUtils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;

@SuppressWarnings({"ClassInitializerMayBeStatic", "ConcatenationWithEmptyString", "IsCancelled"})
public final class PhysicsListenerCommon extends PhysicsListener {

    private static final boolean BLOCK_PHYSICS_EVENT_GET_SOURCE_BLOCK_SUPPORT
        = FileUtils.isMethodPresent(BlockPhysicsEvent.class, "getSourceBlock");

    public PhysicsListenerCommon(@Nonnull PControlData data) {
        super(data);
    }

    private final MaterialMaterialRules rulesBlockGrowEventFromTo = new MaterialMaterialRules(this.data);
    private final MaterialRules rulesBlockGrowEventTo = new MaterialRules(this.data);

    {
        if (this.data.hasVersion(0)) {
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.SUGAR_CANE_GROWING,
                this.tags.SUGAR_CANE_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.CACTUS_GROWING,
                BlocksSet.create(PControlTrigger.CACTUS_GROWING + " trigger", this.data, set ->
                    set.add(Material.CACTUS)));
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.WHEAT_GROWING,
                this.tags.WHEAT_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.POTATOES_GROWING,
                this.tags.POTATO_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.CARROTS_GROWING,
                this.tags.CARROT_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.PUMPKINS_GROWING,
                this.tags.PUMPKIN_STEM_AND_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.MELONS_GROWING,
                this.tags.MELON_STEM_AND_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.COCOAS_GROWING,
                BlocksSet.create(PControlTrigger.COCOAS_GROWING + " trigger", this.data, set ->
                    set.add(Material.COCOA)));
            this.rulesBlockGrowEventFromTo.regPair(PControlTrigger.VINES_GROWING,
                BlocksSet.create(PControlTrigger.VINES_GROWING + " trigger", this.data, set ->
                    set.add(Material.VINE)),
                BlocksSet.create(PControlTrigger.VINES_GROWING + " trigger", this.data, set ->
                    set.add(Material.VINE)));
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.NETHER_WARTS_GROWING,
                this.tags.NETHER_WART_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.BONE_MEAL_USAGE,
                this.tags.BONE_MEAL_HERBS);
        }
        if (this.data.hasVersion(9)) {
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.BEETROOTS_GROWING,
                this.tags.BEETROOT_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.CHORUSES_GROWING,
                BlocksSet.create(PControlTrigger.CHORUSES_GROWING + " trigger", this.data, set ->
                    set.add(Material.CHORUS_FLOWER)));
        }
        if (this.data.hasVersion(13)) {
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.TURTLES_LAYING_EGGS,
                BlocksSet.create(PControlTrigger.TURTLES_LAYING_EGGS + " trigger", this.data, set ->
                    set.add(Material.TURTLE_EGG)));
        }
        if (this.data.hasVersion(14)) {
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.SWEET_BERRIES_GROWING,
                BlocksSet.create(PControlTrigger.SWEET_BERRIES_GROWING + " trigger", this.data, set ->
                    set.add(Material.SWEET_BERRY_BUSH)));
        }

        if (this.data.hasVersion(17)) {
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.AMETHYST_CLUSTERS_GROWING, // from = AIR, CAVE_AIR, WATER, etc
                BlocksSet.create(PControlTrigger.AMETHYST_CLUSTERS_GROWING + " trigger", this.data, set ->
                    set.add(Material.SMALL_AMETHYST_BUD)));
            this.rulesBlockGrowEventFromTo.regPair(PControlTrigger.AMETHYST_CLUSTERS_GROWING,
                BlocksSet.create(PControlTrigger.AMETHYST_CLUSTERS_GROWING + " trigger", this.data, set ->
                    set.add(Material.SMALL_AMETHYST_BUD)),
                BlocksSet.create(PControlTrigger.AMETHYST_CLUSTERS_GROWING + " trigger", this.data, set ->
                    set.add(Material.MEDIUM_AMETHYST_BUD)));
            this.rulesBlockGrowEventFromTo.regPair(PControlTrigger.AMETHYST_CLUSTERS_GROWING,
                BlocksSet.create(PControlTrigger.AMETHYST_CLUSTERS_GROWING + " trigger", this.data, set ->
                    set.add(Material.MEDIUM_AMETHYST_BUD)),
                BlocksSet.create(PControlTrigger.AMETHYST_CLUSTERS_GROWING + " trigger", this.data, set ->
                    set.add(Material.LARGE_AMETHYST_BUD)));
            this.rulesBlockGrowEventFromTo.regPair(PControlTrigger.AMETHYST_CLUSTERS_GROWING,
                BlocksSet.create(PControlTrigger.AMETHYST_CLUSTERS_GROWING + " trigger", this.data, set ->
                    set.add(Material.LARGE_AMETHYST_BUD)),
                BlocksSet.create(PControlTrigger.AMETHYST_CLUSTERS_GROWING + " trigger", this.data, set ->
                    set.add(Material.AMETHYST_CLUSTER)));
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockGrowEvent event) {
        if (this.fertilizedBlocks.remove(event.getBlock().getLocation().toVector())) {
            this.data.cancelIfDisabled(event, PControlTrigger.BONE_MEAL_USAGE);
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
            this.rulesEntityChangeBlockEventFromTo.regPair(PControlTrigger.FARMLANDS_TRAMPLING,
                this.tags.FARMLAND_BLOCK,
                BlocksSet.create(PControlTrigger.FARMLANDS_TRAMPLING + " trigger", this.data, set ->
                    set.add(Material.DIRT)));
            this.rulesEntityChangeBlockEventTo.regSingle(PControlTrigger.IGNORED_STATE, // Redstone ore activation
                this.tags.REDSTONE_ORE_BLOCKS);
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.SAND_FALLING,
                this.tags.SAND);
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.GRAVEL_FALLING,
                this.tags.GRAVEL);
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.ANVILS_FALLING,
                this.tags.ANVIL);
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.DRAGON_EGGS_FALLING,
                BlocksSet.create(PControlTrigger.DRAGON_EGGS_FALLING + " trigger", this.data, set ->
                    set.add(Material.DRAGON_EGG)));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.SAND_FALLING,
                this.tags.SAND);
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.GRAVEL_FALLING,
                this.tags.GRAVEL);
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.ANVILS_FALLING,
                this.tags.ANVIL);
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.DRAGON_EGGS_FALLING,
                BlocksSet.create(PControlTrigger.DRAGON_EGGS_FALLING + " trigger", this.data, set ->
                    set.add(Material.DRAGON_EGG)));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.IGNORED_STATE, // On custom falling blocks fall (created by third-party plugins like WoodCutter)
                this.tags.WORLD_AIR);
            this.rulesNonFallingEntityChangeBlockEventByFrom.regPair(PControlTrigger.BURNING_ARROWS_ACTIVATE_TNT,
                Collections.singleton(EntityType.ARROW),
                BlocksSet.create(PControlTrigger.BURNING_ARROWS_ACTIVATE_TNT + " trigger", this.data, set ->
                    set.add(Material.TNT)));
            this.rulesNonFallingEntityChangeBlockEventByFrom.regPair(PControlTrigger.ZOMBIES_BREAK_DOORS,
                Collections.singleton(EntityType.ZOMBIE),
                this.tags.WOODEN_DOORS);
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
                this.tags.WOODEN_DOORS);
        }
        if (this.data.hasVersion(12)) {
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.CONCRETE_POWDERS_FALLING,
                this.tags.CONCRETE_POWDERS);
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.CONCRETE_POWDERS_FALLING,
                this.tags.CONCRETE_POWDERS);
        }
        if (this.data.hasVersion(13)) {
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.TURTLES_LAYING_EGGS,
                Collections.singleton(EntityType.TURTLE));
        }
        if (this.data.hasVersion(14)) {
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.SCAFFOLDING_FALLING,
                BlocksSet.create(PControlTrigger.SCAFFOLDING_FALLING + " trigger", this.data, set ->
                    set.add(Material.SCAFFOLDING)));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.SCAFFOLDING_FALLING,
                BlocksSet.create(PControlTrigger.SCAFFOLDING_FALLING + " trigger", this.data, set ->
                    set.add(Material.SCAFFOLDING)));
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.RAVAGERS_DESTROY_BLOCKS,
                Collections.singleton(EntityType.RAVAGER));
            this.rulesNonFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.FOXES_EATS_FROM_SWEET_BERRY_BUSHES,
                Collections.singleton(EntityType.FOX));
        }
        if (this.data.hasVersion(17)) {
            this.rulesEntityChangeBlockEventFromTo.regPair(PControlTrigger.DRIPLEAFS_LOWERING,
                BlocksSet.create(PControlTrigger.DRIPLEAFS_LOWERING + " trigger", this.data, set ->
                    set.add(Material.BIG_DRIPLEAF)),
                BlocksSet.create(PControlTrigger.DRIPLEAFS_LOWERING + " trigger", this.data, set ->
                    set.add(Material.BIG_DRIPLEAF)));
            this.rulesEntityChangeBlockEventFromTo.regPair(PControlTrigger.POWDER_SNOW_MELTS_FROM_BURNING_ENTITIES,
                BlocksSet.create(PControlTrigger.POWDER_SNOW_MELTS_FROM_BURNING_ENTITIES + " trigger", this.data, set ->
                    set.add(Material.POWDER_SNOW)),
                this.tags.WORLD_AIR);
            this.rulesEntityChangeBlockEventFromTo.regPair(PControlTrigger.GLOW_BERRIES_PICKING,
                BlocksSet.create(PControlTrigger.GLOW_BERRIES_PICKING + " trigger", this.data, set ->
                    set.add(Material.CAVE_VINES)),
                BlocksSet.create(PControlTrigger.GLOW_BERRIES_PICKING + " trigger", this.data, set ->
                    set.add(Material.CAVE_VINES)));
            this.rulesEntityChangeBlockEventFromTo.regPair(PControlTrigger.GLOW_BERRIES_PICKING,
                BlocksSet.create(PControlTrigger.GLOW_BERRIES_PICKING + " trigger", this.data, set ->
                    set.add(Material.CAVE_VINES_PLANT)),
                BlocksSet.create(PControlTrigger.GLOW_BERRIES_PICKING + " trigger", this.data, set ->
                    set.add(Material.CAVE_VINES_PLANT)));
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.POINTED_DRIPSTONES_FALLING,
                BlocksSet.create(PControlTrigger.POINTED_DRIPSTONES_FALLING + " trigger", this.data, set ->
                    set.add(Material.POINTED_DRIPSTONE)));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.POINTED_DRIPSTONES_FALLING,
                BlocksSet.create(PControlTrigger.POINTED_DRIPSTONES_FALLING + " trigger", this.data, set ->
                    set.add(Material.POINTED_DRIPSTONE)));
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void on(EntityChangeBlockEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getTo();
        World world = event.getEntity().getWorld();

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
            this.data.cancelIfDisabled(event, world, trigger);
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
            this.rulesBlockFromToEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Strange thing from FluidTypeFlowing
                this.tags.WORLD_AIR,
                this.tags.WORLD_AIR);
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.LAVA_FLOWING,
                this.tags.LAVA);
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.WATER_FLOWING,
                this.tags.BLOCKS_UNDER_WATER_ONLY);
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.IGNORED_STATE, // Seems bug while chunks generation (water near gravity blocks?): "Action BlockFromTo (GRAVEL > GRAVEL) was detected"
                this.tags.NATURAL_GRAVITY_BLOCKS);
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.DRAGON_EGGS_TELEPORTING,
                BlocksSet.create(PControlTrigger.DRAGON_EGGS_TELEPORTING + " trigger", this.data, set ->
                    set.add(Material.DRAGON_EGG)));
        }
        if (this.data.hasVersion(13)) {
            this.rulesBlockFromToEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Seems bug while chunks generation (kelp near caves?): "Action BlockFromTo (KELP > AIR) was detected"
                BlocksSet.create(PControlTrigger.IGNORED_STATE + " trigger", this.data, set ->
                    set.add(Material.KELP)),
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
            trigger = PControlTrigger.WATER_FLOWING;

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
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.GRASS_BLOCKS_FADING,
                BlocksSet.create(PControlTrigger.GRASS_BLOCKS_FADING + " trigger", this.data, set -> {
                    set.add(this.tags.GRASS_BLOCK);
                    set.add(this.tags.DIRT_PATH_BLOCK);
                }),
                BlocksSet.create(PControlTrigger.GRASS_BLOCKS_FADING + " trigger", this.data, set ->
                    set.add(Material.DIRT)));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.MYCELIUM_SPREADING,
                this.tags.MYCELIUM_BLOCK,
                BlocksSet.create(PControlTrigger.MYCELIUM_SPREADING + " trigger", this.data, set ->
                    set.add(Material.DIRT)));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.FARMLANDS_DRYING,
                this.tags.FARMLAND_BLOCK,
                BlocksSet.create(PControlTrigger.FARMLANDS_DRYING + " trigger", this.data, set ->
                    set.add(Material.DIRT)));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.SNOW_MELTING,
                BlocksSet.create(PControlTrigger.SNOW_MELTING + " trigger", this.data, set ->
                    set.add(Material.SNOW)),
                this.tags.WORLD_AIR);
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.ICE_MELTING,
                BlocksSet.create(PControlTrigger.ICE_MELTING + " trigger", this.data, set ->
                    set.add(Material.ICE)),
                BlocksSet.create(PControlTrigger.ICE_MELTING + " trigger", this.data, set -> {
                    set.add(this.tags.WATER);
                    set.add(this.tags.WORLD_AIR);
                }));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.FIRE_SPREADING,
                BlocksSet.create(PControlTrigger.FIRE_SPREADING + " trigger", this.data, set ->
                    set.add(Material.FIRE)),
                this.tags.WORLD_AIR);
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Redstone ore deactivation
                this.tags.REDSTONE_ORE_BLOCKS,
                this.tags.REDSTONE_ORE_BLOCKS);
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Strange server action. Perhaps this is due to the fall of blocks without a base (torches for example) during generation (only in mineshafts?)
                this.tags.WORLD_AIR,
                this.tags.WORLD_AIR);
        }
        if (this.data.hasVersion(9)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.FROSTED_ICE_PHYSICS,
                BlocksSet.create(PControlTrigger.FROSTED_ICE_PHYSICS + " trigger", this.data, set ->
                    set.add(Material.FROSTED_ICE)),
                this.tags.WATER);
        }
        if (this.data.hasVersion(13)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.CORALS_DRYING,
                this.tags.ALL_ALIVE_CORALS,
                this.tags.ALL_DEAD_CORALS);
        }
        if (this.data.hasVersion(14)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.SCAFFOLDING_FALLING,
                BlocksSet.create(PControlTrigger.SCAFFOLDING_FALLING + " trigger", this.data, set ->
                    set.add(Material.SCAFFOLDING)),
                this.tags.WORLD_AIR);
        }
        if (this.data.hasVersion(16)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.CRIMSON_NYLIUM_FADING,
                BlocksSet.create(PControlTrigger.CRIMSON_NYLIUM_FADING + " trigger", this.data, set ->
                    set.add(Material.CRIMSON_NYLIUM)),
                BlocksSet.create(PControlTrigger.CRIMSON_NYLIUM_FADING + " trigger", this.data, set ->
                    set.add(Material.NETHERRACK)));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.WARPED_NYLIUM_FADING,
                BlocksSet.create(PControlTrigger.WARPED_NYLIUM_FADING + " trigger", this.data, set ->
                    set.add(Material.WARPED_NYLIUM)),
                BlocksSet.create(PControlTrigger.WARPED_NYLIUM_FADING + " trigger", this.data, set ->
                    set.add(Material.NETHERRACK)));
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
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.GRASS_SPREADING,
                BlocksSet.create(PControlTrigger.GRASS_SPREADING + " trigger", this.data, set ->
                    set.add(Material.DIRT)),
                this.tags.GRASS_BLOCK);
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.MYCELIUM_SPREADING,
                BlocksSet.create(PControlTrigger.MYCELIUM_SPREADING + " trigger", this.data, set ->
                    set.add(Material.DIRT)),
                this.tags.MYCELIUM_BLOCK);
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.VINES_GROWING,
                BlocksSet.create(PControlTrigger.VINES_GROWING + " trigger", this.data, set -> {
                    set.add(Material.VINE);
                    set.add(this.tags.WORLD_AIR);
                }),
                BlocksSet.create(PControlTrigger.VINES_GROWING + " trigger", this.data, set ->
                    set.add(Material.VINE)));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.LITTLE_MUSHROOMS_SPREADING,
                this.tags.WORLD_AIR,
                this.tags.LITTLE_MUSHROOMS);
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.FIRE_SPREADING,
                this.tags.WORLD_AIR,
                BlocksSet.create(PControlTrigger.FIRE_SPREADING + " trigger", this.data, set ->
                    set.add(Material.FIRE)));
        }
        if (this.data.hasVersion(9)) {
            this.rulesBlockSpreadEventTo.regSingle(PControlTrigger.CHORUSES_GROWING,
                BlocksSet.create(PControlTrigger.CHORUSES_GROWING + " trigger", this.data, set ->
                    set.add(Material.CHORUS_FLOWER)));
        }
        if (this.data.hasVersion(13)) {
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.KELPS_GROWING,
                this.tags.WATER,
                BlocksSet.create(PControlTrigger.KELPS_GROWING + " trigger", this.data, set ->
                    set.add(Material.KELP)));
        }
        if (this.data.hasVersion(14)) {
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.BAMBOO_GROWING,
                this.tags.WORLD_AIR,
                BlocksSet.create(PControlTrigger.BAMBOO_GROWING + " trigger", this.data, set ->
                    set.add(Material.BAMBOO)));
        }
        if (this.data.hasVersion(16)) {
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.WEEPING_VINES_GROWING,
                this.tags.WORLD_AIR,
                BlocksSet.create(PControlTrigger.WEEPING_VINES_GROWING + " trigger", this.data, set ->
                    set.add(Material.WEEPING_VINES)));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.TWISTING_VINES_GROWING,
                this.tags.WORLD_AIR,
                BlocksSet.create(PControlTrigger.TWISTING_VINES_GROWING + " trigger", this.data, set ->
                    set.add(Material.TWISTING_VINES)));
        }
        if (this.data.hasVersion(17)) {
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.GLOW_BERRIES_GROWING,
                BlocksSet.create(PControlTrigger.GLOW_BERRIES_GROWING + " trigger", this.data, set -> {
                    set.add(Material.CAVE_VINES);
                    set.add(this.tags.WORLD_AIR);
                }),
                BlocksSet.create(PControlTrigger.GLOW_BERRIES_GROWING + " trigger", this.data, set ->
                    set.add(Material.CAVE_VINES)));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.POINTED_DRIPSTONES_GROWING,
                BlocksSet.create(PControlTrigger.POINTED_DRIPSTONES_GROWING + " trigger", this.data, set -> {
                    set.add(Material.POINTED_DRIPSTONE);
                    set.add(this.tags.WORLD_AIR);
                }),
                BlocksSet.create(PControlTrigger.POINTED_DRIPSTONES_GROWING + " trigger", this.data, set ->
                    set.add(Material.POINTED_DRIPSTONE)));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // BONE_MEAL_USAGE
                this.tags.WORLD_AIR,
                BlocksSet.create(PControlTrigger.IGNORED_STATE + " trigger", this.data, set ->
                    set.add(Material.HANGING_ROOTS)));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // BONE_MEAL_USAGE
                BlocksSet.create(PControlTrigger.IGNORED_STATE + " trigger", this.data, set -> {
                    set.add(Material.GLOW_LICHEN);
                    set.add(this.tags.WORLD_AIR);
                }),
                BlocksSet.create(PControlTrigger.IGNORED_STATE + " trigger", this.data, set ->
                    set.add(Material.GLOW_LICHEN)));
            this.rulesBlockSpreadEventTo.regSingle(PControlTrigger.AMETHYST_CLUSTERS_GROWING, // from = AIR, CAVE_AIR, WATER, etc
                BlocksSet.create(PControlTrigger.AMETHYST_CLUSTERS_GROWING + " trigger", this.data, set ->
                    set.add(Material.SMALL_AMETHYST_BUD)));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.AMETHYST_CLUSTERS_GROWING,
                BlocksSet.create(PControlTrigger.AMETHYST_CLUSTERS_GROWING + " trigger", this.data, set ->
                    set.add(Material.SMALL_AMETHYST_BUD)),
                BlocksSet.create(PControlTrigger.AMETHYST_CLUSTERS_GROWING + " trigger", this.data, set ->
                    set.add(Material.MEDIUM_AMETHYST_BUD)));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.AMETHYST_CLUSTERS_GROWING,
                BlocksSet.create(PControlTrigger.AMETHYST_CLUSTERS_GROWING + " trigger", this.data, set ->
                    set.add(Material.MEDIUM_AMETHYST_BUD)),
                BlocksSet.create(PControlTrigger.AMETHYST_CLUSTERS_GROWING + " trigger", this.data, set ->
                    set.add(Material.LARGE_AMETHYST_BUD)));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.AMETHYST_CLUSTERS_GROWING,
                BlocksSet.create(PControlTrigger.AMETHYST_CLUSTERS_GROWING + " trigger", this.data, set ->
                    set.add(Material.LARGE_AMETHYST_BUD)),
                BlocksSet.create(PControlTrigger.AMETHYST_CLUSTERS_GROWING + " trigger", this.data, set ->
                    set.add(Material.AMETHYST_CLUSTER)));
        }
        if (this.data.hasVersion(19)) {
            this.rulesBlockSpreadEventTo.regSingle(PControlTrigger.SCULKS_SPREADING,
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
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.FARMLANDS_TRAMPLING,
                this.tags.FARMLAND_BLOCK);
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.IGNORED_STATE, // Redstone activators
                this.tags.REDSTONE_PASSIVE_INPUTS);
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.IGNORED_STATE, // Redstone ore activation
                this.tags.REDSTONE_ORE_BLOCKS);
        }
        if (this.data.hasVersion(13)) {
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.TURTLE_EGGS_TRAMPLING,
                BlocksSet.create(PControlTrigger.TURTLE_EGGS_TRAMPLING + " trigger", this.data, set ->
                    set.add(Material.TURTLE_EGG)));
        }
        if (this.data.hasVersion(17)) {
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.IGNORED_STATE, // Control by DRIPLEAFS_LOWERING
                BlocksSet.create(PControlTrigger.IGNORED_STATE + " trigger", this.data, set ->
                    set.add(Material.BIG_DRIPLEAF)));
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
            this.rulesEntityBlockFormEventFromTo.regPair(PControlTrigger.SNOW_GOLEMS_CREATE_SNOW,
                this.tags.WORLD_AIR,
                BlocksSet.create(PControlTrigger.SNOW_GOLEMS_CREATE_SNOW + " trigger", this.data, set ->
                    set.add(Material.SNOW)));
        }
        if (this.data.hasVersion(9)) {
            this.rulesEntityBlockFormEventFromTo.regPair(PControlTrigger.FROSTED_ICE_PHYSICS,
                this.tags.WATER,
                BlocksSet.create(PControlTrigger.FROSTED_ICE_PHYSICS + " trigger", this.data, set ->
                    set.add(Material.FROSTED_ICE)));
        }
        if (this.data.hasVersion(14)) {
            this.rulesEntityBlockFormEventFromTo.regPair(PControlTrigger.WITHER_CREATE_WITHER_ROSE_BLOCKS,
                this.tags.WORLD_AIR,
                BlocksSet.create(PControlTrigger.WITHER_CREATE_WITHER_ROSE_BLOCKS + " trigger", this.data, set ->
                    set.add(Material.WITHER_ROSE)));
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
        PControlTrigger.BONE_MEAL_USAGE.markAvailable();
        this.rulesStructureGrowEventTo.regSingle(PControlTrigger.TREES_GROWING,
            TreeTypesSet.create(PControlTrigger.TREES_GROWING + " trigger", this.data, set -> {
                set.add(TreeType.TREE);
                set.add(TreeType.BIG_TREE);
                set.add(TreeType.REDWOOD);
                set.add(TreeType.TALL_REDWOOD);
                set.add(TreeType.BIRCH);
                set.add(TreeType.JUNGLE);
                set.add(TreeType.SMALL_JUNGLE);
                set.add(TreeType.COCOA_TREE);
                set.add(TreeType.JUNGLE_BUSH);
                set.add(TreeType.SWAMP);
                set.add(TreeType.ACACIA);
                set.add(TreeType.DARK_OAK);
                set.add(TreeType.MEGA_REDWOOD);
                set.add(TreeType.TALL_BIRCH);
            }));
        this.rulesStructureGrowEventTo.regSingle(PControlTrigger.GIANT_MUSHROOMS_GROWING,
            TreeTypesSet.create(PControlTrigger.GIANT_MUSHROOMS_GROWING + " trigger", this.data, set -> {
                set.add(TreeType.RED_MUSHROOM);
                set.add(TreeType.BROWN_MUSHROOM);
            }));
        if (this.data.hasVersion(9)) {
            this.rulesStructureGrowEventTo.regSingle(PControlTrigger.CHORUSES_GROWING,
                TreeTypesSet.create(PControlTrigger.CHORUSES_GROWING + " trigger", this.data,
                    set -> set.add(TreeType.CHORUS_PLANT)));
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void on(StructureGrowEvent event) {
        World world = event.getWorld();
        if (event.getPlayer() != null) {
            this.data.cancelIfDisabled(event, world, PControlTrigger.BONE_MEAL_USAGE);
            return;
        }
        Material from = event.getLocation().getBlock().getType();
        TreeType to = event.getSpecies();

        PControlTrigger trigger = this.rulesStructureGrowEventTo.findTrigger(to);

        if (trigger != null) {
            this.data.cancelIfDisabled(event, world, trigger);
        } else {
            this.unrecognizedAction(event, event.getLocation(), from + " > " + to);
        }
    }


    {
        PControlTrigger.PLAYERS_FLINT_USAGE.markAvailable();
        PControlTrigger.FIRE_SPREADING.markAvailable();
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            this.data.cancelIfDisabled(event, PControlTrigger.PLAYERS_FLINT_USAGE);
        } else {
            this.data.cancelIfDisabled(event, PControlTrigger.FIRE_SPREADING);
        }
    }

    {
        if (this.data.hasVersion(11)) { // ProjectileHitEvent.getHitBlock() since 1.11
            PControlTrigger.BLOCK_HIT_PROJECTILES_REMOVING.markAvailable();
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void on(ProjectileHitEvent event) {
        if (!this.data.hasVersion(11)) return;
        if (event.getHitBlock() == null) return; // Since 1.11
        Entity entity = event.getEntity();
        if (!this.data.getRemovableProjectileTypes().contains(entity.getType())) return;
        if (!this.data.isActionAllowed(entity.getWorld(), PControlTrigger.BLOCK_HIT_PROJECTILES_REMOVING)) return;
        entity.remove();
    }

    {
        PControlTrigger.FIRE_SPREADING.markAvailable();
    }

    @EventHandler(ignoreCancelled = true)
    private void on(BlockBurnEvent event) {
        this.data.cancelIfDisabled(event, PControlTrigger.FIRE_SPREADING);
    }

    {
        PControlTrigger.LEAVES_DECAY.markAvailable();
    }

    @EventHandler(ignoreCancelled = true)
    private void on(LeavesDecayEvent event) {
        this.data.cancelIfDisabled(event, PControlTrigger.LEAVES_DECAY);
    }

    {
        PControlTrigger.BONE_MEAL_USAGE.markAvailable();
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

        this.data.cancelIfDisabled(event, targetBlock.getWorld(), PControlTrigger.BONE_MEAL_USAGE);
        if (event.useItemInHand() == Event.Result.DENY) return;
        this.fertilizedBlocks.add(targetBlock.getLocation().toVector());
    }

    private final MaterialRules rulesBlockPhysicsEventFrom = new MaterialRules(this.data);

    {
        if (BLOCK_PHYSICS_EVENT_GET_SOURCE_BLOCK_SUPPORT || !this.data.hasVersion(13)) {
            this.rulesBlockPhysicsEventFrom.regSingle(PControlTrigger.LADDERS_DESTROYING,
                BlocksSet.create(PControlTrigger.LADDERS_DESTROYING + " trigger", this.data, set ->
                    set.add(this.tags.LADDERS)));
            this.rulesBlockPhysicsEventFrom.regSingle(PControlTrigger.SIGNS_DESTROYING,
                BlocksSet.create(PControlTrigger.SIGNS_DESTROYING + " trigger", this.data, set ->
                    set.add(this.tags.SIGNS)));
            this.rulesBlockPhysicsEventFrom.regSingle(PControlTrigger.RAILS_DESTROYING,
                BlocksSet.create(PControlTrigger.RAILS_DESTROYING + " trigger", this.data, set ->
                    set.add(this.tags.RAILS)));
            this.rulesBlockPhysicsEventFrom.regSingle(PControlTrigger.TORCHES_DESTROYING,
                BlocksSet.create(PControlTrigger.TORCHES_DESTROYING + " trigger", this.data, set ->
                    set.add(this.tags.TORCHES)));
            this.rulesBlockPhysicsEventFrom.regSingle(PControlTrigger.REDSTONE_TORCHES_DESTROYING,
                BlocksSet.create(PControlTrigger.REDSTONE_TORCHES_DESTROYING + " trigger", this.data, set ->
                    set.add(this.tags.REDSTONE_TORCHES)));
        }
        if (this.data.hasVersion(16)) {
            this.rulesBlockPhysicsEventFrom.regSingle(PControlTrigger.SOUL_TORCHES_DESTROYING,
                BlocksSet.create(PControlTrigger.SOUL_TORCHES_DESTROYING + " trigger", this.data, set ->
                    set.add(this.tags.SOUL_TORCHES)));
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

        if (!BLOCK_PHYSICS_EVENT_GET_SOURCE_BLOCK_SUPPORT) { // Not supported on Spigot 1.13 and 1.13.1
            return;
        }

        Block source = event.getSourceBlock();

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
