package com.github.sksharan.parthenon.plugin.network;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class NetworkUtils {
    private final HttpClient httpClient;
    private final Provider<HttpPost> httpPostProvider;
    private final ObjectMapper objectMapper;

    @Inject
    public NetworkUtils(HttpClient httpClient, Provider<HttpPost> httpPostProvider, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.httpPostProvider = httpPostProvider;
        this.objectMapper = objectMapper;
    }

    /** Sends a POST request to the specified URI and returns the status code of the response. */
    public int sendPostRequest(String uri, Object json) throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost httpPost = httpPostProvider.get();
        httpPost.setURI(new URI(uri));
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new ByteArrayEntity(objectMapper.writeValueAsString(json).getBytes()));

        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        EntityUtils.consume(response.getEntity());  // Connection pool exception can occur if entities are not consumed
        return statusCode;
    }

}
