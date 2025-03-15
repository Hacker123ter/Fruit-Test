package org.dw363.fruittest.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.dw363.fruittest.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppleListener implements Listener {
    @EventHandler
    public void onPlayerEat(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        ItemMeta meta = item.getItemMeta();

        if (meta != null && meta.hasDisplayName() &&
                meta.getDisplayName().equals(ChatColor.DARK_RED + "Проклятое яблоко")) {
            event.setCancelled(true);
            Player player = event.getPlayer();

            double currentMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(currentMaxHealth + 20);

            player.getInventory().getItemInMainHand().setAmount(0);

            ItemStack slot9 = player.getInventory().getItem(8);
            if (slot9 != null && slot9.getType() != Material.AIR) {
                HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(slot9);
                if (!leftover.isEmpty()) {
                    leftover.values().forEach(remainingItem ->
                            player.getWorld().dropItemNaturally(player.getLocation(), remainingItem)
                    );
                }
                player.getInventory().setItem(8, null);
            }

            ItemStack bomb = new ItemStack(Material.GOLDEN_APPLE, 1);
            ItemMeta bombMeta = bomb.getItemMeta();
            if (bombMeta != null) {
                bombMeta.setDisplayName(ChatColor.GOLD + "Фрукт Бомба");
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Мастерство: \"мастерство игрока\"");
                lore.add(ChatColor.GRAY + "Нажмите ПКМ для перехода в режим боя");
                bombMeta.setLore(lore);
                if (bombMeta.getAttributeModifiers() != null) {
                    bombMeta.getAttributeModifiers().clear();
                }
                NamespacedKey bombKey = new NamespacedKey(Main.getInstance(), "bomb");
                bombMeta.getPersistentDataContainer().set(bombKey, PersistentDataType.BYTE, (byte) 1);
                bomb.setItemMeta(bombMeta);
            }


            player.getInventory().setItem(8, bomb);
        }
    }
}