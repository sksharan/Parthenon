package com.github.sksharan.parthenon.common.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sksharan.parthenon.common.exception.ParthenonClientException;
import com.google.inject.Inject;

public class ClientCore {
    private final ObjectMapper objectMapper;

    @Inject
    public ClientCore(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T sendGetRequest(String uri, Class<T> clazz) {
        try {
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(new URI(uri));
            try (CloseableHttpResponse response = getHttpClient().execute(httpGet)) {
                if (!isSuccess(response)) {
                    throw new HttpResponseException(response.getStatusLine().getStatusCode(),
                            response.getStatusLine().getReasonPhrase());
                }
                String entity = EntityUtils.toString(response.getEntity());
                return objectMapper.readValue(entity, clazz);
            }
        } catch (URISyntaxException | IOException e) {
            throw new ParthenonClientException(e);
        }
    }

    public void sendPostRequestJson(String uri, Object json) {
        try {
            HttpPost httpPost = new HttpPost();
            httpPost.setURI(new URI(uri));
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new ByteArrayEntity(objectMapper.writeValueAsString(json).getBytes()));
            try (CloseableHttpResponse response = getHttpClient().execute(httpPost)) {
                if (!isSuccess(response)) {
                    throw new HttpResponseException(response.getStatusLine().getStatusCode(),
                            response.getStatusLine().getReasonPhrase());
                }
            }
        } catch (URISyntaxException | IOException e) {
            throw new ParthenonClientException(e);
        }
    }

    private CloseableHttpClient getHttpClient() {
        return HttpClients.createDefault();
    }

    private boolean isSuccess(CloseableHttpResponse response) {
        int statusCode = response.getStatusLine().getStatusCode();
        return statusCode >= 200 && statusCode < 300;
    }
}
