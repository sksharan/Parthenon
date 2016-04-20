package com.github.sksharan.parthenon.server;

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
        modelMapper.addConverter(playerConverter);
        modelMapper.validate();
        return modelMapper;
    }

    private Converter<PlayerModel, PlayerEntity> playerConverter = new AbstractConverter<PlayerModel, PlayerEntity>() {
        @Override
        protected PlayerEntity convert(PlayerModel player) {
            return new PlayerEntity(player.getName(), player.getHealth(), player.getMaxHealth(),
                    player.getExpLevel(), player.getFoodLevel());
        }
    };
}
