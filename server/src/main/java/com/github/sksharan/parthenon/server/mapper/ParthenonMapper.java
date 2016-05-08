package com.github.sksharan.parthenon.server.mapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.server.entity.PlayerEntity;

@Configuration
public class ParthenonMapper {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(playerModelToPlayerEntity);
        modelMapper.addConverter(playerEntityToPlayerModel);
        modelMapper.validate();
        return modelMapper;
    }

    private Converter<PlayerModel, PlayerEntity> playerModelToPlayerEntity = new AbstractConverter<PlayerModel, PlayerEntity>() {
        @Override
        protected PlayerEntity convert(PlayerModel playerModel) {
            return new PlayerEntity(playerModel.getName(), playerModel.getHealth(), playerModel.getMaxHealth(),
                    playerModel.getExpLevel(), playerModel.getFoodLevel(), playerModel.isOnline());
        }
    };

    private Converter<PlayerEntity, PlayerModel> playerEntityToPlayerModel = new AbstractConverter<PlayerEntity, PlayerModel>() {
        @Override
        protected PlayerModel convert(PlayerEntity playerEntity) {
            return new PlayerModel(playerEntity.getName(), playerEntity.getHealth(), playerEntity.getMaxHealth(),
                    playerEntity.getExpLevel(), playerEntity.getFoodLevel(), playerEntity.isOnline());
        }
    };
}
