package com.github.sksharan.parthenon.common.client;

import com.github.sksharan.parthenon.common.di.ClientModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ParthenonClient {
    private static final Injector INJECTOR = Guice.createInjector(new ClientModule());

    public static PlayerClient getPlayerClient() {
        return INJECTOR.getInstance(PlayerClient.class);
    }

}
