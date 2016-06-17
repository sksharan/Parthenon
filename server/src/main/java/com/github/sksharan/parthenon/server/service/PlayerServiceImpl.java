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
import com.github.sksharan.parthenon.server.exception.PlayerServiceException;

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
    public List<PlayerModel> getAllPlayers() {
        List<PlayerModel> playerModels = new ArrayList<PlayerModel>();
        playerRepository.findAll().forEach(new Consumer<PlayerEntity>() {
            @Override public void accept(PlayerEntity entity) {
                playerModels.add(modelMapper.map(entity, PlayerModel.class));
            }
        });
        return playerModels;
    }

    @Override
    public PlayerModel getPlayerByName(String name) {
        PlayerEntity playerEntity = playerRepository.findByName(name);
        if (playerEntity == null) {
            throw new PlayerServiceException(String.format("Player %s does not exist", name));
        }
        return modelMapper.map(playerEntity, PlayerModel.class);
    }

    @Override
    public boolean playerExists(String name) {
        return playerRepository.findByName(name) != null;
    }

    @Override
    @Transactional
    public void savePlayer(PlayerModel playerModel) {
        PlayerEntity playerEntity = modelMapper.map(playerModel, PlayerEntity.class);
        PlayerEntity existingPlayerEntity = playerRepository.findByName(playerModel.getName());
        if (existingPlayerEntity != null) {
            playerEntity.setId(existingPlayerEntity.getId());
        }
        playerRepository.save(playerEntity);
    }

}
