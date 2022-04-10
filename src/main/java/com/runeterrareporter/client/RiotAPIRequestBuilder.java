package com.runeterrareporter.client;

import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class RiotAPIRequestBuilder {
       
    private final Request.Builder requestBuilder = new Request.Builder()
            .addHeader("Accept", "application/json; q=0.5");
    
    private final HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
            .scheme("https")
            .host("europe.api.riotgames.com");

    public static RiotAPIRequestBuilder createRequest() {
        return new RiotAPIRequestBuilder();
    }
    
    public RiotAPIRequestBuilder apiKey(String apiKey) {
        requestBuilder.addHeader("X-Riot-Token", apiKey);
        return this;
    }
    
    public RiotAPIRequestBuilder addToUrl(String urlPart) {
        urlBuilder.addPathSegments(urlPart);
        return this;
    }

    public Request build() {
        URL url = urlBuilder.build().url();
        requestBuilder.url(url);
        return requestBuilder.build();
    }
}
