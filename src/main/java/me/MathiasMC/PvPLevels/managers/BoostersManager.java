package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class BoostersManager {

    private final PvPLevels plugin;

    private String globalUUID;

    private Double globalBoost;

    private int globalSeconds = 0;

    private OfflinePlayer offlinePlayer;

    public BoostersManager(final PvPLevels plugin) {
        this.plugin = plugin;

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (!hasGlobalActive()) {
                    if (globalQueueSize() > 0) {
                        String[] globalSelect = globalQueue().get(0).split(" ");
                        globalUUID = globalSelect[0];
                        globalBoost = Double.valueOf(globalSelect[1]);
                        globalSeconds = Integer.valueOf(globalSelect[2]);
                        offlinePlayer = plugin.getServer().getOfflinePlayer(UUID.fromString(globalUUID));
                        globalStarted();
                    }
                    return;
                }
                if (globalSeconds > 0) {
                    globalSeconds--;
                    return;
                }
                plugin.boosters.get.set("global-queue", globalRemove());
                plugin.boosters.save();
                globalEnded();
                globalUUID = null;
                globalBoost = null;
                globalSeconds = 0;
                offlinePlayer = null;
            }
        }, 20L, 20L);
    }

    public boolean hasGlobalActive() {
        if (globalUUID == null && globalBoost == null && globalSeconds == 0) {
            return false;
        }
        return true;
    }

    public List<String> globalQueue() {
        return plugin.boosters.get.getStringList("global-queue");
    }

    public int globalQueueSize() {
        return globalQueue().size();
    }

    public List<String> globalRemove() {
        List<String> queue = globalQueue();
        queue.remove(0);
        return queue;
    }

    public Double type() {
        return globalBoost;
    }

    public int isInQueueSize(String uuid) {
        int value = 0;
        for (String list : globalQueue()) {
            if (list.split(" ")[0].equalsIgnoreCase(uuid)) {
                value++;
            }
        }
        return value;
    }

    public int queueNumber(String uuid) {
        int value = 1;
        List<Integer> number = new ArrayList<>();
        List<String> list = globalQueue();
        for (int i = 0; i < globalQueueSize(); i++) {
            if (list.get(i).split(" ")[0].equalsIgnoreCase(uuid)) {
                number.add(value + i);
            }
        }
        return number.get(number.size() - 1);
    }

    public void globalStarted() {
        for (String command : plugin.boosters.get.getStringList("global-settings.start")) {
            PvPLevels.call.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvplevels_player}", offlinePlayer.getName()).replace("{pvplevels_booster_global_type}", String.valueOf(globalBoost)).replace("{pvplevels_booster_global_time}", timeLeft(globalSeconds)));
        }
    }

    public void globalEnded() {
        for (String command : plugin.boosters.get.getStringList("global-settings.stop.ended")) {
            PvPLevels.call.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvplevels_player}", offlinePlayer.getName()).replace("{pvplevels_booster_global_type}", String.valueOf(globalBoost)));
        }
        if (globalQueueSize() == 0) {
            for (String command : plugin.boosters.get.getStringList("global-settings.stop.queue")) {
                PvPLevels.call.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvplevels_player}", offlinePlayer.getName()).replace("{pvplevels_booster_global_type}", String.valueOf(globalBoost)));
            }
        }
    }

    public String timeLeft(int seconds) {
        return String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
    }

    public String globalPrefix(Long boost) {
        if (hasGlobalActive()) {
            return ChatColor.translateAlternateColorCodes('&', plugin.boosters.get.getString("global-settings.prefix").replace("{pvplevels_booster_global_xp}", String.valueOf(boost)));
        } else {
            return "";
        }
    }

    public String personalPrefix(String uuid, Long boost) {
        if (plugin.get(uuid).getPersonalBooster() != null) {
            return ChatColor.translateAlternateColorCodes('&', plugin.boosters.get.getString("personal-settings.prefix").replace("{pvplevels_booster_personal_xp}", String.valueOf(boost)));
        } else {
            return "";
        }
    }
}
