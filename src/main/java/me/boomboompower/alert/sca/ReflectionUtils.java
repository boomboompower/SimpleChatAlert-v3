package me.boomboompower.alert.sca;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReflectionUtils {

    private Player player;

    public ReflectionUtils(Player player) {
        this.player = player;
    }

    public void sendActionbar(String message) {
        if (player != null && message != null) {
            try {
                Object actionBarJSON = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + message + "\"}");
                Object packet = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), byte.class).newInstance(actionBarJSON, (byte) 2);

                Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
                Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);

                playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
            } catch (Exception ex) {
                LoggingUtils.log("Could not send an actionbar to: " + player.getName());
                LoggingUtils.log("Error is: " + ex.getMessage() != null ? ex.getLocalizedMessage() : "Unknown");
            }
        }
    }

    protected Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    protected static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }
}
