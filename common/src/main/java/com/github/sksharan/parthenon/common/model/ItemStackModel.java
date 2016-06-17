package com.github.sksharan.parthenon.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemStackModel {

    @JsonProperty
    private String name;

    @JsonProperty
    private int amount;

    @JsonProperty
    private Type type;

    public ItemStackModel() { }

    public ItemStackModel(String name, int amount, Type type) {
        this.name = name;
        this.amount = amount;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        EQUIPPED_BOOTS, EQUIPPED_CHESTPLATE, EQUIPPED_HELMET,
        EQUIPPED_LEGGINGS, GENERAL, HELD_IN_MAIN_HAND, HELD_IN_OFF_HAND
    }

}
