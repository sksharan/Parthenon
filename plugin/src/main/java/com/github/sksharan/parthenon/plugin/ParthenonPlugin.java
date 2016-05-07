package com.github.sksharan.parthenon.plugin;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.github.sksharan.parthenon.plugin.guice.JacksonModule;
import com.github.sksharan.parthenon.plugin.guice.ListenerModule;
import com.github.sksharan.parthenon.plugin.guice.NetworkModule;
import com.github.sksharan.parthenon.plugin.guice.ScheduleModule;
import com.github.sksharan.parthenon.plugin.listener.LoginListenerFactory;
import com.github.sksharan.parthenon.plugin.schedule.SaveAllPlayersRunnableFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ParthenonPlugin extends JavaPlugin {
    private Injector injector;

    @Override
    public void onEnable() {
        injector = Guice.createInjector(new JacksonModule(), new ListenerModule(),
                new NetworkModule(), new ScheduleModule());
        initializeListeners();
        initializeScheduler();
        getLogger().log(Level.INFO, "ParthenonPlugin enabled");
    }

    private void initializeListeners() {
        getServer().getPluginManager().registerEvents(
                injector.getInstance(LoginListenerFactory.class).create(this), this);
    }

    private void initializeScheduler() {
        BukkitScheduler scheduler = getServer().getScheduler();

        int saveAllPlayersTaskId = scheduler.scheduleSyncRepeatingTask(this,
                injector.getInstance(SaveAllPlayersRunnableFactory.class).create(this), 0L, 350L);
        if (saveAllPlayersTaskId == -1) {
            getLogger().log(Level.SEVERE, "Scheduling failed for SaveAllPlayersRunnable");
        }

    }

}
