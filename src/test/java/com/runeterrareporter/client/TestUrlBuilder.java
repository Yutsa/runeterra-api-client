package com.runeterrareporter.client;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class TestUrlBuilder {

    @Test
    void should_build_a_url_only_with_a_base() throws MalformedURLException {
        URL url = UrlBuilder.withBaseUrl("http://test.com").build();
        assertThat(url).hasToString("http://test.com");
    }

    @Test
    void should_build_a_url_with_a_base_and_a_path_without_slashes() throws MalformedURLException {
        URL url = UrlBuilder.withBaseUrl("http://test.com")
                .addPath("api")
                .build();
        assertThat(url).hasToString("http://test.com/api");
    }

    @Test
    void should_build_a_url_with_a_base_and_a_path_with_a_slash_between_two_parts() throws MalformedURLException {
        URL url = UrlBuilder.withBaseUrl("http://test.com")
                .addPath("api/cards")
                .build();
        assertThat(url).hasToString("http://test.com/api/cards");
    }

    @Test
    void should_build_a_url_with_a_base_and_a_path_starting_with_a_slash() throws MalformedURLException {
        URL url = UrlBuilder.withBaseUrl("http://test.com")
                .addPath("/api/cards")
                .build();
        assertThat(url).hasToString("http://test.com/api/cards");
    }
}