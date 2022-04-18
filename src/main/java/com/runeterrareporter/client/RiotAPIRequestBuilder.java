package com.runeterrareporter.client;

import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Builder for the HTTP request to the Runeterra API.
 * 
 * It handles building the URL and the headers with the API key.
 */
class RiotAPIRequestBuilder {
    private final Request.Builder requestBuilder = new Request.Builder()
            .addHeader("Accept", "application/json; q=0.5");

    private final HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
            .scheme("https")
            .host("europe.api.riotgames.com");

    public static RiotAPIRequestBuilder createRequest() {
        return new RiotAPIRequestBuilder();
    }

    /**
     * Adds the API key to the headers.
     * @param apiKey A valid Riot API key to be used to query the Riot APIs.
     * @return An instance of the current {@link RiotAPIRequestBuilder}.
     */
    public RiotAPIRequestBuilder apiKey(String apiKey) {
        requestBuilder.addHeader("X-Riot-Token", apiKey);
        return this;
    }

    /**
     * Adds a new part to the URL being constructed.
     * @param urlPart a part of URL to be added.
     * @return An instance of the current {@link RiotAPIRequestBuilder}.
     */
    public RiotAPIRequestBuilder addToUrl(String urlPart) {
        urlBuilder.addPathSegments(urlPart);
        return this;
    }

    /**
     * Builds the {@link Request} object to be used to query the Riot API.
     * @return a {@link Request} object to be used to query the Riot API.
     */
    public Request build() {
        URL url = urlBuilder.build().url();
        requestBuilder.url(url);
        return requestBuilder.build();
    }
}
