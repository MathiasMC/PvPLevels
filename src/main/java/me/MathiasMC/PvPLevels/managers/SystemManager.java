package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

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
            return worlds.contains(player.getWorld().getName());
        }
        return true;
    }

    public boolean isInLocation(Location block, Location start, Location end) {
        Location loc1 = new Location(block.getWorld(), Math.min(start.getBlockX(), end.getBlockX()), Math.min(start.getBlockY(), end.getBlockY()), Math.min(start.getBlockZ(), end.getBlockZ()));
        Location loc2 = new Location(block.getWorld(), Math.max(start.getBlockX(), end.getBlockX()), Math.max(start.getBlockY(), end.getBlockY()), Math.max(start.getBlockZ(), end.getBlockZ()));
        if (block.getBlockX() >= loc1.getBlockX() && block.getBlockX() <= loc2.getBlockX() && block.getBlockY() >= loc1.getBlockY() && block.getBlockY() <= loc2.getBlockY() && block.getBlockZ() >= loc1.getBlockZ() && block.getBlockZ() <= loc2.getBlockZ()) {
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
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    String commandPath = path + "." + group + "." + key;
                    if (value > 0) {
                        commandPath = path + "." + group + "." + value + "." + key;
                    }
                    for (String commands : fileConfiguration.getStringList(commandPath)) {
                        PvPLevels.call.getServer().dispatchCommand(plugin.consoleCommandSender, plugin.PlaceholderReplace(player, commands));
                    }
                }, fileConfiguration.getLong(path + "." + group + ".delay"));
        }
    }

    public void saveSchedule() {
        final int interval = plugin.config.get.getInt("save.interval");
        plugin.textUtils.info("Saving cached data to the database every " + interval + " minutes");
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (String uuid : plugin.list()) {
                plugin.get(uuid).save();
            }
            if (plugin.config.get.getBoolean("debug.save")) { plugin.textUtils.debug("Saved cached data to database"); }
        }, interval * 1200,interval * 1200);
    }

    public boolean hasItem(Player player, String path) {
        if (!plugin.config.get.contains(path + ".item")) { return true; }
        PlayerInventory inventory = player.getInventory();
        ItemStack itemStack;
        try {
            itemStack = inventory.getItemInMainHand();
        } catch (NoSuchMethodError error) {
            itemStack = inventory.getItemInHand();
        }
        if (itemStack == null) { return false; }
        String itemStackName = itemStack.getType().name();
        if (plugin.config.get.contains(path + ".item.items")) {
            for (String next : plugin.config.get.getConfigurationSection(path + ".item.items").getKeys(false)) {
                if (next.equalsIgnoreCase(itemStackName.toLowerCase())) {
                    if (itemStack.hasItemMeta()) {
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        boolean check = true;
                        String displayName = plugin.PlaceholderReplace(player, ChatColor.translateAlternateColorCodes('&', plugin.config.get.getString(path + ".item.items." + next + ".name")));
                        if (itemMeta.hasDisplayName()) {
                            if (!itemMeta.getDisplayName().equalsIgnoreCase(displayName)) {
                                check = false;
                            }
                        } else if (!displayName.isEmpty()) {
                            check = false;
                        }
                        List<String> lores = plugin.config.get.getStringList(path + ".item.items." + next + ".lores");
                        if (itemMeta.hasLore()) {
                            List<String> list = new ArrayList<>();
                            for (String lore : lores) {
                                list.add(plugin.PlaceholderReplace(player, ChatColor.translateAlternateColorCodes('&', lore)));
                            }
                            if (!itemMeta.getLore().equals(list)) {
                                check = false;
                            }
                        } else if (!lores.isEmpty()) {
                            check = false;
                        }
                        if (check) {
                            return true;
                        }
                    }
                    break;
                }
            }
        }
        return false;
    }
}