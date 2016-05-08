package com.github.sksharan.parthenon.plugin.listener;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.common.url.PlayerUrl;
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
        HttpResponse response = networkUtils.sendPostRequestJson(PlayerUrl.savePlayerUrl(), playerModel);
        networkUtils.checkHttpResponse(parthenonPlugin, response,
                "Successfully saved online player information for " + playerModel.getName(),
                "Failed to save player information for " + playerModel.getName() + " with reason " + response.getStatusLine().getReasonPhrase());
        EntityUtils.consume(response.getEntity());
    }
}
