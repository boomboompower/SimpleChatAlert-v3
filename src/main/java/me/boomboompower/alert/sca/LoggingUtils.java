package me.boomboompower.alert.sca;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class LoggingUtils {

    public static final HashMap<String, Object> hookables = new HashMap<String, Object>(); // Allow other developers to add things

    static {
        hookables.put("{PREFIX}", SimpleChatAlert.PREFIX);
    }

    public LoggingUtils() {
    }

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(translate(message));
    }

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendToAll(String message) {
        message = goThroughFilters(message);
        for (Player p : Bukkit.getOnlinePlayers()) {
            send(p, message.replace("{PLAYER}", p.getName())); // Throwing in our custom hook as well
        }
        send(Bukkit.getConsoleSender(), message);
    }

    public static String goThroughFilters(String message) {
        for (int i = 0; i < hookables.size(); i++) {
            String toBeReplaced = String.valueOf(hookables.keySet().toArray()[i]);
            String toReplaceWith = String.valueOf(((Map.Entry) hookables.entrySet().toArray()[i]).getValue());
            message = message.replace(toBeReplaced, toReplaceWith);
            message = message.replace("{MESSAGE}", message); // Throwing this in as well, an older feature
        }
        return (ConfigUtils.CMD_PREFIX + message).replace("{MESSAGE}", ""); // NO MORE {MESSAGE} !
    }
}
