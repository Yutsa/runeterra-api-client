package com.runeterrareporter.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Builder for the HTTP request to the Runeterra API.
 * 
 * It handles building the URL and the headers with the API key.
 */
class RiotAPIRequestBuilder {
    private final UrlBuilder urlBuilder = UrlBuilder.withBaseUrl("https://europe.api.riotgames.com");
    
    private String apiKey;
    
    public static RiotAPIRequestBuilder createRequest() {
        return new RiotAPIRequestBuilder();
    }

    /**
     * Adds the API key to the headers.
     * @param apiKey A valid Riot API key to be used to query the Riot APIs.
     * @return An instance of the current {@link RiotAPIRequestBuilder}.
     */
    public RiotAPIRequestBuilder apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    /**
     * Adds a new part to the URL being constructed.
     * @param urlPart a part of URL to be added.
     * @return An instance of the current {@link RiotAPIRequestBuilder}.
     */
    public RiotAPIRequestBuilder addToUrl(String urlPart) {
        urlBuilder.addPath(urlPart);
        return this;
    }
    
    public RiotAPIResponse buildUrlConnection() throws IOException {
        URL url = urlBuilder.build();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("X-Riot-Token", apiKey);
        connection.setRequestProperty("Accept", "application/json");
        return new RiotAPIResponse(connection);
    }
}
