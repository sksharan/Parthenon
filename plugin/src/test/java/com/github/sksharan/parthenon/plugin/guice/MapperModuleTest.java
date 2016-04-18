package com.github.sksharan.parthenon.plugin.guice;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class MapperModuleTest {
    private static Injector injector;
    private ModelMapper modelMapper;

    @BeforeClass
    public static void setUpClass() {
        injector = Guice.createInjector(new MapperModule());
    }

    @Before
    public void setUp() {
        modelMapper = injector.getInstance(ModelMapper.class);
    }

    @Test
    public void testPlayerMap() {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn("Player");
        when(player.getHealth()).thenReturn(15.0);
        when(player.getMaxHealth()).thenReturn(20.0);
        when(player.getLevel()).thenReturn(7);
        when(player.getFoodLevel()).thenReturn(12);

        PlayerModel playerModel = modelMapper.map(player, PlayerModel.class);
        assertEquals("Player", playerModel.getName());
        assertEquals(15.0, playerModel.getHealth(), 0.1);
        assertEquals(20.0, playerModel.getMaxHealth(), 0.1);
        assertEquals(7, playerModel.getExpLevel());
        assertEquals(12, playerModel.getFoodLevel());
    }
}
