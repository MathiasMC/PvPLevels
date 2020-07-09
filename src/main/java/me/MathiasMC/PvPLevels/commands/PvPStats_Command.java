package me.MathiasMC.PvPLevels.commands;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvPStats_Command implements CommandExecutor {

    private final PvPLevels plugin;

    public PvPStats_Command(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pvpstats")) {
            String type;
            if (sender instanceof Player) {
                type = "player";
            } else {
                type = "console";
            }
            if (sender.hasPermission("pvplevels.command.pvpstats")) {
                if (args.length == 0) {
                    if (type.equalsIgnoreCase("player")) {
                        Player player = (Player) sender;
                        message(player, player, "player.pvpstats.you.message");
                    } else {
                        for (String message : plugin.language.get.getStringList("console.pvpstats.usage")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                } else if (args.length == 1) {
                    Player target = plugin.getServer().getPlayer(args[0]);
                    if (type.equalsIgnoreCase("player")) {
                        if (sender.hasPermission("pvplevels.command.pvpstats.target")) {
                            if (target != null) {
                                Player player = (Player) sender;
                                message(player, target, "player.pvpstats.target.message");
                            } else {
                                for (String message : plugin.language.get.getStringList("player.pvpstats.target.online")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvpstats.target.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else {
                        if (target != null) {
                            message(target, target, "console.pvpstats.message");
                        } else {
                            for (String message : plugin.language.get.getStringList("console.pvpstats.online")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    }
                } else {
                    for (String message : plugin.language.get.getStringList(type + ".pvpstats.usage")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    }
                }
            } else {
                for (String message : plugin.language.get.getStringList("player.pvpstats.you.permission")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
        return true;
    }

    private void message(Player player, Player target, String path) {
        for (String message : plugin.language.get.getStringList(path)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.PlaceholderReplace(target, message)));
        }
    }
}