package com.github.sksharan.parthenon.plugin.network;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class NetworkUtils {
    private final CloseableHttpClient httpClient;
    private final Provider<HttpPost> httpPostProvider;
    private final ObjectMapper objectMapper;

    @Inject
    public NetworkUtils(CloseableHttpClient httpClient, Provider<HttpPost> httpPostProvider,
            ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.httpPostProvider = httpPostProvider;
        this.objectMapper = objectMapper;
    }

    public CloseableHttpResponse sendPostRequestJson(String uri, Object json)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost httpPost = httpPostProvider.get();
        httpPost.setURI(new URI(uri));
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new ByteArrayEntity(objectMapper.writeValueAsString(json).getBytes()));
        return httpClient.execute(httpPost);
    }

}
