package com.runeterrareporter.client;

/**
 * Exception class that handles unexpected error code with the Runeterra API.
 */
public class UnknownAPIIssueException extends RuntimeException {
    public UnknownAPIIssueException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "An unknown error occured with the Riot API : \n" + super.getMessage();
    }
}
