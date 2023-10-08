package ru.dymeth.pcontrol.api;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import ru.dymeth.pcontrol.CustomTags;
import ru.dymeth.pcontrol.VersionsAdapter;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public abstract class PhysicsListener implements Listener {
    protected static final BlockFace[] NSWE_FACES = new BlockFace[]{
        BlockFace.NORTH,
        BlockFace.SOUTH,
        BlockFace.WEST,
        BlockFace.EAST
    };
    protected static final boolean DEBUG_PHYSICS_EVENT = false;

    protected final PControlData data;
    protected final CustomTags tags;
    protected final VersionsAdapter versionsAdapter;
    protected final Set<Vector> fertilizedBlocks;

    protected PhysicsListener(@Nonnull PControlData data) {
        this.data = data;
        this.tags = data.getCustomTags();
        this.versionsAdapter = data.getVersionsAdapter();
        this.fertilizedBlocks = new HashSet<>();
        data.getPlugin().getServer().getScheduler().runTaskTimer(data.getPlugin(), this.fertilizedBlocks::clear, 1L, 1L);
    }

    protected void unrecognizedAction(@Nonnull Cancellable event, @Nonnull Location l, @Nonnull Object content) {
        if (l.getWorld() == null) throw new IllegalArgumentException("World cannot be null");
        if (!this.data.isActionAllowed(l.getWorld(), PControlTrigger.ALLOW_UNRECOGNIZED_ACTIONS)) {
            event.setCancelled(true);
        }
        if (this.data.isActionAllowed(l.getWorld(), PControlTrigger.DEBUG_MESSAGES)) {
            this.debugAction((Event) event, l, content);
        }
    }

    protected void debugAction(@Nonnull Event event, @Nonnull Location l, @Nonnull Object content) {
        if (l.getWorld() == null) throw new IllegalArgumentException("World cannot be null");

        String world = l.getWorld().getName();
        String xyz = l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ();

        String text = this.data.getMessage("debug-message",
            "%action%", event.getClass().getSimpleName().replace("Event", ""),
            "%content%", content.toString())
            + l.getWorld().getName() + " " + xyz;

        String command;
        if (this.data.hasVersion(13)) {
            command = "/execute in " + world + " run tp @p " + xyz;
        } else {
            // TODO Create own command to teleport any different worlds
            command = "/minecraft:tp @p " + xyz;
        }

        this.data.announce(l.getWorld(), this.data.getTextHelper().createClickable(text, command));
    }

    @SuppressWarnings("unused")
    @Nonnull
    protected String genOffsetMsg(@Nonnull Block first, @Nonnull Block second) {
        return genOffsetMsg(
            first.getX() - second.getX(),
            first.getY() - second.getY(),
            first.getZ() - second.getZ());
    }

    @Nonnull
    private String genOffsetMsg(int oX, int oY, int oZ) {
        return "offset=" + (oX < 0 ? oX : " " + oX) + " " + (oY < 0 ? oY : " " + oY) + " " + (oZ < 0 ? oZ : " " + oZ) + ";";
    }
}
