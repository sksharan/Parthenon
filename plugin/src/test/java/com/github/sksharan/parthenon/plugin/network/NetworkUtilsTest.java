package com.github.sksharan.parthenon.plugin.network;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicNameValuePair;
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
    public void testSendPostRequestFormUrlEncoded() throws IOException, URISyntaxException {
        HttpPost httpPost = mock(HttpPost.class);
        when(httpPostProvider.get()).thenReturn(httpPost);

        networkUtils.sendPostRequestFormUrlEncoded("http://www.github.com",
                new BasicNameValuePair("name1", "value1"),
                new BasicNameValuePair("name2", "value2"));

        verify(httpPostProvider, times(1)).get();
        verify(httpPost, times(1)).setURI(any(URI.class));
        verify(httpPost, times(1)).setEntity(any(UrlEncodedFormEntity.class));
        verify(httpClient, times(1)).execute(httpPost);
    }

    @Test
    public void testSendPostRequestJson() throws ClientProtocolException, URISyntaxException, IOException {
        HttpPost httpPost = mock(HttpPost.class);
        when(httpPostProvider.get()).thenReturn(httpPost);

        networkUtils.sendPostRequestJson("http://www.github.com", new PlayerModel());

        verify(httpPostProvider, times(1)).get();
        verify(httpPost, times(1)).setURI(any(URI.class));
        verify(httpPost, times(1)).setHeader("Content-Type", "application/json");
        verify(httpPost, times(1)).setEntity(any(ByteArrayEntity.class));
        verify(httpClient, times(1)).execute(httpPost);
    }
}
