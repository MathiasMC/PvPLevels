package me.MathiasMC.PvPLevels.commands;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvPTop_Command implements CommandExecutor {

    private final PvPLevels plugin;

    public PvPTop_Command(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pvptop")) {
            if (sender.hasPermission("pvplevels.command.pvptop")) {
                String type;
                if (sender instanceof Player) {
                    type = "player";
                } else {
                    type = "console";
                }
                if (args.length == 0) {
                    if (type.equalsIgnoreCase("player")) {
                        for (String message : plugin.language.get.getStringList("player.pvptop.usage")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    } else {
                        for (String message : plugin.language.get.getStringList("console.pvptop.usage")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                } else if (args.length == 1) {
                    if (type.equalsIgnoreCase("player")) {
                        Player player = (Player) sender;
                        if (args[0].equalsIgnoreCase("kills")) {
                            if (sender.hasPermission("pvplevels.command.pvptop.kills")) {
                                message(player, "player.pvptop.kills.message");
                            } else {
                                for (String message : plugin.language.get.getStringList("player.pvptop.kills.permission")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else if (args[0].equalsIgnoreCase("deaths")) {
                            if (sender.hasPermission("pvplevels.command.pvptop.deaths")) {
                                message(player, "player.pvptop.deaths.message");
                            } else {
                                for (String message : plugin.language.get.getStringList("player.pvptop.deaths.permission")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else if (args[0].equalsIgnoreCase("xp")) {
                            if (sender.hasPermission("pvplevels.command.pvptop.xp")) {
                                message(player, "player.pvptop.xp.message");
                            } else {
                                for (String message : plugin.language.get.getStringList("player.pvptop.xp.permission")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else if (args[0].equalsIgnoreCase("level")) {
                            if (sender.hasPermission("pvplevels.command.pvptop.level")) {
                                message(player, "player.pvptop.level.message");
                            } else {
                                for (String message : plugin.language.get.getStringList("player.pvptop.level.permission")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvptop.usage")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else {
                        for (String message : plugin.language.get.getStringList("console.pvptop.usage")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                } else if (args.length == 2) {
                    if (type.equalsIgnoreCase("console")) {
                        Player target = PvPLevels.call.getServer().getPlayer(args[1]);
                        if (target != null) {
                            if (args[0].equalsIgnoreCase("kills")) {
                                message(target, "console.pvptop.kills.message");
                            } else if (args[0].equalsIgnoreCase("deaths")) {
                                message(target, "console.pvptop.deaths.message");
                            } else if (args[0].equalsIgnoreCase("xp")) {
                                message(target, "console.pvptop.xp.message");
                            } else if (args[0].equalsIgnoreCase("level")) {
                                message(target, "console.pvptop.level.message");
                            } else {
                                for (String message : plugin.language.get.getStringList("console.pvptop.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("console.pvptop.online")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else {
                        for (String message : plugin.language.get.getStringList("player.pvptop.usage")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                } else {
                    if (type.equalsIgnoreCase("console")) {
                        for (String message : plugin.language.get.getStringList("console.pvptop.usage")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    } else {
                        for (String message : plugin.language.get.getStringList("player.pvptop.usage")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                }
            } else {
                for (String message : plugin.language.get.getStringList("player.pvptop.permission")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
        return true;
    }

    private void message(Player player, String path) {
        for (String message : plugin.language.get.getStringList(path)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.PlaceholderReplace(player, message)));
        }
    }
}