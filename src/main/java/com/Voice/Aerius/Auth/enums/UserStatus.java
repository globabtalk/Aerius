package com.Voice.Aerius.Auth.enums;


public enum UserStatus {
    UNVERIFIED,
    VERIFIED,
    SUSPENDED;

private final String UserStatus;

UserStatus(String userStatus) {
    this.UserStatus = userStatus;
}

@Override
public String toString() {
    return userStatus;
}
