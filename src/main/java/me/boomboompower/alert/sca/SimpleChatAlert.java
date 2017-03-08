package me.boomboompower.alert.sca;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleChatAlert extends JavaPlugin {

    private static SimpleChatAlert instance;

    private CommandSender console = Bukkit.getConsoleSender();

    protected static final String PREFIX = ChatColor.DARK_RED.toString() + ChatColor.BOLD + "SCA: " + ChatColor.WHITE;

    @Override
    public void onEnable() {
        try {
            instance = this;

            saveDefaultConfig();

            ConfigUtils.loadToPlugin();
            doMetrics();

            registerCommands("alert", new SimpleCommand());

            LoggingUtils.send(console, PREFIX + "&cSuccessfully loaded the plugin. Everything seems to be running smoothly!");
        } catch (Exception ex) {
            LoggingUtils.send(console, PREFIX + "&cCould not successfully load the plugin. Assuming everything is fine...");
        }
    }

    @Override
    public void onDisable() {
        instance = null; // Don't access our plugin if we have shutdown

        LoggingUtils.send(console, PREFIX + "&cThe plugin has been shutdown.");
    }

    private void doMetrics() {
        if (ConfigUtils.USE_METRICS) {
            MetricsLite metrics = new MetricsLite(this);
        }
    }

    private void registerCommands(Object command, Object clazz) {
        getCommand((String) command).setExecutor((CommandExecutor) clazz);
    }

    protected static SimpleChatAlert getInstance() {
        return instance;
    }
}
