package ru.dymeth.pcontrol.set.parser;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import ru.dymeth.pcontrol.data.CustomTags;
import ru.dymeth.pcontrol.data.PControlData;
import ru.dymeth.pcontrol.set.CustomEnumSet;
import ru.dymeth.pcontrol.set.EntityTypesSet;
import ru.dymeth.pcontrol.set.TreeTypesSet;
import ru.dymeth.pcontrol.set.material.BlockTypesSet;
import ru.dymeth.pcontrol.set.material.ItemTypesSet;
import ru.dymeth.pcontrol.util.ReflectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TypesSetsParser {
    public static final String CUSTOM_TAG_PREFIX = "physicscontrol_";

    private final @Nonnull PControlData data;
    private final @Nonnull CustomTags tags;
    private final boolean bukkitTagsSupported;

    public TypesSetsParser(@Nonnull PControlData data, @Nonnull CustomTags tags) {
        this.data = data;
        this.tags = tags;
        this.bukkitTagsSupported = ReflectionUtils.isClassPresent("org.bukkit.Tag");
    }

    @Nonnull
    public Consumer<BlockTypesSet> createBlockTypesParser(@Nonnull List<String> elements) {
        return this.createSetConsumer(elements, "blocks", Material.class);
    }

    @Nonnull
    public Consumer<EntityTypesSet> createEntityTypesParser(@Nonnull List<String> elements) {
        return this.createSetConsumer(elements, "entity_types", EntityType.class);
    }

    @Nonnull
    public Consumer<ItemTypesSet> createItemTypesParser(@Nonnull List<String> elements) {
        return this.createSetConsumer(elements, "items", Material.class);
    }

    @Nonnull
    public Consumer<TreeTypesSet> createTreeTypesParser(@Nonnull List<String> elements) {
        return this.createSetConsumer(elements, "tree_types", TreeType.class);
    }

    private final Map<String, Boolean> supportedVersionsCache = new HashMap<>();

    @Nonnull
    private <E extends Enum<E>, T extends CustomEnumSet<E, ?>> Consumer<T> createSetConsumer(
        @Nonnull List<String> elements,
        @Nonnull String tagRegistryName,
        @Nonnull Class<E> typeClass
    ) {
        return set -> {
            for (String elementRaw : elements) {
                String[] args = elementRaw.split(" ");
                if (args.length < 1 || args.length > 2) {
                    throw new IllegalArgumentException("Wrong element format: " + elementRaw);
                }
                String elementOrTagName = args[0];

                if (args.length > 1 && !this.isVersionSupported(args[1])) continue;

                if (elementOrTagName.startsWith("#")) {

                    Collection<E> values;

                    elementOrTagName = elementOrTagName.substring("#".length());
                    if (elementOrTagName.startsWith(CUSTOM_TAG_PREFIX)) { // custom
                        elementOrTagName = elementOrTagName.substring(CUSTOM_TAG_PREFIX.length());

                        values = this.tags.getTag(elementOrTagName, typeClass);
                        if (values == null) {
                            throw new IllegalArgumentException("Tag \"" + elementOrTagName + "\" not found for element: " + elementRaw);
                        }
                    } else { // bukkit
                        if (!this.bukkitTagsSupported) {
                            throw new IllegalArgumentException("Tags in unsupported on current server version");
                        }

                        if (!Keyed.class.isAssignableFrom(typeClass)) {
                            throw new IllegalArgumentException("Class " + typeClass + " isn't supported vanilla tags");
                        }
                        //noinspection unchecked
                        Class<? extends Keyed> keyedClass = (Class<? extends Keyed>) typeClass;
                        Tag<? extends Keyed> tag = Bukkit.getTag(tagRegistryName, NamespacedKey.minecraft(elementOrTagName.toLowerCase()), keyedClass);
                        if (tag == null) {
                            throw new IllegalArgumentException("Tag \"" + elementOrTagName + "\" not found for element: " + elementRaw);
                        }
                        //noinspection unchecked
                        values = tag.getValues().stream()
                            .map(keyed -> (E) keyed)
                            .collect(Collectors.toList());
                    }

                    set.addPrimitive(values);
                } else {
                    set.add(elementOrTagName);
                }
            }
        };
    }

    @SuppressWarnings("RedundantIfStatement")
    private boolean isVersionSupported(@Nonnull String version) {
        Boolean supported = this.supportedVersionsCache.get(version);
        if (supported != null) return supported;

        String[] args = version.split("-");
        if (args.length != 2) throw new IllegalArgumentException("Wrong version format: " + version);

        String min = args[0];
        String max = args[1];

        if (!min.equals("*") && !this.hasVersion(min, true)) supported = false;
        else if (!max.equals("*") && this.hasVersion(max, false)) supported = false;
        else supported = true;

        this.supportedVersionsCache.put(version, supported);

        return supported;
    }

    private boolean hasVersion(@Nonnull String version, @Nullable Boolean resultIfCurrentVersion) {
        String[] args = version.split("\\.");
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException("Wrong version format: " + version);
        }
        try {
            int majorVersion = Integer.parseInt(args[0]);
            int minorVersion = Integer.parseInt(args[1]);
            int patchVersion = args.length < 3 ? 0 : Integer.parseInt(args[2]);

            if (resultIfCurrentVersion != null && this.data.isVersion(majorVersion, minorVersion, patchVersion)) {
                return resultIfCurrentVersion;
            }
            return this.data.hasVersion(majorVersion, minorVersion, patchVersion);
        } catch (Exception e) {
            throw new IllegalArgumentException("Wrong version format: " + version);
        }
    }
}
