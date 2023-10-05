package ru.dymeth.pcontrol.api.set;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public abstract class KeysSet<T> implements Iterable<T> {
    final Class<T> clazz;
    final Set<T> elements;

    public KeysSet(@Nonnull Class<T> clazz, @Nonnull Set<T> elements) {
        this.clazz = clazz;
        this.elements = elements;
    }

    @Nonnull
    public KeysSet<T> add(@Nonnull Collection<T> elements) {
        this.elements.addAll(elements);
        return this;
    }

    @Nonnull
    public KeysSet<T> add(@Nonnull KeysSet<T> elements) {
        this.elements.addAll(elements.elements);
        return this;
    }

    @SafeVarargs
    @Nonnull
    public final KeysSet<T> add(@Nonnull T... elements) {
        this.elements.addAll(Arrays.asList(elements));
        return this;
    }

    @Nonnull
    public Set<T> getValues() {
        return this.elements;
    }

    public boolean isTagged(@Nonnull T element) {
        return this.elements.contains(element);
    }

    @Override
    @Nonnull
    public Iterator<T> iterator() {
        return this.elements.iterator();
    }
}
