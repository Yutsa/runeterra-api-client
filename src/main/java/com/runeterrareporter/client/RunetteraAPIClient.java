package com.runeterrareporter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runeterrareporter.user.User;
import com.runeterrareporter.user.UserNotFoundException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RunetteraAPIClient {

    public static final String API_KEY = "RGAPI-a0e45947-3f97-4036-91ab-43cf987e0d89";
    public static final String ENDPOINT_GET_ACCOUNT = "riot/account/v1/accounts/by-riot-id";

    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public RunetteraAPIClient(OkHttpClient httpClient, ObjectMapper mapper) {
        this.client = httpClient;
        this.mapper = mapper;
    }

    public User getUser(String gameName, String tagLine) throws IOException {
        Request request = RiotAPIRequestBuilder.createRequest()
                .apiKey(API_KEY)
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
    
    public String getLatestMatchIDs(User user) throws IOException {
        Request request = RiotAPIRequestBuilder.createRequest()
                .apiKey(API_KEY)
                .addToUrl("lor/match/v1/matches/by-puuid")
                .addToUrl(user.puuid())
                .addToUrl("ids")
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }
        return "";
    }
}
