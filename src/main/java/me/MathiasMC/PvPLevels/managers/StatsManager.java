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

    public StatsManager(PvPLevels plugin) {
        this.plugin = plugin;
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this::updateTopMap,0, plugin.getFileUtils().config.getLong("top.update") * 20);
    }

    public String getKDR(PlayerConnect playerConnect) {
        if (playerConnect.getDeaths() > 0L) {
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("en", "UK"));
            decimalFormat.applyPattern("#.##");
            return decimalFormat.format((double) playerConnect.getKills() / (double) playerConnect.getDeaths());
        } else if (playerConnect.getDeaths() == 0L) {
            return String.valueOf(playerConnect.getKills());
        }
        return String.valueOf(0.0D);
    }

    public String getKillFactor(PlayerConnect playerConnect) {
        if (playerConnect.getKills() > 0L) {
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("en", "UK"));
            decimalFormat.applyPattern("#.##");
            return decimalFormat.format((double) playerConnect.getKills() / ((double) playerConnect.getKills() + (double) playerConnect.getDeaths()));
        }
        return String.valueOf(0L);
    }

    public Long getXPRequired(PlayerConnect playerConnect, boolean next) {
        long level;
        if (!next) {
            level = playerConnect.getLevel();
        } else {
            level = playerConnect.getLevel() + 1;
        }
        String group = playerConnect.getGroup();
        if (plugin.getFileUtils().levels.getConfigurationSection(group).getKeys(false).size() > level) {
            return plugin.getFileUtils().levels.getLong(group + "." + (level + 1) + ".xp");
        }
        return 0L;
    }

    public Long getXPNeeded(PlayerConnect playerConnect) {
        return plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + (playerConnect.getLevel() + 1) + ".xp") - playerConnect.getXp();
    }

    public int getXPProgress(PlayerConnect playerConnect) {
        long xp_cur = playerConnect.getXp();
        long xp_req_cur = plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".xp");
        long xp_req_next = plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + (playerConnect.getLevel() + 1) + ".xp");
        double set = (double) (xp_cur - xp_req_cur) / (xp_req_next - xp_req_cur);
        if (xp_cur > xp_req_next) {
            return 100;
        }
        return (int) Math.round(set * 100);
    }

    public String getXPProgressStyle(PlayerConnect playerConnect, String path) {
        char xp = (char) Integer.parseInt(plugin.getFileUtils().config.getString(path + ".xp.symbol").substring(2), 16);
        char none = (char) Integer.parseInt(plugin.getFileUtils().config.getString(path + ".none.symbol").substring(2), 16);
        ChatColor xpColor = getChatColor(plugin.getFileUtils().config.getString(path + ".xp.color"));
        ChatColor noneColor = getChatColor(plugin.getFileUtils().config.getString(path + ".none.color"));
        int bars = plugin.getFileUtils().config.getInt(path + ".amount");
        int progressBars = (bars * getXPProgress(playerConnect) / 100);
        try {
            return Strings.repeat("" + xpColor + xp, progressBars) + Strings.repeat("" + noneColor + none, bars - progressBars);
        } catch (Exception exception) {
            return "";
        }
    }

    public String getPrefix(PlayerConnect playerConnect) {
        long level = playerConnect.getLevel();
        if (!plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + level + ".group")) {
            level = plugin.getFileUtils().config.getLong("start-level");
        }
        return ChatColor.translateAlternateColorCodes('&', plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + level + ".prefix", ""));
    }

    public String getSuffix(PlayerConnect playerConnect) {
        long level = playerConnect.getLevel();
        if (!plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + level + ".group")) {
            level = plugin.getFileUtils().config.getLong("start-level");
        }
        return ChatColor.translateAlternateColorCodes('&', plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + level + ".suffix", ""));
    }

    public String getGroup(PlayerConnect playerConnect) {
        long level = playerConnect.getLevel();
        if (!plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + level + ".group")) {
            level = plugin.getFileUtils().config.getLong("start-level");
        }
        return ChatColor.translateAlternateColorCodes('&', plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + level + ".group", ""));
    }

    public String getTopKills(int number, boolean isName) {
        return getTop(number - 1, isName, topKills);
    }

    public String getTopDeaths(int number, boolean isName) {
        return getTop(number - 1, isName, topDeaths);
    }

    public String getTopXp(int number, boolean isName) {
        return getTop(number - 1, isName, topXp);
    }

    public String getTopLevel(int number, boolean isName) {
        return getTop(number - 1, isName, topLevel);
    }

    public String getTopKillStreak(int number, boolean isName) {
        return getTop(number - 1, isName, topKillStreak);
    }

    public String getTopKillStreakTop(int number, boolean isName) {
        return getTop(number - 1, isName, topKillStreakTop);
    }

    public boolean canProgress(Player player) {
        return !plugin.getFileUtils().config.getStringList("worlds").contains(player.getWorld().getName());
    }

    private String getTop(int number, boolean key, LinkedHashMap<String, Long> topMap) {
        if (key) {
            ArrayList<String> map = new ArrayList<>(topMap.keySet());
            if (map.size() > number) {
                return plugin.getServer().getOfflinePlayer(UUID.fromString(map.get(number))).getName();
            } else {
                return ChatColor.translateAlternateColorCodes('&', plugin.getFileUtils().config.getString("top.name"));
            }
        } else {
            ArrayList<Long> map = new ArrayList<>(topMap.values());
            if (map.size() > number) {
                return String.valueOf(map.get(number));
            } else {
                return ChatColor.translateAlternateColorCodes('&', plugin.getFileUtils().config.getString("top.value"));
            }
        }
    }

    public void updateTopMap() {
        Map<String, Long> unsortedKills = new HashMap<>();
        Map<String, Long> unsortedDeaths = new HashMap<>();
        Map<String, Long> unsortedXp = new HashMap<>();
        Map<String, Long> unsortedLevel = new HashMap<>();
        Map<String, Long> unsortedKillStreak = new HashMap<>();
        Map<String, Long> unsortedKillStreakTop = new HashMap<>();
        List<String> excluded = plugin.getFileUtils().config.getStringList("top.excluded");
        for (OfflinePlayer offlinePlayer : plugin.getServer().getOfflinePlayers()) {
            String uuid = offlinePlayer.getUniqueId().toString();
            if (!excluded.contains(uuid)) {
                PlayerConnect playerConnect = plugin.getPlayerConnect(uuid);
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


    private LinkedHashMap<String, Long> getSortedMap(Map<String, Long> map) {
        return map.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    private ChatColor getChatColor(String colorCode){
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

    public String getType(PlayerConnect playerConnect) {
        String type = playerConnect.getXpType();
        if (type.length() == 0) {
            return plugin.getFileUtils().language.getString("translate.xp.type");
        }
        return type;
    }

    public String getGet(PlayerConnect playerConnect) {
        long type = playerConnect.getXpLast();
        if (type == 0) {
            return plugin.getFileUtils().language.getString("translate.xp.get");
        }
        return String.valueOf(type);
    }

    public String getLost(PlayerConnect playerConnect) {
        long type = playerConnect.getXpLost();
        if (type == 0) {
            return plugin.getFileUtils().language.getString("translate.xp.lost");
        }
        return String.valueOf(type);
    }

    public String getItem(PlayerConnect playerConnect) {
        long type = playerConnect.getXpItem();
        if (type == 0) {
            return plugin.getFileUtils().language.getString("translate.xp.item");
        }
        return String.valueOf(type);
    }

    public String getMultiplier(PlayerConnect playerConnect) {
        double multiplier = playerConnect.getMultiplier();
        if (multiplier == 0D) {
            return plugin.getFileUtils().language.getString("translate.xp.multiplier.none");
        }
        return String.valueOf(multiplier);
    }

    public String getMultiplierTime(PlayerConnect playerConnect) {
        long multiplier = playerConnect.getMultiplierTime();
        if (multiplier == 0) {
            return plugin.getFileUtils().language.getString("translate.xp.multiplier.time");
        }
        return String.valueOf(getTime(multiplier));
    }

    public String getMultiplierTimeLeft(PlayerConnect playerConnect) {
        long multiplier = playerConnect.getMultiplierTimeLeft();
        if (multiplier == 0) {
            return plugin.getFileUtils().language.getString("translate.xp.multiplier.left");
        }
        return String.valueOf(getTime(multiplier));
    }

    public String getTime(long seconds){
        long millis = seconds * 1000;
        String time = "";
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long second = TimeUnit.MILLISECONDS.toSeconds(millis);
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
        if (second > 1) {
            time += second + " " + plugin.getFileUtils().language.getString("translate.time.seconds");
        } else if (second == 1) {
            time += second + " " + plugin.getFileUtils().language.getString("translate.time.second");
        }
        if (time.endsWith(" ")) {
            return time.replaceAll("\\s+$", "");
        }
        return time;
    }

}