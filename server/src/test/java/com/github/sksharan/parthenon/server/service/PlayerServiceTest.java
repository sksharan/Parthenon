package com.github.sksharan.parthenon.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
    private ModelMapper modelMapper;
    @Mock private PlayerRepository playerRepository;

    @Before
    public void setUp() {
        modelMapper = mock(ModelMapper.class);
        playerService = new PlayerServiceImpl(modelMapper, playerRepository);
    }

    @Test
    public void testSavePlayer() {
        playerService.savePlayer(mock(PlayerModel.class));
        verify(playerRepository).save(any(PlayerEntity.class));
    }

    @Test
    public void testGetAllPlayers() {
        PlayerEntity entity1 = mock(PlayerEntity.class);
        PlayerEntity entity2 = mock(PlayerEntity.class);
        PlayerEntity entity3 = mock(PlayerEntity.class);
        PlayerModel model1 = mock(PlayerModel.class);
        PlayerModel model2 = mock(PlayerModel.class);
        PlayerModel model3 = mock(PlayerModel.class);
        when(modelMapper.map(entity1, PlayerModel.class)).thenReturn(model1);
        when(modelMapper.map(entity2, PlayerModel.class)).thenReturn(model2);
        when(modelMapper.map(entity3, PlayerModel.class)).thenReturn(model3);

        List<PlayerEntity> entities = new ArrayList<PlayerEntity>();
        entities.add(entity1);
        entities.add(entity2);
        entities.add(entity3);
        when(playerRepository.findAll()).thenReturn(entities);

        List<PlayerModel> models = playerService.getAllPlayers();
        verify(modelMapper, times(3)).map(any(PlayerEntity.class), any());
        assertEquals(3, models.size());
        assertTrue(models.contains(model1));
        assertTrue(models.contains(model2));
        assertTrue(models.contains(model3));
    }
}
