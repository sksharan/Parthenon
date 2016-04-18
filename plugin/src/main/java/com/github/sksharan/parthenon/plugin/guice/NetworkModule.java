package com.github.sksharan.parthenon.plugin.guice;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class NetworkModule extends AbstractModule {

    @Provides
    public HttpClient provideHttpClient() {
        return HttpClients.createDefault();
    }

    @Override
    protected void configure() {
    }

}
