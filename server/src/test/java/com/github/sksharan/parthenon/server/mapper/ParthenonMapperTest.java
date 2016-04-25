package com.github.sksharan.parthenon.server.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.server.Parthenon;
import com.github.sksharan.parthenon.server.entity.PlayerEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Parthenon.class)
public class ParthenonMapperTest {
    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testModelMapperNotNull() {
        assertNotNull(modelMapper);
    }

    @Test
    public void testPlayerMapper() {
        PlayerModel playerModel = mock(PlayerModel.class);
        when(playerModel.getName()).thenReturn("Test");
        when(playerModel.getHealth()).thenReturn(12.0);
        when(playerModel.getMaxHealth()).thenReturn(20.0);
        when(playerModel.getExpLevel()).thenReturn(7);
        when(playerModel.getFoodLevel()).thenReturn(12);

        PlayerEntity playerEntity = modelMapper.map(playerModel, PlayerEntity.class);
        assertEquals("Test", playerEntity.getName());
        assertEquals(12.0, playerEntity.getHealth(), 0.1);
        assertEquals(20.0, playerEntity.getMaxHealth(), 0.1);
        assertEquals(7, playerEntity.getExpLevel());
        assertEquals(12, playerEntity.getFoodLevel());
    }
}
