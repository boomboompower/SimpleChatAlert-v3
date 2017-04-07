package me.boomboompower.alert.sca;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MessagingAPI {

    private Logger logger;

    public MessagingAPI(JavaPlugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null!");
        }

        this.logger = plugin.getLogger();

        logger.log(Level.INFO, String.format("Successfully registered \'%s\' ! (Package: \'%s\')", plugin.getName(), plugin.getClass().getPackage()));
    }

    public void addPlaceholder(String textToReplace, String toReplaceWith) {
        LoggingUtils.placeholders.put(textToReplace, toReplaceWith);

        logger.log(Level.INFO, String.format("Registered \'%s\' as a placeholder to SimpleChatAlert.", textToReplace));
    }

    public Object getFromConfig(ConfigType configurationSection) {
        return configurationSection.getReturnType();
    }

    public enum ConfigType {
        PERMISSION(ConfigUtils.INVALID_PERMS),
        PREFIX(ConfigUtils.CMD_PREFIX),
        USAGE(ConfigUtils.INVALID_USAGE),
        TITLE_USE(ConfigUtils.USE_TITLE),
        TITLE_MAIN(ConfigUtils.MAIN_TITLE),
        TITLE_SUB(ConfigUtils.SUB_TITLE),
        TITLE_FADEIN(ConfigUtils.FADEIN),
        TITLE_STAY(ConfigUtils.STAY),
        TITLE_FADEOUT(ConfigUtils.FADEOUT),
        METRICS(ConfigUtils.USE_METRICS);

        Object returnType;

        ConfigType(String returnType) {
            this.returnType = returnType;
        }

        ConfigType(Boolean returnType) {
            this.returnType = returnType;
        }

        ConfigType(Integer returnType) {
            this.returnType = returnType;
        }

        public Object getReturnType() {
            return returnType;
        }
    }
}
