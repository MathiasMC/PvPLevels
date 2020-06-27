package me.MathiasMC.PvPLevels.managers;

import com.google.common.base.Strings;
import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class StatsManager {

    private final PvPLevels plugin;

    public StatsManager(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    public String kdr(String uuid) {
        PlayerConnect playerConnect = plugin.get(uuid);
        if (playerConnect.deaths()> 0L) {
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("en", "UK"));
            decimalFormat.applyPattern("#.#");
            return decimalFormat.format(Double.valueOf(playerConnect.kills()) / Double.valueOf(playerConnect.deaths()));
        }
        return String.valueOf(0.0D);
    }

    public Long xp_required(String uuid) {
        PlayerConnect playerConnect = plugin.get(uuid);
        Long level = playerConnect.level();
        if (plugin.levels.get.getConfigurationSection("levels").getKeys(false).size() > level) {
            return plugin.levels.get.getLong("levels." + (level + 1) + ".xp");
        }
        return 0L;
    }

    public String xp_progress(String uuid) {
        PlayerConnect playerConnect = plugin.get(uuid);
        Long currentXP = plugin.levels.get.getLong("levels." + playerConnect.level() + ".xp");
        if (plugin.config.get.getBoolean("levelup.xp-clear")) {
            currentXP = 0L;
        }
        try {
        return new DecimalFormat("#").format(Math.round(((Double.valueOf(playerConnect.xp()) - currentXP) / (plugin.levels.get.getLong("levels." + (playerConnect.level() + 1) + ".xp") - currentXP) * 100) * 10.0) / 10.0);
        } catch (Exception exception) {
            return "";
        }
    }

    public String xp_progress_style(String uuid) {
        char xp = (char) Integer.parseInt(plugin.config.get.getString("xp-progress-style.xp.symbol").substring(2), 16);
        char none = (char) Integer.parseInt(plugin.config.get.getString("xp-progress-style.none.symbol").substring(2), 16);
        ChatColor xpColor = getChatColor(plugin.config.get.getString("xp-progress-style.xp.color"));
        ChatColor noneColor = getChatColor(plugin.config.get.getString("xp-progress-style.none.color"));
        int bars = plugin.config.get.getInt("xp-progress-style.amount");
        int progressBars = (int) (bars * Double.parseDouble(xp_progress(uuid)) / 100);
        try {
            return Strings.repeat("" + xpColor + xp, progressBars) + Strings.repeat("" + noneColor + none, bars - progressBars);
        } catch (Exception exception) {
            return "";
        }
    }

    public String group(Player player) {
        return getGroup(player, plugin.get(player.getUniqueId().toString()).level());
    }

    public String group_to(Player player) {
        return getGroup(player, plugin.get(player.getUniqueId().toString()).level() + 1L);
    }

    private String getGroup(Player player, Long level) {
        String group = plugin.systemManager.getGroup(player, plugin.config.get, "groups.list", false);
        if (group != null) {
            if (plugin.config.get.contains("groups.list." + group + ".list." + level)) {
                return plugin.config.get.getString("groups.list." + group + ".list." + level);
            } else {
                return plugin.config.get.getString("groups.list." + group + ".none");
            }
        }
        return plugin.config.get.getString("groups.none");
    }

    public String prefix(Player player) {
        String prefix_name = "%pvplevels_prefix%";
        String group = plugin.systemManager.getGroup(player, plugin.config.get, "placeholders.prefix", false);
        if (group != null) {
            PlayerConnect playerConnect = plugin.get(player.getUniqueId().toString());
            if (plugin.config.get.contains("placeholders.prefix." + group + ".list." + playerConnect.level())) {
                prefix_name = ChatColor.translateAlternateColorCodes('&', plugin.PlaceholderReplace(player, plugin.config.get.getString("placeholders.prefix." + group + ".list." + playerConnect.level())));
            } else {
                prefix_name = ChatColor.translateAlternateColorCodes('&', plugin.PlaceholderReplace(player, plugin.config.get.getString("placeholders.prefix." + group + ".none")));
            }
        }
        return prefix_name;
    }

    public String getTopValue(String type, int number, boolean key) {
        List<List<String>> map;
        if (key) {
            map = new ArrayList<List<String>>(getTopMap(type).keySet());
            if (map.size() > number) {
                return String.valueOf(map.get(number));
            } else {
                return ChatColor.translateAlternateColorCodes('&', plugin.config.get.getString("pvptop." + type + ".name"));
            }
        }
        map = new ArrayList<List<String>>(getTopMap(type).values());
        if (map.size() > number) {
            return String.valueOf(map.get(number));
        } else {
            return ChatColor.translateAlternateColorCodes('&', plugin.config.get.getString("pvptop." + type + ".value"));
        }
    }

    public void clearKillStreak(PlayerConnect playerConnect, Player player, String killer) {
        if (playerConnect.killstreak() > 0L) {
            String group = plugin.systemManager.getGroup(player, plugin.config.get, "killstreaks", true);
            if (group != null) {
                Long killstreak = playerConnect.killstreak();
                ArrayList<Integer> list = new ArrayList<>();
                for (String inter : plugin.config.get.getConfigurationSection("killstreaks." + group).getKeys(false)) {
                    if (!inter.equalsIgnoreCase("delay") && !inter.equalsIgnoreCase("permission") && !inter.equalsIgnoreCase("worlds")) {
                        if (killstreak >= Integer.parseInt(inter)) {
                            list.add(Integer.valueOf(inter));
                        }
                    }
                }
                if (!list.isEmpty()) {
                    for (String commands : plugin.config.get.getStringList("killstreaks." + group + "." +  + list.get(list.size() - 1) + ".lose-commands")) {
                        PvPLevels.call.getServer().dispatchCommand(plugin.consoleCommandSender, plugin.PlaceholderReplace(player, commands.replace("{pvplevels_killstreak_lost}", String.valueOf(killstreak)).replace("{pvplevels_type}", killer)));
                    }
                }
            }
            playerConnect.killstreak(0L);
        }
    }

    public LinkedHashMap getTopMap(String type) {
        Map<String, Long> unsorted = new HashMap<>();
        for (String uuid : plugin.list()) {
            if (type.equalsIgnoreCase("kills") && !plugin.config.get.getStringList("pvptop.kills.excluded").contains(uuid)) { unsorted.put(PvPLevels.call.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName(), plugin.get(uuid).kills()); }
            if (type.equalsIgnoreCase("deaths") && !plugin.config.get.getStringList("pvptop.deaths.excluded").contains(uuid)) { unsorted.put(PvPLevels.call.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName(), plugin.get(uuid).deaths()); }
            if (type.equalsIgnoreCase("xp") && !plugin.config.get.getStringList("pvptop.xp.excluded").contains(uuid)) { unsorted.put(PvPLevels.call.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName(), plugin.get(uuid).xp()); }
            if (type.equalsIgnoreCase("level") && !plugin.config.get.getStringList("pvptop.level.excluded").contains(uuid)) { unsorted.put(PvPLevels.call.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName(), plugin.get(uuid).level()); }
        }
        LinkedHashMap<String, Long> sorted = new LinkedHashMap<>();
        unsorted.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
        return sorted;
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
}
