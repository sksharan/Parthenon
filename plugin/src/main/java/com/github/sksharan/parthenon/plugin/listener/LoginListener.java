package com.github.sksharan.parthenon.plugin.listener;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.common.url.ParthenonUrl;
import com.github.sksharan.parthenon.plugin.mapper.ParthenonMapper;
import com.github.sksharan.parthenon.plugin.network.NetworkUtils;
import com.google.inject.Inject;

public class LoginListener implements Listener {
    private final NetworkUtils networkUtils;
    private final ParthenonMapper parthenonMapper;

    @Inject
    public LoginListener(NetworkUtils networkUtils, ParthenonMapper parthenonMapper) {
        this.networkUtils = networkUtils;
        this.parthenonMapper = parthenonMapper;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException, URISyntaxException {
        PlayerModel playerModel = parthenonMapper.map(event.getPlayer());
        HttpResponse response = networkUtils.sendPostRequest(ParthenonUrl.BASE_URL+ParthenonUrl.PLAYER, playerModel);
    }
}
