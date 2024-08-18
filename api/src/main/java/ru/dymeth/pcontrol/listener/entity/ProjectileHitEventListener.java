package ru.dymeth.pcontrol.listener.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import ru.dymeth.pcontrol.PhysicsListener;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.EventsListenerParser;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;

import javax.annotation.Nonnull;

public class ProjectileHitEventListener extends PhysicsListener {

    private final boolean supportProjectileHitEventGetHitBlock
        = this.data.hasVersion(1, 11, 0);
    private final PControlTrigger triggerBlockHitProjectilesRemoving
        = !this.supportProjectileHitEventGetHitBlock ? null
        : this.data.getTriggersRegisty().valueOf("BLOCK_HIT_PROJECTILES_REMOVING");

    public ProjectileHitEventListener(@Nonnull PControlData data, @Nonnull EventsListenerParser parser) {
        super(data);
    }

    @EventHandler(ignoreCancelled = true)
    private void on(ProjectileHitEvent event) {
        if (!this.supportProjectileHitEventGetHitBlock) return;
        if (event.getHitBlock() == null) return; // Since 1.11
        Entity entity = event.getEntity();
        if (!this.data.getRemovableProjectileTypes().contains(entity.getType())) return;
        if (!this.data.isActionAllowed(entity.getWorld(), this.triggerBlockHitProjectilesRemoving)) return;
        entity.remove();
    }
}
