package com.github.sksharan.parthenon.server.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "player")
public class PlayerEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "health")
    private double health;

    @Column(name = "max_health")
    private double maxHealth;

    @Column(name = "exp_level")
    private int expLevel;

    @Column(name = "curr_exp_percentage")
    private double currExpPercentage;

    @Column(name = "food_level")
    private int foodLevel;

    @Column(name = "is_online")
    private boolean isOnline;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "player_item_stack",
            joinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_stack_id", referencedColumnName = "id"))
    private List<ItemStackEntity> items;

    protected PlayerEntity() {}

    public PlayerEntity(String name, double health, double maxHealth, int expLevel, double currExpPercentage,
            int foodLevel, boolean isOnline, List<ItemStackEntity> items) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.expLevel = expLevel;
        this.currExpPercentage = currExpPercentage;
        this.foodLevel = foodLevel;
        this.isOnline = isOnline;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<ItemStackEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemStackEntity> items) {
        this.items = items;
    }

}
