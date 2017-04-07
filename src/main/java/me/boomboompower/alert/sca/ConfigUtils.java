package me.boomboompower.alert.sca;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {

    protected static String CMD_PREFIX;
    protected static String INVALID_PERMS;
    protected static String INVALID_USAGE;
    protected static String ACTIONBAR_FORMAT;
    protected static String MAIN_TITLE;
    protected static String SUB_TITLE;

    protected static Boolean USE_ACTIONBAR;
    protected static Boolean USE_METRICS;
    protected static Boolean USE_TITLE;

    protected static Integer FADEIN;
    protected static Integer STAY;
    protected static Integer FADEOUT;

    protected ConfigUtils() {
    }

    public static void loadToPlugin() {
        SimpleChatAlert.getInstance().reloadConfig();

        CMD_PREFIX = getString("prefix") != null ? getString("prefix") : "&4&lALERT:&c {MESSAGE}";
        INVALID_PERMS = getString("permissions") != null ? getString("permissions") : "&cYou do not have permission to use this command!";
        INVALID_USAGE = getString("usage") != null ? getString("usage") : "&cInvalid usage, use /alert <message, reload>";
        INVALID_USAGE = getString("usage") != null ? getString("actionbar_format") : "&4&lALERT:&c {MESSAGE}";
        MAIN_TITLE = getString("maintitle") != null ? getString("maintitle") : "&4&lALERT";
        SUB_TITLE = getString("subtitle") != null ? getString("subtitle") : "&c{MESSAGE}";

        USE_ACTIONBAR = getBoolean("actionbar");
        USE_METRICS = getBoolean("metrics");
        USE_TITLE = getBoolean("title");

        FADEIN = getInteger("fadein");
        STAY = getInteger("stay");
        FADEOUT = getInteger("fadeout");
    }

    protected static String getString(String path) {
        return getConfig().getString(path);
    }

    protected static Boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }

    protected static Integer getInteger(String path) {
        return getConfig().getInt(path);
    }

    protected static FileConfiguration getConfig() {
        return SimpleChatAlert.getInstance().getConfig();
    }
}
