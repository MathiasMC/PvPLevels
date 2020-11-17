package me.MathiasMC.PvPLevels.commands;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.gui.AdminGUI;
import me.MathiasMC.PvPLevels.gui.ProfilesGUI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
                            plugin.getFileUtils().loadProfiles();
                            plugin.getFileUtils().loadAdmin();
                            if (plugin.isLevelsValid()) {
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
                                    for (String message : plugin.getFileUtils().language.getStringList("reload.validator")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                    }
                                } else {
                                    for (String message : plugin.getFileUtils().language.getStringList("console.reload.validator")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            if (type.equalsIgnoreCase("player")) {
                                for (String message : plugin.getFileUtils().language.getStringList("reload.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("admin")) {
                        unknown = false;
                        if (type.equalsIgnoreCase("player")) {
                            if (sender.hasPermission("pvplevels.admin.admin")) {
                                final Player player = (Player) sender;
                                new AdminGUI(plugin.getPlayerMenu(player)).open();
                            } else {
                                for (String message : plugin.getFileUtils().language.getStringList("admin.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        } else {
                            for (String message : plugin.getFileUtils().language.getStringList("console.only-player")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("profiles")) {
                        unknown = false;
                        if (type.equalsIgnoreCase("player")) {
                            if (sender.hasPermission("pvplevels.player.profiles")) {
                                final Player player = (Player) sender;
                                new ProfilesGUI(plugin.getPlayerMenu(player)).open();
                            } else {
                                for (String message : plugin.getFileUtils().language.getStringList("profiles.permission")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", sender.getName())));
                                }
                            }
                        } else {
                            for (String message : plugin.getFileUtils().language.getStringList("console.only-player")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
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
                                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
                                    } else {
                                        for (String message : text.split(Pattern.quote("\\n"))) {
                                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
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
                                        try {
                                            int seconds = Integer.parseInt(args[2]);
                                            new BukkitRunnable() {
                                                int time = 0;

                                                @Override
                                                public void run() {
                                                    if (time >= seconds) {
                                                        this.cancel();
                                                    } else {
                                                        time++;
                                                        target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(target, false, sb.toString().trim()))));
                                                    }
                                                }
                                            }.runTaskTimer(plugin, 0, 20);
                                        } catch (NoSuchMethodError exception) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis command is not supported in this version."));
                                        }
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
                                        playerConnect.setKills(0L);
                                        triggerReset(type, sender, target, "kills", 1, 0);
                                    } else if (args[1].equalsIgnoreCase("deaths")) {
                                        final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                        playerConnect.setDeaths(0L);
                                        triggerReset(type, sender, target, "deaths", 1, 0);
                                    } else if (args[1].equalsIgnoreCase("level")) {
                                        final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                        playerConnect.setLevel(plugin.getFileUtils().config.getLong("start-level"));
                                        playerConnect.setXp(0L);
                                        triggerReset(type, sender, target, "level", 1, 0);
                                    } else if (args[1].equalsIgnoreCase("killstreak")) {
                                        final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                        if (args.length > 3) {
                                            triggerReset(type, sender, target, "killstreak " + args[3], playerConnect.getKillstreak(), 0);
                                        } else {
                                            triggerReset(type, sender, target, "killstreak", playerConnect.getKillstreak(), 0);
                                        }
                                        playerConnect.setKillstreak(0L);
                                    } else if (args[1].equalsIgnoreCase("stats")) {
                                        if (args.length == 4) {
                                            final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                            playerConnect.setKills(0L);
                                            playerConnect.setDeaths(0L);
                                            playerConnect.setXp(0L);
                                            playerConnect.setLevel(plugin.getFileUtils().config.getLong("start-level"));
                                            playerConnect.setKillstreak(0L);
                                            playerConnect.setKillstreakTop(0L);
                                            if (Boolean.parseBoolean(args[2])) {
                                                playerConnect.setGroup("default");
                                            }
                                            triggerReset(type, sender, target, "stats", 1, 0);
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
                                        long number = plugin.getFileUtils().config.getLong("start-level");
                                        for (long integer : xpList(playerConnect)) {
                                            if (set >= integer && integer != 0) {
                                                number++;
                                            }
                                        }
                                        if (set >= 0) {
                                            final Set<String> stringList = plugin.getFileUtils().levels.getConfigurationSection(playerConnect.getGroup()).getKeys(false);
                                            stringList.remove("execute");
                                            final Set<Integer> list = stringList.stream().map(Integer::parseInt).collect(Collectors.toSet());
                                            final long maxXP = plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + Collections.max(list) + ".xp");
                                            playerConnect.setXp(Math.min(set, maxXP));
                                        } else {
                                            playerConnect.setXp(0L);
                                        }
                                            if (playerConnect.getLevel() != number) {
                                                if (plus) {
                                                    playerConnect.setLevel(number - 1);
                                                    plugin.getXPManager().sendCommands(target, plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + number + ".execute") + ".level.up", plugin.getFileUtils().execute, "", 0, 0, 0, 0);
                                                    playerConnect.setLevel(number);
                                                } else {
                                                    playerConnect.setLevel(number);
                                                    plugin.getXPManager().sendCommands(target, plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + number + ".execute") + ".level.down", plugin.getFileUtils().execute, "", 0, 0, 0, 0);
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
                                        if (args[2].contains("+")) {
                                            set = playerConnect.getLevel() + Long.parseLong(args[2].replace("+", ""));
                                        } else if (args[2].contains("-")) {
                                            set = playerConnect.getLevel() - Long.parseLong(args[2].replace("-", ""));
                                        }
                                        if (set >= plugin.getFileUtils().config.getLong("start-level") && plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + set)) {
                                            playerConnect.setLevel(set - 1);
                                            plugin.getXPManager().sendCommands(target, plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + set + ".execute") + ".level.up", plugin.getFileUtils().execute, "", 0, 0, 0, 0);
                                            playerConnect.setLevel(set);
                                            playerConnect.setXp(plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + set + ".xp"));
                                            playerConnect.save();
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
                                        if (plugin.getCalculateManager().isInt(args[3])) {
                                            final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                            playerConnect.setMultiplier(Double.parseDouble(args[2]));
                                            playerConnect.setMultiplierTime(Integer.parseInt(args[3]));
                                            playerConnect.setMultiplierTimeLeft(Integer.parseInt(args[3]));
                                            playerConnect.save();
                                            plugin.multipliers.add(target);
                                            if (type.equalsIgnoreCase("player")) {
                                                for (String message : plugin.getFileUtils().language.getStringList("multiplier.got")) {
                                                    plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName()).replace("{player}", sender.getName()).replace("{multiplier}", args[2]).replace("{time}", plugin.getStatsManager().getTime("multiplier", new GregorianCalendar(0, 0, 0, 0, 0, Integer.parseInt(args[3])).getTime().getTime()))));
                                                }
                                            } else {
                                                for (String message : plugin.getFileUtils().language.getStringList("console.multiplier.got")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName()).replace("{multiplier}", args[2]).replace("{time}", plugin.getStatsManager().getTime("multiplier", new GregorianCalendar(0, 0, 0, 0, 0, Integer.parseInt(args[3])).getTime().getTime()))));
                                                }
                                            }
                                            for (String message : plugin.getFileUtils().language.getStringList("multiplier.target")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName()).replace("{multiplier}", args[2]).replace("{time}", plugin.getStatsManager().getTime("multiplier", new GregorianCalendar(0, 0, 0, 0, 0, Integer.parseInt(args[3])).getTime().getTime()))));
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

    private ArrayList<Long> xpList(final PlayerConnect playerConnect) {
        final ArrayList<Long> xp = new ArrayList<>();
        for (String level : plugin.getFileUtils().levels.getConfigurationSection(playerConnect.getGroup()).getKeys(false)) {
            if (!level.equalsIgnoreCase("execute")) {
                xp.add(plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + level + ".xp"));
            }
        }
        return xp;
    }

    private void triggerReset(final String type, final CommandSender sender, final Player target, String path, final long set, final long next) {
        final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
        playerConnect.save();
        if (path.contains("killstreak")) {
            String killer = "";
            if (path.split(" ").length == 2) {
                killer = path.split(" ")[1];
            }
            if (plugin.getFileUtils().config.contains("killstreak." + playerConnect.getGroup())) {
                ArrayList<Integer> list = new ArrayList<>();
                final long killstreak = playerConnect.getKillstreak();
                for (String select : plugin.getFileUtils().config.getConfigurationSection("killstreak." + playerConnect.getGroup()).getKeys(false)) {
                    if (!select.equalsIgnoreCase("worlds") && !select.equalsIgnoreCase("get")) {
                        if (killstreak >= Integer.parseInt(select)) {
                            list.add(Integer.parseInt(select));
                        }
                    }
                }
                if (!list.isEmpty()) {
                    for (String commands : plugin.getFileUtils().config.getStringList("killstreak." + playerConnect.getGroup() + "." + +list.get(list.size() - 1) + ".lost")) {
                        plugin.getServer().dispatchCommand(plugin.consoleSender, plugin.getPlaceholderManager().replacePlaceholders(target, false, commands.replace("{lost}", String.valueOf(killstreak)).replace("{target}", killer)));
                    }
                }
            }
            path = "killstreak";
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
        if (set > next) {
            for (String message : plugin.getFileUtils().language.getStringList("reset." + path + ".target")) {
                plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{target}", target.getName())));
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