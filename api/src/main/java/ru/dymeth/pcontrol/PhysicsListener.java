package ru.dymeth.pcontrol;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import ru.dymeth.pcontrol.data.CustomTags;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.data.trigger.PControlTrigger;
import ru.dymeth.pcontrol.data.trigger.TriggersRegistry;
import ru.dymeth.pcontrol.set.BlocksSet;
import ru.dymeth.pcontrol.set.EntityTypesSet;
import ru.dymeth.pcontrol.set.TreeTypesSet;
import ru.dymeth.pcontrol.text.CommonColor;
import ru.dymeth.pcontrol.text.Text;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class PhysicsListener implements Listener {
    protected static final BlockFace[] NSWE_FACES = new BlockFace[]{
        BlockFace.NORTH,
        BlockFace.SOUTH,
        BlockFace.WEST,
        BlockFace.EAST
    };
    protected static final boolean DEBUG_BLOCK_PHYSICS_EVENT = false;

    protected final PControlData data;
    protected final CustomTags tags;
    protected final TriggersRegistry triggers;
    protected final VersionsAdapter versionsAdapter;
    protected final Set<Vector> fertilizedBlocks;

    protected PhysicsListener(@Nonnull PControlData data) {
        this.data = data;
        this.tags = data.tags();
        this.triggers = data.triggers();
        this.versionsAdapter = data.getVersionsAdapter();
        this.fertilizedBlocks = new HashSet<>();
        data.server().getScheduler().runTaskTimer(data.getPlugin(), this.fertilizedBlocks::clear, 1L, 1L);
    }

    protected void unrecognizedAction(@Nonnull Cancellable event, @Nonnull Location l, @Nonnull Object content) {
        if (l.getWorld() == null) throw new IllegalArgumentException("World cannot be null");
        if (!this.data.isActionAllowed(l.getWorld(), this.triggers.ALLOW_UNRECOGNIZED_ACTIONS)) {
            event.setCancelled(true);
        }
        if (this.data.isActionAllowed(l.getWorld(), this.triggers.DEBUG_MESSAGES)) {
            this.debugAction((Event) event, l, content);
        }
    }

    protected void debugAction(@Nonnull Event event, @Nonnull Location l, @Nonnull Object content) {
        if (l.getWorld() == null) throw new IllegalArgumentException("World cannot be null");

        String pos = l.getWorld().getName() + " " + l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ();

        Text text = this.data.getMessage("debug-message",
            "%action%", event.getClass().getSimpleName().replace("Event", ""),
            "%content%", content.toString(),
            "%pos%", pos
        );

        String command = "/pc tp " + pos;

        Text hover = this.data.getTextHelper().create("✔", CommonColor.YELLOW);

        this.data.announce(l.getWorld(), text.setClickCommand(command).setHoverText(hover));
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

    @Nonnull
    protected Set<Material> blocksSet(@Nonnull PControlTrigger trigger, @Nonnull Consumer<BlocksSet> consumer) {
        return BlocksSet.createPrimitive(trigger + " trigger", this.data.log(), consumer);
    }

    @Nonnull
    protected Set<EntityType> entitiesSet(@Nonnull PControlTrigger trigger, @Nonnull Consumer<EntityTypesSet> consumer) {
        return EntityTypesSet.create(trigger + " trigger", this.data.log(), consumer);
    }

    @Nonnull
    protected Set<TreeType> treesSet(@Nonnull PControlTrigger trigger, @Nonnull Consumer<TreeTypesSet> consumer) {
        return TreeTypesSet.create(trigger + " trigger", this.data.log(), consumer);
    }
}
