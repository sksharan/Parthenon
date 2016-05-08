package com.github.sksharan.parthenon.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerModel {

    @JsonProperty
    private String name;

    @JsonProperty
    private double health;

    @JsonProperty
    private double maxHealth;

    @JsonProperty
    private int expLevel;

    @JsonProperty
    private int foodLevel;

    @JsonProperty
    private boolean isOnline;

    public PlayerModel() {
    }

    public PlayerModel(String name, double health, double maxHealth, int expLevel, int foodLevel, boolean isOnline) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.expLevel = expLevel;
        this.foodLevel = foodLevel;
        this.isOnline = isOnline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(int expLevel) {
        this.expLevel = expLevel;
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

}
