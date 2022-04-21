package com.runeterrareporter.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representation of a match data.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
// Using @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) with a record doesn't work as it 
// tries to set a final value. It is thus necessary to manually map each property.
public record MatchData(@JsonProperty("game_mode") String gameMode,
                        @JsonProperty("game_type") String gameType) {

}
