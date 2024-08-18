package ru.dymeth.pcontrol.listener.player;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.dymeth.pcontrol.PhysicsListener;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.EventsListenerParser;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.rules.single.MaterialRules;

import javax.annotation.Nonnull;

public class PlayerInteractEventListener extends PhysicsListener {

    private final PControlTrigger triggerPlayersBoneMealUsage
        = this.data.getTriggersRegisty().valueOf("PLAYERS_BONE_MEAL_USAGE");
    private final MaterialRules rulesPlayerInteractEventPhysicalMaterial = new MaterialRules(
        this.data, PlayerInteractEvent.class, "physical-material");
    private final MaterialRules rulesPlayerInteractEventClickedMaterial = new MaterialRules(
        this.data, PlayerInteractEvent.class, "right-clicked-material");

    public PlayerInteractEventListener(@Nonnull PControlData data, @Nonnull EventsListenerParser parser) {
        super(data);
        parser.registerParser(this.rulesPlayerInteractEventPhysicalMaterial);
        parser.registerParser(this.rulesPlayerInteractEventClickedMaterial);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void on(PlayerInteractEvent event) {
        Block interactedBlock = event.getClickedBlock();
        if (interactedBlock == null) return;
        if (event.getAction() == Action.PHYSICAL) {
            if (event.getBlockFace() != BlockFace.SELF) return;
            Player player = event.getPlayer();
            World world = interactedBlock.getWorld();
            Material material = interactedBlock.getType();

            PControlTrigger trigger = this.rulesPlayerInteractEventPhysicalMaterial.findTrigger(material);

            if (trigger != null) {
                this.data.cancelIfDisabled(event, world, trigger);
            } else {
                this.unrecognizedAction(event, interactedBlock.getLocation(), material + " (by player " + player.getName() + ")");
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Material clickedType = interactedBlock.getType();
            PControlTrigger trigger = this.rulesPlayerInteractEventClickedMaterial.findTrigger(clickedType);
            if (trigger != null) {
                World world = interactedBlock.getWorld();
                this.data.cancelIfDisabled(event, world, trigger);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void onBoneMeal(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block targetBlock = event.getClickedBlock();
        if (targetBlock == null) {
            throw new IllegalArgumentException("Block absent on " + PlayerInteractEvent.class.getSimpleName()
                + " with " + event.getAction().getClass().getSimpleName() + "." + event.getAction().name());
        }

        ItemStack usedItem = event.getItem();
        if (usedItem == null) return;
        if (!this.versionsAdapter.isBoneMealItem(usedItem)) return;

        if (!this.data.isActionAllowed(targetBlock.getWorld(), this.triggerPlayersBoneMealUsage)) {
            event.setUseItemInHand(Event.Result.DENY);
        }
        if (event.useItemInHand() == Event.Result.DENY) return;
        this.fertilizedBlocks.add(targetBlock.getLocation().toVector());
    }
}
