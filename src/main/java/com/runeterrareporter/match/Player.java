package com.runeterrareporter.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Player(String puuid,
                     @JsonProperty("deck_code") String deckCode,
                     @JsonProperty("game_outcome") GameOutcome gameOutcome,
                     @JsonProperty("order_of_play") OrderOfPlay orderOfPlay) {
}
