package com.github.sksharan.parthenon.server.service;

import java.util.List;

import com.github.sksharan.parthenon.common.model.PlayerModel;

public interface PlayerService {

    public List<PlayerModel> getAllPlayers();

    public PlayerModel getPlayerByName(String name);

    public boolean playerExists(String name);

    public void savePlayer(PlayerModel player);

}
