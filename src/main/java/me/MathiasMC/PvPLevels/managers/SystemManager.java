package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class SystemManager {

    private final PvPLevels plugin;

    public SystemManager(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    public String getGroup(Player player, FileConfiguration fileConfiguration, String path, boolean world) {
        for (String group : fileConfiguration.getConfigurationSection(path).getKeys(false)) {
            if (!group.equalsIgnoreCase("worlds")) {
                if (player.hasPermission(fileConfiguration.getString(path + "." + group + ".permission"))) {
                    if (world) {
                        if (world(player, fileConfiguration, path + "." + group)) {
                            return group;
                        }
                        return null;
                    }
                    return group;
                }
            }
        }
        return null;
    }

    public boolean world(Player player, FileConfiguration fileConfiguration, String path) {
        if (fileConfiguration.contains(path + ".worlds")) {
            List<String> worlds = fileConfiguration.getStringList(path + ".worlds");
            if (worlds.contains(player.getWorld().getName())) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public void executeCommands(Player player, FileConfiguration fileConfiguration, String path, String key, Long value) {
        String group = getGroup(player, fileConfiguration, path, true);
        if (group != null) {
            if (value > 0 && !fileConfiguration.contains(path + "." + group + "." + value)) {
                return;
            }
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(PvPLevels.call, new Runnable() {
                @Override
                public void run() {
                    String commandPath = path + "." + group + "." + key;
                    if (value > 0) {
                        commandPath = path + "." + group + "." + value + "." + key;
                    }
                    for (String commands : fileConfiguration.getStringList(commandPath)) {
                        PvPLevels.call.getServer().dispatchCommand(plugin.consoleCommandSender, plugin.PlaceholderReplace(player, commands));
                    }
                }
                }, fileConfiguration.getLong(path + "." + group + ".delay"));
        }
    }
}
