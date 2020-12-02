package me.MathiasMC.PvPLevels.managers;

import com.google.common.base.Strings;
import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class StatsManager {

    private final PvPLevels plugin;

    public StatsManager(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    public String getKDR(final PlayerConnect playerConnect) {
        if (playerConnect.getDeaths() > 0L) {
            final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("en", "UK"));
            decimalFormat.applyPattern("#.##");
            return decimalFormat.format((double) playerConnect.getKills() / (double) playerConnect.getDeaths());
        } else if (playerConnect.getDeaths() == 0L) {
            return String.valueOf(playerConnect.getKills());
        }
        return String.valueOf(0.0D);
    }

    public String getKillFactor(final PlayerConnect playerConnect) {
        if (playerConnect.getKills() > 0L) {
            final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("en", "UK"));
            decimalFormat.applyPattern("#.##");
            return decimalFormat.format((double) playerConnect.getKills() / ((double) playerConnect.getKills() + (double) playerConnect.getDeaths()));
        }
        return String.valueOf(0L);
    }

    public Long getXPRequired(final PlayerConnect playerConnect, final boolean next) {
        long level;
        if (!next) {
            level = playerConnect.getLevel();
        } else {
            level = playerConnect.getLevel() + 1;
        }
        final String group = playerConnect.getGroup();
        if (plugin.getFileUtils().levels.getConfigurationSection(group).getKeys(false).size() > level) {
            return plugin.getFileUtils().levels.getLong(group + "." + (level + 1) + ".xp");
        }
        return 0L;
    }

    public Long getXPNeeded(final PlayerConnect playerConnect) {
        return plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + (playerConnect.getLevel() + 1) + ".xp") - playerConnect.getXp();
    }

    public int getXPProgress(final PlayerConnect playerConnect) {
        final long xp_cur = playerConnect.getXp();
        final long xp_req_cur = plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".xp");
        final long xp_req_next = plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + (playerConnect.getLevel() + 1) + ".xp");
        final double set = (double) (xp_cur - xp_req_cur) / (xp_req_next - xp_req_cur);
        if (xp_cur > xp_req_next) {
            return 100;
        }
        return (int) Math.round(set * 100);
    }

    public String getXPProgressStyle(final PlayerConnect playerConnect, final String path) {
        final char xp = (char) Integer.parseInt(plugin.getFileUtils().config.getString(path + ".xp.symbol").substring(2), 16);
        final char none = (char) Integer.parseInt(plugin.getFileUtils().config.getString(path + ".none.symbol").substring(2), 16);
        final ChatColor xpColor = getChatColor(plugin.getFileUtils().config.getString(path + ".xp.color"));
        final ChatColor noneColor = getChatColor(plugin.getFileUtils().config.getString(path + ".none.color"));
        final int bars = plugin.getFileUtils().config.getInt(path + ".amount");
        final int progressBars = (bars * getXPProgress(playerConnect) / 100);
        try {
            return Strings.repeat("" + xpColor + xp, progressBars) + Strings.repeat("" + noneColor + none, bars - progressBars);
        } catch (Exception exception) {
            return "";
        }
    }

    public String getPrefix(final OfflinePlayer offlinePlayer) {
        final PlayerConnect playerConnect = plugin.getPlayerConnect(offlinePlayer.getUniqueId().toString());
        long level = playerConnect.getLevel();
        if (!plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + level + ".group")) {
            level = plugin.getFileUtils().config.getLong("start-level");
        }
        return ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(offlinePlayer, true, plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + level + ".prefix")));
    }

    public String getSuffix(final OfflinePlayer offlinePlayer) {
        final PlayerConnect playerConnect = plugin.getPlayerConnect(offlinePlayer.getUniqueId().toString());
        long level = playerConnect.getLevel();
        if (!plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + level + ".group")) {
            level = plugin.getFileUtils().config.getLong("start-level");
        }
        return ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(offlinePlayer,  true, plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + level + ".suffix")));
    }

    public String getGroup(final OfflinePlayer offlinePlayer) {
        final PlayerConnect playerConnect = plugin.getPlayerConnect(offlinePlayer.getUniqueId().toString());
        long level = playerConnect.getLevel();
        if (!plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + level + ".group")) {
            level = plugin.getFileUtils().config.getLong("start-level");
        }
        return ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(offlinePlayer,  true, plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + level + ".group")));
    }

    public String getTopValue(final String type, final int number, final boolean key, final boolean reverse) {
        final LinkedHashMap<OfflinePlayer, Long> linkedHashMap = getTopMap(type, reverse);
        if (key) {
            final ArrayList<OfflinePlayer> map = new ArrayList<>(linkedHashMap.keySet());
            if (map.size() > number) {
                return map.get(number).getName();
            } else {
                return ChatColor.translateAlternateColorCodes('&', plugin.getFileUtils().config.getString("top.name"));
            }
        } else {
            final ArrayList<Long> map = new ArrayList<>(linkedHashMap.values());
            if (map.size() > number) {
                return String.valueOf(map.get(number));
            } else {
                return ChatColor.translateAlternateColorCodes('&', plugin.getFileUtils().config.getString("top.value"));
            }
        }
    }

    public LinkedHashMap<OfflinePlayer, Long> getTopMap(final String type, final boolean reverse) {
        final Map<OfflinePlayer, Long> unsorted = new HashMap<>();
        final List<String> excluded = plugin.getFileUtils().config.getStringList("top.excluded");
        final Set<String> listPlayerConnect = plugin.listPlayerConnect();
        for (OfflinePlayer offlinePlayer : plugin.getServer().getOfflinePlayers()) {
            final String uuid = offlinePlayer.getUniqueId().toString();
            if (!excluded.contains(uuid) && listPlayerConnect.contains(uuid)) {
                PlayerConnect playerConnect = plugin.getPlayerConnect(uuid);
                switch (type) {
                    case "kills":
                        unsorted.put(offlinePlayer, playerConnect.getKills());
                        break;
                    case "deaths":
                        unsorted.put(offlinePlayer, playerConnect.getDeaths());
                        break;
                    case "xp":
                        unsorted.put(offlinePlayer, playerConnect.getXp());
                        break;
                    case "level":
                        unsorted.put(offlinePlayer, playerConnect.getLevel());
                        break;
                    case "killstreak":
                        unsorted.put(offlinePlayer, playerConnect.getKillstreak());
                        break;
                    case "killstreak_top":
                        unsorted.put(offlinePlayer, playerConnect.getKillstreakTop());
                        break;
                    case "lastseen":
                        unsorted.put(offlinePlayer, playerConnect.getTime().getTime());
                        break;
                    case "kdr":
                        unsorted.put(offlinePlayer, Long.valueOf(getKDR(playerConnect)));
                        break;
                    case "killfactor":
                        unsorted.put(offlinePlayer, Long.valueOf(getKillFactor(playerConnect)));
                        break;
                }
            }
        }
        final LinkedHashMap<OfflinePlayer, Long> sorted = new LinkedHashMap<>();
        if (reverse) {
            unsorted.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
        } else {
            unsorted.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.naturalOrder())).forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
        }
        return sorted;
    }

    private ChatColor getChatColor(final String colorCode){
        switch (colorCode){
            case "&0" : return ChatColor.BLACK;
            case "&1" : return ChatColor.DARK_BLUE;
            case "&2" : return ChatColor.DARK_GREEN;
            case "&3" : return ChatColor.DARK_AQUA;
            case "&4" : return ChatColor.DARK_RED;
            case "&5" : return ChatColor.DARK_PURPLE;
            case "&6" : return ChatColor.GOLD;
            case "&7" : return ChatColor.GRAY;
            case "&8" : return ChatColor.DARK_GRAY;
            case "&9" : return ChatColor.BLUE;
            case "&a" : return ChatColor.GREEN;
            case "&b" : return ChatColor.AQUA;
            case "&c" : return ChatColor.RED;
            case "&d" : return ChatColor.LIGHT_PURPLE;
            case "&e" : return ChatColor.YELLOW;
            case "&f" : return ChatColor.WHITE;
            case "&k" : return ChatColor.MAGIC;
            case "&l" : return ChatColor.BOLD;
            case "&m" : return ChatColor.STRIKETHROUGH;
            case "&n" : return ChatColor.UNDERLINE;
            case "&o" : return ChatColor.ITALIC;
            case "&r" : return ChatColor.RESET;
            default: return ChatColor.WHITE;
        }
    }

    public String getType(final PlayerConnect playerConnect) {
        final String type = playerConnect.getXpType();
        if (type.length() == 0) {
            return plugin.getFileUtils().language.getString("translate.xp.type");
        }
        return type;
    }

    public String getGet(final PlayerConnect playerConnect) {
        final long type = playerConnect.getXpLast();
        if (type == 0) {
            return plugin.getFileUtils().language.getString("translate.xp.get");
        }
        return String.valueOf(type);
    }

    public String getLost(final PlayerConnect playerConnect) {
        final long type = playerConnect.getXpLost();
        if (type == 0) {
            return plugin.getFileUtils().language.getString("translate.xp.lost");
        }
        return String.valueOf(type);
    }

    public String getItem(final PlayerConnect playerConnect) {
        final long type = playerConnect.getXpItem();
        if (type == 0) {
            return plugin.getFileUtils().language.getString("translate.xp.item");
        }
        return String.valueOf(type);
    }

    public String getMultiplier(final PlayerConnect playerConnect) {
        final double multiplier = playerConnect.getMultiplier();
        if (multiplier == 0D) {
            return plugin.getFileUtils().language.getString("translate.xp.multiplier.none");
        }
        return String.valueOf(multiplier);
    }

    public String getMultiplierTime(final PlayerConnect playerConnect) {
        final long multiplier = playerConnect.getMultiplierTime();
        if (multiplier == 0) {
            return plugin.getFileUtils().language.getString("translate.xp.multiplier.time");
        }
        return String.valueOf(getTime(multiplier * 1000));
    }

    public String getMultiplierTimeLeft(final PlayerConnect playerConnect) {
        final long multiplier = playerConnect.getMultiplierTimeLeft();
        if (multiplier == 0) {
            return plugin.getFileUtils().language.getString("translate.xp.multiplier.left");
        }
        return String.valueOf(getTime(multiplier * 1000));
    }

    public String getTime(long millis){
        String time = "";
        final long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        final long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        if (days > 1) {
            time += days + " " + plugin.getFileUtils().language.getString("translate.time.days") + " ";
        } else if (days == 1) {
            time += days + " " + plugin.getFileUtils().language.getString("translate.time.day") + " ";
        }
        if (hours > 1) {
            time += hours + " " + plugin.getFileUtils().language.getString("translate.time.hours") + " ";
        } else if (hours == 1) {
            time += hours + " " + plugin.getFileUtils().language.getString("translate.time.hour") + " ";
        }
        if (minutes > 1) {
            time += minutes + " " + plugin.getFileUtils().language.getString("translate.time.minutes") + " ";
        } else if (minutes == 1) {
            time += minutes + " " + plugin.getFileUtils().language.getString("translate.time.minute") + " ";
        }
        if (seconds > 1) {
            time += seconds + " " + plugin.getFileUtils().language.getString("translate.time.seconds");
        } else if (seconds == 1) {
            time += seconds + " " + plugin.getFileUtils().language.getString("translate.time.second");
        }
        if (time.endsWith(" ")) {
            return time.replaceAll("\\s+$", "");
        }
        return time;
    }

}