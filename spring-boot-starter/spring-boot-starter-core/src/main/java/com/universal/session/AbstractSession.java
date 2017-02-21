package com.universal.session;

import java.util.concurrent.TimeUnit;

public abstract class AbstractSession implements Session {

    
    private String id;
    private Object storage;
    protected long timeout = 30;
    protected TimeUnit timeUnit = TimeUnit.DAYS;

    private static final ThreadLocal<Session> sessionHolder = new ThreadLocal<>();

    public static Session get() {

        return sessionHolder.get();
    }

    public static void set(final Session session) {
        sessionHolder.set(session);
    }

    public AbstractSession(final Object storage) {

        this.storage = storage;
    }
    
    protected Object storage() {
        
        return this.storage;
    }
    
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(final String id) {
        this.id = id;
    }
    
    @Override
    public void setTimeout(long timeout, TimeUnit timeUnit) {

        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    @Override
    public Session clone() throws CloneNotSupportedException {
        return (Session) super.clone();
    }
}
