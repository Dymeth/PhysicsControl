package ru.dymeth.pcontrol.api.set;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public abstract class CustomSet<T> {
    final Class<T> clazz;
    private final Set<T> elements;

    public CustomSet(@Nonnull Class<T> clazz, @Nonnull Set<T> elements) {
        this.clazz = clazz;
        this.elements = elements;
    }

    @Nonnull
    public CustomSet<T> add(@Nonnull Collection<T> elements) {
        this.elements.addAll(elements);
        return this;
    }

    @Nonnull
    public CustomSet<T> add(@Nonnull CustomSet<T> elements) {
        this.add(elements.getValues());
        return this;
    }

    @SafeVarargs
    @Nonnull
    public final CustomSet<T> add(@Nonnull T... elements) {
        this.add(Arrays.asList(elements));
        return this;
    }

    @Nonnull
    public Set<T> getValues() {
        return Collections.unmodifiableSet(this.elements);
    }

    public boolean isTagged(@Nonnull T element) {
        return this.elements.contains(element);
    }
}
