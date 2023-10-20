package ru.dymeth.pcontrol.data;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.plugin.Plugin;
import ru.dymeth.pcontrol.VersionsAdapter;
import ru.dymeth.pcontrol.text.Text;
import ru.dymeth.pcontrol.text.TextHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public interface PControlData {
    @Nonnull
    Plugin getPlugin();

    @Nonnull
    Set<EntityType> getRemovableProjectileTypes();

    @Nonnull
    Text getMessage(@Nonnull String key, @Nonnull String... placeholders);

    String getTriggerName(@Nonnull PControlTrigger trigger);

    @Nonnull
    String getCategoryName(@Nonnull PControlCategory category);

    boolean hasVersion(int version);

    void cancelIfDisabled(@Nonnull BlockEvent event, @Nonnull PControlTrigger trigger);

    void cancelIfDisabled(@Nonnull Cancellable event, @Nonnull World world, @Nonnull PControlTrigger trigger);

    boolean isActionAllowed(@Nonnull World world, @Nonnull PControlTrigger trigger);

    void announce(@Nullable World world, @Nonnull Text text);

    @Nonnull
    CustomTags getCustomTags();

    @Nonnull
    VersionsAdapter getVersionsAdapter();

    @Nonnull
    TextHelper getTextHelper();
}
