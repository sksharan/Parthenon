package com.github.sksharan.parthenon.plugin.network;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.google.inject.Provider;

public class NetworkUtilsTest {
    private NetworkUtils networkUtils;

    private CloseableHttpClient httpClient;
    private Provider<HttpGet> httpGetProvider;
    private Provider<HttpPost> httpPostProvider;
    private HttpPost httpPost;
    private ObjectMapper objectMapper;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        httpClient = mock(CloseableHttpClient.class);
        httpGetProvider = mock(Provider.class);
        httpPostProvider = mock(Provider.class);
        httpPost = mock(HttpPost.class);
        objectMapper = new ObjectMapper();
        when(httpPostProvider.get()).thenReturn(httpPost);

        networkUtils = new NetworkUtils(httpClient, httpGetProvider,
                httpPostProvider, objectMapper);
    }

    @Test
    public void testSendPostRequestJson() throws Exception {
        String uriString = "localhost:8080/test";
        Object json = new PlayerModel();

        networkUtils.sendPostRequestJson(uriString, json);
        verify(httpPost, times(1)).setURI(new URI(uriString));
        verify(httpPost, times(1)).setHeader("Content-Type", "application/json");
        verify(httpPost, times(1)).setEntity(any(ByteArrayEntity.class));
        verify(httpClient, times(1)).execute(httpPost);
    }

}
