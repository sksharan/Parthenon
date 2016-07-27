package com.github.sksharan.parthenon.plugin.network;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sksharan.parthenon.plugin.exception.ParthenonPluginException;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class NetworkUtils {
    private final CloseableHttpClient httpClient;
    private final Provider<HttpGet> httpGetProvider;
    private final Provider<HttpPost> httpPostProvider;
    private final ObjectMapper objectMapper;

    @Inject
    public NetworkUtils(CloseableHttpClient httpClient, Provider<HttpGet> httpGetProvider,
            Provider<HttpPost> httpPostProvider, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.httpGetProvider = httpGetProvider;
        this.httpPostProvider = httpPostProvider;
        this.objectMapper = objectMapper;
    }

    public String sendGetRequest(String uri)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpGet httpGet = httpGetProvider.get();
        httpGet.setURI(new URI(uri));
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new ParthenonPluginException(
                        "GET request failed: " + response.getStatusLine().getReasonPhrase());
            }
            return EntityUtils.toString(response.getEntity());
        }
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
