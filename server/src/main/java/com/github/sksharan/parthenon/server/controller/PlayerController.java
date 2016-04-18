package com.github.sksharan.parthenon.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.common.url.ParthenonUrl;
import com.github.sksharan.parthenon.server.service.PlayerService;

@Controller
@RequestMapping(value = ParthenonUrl.PLAYER)
public class PlayerController {
    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping(method = RequestMethod.POST, headers = "content-type=application/json")
    @ResponseBody
    public void updatePlayer(@RequestBody PlayerModel playerModel) {
        System.out.println("name: " + playerModel.getName());
        System.out.println("health: " + playerModel.getHealth());
        System.out.println("maxHealth: " + playerModel.getMaxHealth());
        System.out.println("expLevel: " + playerModel.getExpLevel());
        System.out.println("foodLevel: " + playerModel.getFoodLevel());
    }

}
