package com.runeterrareporter.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * Representation of a match data.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
// Using @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) with a record doesn't work as it 
// tries to set a final value. It is thus necessary to manually map each property.
public record MatchData(@JsonProperty("game_mode") GameMode gameMode,
                        @JsonProperty("game_type") GameType gameType,
                        @JsonProperty("game_start_time_utc") OffsetDateTime gameStartTime,
                        @JsonProperty("game_version") String gameVersion,
                        @JsonProperty("total_turn_count") int totalTurnCount,
                        @JsonProperty("players") Player[] players) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchData matchData = (MatchData) o;
        return totalTurnCount == matchData.totalTurnCount && gameMode.equals(matchData.gameMode) && gameType.equals(matchData.gameType) && gameStartTime.equals(matchData.gameStartTime) && gameVersion.equals(matchData.gameVersion) && Arrays.equals(players, matchData.players);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(gameMode, gameType, gameStartTime, gameVersion, totalTurnCount);
        result = 31 * result + Arrays.hashCode(players);
        return result;
    }

    @Override
    public String toString() {
        return "MatchData{" +
                "gameMode='" + gameMode + '\'' +
                ", gameType='" + gameType + '\'' +
                ", gameStartTime=" + gameStartTime +
                ", gameVersion='" + gameVersion + '\'' +
                ", totalTurnCount=" + totalTurnCount +
                ", players=" + Arrays.toString(players) +
                '}';
    }
}
