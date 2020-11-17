package me.MathiasMC.PvPLevels.commands;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class PvPLevels_TabComplete implements TabCompleter {

    private final PvPLevels plugin;

    public PvPLevels_TabComplete(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            final List<String> commands = new ArrayList<>();
            final List<String> list = new ArrayList<>();
            if (player.hasPermission("pvplevels.player.help")) {
                if (args.length == 1) {
                    commands.add("help");
                }
            }
            if (player.hasPermission("pvplevels.admin.reload")) {
                if (args.length == 1) {
                    commands.add("reload");
                }
            }
            if (player.hasPermission("pvplevels.admin.admin")) {
                if (args.length == 1) {
                    commands.add("admin");
                }
            }
            if (player.hasPermission("pvplevels.player.profiles")) {
                if (args.length == 1) {
                    commands.add("profiles");
                }
            }
            if (player.hasPermission("pvplevels.player.stats")) {
                if (args.length == 1) {
                    commands.add("stats");
                } else if (args.length > 1 && args[0].equalsIgnoreCase("stats")) {
                    if (args.length == 2) {
                        commands.addAll(getPlayers(args[1]));
                    }
                }
            }
            if (player.hasPermission("pvplevels.player.top")) {
                if (args.length == 1) {
                    commands.add("top");
                } else if (args.length > 1 && args[0].equalsIgnoreCase("top")) {
                    if (args.length == 2) {
                        commands.add("kills");
                        commands.add("deaths");
                        commands.add("xp");
                        commands.add("level");
                        commands.add("killstreak");
                        commands.add("killstreak_top");
                    }
                }
            }
            if (player.hasPermission("pvplevels.admin.save")) {
                if (args.length == 1) {
                    commands.add("save");
                }
            }
            if (player.hasPermission("pvplevels.admin.broadcast")) {
                if (args.length == 1) {
                    commands.add("broadcast");
                } else if (args.length > 1 && args[0].equalsIgnoreCase("broadcast")) {
                    if (args.length == 2) {
                        commands.add("null");
                    } else if (args.length == 3) {
                        commands.add("text");
                    }
                }
            }
            if (player.hasPermission("pvplevels.admin.message")) {
                if (args.length == 1) {
                    commands.add("message");
                } else if (args.length > 1 && args[0].equalsIgnoreCase("message")) {
                    if (args.length == 2) {
                        commands.addAll(getPlayers(args[1]));
                    } else if (args.length == 3) {
                        commands.add("text");
                    }
                }
            }
            if (player.hasPermission("pvplevels.admin.actionbar")) {
                if (args.length == 1) {
                    commands.add("actionbar");
                } else if (args.length > 1 && args[0].equalsIgnoreCase("actionbar")) {
                    if (args.length == 2) {
                        commands.addAll(getPlayers(args[1]));
                    } else if (args.length == 3) {
                        commands.add("seconds");
                    } else if (args.length == 4) {
                        commands.add("text");
                    }
                }
            }
            if (player.hasPermission("pvplevels.admin.group")) {
                if (args.length == 1) {
                    commands.add("group");
                } else if (args.length > 1 && args[0].equalsIgnoreCase("group")){
                    if (args.length == 2) {
                        commands.add("set");
                        commands.add("reset");
                    } else if (args.length == 3) {
                        commands.addAll(getPlayers(args[2]));
                    } else if (args.length == 4) {
                        if (args[1].equalsIgnoreCase("set")) {
                            commands.addAll(plugin.getFileUtils().levels.getConfigurationSection("").getKeys(false));
                        }
                    }
                }
            }
            if (player.hasPermission("pvplevels.admin.reset")) {
                if (args.length == 1) {
                    commands.add("reset");
                } else if (args.length > 1 && args[0].equalsIgnoreCase("reset")){
                    if (args.length == 2) {
                        commands.add("kills");
                        commands.add("deaths");
                        commands.add("level");
                        commands.add("killstreak");
                        commands.add("stats");
                    } else if (args.length == 3) {
                        commands.addAll(getPlayers(args[2]));
                    } else if (args.length == 4) {
                        if (args[1].equalsIgnoreCase("stats")) {
                            commands.add("false");
                            commands.add("true");
                        }
                    }
                }
            }
            if (player.hasPermission("pvplevels.admin.xp")) {
                if (args.length == 1) {
                    commands.add("xp");
                } else if (args.length > 1 && args[0].equalsIgnoreCase("xp")){
                    if (args.length == 2) {
                        commands.addAll(getPlayers(args[1]));
                    } else if (args.length == 3) {
                        commands.add("+-amount");
                    }
                }
            }
            if (player.hasPermission("pvplevels.admin.level")) {
                if (args.length == 1) {
                    commands.add("level");
                } else if (args.length > 1 && args[0].equalsIgnoreCase("level")){
                    if (args.length == 2) {
                        commands.addAll(getPlayers(args[1]));
                    } else if (args.length == 3) {
                        commands.add("+-amount");
                    }
                }
            }
            if (player.hasPermission("pvplevels.admin.multiplier")) {
                if (args.length == 1) {
                    commands.add("multiplier");
                } else if (args.length > 1 && args[0].equalsIgnoreCase("multiplier")){
                    if (args.length == 2) {
                        commands.addAll(getPlayers(args[1]));
                    } else if (args.length == 3) {
                        commands.add("1.8");
                    } else if (args.length == 4) {
                        commands.add("seconds");
                    }
                }
            }
            StringUtil.copyPartialMatches(args[args.length - 1], commands, list);
            Collections.sort(list);
            return list;
        }
        return null;
    }

    private List<String> getPlayers(String startsWith) {
        final List<String> list = new ArrayList<>();
        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            if (onlinePlayer.isOnline()) {
                final String name = onlinePlayer.getName();
                if (name.startsWith(startsWith)) {
                    list.add(name);
                }
            }
        }
        return list;
    }
}
