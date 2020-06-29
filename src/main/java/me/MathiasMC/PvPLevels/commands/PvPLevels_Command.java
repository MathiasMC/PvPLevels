package me.MathiasMC.PvPLevels.commands;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.gui.GUI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PvPLevels_Command implements CommandExecutor {

    private final PvPLevels plugin;

    public PvPLevels_Command(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pvplevels")) {
            if (sender.hasPermission("pvplevels.command")) {
                boolean unknown = true;
                String path;
                if (sender instanceof Player) {
                    path = "player";
                } else {
                    path = "console";
                }
                if (args.length == 0) {
                    for (String message : plugin.language.get.getStringList(path + ".pvplevels.command.message")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvplevels_version}", PvPLevels.call.getDescription().getVersion())));
                    }
                } else {
                    if (args[0].equalsIgnoreCase("help")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.command.help")) {
                            for (String message : plugin.language.get.getStringList(path + ".pvplevels.help.message")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvplevels.help.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("reload")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.command.reload")) {
                            plugin.config.load();
                            plugin.language.load();
                            plugin.levels.load();
                            plugin.boosters.load();
                            for (String message : plugin.language.get.getStringList(path + ".pvplevels.reload.reloaded")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvplevels.reload.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("broadcast")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.command.broadcast")) {
                            if (args.length > 2) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 2; i < args.length; i++) {
                                    sb.append(args[i]).append(" ");
                                }
                                String text = sb.toString().trim();
                                if (!text.contains("\\n")) {
                                    broadcast(ChatColor.translateAlternateColorCodes('&', text), args);
                                } else {
                                    for (String message : text.split(Pattern.quote("\\n"))) {
                                        broadcast(ChatColor.translateAlternateColorCodes('&', message), args);
                                    }
                                }
                            } else {
                                for (String message : plugin.language.get.getStringList(path + ".pvplevels.broadcast.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvplevels.broadcast.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("message")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.command.message")) {
                            if (args.length <= 2) {
                                for (String message : plugin.language.get.getStringList(path + ".pvplevels.message.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            } else {
                                Player target = PvPLevels.call.getServer().getPlayer(args[1]);
                                if (target != null) {
                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 2; i < args.length; i++) {
                                        sb.append(args[i]).append(" ");
                                    }
                                    String text = sb.toString().trim();
                                    if (!text.contains("\\n")) {
                                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.PlaceholderReplace(target, text)));
                                    } else {
                                        for (String message : text.split(Pattern.quote("\\n"))) {
                                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.PlaceholderReplace(target, message)));
                                        }
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList(path + ".pvplevels.message.online")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvplevels.message.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("actionbar")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.command.actionbar")) {
                            if (args.length <= 2) {
                                for (String message : plugin.language.get.getStringList(path + ".pvplevels.actionbar.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            } else {
                                Player target = PvPLevels.call.getServer().getPlayer(args[1]);
                                if (target != null) {
                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 2; i < args.length; i++) {
                                        sb.append(args[i]).append(" ");
                                    }
                                    try {
                                        target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', plugin.PlaceholderReplace(target, sb.toString().trim()))));
                                    } catch (NoSuchMethodError exception) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&bPvPLevels&7] &cThis command is not supported in this spigot version"));
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList(path + ".pvplevels.actionbar.online")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvplevels.actionbar.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("save")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.command.save")) {
                            for (String uuid : plugin.list()) {
                                plugin.get(uuid).save();
                            }
                            for (String message : plugin.language.get.getStringList(path + ".pvplevels.save.saved")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvplevels.save.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.command." + args[0])) {
                            if (args.length == 4) {
                                Player target = PvPLevels.call.getServer().getPlayer(args[2]);
                                if (target != null) {
                                    if (plugin.isInt(args[3]) && !args[3].contains("-")) {
                                        if (args[1].equalsIgnoreCase("kills")) {
                                            if (args[0].equalsIgnoreCase("set")) {
                                                setValue(sender, target, "kills", Long.valueOf(args[3]), plugin.get(target.getUniqueId().toString()), args, "set", path);
                                            } else if (args[0].equalsIgnoreCase("add")) {
                                                PlayerConnect playerConnect = plugin.get(target.getUniqueId().toString());
                                                setValue(sender, target, "kills", playerConnect.kills() + Long.parseLong(args[3]), playerConnect, args, "add", path);
                                            } else if (args[0].equalsIgnoreCase("remove")) {
                                                PlayerConnect playerConnect = plugin.get(target.getUniqueId().toString());
                                                setValue(sender, target, "kills", playerConnect.kills() - Long.parseLong(args[3]), playerConnect, args, "remove", path);
                                            }
                                        } else if (args[1].equalsIgnoreCase("deaths")) {
                                            if (args[0].equalsIgnoreCase("set")) {
                                                setValue(sender, target, "deaths", Long.valueOf(args[3]), plugin.get(target.getUniqueId().toString()), args, "set", path);
                                            } else if (args[0].equalsIgnoreCase("add")) {
                                                PlayerConnect playerConnect = plugin.get(target.getUniqueId().toString());
                                                setValue(sender, target, "deaths", playerConnect.deaths() + Long.parseLong(args[3]), playerConnect, args, "add", path);
                                            } else if (args[0].equalsIgnoreCase("remove")) {
                                                PlayerConnect playerConnect = plugin.get(target.getUniqueId().toString());
                                                setValue(sender, target, "deaths", playerConnect.deaths() - Long.parseLong(args[3]), playerConnect, args, "remove", path);
                                            }
                                        } else if (args[1].equalsIgnoreCase("xp")) {
                                            if (args[0].equalsIgnoreCase("set")) {
                                                setValue(sender, target, "xp", Long.valueOf(args[3]), plugin.get(target.getUniqueId().toString()), args, "set", path);
                                            } else if (args[0].equalsIgnoreCase("add")) {
                                                PlayerConnect playerConnect = plugin.get(target.getUniqueId().toString());
                                                setValue(sender, target, "xp", playerConnect.xp() + Long.parseLong(args[3]), playerConnect, args, "add", path);
                                            } else if (args[0].equalsIgnoreCase("remove")) {
                                                PlayerConnect playerConnect = plugin.get(target.getUniqueId().toString());
                                                setValue(sender, target, "xp", playerConnect.xp() - Long.parseLong(args[3]), playerConnect, args, "remove", path);
                                            }
                                        } else if (args[1].equalsIgnoreCase("level")) {
                                            if (args[0].equalsIgnoreCase("set")) {
                                                setValue(sender, target, "level", Long.valueOf(args[3]), plugin.get(target.getUniqueId().toString()), args, "set", path);
                                            } else if (args[0].equalsIgnoreCase("add")) {
                                                PlayerConnect playerConnect = plugin.get(target.getUniqueId().toString());
                                                setValue(sender, target, "level", playerConnect.level() + Long.parseLong(args[3]), playerConnect, args, "add", path);
                                            } else if (args[0].equalsIgnoreCase("remove")) {
                                                PlayerConnect playerConnect = plugin.get(target.getUniqueId().toString());
                                                setValue(sender, target, "level", playerConnect.level() - Long.parseLong(args[3]), playerConnect, args, "remove", path);
                                            }
                                        } else {
                                            for (String message : plugin.language.get.getStringList(path + ".pvplevels." + args[0] + ".usage")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    } else {
                                        for (String message : plugin.language.get.getStringList(path + ".pvplevels." + args[0] + ".number")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList(path + ".pvplevels." + args[0] + ".online")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            } else {
                                for (String message : plugin.language.get.getStringList(path + ".pvplevels." + args[0] + ".usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvplevels." + args[0] + ".permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("generate")) {
                        if (path.equalsIgnoreCase("console")) {
                            unknown = false;
                            if (!plugin.config.get.getBoolean("generate.disable")) {
                                if (args.length == 2) {
                                    if (plugin.isInt(args[1]) && !args[1].contains("-")) {
                                        ArrayList<Integer> xpList = new ArrayList<>();
                                        xpList.add(plugin.config.get.getInt("generate.xp.static") + plugin.random(plugin.config.get.getInt("generate.xp.min"), plugin.config.get.getInt("generate.xp.max")));
                                        for (int i = 0; i < Integer.parseInt(args[1]) - 1; i++) {
                                            xpList.add(xpList.get(i) + (plugin.config.get.getInt("generate.xp.static") + plugin.random(plugin.config.get.getInt("generate.xp.min"), plugin.config.get.getInt("generate.xp.max"))));
                                        }
                                        for (int i = 1; i <= xpList.size(); i++) {
                                            List<String> list = plugin.config.get.getStringList("generate.commands");
                                            if (plugin.config.get.getBoolean("generate.random.use")) {
                                                if (plugin.levels.get.contains("levels." + plugin.random(plugin.config.get.getInt("generate.random.min"), plugin.config.get.getInt("generate.random.max")))) {
                                                    list.add(plugin.config.get.getStringList("generate.random.commands").get(plugin.random(0, plugin.config.get.getStringList("generate.random.commands").size() - 1)));
                                                }
                                            }
                                            plugin.levels.get.set("levels." + i + ".commands", list);
                                            plugin.levels.get.set("levels." + i + ".xp", xpList.get(i - 1));
                                        }
                                        plugin.levels.save();
                                        for (String message : plugin.language.get.getStringList("console.pvplevels.generate.message")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvplevels_levels}", String.valueOf(xpList.size()))));
                                        }
                                    } else {
                                        for (String message : plugin.language.get.getStringList("console.pvplevels.generate.number")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList("console.pvplevels.generate.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            } else {
                                for (String message : plugin.language.get.getStringList("console.pvplevels.generate.disable")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("boosters")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.command.boosters")) {
                            if (args.length >= 2) {
                                if (args[1].equalsIgnoreCase("give")) {
                                    if (sender.hasPermission("pvplevels.command.boosters.give")) {
                                        if (args.length == 6) {
                                            Player target = PvPLevels.call.getServer().getPlayer(args[2]);
                                            if (target != null) {
                                                if (args[3].equalsIgnoreCase("global")) {
                                                    if (plugin.isDouble(args[4])) {
                                                        if (plugin.isInt(args[5])) {
                                                            List<String> list = plugin.boosters.get.getStringList("players." + target.getUniqueId().toString() + ".global");
                                                            list.add(args[4] + " " + args[5]);
                                                            plugin.boosters.get.set("players." + target.getUniqueId().toString() + ".global", list);
                                                            plugin.boosters.save();
                                                            if (path.equalsIgnoreCase("player")) {
                                                                for (String message : plugin.language.get.getStringList("player.pvplevels.boosters.give.message")) {
                                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvplevels_player_target}", target.getName()).replace("{pvplevels_booster_global_type}", args[4]).replace("{pvplevels_booster_global_time}", plugin.boostersManager.timeLeft(Integer.parseInt(args[5])))));
                                                                }
                                                                if (!target.getName().equals(sender.getName())) {
                                                                    for (String message : plugin.language.get.getStringList("player.pvplevels.boosters.give.target")) {
                                                                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvplevels_booster_global_type}", args[4]).replace("{pvplevels_booster_global_time}", plugin.boostersManager.timeLeft(Integer.parseInt(args[5])))));
                                                                    }
                                                                }
                                                            } else {
                                                                for (String message : plugin.language.get.getStringList("console.pvplevels.boosters.give.target")) {
                                                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvplevels_booster_global_type}", args[4]).replace("{pvplevels_booster_global_time}", plugin.boostersManager.timeLeft(Integer.parseInt(args[5])))));
                                                                }
                                                            }
                                                        } else {
                                                            for (String message : plugin.language.get.getStringList(path + ".pvplevels.boosters.give.number")) {
                                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                            }
                                                        }
                                                    } else {
                                                        for (String message : plugin.language.get.getStringList(path + ".pvplevels.boosters.give.booster")) {
                                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                        }
                                                    }
                                                } else if (args[3].equalsIgnoreCase("personal")) {
                                                    if (plugin.isDouble(args[4])) {
                                                        if (plugin.isInt(args[5])) {
                                                            List<String> list = plugin.boosters.get.getStringList("players." + target.getUniqueId().toString() + ".personal");
                                                            list.add(args[4] + " " + args[5]);
                                                            plugin.boosters.get.set("players." + target.getUniqueId().toString() + ".personal", list);
                                                            plugin.boosters.save();
                                                            if (path.equalsIgnoreCase("player")) {
                                                                for (String message : plugin.language.get.getStringList("player.pvplevels.boosters.personal.give.message")) {
                                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvplevels_player_target}", target.getName()).replace("{pvplevels_booster_personal_type}", args[4]).replace("{pvplevels_booster_personal_time}", plugin.boostersManager.timeLeft(Integer.parseInt(args[5])))));
                                                                }
                                                                if (!target.getName().equals(sender.getName())) {
                                                                    for (String message : plugin.language.get.getStringList("player.pvplevels.boosters.personal.give.target")) {
                                                                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvplevels_booster_personal_type}", args[4]).replace("{pvplevels_booster_personal_time}", plugin.boostersManager.timeLeft(Integer.parseInt(args[5])))));
                                                                    }
                                                                }
                                                            } else {
                                                                for (String message : plugin.language.get.getStringList("console.pvplevels.boosters.personal.give.target")) {
                                                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvplevels_booster_personal_type}", args[4]).replace("{pvplevels_booster_personal_time}", plugin.boostersManager.timeLeft(Integer.parseInt(args[5])))));
                                                                }
                                                            }
                                                        } else {
                                                            for (String message : plugin.language.get.getStringList(path + ".pvplevels.boosters.personal.give.number")) {
                                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                            }
                                                        }
                                                    } else {
                                                        for (String message : plugin.language.get.getStringList(path + ".pvplevels.boosters.personal.give.booster")) {
                                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                        }
                                                    }
                                                } else {
                                                    for (String message : plugin.language.get.getStringList(path + ".pvplevels.boosters.give.usage")) {
                                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                    }
                                                }
                                            } else {
                                                for (String message : plugin.language.get.getStringList(path + ".pvplevels.boosters.give.online")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        } else {
                                            for (String message : plugin.language.get.getStringList(path + ".pvplevels.boosters.give.usage")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    } else {
                                        for (String message : plugin.language.get.getStringList("player.pvplevels.boosters.give.permission")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList(path + ".pvplevels.boosters.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            } else {
                                for (String message : plugin.language.get.getStringList(path + ".pvplevels.boosters.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvplevels.boosters.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("gui")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.command.gui")) {
                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("open")) {
                                    if (sender.hasPermission("pvplevels.command.gui.open")) {
                                        if (args.length == 3) {
                                            if (path.equalsIgnoreCase("player")) {
                                                Player player = (Player) sender;
                                                GUI gui = plugin.guiList.get(args[2]);
                                                if (gui != null) {
                                                    gui.open(player);
                                                } else {
                                                    for (String message : plugin.language.get.getStringList("player.pvplevels.gui.open.found")) {
                                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvplevels_gui_file}", args[2])));
                                                    }
                                                }
                                            } else {
                                                for (String message : plugin.language.get.getStringList("console.pvplevels.gui.open.usage")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        } else if (args.length == 4) {
                                            Player target = plugin.getServer().getPlayer(args[3]);
                                            if (target != null) {
                                                GUI gui = plugin.guiList.get(args[2]);
                                                if (gui != null) {
                                                    gui.open(target);
                                                } else {
                                                    for (String message : plugin.language.get.getStringList(path + ".pvplevels.gui.open.found")) {
                                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvplevels_gui_file}", args[2])));
                                                    }
                                                }
                                            } else {
                                                for (String message : plugin.language.get.getStringList(path + ".pvplevels.gui.open.online")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        } else {
                                            for (String message : plugin.language.get.getStringList(path + ".pvplevels.gui.open.usage")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    } else {
                                        for (String message : plugin.language.get.getStringList("player.pvplevels.gui.open.permission")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList(path + ".pvplevels.gui.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            } else {
                                for (String message : plugin.language.get.getStringList(path + ".pvplevels.gui.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvplevels.gui.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("player")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.command.player")) {
                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("get")) {
                                    playerCheckXP(sender, args, "get", path);
                                } else if (args[1].equalsIgnoreCase("lose")) {
                                    playerCheckXP(sender, args, "lose", path);
                                } else {
                                    for (String message : plugin.language.get.getStringList(path + ".pvplevels.player.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            } else {
                                for (String message : plugin.language.get.getStringList(path + ".pvplevels.player.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvplevels.player.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    }
                    if (unknown) {
                        for (String message : plugin.language.get.getStringList(path + ".pvplevels.command.unknown")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvplevels_command}", args[0])));
                        }
                    }
                }
            } else {
                for (String message : plugin.language.get.getStringList("player.pvplevels.command.permission")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
        return true;
    }

    private void playerCheckXP(CommandSender sender, String[] args, String type, String path) {
        if (sender.hasPermission("pvplevels.command." + type)) {
            if (args.length == 4) {
                if (plugin.config.get.contains("xp." + args[2])) {
                    Player target = PvPLevels.call.getServer().getPlayer(args[3]);
                    if (target != null) {
                        if (type.equalsIgnoreCase("get")) {
                            plugin.xpManager.check(plugin.get(target.getUniqueId().toString()), args[2], "", target, true);
                            return;
                        }
                        plugin.xpManager.check(plugin.get(target.getUniqueId().toString()), args[2], "", target, false);
                    } else {
                        for (String message : plugin.language.get.getStringList(path + ".pvplevels.player." + type + ".online")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                } else {
                    for (String message : plugin.language.get.getStringList(path + ".pvplevels.player." + type + ".config")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvplevels_xp_type}", args[2])));
                    }
                }
            } else {
                for (String message : plugin.language.get.getStringList(path + ".pvplevels.player." + type + ".usage")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        } else {
            for (String message : plugin.language.get.getStringList("player.pvplevels.player." + type + ".permission")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        }
    }

    private void setValue(CommandSender sender, Player target, String colum, Long set, PlayerConnect playerConnect, String[] args, String type, String path) {
        boolean killsCheck = true;
        boolean deathsCheck = true;
        boolean xpAddCheck = true;
        boolean xpRemoveCheck = true;
        boolean xpSetCheck = true;
        boolean levelCheck = true;
        if (colum.equalsIgnoreCase("kills")) {
            if (set > 0L) {
                playerConnect.kills(set);
            } else if (set == 0) {
                playerConnect.kills(0L);
            } else {
                killsCheck = false;
            }
        } else if (colum.equalsIgnoreCase("deaths")) {
            if (set > 0L) {
                playerConnect.deaths(set);
            } else if (set == 0) {
                playerConnect.deaths(0L);
            } else {
                deathsCheck = false;
            }
        } else if (colum.equalsIgnoreCase("xp")) {
            if (type.equalsIgnoreCase("add")) {
                ArrayList<Long> xp = xpControl();
                if (set <= xp.get(xp.size() - 1)) {
                    if (!plugin.xpManager.isMaxLevel(target, playerConnect)) {
                        playerConnect.xp(set);
                        plugin.xpManager.getLevel(playerConnect, target);
                    } else {
                        xpAddCheck = false;
                    }
                } else {
                    xpAddCheck = false;
                }
            } else if (type.equalsIgnoreCase("remove")) {
                if (set >= 0) {
                    playerConnect.xp(set);
                    if (!plugin.config.get.getBoolean("levelup.xp-clear") && playerConnect.xp() < plugin.levels.get.getLong("levels." + playerConnect.level() + ".xp")) {
                        plugin.xpManager.loseLevel(playerConnect, playerConnect.level() - 1, target, null);
                    }
                } else {
                    Long lowerLevel = playerConnect.level() - 1;
                    if (plugin.levels.get.contains("levels." + lowerLevel + ".xp")) {
                        Long all = plugin.levels.get.getLong("levels." + playerConnect.level() + ".xp") - Long.parseLong(args[3]);
                        if (all >= 0) {
                            playerConnect.xp(all);
                        } else {
                            playerConnect.xp(0L);
                        }
                        plugin.xpManager.loseLevel(playerConnect, lowerLevel, target, null);
                    } else {
                        xpRemoveCheck = false;
                    }
                }
            } else {
                ArrayList<Long> xp = xpControl();
                if (set <= xp.get(xp.size() - 1)) {
                    if (!plugin.xpManager.isMaxLevel(target, playerConnect)) {
                        playerConnect.xp(set);
                        plugin.xpManager.getLevel(playerConnect, target);
                    } else {
                        xpSetCheck = false;
                    }
                } else {
                    xpSetCheck = false;
                }
            }
        } else if (colum.equalsIgnoreCase("level")) {
            if (set > 0L) {
                if (plugin.levels.get.contains("levels." + set)) {
                    playerConnect.level(set);
                    if (plugin.config.get.getBoolean("levelup.xp-clear")) {
                        playerConnect.xp(0L);
                    } else {
                        playerConnect.xp(plugin.levels.get.getLong("levels." + set + ".xp"));
                    }
                } else {
                    levelCheck = false;
                }
            } else if (set == 0) {
                playerConnect.level(0L);
                playerConnect.xp(0L);
            }
        }
        if (killsCheck && deathsCheck && xpAddCheck && xpRemoveCheck && xpSetCheck && levelCheck) {
            for (String message : plugin.language.get.getStringList(path + ".pvplevels." + args[0] + "." + colum))
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message
                        .replace("{pvplevels_player}", args[2]).replace("{pvplevels_" + args[0] + "}", args[3])));
        } else if (!killsCheck) {
            for (String message : plugin.language.get.getStringList(path + ".pvplevels.set.kills-cannot")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        } else if (!deathsCheck) {
            for (String message : plugin.language.get.getStringList(path + ".pvplevels.set.deaths-cannot")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        } else if (!xpAddCheck) {
            for (String message : plugin.language.get.getStringList(path + ".pvplevels.set.xp-add-cannot")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        } else if (!xpRemoveCheck) {
            for (String message : plugin.language.get.getStringList(path + ".pvplevels.set.xp-remove-cannot")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        } else if (!xpSetCheck) {
            for (String message : plugin.language.get.getStringList(path + ".pvplevels.set.xp-set-cannot")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        } else if (!levelCheck) {
            for (String message : plugin.language.get.getStringList(path + ".pvplevels.set.level-cannot")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        }
    }

    private ArrayList<Long> xpControl() {
        ArrayList<Long> xp = new ArrayList<>();
        for (String level : plugin.levels.get.getConfigurationSection("levels").getKeys(false)) {
            xp.add(plugin.levels.get.getLong("levels." + level + ".xp"));
        }
        return xp;
    }

    private void broadcast(String text, String[] args) {
        if (args[1].equalsIgnoreCase("null")) {
            PvPLevels.call.getServer().broadcastMessage(text);
        } else {
            PvPLevels.call.getServer().broadcast(text, args[1]);
        }
    }
}