package com.github.sksharan.parthenon.plugin.guice;

import org.bukkit.entity.Player;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class MapperModule extends AbstractModule {

    @Provides
    public ModelMapper provideModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(playerConverter);
        modelMapper.validate();
        return modelMapper;
    }

    @Override
    protected void configure() {
    }

    private Converter<Player, PlayerModel> playerConverter = new AbstractConverter<Player, PlayerModel>() {
        @Override
        protected PlayerModel convert(Player player) {
            return new PlayerModel(player.getName(), player.getHealth(), player.getMaxHealth(),
                    player.getLevel(), player.getFoodLevel());
        }
    };

}
