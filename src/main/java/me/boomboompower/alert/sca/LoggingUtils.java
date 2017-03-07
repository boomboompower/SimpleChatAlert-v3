package me.boomboompower.alert.sca;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class LoggingUtils {

    public static final HashMap<String, String> hookables = new HashMap<String, String>(); // Allow other developers to add things

    static {
        hookables.put("\\{PREFIX}", SimpleChatAlert.PREFIX);
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
            send(p, message.replaceFirst("\\{PLAYER}", p.getName())); // Throwing in our custom hook as well
        }
    }

    public static String goThroughFilters(String message) {
        for (int i = 0; i < hookables.size(); i++) {
            message = message.replace((String) hookables.keySet().toArray()[i], (String) hookables.entrySet().toArray()[i]);
            message = message.replaceFirst("\\{MESSAGE}", message); // Throwing this in as well, an older feature
        }
        return message;
    }
}
