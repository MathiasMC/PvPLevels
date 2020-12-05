package me.MathiasMC.PvPLevels.commands;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.api.events.*;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.utils.GenerateThread;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
            boolean unknown = true;
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (sender.hasPermission("pvplevels")) {
                if (args.length == 0) {
                    if (player != null) {
                        dispatchCommandList(player, "command.message");
                    } else {
                        sendMessageList(sender, "console.command.message");
                    }
                } else {
                    if (args[0].equalsIgnoreCase("help")) {
                        unknown = false;
                        if (player != null) {
                            if (sender.hasPermission("pvplevels.admin.help")) {
                                dispatchCommandList(player, "help.admin");
                            } else if (sender.hasPermission("pvplevels.player.help")) {
                                dispatchCommandList(player, "help.player");
                            } else {
                                dispatchCommandList(player, "help.permission");
                            }
                        } else {
                            sendMessageList(sender, "console.help.message");
                        }
                    } else if (args[0].equalsIgnoreCase("reload")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.reload")) {
                            plugin.getFileUtils().loadConfig();
                            plugin.getFileUtils().loadLanguage();
                            plugin.getFileUtils().loadLevels();
                            plugin.getFileUtils().loadExecute();
                            if (player != null) {
                                dispatchCommandList(player, "reload.all");
                            } else {
                                sendMessageList(sender, "console.reload.all");
                            }
                        } else if (player != null) {
                            dispatchCommandList(player, "reload.permission");
                        }
                    } else if (args[0].equalsIgnoreCase("top")) {
                        unknown = false;
                        if (player != null) {
                            if (sender.hasPermission("pvplevels.player.top")) {
                                if (args.length == 2) {
                                    switch (args[1]) {
                                        case "kills":
                                        case "deaths":
                                        case "xp":
                                        case "level":
                                        case "killstreak":
                                        case "killstreak_top":
                                            dispatchCommandList(player, "top." + args[1]);
                                            break;
                                        default:
                                            dispatchCommandList(player, "top.usage");
                                            break;
                                    }
                                } else {
                                    dispatchCommandList(player, "top.usage");
                                }
                            } else {
                                dispatchCommandList(player, "top.permission");
                            }
                        } else {
                            sender.sendMessage("Not supported in console.");
                        }
                    } else if (args[0].equalsIgnoreCase("stats")) {
                        unknown = false;
                        if (player != null) {
                            if (sender.hasPermission("pvplevels.player.top")) {
                                if (args.length == 1) {
                                    dispatchCommandList(player, "stats.message");
                                } else {
                                    final Player target = plugin.getServer().getPlayer(args[1]);
                                    if (target != null) {
                                        for (String command : getCommands("stats.target")) {
                                            dispatchCommand(target, command.replace("{target}", target.getName()));
                                        }
                                    } else {
                                        dispatchCommandList(player, "stats.online");
                                    }
                                }
                            } else {
                                dispatchCommandList(player, "stats.permission");
                            }
                        } else {
                            sender.sendMessage("Not supported in console.");
                        }
                    } else if (args[0].equalsIgnoreCase("save")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.save")) {
                            for (String uuid : plugin.listPlayerConnect()) {
                                plugin.getPlayerConnect(uuid).save();
                            }
                            if (player != null) {
                                dispatchCommandList(player, "save.message");
                            } else {
                                sendMessageList(sender, "console.save.message");
                            }
                        } else if (player != null) {
                            dispatchCommandList(player, "save.permission");
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
                            } else if (player != null) {
                                dispatchCommandList(player, "broadcast.usage");
                            } else {
                                sendMessageList(sender, "console.broadcast.usage");
                            }
                        } else if (player != null) {
                            dispatchCommandList(player, "broadcast.permission");
                        }
                    } else if (args[0].equalsIgnoreCase("message")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.message")) {
                            if (args.length <= 2) {
                                if (player != null) {
                                    dispatchCommandList(player, "message.usage");
                                } else {
                                    sendMessageList(sender, "console.message.usage");
                                }
                            } else {
                                final Player target = plugin.getServer().getPlayer(args[1]);
                                if (target != null) {
                                    final StringBuilder sb = new StringBuilder();
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
                                    if (player != null) {
                                        dispatchCommandList(player, "message.online");
                                    } else {
                                        sendMessageList(sender, "console.message.online");
                                    }
                                }
                            }
                        } else if (player != null) {
                            dispatchCommandList(player, "message.permission");
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
                                        final int seconds = Integer.parseInt(args[2]);
                                        new BukkitRunnable() {
                                            int time = 0;

                                            @Override
                                            public void run() {
                                                if (time >= seconds) {
                                                    this.cancel();
                                                } else {
                                                    time++;
                                                    plugin.actionBar.sendMessage(target, sb.toString().trim());
                                                }
                                            }
                                        }.runTaskTimer(plugin, 0, 20);
                                    } else {
                                        if (player != null) {
                                            dispatchCommandList(player, "actionbar.number");
                                        } else {
                                            sendMessageList(sender, "console.actionbar.number");
                                        }
                                    }
                                } else {
                                    if (player != null) {
                                        dispatchCommandList(player, "actionbar.online");
                                    } else {
                                        sendMessageList(sender, "console.actionbar.online");
                                    }
                                }
                            } else {
                                if (player != null) {
                                    dispatchCommandList(player, "actionbar.usage");
                                } else {
                                    sendMessageList(sender, "console.actionbar.usage");
                                }
                            }
                        } else if (player != null) {
                            dispatchCommandList(player, "actionbar.permission");
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
                                                if (player != null) {
                                                    for (String command : getCommands("group.set.message")) {
                                                        dispatchCommand(player, command.replace("{target}", target.getName()));
                                                    }
                                                } else {
                                                    for (String message : getCommands("console.group.set.message")) {
                                                        sendMessage(sender, message.replace("{target}", target.getName()).replace("{group}", args[3]));
                                                    }
                                                }
                                            } else if (player != null) {
                                                dispatchCommandList(player, "group.valid");
                                            } else {
                                                sendMessageList(sender, "console.group.valid");
                                            }
                                        } else {
                                            if (player != null) {
                                                dispatchCommandList(player, "group.online");
                                            } else {
                                                sendMessageList(sender, "console.group.online");
                                            }
                                        }
                                    } else if (player != null) {
                                        dispatchCommandList(player, "group.set.usage");
                                    } else {
                                        sendMessageList(sender, "console.group.set.usage");
                                    }
                                } else if (args[1].equalsIgnoreCase("reset")) {
                                    if (args.length == 3) {
                                        Player target = plugin.getServer().getPlayer(args[2]);
                                        if (target != null) {
                                            PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                            playerConnect.setGroup("default");
                                            playerConnect.save();
                                            if (player != null) {
                                                for (String command : getCommands("group.reset.message")) {
                                                    dispatchCommand(player, command.replace("{target}", target.getName()));
                                                }
                                            } else {
                                                for (String message : getCommands("console.group.reset.message")) {
                                                    sendMessage(sender, message.replace("{target}", target.getName()));
                                                }
                                            }
                                        } else if (player != null) {
                                            dispatchCommandList(player, "group.online");
                                        } else {
                                            sendMessageList(sender, "console.group.online");
                                        }
                                    } else if (player != null) {
                                        dispatchCommandList(player, "group.reset.usage");
                                    } else {
                                        sendMessageList(sender, "console.group.reset.usage");
                                    }
                                } else if (player != null) {
                                    dispatchCommandList(player, "group.usage");
                                } else {
                                    sendMessageList(sender, "console.group.usage");
                                }
                            } else if (player != null) {
                                dispatchCommandList(player, "group.usage");
                            } else {
                                sendMessageList(sender, "console.group.usage");
                            }
                        } else if (player != null) {
                            dispatchCommandList(player, "group.permission");
                        }
                    } else if (args[0].equalsIgnoreCase("reset")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.reset")) {
                            if (args.length > 2) {
                                final Player target = plugin.getServer().getPlayer(args[2]);
                                if (target != null) {
                                    final PlayerConnect playerConnect = plugin.getPlayerConnect(target.getUniqueId().toString());
                                    switch (args[1]) {
                                        case "kills":
                                            triggerReset(player, target, sender, playerConnect, args[1]);
                                            playerConnect.setKills(0L);
                                            break;
                                        case "deaths":
                                            triggerReset(player, target, sender, playerConnect, args[1]);
                                            playerConnect.setDeaths(0L);
                                            break;
                                        case "level":
                                            triggerReset(player, target, sender, playerConnect, args[1]);
                                            playerConnect.setLevel(0L);
                                            break;
                                        case "killstreak":
                                            triggerReset(player, target, sender, playerConnect, args[1]);
                                            playerConnect.setKillstreak(0L);
                                            break;
                                        case "stats":
                                            if (args.length == 4) {
                                                triggerReset(player, target, sender, playerConnect, args[1]);
                                                playerConnect.setKills(0L);
                                                playerConnect.setDeaths(0L);
                                                playerConnect.setXp(0L);
                                                playerConnect.setLevel(plugin.getStartLevel());
                                                playerConnect.setKillstreak(0L);
                                                playerConnect.setKillstreakTop(0L);
                                                if (Boolean.parseBoolean(args[2])) {
                                                    playerConnect.setGroup("default");
                                                }
                                            } else if (player != null) {
                                                dispatchCommandList(player, "reset.stats.usage");
                                            } else {
                                                sendMessageList(sender, "console.reset.stats.usage");
                                            }
                                            break;
                                        default:
                                            if (player != null) {
                                                dispatchCommandList(player, "reset.found");
                                            } else {
                                                sendMessageList(sender, "console.reset.found");
                                            }
                                            break;
                                    }
                                } else if (player != null) {
                                    dispatchCommandList(player, "reset.online");
                                } else {
                                    sendMessageList(sender, "console.reset.online");
                                }
                            } else if (player != null) {
                                dispatchCommandList(player, "reset.usage");
                            } else {
                                sendMessageList(sender, "console.reset.usage");
                            }
                        } else if (player != null) {
                            dispatchCommandList(player, "reset.permission");
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
                                        if (player != null) {
                                            for (String command : getCommands("xp.set")) {
                                                dispatchCommand(player, command.replace("{target}", target.getName()).replace("{xp}", args[2]));
                                            }
                                        } else {
                                            for (String message : getCommands("console.xp.set")) {
                                                sendMessage(sender, message.replace("{target}", target.getName()).replace("{xp}", args[2]));
                                            }
                                        }
                                        for (String command : getCommands("xp.target")) {
                                            dispatchCommand(target, command.replace("{target}", target.getName()).replace("{xp}", args[2]));
                                        }
                                    } else if (player != null) {
                                        dispatchCommandList(player, "xp.number");
                                    } else {
                                        sendMessageList(sender, "console.xp.number");
                                    }
                                } else if (player != null) {
                                    dispatchCommandList(player, "xp.online");
                                } else {
                                    sendMessageList(sender, "console.xp.online");
                                }
                            } else if (player != null) {
                                dispatchCommandList(player, "xp.usage");
                            } else {
                                sendMessageList(sender, "console.xp.usage");
                            }
                        } else if (player != null) {
                            dispatchCommandList(player, "xp.permission");
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
                                            if (player != null) {
                                                for (String command : getCommands("level.set")) {
                                                    dispatchCommand(player, command.replace("{target}", target.getName()).replace("{level}", args[2]));
                                                }
                                            } else {
                                                for (String message : getCommands("console.level.set")) {
                                                    sendMessage(sender, message.replace("{target}", target.getName()).replace("{level}", args[2]));
                                                }
                                            }
                                            for (String command : getCommands("level.target")) {
                                                dispatchCommand(target, command.replace("{target}", target.getName()).replace("{level}", args[2]));
                                            }
                                        } else if (player != null) {
                                            dispatchCommandList(player, "level.found");
                                        } else {
                                            sendMessageList(sender, "console.level.found");
                                        }
                                    } else if (player != null) {
                                        dispatchCommandList(player, "level.number");
                                    } else {
                                        sendMessageList(sender, "console.level.number");
                                    }
                                } else if (player != null) {
                                    dispatchCommandList(player, "level.online");
                                } else {
                                    sendMessageList(sender, "console.level.online");
                                }
                            } else if (player != null) {
                                dispatchCommandList(player, "level.usage");
                            } else {
                                sendMessageList(sender, "console.level.usage");
                            }
                        } else if (player != null) {
                            dispatchCommandList(player, "level.permission");
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
                                            if (player != null) {
                                                for (String command : getCommands("multiplier.got")) {
                                                    dispatchCommand(player, command.replace("{target}", target.getName()));
                                                }
                                            } else {
                                                for (String message : getCommands("console.multiplier.got")) {
                                                    sendMessage(sender, message.replace("{target}", target.getName()).replace("{xp_multiplier}", plugin.getStatsManager().getMultiplier(playerConnect)).replace("{xp_multiplier_time}", plugin.getStatsManager().getMultiplierTime(playerConnect)));
                                                }
                                            }
                                            for (String command : getCommands("multiplier.target")) {
                                                dispatchCommand(target, command.replace("{target}", target.getName()));
                                            }
                                        } else if (player != null) {
                                            dispatchCommandList(player, "multiplier.number");
                                        } else {
                                            sendMessageList(sender, "console.multiplier.number");
                                        }
                                    } else if (player != null) {
                                        dispatchCommandList(player, "multiplier.double");
                                    } else {
                                        sendMessageList(sender, "console.multiplier.double");
                                    }
                                } else if (player != null) {
                                    dispatchCommandList(player, "multiplier.online");
                                } else {
                                    sendMessageList(sender, "console.multiplier.online");
                                }
                            } else if (player != null) {
                                dispatchCommandList(player, "multiplier.usage");
                            } else {
                                sendMessageList(sender, "console.multiplier.usage");
                            }
                        } else if (player != null) {
                            dispatchCommandList(player, "multiplier.permission");
                        }
                    } else if (args[0].equalsIgnoreCase("generate")) {
                        unknown = false;
                        if (sender.hasPermission("pvplevels.admin.generate")) {
                            if (plugin.getFileUtils().config.getBoolean("generate.use")) {
                                if (!plugin.isGenerate) {
                                    if (player != null) {
                                        if (args.length > 2) {
                                            if (plugin.getCalculateManager().isString(args[1])) {
                                                if (plugin.getCalculateManager().isLong(args[2]) && Long.parseLong(args[2]) > 0 && Long.parseLong(args[2]) <= 50000) {
                                                    plugin.generateGroup = args[1];
                                                    plugin.generateAmount = Long.parseLong(args[2]);
                                                    dispatchCommandList(player, "generate.group");
                                                } else {
                                                    dispatchCommandList(player, "generate.number");
                                                }
                                            } else {
                                                dispatchCommandList(player, "generate.valid");
                                            }
                                        } else {
                                            dispatchCommandList(player, "generate.usage");
                                        }
                                    } else if (args.length > 1 && args[1].equalsIgnoreCase("confirm")) {
                                        if (plugin.generateGroup != null && plugin.generateAmount != 0) {
                                            if (plugin.getScriptEngine() != null) {
                                                new GenerateThread(plugin, plugin.generateGroup, plugin.generateAmount, plugin.getStartLevel()).start();
                                            } else {
                                                sendMessageList(sender, "console.generate.engine");
                                            }
                                        } else {
                                            sendMessageList(sender, "console.generate.confirm");
                                        }
                                    } else {
                                        sendMessageList(sender, "console.generate.usage");
                                    }
                                } else if (player != null) {
                                    dispatchCommandList(player, "generate.generate");
                                } else {
                                    sendMessageList(sender, "console.generate.generate");
                                }
                            } else if (player != null) {
                                dispatchCommandList(player, "generate.use");
                            } else {
                                sendMessageList(sender, "console.generate.use");
                            }
                        } else if (player != null) {
                            dispatchCommandList(player, "generate.permission");
                        }
                    }
                    if (unknown) {
                        if (player != null) {
                            for (String command : getCommands("command.unknown")) {
                                dispatchCommand(player, command.replace("{command}", args[0]));
                            }
                        } else {
                            for (String message : getCommands("console.command.unknown")) {
                                sendMessage(sender, message.replace("{command}", args[0]));
                            }
                        }
                    }
                }
            } else if (player != null) {
                dispatchCommandList(player, "command.permission");
            }
        }
        return true;
    }

    private void triggerReset(final Player player, final Player target, final CommandSender sender, final PlayerConnect playerConnect, final String type) {
        if (type.equalsIgnoreCase("killstreak")) {
            final String path = "killstreak." + playerConnect.getGroup();
            if (plugin.getFileUtils().config.contains(path)) {
                final ArrayList<Integer> list = new ArrayList<>();
                long set = playerConnect.getKillstreak();
                for (String select : plugin.getFileUtils().config.getConfigurationSection(path).getKeys(false)) {
                    if (!select.equalsIgnoreCase("worlds") && !select.equalsIgnoreCase("get") && set >= Integer.parseInt(select)) {
                        list.add(Integer.parseInt(select));
                    }
                }
                if (!list.isEmpty()) {
                    set = list.get(list.size() - 1);
                }
                if (set > 0) {
                    final PlayerLostKillStreakEvent playerLostKillStreakEvent = new PlayerLostKillStreakEvent(target, playerConnect, set);
                    plugin.getServer().getPluginManager().callEvent(playerLostKillStreakEvent);
                    if (!playerLostKillStreakEvent.isCancelled()) {
                        playerLostKillStreakEvent.execute();
                    }
                }
            }
        }
        for (String command : getCommands("reset." + type + ".target")) {
            dispatchCommand(target, command.replace("{target}", target.getName()));
        }
        playerConnect.save();
        if (player != null) {
            for (String command : getCommands("reset." + type + ".reset")) {
                dispatchCommand(player, command.replace("{target}", target.getName()));
            }
        } else {
            for (String message : getCommands("console.reset." + type + ".reset")) {
                sendMessage(sender, message.replace("{target}", target.getName()));
            }
        }
    }

    private List<String> getCommands(final String path) {
        return plugin.getFileUtils().language.getStringList(path);
    }

    private void dispatchCommandList(final Player player, final String path) {
        for (String command : getCommands(path)) {
            dispatchCommand(player, command);
        }
    }

    private void dispatchCommand(final Player player, final String message) {
        plugin.getServer().dispatchCommand(plugin.consoleSender, ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(player, false, message.replace("{version}", plugin.getDescription().getVersion()))));
    }

    private void sendMessageList(final CommandSender sender, final String path) {
        for (String message : getCommands(path)) {
            sendMessage(sender, message);
        }
    }

    private void sendMessage(final CommandSender sender, final String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private void broadcast(final String text, final String[] args) {
        if (args[1].equalsIgnoreCase("null")) {
            plugin.getServer().broadcastMessage(text);
        } else {
            plugin.getServer().broadcast(text, args[1]);
        }
    }
}