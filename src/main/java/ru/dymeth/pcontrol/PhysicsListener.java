package ru.dymeth.pcontrol;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public abstract class PhysicsListener implements Listener {
    protected final BlockFace[] nsweFaces = new BlockFace[]{
            BlockFace.NORTH,
            BlockFace.SOUTH,
            BlockFace.WEST,
            BlockFace.EAST
    };
    protected final boolean debugPhysicsEvent = false;

    protected final PControlData data;
    protected final Set<Vector> fertilizedBlocks;

    public PhysicsListener(@Nonnull PControlData data) {
        this.data = data;
        this.fertilizedBlocks = new HashSet<>();
        Bukkit.getScheduler().runTaskTimer(data.getPlugin(), this.fertilizedBlocks::clear, 1L, 1L);
    }

    protected void unrecognizedAction(@Nonnull Cancellable event, @Nonnull Location l, @Nonnull Object content) {
        if (l.getWorld() == null) throw new IllegalArgumentException("World cannot be null");
        if (!this.data.isActionAllowed(l.getWorld(), PControlTrigger.ALLOW_UNRECOGNIZED_ACTIONS))
            event.setCancelled(true);
        if (this.data.isActionAllowed(l.getWorld(), PControlTrigger.DEBUG_MESSAGES))
            this.debugAction((Event) event, l, content);
    }

    protected void debugAction(@Nonnull Event event, @Nonnull Location l, @Nonnull Object content) {
        if (l.getWorld() == null) throw new IllegalArgumentException("World cannot be null");
        String xyz = l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ();
        TextComponent message = new TextComponent(this.data.getMessage("debug-message", "%action%", event.getClass().getSimpleName().replace("Event", ""), "%content%", content.toString()));
        TextComponent posPart = new TextComponent(l.getWorld().getName() + " " + xyz);
        posPart.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/teleport " + xyz));
        message.addExtra(posPart);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("physicscontrol.announce")) {
                player.spigot().sendMessage(message);
            }
        }
        Bukkit.getConsoleSender().sendMessage(message.toPlainText());
    }

    protected String genOffsetMsg(Block first, Block second) {
        return genOffsetMsg(
                first.getX() - second.getX(),
                first.getY() - second.getY(),
                first.getZ() - second.getZ());
    }

    private String genOffsetMsg(int oX, int oY, int oZ) {
        return "offset=" + (oX < 0 ? oX : " " + oX) + " " + (oY < 0 ? oY : " " + oY) + " " + (oZ < 0 ? oZ : " " + oZ) + ";";
    }
}
