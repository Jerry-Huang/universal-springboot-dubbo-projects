package com.universal.spring.boot.metadata.util;

public class ReflectionUtils {

    public static Class<?> getClass(String className) {

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getInstance(Class<?> clazz) {

        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getInstance(String className) {

        return getInstance(getClass(className));
    }
}
