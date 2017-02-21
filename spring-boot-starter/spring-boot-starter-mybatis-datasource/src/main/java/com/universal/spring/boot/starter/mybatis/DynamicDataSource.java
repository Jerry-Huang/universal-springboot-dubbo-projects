package com.universal.spring.boot.starter.mybatis;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<>();

    public static String getDataSource() {
        return dataSourceHolder.get();
    }

    public static void setDataSource(String name) {
        dataSourceHolder.set(name);
    }

    @Override
    protected Object determineCurrentLookupKey() {

        return dataSourceHolder.get();
    }
}
