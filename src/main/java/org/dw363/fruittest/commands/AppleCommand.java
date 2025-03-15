package org.dw363.fruittest.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AppleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Эту команду может использовать только игрок!");
            return true;
        }

        Player player = (Player) sender;
        ItemStack cursedApple = new ItemStack(Material.GOLDEN_APPLE, 1);
        ItemMeta meta = cursedApple.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.DARK_RED + "Проклятое яблоко");
            cursedApple.setItemMeta(meta);
        }

        player.getInventory().addItem(cursedApple);
        player.sendMessage(ChatColor.GRAY + "Ты получил " + ChatColor.DARK_RED + "Проклятое яблоко!");

        return true;
    }
}