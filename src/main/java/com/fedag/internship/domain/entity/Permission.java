package com.fedag.internship.domain.entity;

public enum Permission {
    USER("user"),
    ADMIN("admin"),
    DELETED("deleted");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
