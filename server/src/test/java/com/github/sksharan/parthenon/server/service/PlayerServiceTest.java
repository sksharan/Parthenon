package com.github.sksharan.parthenon.server.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.server.dao.PlayerRepository;
import com.github.sksharan.parthenon.server.entity.PlayerEntity;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {
    private PlayerService playerService;
    @Mock private ModelMapper modelMapper;
    @Mock private PlayerRepository playerRepository;

    @Before
    public void setUp() {
        playerService = new PlayerServiceImpl(modelMapper, playerRepository);
    }

    @Test
    public void testSavePlayer() {
        playerService.savePlayer(mock(PlayerModel.class));
        verify(playerRepository).save(any(PlayerEntity.class));
    }
}
