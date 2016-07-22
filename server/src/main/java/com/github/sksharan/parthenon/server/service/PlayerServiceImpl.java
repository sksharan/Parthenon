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
        for (int i = 0; i < playerEntity.getItems().size(); i++) {
            /* Handle the case of the player having duplicate item stacks by saving each
             * item stack individually before saving the player. This ensures that every
             * item stack has a valid ID. Update the player's items with the ones returned
             * by the repository save() method to prevent a "detached entity" error when
             * saving the player. */
            ItemStackEntity itemStackEntity = playerEntity.getItems().get(i);
            setItemStackEntityId(itemStackEntity);
            itemStackEntity = itemStackRepository.save(itemStackEntity);
            playerEntity.getItems().set(i, itemStackEntity);
        }
        playerRepository.save(playerEntity);
    }

    /** Sets the id of the player entity if it exists in the database. */
    private void setPlayerEntityId(PlayerEntity entity) {
        PlayerEntity existingEntity = playerRepository.findByName(entity.getName());
        if (existingEntity != null) {
            entity.setId(existingEntity.getId());
        }
    }

    /** Sets the id of the item stack entity if it exists in the database. */
    private void setItemStackEntityId(ItemStackEntity entity) {
        String name = entity.getName();
        int amount = entity.getAmount();
        String type = entity.getType();
        List<ItemStackEntity> existingEntities = itemStackRepository.findByNameAndAmountAndType(name, amount, type);
        if (!existingEntities.isEmpty()) {
            entity.setId(existingEntities.get(0).getId());
        }
    }

}
