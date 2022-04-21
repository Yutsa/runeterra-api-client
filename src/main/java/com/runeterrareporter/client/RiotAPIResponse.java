package com.runeterrareporter.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

class RiotAPIResponse {
    private final HttpURLConnection connection;

    public RiotAPIResponse(HttpURLConnection connection) {
        this.connection = connection;
    }
    
    public boolean isSuccessful() throws IOException {
        return connection.getResponseCode() == 200;
    }
    
    public String getContent() throws IOException {
        return new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
    
    public String getMessage() throws IOException {
        return connection.getResponseMessage();
    }
    
    public int getCode() throws IOException {
        return connection.getResponseCode();
    }
}
