package com.github.sksharan.parthenon.plugin.guice;

import com.github.sksharan.parthenon.plugin.listener.LoginListenerFactory;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class ListenerModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().build(LoginListenerFactory.class));
    }

}
