package com.runeterrareporter.client;

/**
 * Lists all the endpoints for the Runeterra API.
 */
class RuneterraEndpoints {
    public static final String ENDPOINT_GET_ACCOUNT = "riot/account/v1/accounts/by-riot-id";
    public static final String ENDPOINT_GET_MATCH_BY_PUUID = "lor/match/v1/matches/by-puuid";
    public static final String ENDPOINT_GET_MATCHES = "lor/match/v1/matches";
    
    private RuneterraEndpoints() {
    }
}
