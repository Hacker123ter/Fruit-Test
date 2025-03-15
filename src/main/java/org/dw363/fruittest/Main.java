package org.dw363.fruittest;

import org.bukkit.plugin.java.JavaPlugin;
import org.dw363.fruittest.commands.AppleCommand;
import org.dw363.fruittest.listeners.*;
import org.dw363.fruittest.tabcompleters.AppleTabCompleter;

public final class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("apple").setExecutor(new AppleCommand());
        getCommand("apple").setTabCompleter(new AppleTabCompleter());

        getServer().getPluginManager().registerEvents(new AppleListener(), this);
        getServer().getPluginManager().registerEvents(new AppleDeathListener(), this);
        getServer().getPluginManager().registerEvents(new FruitBombListener(), this);
    }

    @Override
    public void onDisable() {
    }

    public static Main getInstance() {
        return instance;
    }
}