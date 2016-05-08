package com.github.sksharan.parthenon.plugin.schedule;

import java.util.logging.Level;

import org.apache.http.HttpStatus;
import org.bukkit.OfflinePlayer;

import com.github.sksharan.parthenon.common.model.PlayerModel;
import com.github.sksharan.parthenon.common.url.ParthenonUrl;
import com.github.sksharan.parthenon.plugin.ParthenonPlugin;
import com.github.sksharan.parthenon.plugin.mapper.ParthenonMapper;
import com.github.sksharan.parthenon.plugin.network.NetworkUtils;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/** Runnable for periodically saving the player information of every player
 *  who has ever played on the server. This is useful for determining if players
 *  are offline or online. */
public class SaveAllPlayersRunnable implements Runnable {
    private final ParthenonPlugin parthenonPlugin;
    private final NetworkUtils networkUtils;
    private final ParthenonMapper parthenonMapper;

    @Inject
    public SaveAllPlayersRunnable(@Assisted ParthenonPlugin parthenonPlugin, NetworkUtils networkUtils,
            ParthenonMapper parthenonMapper) {
        this.parthenonPlugin = parthenonPlugin;
        this.networkUtils = networkUtils;
        this.parthenonMapper = parthenonMapper;
    }

    @Override
    public void run() {
        try {
            for (OfflinePlayer offlinePlayer: parthenonPlugin.getServer().getOfflinePlayers()) {
                if (offlinePlayer.isOnline()) {
                    parthenonPlugin.getLogger().log(Level.INFO, offlinePlayer.getName() + " is currently online");
                    PlayerModel playerModel = parthenonMapper.map(offlinePlayer.getPlayer());

                    int statusCode = networkUtils.sendPostRequest(ParthenonUrl.BASE_URL+ParthenonUrl.PLAYER, playerModel);
                    if (statusCode == HttpStatus.SC_OK) {
                        parthenonPlugin.getLogger().log(Level.INFO, "Successfully saved player information for " + playerModel.getName());
                    }
                } else {
                    parthenonPlugin.getLogger().log(Level.INFO, offlinePlayer.getName() + " is currently offline");
                }
            }
        } catch (Exception e) {
            parthenonPlugin.getLogger().log(Level.SEVERE, e.getMessage());
        }
    }

}
