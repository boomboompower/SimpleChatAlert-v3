package me.boomboompower.alert.sca;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class LoggingUtils {

    protected static final HashMap<String, Object> placeholders = new HashMap<String, Object>(); // Allow other developers to add things

    static {
        placeholders.put("{PREFIX}", SimpleChatAlert.PREFIX);
    }

    protected LoggingUtils() {
    }

    public static void log(String message) {
        send(Bukkit.getConsoleSender(), message);
    }

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(translate(message));
    }

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendToAll(String message) {
        message = goThroughFilters(ConfigUtils.CMD_PREFIX + message);
        for (Player p : Bukkit.getOnlinePlayers()) {
            send(p, message.replace("{PLAYER}", p.getName())); // Throwing in our custom hook as well
        }
        send(Bukkit.getConsoleSender(), message);
    }

    public static void sendTitleToAll(final String title, final String subtitle, Integer fadeIn, Integer stay, Integer fadeOut) {
        try {
            Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle(goThroughFilters(ConfigUtils.MAIN_TITLE + title, player), goThroughFilters(ConfigUtils.SUB_TITLE + subtitle, player), fadeIn, stay, fadeOut));
        } catch (Exception e) {
            log("&cAssuming server is running an older version of Bukkit");
            log("&cIgnoring timings due to this.");
            // Use deprecated method if the above one cannot be found
            Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle(goThroughFilters(ConfigUtils.MAIN_TITLE + title, player), goThroughFilters(ConfigUtils.SUB_TITLE + subtitle, player)));
        }
    }

    public static void sendActionbarToAll(final String message) {
        try {
            Bukkit.getOnlinePlayers().forEach(player -> new ReflectionUtils(player).sendActionbar(goThroughFilters(ConfigUtils.ACTIONBAR_FORMAT + message)));
        } catch (Exception ex) {
            log("&cAn error occurred whilst sending an actionbar to all players...");
        }
    }

    protected static String goThroughFilters(String message) {
        String xmessage = message.replace("{MESSAGE}", message); // Throwing this in as well, an older feature requested
        for (int i = 0; i < placeholders.size(); i++) {
            String toBeReplaced = String.valueOf(placeholders.keySet().toArray()[i]);
            String toReplaceWith = String.valueOf(((Map.Entry) placeholders.entrySet().toArray()[i]).getValue());
            xmessage = message.replace(toBeReplaced, toReplaceWith);
        }
        return xmessage.replace("{MESSAGE}", ""); // NO MORE {MESSAGE} !
    }

    protected static String goThroughFilters(String message, Player player) {
        return goThroughFilters(message.replace("{PLAYER}", player.getName()));
    }
}
