package ru.dymeth.pcontrol.api.set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import javax.annotation.Nonnull;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class EnumKeysSet<T extends Enum<T>> extends KeysSet<T> {
    @SafeVarargs
    public EnumKeysSet(@Nonnull Class<T> clazz, @Nonnull T... elements) {
        super(clazz, Sets.newEnumSet(Lists.newArrayList(elements), clazz));
    }

    @Nonnull
    public EnumKeysSet<T> add(@Nonnull Predicate<T> filter) {
        this.elements.addAll(Stream.of(this.clazz.getEnumConstants()).filter(filter).collect(Collectors.toList()));
        return this;
    }
}
