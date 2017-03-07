package me.boomboompower.alert.simplechatalert;

import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleChatAlert extends JavaPlugin {

    private static SimpleChatAlert instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SimpleChatAlert getInstance() {
        return instance;
    }
}
