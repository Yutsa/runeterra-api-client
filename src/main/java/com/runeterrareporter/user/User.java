package com.runeterrareporter.user;

/**
 * Representation of a Runeterra user or player with its puuid which is needed for
 * other API calls.
 * 
 * The puuid is a unique ID for a user.
 */
public record User(String puuid,
                   String gameName,
                   String tagLine) {
}
