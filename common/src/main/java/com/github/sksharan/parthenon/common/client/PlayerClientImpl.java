package com.github.sksharan.parthenon.common.client;

import java.util.Collections;
import java.util.List;

import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.common.url.PlayerUrl;
import com.google.inject.Inject;

public class PlayerClientImpl implements PlayerClient {
    private final ClientCore clientCore;

    @Inject
    public PlayerClientImpl(ClientCore clientCore) {
        this.clientCore = clientCore;
    }

    @Override
    public PlayerModel getPlayer(String baseUrl, String playerName) {
        return clientCore.sendGetRequest(PlayerUrl.getPlayerUrl(baseUrl, playerName), PlayerModel.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PlayerModel> getAllPlayers(String baseUrl) {
        return clientCore.sendGetRequest(PlayerUrl.getAllPlayersUrl(baseUrl),
                Collections.<PlayerModel>emptyList().getClass());
    }

    @Override
    public boolean playerExists(String baseUrl, String playerName) {
        return clientCore.sendGetRequest(PlayerUrl.getPlayerExistsUrl(baseUrl, playerName), Boolean.class);
    }

    @Override
    public void savePlayer(String baseUrl, PlayerModel playerModel) {
        clientCore.sendPostRequestJson(PlayerUrl.savePlayerUrl(baseUrl), playerModel);
    }

}
