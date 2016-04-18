package com.github.sksharan.parthenon.plugin;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.sksharan.parthenon.plugin.guice.MapperModule;
import com.github.sksharan.parthenon.plugin.guice.NetworkModule;
import com.github.sksharan.parthenon.plugin.listener.LoginListener;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ParthenonPlugin extends JavaPlugin {
    private Injector injector;
    private LoginListener loginListener;

    @Override
    public void onEnable() {
        injector = Guice.createInjector(new MapperModule(), new NetworkModule());
        loginListener = injector.getInstance(LoginListener.class);
        registerListeners();
        getLogger().log(Level.INFO, "ParthenonPlugin enabled");
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(loginListener, this);
    }

}
