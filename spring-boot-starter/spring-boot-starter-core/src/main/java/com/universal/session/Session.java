package com.universal.session;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface Session extends Cloneable {

    Set<String> keys();

    void remove(final String key);

    String getId();

    void setId(final String id);

    void invalidate();

    <T extends Serializable> T get(final String key);

    void setTimeout(final long timeout, final TimeUnit timeUnit);

    <T extends Serializable> void put(final String key, final T value);

    <T extends Serializable> T get(final String key, final Class<T> clazz);

    Session clone() throws CloneNotSupportedException;
}
