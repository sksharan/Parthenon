package com.github.sksharan.parthenon.server.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import com.github.sksharan.parthenon.common.model.ItemStackModel;
import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.server.controller.PlayerController;
import com.github.sksharan.parthenon.server.dao.ItemStackRepository;
import com.github.sksharan.parthenon.server.dao.PlayerRepository;
import com.github.sksharan.parthenon.server.entity.ItemStackEntity;
import com.github.sksharan.parthenon.server.entity.PlayerEntity;

public class PlayerControllerIT extends ParthenonIntegrationTest {
    @Autowired private PlayerController playerController;
    @Autowired private PlayerRepository playerRepository;
    @Autowired private ItemStackRepository itemStackRepository;
    private static final double DELTA = 0.05;

    @Test
    @DirtiesContext
    public void testSavePlayers() {
        List<ItemStackModel> i1 = new ArrayList<ItemStackModel>();
        PlayerModel pm1 = new PlayerModel("Player1", 0, 20, 0, 0.1, 15, false, i1);
        playerController.savePlayer(pm1);

        assertEquals(1, playerRepository.count());
        checkEquals(pm1, playerRepository.findByName("Player1"));
        assertEquals(0, itemStackRepository.count());

        List<ItemStackModel> i2 = new ArrayList<ItemStackModel>();
        i2.add(new ItemStackModel("COBBLESTONE", 61, ItemStackModel.Type.GENERAL));
        PlayerModel pm2 = new PlayerModel("Player2", 0, 9, 3, 0.5, 20, false, i2);
        playerController.savePlayer(pm2);

        assertEquals(2, playerRepository.count());
        checkEquals(pm2, playerRepository.findByName("Player2"));
        assertEquals(1, itemStackRepository.count());

        List<ItemStackModel> i3 = new ArrayList<ItemStackModel>();
        i3.add(new ItemStackModel("DIAMOND_HELMET", 32, ItemStackModel.Type.EQUIPPED_HELMET));
        i3.add(new ItemStackModel("WOOD", 47, ItemStackModel.Type.GENERAL));
        PlayerModel pm3 = new PlayerModel("Player1", 2, 17, 0, 0.99, 6, true, i3);
        playerController.savePlayer(pm3);

        assertEquals(2, playerRepository.count());
        checkEquals(pm3, playerRepository.findByName("Player1"));
        assertEquals(3, itemStackRepository.count());

        List<ItemStackModel> i4 = new ArrayList<ItemStackModel>();
        i3.add(new ItemStackModel("DIAMOND_HELMET", 32, ItemStackModel.Type.EQUIPPED_HELMET));
        PlayerModel pm4 = new PlayerModel("Player1", 4, 15, 32, 0.5, 19, true, i4);
        playerController.savePlayer(pm4);

        assertEquals(2, playerRepository.count());
        checkEquals(pm4, playerRepository.findByName("Player1"));
        assertEquals(3, itemStackRepository.count());
    }

    @Test
    @DirtiesContext
    public void testSavePlayerWithDuplicateItemsMultipleTimes() {
        List<ItemStackModel> i1 = new ArrayList<ItemStackModel>();
        i1.add(new ItemStackModel("WOOD", 47, ItemStackModel.Type.GENERAL));
        i1.add(new ItemStackModel("WOOD", 47, ItemStackModel.Type.GENERAL));
        i1.add(new ItemStackModel("WOOD", 47, ItemStackModel.Type.HELD_IN_MAIN_HAND));
        i1.add(new ItemStackModel("WOOD", 48, ItemStackModel.Type.GENERAL));
        i1.add(new ItemStackModel("WOOD", 49, ItemStackModel.Type.GENERAL));
        i1.add(new ItemStackModel("WOOD", 47, ItemStackModel.Type.GENERAL));
        PlayerModel pm1 = new PlayerModel("Player", 2, 17, 0, 0.5, 6, true, i1);
        playerController.savePlayer(pm1);
        playerController.savePlayer(pm1);

        assertEquals(1, playerRepository.count());
        checkEquals(pm1, playerRepository.findByName("Player"));
        assertEquals(4, itemStackRepository.count());
    }

