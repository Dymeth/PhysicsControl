package ru.dymeth.pcontrol.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CustomMaterialSet {
    private final Set<Material> materials;

    public CustomMaterialSet(@Nonnull Predicate<Material> filter) {
        this(Stream.of(Material.values()).filter(filter).collect(Collectors.toList()));
    }

    public CustomMaterialSet(@Nonnull Material... materials) {
        this(Lists.newArrayList(materials));
    }

    public CustomMaterialSet(@Nonnull Collection<Material> materials) {
        this.materials = Sets.newEnumSet(materials, Material.class);
    }

    @Nonnull
    public CustomMaterialSet add(@Nonnull Collection<Material> materials) {
        this.materials.addAll(materials);
        return this;
    }

    @Nonnull
    public CustomMaterialSet addBlocks(@Nonnull String... materialNames) {
        this.materials.addAll(BukkitUtils.matchBlockMaterials(null, materialNames));
        return this;
    }

    @Nonnull
    public CustomMaterialSet addItems(@Nonnull String... materialNames) {
        this.materials.addAll(BukkitUtils.matchBlockMaterials(null, materialNames));
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
