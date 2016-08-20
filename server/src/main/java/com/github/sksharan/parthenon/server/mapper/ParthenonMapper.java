package com.github.sksharan.parthenon.server.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.sksharan.parthenon.common.model.ItemStackModel;
import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.server.entity.ItemStackEntity;
import com.github.sksharan.parthenon.server.entity.PlayerEntity;

@Configuration
public class ParthenonMapper {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(itemStackModelToItemStackEntity);
        modelMapper.addConverter(itemStackEntityToItemStackModel);
        modelMapper.addConverter(playerModelToPlayerEntity);
        modelMapper.addConverter(playerEntityToPlayerModel);
        modelMapper.validate();
        return modelMapper;
    }

    /* -- CONVERTERS -- */

    private Converter<PlayerModel, PlayerEntity> playerModelToPlayerEntity
            = new AbstractConverter<PlayerModel, PlayerEntity>() {
        @Override
        protected PlayerEntity convert(PlayerModel playerModel) {
            return toPlayerEntity(playerModel);
        }
    };

    private Converter<PlayerEntity, PlayerModel> playerEntityToPlayerModel
            = new AbstractConverter<PlayerEntity, PlayerModel>() {
        @Override
        protected PlayerModel convert(PlayerEntity playerEntity) {
            return toPlayerModel(playerEntity);
        }
    };

    private Converter<ItemStackModel, ItemStackEntity> itemStackModelToItemStackEntity
            = new AbstractConverter<ItemStackModel, ItemStackEntity>() {
        @Override
        protected ItemStackEntity convert(ItemStackModel itemStackModel) {
            return toItemStackEntity(itemStackModel);
        }
    };

    private Converter<ItemStackEntity, ItemStackModel> itemStackEntityToItemStackModel
            = new AbstractConverter<ItemStackEntity, ItemStackModel>() {
        @Override
        protected ItemStackModel convert(ItemStackEntity itemStackEntity) {
            return toItemStackModel(itemStackEntity);
        }
    };

    /* -- CONVERSION METHODS -- */

    private ItemStackEntity toItemStackEntity(ItemStackModel itemStackModel) {
        return new ItemStackEntity(itemStackModel.getName(), itemStackModel.getAmount(),
                itemStackModel.getType().name());
    }

    private List<ItemStackEntity> toItemStackEntityList(List<ItemStackModel> itemStackModelList) {
        List<ItemStackEntity> entities = new ArrayList<ItemStackEntity>();
        for (ItemStackModel itemStackModel: itemStackModelList) {
            entities.add(toItemStackEntity(itemStackModel));
        }
        return entities;
    }

    private ItemStackModel toItemStackModel(ItemStackEntity itemStackEntity) {
        return new ItemStackModel(itemStackEntity.getName(), itemStackEntity.getAmount(),
                ItemStackModel.Type.valueOf(itemStackEntity.getType()));
    }

    private List<ItemStackModel> toItemStackModelList(List<ItemStackEntity> itemStackEntityList) {
        List<ItemStackModel> models = new ArrayList<ItemStackModel>();
        for (ItemStackEntity itemStackEntity: itemStackEntityList) {
            models.add(toItemStackModel(itemStackEntity));
        }
        return models;
    }

    private PlayerEntity toPlayerEntity(PlayerModel playerModel) {
        List<ItemStackEntity> playerInventory = toItemStackEntityList(playerModel.getItems());
        return new PlayerEntity(playerModel.getName(), playerModel.getHealth(),
                playerModel.getMaxHealth(), playerModel.getExpLevel(), playerModel.getCurrExpPercentage(),
                playerModel.getFoodLevel(), playerModel.isOnline(), playerInventory);
    }

    private PlayerModel toPlayerModel(PlayerEntity playerEntity) {
        List<ItemStackModel> playerInventory = toItemStackModelList(playerEntity.getItems());
        return new PlayerModel(playerEntity.getName(), playerEntity.getHealth(),
                playerEntity.getMaxHealth(), playerEntity.getExpLevel(), playerEntity.getCurrExpPercentage(),
                playerEntity.getFoodLevel(), playerEntity.isOnline(), playerInventory);
    }

}
