package com.runeterrareporter.user;

public class UserNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "No user was found.";
    }
}
