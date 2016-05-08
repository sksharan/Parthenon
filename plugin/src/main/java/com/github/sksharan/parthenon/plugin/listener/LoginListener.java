package com.github.sksharan.parthenon.plugin.listener;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;

import org.apache.http.HttpStatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.common.url.ParthenonUrl;
import com.github.sksharan.parthenon.plugin.ParthenonPlugin;
import com.github.sksharan.parthenon.plugin.mapper.ParthenonMapper;
import com.github.sksharan.parthenon.plugin.network.NetworkUtils;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class LoginListener implements Listener {
    private final ParthenonPlugin parthenonPlugin;
    private final NetworkUtils networkUtils;
    private final ParthenonMapper parthenonMapper;

    @Inject
    public LoginListener(@Assisted ParthenonPlugin parthenonPlugin, NetworkUtils networkUtils,
            ParthenonMapper parthenonMapper) {
        this.parthenonPlugin = parthenonPlugin;
        this.networkUtils = networkUtils;
        this.parthenonMapper = parthenonMapper;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException, URISyntaxException {
        PlayerModel playerModel = parthenonMapper.map(event.getPlayer());
        int statusCode = networkUtils.sendPostRequest(ParthenonUrl.BASE_URL+ParthenonUrl.PLAYER, playerModel);

        if (statusCode == HttpStatus.SC_OK) {
            parthenonPlugin.getLogger().log(Level.INFO, "Successfully saved player information for " + playerModel.getName());
        }
    }
}
