package com.github.sksharan.parthenon.common.url;

public class PlayerUrl {
    public static final String URL = "/player";

    public static String getPlayerUrl(String baseUrl, String playerName) {
        return baseUrl + PlayerUrl.URL + "/" + playerName;
    }

    public static String getAllPlayersUrl(String baseUrl) {
        return baseUrl + PlayerUrl.URL;
    }

    public static String getPlayerExistsUrl(String baseUrl, String playerName) {
        return baseUrl + PlayerUrl.URL + "/" + playerName + "/exists";
    }

    public static String savePlayerUrl(String baseUrl) {
        return baseUrl + PlayerUrl.URL;
    }

}
