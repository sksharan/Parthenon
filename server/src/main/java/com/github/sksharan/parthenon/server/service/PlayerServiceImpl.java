package com.github.sksharan.parthenon.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.server.dao.ItemStackRepository;
import com.github.sksharan.parthenon.server.dao.PlayerRepository;
import com.github.sksharan.parthenon.server.entity.ItemStackEntity;
import com.github.sksharan.parthenon.server.entity.PlayerEntity;
import com.github.sksharan.parthenon.server.exception.PlayerServiceException;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final ModelMapper modelMapper;
    private final PlayerRepository playerRepository;
    private final ItemStackRepository itemStackRepository;

    @Autowired
    public PlayerServiceImpl(ModelMapper modelMapper, PlayerRepository playerRepository,
            ItemStackRepository itemStackRepository) {
        this.modelMapper = modelMapper;
        this.playerRepository = playerRepository;
        this.itemStackRepository = itemStackRepository;
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
        setPlayerEntityId(playerEntity);
        for (ItemStackEntity itemStackEntity: playerEntity.getItems()) {
            setItemStackEntityId(itemStackEntity);
        }
        playerRepository.save(playerEntity);
    }

    private void setPlayerEntityId(PlayerEntity entity) {
        PlayerEntity existingEntity = playerRepository.findByName(entity.getName());
        if (existingEntity != null) {
            entity.setId(existingEntity.getId());
        }
    }

    private void setItemStackEntityId(ItemStackEntity entity) {
        String name = entity.getName();
        int amount = entity.getAmount();
        String type = entity.getType();
        ItemStackEntity existingEntity = itemStackRepository.findByNameAndAmountAndType(name, amount, type);
        if (existingEntity != null) {
            entity.setId(existingEntity.getId());
        }
    }

}
