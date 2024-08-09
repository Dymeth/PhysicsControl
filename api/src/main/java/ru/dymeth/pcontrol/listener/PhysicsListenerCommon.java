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
        if (this.data.hasVersion(1, 0, 0)) {
            this.rulesBlockGrowEventTo.reg(this.triggers.SUGAR_CANE_GROWING,
                this.tags.sugar_cane_block);
            this.rulesBlockGrowEventTo.reg(this.triggers.CACTUS_GROWING,
                set ->
                    set.addPrimitive(Material.CACTUS));
            this.rulesBlockGrowEventTo.reg(this.triggers.WHEAT_GROWING,
                this.tags.wheat_block);
            this.rulesBlockGrowEventTo.reg(this.triggers.POTATOES_GROWING,
                this.tags.potato_block);
            this.rulesBlockGrowEventTo.reg(this.triggers.CARROTS_GROWING,
                this.tags.carrot_block);
            this.rulesBlockGrowEventTo.reg(this.triggers.PUMPKINS_GROWING,
                this.tags.pumpkin_stem_and_block);
            this.rulesBlockGrowEventTo.reg(this.triggers.MELONS_GROWING,
                this.tags.melon_stem_and_block);
            this.rulesBlockGrowEventTo.reg(this.triggers.COCOAS_GROWING,
                set ->
                    set.addPrimitive(Material.COCOA));
            this.rulesBlockGrowEventFromTo.reg(this.triggers.VINES_GROWING,
                from ->
                    from.addPrimitive(Material.VINE),
                to ->
                    to.addPrimitive(Material.VINE));
            this.rulesBlockGrowEventTo.reg(this.triggers.NETHER_WARTS_GROWING,
                this.tags.nether_wart_block);
            this.rulesBlockGrowEventTo.reg(this.triggers.PLAYERS_BONE_MEAL_USAGE,
                this.tags.bone_meal_herbs);
        }
        if (this.data.hasVersion(1, 9, 0)) {
            this.rulesBlockGrowEventTo.reg(this.triggers.BEETROOTS_GROWING,
                this.tags.beetroot_block);
            this.rulesBlockGrowEventTo.reg(this.triggers.CHORUSES_GROWING,
                set ->
                    set.addPrimitive(Material.CHORUS_FLOWER));
        }
        if (this.data.hasVersion(1, 13, 0)) {
            this.rulesBlockGrowEventTo.reg(this.triggers.TURTLES_LAYING_EGGS,
                set ->
                    set.addPrimitive(Material.TURTLE_EGG));
        }
        if (this.data.hasVersion(1, 14, 0)) {
            this.rulesBlockGrowEventTo.reg(this.triggers.SWEET_BERRIES_GROWING,
                set ->
                    set.addPrimitive(Material.SWEET_BERRY_BUSH));
        }

        if (this.data.hasVersion(1, 17, 0)) {
            this.rulesBlockGrowEventTo.reg(this.triggers.AMETHYST_CLUSTERS_GROWING, // from = AIR, CAVE_AIR, WATER, etc
                set ->
                    set.addPrimitive(Material.SMALL_AMETHYST_BUD));
            this.rulesBlockGrowEventFromTo.reg(this.triggers.AMETHYST_CLUSTERS_GROWING,
                from ->
                    from.addPrimitive(Material.SMALL_AMETHYST_BUD),
                to ->
                    to.addPrimitive(Material.MEDIUM_AMETHYST_BUD));
            this.rulesBlockGrowEventFromTo.reg(this.triggers.AMETHYST_CLUSTERS_GROWING,
                from ->
                    from.addPrimitive(Material.MEDIUM_AMETHYST_BUD),
                to ->
                    to.addPrimitive(Material.LARGE_AMETHYST_BUD));
            this.rulesBlockGrowEventFromTo.reg(this.triggers.AMETHYST_CLUSTERS_GROWING,
                from ->
                    from.addPrimitive(Material.LARGE_AMETHYST_BUD),
                to ->
                    to.addPrimitive(Material.AMETHYST_CLUSTER));
        }
    }

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

    private final MaterialMaterialRules rulesEntityChangeBlockEventFromTo = new MaterialMaterialRules(this.data);
    private final MaterialRules rulesEntityChangeBlockEventTo = new MaterialRules(this.data);

    private final MaterialRules rulesFallingEntityChangeBlockEventBy = new MaterialRules(this.data);
    private final MaterialRules rulesFallingEntityChangeBlockEventFrom = new MaterialRules(this.data);

    private final EntityMaterialRules rulesNonFallingEntityChangeBlockEventByFrom = new EntityMaterialRules(this.data);
    private final EntityRules rulesNonFallingEntityChangeBlockEventBy = new EntityRules(this.data);

    {
        if (this.data.hasVersion(1, 0, 0)) {
            this.rulesEntityChangeBlockEventFromTo.reg(this.triggers.FARMLANDS_TRAMPLING,
                this.tags.farmland_block,
                to ->
                    to.addPrimitive(Material.DIRT));
            this.rulesEntityChangeBlockEventFromTo.reg(this.triggers.END_PORTAL_FRAMES_FILLING,
                from -> {
                    if (!this.data.hasVersion(1, 13, 0)) {
                        from.add("ENDER_PORTAL_FRAME");
                    } else {
                        from.addPrimitive(Material.END_PORTAL_FRAME);
                    }
                },
                to -> {
                    if (!this.data.hasVersion(1, 13, 0)) {
                        to.add("ENDER_PORTAL_FRAME");
                    } else {
                        to.addPrimitive(Material.END_PORTAL_FRAME);
                    }
                });
            this.rulesEntityChangeBlockEventTo.reg(this.triggers.IGNORED_STATE, // Redstone ore activation
                this.tags.redstone_ore_blocks);
            this.rulesFallingEntityChangeBlockEventBy.reg(this.triggers.SAND_FALLING,
                this.tags.sand);
            this.rulesFallingEntityChangeBlockEventBy.reg(this.triggers.GRAVEL_FALLING,
                this.tags.gravel);
            this.rulesFallingEntityChangeBlockEventBy.reg(this.triggers.ANVILS_FALLING,
                this.tags.anvil);
            this.rulesFallingEntityChangeBlockEventBy.reg(this.triggers.DRAGON_EGGS_FALLING,
                set ->
                    set.addPrimitive(Material.DRAGON_EGG));
            this.rulesFallingEntityChangeBlockEventFrom.reg(this.triggers.SAND_FALLING,
                this.tags.sand);
            this.rulesFallingEntityChangeBlockEventFrom.reg(this.triggers.GRAVEL_FALLING,
                this.tags.gravel);
            this.rulesFallingEntityChangeBlockEventFrom.reg(this.triggers.ANVILS_FALLING,
                this.tags.anvil);
            this.rulesFallingEntityChangeBlockEventFrom.reg(this.triggers.DRAGON_EGGS_FALLING,
                set ->
                    set.addPrimitive(Material.DRAGON_EGG));
            this.rulesFallingEntityChangeBlockEventFrom.reg(this.triggers.IGNORED_STATE, // On custom falling blocks fall (created by third-party plugins like WoodCutter)
                this.tags.world_air);
            this.rulesNonFallingEntityChangeBlockEventByFrom.reg(this.triggers.BURNING_ARROWS_ACTIVATE_TNT,
                Collections.singleton(EntityType.ARROW),
                set ->
                    set.addPrimitive(Material.TNT));
            this.rulesNonFallingEntityChangeBlockEventByFrom.reg(this.triggers.ZOMBIES_BREAK_DOORS,
                Collections.singleton(EntityType.ZOMBIE),
                this.tags.wooden_doors);
            this.rulesNonFallingEntityChangeBlockEventBy.reg(this.triggers.IGNORED_STATE, // Boats destroys lilies. TODO It is necessary to implement a smart system of destruction and restoration of water lilies so that there are no problems with movement
                Collections.singleton(EntityType.BOAT));
            this.rulesNonFallingEntityChangeBlockEventBy.reg(this.triggers.SHEEPS_EATING_GRASS,
                Collections.singleton(EntityType.SHEEP));
            this.rulesNonFallingEntityChangeBlockEventBy.reg(this.triggers.ENDERMANS_GRIEFING,
                Collections.singleton(EntityType.ENDERMAN));
            this.rulesNonFallingEntityChangeBlockEventBy.reg(this.triggers.WITHERS_GRIEFING,
                Collections.singleton(EntityType.WITHER));
            this.rulesNonFallingEntityChangeBlockEventBy.reg(this.triggers.SILVERFISHES_HIDING_IN_BLOCKS,
                Collections.singleton(EntityType.SILVERFISH));
            this.rulesNonFallingEntityChangeBlockEventBy.reg(this.triggers.VILLAGERS_FARMING,
                Collections.singleton(EntityType.VILLAGER));
        }
        if (this.data.hasVersion(1, 8, 0)) {
            this.rulesNonFallingEntityChangeBlockEventBy.reg(this.triggers.RABBITS_EATING_CARROTS,
                Collections.singleton(EntityType.RABBIT));
        }
        if (this.data.hasVersion(1, 11, 0)) {
            this.rulesNonFallingEntityChangeBlockEventByFrom.reg(this.triggers.ZOMBIES_BREAK_DOORS,
                Collections.singleton(EntityType.ZOMBIE_VILLAGER),
                this.tags.wooden_doors);
        }
        if (this.data.hasVersion(1, 12, 0)) {
            this.rulesFallingEntityChangeBlockEventBy.reg(this.triggers.CONCRETE_POWDERS_FALLING,
                this.tags.concrete_powders);
            this.rulesFallingEntityChangeBlockEventFrom.reg(this.triggers.CONCRETE_POWDERS_FALLING,
                this.tags.concrete_powders);
        }
        if (this.data.hasVersion(1, 13, 0)) {
            this.rulesNonFallingEntityChangeBlockEventBy.reg(this.triggers.TURTLES_LAYING_EGGS,
                Collections.singleton(EntityType.TURTLE));
        }
        if (this.data.hasVersion(1, 14, 0)) {
            this.rulesFallingEntityChangeBlockEventBy.reg(this.triggers.SCAFFOLDING_FALLING,
                set ->
                    set.addPrimitive(Material.SCAFFOLDING));
            this.rulesFallingEntityChangeBlockEventFrom.reg(this.triggers.SCAFFOLDING_FALLING,
                set ->
                    set.addPrimitive(Material.SCAFFOLDING));
            this.rulesNonFallingEntityChangeBlockEventBy.reg(this.triggers.RAVAGERS_DESTROY_BLOCKS,
                Collections.singleton(EntityType.RAVAGER));
            this.rulesNonFallingEntityChangeBlockEventBy.reg(this.triggers.FOXES_EATS_FROM_SWEET_BERRY_BUSHES,
                Collections.singleton(EntityType.FOX));
        }
        if (this.data.hasVersion(1, 17, 0)) {
            this.rulesEntityChangeBlockEventFromTo.reg(this.triggers.DRIPLEAFS_LOWERING,
                from ->
                    from.addPrimitive(Material.BIG_DRIPLEAF),
                to ->
                    to.addPrimitive(Material.BIG_DRIPLEAF));
            this.rulesEntityChangeBlockEventFromTo.reg(this.triggers.POWDER_SNOW_MELTS_FROM_BURNING_ENTITIES,
                from ->
                    from.addPrimitive(Material.POWDER_SNOW),
                this.tags.world_air);
            this.rulesEntityChangeBlockEventFromTo.reg(this.triggers.GLOW_BERRIES_PICKING,
                from ->
                    from.addPrimitive(Material.CAVE_VINES),
                to ->
                    to.addPrimitive(Material.CAVE_VINES));
            this.rulesEntityChangeBlockEventFromTo.reg(this.triggers.GLOW_BERRIES_PICKING,
                from ->
                    from.addPrimitive(Material.CAVE_VINES_PLANT),
                to ->
                    to.addPrimitive(Material.CAVE_VINES_PLANT));
            this.rulesFallingEntityChangeBlockEventBy.reg(this.triggers.POINTED_DRIPSTONES_FALLING,
                set ->
                    set.addPrimitive(Material.POINTED_DRIPSTONE));
            this.rulesFallingEntityChangeBlockEventFrom.reg(this.triggers.POINTED_DRIPSTONES_FALLING,
                set ->
                    set.addPrimitive(Material.POINTED_DRIPSTONE));
        }
        if (this.data.hasVersion(1, 19, 0)) {
            this.rulesEntityChangeBlockEventFromTo.reg(this.triggers.FROGSPAWN_LAYING_AND_SPAWNING,
                from ->
                    from.addPrimitive(this.tags.world_air),
                to ->
                    to.addPrimitive(Material.FROGSPAWN));
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
        if (event.isCancelled() && updateBlockOnCancel && !this.data.hasVersion(1, 19, 4)) {
            event.getBlock().getState().update(false, false);
        }
    }

    private final MaterialMaterialRules rulesBlockFromToEventFromTo = new MaterialMaterialRules(this.data);
    private final MaterialRules rulesBlockFromToEventFrom = new MaterialRules(this.data);

    {
        if (this.data.hasVersion(1, 0, 0)) {
            this.rulesBlockFromToEventFromTo.reg(this.triggers.IGNORED_STATE, // Strange thing from FluidTypeFlowing
                this.tags.world_air,
                this.tags.world_air);
            this.rulesBlockFromToEventFrom.reg(this.triggers.LAVA_FLOWING,
                this.tags.lava);
            this.rulesBlockFromToEventFrom.reg(this.triggers.WATER_FLOWING,
                this.tags.blocks_under_water_only);
            this.rulesBlockFromToEventFrom.reg(this.triggers.IGNORED_STATE, // Seems bug while chunks generation (water near gravity blocks?): "Action BlockFromTo (GRAVEL > GRAVEL) was detected"
                this.tags.natural_gravity_blocks);
            this.rulesBlockFromToEventFrom.reg(this.triggers.DRAGON_EGGS_TELEPORTING,
                set ->
                    set.addPrimitive(Material.DRAGON_EGG));
        }
        if (this.data.hasVersion(1, 13, 0)) {
            this.rulesBlockFromToEventFromTo.reg(this.triggers.IGNORED_STATE, // Seems bug while chunks generation (kelp near caves?): "Action BlockFromTo (KELP > AIR) was detected"
                from ->
                    from.addPrimitive(Material.KELP),
                this.tags.world_air);
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
        if (this.data.hasVersion(1, 0, 0)) {
            this.rulesBlockFadeEventFromTo.reg(this.triggers.GRASS_BLOCKS_FADING,
                from -> {
                    from.addPrimitive(this.tags.grass_block);
                    from.addPrimitive(this.tags.dirt_path_block);
                },
                to ->
                    to.addPrimitive(Material.DIRT));
            this.rulesBlockFadeEventFromTo.reg(this.triggers.MYCELIUM_SPREADING,
                this.tags.mycelium_block,
                to ->
                    to.addPrimitive(Material.DIRT));
            this.rulesBlockFadeEventFromTo.reg(this.triggers.FARMLANDS_DRYING,
                this.tags.farmland_block,
                to ->
                    to.addPrimitive(Material.DIRT));
            this.rulesBlockFadeEventFromTo.reg(this.triggers.SNOW_MELTING,
                from ->
                    from.addPrimitive(Material.SNOW),
                this.tags.world_air);
            this.rulesBlockFadeEventFromTo.reg(this.triggers.ICE_MELTING,
                from ->
                    from.addPrimitive(Material.ICE),
                to -> {
                    to.addPrimitive(this.tags.water);
                    to.addPrimitive(this.tags.world_air);
                });
            this.rulesBlockFadeEventFromTo.reg(this.triggers.FIRE_SPREADING,
                from ->
                    from.addPrimitive(Material.FIRE),
                this.tags.world_air);
            this.rulesBlockFadeEventFromTo.reg(this.triggers.IGNORED_STATE, // Redstone ore deactivation
                this.tags.redstone_ore_blocks,
                this.tags.redstone_ore_blocks);
            this.rulesBlockFadeEventFromTo.reg(this.triggers.IGNORED_STATE, // Strange server action. Perhaps this is due to the fall of blocks without a base (torches for example) during generation (only in mineshafts?)
                this.tags.world_air,
                this.tags.world_air);
        }
        if (this.data.hasVersion(1, 9, 0)) {
            this.rulesBlockFadeEventFromTo.reg(this.triggers.FROSTED_ICE_PHYSICS,
                from ->
                    from.addPrimitive(Material.FROSTED_ICE),
                this.tags.water);
        }
        if (this.data.hasVersion(1, 13, 0)) {
            this.rulesBlockFadeEventFromTo.reg(this.triggers.CORALS_DRYING,
                this.tags.all_alive_corals,
                this.tags.all_dead_corals);
        }
        if (this.data.hasVersion(1, 14, 0)) {
            this.rulesBlockFadeEventFromTo.reg(this.triggers.SCAFFOLDING_FALLING,
                from ->
                    from.addPrimitive(Material.SCAFFOLDING),
                this.tags.world_air);
        }
        if (this.data.hasVersion(1, 16, 0)) {
            this.rulesBlockFadeEventFromTo.reg(this.triggers.CRIMSON_NYLIUM_FADING,
                from ->
                    from.addPrimitive(Material.CRIMSON_NYLIUM),
                to ->
                    to.addPrimitive(Material.NETHERRACK));
            this.rulesBlockFadeEventFromTo.reg(this.triggers.WARPED_NYLIUM_FADING,
                from ->
                    from.addPrimitive(Material.WARPED_NYLIUM),
                to ->
                    to.addPrimitive(Material.NETHERRACK));
        }
        if (this.data.hasVersion(1, 19, 0)) {
            this.rulesBlockFadeEventFromTo.reg(this.triggers.FROGSPAWN_LAYING_AND_SPAWNING,
                from ->
                    from.addPrimitive(Material.FROGSPAWN),
                to ->
                    to.addPrimitive(this.tags.world_air));
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
        if (this.data.hasVersion(1, 0, 0)) {
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.GRASS_SPREADING,
                from ->
                    from.addPrimitive(Material.DIRT),
                this.tags.grass_block);
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.MYCELIUM_SPREADING,
                from ->
                    from.addPrimitive(Material.DIRT),
                this.tags.mycelium_block);
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.VINES_GROWING,
                from -> {
                    from.addPrimitive(Material.VINE);
                    from.addPrimitive(this.tags.world_air);
                },
                to ->
                    to.addPrimitive(Material.VINE));
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.LITTLE_MUSHROOMS_SPREADING,
                this.tags.world_air,
                this.tags.little_mushrooms);
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.FIRE_SPREADING,
                this.tags.world_air,
                to ->
                    to.addPrimitive(Material.FIRE));
        }
        if (this.data.hasVersion(1, 9, 0)) {
            this.rulesBlockSpreadEventTo.reg(this.triggers.CHORUSES_GROWING,
                set ->
                    set.addPrimitive(Material.CHORUS_FLOWER));
        }
        if (this.data.hasVersion(1, 13, 0)) {
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.KELPS_GROWING,
                this.tags.water,
                to ->
                    to.addPrimitive(Material.KELP));
        }
        if (this.data.hasVersion(1, 14, 0)) {
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.BAMBOO_GROWING,
                this.tags.world_air,
                to ->
                    to.addPrimitive(Material.BAMBOO));
        }
        if (this.data.hasVersion(1, 16, 0)) {
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.WEEPING_VINES_GROWING,
                this.tags.world_air,
                to ->
                    to.addPrimitive(Material.WEEPING_VINES));
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.TWISTING_VINES_GROWING,
                this.tags.world_air,
                to ->
                    to.addPrimitive(Material.TWISTING_VINES));
        }
        if (this.data.hasVersion(1, 17, 0)) {
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.GLOW_BERRIES_GROWING,
                from -> {
                    from.addPrimitive(Material.CAVE_VINES);
                    from.addPrimitive(this.tags.world_air);
                },
                to ->
                    to.addPrimitive(Material.CAVE_VINES));
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.POINTED_DRIPSTONES_GROWING,
                from -> {
                    from.addPrimitive(Material.POINTED_DRIPSTONE);
                    from.addPrimitive(this.tags.world_air);
                },
                to ->
                    to.addPrimitive(Material.POINTED_DRIPSTONE));
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.IGNORED_STATE, // BONE_MEAL_USAGE
                this.tags.world_air,
                to ->
                    to.addPrimitive(Material.HANGING_ROOTS));
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.IGNORED_STATE, // BONE_MEAL_USAGE
                from -> {
                    from.addPrimitive(Material.GLOW_LICHEN);
                    from.addPrimitive(this.tags.world_air);
                },
                to ->
                    to.addPrimitive(Material.GLOW_LICHEN));
            this.rulesBlockSpreadEventTo.reg(this.triggers.AMETHYST_CLUSTERS_GROWING, // from = AIR, CAVE_AIR, WATER, etc
                set ->
                    set.addPrimitive(Material.SMALL_AMETHYST_BUD));
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.AMETHYST_CLUSTERS_GROWING,
                from ->
                    from.addPrimitive(Material.SMALL_AMETHYST_BUD),
                to ->
                    to.addPrimitive(Material.MEDIUM_AMETHYST_BUD));
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.AMETHYST_CLUSTERS_GROWING,
                from ->
                    from.addPrimitive(Material.MEDIUM_AMETHYST_BUD),
                to ->
                    to.addPrimitive(Material.LARGE_AMETHYST_BUD));
            this.rulesBlockSpreadEventFromTo.reg(this.triggers.AMETHYST_CLUSTERS_GROWING,
                from ->
                    from.addPrimitive(Material.LARGE_AMETHYST_BUD),
                to ->
                    to.addPrimitive(Material.AMETHYST_CLUSTER));
        }
        if (this.data.hasVersion(1, 19, 0)) {
            this.rulesBlockSpreadEventTo.reg(this.triggers.SCULKS_SPREADING,
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
        if (this.data.hasVersion(1, 0, 0)) {
            this.rulesEntityInteractEventMaterial.reg(this.triggers.FARMLANDS_TRAMPLING,
                this.tags.farmland_block);
            this.rulesEntityInteractEventMaterial.reg(this.triggers.IGNORED_STATE, // Redstone activators
                this.tags.redstone_passive_inputs);
            this.rulesEntityInteractEventMaterial.reg(this.triggers.IGNORED_STATE, // Redstone ore activation
                this.tags.redstone_ore_blocks);
            this.rulesEntityInteractEventMaterial.reg(this.triggers.END_PORTAL_FRAMES_FILLING,
                this.tags.end_portal_frames);
        }
        if (this.data.hasVersion(1, 13, 0)) {
            this.rulesEntityInteractEventMaterial.reg(this.triggers.TURTLE_EGGS_TRAMPLING,
                set ->
                    set.addPrimitive(Material.TURTLE_EGG));
        }
        if (this.data.hasVersion(1, 17, 0)) {
            this.rulesEntityInteractEventMaterial.reg(this.triggers.IGNORED_STATE, // Control by DRIPLEAFS_LOWERING
                set ->
                    set.addPrimitive(Material.BIG_DRIPLEAF));
            this.rulesEntityInteractEventMaterial.reg(this.triggers.IGNORED_STATE, // Any entities stay on block
                set ->
                    set.addPrimitive(Material.SCULK_SENSOR));
        }
        if (this.data.hasVersion(1, 19, 0)) {
            this.rulesEntityInteractEventMaterial.reg(this.triggers.IGNORED_STATE, // Players stay on block
                set ->
                    set.addPrimitive(Material.SCULK_SHRIEKER));
        }
        if (this.data.hasVersion(1, 20, 0)) {
            this.rulesEntityInteractEventMaterial.reg(this.triggers.IGNORED_STATE, // Any entities stay on block
                set ->
                    set.addPrimitive(Material.CALIBRATED_SCULK_SENSOR));
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void on(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) return;
        if (event.getAction() == Action.PHYSICAL) {
            if (event.getBlockFace() != BlockFace.SELF) return;
            this.handleInteraction(event, clickedBlock, event.getPlayer());
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (this.tags.end_portal_frames.contains(clickedBlock.getType())) {
                this.data.cancelIfDisabled(event, clickedBlock.getWorld(), this.triggers.END_PORTAL_FRAMES_FILLING);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void on(EntityInteractEvent event) {
        if (event.getEntityType() == EntityType.VILLAGER && this.tags.wooden_doors.contains(event.getBlock().getType())) {
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
        if (this.data.hasVersion(1, 0, 0)) {
            this.rulesEntityBlockFormEventFromTo.reg(this.triggers.SNOW_GOLEMS_CREATE_SNOW,
                this.tags.world_air,
                to ->
                    to.addPrimitive(Material.SNOW));
        }
        if (this.data.hasVersion(1, 9, 0)) {
            this.rulesEntityBlockFormEventFromTo.reg(this.triggers.FROSTED_ICE_PHYSICS,
                this.tags.water,
                to ->
                    to.addPrimitive(Material.FROSTED_ICE));
        }
        if (this.data.hasVersion(1, 14, 0)) {
            this.rulesEntityBlockFormEventFromTo.reg(this.triggers.WITHER_CREATE_WITHER_ROSE_BLOCKS,
                this.tags.world_air,
                to ->
                    to.addPrimitive(Material.WITHER_ROSE));
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
        this.triggers.PLAYERS_BONE_MEAL_USAGE.markAvailable();
        this.rulesStructureGrowEventTo.reg(this.triggers.TREES_GROWING,
            set -> {
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
            });
        this.rulesStructureGrowEventTo.reg(this.triggers.GIANT_MUSHROOMS_GROWING,
            set -> {
                set.addPrimitive(TreeType.RED_MUSHROOM);
                set.addPrimitive(TreeType.BROWN_MUSHROOM);
            });
        if (this.data.hasVersion(1, 9, 0)) {
            this.rulesStructureGrowEventTo.reg(this.triggers.CHORUSES_GROWING,
                set ->
                    set.addPrimitive(TreeType.CHORUS_PLANT));
        }
    }

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
        if (this.data.hasVersion(1, 11, 0)) { // ProjectileHitEvent.getHitBlock() since 1.11
            this.triggers.BLOCK_HIT_PROJECTILES_REMOVING.markAvailable();
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void on(ProjectileHitEvent event) {
        if (!this.data.hasVersion(1, 11, 0)) return;
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
        this.triggers.PLAYERS_BONE_MEAL_USAGE.markAvailable();
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

    private final MaterialRules rulesBlockPhysicsEventFrom = new MaterialRules(this.data);

    {
        if (BLOCK_PHYSICS_EVENT_GET_SOURCE_BLOCK_SUPPORT || !this.data.hasVersion(1, 13, 0)) {
            this.rulesBlockPhysicsEventFrom.reg(this.triggers.SAPLINGS_DESTROYING,
                set ->
                    set.addPrimitive(this.tags.saplings));
            this.rulesBlockPhysicsEventFrom.reg(this.triggers.LADDERS_DESTROYING,
                set ->
                    set.addPrimitive(this.tags.ladders));
            this.rulesBlockPhysicsEventFrom.reg(this.triggers.SIGNS_DESTROYING,
                set ->
                    set.addPrimitive(this.tags.signs));
            this.rulesBlockPhysicsEventFrom.reg(this.triggers.RAILS_DESTROYING,
                set ->
                    set.addPrimitive(this.tags.rails));
            this.rulesBlockPhysicsEventFrom.reg(this.triggers.TORCHES_DESTROYING,
                set ->
                    set.addPrimitive(this.tags.torches));
            this.rulesBlockPhysicsEventFrom.reg(this.triggers.REDSTONE_TORCHES_DESTROYING,
                set ->
                    set.addPrimitive(this.tags.redstone_torches));
        }
        if (this.data.hasVersion(1, 16, 0)) {
            this.rulesBlockPhysicsEventFrom.reg(this.triggers.SOUL_TORCHES_DESTROYING,
                set ->
                    set.addPrimitive(this.tags.soul_torches));
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void modernListener(BlockPhysicsEvent event) {
        Block block = event.getBlock();

        if (!this.data.hasVersion(1, 13, 0)) {
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
