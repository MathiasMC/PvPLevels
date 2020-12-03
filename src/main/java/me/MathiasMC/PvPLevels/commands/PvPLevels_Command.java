package me.MathiasMC.PvPLevels.commands;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.api.events.*;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.utils.GenerateThread;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class PvPLevels_Command implements CommandExecutor {

    private final PvPLevels plugin;

    public PvPLevels_Command(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pvplevels")) {
            boolean unknown = true;
            String type;
            if (sender instanceof Player) {
                type = "player";
            } else {
                type = "console";
            }
            if (sender.hasPermission("pvplevels")) {
                if (args.length == 0) {
                    if (type.equalsIgnoreCase("player")) {
                        for (String message : plugin.getFileUtils().language.getStringList("command.message")) {
                            plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName()).replace("{version}", plugin.getDescription().getVersion())));
                        }
                    } else {
                        for (String message : plugin.getFileUtils().language.getStringList("console.command.message")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{version}", plugin.getDescription().getVersion())));
                        }
                    }
                } else {
                    if (args[0].equalsIgnoreCase("help")) {
                        unknown = false;
                        if (type.equalsIgnoreCase("player")) {
                            if (sender.hasPermission("")) {
                                for (String message : plugin.getFileUtils().language.getStringList("help.admin")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            } else if (sender.hasPermission("")) {
                                for (String message : plugin.getFileUtils().language.getStringList("help.player")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            } else {
                                for (String message : plugin.getFileUtils().language.getStringList("help.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        } else {
                            for (String message : plugin.getFileUtils().language.getStringList("console.help.message")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("reload")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.reload")) {
                            plugin.getFileUtils().loadConfig();
                            plugin.getFileUtils().loadLanguage();
                            plugin.getFileUtils().loadLevels();
                            plugin.getFileUtils().loadExecute();
                            if (type.equalsIgnoreCase("player")) {
                                for (String message : plugin.getFileUtils().language.getStringList("reload.all")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            } else {
                                for (String message : plugin.getFileUtils().language.getStringList("console.reload.all")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            if (type.equalsIgnoreCase("player")) {
                                for (String message : plugin.getFileUtils().language.getStringList("reload.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("top")) {
                        unknown = false;
                        if (type.equalsIgnoreCase("player")) {
                            final Player player = (Player) sender;
                            if (sender.hasPermission("pvplevels.player.top")) {
                                if (args.length == 2) {
                                    if (args[1].equalsIgnoreCase("kills")) {
                                        for (String message : plugin.getFileUtils().language.getStringList("top.kills")) {
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(player, false, message)));
                                        }
                                    } else if (args[1].equalsIgnoreCase("deaths")) {
                                        for (String message : plugin.getFileUtils().language.getStringList("top.deaths")) {
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(player, false, message)));
                                        }
                                    } else if (args[1].equalsIgnoreCase("xp")) {
                                        for (String message : plugin.getFileUtils().language.getStringList("top.xp")) {
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(player, false, message)));
                                        }
                                    } else if (args[1].equalsIgnoreCase("level")) {
                                        for (String message : plugin.getFileUtils().language.getStringList("top.level")) {
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(player, false, message)));
                                        }
                                    } else if (args[1].equalsIgnoreCase("killstreak")) {
                                        for (String message : plugin.getFileUtils().language.getStringList("top.killstreak")) {
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(player, false, message)));
                                        }
                                    } else if (args[1].equalsIgnoreCase("killstreak_top")) {
                                        for (String message : plugin.getFileUtils().language.getStringList("top.killstreak_top")) {
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(player, false, message)));
                                        }
                                    } else {
                                        for (String message : plugin.getFileUtils().language.getStringList("top.usage")) {
                                            plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                        }
                                    }
                                } else {
                                    for (String message : plugin.getFileUtils().language.getStringList("top.usage")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                    }
                                }
                            } else {
                                for (String message : plugin.getFileUtils().language.getStringList("top.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        } else {
                            for (String message : plugin.getFileUtils().language.getStringList("console.only-player")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("stats")) {
                        unknown = false;
                        if (type.equalsIgnoreCase("player")) {
                            if (sender.hasPermission("pvplevels.player.top")) {
                                final Player player = (Player) sender;
                                if (args.length == 1) {
                                    for (String message : plugin.getFileUtils().language.getStringList("stats.message")) {
                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(player, false, message)));
                                    }
                                } else {
                                    final Player target = plugin.getServer().getPlayer(args[1]);
                                    if (target != null) {
                                        for (String message : plugin.getFileUtils().language.getStringList("stats.target")) {
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(target, false, message)));
                                        }
                                    } else {
                                        for (String message : plugin.getFileUtils().language.getStringList("stats.online")) {
                                            plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                        }
                                    }
                                }
                            } else {
                                for (String message : plugin.getFileUtils().language.getStringList("stats.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        } else {
                            for (String message : plugin.getFileUtils().language.getStringList("console.only-player")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("save")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.save")) {
                            for (String uuid : plugin.listPlayerConnect()) {
                                plugin.getPlayerConnect(uuid).save();
                            }
                            if (type.equalsIgnoreCase("player")) {
                                for (String message : plugin.getFileUtils().language.getStringList("save.message")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            } else {
                                for (String message : plugin.getFileUtils().language.getStringList("console.save.message")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            if (type.equalsIgnoreCase("player")) {
                                for (String message : plugin.getFileUtils().language.getStringList("save.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("broadcast")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.broadcast")) {
                            if (args.length > 2) {
                                final StringBuilder sb = new StringBuilder();
                                for (int i = 2; i < args.length; i++) {
                                    sb.append(args[i]).append(" ");
                                }
                                final String text = sb.toString().trim();
                                if (!text.contains("\\n")) {
                                    broadcast(ChatColor.translateAlternateColorCodes('&', text), args);
                                } else {
                                    for (String message : text.split(Pattern.quote("\\n"))) {
                                        broadcast(ChatColor.translateAlternateColorCodes('&', message), args);
                                    }
                                }
                            } else {
                                if (type.equalsIgnoreCase("player")) {
                                    for (String message : plugin.getFileUtils().language.getStringList("broadcast.usage")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                    }
                                } else {
                                    for (String message : plugin.getFileUtils().language.getStringList("console.broadcast.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            if (type.equalsIgnoreCase("player")) {
                                for (String message : plugin.getFileUtils().language.getStringList("broadcast.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("message")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.message")) {
                            if (args.length <= 2) {
                                if (type.equalsIgnoreCase("player")) {
                                    for (String message : plugin.getFileUtils().language.getStringList("message.usage")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                    }
                                } else {
                                    for (String message : plugin.getFileUtils().language.getStringList("console.message.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            } else {
                                final Player target = plugin.getServer().getPlayer(args[1]);
                                if (target != null) {
                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 2; i < args.length; i++) {
                                        sb.append(args[i]).append(" ");
                                    }
                                    String text = sb.toString().trim();
                                    if (!text.contains("\\n")) {
                                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(target, false, text)));
                                    } else {
                                        for (String message : text.split(Pattern.quote("\\n"))) {
                                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(target, false, message)));
                                        }
                                    }
                                } else {
                                    if (type.equalsIgnoreCase("player")) {
                                        for (String message : plugin.getFileUtils().language.getStringList("message.online")) {
                                            plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                        }
                                    } else {
                                        for (String message : plugin.getFileUtils().language.getStringList("console.message.online")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                }
                            }
                        } else {
                            if (type.equalsIgnoreCase("player")) {
                                for (String message : plugin.getFileUtils().language.getStringList("message.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("actionbar")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.actionbar")) {
                            if (args.length > 3) {
                                final Player target = plugin.getServer().getPlayer(args[1]);
                                if (target != null) {
                                    if (plugin.getCalculateManager().isInt(args[2])) {
                                        final StringBuilder sb = new StringBuilder();
                                        for (int i = 3; i < args.length; i++) {
                                            sb.append(args[i]).append(" ");
                                        }
                                        int seconds = Integer.parseInt(args[2]);
                                        new BukkitRunnable() {
                                            int time = 0;

                                            @Override
                                            public void run() {
                                                if (time >= seconds) {
                                                    this.cancel();
                                                } else {
                                                    time++;
                                                    if (plugin.actionBar_1_8 == null) {
                                                        target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(target, false, sb.toString().trim()))));
                                                    } else {
                                                        plugin.actionBar_1_8.send(target, ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(target, false, sb.toString().trim())));
                                                    }
                                                }
                                            }
                                        }.runTaskTimer(plugin, 0, 20);
                                    } else {
                                        if (type.equalsIgnoreCase("player")) {
                                            for (String message : plugin.getFileUtils().language.getStringList("actionbar.number")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                            }
                                        } else {
                                            for (String message : plugin.getFileUtils().language.getStringList("console.actionbar.number")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    }
                                } else {
                                    if (type.equalsIgnoreCase("player")) {
                                        for (String message : plugin.getFileUtils().language.getStringList("actionbar.online")) {
                                            plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                        }
                                    } else {
                                        for (String message : plugin.getFileUtils().language.getStringList("console.actionbar.online")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                }
                            } else {
                                if (type.equalsIgnoreCase("player")) {
                                    for (String message : plugin.getFileUtils().language.getStringList("actionbar.usage")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                    }
                                } else {
                                    for (String message : plugin.getFileUtils().language.getStringList("console.actionbar.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            if (type.equalsIgnoreCase("player")) {
                                for (String message : plugin.getFileUtils().language.getStringList("actionbar.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("group")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.group")) {
                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("set")) {
                                    if (args.length == 4) {
                                        final Player target = plugin.getServer().getPlayer(args[2]);
                                        if (target != null) {
                                            if (plugin.getCalculateManager().isString(args[3]) && plugin.getFileUtils().levels.contains(args[3])) {
                                                final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                                playerConnect.setGroup(args[3]);
                                                playerConnect.save();
                                                if (type.equalsIgnoreCase("player")) {
                                                    for (String message : plugin.getFileUtils().language.getStringList("group.set.message")) {
                                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName()).replace("{target}", target.getName()).replace("{group}", args[3])));
                                                    }
                                                } else {
                                                    for (String message : plugin.getFileUtils().language.getStringList("console.group.set.message")) {
                                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{group}", args[3])));
                                                    }
                                                }
                                            } else {
                                                if (type.equalsIgnoreCase("player")) {
                                                    for (String message : plugin.getFileUtils().language.getStringList("group.valid")) {
                                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                                    }
                                                } else {
                                                    for (String message : plugin.getFileUtils().language.getStringList("console.group.valid")) {
                                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                    }
                                                }
                                            }
                                        } else {
                                            if (type.equalsIgnoreCase("player")) {
                                                for (String message : plugin.getFileUtils().language.getStringList("group.online")) {
                                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                                }
                                            } else {
                                                for (String message : plugin.getFileUtils().language.getStringList("console.group.online")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        }
                                    } else {
                                        if (type.equalsIgnoreCase("player")) {
                                            for (String message : plugin.getFileUtils().language.getStringList("group.set.usage")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                            }
                                        } else {
                                            for (String message : plugin.getFileUtils().language.getStringList("console.group.set.usage")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    }
                                } else if (args[1].equalsIgnoreCase("reset")) {
                                    if (args.length == 3) {
                                        Player target = plugin.getServer().getPlayer(args[2]);
                                        if (target != null) {
                                            PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                            playerConnect.setGroup("default");
                                            playerConnect.save();
                                            if (type.equalsIgnoreCase("player")) {
                                                for (String message : plugin.getFileUtils().language.getStringList("group.reset.message")) {
                                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName()).replace("{target}", target.getName())));
                                                }
                                            } else {
                                                for (String message : plugin.getFileUtils().language.getStringList("console.group.reset.message")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        } else {
                                            if (type.equalsIgnoreCase("player")) {
                                                for (String message : plugin.getFileUtils().language.getStringList("group.online")) {
                                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                                }
                                            } else {
                                                for (String message : plugin.getFileUtils().language.getStringList("console.group.online")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        }
                                    } else {
                                        if (type.equalsIgnoreCase("player")) {
                                            for (String message : plugin.getFileUtils().language.getStringList("group.reset.usage")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                            }
                                        } else {
                                            for (String message : plugin.getFileUtils().language.getStringList("console.group.reset.usage")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    }
                                } else {
                                    if (type.equalsIgnoreCase("player")) {
                                        for (String message : plugin.getFileUtils().language.getStringList("group.usage")) {
                                            plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                        }
                                    } else {
                                        for (String message : plugin.getFileUtils().language.getStringList("console.group.usage")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                }
                            } else {
                                if (type.equalsIgnoreCase("player")) {
                                    for (String message : plugin.getFileUtils().language.getStringList("group.usage")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                    }
                                } else {
                                    for (String message : plugin.getFileUtils().language.getStringList("console.group.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            if (type.equalsIgnoreCase("player")) {
                                for (String message : plugin.getFileUtils().language.getStringList("group.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("reset")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.reset")) {
                            if (args.length > 2) {
                                final Player target = plugin.getServer().getPlayer(args[2]);
                                if (target != null) {
                                    if (args[1].equalsIgnoreCase("kills")) {
                                        final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                        triggerReset(type, sender, target, "kills", playerConnect.getKills());
                                        playerConnect.setKills(0L);
                                    } else if (args[1].equalsIgnoreCase("deaths")) {
                                        final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                        triggerReset(type, sender, target, "deaths", playerConnect.getDeaths());
                                        playerConnect.setDeaths(0L);
                                    } else if (args[1].equalsIgnoreCase("level")) {
                                        final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                        triggerReset(type, sender, target, "level", playerConnect.getLevel());
                                        playerConnect.setLevel(plugin.getStartLevel());
                                        playerConnect.setXp(0L);
                                    } else if (args[1].equalsIgnoreCase("killstreak")) {
                                        final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                        triggerReset(type, sender, target, "killstreak", playerConnect.getKillstreak());
                                    } else if (args[1].equalsIgnoreCase("stats")) {
                                        if (args.length == 4) {
                                            final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                            playerConnect.setKills(0L);
                                            playerConnect.setDeaths(0L);
                                            playerConnect.setXp(0L);
                                            playerConnect.setLevel(plugin.getStartLevel());
                                            playerConnect.setKillstreak(0L);
                                            playerConnect.setKillstreakTop(0L);
                                            if (Boolean.parseBoolean(args[2])) {
                                                playerConnect.setGroup("default");
                                            }
                                            triggerReset(type, sender, target, "stats", 1);
                                        } else {
                                            if (type.equalsIgnoreCase("player")) {
                                                for (String message : plugin.getFileUtils().language.getStringList("reset.stats.usage")) {
                                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                                }
                                            } else {
                                                for (String message : plugin.getFileUtils().language.getStringList("console.reset.stats.usage")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        }
                                    } else {
                                        if (type.equalsIgnoreCase("player")) {
                                            for (String message : plugin.getFileUtils().language.getStringList("reset.found")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                            }
                                        } else {
                                            for (String message : plugin.getFileUtils().language.getStringList("console.reset.found")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    }
                                } else {
                                    if (type.equalsIgnoreCase("player")) {
                                        for (String message : plugin.getFileUtils().language.getStringList("reset.online")) {
                                            plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                        }
                                    } else {
                                        for (String message : plugin.getFileUtils().language.getStringList("console.reset.online")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                }
                            } else {
                                if (type.equalsIgnoreCase("player")) {
                                    for (String message : plugin.getFileUtils().language.getStringList("reset.usage")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                    }
                                } else {
                                    for (String message : plugin.getFileUtils().language.getStringList("console.reset.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            if (type.equalsIgnoreCase("player")) {
                                for (String message : plugin.getFileUtils().language.getStringList("reset.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("xp")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.xp")) {
                            if (args.length == 3) {
                                final Player target = plugin.getServer().getPlayer(args[1]);
                                if (target != null) {
                                    if (plugin.getCalculateManager().isLong(args[2])) {
                                        final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                        long set = Long.parseLong(args[2]);
                                        boolean plus = true;
                                        if (args[2].contains("+")) {
                                            set = playerConnect.getXp() + Long.parseLong(args[2].replace("+", ""));
                                        } else if (args[2].contains("-")) {
                                            set = playerConnect.getXp() - Long.parseLong(args[2].replace("-", ""));
                                            plus = false;
                                        }
                                        if (set >= plugin.getStartLevel()) {
                                            if (plus) {
                                                if (set != plugin.getStartLevel()) {
                                                    playerConnect.setXpType("");
                                                    playerConnect.setXpLast(Long.parseLong(args[2].replace("+", "").replace("-", "")));
                                                    final PlayerGetXPEvent playerGetXPEvent = new PlayerGetXPEvent(target, null, playerConnect, set);
                                                    playerGetXPEvent.execute();
                                                } else {
                                                    playerConnect.setXpLost(Long.parseLong(args[2].replace("+", "").replace("-", "")));
                                                    final PlayerLostXPEvent playerLostXPEvent = new PlayerLostXPEvent(target, null, playerConnect, set);
                                                    playerLostXPEvent.execute();
                                                }
                                            } else {
                                                playerConnect.setXpLost(Long.parseLong(args[2].replace("+", "").replace("-", "")));
                                                final PlayerLostXPEvent playerLostXPEvent = new PlayerLostXPEvent(target, null, playerConnect, set);
                                                playerLostXPEvent.execute();
                                            }
                                        }
                                        if (type.equalsIgnoreCase("player")) {
                                            for (String message : plugin.getFileUtils().language.getStringList("xp.set")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName()).replace("{xp}", String.valueOf(args[2])).replace("{player}", sender.getName())));
                                            }
                                        } else {
                                            for (String message : plugin.getFileUtils().language.getStringList("console.xp.set")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName()).replace("{xp}", String.valueOf(args[2]))));
                                            }
                                        }
                                        for (String message : plugin.getFileUtils().language.getStringList("xp.target")) {
                                            plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName()).replace("{xp}", String.valueOf(args[2]))));
                                        }
                                    } else {
                                        if (type.equalsIgnoreCase("player")) {
                                            for (String message : plugin.getFileUtils().language.getStringList("xp.number")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                            }
                                        } else {
                                            for (String message : plugin.getFileUtils().language.getStringList("console.xp.number")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    }
                                } else {
                                    if (type.equalsIgnoreCase("player")) {
                                        for (String message : plugin.getFileUtils().language.getStringList("xp.online")) {
                                            plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                        }
                                    } else {
                                        for (String message : plugin.getFileUtils().language.getStringList("console.xp.online")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                }
                            } else {
                                if (type.equalsIgnoreCase("player")) {
                                    for (String message : plugin.getFileUtils().language.getStringList("xp.usage")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                    }
                                } else {
                                    for (String message : plugin.getFileUtils().language.getStringList("console.xp.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            if (type.equalsIgnoreCase("player")) {
                                for (String message : plugin.getFileUtils().language.getStringList("xp.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("level")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.level")) {
                            if (args.length == 3) {
                                final Player target = plugin.getServer().getPlayer(args[1]);
                                if (target != null) {
                                    if (plugin.getCalculateManager().isLong(args[2])) {
                                        final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                        long set = Long.parseLong(args[2]);
                                        boolean plus = true;
                                        if (args[2].contains("+")) {
                                            set = playerConnect.getLevel() + Long.parseLong(args[2].replace("+", ""));
                                        } else if (args[2].contains("-")) {
                                            plus = false;
                                            set = playerConnect.getLevel() - Long.parseLong(args[2].replace("-", ""));
                                        }
                                        if (set >= plugin.getStartLevel() && plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + set)) {
                                            if (plus) {
                                                final PlayerLevelUPEvent playerLevelUPEvent = new PlayerLevelUPEvent(target, null, playerConnect, set);
                                                playerLevelUPEvent.setXp();
                                                playerLevelUPEvent.execute();
                                            } else {
                                                final PlayerLevelDownEvent playerLevelDownEvent = new PlayerLevelDownEvent(target, null, playerConnect, set);
                                                playerLevelDownEvent.setXp();
                                                playerLevelDownEvent.execute();
                                            }
                                            if (type.equalsIgnoreCase("player")) {
                                                for (String message : plugin.getFileUtils().language.getStringList("level.set")) {
                                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName()).replace("{level}", String.valueOf(set)).replace("{player}", sender.getName())));
                                                }
                                            } else {
                                                for (String message : plugin.getFileUtils().language.getStringList("console.level.set")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName()).replace("{level}", String.valueOf(set))));
                                                }
                                            }
                                            for (String message : plugin.getFileUtils().language.getStringList("level.target")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName()).replace("{level}", String.valueOf(set))));
                                            }
                                        } else {
                                            if (type.equalsIgnoreCase("player")) {
                                                for (String message : plugin.getFileUtils().language.getStringList("level.found")) {
                                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                                }
                                            } else {
                                                for (String message : plugin.getFileUtils().language.getStringList("console.level.found")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        }
                                    } else {
                                        if (type.equalsIgnoreCase("player")) {
                                            for (String message : plugin.getFileUtils().language.getStringList("level.number")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                            }
                                        } else {
                                            for (String message : plugin.getFileUtils().language.getStringList("console.level.number")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    }
                                } else {
                                    if (type.equalsIgnoreCase("player")) {
                                        for (String message : plugin.getFileUtils().language.getStringList("level.online")) {
                                            plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                        }
                                    } else {
                                        for (String message : plugin.getFileUtils().language.getStringList("console.level.online")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                }
                            } else {
                                if (type.equalsIgnoreCase("player")) {
                                    for (String message : plugin.getFileUtils().language.getStringList("level.usage")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                    }
                                } else {
                                    for (String message : plugin.getFileUtils().language.getStringList("console.level.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            if (type.equalsIgnoreCase("player")) {
                                for (String message : plugin.getFileUtils().language.getStringList("level.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("multiplier")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.multiplier")) {
                            if (args.length == 4) {
                                final Player target = plugin.getServer().getPlayer(args[1]);
                                if (target != null) {
                                    if (plugin.getCalculateManager().isDouble(args[2])) {
                                        if (plugin.getCalculateManager().isInt(args[3]) && Integer.parseInt(args[3]) <= 2073600) {
                                            final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                            playerConnect.setMultiplier(Double.parseDouble(args[2]));
                                            playerConnect.setMultiplierTime(Integer.parseInt(args[3]));
                                            playerConnect.setMultiplierTimeLeft(Integer.parseInt(args[3]));
                                            playerConnect.save();
                                            plugin.multipliers.add(target);
                                            if (type.equalsIgnoreCase("player")) {
                                                for (String message : plugin.getFileUtils().language.getStringList("multiplier.got")) {
                                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName()).replace("{player}", sender.getName()).replace("{xp_multiplier}", args[2]).replace("{time}", plugin.getStatsManager().getTime(Long.parseLong(args[3]) * 1000))));
                                                }
                                            } else {
                                                for (String message : plugin.getFileUtils().language.getStringList("console.multiplier.got")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName()).replace("{xp_multiplier}", args[2]).replace("{time}", plugin.getStatsManager().getTime(Long.parseLong(args[3]) * 1000))));
                                                }
                                            }
                                            for (String message : plugin.getFileUtils().language.getStringList("multiplier.target")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName()).replace("{xp_multiplier}", args[2]).replace("{time}", plugin.getStatsManager().getTime(Long.parseLong(args[3]) * 1000))));
                                            }
                                        } else {
                                            if (type.equalsIgnoreCase("player")) {
                                                for (String message : plugin.getFileUtils().language.getStringList("multiplier.number")) {
                                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                                }
                                            } else {
                                                for (String message : plugin.getFileUtils().language.getStringList("console.multiplier.number")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        }
                                    } else {
                                        if (type.equalsIgnoreCase("player")) {
                                            for (String message : plugin.getFileUtils().language.getStringList("multiplier.double")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                            }
                                        } else {
                                            for (String message : plugin.getFileUtils().language.getStringList("console.multiplier.double")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    }
                                } else {
                                    if (type.equalsIgnoreCase("player")) {
                                        for (String message : plugin.getFileUtils().language.getStringList("multiplier.online")) {
                                            plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                        }
                                    } else {
                                        for (String message : plugin.getFileUtils().language.getStringList("console.multiplier.online")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                }
                            } else {
                                if (type.equalsIgnoreCase("player")) {
                                    for (String message : plugin.getFileUtils().language.getStringList("multiplier.usage")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                    }
                                } else {
                                    for (String message : plugin.getFileUtils().language.getStringList("console.multiplier.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            if (type.equalsIgnoreCase("player")) {
                                for (String message : plugin.getFileUtils().language.getStringList("multiplier.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("generate")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.generate")) {
                            if (plugin.getFileUtils().config.getBoolean("generate.use")) {
                                if (!plugin.isGenerate) {
                                    if (type.equalsIgnoreCase("player")) {
                                        if (args.length > 2) {
                                            if (plugin.getCalculateManager().isString(args[1])) {
                                                if (plugin.getCalculateManager().isLong(args[2]) && Long.parseLong(args[2]) > 0 && Long.parseLong(args[2]) <= 50000) {
                                                    plugin.generateGroup = args[1];
                                                    plugin.generateAmount = Long.parseLong(args[2]);
                                                    for (String message : plugin.getFileUtils().language.getStringList("generate.group")) {
                                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                                    }
                                                } else {
                                                    for (String message : plugin.getFileUtils().language.getStringList("generate.number")) {
                                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                                    }
                                                }
                                            } else {
                                                for (String message : plugin.getFileUtils().language.getStringList("generate.valid")) {
                                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                                }
                                            }
                                        } else {
                                            for (String message : plugin.getFileUtils().language.getStringList("generate.usage")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                            }
                                        }
                                    } else {
                                        if (args.length > 1 && args[1].equalsIgnoreCase("confirm")) {
                                            if (plugin.generateGroup != null && plugin.generateAmount != 0) {
                                                if (plugin.getScriptEngine() != null) {
                                                    new GenerateThread(plugin, plugin.generateGroup, plugin.generateAmount, plugin.getFileUtils().config.getLong("start-level")).start();
                                                } else {
                                                    for (String message : plugin.getFileUtils().language.getStringList("console.generate.engine")) {
                                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                    }
                                                }
                                            } else {
                                                for (String message : plugin.getFileUtils().language.getStringList("console.generate.confirm")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        } else {
                                            for (String message : plugin.getFileUtils().language.getStringList("console.generate.usage")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    }
                                } else {
                                    if (type.equalsIgnoreCase("player")) {
                                        for (String message : plugin.getFileUtils().language.getStringList("generate.generate")) {
                                            plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                        }
                                    } else {
                                        for (String message : plugin.getFileUtils().language.getStringList("console.generate.generate")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                }
                            } else {
                                if (type.equalsIgnoreCase("player")) {
                                    for (String message : plugin.getFileUtils().language.getStringList("generate.use")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                    }
                                } else {
                                    for (String message : plugin.getFileUtils().language.getStringList("console.generate.use")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            if (type.equalsIgnoreCase("player")) {
                                for (String message : plugin.getFileUtils().language.getStringList("generate.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        }
                    }
                    if (unknown) {
                        if (type.equalsIgnoreCase("player")) {
                            for (String message : plugin.getFileUtils().language.getStringList("command.unknown")) {
                                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName()).replace("{command}", args[0])));
                            }
                        } else {
                            for (String message : plugin.getFileUtils().language.getStringList("console.command.unknown")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{command}", args[0])));
                            }
                        }
                    }
                }
            } else {
                if (type.equalsIgnoreCase("player")) {
                    for (String message : plugin.getFileUtils().language.getStringList("command.permission")) {
                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                    }
                }
            }
        }
        return true;
    }

    private void triggerReset(final String type, final CommandSender sender, final Player target, String path, long set) {
        final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
        if (set > 0) {
            if (path.contains("killstreak")) {
                if (plugin.getFileUtils().config.contains("killstreak." + playerConnect.getGroup())) {
                    ArrayList<Integer> list = new ArrayList<>();
                    for (String select : plugin.getFileUtils().config.getConfigurationSection("killstreak." + playerConnect.getGroup()).getKeys(false)) {
                        if (!select.equalsIgnoreCase("worlds") && !select.equalsIgnoreCase("get")) {
                            if (set >= Integer.parseInt(select)) {
                                list.add(Integer.parseInt(select));
                            }
                        }
                    }
                    if (!list.isEmpty()) {
                        set = list.get(list.size() - 1);
                    }
                    final PlayerLostKillStreakEvent playerLostKillStreakEvent = new PlayerLostKillStreakEvent(target, playerConnect, set);
                    plugin.getServer().getPluginManager().callEvent(playerLostKillStreakEvent);
                    if (!playerLostKillStreakEvent.isCancelled()) {
                        playerLostKillStreakEvent.execute();
                    }
                }
            }
            for (String message : plugin.getFileUtils().language.getStringList("reset." + path + ".target")) {
                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName())));
            }
            playerConnect.save();
        }
        if (type.equalsIgnoreCase("player")) {
            for (String message : plugin.getFileUtils().language.getStringList("reset." + path + ".reset")) {
                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName()).replace("{target}", target.getName())));
            }
        } else {
            for (String message : plugin.getFileUtils().language.getStringList("console.reset." + path + ".reset")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName())));
            }
        }
    }

    private void broadcast(String text, String[] args) {
        if (args[1].equalsIgnoreCase("null")) {
            plugin.getServer().broadcastMessage(text);
        } else {
            plugin.getServer().broadcast(text, args[1]);
        }
    }
}