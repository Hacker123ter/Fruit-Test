package org.dw363.fruittest.listeners;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.dw363.fruittest.Main;

import java.util.HashMap;
import java.util.UUID;

public class FruitBombListener implements Listener {

    private HashMap<UUID, ItemStack> bombCache = new HashMap<>();
    private NamespacedKey bombKey = new NamespacedKey(Main.getInstance(), "bomb");

    private boolean isBomb(ItemStack item) {
        if (item == null || item.getType() != Material.GOLDEN_APPLE) return false;
        if (!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().has(bombKey, PersistentDataType.BYTE);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (isBomb(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack currentItem = event.getCurrentItem();
        if (isBomb(currentItem)) {
            event.setCancelled(true);
            if (event.getWhoClicked() instanceof Player) {
            }
        }
    }

    @EventHandler
    public void onPlayerConsumeBomb(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (isBomb(item)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.getDrops().removeIf(item -> isBomb(item));

        Player player = event.getEntity();
        if (isBomb(player.getInventory().getItem(8))) {
            player.getInventory().setItem(8, null);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (bombCache.containsKey(player.getUniqueId())) {
            ItemStack bomb = bombCache.get(player.getUniqueId());
            if (player.getInventory().getItem(8) == null || player.getInventory().getItem(8).getType() == Material.AIR) {
                player.getInventory().setItem(8, bomb);
            } else {
                HashMap<Integer, ItemStack> left = player.getInventory().addItem(bomb);
                if (!left.isEmpty()) {
                    left.values().forEach(item ->
                            player.getWorld().dropItemNaturally(player.getLocation(), item)
                    );
                }
            }
            bombCache.remove(player.getUniqueId());
        }
    }
}