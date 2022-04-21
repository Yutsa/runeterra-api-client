package com.runeterrareporter.client;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.runeterrareporter.match.MatchData;
import com.runeterrareporter.user.User;
import com.runeterrareporter.user.UserNotFoundException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.runeterrareporter.client.RuneterraEndpoints.*;

/**
 * Client for the Runeterra API from Riot Games.
 * <p>
 * An API key has to be provided for the call to be successful, for instance :
 * <pre>
 * new RunetteraAPIClient()
 *             .withAPIKey(apiKey)
 *             .getUser("Cithria", "EUW");
 * </pre>
 */
public class RunetteraAPIClient {

    private String apiKey;
    private final ObjectMapper mapper;

    public RunetteraAPIClient(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public RunetteraAPIClient() {
        this(JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .addModule(new JavaTimeModule())
                .build());
    }

    public RunetteraAPIClient withAPIKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    /**
     * Retrieves a {@link User user's} information.
     *
     * @param gameName the in game name of the user.
     * @param tagLine  the tag of the user, which is the string after the # in the LoR friendlist.
     * @return A {@link User} object with the user's informations, including the puuid needed for other endpoints.
     * @throws IOException if there is an issue with the connection.
     */
    public User getUser(String gameName, String tagLine) throws IOException {
        var response = RiotAPIRequestBuilder.createRequest()
                .apiKey(apiKey)
                .addToUrl(ENDPOINT_GET_ACCOUNT)
                .addToUrl(gameName)
                .addToUrl(tagLine)
                .buildUrlConnection();
        if (response.isSuccessful()) {
            return mapper.readValue(response.getContent(), User.class);
        } else if (response.getCode() == 404) {
            throw new UserNotFoundException();
        }
        throw new UnknownAPIIssueException(response.getMessage());
    }

    /**
     * Retrieves the IDs of the last 20 matches of a {@link User user}.
     *
     * @param user The user from which the history is requested.
     * @return A list of match IDs.
     * @throws IOException if there is an issue with the connection.
     */
    public List<String> getLatestMatchIDs(User user) throws IOException {
        var response = RiotAPIRequestBuilder.createRequest()
                .apiKey(apiKey)
                .addToUrl(ENDPOINT_GET_MATCH_BY_PUUID)
                .addToUrl(user.puuid())
                .addToUrl("ids")
                .buildUrlConnection();
        if (response.isSuccessful()) {
            return Arrays.asList(mapper.readValue(response.getContent(), String[].class));
        }
        throw new UnknownAPIIssueException(response.getMessage());
    }

    /**
     * Retrieves the {@link MatchData match data} from a match ID.
     *
     * @param matchId The ID of the match, which can be obtained with {@link RunetteraAPIClient#getLatestMatchIDs(User)}.
     * @return A {@link MatchData} object with all the data from the given match.
     * @throws IOException if there is an issue with the connection.
     */
    public MatchData getMatchData(String matchId) throws IOException {
        var response = RiotAPIRequestBuilder.createRequest()
                .apiKey(apiKey)
                .addToUrl(ENDPOINT_GET_MATCHES)
                .addToUrl(matchId)
                .buildUrlConnection();

        if (response.isSuccessful()) {
            return mapper.treeToValue(mapper.readTree(response.getContent()).at("/info"), MatchData.class);
        }
        throw new UnknownAPIIssueException(response.getMessage());
    }
}
