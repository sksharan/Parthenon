package com.github.sksharan.parthenon.plugin.guice;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class NetworkModule extends AbstractModule {

    @Provides
    public HttpClient provideHttpClient() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000)
                .build();
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    @Override
    protected void configure() {
    }

}
