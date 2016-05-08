package com.github.sksharan.parthenon.plugin.network;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.google.inject.Provider;

@RunWith(MockitoJUnitRunner.class)
public class NetworkUtilsTest {
    private NetworkUtils networkUtils;
    @Mock private HttpClient httpClient;
    @Mock private Provider<HttpPost> httpPostProvider;

    @Before
    public void setUp() {
        networkUtils = new NetworkUtils(httpClient, httpPostProvider, new ObjectMapper());
    }

    @Test
    public void testSendPostRequestByteEntity() throws ClientProtocolException, URISyntaxException, IOException {
        HttpPost httpPost = mock(HttpPost.class);
        when(httpPostProvider.get()).thenReturn(httpPost);

        HttpResponse httpResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);
        when(httpClient.execute(httpPost)).thenReturn(httpResponse);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);

        int statusCode = networkUtils.sendPostRequest("http://www.github.com", new PlayerModel());
        assertEquals(HttpStatus.SC_OK, statusCode);

        verify(httpPostProvider, times(1)).get();
        verify(httpPost, times(1)).setURI(any(URI.class));
        verify(httpPost, times(1)).setHeader("Content-Type", "application/json");
        verify(httpPost, times(1)).setEntity(any(ByteArrayEntity.class));
    }
}
