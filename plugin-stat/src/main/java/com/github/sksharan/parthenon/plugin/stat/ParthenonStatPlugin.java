package com.github.sksharan.parthenon.plugin.stat;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class ParthenonStatPlugin extends JavaPlugin {
    public static final String BASE_URL = "http://localhost:8080";

    @Override
    public void onEnable() {
        initializeScheduler();
        logInfo("Parthenon Stat Plugin enabled");
    }


    private void initializeScheduler() {
        BukkitScheduler scheduler = getServer().getScheduler();
        SaveAllStatsRunnable runnable = new SaveAllStatsRunnable(this, new ParthenonStatMapper());
        if (scheduler.scheduleSyncRepeatingTask(this, runnable, 0L, 350L) == -1) {
            logSevere("Scheduling failed for SaveAllStatsRunnable");
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
