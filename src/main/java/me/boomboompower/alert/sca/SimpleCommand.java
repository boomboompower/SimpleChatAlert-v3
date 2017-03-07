package me.boomboompower.alert.sca;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SimpleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player && !sender.hasPermission("sca.alert")) {
            LoggingUtils.send(sender, ConfigUtils.INVALID_PERMS);
            return true;
        }

        if (args.length == 0) {
            LoggingUtils.send(sender, ConfigUtils.INVALID_USAGE);
        } else {
            if (args[0].equalsIgnoreCase("reload")) {
                ConfigUtils.loadToPlugin();
                LoggingUtils.send(sender, SimpleChatAlert.PREFIX + "Config has been reloaded!");
            } else {
                LoggingUtils.sendToAll(ConfigUtils.CMD_PREFIX + build(args));
            }
        }

        return true;
    }

    private String build(String[] args) {
        StringBuilder builder = new StringBuilder();

        for (String s : args) {
            builder.append(s);
            builder.append(" ");
        }

        return builder.toString().trim();
    }
}
