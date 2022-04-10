package com.runeterrareporter.client;

import com.runeterrareporter.user.User;
import com.runeterrareporter.user.UserNotFoundException;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


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
        assertThatThrownBy(() -> new RunetteraAPIClient()
                .withAPIKey(apiKey)
                .getUser("jjreifjr", "EUW"))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void should_get_the_last_match_ids_from_a_user() throws IOException {
        RunetteraAPIClient runetteraAPIClient = new RunetteraAPIClient().withAPIKey(apiKey);
        User user = runetteraAPIClient.getUser("Yutsa", "EUW");
        var latestMatchIDs = runetteraAPIClient.getLatestMatchIDs(user);
        assertThat(latestMatchIDs).isNotEmpty();
        assertThat(latestMatchIDs).hasSize(20);
    }
}