package com.runeterrareporter.match;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.Test;

import static com.runeterrareporter.match.GameMode.CONSTRUCTED;
import static com.runeterrareporter.match.GameOutcome.LOSS;
import static com.runeterrareporter.match.GameOutcome.WIN;
import static com.runeterrareporter.match.GameType.RANKED;
import static com.runeterrareporter.match.OrderOfPlay.FIRST;
import static com.runeterrareporter.match.OrderOfPlay.SECOND;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class TestParsingMatchData {

    @Test
    void should_map_the_match_data() throws JsonProcessingException {
        JsonMapper mapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .addModule(new JavaTimeModule())
                .build();
        String JSON = """
                {
                    "game_mode": "Constructed",
                    "game_type": "Ranked",
                    "game_start_time_utc": "2022-04-10T08:49:42.1638989+00:00",
                    "game_version": "live_3_04_11",
                    "players": [
                      {
                        "puuid": "Ijz744loi9w2xsvT4FP-C-Cq_DRp-Q4nFFnEywhBzW_FQB-RDjNiVGmy6rnLq_eFVyyZRtVqP70O8g",
                        "deck_id": "0048c835-7b7a-4446-b565-aaf92d58ebec",
                        "deck_code": "CQCQCAYCAUAQIAQPAECQUOQCAUBAIGQFAEBAQCYMFQZAGAIEA6FACAIFAIKQEAICGE4QEAICAIFACAYCCQ",
                        "factions": [
                          "faction_Ionia_Name",
                          "faction_Shurima_Name"
                        ],
                        "game_outcome": "win",
                        "order_of_play": 1
                      },
                      {
                        "puuid": "lvBGJFSMcvjBPUwsTNgFF2scHhazTCzj8BULoPtAAbM2AlfPLgRrvt6D6mVGIurscdi36eS5fqyiFw",
                        "deck_id": "e8e25225-6cbc-446c-b95c-72afb772838f",
                        "deck_code": "CMBQEAICAYVAIBACAQCQQCIEAQDQGGQ4GMBQCAICBQAQIAQLAICAOXLSAIAQCARRAEBQEFA",
                        "factions": [
                          "faction_Ionia_Name",
                          "faction_Shurima_Name"
                        ],
                        "game_outcome": "loss",
                        "order_of_play": 0
                      }
                    ],
                    "total_turn_count": 25
                }""";
        MatchData matchData = mapper.readValue(JSON, MatchData.class);
        assertSoftly(softly -> {
            softly.assertThat(matchData).isNotNull();
            softly.assertThat(matchData.gameMode()).isEqualTo(CONSTRUCTED);
            softly.assertThat(matchData.gameType()).isEqualTo(RANKED);
            softly.assertThat(matchData.gameStartTime()).isEqualTo("2022-04-10T08:49:42.1638989+00:00");
            softly.assertThat(matchData.gameVersion()).isEqualTo("live_3_04_11");
            softly.assertThat(matchData.totalTurnCount()).isEqualTo(25);
            
            softly.assertThat(matchData.players()[0].deckCode()).isEqualTo("CQCQCAYCAUAQIAQPAECQUOQCAUBAIGQFAEBAQCYMFQZAGAIEA6FACAIFAIKQEAICGE4QEAICAIFACAYCCQ");
            softly.assertThat(matchData.players()[0].puuid()).isEqualTo("Ijz744loi9w2xsvT4FP-C-Cq_DRp-Q4nFFnEywhBzW_FQB-RDjNiVGmy6rnLq_eFVyyZRtVqP70O8g");
            softly.assertThat(matchData.players()[0].gameOutcome()).isEqualTo(WIN);
            softly.assertThat(matchData.players()[0].orderOfPlay()).isEqualTo(SECOND);

            softly.assertThat(matchData.players()[1].deckCode()).isEqualTo("CMBQEAICAYVAIBACAQCQQCIEAQDQGGQ4GMBQCAICBQAQIAQLAICAOXLSAIAQCARRAEBQEFA");
            softly.assertThat(matchData.players()[1].puuid()).isEqualTo("lvBGJFSMcvjBPUwsTNgFF2scHhazTCzj8BULoPtAAbM2AlfPLgRrvt6D6mVGIurscdi36eS5fqyiFw");
            softly.assertThat(matchData.players()[1].gameOutcome()).isEqualTo(LOSS);
            softly.assertThat(matchData.players()[1].orderOfPlay()).isEqualTo(FIRST);
        });
    }
}