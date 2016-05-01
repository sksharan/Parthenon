package com.github.sksharan.parthenon.server.service;

import java.util.List;

import com.github.sksharan.parthenon.common.model.PlayerModel;

public interface PlayerService {

    public void savePlayer(PlayerModel player);

    public List<PlayerModel> getAllPlayers();

}
