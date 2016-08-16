package com.github.sksharan.parthenon.common.di;

import com.github.sksharan.parthenon.common.client.PlayerClient;
import com.github.sksharan.parthenon.common.client.PlayerClientImpl;
import com.google.inject.AbstractModule;

public class ClientModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PlayerClient.class).to(PlayerClientImpl.class);
    }

}
