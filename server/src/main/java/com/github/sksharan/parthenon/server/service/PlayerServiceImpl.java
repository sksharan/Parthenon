package com.github.sksharan.parthenon.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.server.dao.PlayerRepository;
import com.github.sksharan.parthenon.server.entity.PlayerEntity;

@Service
public class PlayerServiceImpl implements PlayerService {
    private ModelMapper modelMapper;
    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(ModelMapper modelMapper, PlayerRepository playerRepository) {
        this.modelMapper = modelMapper;
        this.playerRepository = playerRepository;
    }

    @Override
    @Transactional
    public void savePlayer(PlayerModel player) {
        playerRepository.save(modelMapper.map(player, PlayerEntity.class));
    }

    @Override
    public List<PlayerModel> getAllPlayers() {
        List<PlayerModel> playerModels = new ArrayList<PlayerModel>();
        playerRepository.findAll().forEach(new Consumer<PlayerEntity>() {
            @Override public void accept(PlayerEntity entity) {
                playerModels.add(modelMapper.map(entity, PlayerModel.class));
            }
        });
        return playerModels;
    }

}
