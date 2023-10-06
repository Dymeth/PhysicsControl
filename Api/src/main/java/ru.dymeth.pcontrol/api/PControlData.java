package ru.dymeth.pcontrol.api;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.plugin.Plugin;
import ru.dymeth.pcontrol.CustomTags;
import ru.dymeth.pcontrol.VersionsAdapter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public interface PControlData {
    @Nonnull
    Plugin getPlugin();

    @Nonnull
    Set<EntityType> getRemovableProjectileTypes();

    @Nonnull
    String getMessage(@Nonnull String key, @Nonnull String... placeholders);

    String getTriggerName(@Nonnull PControlTrigger trigger);

    @Nonnull
    String getCategoryName(@Nonnull PControlCategory category);

    boolean hasVersion(int version);

    @Deprecated // TODO Mark trigger as available while filling rules on plugin startup
    boolean isTriggerSupported(@Nonnull PControlTrigger trigger);

    void cancelIfDisabled(@Nonnull BlockEvent event, @Nonnull PControlTrigger trigger);

    void cancelIfDisabled(@Nonnull Cancellable event, @Nonnull World world, @Nonnull PControlTrigger trigger);

    boolean isActionAllowed(@Nonnull World world, @Nonnull PControlTrigger trigger);

    void announce(@Nullable World world, @Nonnull String plain, @Nullable BaseComponent component);

    @Nonnull
    CustomTags getCustomTags();

    @Nonnull
    VersionsAdapter getVersionsAdapter();
}
