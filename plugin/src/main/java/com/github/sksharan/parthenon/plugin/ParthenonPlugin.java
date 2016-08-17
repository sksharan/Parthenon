package com.github.sksharan.parthenon.plugin;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class ParthenonPlugin extends JavaPlugin {
    public static final String BASE_URL = "http://localhost:8080";

    @Override
    public void onEnable() {
        initializeScheduler();
        logInfo("ParthenonPlugin enabled");
    }


    private void initializeScheduler() {
        BukkitScheduler scheduler = getServer().getScheduler();
        SaveAllPlayersRunnable runnable = new SaveAllPlayersRunnable(this, new ParthenonMapper());
        if (scheduler.scheduleSyncRepeatingTask(this, runnable, 0L, 350L) == -1) {
            logSevere("Scheduling failed for SaveAllPlayersRunnable");
        }
    }

    public void logInfo(String format, Object... args) {
        log(Level.INFO, format, args);
    }

    public void logSevere(String format, Object... args) {
        log(Level.SEVERE, format, args);
    }

    public void log(Level level, String format, Object... args) {
        getLogger().log(level, String.format(format, args));
    }

}
