package com.runeterrareporter.client;

import java.net.MalformedURLException;
import java.net.URL;

class UrlBuilder {
    public static final String PATH_DELIMITER = "/";
    private String url;

    UrlBuilder(String url) {
        this.url = url;
    }

    public URL build() throws MalformedURLException {
        return new URL(url);
    }

    public static UrlBuilder withBaseUrl(String base) {
        return new UrlBuilder(base);
    }

    public UrlBuilder addPath(String path) {
        path = addStartingSlash(path);
        url = url + path;
        return this;
    }

    private String addStartingSlash(String path) {
        if (!path.startsWith("/")) {
            path = PATH_DELIMITER + path;
        }
        return path;
    }
}
