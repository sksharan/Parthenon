package com.github.sksharan.parthenon.plugin.listener;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.modelmapper.ModelMapper;

import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.common.url.ParthenonUrl;
import com.github.sksharan.parthenon.plugin.network.NetworkUtils;
import com.google.inject.Inject;

public class LoginListener implements Listener {
    private final ModelMapper modelMapper;
    private final NetworkUtils networkUtils;

    @Inject
    public LoginListener(ModelMapper modelMapper, NetworkUtils networkUtils) {
        this.modelMapper = modelMapper;
        this.networkUtils = networkUtils;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException, URISyntaxException {
        PlayerModel playerModel = modelMapper.map(event.getPlayer(), PlayerModel.class);
        HttpResponse response = networkUtils.sendPostRequest(ParthenonUrl.BASE_URL+ParthenonUrl.PLAYER, playerModel);
    }
}
