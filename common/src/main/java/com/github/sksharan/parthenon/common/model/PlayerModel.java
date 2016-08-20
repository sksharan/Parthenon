package com.github.sksharan.parthenon.common.model;

import java.util.List;

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
    private double currExpPercentage;

    @JsonProperty
    private int foodLevel;

    @JsonProperty
    private boolean isOnline;

    @JsonProperty
    private List<ItemStackModel> items;

    public PlayerModel() { }

    public PlayerModel(String name, double health, double maxHealth, int expLevel,
            double currExpPercentage, int foodLevel, boolean isOnline, List<ItemStackModel> items) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.expLevel = expLevel;
        this.currExpPercentage = currExpPercentage;
        this.foodLevel = foodLevel;
        this.isOnline = isOnline;
        this.items = items;
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

    public double getCurrExpPercentage() {
        return currExpPercentage;
    }

    public void setCurrExpPercentage(double currExpPercentage) {
        this.currExpPercentage = currExpPercentage;
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

    public List<ItemStackModel> getItems() {
        return items;
    }

    public void setItems(List<ItemStackModel> items) {
        this.items = items;
    }

}
