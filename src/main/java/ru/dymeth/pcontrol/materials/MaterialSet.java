package ru.dymeth.pcontrol.materials;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MaterialSet {
    private final NamespacedKey key;
    private final Set<Material> materials;

    public MaterialSet(@Nonnull String key, @Nonnull Predicate<Material> filter) {
        this(key, Stream.of(Material.values()).filter(filter).collect(Collectors.toList()));
    }

    public MaterialSet(@Nonnull String key, @Nonnull Material... materials) {
        this(key, Lists.newArrayList(materials));
    }

    public MaterialSet(@Nonnull String key, @Nonnull Collection<Material> materials) {
        this.key = NamespacedKey.minecraft(key);
        this.materials = Sets.newEnumSet(materials, Material.class);
    }

    @Nonnull
    public NamespacedKey getKey() {
        return this.key;
    }

    @Nonnull
    public MaterialSet add(@Nonnull Collection<Material> materials) {
        this.materials.addAll(materials);
        return this;
    }

    @Nonnull
    public Set<Material> getValues() {
        return this.materials;
    }

    public boolean isTagged(@Nonnull Material material) {
        return this.materials.contains(material);
    }
}
