package com.runeterrareporter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runeterrareporter.user.User;
import com.runeterrareporter.user.UserNotFoundException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RunetteraAPIClient {

    public static final String ENDPOINT_GET_ACCOUNT = "riot/account/v1/accounts/by-riot-id";

    private String apiKey;
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public RunetteraAPIClient(OkHttpClient httpClient, ObjectMapper mapper, String apiKey) {
        this.client = httpClient;
        this.mapper = mapper;
        this.apiKey = apiKey;
    }
    
    public RunetteraAPIClient() {
        this(new OkHttpClient(), new ObjectMapper());
    }

    public RunetteraAPIClient(OkHttpClient httpClient, ObjectMapper mapper) {
        this(httpClient, mapper, null);
    }
    
    public RunetteraAPIClient withAPIKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public User getUser(String gameName, String tagLine) throws IOException {
        Request request = RiotAPIRequestBuilder.createRequest()
                .apiKey(apiKey)
                .addToUrl(ENDPOINT_GET_ACCOUNT)
                .addToUrl(gameName)
                .addToUrl(tagLine)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return mapper.readValue(response.body().string(), User.class);
        } else if (response.code() == 404) {
            throw new UserNotFoundException();
        }
        throw new UnknownAPIIssueException(response.message());
    }
    
    public List<String> getLatestMatchIDs(User user) throws IOException {
        Request request = RiotAPIRequestBuilder.createRequest()
                .apiKey(apiKey)
                .addToUrl("lor/match/v1/matches/by-puuid")
                .addToUrl(user.puuid())
                .addToUrl("ids")
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return Arrays.asList(mapper.readValue(response.body().string(), String[].class));
        }
        return Collections.emptyList();
    }
}
