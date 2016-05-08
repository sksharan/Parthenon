package com.github.sksharan.parthenon.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value = "/{playerName}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerModel> getPlayer(@PathVariable String playerName) {
        if (!playerService.playerExists(playerName)) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(playerService.getPlayer(playerName));
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerModel>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerModel> savePlayer(@RequestBody PlayerModel playerModel) {
        playerService.savePlayer(playerModel);
        return ResponseEntity.ok().body(playerModel);
    }

    @RequestMapping(value = "/{playerName}/online", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerModel> updatePlayerOnline(@PathVariable String playerName,
            @RequestParam("isOnline") boolean isOnline) {
        playerService.updatePlayerOnline(playerName, isOnline);
        return ResponseEntity.ok().body(playerService.getPlayer(playerName));
    }
}
