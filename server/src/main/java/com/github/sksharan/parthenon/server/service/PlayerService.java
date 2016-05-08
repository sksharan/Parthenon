package com.github.sksharan.parthenon.server.service;

import java.util.List;

import com.github.sksharan.parthenon.common.model.PlayerModel;

public interface PlayerService {

    public void savePlayer(PlayerModel player);

    public void updatePlayerOnline(String name, boolean isOnline);

    public PlayerModel getPlayer(String name);

    public List<PlayerModel> getAllPlayers();

    public boolean playerExists(String name);

}
