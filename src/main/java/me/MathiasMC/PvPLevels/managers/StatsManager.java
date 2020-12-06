package me.MathiasMC.PvPLevels.managers;

import com.google.common.base.Strings;
import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class StatsManager {

    private final PvPLevels plugin;

    private LinkedHashMap<String, Long> topKills = new LinkedHashMap<>();

    private LinkedHashMap<String, Long> topDeaths = new LinkedHashMap<>();

    private LinkedHashMap<String, Long> topXp = new LinkedHashMap<>();

    private LinkedHashMap<String, Long> topLevel = new LinkedHashMap<>();

    private LinkedHashMap<String, Long> topKillStreak = new LinkedHashMap<>();

    private LinkedHashMap<String, Long> topKillStreakTop = new LinkedHashMap<>();

    public StatsManager(final PvPLevels plugin) {
        this.plugin = plugin;
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this::updateTopMap,0, plugin.getFileUtils().config.getLong("top.update") * 20);
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

    public String getPrefix(final PlayerConnect playerConnect) {
        long level = playerConnect.getLevel();
        if (!plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + level + ".group")) {
            level = plugin.getFileUtils().config.getLong("start-level");
        }
        return ChatColor.translateAlternateColorCodes('&', plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + level + ".prefix"));
    }

    public String getSuffix(final PlayerConnect playerConnect) {
        long level = playerConnect.getLevel();
        if (!plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + level + ".group")) {
            level = plugin.getFileUtils().config.getLong("start-level");
        }
        return ChatColor.translateAlternateColorCodes('&', plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + level + ".suffix"));
    }

    public String getGroup(final PlayerConnect playerConnect) {
        long level = playerConnect.getLevel();
        if (!plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + level + ".group")) {
            level = plugin.getFileUtils().config.getLong("start-level");
        }
        return ChatColor.translateAlternateColorCodes('&', plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + level + ".group"));
    }

    public String getTopKills(final int number, final boolean isName) {
        return getTop(number - 1, isName, topKills);
    }

    public String getTopDeaths(final int number, final boolean isName) {
        return getTop(number - 1, isName, topDeaths);
    }

    public String getTopXp(final int number, final boolean isName) {
        return getTop(number - 1, isName, topXp);
    }

    public String getTopLevel(final int number, final boolean isName) {
        return getTop(number - 1, isName, topLevel);
    }

    public String getTopKillStreak(final int number, final boolean isName) {
        return getTop(number - 1, isName, topKillStreak);
    }

    public String getTopKillStreakTop(final int number, final boolean isName) {
        return getTop(number - 1, isName, topKillStreakTop);
    }

    private String getTop(final int number, final boolean key, final LinkedHashMap<String, Long> topMap) {
        if (key) {
            final ArrayList<String> map = new ArrayList<>(topMap.keySet());
            if (map.size() > number) {
                return plugin.getServer().getOfflinePlayer(UUID.fromString(map.get(number))).getName();
            } else {
                return ChatColor.translateAlternateColorCodes('&', plugin.getFileUtils().config.getString("top.name"));
            }
        } else {
            final ArrayList<Long> map = new ArrayList<>(topMap.values());
            if (map.size() > number) {
                return String.valueOf(map.get(number));
            } else {
                return ChatColor.translateAlternateColorCodes('&', plugin.getFileUtils().config.getString("top.value"));
            }
        }
    }

    public void updateTopMap() {
        final Map<String, Long> unsortedKills = new HashMap<>();
        final Map<String, Long> unsortedDeaths = new HashMap<>();
        final Map<String, Long> unsortedXp = new HashMap<>();
        final Map<String, Long> unsortedLevel = new HashMap<>();
        final Map<String, Long> unsortedKillStreak = new HashMap<>();
        final Map<String, Long> unsortedKillStreakTop = new HashMap<>();
        final List<String> excluded = plugin.getFileUtils().config.getStringList("top.excluded");
        for (OfflinePlayer offlinePlayer : plugin.getServer().getOfflinePlayers()) {
            final String uuid = offlinePlayer.getUniqueId().toString();
            if (!excluded.contains(uuid)) {
                final PlayerConnect playerConnect = plugin.getPlayerConnect(uuid);
                unsortedKills.put(uuid, playerConnect.getKills());
                unsortedDeaths.put(uuid, playerConnect.getDeaths());
                unsortedXp.put(uuid, playerConnect.getXp());
                unsortedLevel.put(uuid, playerConnect.getLevel());
                unsortedKillStreak.put(uuid, playerConnect.getKillstreak());
                unsortedKillStreakTop.put(uuid, playerConnect.getKillstreakTop());
            }
        }
        topKills = getSortedMap(unsortedKills);
        topDeaths = getSortedMap(unsortedDeaths);
        topXp = getSortedMap(unsortedXp);
        topLevel = getSortedMap(unsortedLevel);
        topKillStreak = getSortedMap(unsortedKillStreak);
        topKillStreakTop = getSortedMap(unsortedKillStreakTop);
    }


    private LinkedHashMap<String, Long> getSortedMap(final Map<String, Long> map) {
        return map.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
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