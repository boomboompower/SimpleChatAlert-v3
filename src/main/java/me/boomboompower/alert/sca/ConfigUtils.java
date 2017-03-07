package me.boomboompower.alert.sca;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {

    public static String CMD_PREFIX;
    public static String INVALID_PERMS;
    public static String INVALID_USAGE;

    public static boolean USE_METRICS;

    private ConfigUtils() {
    }

    public static void loadToPlugin() {
        SimpleChatAlert.getInstance().reloadConfig();

        CMD_PREFIX = getString("prefix"); //!= null ? getString("prefix") : "&4&lALERT: &c";
        INVALID_PERMS = getString("permissions"); //!= null ? getString("permissions") : "&cYou do not have permission to use this command!";
        INVALID_USAGE = getString("usage"); //!= null ? getString("usage") : "&cInvalid usage, use /alert <message, reload>";

        USE_METRICS = getBoolean("metrics");
    }

    public static String getString(String path) {
        return getConfig().getString(path);
    }

    public static boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }

    public static FileConfiguration getConfig() {
        return SimpleChatAlert.getInstance().getConfig();
    }
}
