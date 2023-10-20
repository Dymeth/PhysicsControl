package ru.dymeth.pcontrol.util;

import javax.annotation.Nonnull;

public class ReflectionUtils {

    public static boolean isClassPresent(@Nonnull String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean isMethodPresent(@Nonnull Class<?> clazz, @Nonnull String name, @Nonnull Class<?>... parameterTypes) {
        try {
            clazz.getDeclaredMethod(name, parameterTypes);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

}
