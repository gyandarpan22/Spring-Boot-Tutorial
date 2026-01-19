package com.gyan.darpan.datasource;

public class TenantContextHolder {
    private static ThreadLocal<String> context = new ThreadLocal<>();

    public static void set(String tenantId) {
        context.set(tenantId);
    }

    public static String get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
