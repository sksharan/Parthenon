package com.github.sksharan.parthenon.plugin.schedule;

import java.util.ArrayList;
import java.util.logging.Level;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.bukkit.OfflinePlayer;

import com.github.sksharan.parthenon.common.model.ItemStackModel;
import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.common.url.PlayerUrl;
import com.github.sksharan.parthenon.plugin.ParthenonPlugin;
import com.github.sksharan.parthenon.plugin.mapper.ParthenonMapper;
import com.github.sksharan.parthenon.plugin.network.NetworkUtils;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/** Periodically updates the data for all players who have ever joined the server. */
public class SaveAllPlayersRunnable implements Runnable {
    private final ParthenonPlugin parthenonPlugin;
    private final NetworkUtils networkUtils;
    private final ParthenonMapper parthenonMapper;

    @Inject
    public SaveAllPlayersRunnable(@Assisted ParthenonPlugin parthenonPlugin,
            NetworkUtils networkUtils, ParthenonMapper parthenonMapper) {
        this.parthenonPlugin = parthenonPlugin;
        this.networkUtils = networkUtils;
        this.parthenonMapper = parthenonMapper;
    }

    @Override
    public void run() {
        try {
            for (OfflinePlayer offlinePlayer: parthenonPlugin.getServer().getOfflinePlayers()) {
                PlayerModel playerModel;
                if (offlinePlayer.isOnline()) {
                    parthenonPlugin.getLogger().log(Level.INFO,
                            offlinePlayer.getName() + " is currently online");
                    playerModel = parthenonMapper.map(offlinePlayer.getPlayer());
                } else {
                    parthenonPlugin.getLogger().log(Level.INFO,
                            offlinePlayer.getName() + " is currently offline");
                    playerModel = new PlayerModel(offlinePlayer.getName(),
                            0, 20, 0, 0, false, new ArrayList<ItemStackModel>());
                }

                String url = PlayerUrl.savePlayerUrl(parthenonPlugin.getServerBaseUrl());
                try (CloseableHttpResponse response = networkUtils.sendPostRequestJson(url, playerModel)) {
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        parthenonPlugin.getLogger().log(Level.INFO,
                                "Successfully saved info for " + playerModel.getName());
                    }
                }
            }
        } catch (Exception e) {
            parthenonPlugin.getLogger().log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
        }
    }

}
