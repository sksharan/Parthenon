package com.github.sksharan.parthenon.plugin.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;

import com.github.sksharan.parthenon.common.model.PlayerModel;

public class ParthenonMapperTest {
    private ParthenonMapper parthenonMapper;

    @Before
    public void setUp() {
        parthenonMapper = new ParthenonMapper();
    }

    @Test
    public void testPlayerMap() {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn("Player");
        when(player.getHealth()).thenReturn(15.0);
        when(player.getMaxHealth()).thenReturn(20.0);
        when(player.getLevel()).thenReturn(7);
        when(player.getFoodLevel()).thenReturn(12);
        when(player.isOnline()).thenReturn(true);

        PlayerModel playerModel = parthenonMapper.map(player);
        assertEquals("Player", playerModel.getName());
        assertEquals(15.0, playerModel.getHealth(), 0.1);
        assertEquals(20.0, playerModel.getMaxHealth(), 0.1);
        assertEquals(7, playerModel.getExpLevel());
        assertEquals(12, playerModel.getFoodLevel());
        assertTrue(playerModel.isOnline());
    }
}
