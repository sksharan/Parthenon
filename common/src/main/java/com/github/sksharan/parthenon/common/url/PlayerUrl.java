package com.github.sksharan.parthenon.common.url;

public class PlayerUrl {
    private static final String BASE = ParthenonUrl.BASE_URL + ParthenonUrl.PLAYER;

    public static String getPlayerUrl(String playerName) {
        return BASE + "/" + playerName;
    }

    public static String getAllPlayersUrl() {
        return BASE;
    }

    public static String savePlayerUrl() {
        return BASE;
    }

    public static String updatePlayerOnlineUrl(String playerName) {
        return BASE + "/" + playerName + "/online";
    }

}
