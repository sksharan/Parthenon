package com.github.sksharan.parthenon.plugin;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.sksharan.parthenon.plugin.guice.JacksonModule;
import com.github.sksharan.parthenon.plugin.guice.ListenerModule;
import com.github.sksharan.parthenon.plugin.guice.NetworkModule;
import com.github.sksharan.parthenon.plugin.listener.LoginListener;
import com.github.sksharan.parthenon.plugin.listener.LoginListenerFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ParthenonPlugin extends JavaPlugin {
    private LoginListener loginListener;

    @Override
    public void onEnable() {
        initializeGuice();
        registerListeners();
        getLogger().log(Level.INFO, "ParthenonPlugin enabled");
    }

    private void initializeGuice() {
        Injector injector = Guice.createInjector(new JacksonModule(), new ListenerModule(), new NetworkModule());
        loginListener = injector.getInstance(LoginListenerFactory.class).create(this);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(loginListener, this);
    }

}
