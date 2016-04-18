package com.github.sksharan.parthenon.plugin.guice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class JacksonModule extends AbstractModule {

    @Provides
    public ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

    @Override
    protected void configure() {
    }

}
