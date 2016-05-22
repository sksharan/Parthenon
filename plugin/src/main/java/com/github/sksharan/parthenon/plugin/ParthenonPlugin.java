package com.github.sksharan.parthenon.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.github.sksharan.parthenon.plugin.exception.ParthenonPluginException;
import com.github.sksharan.parthenon.plugin.guice.JacksonModule;
import com.github.sksharan.parthenon.plugin.guice.ListenerModule;
import com.github.sksharan.parthenon.plugin.guice.NetworkModule;
import com.github.sksharan.parthenon.plugin.guice.ScheduleModule;
import com.github.sksharan.parthenon.plugin.schedule.SaveAllPlayersRunnableFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ParthenonPlugin extends JavaPlugin {
    private final String SERVER_BASE_URL_FILE = "/server.txt";
    private String serverBaseUrl;
    private Injector injector;

    @Override
    public void onEnable() {
        initializeServerBaseUrl();
        injector = Guice.createInjector(new JacksonModule(), new ListenerModule(),
                new NetworkModule(), new ScheduleModule());
        initializeListeners();
        initializeScheduler();
        getLogger().log(Level.INFO, "ParthenonPlugin enabled");
    }

    private void initializeServerBaseUrl() {
        InputStream is = getClass().getResourceAsStream(SERVER_BASE_URL_FILE);
        if (is == null) {
            throw new ParthenonPluginException(SERVER_BASE_URL_FILE + " does not exist");
        }

        try {
            serverBaseUrl = IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ParthenonPluginException(ExceptionUtils.getStackTrace(e));
        }

        UrlValidator validator = new UrlValidator(UrlValidator.ALLOW_2_SLASHES
                + UrlValidator.ALLOW_ALL_SCHEMES + UrlValidator.ALLOW_LOCAL_URLS);
        if (!validator.isValid(serverBaseUrl)) {
            throw new ParthenonPluginException("Invalid url in " + SERVER_BASE_URL_FILE);
        }
    }

    private void initializeListeners() {
    }

    private void initializeScheduler() {
        BukkitScheduler scheduler = getServer().getScheduler();

        int saveAllPlayersTaskId = scheduler.scheduleSyncRepeatingTask(this,
                injector.getInstance(SaveAllPlayersRunnableFactory.class).create(this), 0L, 350L);
        if (saveAllPlayersTaskId == -1) {
            getLogger().log(Level.SEVERE, "Scheduling failed for SaveAllPlayersRunnable");
        }
    }

    /** Returns the base URL of the server to send HTTP requests to. */
    public String getServerBaseUrl() {
        return serverBaseUrl;
    }

}
