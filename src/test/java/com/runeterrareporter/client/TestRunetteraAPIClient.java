package com.runeterrareporter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runeterrareporter.user.User;
import com.runeterrareporter.user.UserNotFoundException;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import okhttp3.OkHttpClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class TestRunetteraAPIClient {

    private static final String PUUID = "Ijz744loi9w2xsvT4FP-C-Cq_DRp-Q4nFFnEywhBzW_FQB-RDjNiVGmy6rnLq_eFVyyZRtVqP70O8g";

    @Test
    void should_get_puuid_for_the_Yutsa_user() throws IOException {
        User user = new RunetteraAPIClient(new OkHttpClient(), new ObjectMapper()).getUser("Yutsa", "EUW");
        assertThat(user.puuid()).isEqualTo(PUUID);
        assertThat(user.gameName()).isEqualTo("Yutsa");
        assertThat(user.tagLine()).isEqualTo("EUW");
    }

    @Test
    void should_throw_UserNotFoundException_for_a_unknown_user() {
        assertThatThrownBy(() -> new RunetteraAPIClient(new OkHttpClient(), new ObjectMapper()).getUser("jjreifjr", "EUW"))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void should_get_the_last_match_ids_from_a_user() throws IOException {
        RunetteraAPIClient runetteraAPIClient = new RunetteraAPIClient(new OkHttpClient(), new ObjectMapper());
        User user = runetteraAPIClient.getUser("Yutsa", "EUW");
        String latestMatchIDs = runetteraAPIClient.getLatestMatchIDs(user);
        assertThat(latestMatchIDs).isNotNull();
    }
}