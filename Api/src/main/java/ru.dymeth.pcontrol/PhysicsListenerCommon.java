package ru.dymeth.pcontrol;

import org.bukkit.Material;
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
import ru.dymeth.pcontrol.rules.pair.EntityMaterialRules;
import ru.dymeth.pcontrol.rules.pair.MaterialMaterialRules;
import ru.dymeth.pcontrol.rules.single.EntityRules;
import ru.dymeth.pcontrol.rules.single.MaterialRules;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;

public abstract class PhysicsListenerCommon extends PhysicsListener {

    protected final CustomTag customTag;

    protected PhysicsListenerCommon(@Nonnull PControlData data) {
        super(data);
        this.customTag = new CustomTag(data);
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
                this.customTag.SUGAR_CANE_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.CACTUS_GROWING,
                BlocksSet.create(PControlTrigger.CACTUS_GROWING + " trigger", this.data, set ->
                    set.add(Material.CACTUS)));
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.WHEAT_GROWING,
                this.customTag.WHEAT_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.POTATOES_GROWING,
                this.customTag.POTATO_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.CARROTS_GROWING,
                this.customTag.CARROT_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.PUMPKINS_GROWING,
                this.customTag.PUMPKIN_STEM_AND_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.MELONS_GROWING,
                this.customTag.MELON_STEM_AND_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.COCOAS_GROWING,
                BlocksSet.create(PControlTrigger.COCOAS_GROWING + " trigger", this.data, set ->
                    set.add(Material.COCOA)));
            this.rulesBlockGrowEventFromTo.regPair(PControlTrigger.VINES_GROWING,
                BlocksSet.create(PControlTrigger.VINES_GROWING + " trigger", this.data, set ->
                    set.add(Material.VINE)),
                BlocksSet.create(PControlTrigger.VINES_GROWING + " trigger", this.data, set ->
                    set.add(Material.VINE)));
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.NETHER_WARTS_GROWING,
                this.customTag.NETHER_WART_BLOCK);
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.BONE_MEAL_USAGE,
                this.customTag.BONE_MEAL_HERBS);
        }
        if (this.data.hasVersion(9)) {
            this.rulesBlockGrowEventTo.regSingle(PControlTrigger.BEETROOTS_GROWING,
                this.customTag.BEETROOT_BLOCK);
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

    private void initEntityChangeBlockEvent() {
        if (this.data.hasVersion(0)) {
            this.rulesEntityChangeBlockEventFromTo.regPair(PControlTrigger.FARMLANDS_TRAMPLING,
                this.customTag.FARMLAND_BLOCK,
                BlocksSet.create(PControlTrigger.FARMLANDS_TRAMPLING + " trigger", this.data, set ->
                    set.add(Material.DIRT)));
            this.rulesEntityChangeBlockEventTo.regSingle(PControlTrigger.IGNORED_STATE, // Redstone ore activation
                this.customTag.REDSTONE_ORE_BLOCKS);
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.SAND_FALLING,
                this.customTag.SAND);
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.GRAVEL_FALLING,
                this.customTag.GRAVEL);
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.ANVILS_FALLING,
                this.customTag.ANVIL);
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.DRAGON_EGGS_FALLING,
                BlocksSet.create(PControlTrigger.DRAGON_EGGS_FALLING + " trigger", this.data, set ->
                    set.add(Material.DRAGON_EGG)));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.SAND_FALLING,
                this.customTag.SAND);
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.GRAVEL_FALLING,
                this.customTag.GRAVEL);
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.ANVILS_FALLING,
                this.customTag.ANVIL);
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.DRAGON_EGGS_FALLING,
                BlocksSet.create(PControlTrigger.DRAGON_EGGS_FALLING + " trigger", this.data, set ->
                    set.add(Material.DRAGON_EGG)));
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.IGNORED_STATE, // On custom falling blocks fall (created by third-party plugins like WoodCutter)
                this.customTag.WORLD_AIR);
            this.rulesNonFallingEntityChangeBlockEventByFrom.regPair(PControlTrigger.BURNING_ARROWS_ACTIVATE_TNT,
                Collections.singleton(EntityType.ARROW),
                BlocksSet.create(PControlTrigger.BURNING_ARROWS_ACTIVATE_TNT + " trigger", this.data, set ->
                    set.add(Material.TNT)));
            this.rulesNonFallingEntityChangeBlockEventByFrom.regPair(PControlTrigger.ZOMBIES_BREAK_DOORS,
                Collections.singleton(EntityType.ZOMBIE),
                this.customTag.WOODEN_DOORS);
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
                this.customTag.WOODEN_DOORS);
        }
        if (this.data.hasVersion(12)) {
            this.rulesFallingEntityChangeBlockEventBy.regSingle(PControlTrigger.CONCRETE_POWDERS_FALLING,
                this.customTag.CONCRETE_POWDERS);
            this.rulesFallingEntityChangeBlockEventFrom.regSingle(PControlTrigger.CONCRETE_POWDERS_FALLING,
                this.customTag.CONCRETE_POWDERS);
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
                this.customTag.WORLD_AIR);
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

    private void initBlockFromToEvent() {
        if (this.data.hasVersion(0)) {
            this.rulesBlockFromToEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Strange thing from FluidTypeFlowing
                this.customTag.WORLD_AIR,
                this.customTag.WORLD_AIR);
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.LAVA_FLOWING,
                this.customTag.LAVA);
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.WATER_FLOWING,
                this.customTag.UNDERWATER_BLOCKS_ONLY);
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.IGNORED_STATE, // Seems bug while chunks generation (water near gravity blocks?): "Action BlockFromTo (GRAVEL > GRAVEL) was detected"
                this.customTag.NATURAL_GRAVITY_BLOCKS);
            this.rulesBlockFromToEventFrom.regSingle(PControlTrigger.DRAGON_EGGS_TELEPORTING,
                BlocksSet.create(PControlTrigger.DRAGON_EGGS_TELEPORTING + " trigger", this.data, set ->
                    set.add(Material.DRAGON_EGG)));
        }
        if (this.data.hasVersion(13)) {
            this.rulesBlockFromToEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Seems bug while chunks generation (kelp near caves?): "Action BlockFromTo (KELP > AIR) was detected"
                BlocksSet.create(PControlTrigger.IGNORED_STATE + " trigger", this.data, set ->
                    set.add(Material.KELP)),
                this.customTag.WORLD_AIR);
        }
    }

    private void initBlockFadeEvent() {
        if (this.data.hasVersion(0)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.GRASS_BLOCKS_FADING,
                BlocksSet.create(PControlTrigger.GRASS_BLOCKS_FADING + " trigger", this.data, set -> {
                    set.add(this.customTag.GRASS_BLOCK);
                    set.add(this.customTag.DIRT_PATH_BLOCK);
                }),
                BlocksSet.create(PControlTrigger.GRASS_BLOCKS_FADING + " trigger", this.data, set ->
                    set.add(Material.DIRT)));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.MYCELIUM_SPREADING,
                this.customTag.MYCELIUM_BLOCK,
                BlocksSet.create(PControlTrigger.MYCELIUM_SPREADING + " trigger", this.data, set ->
                    set.add(Material.DIRT)));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.FARMLANDS_DRYING,
                this.customTag.FARMLAND_BLOCK,
                BlocksSet.create(PControlTrigger.FARMLANDS_DRYING + " trigger", this.data, set ->
                    set.add(Material.DIRT)));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.SNOW_MELTING,
                BlocksSet.create(PControlTrigger.SNOW_MELTING + " trigger", this.data, set ->
                    set.add(Material.SNOW)),
                this.customTag.WORLD_AIR);
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.ICE_MELTING,
                BlocksSet.create(PControlTrigger.ICE_MELTING + " trigger", this.data, set ->
                    set.add(Material.ICE)),
                BlocksSet.create(PControlTrigger.ICE_MELTING + " trigger", this.data, set -> {
                    set.add(this.customTag.WATER);
                    set.add(this.customTag.WORLD_AIR);
                }));
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.FIRE_SPREADING,
                BlocksSet.create(PControlTrigger.FIRE_SPREADING + " trigger", this.data, set ->
                    set.add(Material.FIRE)),
                this.customTag.WORLD_AIR);
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Redstone ore deactivation
                this.customTag.REDSTONE_ORE_BLOCKS,
                this.customTag.REDSTONE_ORE_BLOCKS);
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // Strange server action. Perhaps this is due to the fall of blocks without a base (torches for example) during generation (only in mineshafts?)
                this.customTag.WORLD_AIR,
                this.customTag.WORLD_AIR);
        }
        if (this.data.hasVersion(9)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.FROSTED_ICE_PHYSICS,
                BlocksSet.create(PControlTrigger.FROSTED_ICE_PHYSICS + " trigger", this.data, set ->
                    set.add(Material.FROSTED_ICE)),
                this.customTag.WATER);
        }
        if (this.data.hasVersion(13)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.CORALS_DRYING,
                this.customTag.ALL_ALIVE_CORALS,
                this.customTag.ALL_DEAD_CORALS);
        }
        if (this.data.hasVersion(14)) {
            this.rulesBlockFadeEventFromTo.regPair(PControlTrigger.SCAFFOLDING_FALLING,
                BlocksSet.create(PControlTrigger.SCAFFOLDING_FALLING + " trigger", this.data, set ->
                    set.add(Material.SCAFFOLDING)),
                this.customTag.WORLD_AIR);
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

    private void initBlockSpreadEvent() {
        if (this.data.hasVersion(0)) {
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.GRASS_SPREADING,
                BlocksSet.create(PControlTrigger.GRASS_SPREADING + " trigger", this.data, set ->
                    set.add(Material.DIRT)),
                this.customTag.GRASS_BLOCK);
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.MYCELIUM_SPREADING,
                BlocksSet.create(PControlTrigger.MYCELIUM_SPREADING + " trigger", this.data, set ->
                    set.add(Material.DIRT)),
                this.customTag.MYCELIUM_BLOCK);
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.VINES_GROWING,
                BlocksSet.create(PControlTrigger.VINES_GROWING + " trigger", this.data, set -> {
                    set.add(Material.VINE);
                    set.add(this.customTag.WORLD_AIR);
                }),
                BlocksSet.create(PControlTrigger.VINES_GROWING + " trigger", this.data, set ->
                    set.add(Material.VINE)));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.LITTLE_MUSHROOMS_SPREADING,
                this.customTag.WORLD_AIR,
                this.customTag.LITTLE_MUSHROOMS);
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.FIRE_SPREADING,
                this.customTag.WORLD_AIR,
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
                this.customTag.WATER,
                BlocksSet.create(PControlTrigger.KELPS_GROWING + " trigger", this.data, set ->
                    set.add(Material.KELP)));
        }
        if (this.data.hasVersion(14)) {
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.BAMBOO_GROWING,
                this.customTag.WORLD_AIR,
                BlocksSet.create(PControlTrigger.BAMBOO_GROWING + " trigger", this.data, set ->
                    set.add(Material.BAMBOO)));
        }
        if (this.data.hasVersion(16)) {
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.WEEPING_VINES_GROWING,
                this.customTag.WORLD_AIR,
                BlocksSet.create(PControlTrigger.WEEPING_VINES_GROWING + " trigger", this.data, set ->
                    set.add(Material.WEEPING_VINES)));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.TWISTING_VINES_GROWING,
                this.customTag.WORLD_AIR,
                BlocksSet.create(PControlTrigger.TWISTING_VINES_GROWING + " trigger", this.data, set ->
                    set.add(Material.TWISTING_VINES)));
        }
        if (this.data.hasVersion(17)) {
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.GLOW_BERRIES_GROWING,
                BlocksSet.create(PControlTrigger.GLOW_BERRIES_GROWING + " trigger", this.data, set -> {
                    set.add(Material.CAVE_VINES);
                    set.add(this.customTag.WORLD_AIR);
                }),
                BlocksSet.create(PControlTrigger.GLOW_BERRIES_GROWING + " trigger", this.data, set ->
                    set.add(Material.CAVE_VINES)));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.POINTED_DRIPSTONES_GROWING,
                BlocksSet.create(PControlTrigger.POINTED_DRIPSTONES_GROWING + " trigger", this.data, set -> {
                    set.add(Material.POINTED_DRIPSTONE);
                    set.add(this.customTag.WORLD_AIR);
                }),
                BlocksSet.create(PControlTrigger.POINTED_DRIPSTONES_GROWING + " trigger", this.data, set ->
                    set.add(Material.POINTED_DRIPSTONE)));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // BONE_MEAL_USAGE
                this.customTag.WORLD_AIR,
                BlocksSet.create(PControlTrigger.IGNORED_STATE + " trigger", this.data, set ->
                    set.add(Material.HANGING_ROOTS)));
            this.rulesBlockSpreadEventFromTo.regPair(PControlTrigger.IGNORED_STATE, // BONE_MEAL_USAGE
                BlocksSet.create(PControlTrigger.IGNORED_STATE + " trigger", this.data, set -> {
                    set.add(Material.GLOW_LICHEN);
                    set.add(this.customTag.WORLD_AIR);
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

    private void initEntityInteractEvent() {
        if (this.data.hasVersion(0)) {
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.FARMLANDS_TRAMPLING,
                this.customTag.FARMLAND_BLOCK);
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.IGNORED_STATE, // Redstone activators
                this.customTag.REDSTONE_PASSIVE_INPUTS);
            this.rulesEntityInteractEventMaterial.regSingle(PControlTrigger.IGNORED_STATE, // Redstone ore activation
                this.customTag.REDSTONE_ORE_BLOCKS);
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

    private void initEntityBlockFormEvent() {
        if (this.data.hasVersion(0)) {
            this.rulesEntityBlockFormEventFromTo.regPair(PControlTrigger.SNOW_GOLEMS_CREATE_SNOW,
                this.customTag.WORLD_AIR,
                BlocksSet.create(PControlTrigger.SNOW_GOLEMS_CREATE_SNOW + " trigger", this.data, set ->
                    set.add(Material.SNOW)));
        }
        if (this.data.hasVersion(9)) {
            this.rulesEntityBlockFormEventFromTo.regPair(PControlTrigger.FROSTED_ICE_PHYSICS,
                this.customTag.WATER,
                BlocksSet.create(PControlTrigger.FROSTED_ICE_PHYSICS + " trigger", this.data, set ->
                    set.add(Material.FROSTED_ICE)));
        }
        if (this.data.hasVersion(14)) {
            this.rulesEntityBlockFormEventFromTo.regPair(PControlTrigger.WITHER_CREATE_WITHER_ROSE_BLOCKS,
                this.customTag.WORLD_AIR,
                BlocksSet.create(PControlTrigger.WITHER_CREATE_WITHER_ROSE_BLOCKS + " trigger", this.data, set ->
                    set.add(Material.WITHER_ROSE)));
        }
    }

    protected final MaterialMaterialRules rulesBlockGrowEventFromTo = new MaterialMaterialRules(this.data);
    protected final MaterialRules rulesBlockGrowEventTo = new MaterialRules(this.data);

    @EventHandler(ignoreCancelled = true)
    public void on(BlockGrowEvent event) {
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

    protected final MaterialMaterialRules rulesEntityChangeBlockEventFromTo = new MaterialMaterialRules(this.data);
    protected final MaterialRules rulesEntityChangeBlockEventTo = new MaterialRules(this.data);

    protected final MaterialRules rulesFallingEntityChangeBlockEventBy = new MaterialRules(this.data);
    protected final MaterialRules rulesFallingEntityChangeBlockEventFrom = new MaterialRules(this.data);

    protected final EntityMaterialRules rulesNonFallingEntityChangeBlockEventByFrom = new EntityMaterialRules(this.data);
    protected final EntityRules rulesNonFallingEntityChangeBlockEventBy = new EntityRules(this.data);

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void on(EntityChangeBlockEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getTo();
        World world = event.getEntity().getWorld();

        boolean updateBlockOnCancel = false;

        PControlTrigger trigger = this.rulesEntityChangeBlockEventFromTo.findTrigger(from, to);
        if (trigger == null) trigger = this.rulesEntityChangeBlockEventTo.findTrigger(to);

        if (trigger == null) {
            if (event.getEntity() instanceof FallingBlock) {
                Material by = this.getFallingBlockMaterial((FallingBlock) event.getEntity());

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

    @Nonnull
    protected abstract Material getFallingBlockMaterial(@Nonnull FallingBlock fallingBlock);

    protected final MaterialMaterialRules rulesBlockFromToEventFromTo = new MaterialMaterialRules(this.data);
    protected final MaterialRules rulesBlockFromToEventFrom = new MaterialRules(this.data);

    @EventHandler(ignoreCancelled = true)
    public void on(BlockFromToEvent event) {
        Material from = event.getBlock().getType();
        Material to = event.getToBlock().getType();

        PControlTrigger trigger = this.getBlockFromToEventTrigger(event.getBlock(), from, to);

        if (trigger != null) {
            this.data.cancelIfDisabled(event, trigger);
        } else {
            this.unrecognizedAction(event, event.getBlock().getLocation(), from + " > " + to);
        }
    }

    @Nullable
    protected PControlTrigger getBlockFromToEventTrigger(@Nonnull Block block, @Nonnull Material from, @Nonnull Material to) {
        PControlTrigger trigger = this.rulesBlockFromToEventFromTo.findTrigger(from, to);
        if (trigger == null) trigger = this.rulesBlockFromToEventFrom.findTrigger(from);
        return trigger;
    }

    protected final MaterialMaterialRules rulesBlockFadeEventFromTo = new MaterialMaterialRules(this.data);
    protected final MaterialRules rulesBlockFadeEventTo = new MaterialRules(this.data);

    @EventHandler(ignoreCancelled = true)
    public void on(BlockFadeEvent event) {
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

    protected final MaterialMaterialRules rulesBlockSpreadEventFromTo = new MaterialMaterialRules(this.data);
    protected final MaterialRules rulesBlockSpreadEventTo = new MaterialRules(this.data);

    @EventHandler(ignoreCancelled = true)
    public void on(BlockSpreadEvent event) {
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

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void on(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) return;
        if (event.getBlockFace() != BlockFace.SELF) return;
        if (event.getClickedBlock() == null) return;
        this.handleInteraction(event, event.getClickedBlock(), event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    public void on(EntityInteractEvent event) {
        if (event.getEntityType() == EntityType.VILLAGER && this.customTag.WOODEN_DOORS.contains(event.getBlock().getType())) {
            return;
        }
        this.handleInteraction(event, event.getBlock(), event.getEntity());
    }

    protected final MaterialRules rulesEntityInteractEventMaterial = new MaterialRules(this.data);

    protected void handleInteraction(@Nonnull Cancellable event, @Nonnull Block source, @Nonnull Entity entity) {
        World world = source.getWorld();
        Material material = source.getType();

        PControlTrigger trigger = this.rulesEntityInteractEventMaterial.findTrigger(material);

        if (trigger != null) {
            this.data.cancelIfDisabled(event, world, trigger);
        } else {
            this.unrecognizedAction(event, source.getLocation(), material + " (by " + entity + ")");
        }
    }

    protected final MaterialMaterialRules rulesEntityBlockFormEventFromTo = new MaterialMaterialRules(this.data);
    protected final MaterialRules rulesEntityBlockFormEventTo = new MaterialRules(this.data);

    @EventHandler(ignoreCancelled = true)
    public void on(EntityBlockFormEvent event) {
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

    @EventHandler(ignoreCancelled = true)
    public void on(StructureGrowEvent event) {
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
            case TALL_BIRCH: {
                this.data.cancelIfDisabled(event, world, PControlTrigger.TREES_GROWING);
                break;
            }
            case RED_MUSHROOM:
            case BROWN_MUSHROOM: {
                this.data.cancelIfDisabled(event, world, PControlTrigger.GIANT_MUSHROOMS_GROWING);
                break;
            }
            case CHORUS_PLANT: {
                this.data.cancelIfDisabled(event, world, PControlTrigger.CHORUSES_GROWING);
                break;
            }
            default: {
                this.unrecognizedAction(event, event.getLocation(), from + " > " + event.getSpecies());
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void on(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            this.data.cancelIfDisabled(event, PControlTrigger.PLAYERS_FLINT_USAGE);
        } else {
            this.data.cancelIfDisabled(event, PControlTrigger.FIRE_SPREADING);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void on(ProjectileHitEvent event) {
        if (!this.data.hasVersion(11)) return;
        if (event.getHitBlock() == null) return; // Since 1.11
        Entity entity = event.getEntity();
        if (!this.data.getRemovableProjectileTypes().contains(entity.getType())) return;
        if (!this.data.isActionAllowed(entity.getWorld(), PControlTrigger.BLOCK_HIT_PROJECTILES_REMOVING)) return;
        entity.remove();
    }

    @EventHandler(ignoreCancelled = true)
    public void on(BlockBurnEvent event) {
        this.data.cancelIfDisabled(event, PControlTrigger.FIRE_SPREADING);
    }

    @EventHandler(ignoreCancelled = true)
    public void on(LeavesDecayEvent event) {
        this.data.cancelIfDisabled(event, PControlTrigger.LEAVES_DECAY);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBoneMeal(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block targetBlock = event.getClickedBlock();
        if (targetBlock == null) {
            throw new IllegalArgumentException("Block absent on PlayerInteractEvent with Action.RIGHT_CLICK_BLOCK");
        }

        ItemStack usedItem = event.getItem();
        if (usedItem == null) return;
        if (!this.isBoneMealItem(usedItem)) return;

        this.data.cancelIfDisabled(event, targetBlock.getWorld(), PControlTrigger.BONE_MEAL_USAGE);
        if (event.useItemInHand() == Event.Result.DENY) return;
        this.fertilizedBlocks.add(targetBlock.getLocation().toVector());
    }

    protected abstract boolean isBoneMealItem(@Nonnull ItemStack stack);
}
