package com.runeterrareporter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runeterrareporter.match.MatchData;
import com.runeterrareporter.user.User;
import com.runeterrareporter.user.UserNotFoundException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Client for the Runeterra API from Riot Games.
 * 
 * An API key has to be provided for the call to be successful, for instance :
 * <pre>
 * new RunetteraAPIClient()
 *             .withAPIKey(apiKey)
 *             .getUser("Cithria", "EUW");
 * </pre>
 */
public class RunetteraAPIClient {

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

    /**
     * Retrieves a {@link User user's} information. 
     * @param gameName the in game name of the user.
     * @param tagLine the tag of the user, which is the string after the # in the LoR friendlist.
     * @return A {@link User} object with the user's informations, including the puuid needed for other endpoints.
     * @throws IOException if there is an issue with the connection.
     */
    public User getUser(String gameName, String tagLine) throws IOException {
        Request request = RiotAPIRequestBuilder.createRequest()
                .apiKey(apiKey)
                .addToUrl(RuneterraEndpoints.ENDPOINT_GET_ACCOUNT)
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

    /**
     * Retrieves the IDs of the last 20 matches of a {@link User user}.
     * @param user The user from which the history is requested.
     * @return A list of match IDs.
     * @throws IOException if there is an issue with the connection.
     */
    public List<String> getLatestMatchIDs(User user) throws IOException {
        Request request = RiotAPIRequestBuilder.createRequest()
                .apiKey(apiKey)
                .addToUrl(RuneterraEndpoints.ENDPOINT_GET_MATCH_BY_PUUID)
                .addToUrl(user.puuid())
                .addToUrl("ids")
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return Arrays.asList(mapper.readValue(response.body().string(), String[].class));
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves the {@link MatchData match data} from a match ID.
     * @param matchId The ID of the match, which can be obtained with {@link RunetteraAPIClient#getLatestMatchIDs(User)}.
     * @return A {@link MatchData} object with all the data from the given match.
     * @throws IOException if there is an issue with the connection.
     */
    public MatchData getMatchData(String matchId) throws IOException {
        Request request = RiotAPIRequestBuilder.createRequest()
                .apiKey(apiKey)
                .addToUrl(RuneterraEndpoints.ENDPOINT_GET_MATCHES)
                .addToUrl(matchId)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return mapper.readValue(response.body().string(), MatchData.class);
        }
        return null;
    }
}
