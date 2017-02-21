package com.universal.util;

import org.apache.commons.lang3.ObjectUtils;

import com.universal.session.AbstractSession;
import com.universal.session.Session;

import java.io.Serializable;
import java.lang.reflect.Constructor;


public class SessionUtils {

    private static final String USER_BEAN_KEY = "USER-BEAN";
    private static final String USER_BEAN_ID_KEY = "USER-BEAN-ID";

    public static Session current() {
        return AbstractSession.get();
    }

    public static void current(final Session session) {
        AbstractSession.set(session);
    }

    public static Session generate(final String className, final Object storage) throws IllegalArgumentException {

        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor(storage.getClass());

            return (Session) constructor.newInstance(storage);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T extends Serializable> void cacheUser(final T value) {

        current().put(USER_BEAN_KEY, value);
    }

    public static <T extends Serializable> T findUser() {

        return current().get(USER_BEAN_KEY);
    }

    public static void cacheUserId(final int value) {

        current().put(USER_BEAN_ID_KEY, value);
    }

    public static int findUserId() {

        Integer userId = current().get(USER_BEAN_ID_KEY);
        return ObjectUtils.defaultIfNull(userId, 0);
    }
}
