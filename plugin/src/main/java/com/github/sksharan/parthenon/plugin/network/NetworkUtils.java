package com.github.sksharan.parthenon.plugin.network;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

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

    public HttpResponse sendPostRequest(String uri, NameValuePair... parameters) throws IOException, URISyntaxException {
        HttpPost httpPost = httpPostProvider.get();
        httpPost.setURI(new URI(uri));
        httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(parameters), "UTF-8"));
        return httpClient.execute(httpPost);
    }

    public HttpResponse sendPostRequest(String uri, Object json) throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost httpPost = httpPostProvider.get();
        httpPost.setURI(new URI(uri));
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new ByteArrayEntity(objectMapper.writeValueAsString(json).getBytes()));
        return httpClient.execute(httpPost);
    }

}
