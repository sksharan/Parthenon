package com.github.sksharan.parthenon.plugin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bukkit.OfflinePlayer;

import com.github.sksharan.parthenon.common.client.ParthenonClient;
import com.github.sksharan.parthenon.common.model.ItemStackModel;
import com.github.sksharan.parthenon.common.model.PlayerModel;

/** Periodically updates the data for all players who have ever joined the server. */
public class SaveAllPlayersRunnable implements Runnable {
    private final ParthenonPlugin parthenonPlugin;
    private final ParthenonMapper parthenonMapper;

    public SaveAllPlayersRunnable(ParthenonPlugin parthenonPlugin, ParthenonMapper parthenonMapper) {
        this.parthenonPlugin = parthenonPlugin;
        this.parthenonMapper = parthenonMapper;
    }

    @Override
    public void run() {
        try {
            for (OfflinePlayer offlinePlayer: parthenonPlugin.getServer().getOfflinePlayers()) {
                PlayerModel playerModel;
                if (offlinePlayer.isOnline()) {
                    parthenonPlugin.logInfo("%s is currently online", offlinePlayer.getName());
                    playerModel = parthenonMapper.map(offlinePlayer.getPlayer());
                } else {
                    parthenonPlugin.logInfo("%s is currently offline", offlinePlayer.getName());
                    playerModel = getOfflinePlayerModel(offlinePlayer.getName());
                }
                ParthenonClient.getPlayerClient().savePlayer(ParthenonPlugin.BASE_URL, playerModel);
            }
        } catch (Exception e) {
            parthenonPlugin.logSevere(ExceptionUtils.getStackTrace(e));
        }
    }

    private PlayerModel getOfflinePlayerModel(String playerName) throws URISyntaxException, IOException {
        PlayerModel playerModel;
        if (ParthenonClient.getPlayerClient().playerExists(ParthenonPlugin.BASE_URL, playerName)) {
            playerModel = ParthenonClient.getPlayerClient().getPlayer(ParthenonPlugin.BASE_URL, playerName);
            playerModel.setOnline(false);
        } else {
            playerModel = new PlayerModel();
            playerModel.setName(playerName);
            playerModel.setItems(new ArrayList<ItemStackModel>());
            playerModel.setOnline(false);
        }
        return playerModel;
    }

}
