package com.github.sksharan.parthenon.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "player")
public class PlayerEntity {

    @Id
    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "health")
    private double health;

    @NotNull
    @Column(name = "max_health")
    private double maxHealth;

    @NotNull
    @Column(name = "exp_level")
    private int expLevel;

    @NotNull
    @Column(name = "food_level")
    private int foodLevel;

    public PlayerEntity() {
    }

    public PlayerEntity(String name, double health, double maxHealth, int expLevel, int foodLevel) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.expLevel = expLevel;
        this.foodLevel = foodLevel;
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

}
