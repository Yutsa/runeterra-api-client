package com.runeterrareporter.user;

/**
 * Exception for when a User is searched using the Runeterra API but that user does not exist.
 */
public class UserNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "No user was found.";
    }
}
