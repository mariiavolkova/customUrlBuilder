package com.volkova;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class UrlBuilder {
    private String scheme;
    private String host;
    private int port = -1;
    private String path = "";
    private Map<String, String> queryParams = new HashMap<>();

    public UrlBuilder() {
    }

    public static UrlBuilder newInstance() {
        return new UrlBuilder();
    }

    public UrlBuilder https(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public UrlBuilder host(String host) {
        this.host = host;
        return this;
    }

    public UrlBuilder port(int port) {
        this.port = port;
        return this;
    }

    public UrlBuilder path(String path) {
        this.path = path;
        return this;
    }

    public UrlBuilder queryParams() {
        this.queryParams.put("key1", "value1");
        return this;
    }

    public URI build() throws URISyntaxException {
        validateState();

        StringBuilder uriString = new StringBuilder();
        uriString.append(scheme).append("://").append(host);

        if (port > 0) {
            uriString.append(":").append(port);
        }

        uriString.append("/").append(path);

        if (!queryParams.isEmpty()) {
            uriString.append("?");
            queryParams.forEach((key, value) -> uriString.append(key).append("=").append(value).append("&"));
            uriString.deleteCharAt(uriString.length() - 1); // Remove the trailing "&"
        }
            return new URI(uriString.toString());
    }

    private void validateState() {
        if (host == null) {
            throw new IllegalStateException("Host must be set before building URI.");
        }
    }

    public static void main(String[] args) throws URISyntaxException,
            MalformedURLException {
        try {

            URI uri1 = UrlBuilder.newInstance()
                    .https("https")
                    .host("codility.com")
                    .port(8080)
                    .path("test/hello/world")
                    .queryParams()
                    .build();

            URI uri2 = UrlBuilder.newInstance()
                    .https("https")
                    .host("codility.com")
                    .port(8080)
                    .build();

            System.out.println(uri1.toString());
            System.out.println(uri2.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}