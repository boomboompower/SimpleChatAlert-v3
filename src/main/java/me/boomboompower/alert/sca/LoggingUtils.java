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

    protected static void log(String message) {
        send(Bukkit.getConsoleSender(), message);
    }

    protected static void send(CommandSender sender, String message) {
        sender.sendMessage(translate(message));
    }

    protected static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    protected static void sendToAll(String message) {
        message = goThroughFilters(message);
        for (Player p : Bukkit.getOnlinePlayers()) {
            send(p, message.replace("{PLAYER}", p.getName())); // Throwing in our custom hook as well
        }
        send(Bukkit.getConsoleSender(), message);
    }

    protected static void sendTitleToAll(String title, String subtitle, Integer fadeIn, Integer stay, Integer fadeOut) {
        title = goThroughFilters(title);
        subtitle = goThroughFilters(subtitle);
        try {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(title.replace("{PLAYER}", p.getName()), subtitle.replace("{PLAYER}", p.getName()), fadeIn, stay, fadeOut);
            }
        } catch (Exception e) {
            log("&cAssuming server is running an older version of Bukkit");
            log("&cIgnoring timings due to this.");
            for (Player p : Bukkit.getOnlinePlayers()) {
                // Use deprecated method if the above one cannot be found
                p.sendTitle(title.replace("{PLAYER}", p.getName()), subtitle.replace("{PLAYER}", p.getName()));
            }
        }
    }

    protected static String goThroughFilters(String message) {
        message = message.replace("{MESSAGE}", message); // Throwing this in as well, an older feature requested
        for (int i = 0; i < placeholders.size(); i++) {
            String toBeReplaced = String.valueOf(placeholders.keySet().toArray()[i]);
            String toReplaceWith = String.valueOf(((Map.Entry) placeholders.entrySet().toArray()[i]).getValue());
            message = message.replace(toBeReplaced, toReplaceWith);
        }
        return (ConfigUtils.CMD_PREFIX + message).replace("{MESSAGE}", ""); // NO MORE {MESSAGE} !
    }
}
