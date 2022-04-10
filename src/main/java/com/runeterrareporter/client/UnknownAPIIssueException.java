package com.runeterrareporter.client;

public class UnknownAPIIssueException extends RuntimeException {

    public UnknownAPIIssueException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "An unknown error occured with the Riot API : \n" + super.getMessage();
    }
}
