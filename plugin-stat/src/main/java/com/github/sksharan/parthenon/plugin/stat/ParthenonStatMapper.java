package com.github.sksharan.parthenon.plugin.stat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.sksharan.parthenon.common.model.ItemStackModel;
import com.github.sksharan.parthenon.common.model.PlayerModel;

public class ParthenonStatMapper {

    public PlayerModel map(Player player) {
        String name = player.getName();
        double health = player.getHealth();
        double maxHealth = player.getMaxHealth();
        int expLevel = player.getLevel();
        double currExpPercentage = player.getExp();
        int foodLevel = player.getFoodLevel();
        boolean isOnline = player.isOnline();

        boolean assignedBoots = false;
        boolean assignedChestplate = false;
        boolean assignedHelmet = false;
        boolean assignedLeggings = false;
        boolean assignedMainHand = false;
        boolean assignedOffHand = false;

        List<ItemStackModel> items = new ArrayList<ItemStackModel>();
        PlayerInventory inv = player.getInventory();
        Iterator<ItemStack> iter = inv.iterator();
        while (iter.hasNext()) {
            ItemStack itemStack = iter.next();
            if (itemStack != null && itemStack.getType() != Material.AIR) {
                if (!assignedBoots && inv.getBoots() != null
                        && itemStack.getType() == inv.getBoots().getType()) {
                    assignedBoots = true;
                    items.add(map(itemStack, ItemStackModel.Type.EQUIPPED_BOOTS));

                } else if (!assignedChestplate && inv.getChestplate() != null
                        && itemStack.getType() == inv.getChestplate().getType()) {
                    assignedChestplate = true;
                    items.add(map(itemStack, ItemStackModel.Type.EQUIPPED_CHESTPLATE));

                } else if (!assignedHelmet && inv.getHelmet() != null
                        && itemStack.getType() == inv.getHelmet().getType()) {
                    assignedHelmet = true;
                    items.add(map(itemStack, ItemStackModel.Type.EQUIPPED_HELMET));

                } else if (!assignedLeggings && inv.getLeggings() != null
                        && itemStack.getType() == inv.getLeggings().getType()) {
                    assignedLeggings = true;
                    items.add(map(itemStack, ItemStackModel.Type.EQUIPPED_LEGGINGS));

                } else if (!assignedMainHand && inv.getItemInMainHand() != null
                        && itemStack.getType() == inv.getItemInMainHand().getType()) {
                    assignedMainHand = true;
                    items.add(map(itemStack, ItemStackModel.Type.HELD_IN_MAIN_HAND));

                } else if (!assignedOffHand && inv.getItemInOffHand() != null
                        && itemStack.getType() == inv.getItemInOffHand().getType()) {
                    assignedOffHand = true;
                    items.add(map(itemStack, ItemStackModel.Type.HELD_IN_OFF_HAND));

                } else {
                    items.add(map(itemStack, ItemStackModel.Type.GENERAL));
                }
            }
        }

        return new PlayerModel(name, health, maxHealth, expLevel, currExpPercentage,
                foodLevel, isOnline, items);
    }

    public ItemStackModel map(ItemStack itemStack, ItemStackModel.Type type) {
        String name = itemStack.getData().toString();
        int amount = itemStack.getAmount();

        String materialName = itemStack.getType().name();
        if (materialName.contains("FENCE_GATE")) {
            name = materialName + " " + name;
        }
        if (materialName.equals(Material.MONSTER_EGG.name())) {
            // metaString ~ UNSPECIFIC_META:{meta-type=UNSPECIFIC, internal=H4sIAAAAAAAAAONiYOBi4HTNK8ksqQxJTOdgYMpMYWALLshMSS1iYAAAmJdvsh4AAAA=}
            String metaString = itemStack.getItemMeta().toString();
            if (metaString.length() > 48) {
                String internal = metaString.substring(48, metaString.length() - 1);
                name = internal;
            }
        }
        if (materialName.contains("POTION") || materialName.equals(Material.TIPPED_ARROW.name())) {
            // metaString ~ POTION_META:{meta-type=POTION, potion-type=minecraft:night_vision}
            String metaString = itemStack.getItemMeta().toString();
            if (metaString.length() > 53) {
                String internal = metaString.substring(53, metaString.length() - 1);
                name = materialName + " " + internal;
            }
        }

        return new ItemStackModel(name, amount, type);
    }

}
