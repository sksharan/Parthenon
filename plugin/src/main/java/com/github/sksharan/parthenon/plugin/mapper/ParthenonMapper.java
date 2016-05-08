package com.github.sksharan.parthenon.plugin.mapper;

import org.bukkit.entity.Player;

import com.github.sksharan.parthenon.common.model.PlayerModel;

public class ParthenonMapper {

    public PlayerModel map(Player player) {
        PlayerModel playerModel = new PlayerModel();
        playerModel.setName(player.getName());
        playerModel.setHealth(player.getHealth());
        playerModel.setMaxHealth(player.getMaxHealth());
        playerModel.setExpLevel(player.getLevel());
        playerModel.setFoodLevel(player.getFoodLevel());
        playerModel.setOnline(player.isOnline());
        return playerModel;
    }

}
