package me.MathiasMC.PvPLevels.commands;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvPAdmin_Command implements CommandExecutor {

    private final PvPLevels plugin;

    public PvPAdmin_Command(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pvpadmin")) {
            String type;
            if (sender instanceof Player) {
                type = "player";
            } else {
                type = "console";
            }
            if (sender.hasPermission("pvplevels.command.pvpadmin")) {
                if (args.length == 0) {
                    if (type.equalsIgnoreCase("player")) {
                        run((Player) sender, type);
                    } else {
                        for (String message : plugin.language.get.getStringList("console.pvpadmin.usage")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                } else if (args.length == 1) {
                    Player target = PvPLevels.call.getServer().getPlayer(args[0]);
                    if (type.equalsIgnoreCase("player")) {
                        if (sender.hasPermission("pvplevels.command.pvpadmin.target")) {
                            if (target != null) {
                                run(target, type);
                            } else {
                                for (String message : plugin.language.get.getStringList("player.pvpadmin.online")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvpadmin.target.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else {
                        if (target != null) {
                            run(target, type);
                        } else {
                            for (String message : plugin.language.get.getStringList("console.pvpadmin.online")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    }
                } else {
                    for (String message : plugin.language.get.getStringList(type + ".pvpadmin.usage")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    }
                }
            } else {
                for (String message : plugin.language.get.getStringList("player.pvpadmin.permission")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
        return true;
    }

    private void run(Player target, String type) {
        for (String command : plugin.language.get.getStringList(type + ".pvpadmin.commands")) {
            plugin.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvplevels_player}", target.getName()));
        }
    }
}