    @Test
    @DirtiesContext
    public void testGetPlayers() {
        List<ItemStackEntity> i1 = new ArrayList<ItemStackEntity>();
        PlayerEntity pe1 = new PlayerEntity("Player1", 13, 20, 0, 0, 12, true, i1);
        playerRepository.save(pe1);

        assertEquals(1, playerRepository.count());
        checkEquals(playerController.getPlayer("Player1").getBody(), pe1);

        List<ItemStackEntity> i2 = new ArrayList<ItemStackEntity>();
        i2.add(new ItemStackEntity("DIAMOND_HELMET", 12, ItemStackModel.Type.EQUIPPED_HELMET.name()));
        i2.add(new ItemStackEntity("WOOD", 47, ItemStackModel.Type.GENERAL.name()));
        PlayerEntity pe2 = new PlayerEntity("Player2", 4, 20, 14, 0.5, 30, true, i2);
        playerRepository.save(pe2);

        assertEquals(2, playerRepository.count());
        checkEquals(playerController.getPlayer("Player2").getBody(), pe2);

        List<ItemStackEntity> i3 = new ArrayList<ItemStackEntity>();
        i3.add(new ItemStackEntity("COBBLESTONE", 12, ItemStackModel.Type.GENERAL.name()));
        PlayerEntity pe3 = new PlayerEntity("Player1", 12, 20, 5, 0.24, 9, false, i3);
        pe3.setId(playerRepository.findByName("Player1").getId());
        playerRepository.save(pe3);

        assertEquals(2, playerRepository.count());
        checkEquals(playerController.getPlayer("Player1").getBody(), pe3);

        List<PlayerModel> players = playerController.getAllPlayers().getBody().stream()
                .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                .collect(Collectors.toList());

        assertEquals(2, players.size());
        checkEquals(players.get(0), pe3);
        checkEquals(players.get(1), pe2);
    }

    @Test
    @DirtiesContext
    public void testGetPlayerWithDuplicateItems() {
        List<ItemStackEntity> i1 = new ArrayList<ItemStackEntity>();
        i1.add(new ItemStackEntity("WOOD", 47, ItemStackModel.Type.GENERAL.name()));
        i1.add(new ItemStackEntity("WOOD", 47, ItemStackModel.Type.GENERAL.name()));
        i1.add(new ItemStackEntity("WOOD", 47, ItemStackModel.Type.HELD_IN_MAIN_HAND.name()));
        i1.add(new ItemStackEntity("WOOD", 48, ItemStackModel.Type.GENERAL.name()));
        i1.add(new ItemStackEntity("WOOD", 49, ItemStackModel.Type.GENERAL.name()));
        i1.add(new ItemStackEntity("WOOD", 47, ItemStackModel.Type.GENERAL.name()));
        PlayerEntity pe1 = new PlayerEntity("Player", 2, 17, 0, 0.5, 6, true, i1);
        playerRepository.save(pe1);

        assertEquals(1, playerRepository.count());
        checkEquals(playerController.getPlayer("Player").getBody(), pe1);
    }

    @Test
    public void testGetNonExistentPlayer() {
        assertTrue(playerController.getPlayer("SomePlayer").getStatusCode().is4xxClientError());
    }

    @Test
    @DirtiesContext
    public void testPlayerExists() {
        PlayerEntity pe1 = new PlayerEntity("Player1", 13, 20, 0, 0.5, 12, true, null);
        playerRepository.save(pe1);
        assertTrue(playerController.playerExists(pe1.getName()).getBody());
        assertFalse(playerController.playerExists("Not a player").getBody());
        assertFalse(playerController.playerExists(null).getBody());
    }

    private void checkEquals(PlayerModel pm, PlayerEntity pe) {
        assertNotNull(pm);
        assertNotNull(pe);

        assertEquals(pm.getName(), pe.getName());
        assertEquals(pm.getHealth(), pe.getHealth(), DELTA);
        assertEquals(pm.getMaxHealth(), pe.getMaxHealth(), DELTA);
        assertEquals(pm.getExpLevel(), pe.getExpLevel());
        assertEquals(pm.getCurrExpPercentage(), pe.getCurrExpPercentage(), DELTA);
        assertEquals(pm.getFoodLevel(), pe.getFoodLevel());

        assertNotNull(pm.getItems());
        assertNotNull(pe.getItems());
        assertEquals(pm.getItems().size(), pe.getItems().size());

        List<ItemStackModel> sortedPmItems = pm.getItems().stream()
                .sorted((i1, i2) -> i1.getName().compareTo(i2.getName()))
                .collect(Collectors.toList());

        List<ItemStackEntity> sortedPeItems = pe.getItems().stream()
                .sorted((i1, i2) -> i1.getName().compareTo(i2.getName()))
                .collect(Collectors.toList());

        for (int i = 0; i < pm.getItems().size(); i++) {
            assertEquals(sortedPmItems.get(i).getName(), sortedPeItems.get(i).getName());
            assertEquals(sortedPmItems.get(i).getAmount(), sortedPeItems.get(i).getAmount());
            assertEquals(sortedPmItems.get(i).getType().name(), sortedPeItems.get(i).getType());
        }
    }

}
