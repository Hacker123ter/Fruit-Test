package org.dw363.fruittest.listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;

public class AppleDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        double currentMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

        if (currentMaxHealth > 20) {
            double newMaxHealth = Math.max(20, currentMaxHealth - 20); // Не даем уйти ниже 20 HP
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newMaxHealth);
        }
    }
}