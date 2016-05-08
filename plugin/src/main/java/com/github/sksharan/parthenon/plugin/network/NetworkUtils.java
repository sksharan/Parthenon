package com.github.sksharan.parthenon.plugin.network;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sksharan.parthenon.plugin.ParthenonPlugin;
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

    public HttpResponse sendPostRequestFormUrlEncoded(String uri, NameValuePair... parameters) throws IOException, URISyntaxException {
        HttpPost httpPost = httpPostProvider.get();
        httpPost.setURI(new URI(uri));
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(parameters), StandardCharsets.UTF_8));
        return httpClient.execute(httpPost);
    }

    public HttpResponse sendPostRequestJson(String uri, Object json) throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost httpPost = httpPostProvider.get();
        httpPost.setURI(new URI(uri));
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new ByteArrayEntity(objectMapper.writeValueAsString(json).getBytes()));
        return httpClient.execute(httpPost);
    }

    public String entityToString(HttpEntity entity) throws IOException {
        return IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
    }

    /** Logs information about the response using the plugin's logger. Logs the success text if the
     *  response if HTTP_OK, otherwise logs the errorText. */
    public void checkHttpResponse(ParthenonPlugin plugin, HttpResponse response, String successText, String errorText) {
        switch (response.getStatusLine().getStatusCode()) {
        case HttpStatus.SC_OK:
            plugin.getLogger().log(Level.INFO, successText);
            break;
        default:
            plugin.getLogger().log(Level.SEVERE, errorText);
        }
    }
}
