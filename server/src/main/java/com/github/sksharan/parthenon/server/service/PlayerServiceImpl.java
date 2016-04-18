package com.github.sksharan.parthenon.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.sksharan.parthenon.server.entity.PlayerEntity;

@Service
public class PlayerServiceImpl implements PlayerService {

    /*private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }*/

    @Override
    @Transactional
    public void createPlayer(PlayerEntity player) {
        //playerRepository.save(player);
    }

}
