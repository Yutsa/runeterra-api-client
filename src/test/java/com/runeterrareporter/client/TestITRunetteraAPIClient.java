package com.runeterrareporter.client;

import com.runeterrareporter.match.MatchData;
import com.runeterrareporter.user.User;
import com.runeterrareporter.user.UserNotFoundException;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.runeterrareporter.match.GameMode.CONSTRUCTED;
import static com.runeterrareporter.match.GameOutcome.LOSS;
import static com.runeterrareporter.match.GameOutcome.WIN;
import static com.runeterrareporter.match.GameType.RANKED;
import static com.runeterrareporter.match.OrderOfPlay.FIRST;
import static com.runeterrareporter.match.OrderOfPlay.SECOND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;


class TestITRunetteraAPIClient {

    private static final String PUUID = "Ijz744loi9w2xsvT4FP-C-Cq_DRp-Q4nFFnEywhBzW_FQB-RDjNiVGmy6rnLq_eFVyyZRtVqP70O8g";
    private final String apiKey = getAPIKey();

    private String getAPIKey() {
        try (InputStream input = this.getClass().getResourceAsStream("/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("API_KEY");
        } catch (IOException ex) {
            return "";
        }
    }

    @Test
    void should_get_puuid_for_the_Yutsa_user() throws IOException {
        User user = new RunetteraAPIClient()
                .withAPIKey(apiKey)
                .getUser("Yutsa", "EUW");
        assertThat(user.puuid()).isEqualTo(PUUID);
        assertThat(user.gameName()).isEqualTo("Yutsa");
        assertThat(user.tagLine()).isEqualTo("EUW");
    }

    @Test
    void should_throw_UserNotFoundException_for_a_unknown_user() {
        RunetteraAPIClient runetteraAPIClient = new RunetteraAPIClient()
                .withAPIKey(apiKey);
        assertThatThrownBy(() -> runetteraAPIClient.getUser("jjreifjr", "EUW"))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void should_get_the_last_match_ids_from_a_user() throws IOException {
        RunetteraAPIClient runetteraAPIClient = new RunetteraAPIClient().withAPIKey(apiKey);
        User user = runetteraAPIClient.getUser("Yutsa", "EUW");
        var latestMatchIDs = runetteraAPIClient.getLatestMatchIDs(user);
        assertThat(latestMatchIDs).isNotEmpty().hasSize(20);
    }

    @Test
    void should_get_the_match_data() throws IOException {
        MatchData matchData = new RunetteraAPIClient().withAPIKey(apiKey)
                .getMatchData("1b538875-e19b-4150-9fc3-ae083ed300d9");
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