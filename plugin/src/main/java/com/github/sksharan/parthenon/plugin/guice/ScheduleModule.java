package com.github.sksharan.parthenon.plugin.guice;

import com.github.sksharan.parthenon.plugin.schedule.SaveAllPlayersRunnableFactory;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class ScheduleModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().build(SaveAllPlayersRunnableFactory.class));
    }

}
