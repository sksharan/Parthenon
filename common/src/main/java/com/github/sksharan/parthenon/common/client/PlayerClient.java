package com.github.sksharan.parthenon.common.client;

import java.util.List;

import com.github.sksharan.parthenon.common.model.PlayerModel;

public interface PlayerClient {

    PlayerModel getPlayer(String baseUrl, String playerName);

    List<PlayerModel> getAllPlayers(String baseUrl);

    boolean playerExists(String baseUrl, String playerName);

    void savePlayer(String baseUrl, PlayerModel playerModel);

}
