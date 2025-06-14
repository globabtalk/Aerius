package com.Voice.Aerius.Auth.enums;

public enum Role {

   USER("user"),ADMIN("admin"),SUPER_ADMIN("super_admin");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
