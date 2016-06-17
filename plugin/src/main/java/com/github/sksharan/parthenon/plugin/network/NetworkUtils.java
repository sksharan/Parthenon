package com.github.sksharan.parthenon.plugin.network;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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

    public CloseableHttpResponse sendPostRequestFormUrlEncoded(String uri, NameValuePair... parameters) throws IOException, URISyntaxException {
        HttpPost httpPost = httpPostProvider.get();
        httpPost.setURI(new URI(uri));
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(parameters), StandardCharsets.UTF_8));
        return httpClient.execute(httpPost);
    }

    public CloseableHttpResponse sendPostRequestJson(String uri, Object json) throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost httpPost = httpPostProvider.get();
        httpPost.setURI(new URI(uri));
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new ByteArrayEntity(objectMapper.writeValueAsString(json).getBytes()));
        return httpClient.execute(httpPost);
    }

    public String entityToString(HttpEntity entity) throws IOException {
        return IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
    }

}
