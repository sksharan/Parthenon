package com.github.sksharan.parthenon.plugin.guice;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class NetworkModule extends AbstractModule {

    @Provides
    public CloseableHttpClient provideHttpClient() {
        return HttpClients.createDefault();
    }

    @Override
    protected void configure() {
    }

}
