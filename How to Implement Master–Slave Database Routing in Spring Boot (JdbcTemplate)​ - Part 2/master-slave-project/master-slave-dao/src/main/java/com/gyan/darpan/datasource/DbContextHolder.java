package com.gyan.darpan.datasource;

import com.gyan.darpan.enums.DbType;

public class DbContextHolder {
    private static final ThreadLocal<DbType> context = new ThreadLocal<>();

    public static void set(DbType dbType) {
        context.set(dbType);
    }

    public static void clear() {
        context.remove();
    }

    public static DbType get() {
        return context.get();
    }
}
