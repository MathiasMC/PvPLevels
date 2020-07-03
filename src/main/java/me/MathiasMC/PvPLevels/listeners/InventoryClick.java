package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.gui.GUI;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InventoryClick implements Listener {

    private final PvPLevels plugin;

    public InventoryClick(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof GUI) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getItemMeta() == null) {
                return;
            }
            if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                e.setCancelled(true);
            }
            if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
                e.setCancelled(true);
                return;
            }
            if (e.isShiftClick()) {
                e.setCancelled(true);
            }
            Player player = (Player) e.getWhoClicked();
            player.updateInventory();
            for (String key : plugin.guiList.keySet()) {
                if (plugin.guiList.get(key) == e.getInventory().getHolder()) {
                    FileConfiguration fileConfiguration = plugin.guiList.get(key).fileConfiguration;
                    if (fileConfiguration.contains("settings.global-boosters")) {
                        boostersClick(player, e.getSlot(), "global-settings", "global", true);
                    } else if (fileConfiguration.contains("settings.personal-boosters")) {
                        boostersClick(player, e.getSlot(), "personal-settings", "personal", false);
                    } else if (fileConfiguration.contains("settings.profile-all")) {
                        if (e.getSlot() == fileConfiguration.getInt("settings.profile-all.next.POSITION")) {
                            plugin.guiPageID.put(player.getUniqueId().toString(), plugin.guiPageID.get(player.getUniqueId().toString()) + 1);
                            plugin.getServer().dispatchCommand(plugin.consoleCommandSender, "pvplevels gui open profileAll.yml " + player.getName());
                        } else if (e.getSlot() == fileConfiguration.getInt("settings.profile-all.back.POSITION")) {
                            plugin.guiPageID.put(player.getUniqueId().toString(), plugin.guiPageID.get(player.getUniqueId().toString()) - 1);
                            plugin.getServer().dispatchCommand(plugin.consoleCommandSender, "pvplevels gui open profileAll.yml " + player.getName());
                        } else {
                            if (e.isShiftClick() && e.isRightClick() && player.hasPermission("pvplevels.gui.admin.delete")) {
                                if (e.getCurrentItem().getItemMeta().hasLore()) {
                                    for (String lore : e.getCurrentItem().getItemMeta().getLore()) {
                                        Matcher matcher = Pattern.compile("\\[([^]]+)\\]").matcher(lore);
                                        while (matcher.find()) {
                                            OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(UUID.fromString(matcher.group(1)));
                                            if (offlinePlayer != null) {
                                                String targetUUID = offlinePlayer.getUniqueId().toString();
                                                if (!player.getUniqueId().toString().equalsIgnoreCase(targetUUID)) {
                                                    if (offlinePlayer.isOnline()) {
                                                        Player target = (Player) offlinePlayer;
                                                        target.kickPlayer("");
                                                    }
                                                    plugin.unload(targetUUID);
                                                    plugin.database.delete(targetUUID);
                                                    for (String command : plugin.language.get.getStringList("player.pvpadmin.deleted-commands")) {
                                                        plugin.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvplevels_target}", offlinePlayer.getName()).replace("{pvplevels_player}", player.getName()));
                                                    }
                                                } else {
                                                    for (String message : plugin.language.get.getStringList("player.pvpadmin.you")) {
                                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    for (String keyItem : fileConfiguration.getConfigurationSection("").getKeys(false)) {
                        if (!keyItem.equalsIgnoreCase("settings")) {
                            if (e.getSlot() == fileConfiguration.getInt(keyItem + ".POSITION")) {
                                if (fileConfiguration.contains(keyItem + ".OPTIONS")) {
                                    if (fileConfiguration.getStringList(keyItem + ".OPTIONS").contains("SORT_KILLS")) { plugin.guiPageSort.put(player.getUniqueId().toString(), "kills"); }
                                    if (fileConfiguration.getStringList(keyItem + ".OPTIONS").contains("SORT_DEATHS")) { plugin.guiPageSort.put(player.getUniqueId().toString(), "deaths"); }
                                    if (fileConfiguration.getStringList(keyItem + ".OPTIONS").contains("SORT_XP")) { plugin.guiPageSort.put(player.getUniqueId().toString(), "xp"); }
                                    if (fileConfiguration.getStringList(keyItem + ".OPTIONS").contains("SORT_LEVEL")) { plugin.guiPageSort.put(player.getUniqueId().toString(), "level"); }
                                    if (fileConfiguration.getStringList(keyItem + ".OPTIONS").contains("SORT_KILLSTREAK")) { plugin.guiPageSort.put(player.getUniqueId().toString(), "killstreak"); }
                                    if (fileConfiguration.getStringList(keyItem + ".OPTIONS").contains("SORT_LASTSEEN")) { plugin.guiPageSort.put(player.getUniqueId().toString(), "lastseen"); }
                                    if (fileConfiguration.getStringList(keyItem + ".OPTIONS").contains("CLOSE")) { player.closeInventory(); }
                                }
                                if (fileConfiguration.contains(keyItem + ".COMMANDS")) {
                                    for (String command : fileConfiguration.getStringList(keyItem + ".COMMANDS")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvplevels_player}", player.getName()));
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    private void boostersClick(Player player, int slot, String path, String key, boolean global) {
        List<String> list = plugin.boosters.get.getStringList("players." + player.getUniqueId().toString() + "." + key);
        for (String line : list) {
            if (line.split(" ").length == 3) {
                if (slot == Integer.parseInt(line.split(" ")[2])) {
                    if (global) {
                        global(player, line, list);
                    } else {
                        personal(player, line, list);
                    }
                    if (plugin.boosters.get.contains(path + ".gui." + line.split(" ")[0] + ".OPTIONS") && plugin.boosters.get.getStringList(path + ".gui." + line.split(" ")[0] + ".OPTIONS").contains("CLOSE")) { player.closeInventory(); }
                    if (plugin.boosters.get.contains(path + ".gui.none.OPTIONS") && plugin.boosters.get.getStringList(path + ".gui.none.OPTIONS").contains("CLOSE")) { player.closeInventory(); }
                    break;
                }
            }
        }
    }


    private void global(Player player, String line, List<String> list) {
        int maxQueue = plugin.boostersManager.isInQueueSize(player.getUniqueId().toString());
        if (maxQueue < plugin.boosters.get.getInt("global-settings.max-queue")) {
            List<String> queue = plugin.boosters.get.getStringList("global-queue");
            queue.add(player.getUniqueId().toString() + " " + line.split(" ")[0] + " " + line.split(" ")[1]);
            plugin.boosters.get.set("global-queue", queue);
            for (String command : plugin.boosters.get.getStringList("global-settings.queue.add")) {
                plugin.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvplevels_player}", player.getName()).replace("{pvplevels_booster_global_type}", String.valueOf(line.split(" ")[0])).replace("{pvplevels_booster_global_time}", plugin.boostersManager.timeLeft(Integer.parseInt(line.split(" ")[1]))).replace("{pvplevels_booster_global_queue}", String.valueOf(plugin.boostersManager.queueNumber(player.getUniqueId().toString()))));
            }
            list.remove(line);
            plugin.boosters.get.set("players." + player.getUniqueId().toString() + ".global", list);
            plugin.boosters.save();
        } else {
            for (String command : plugin.boosters.get.getStringList("global-settings.queue.max")) {
                plugin.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvplevels_player}", player.getName()).replace("{pvplevels_booster_global_max}", String.valueOf(maxQueue)));
            }
        }
    }

    private void personal(Player player, String line, List<String> list) {
        PlayerConnect playerConnect = plugin.get(player.getUniqueId().toString());
        if (playerConnect.getPersonalBooster() == null && !plugin.boosters.get.contains("players." + player.getUniqueId().toString() + ".personal-active")) {
            playerConnect.timer(Integer.parseInt(line.split(" ")[1]), Double.valueOf(line.split(" ")[0]));
            plugin.boosters.get.set("players." + player.getUniqueId().toString() + ".personal-active", line.split(" ")[0] + " " + line.split(" ")[1]);
            for (String command : plugin.boosters.get.getStringList("personal-settings.start")) {
                plugin.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvplevels_player}", player.getName()).replace("{pvplevels_booster_personal_type}", String.valueOf(line.split(" ")[0])).replace("{pvplevels_booster_personal_time}", plugin.boostersManager.timeLeft(Integer.parseInt(line.split(" ")[1]))));
            }
            list.remove(line);
            plugin.boosters.get.set("players." + player.getUniqueId().toString() + ".personal", list);
            plugin.boosters.save();
        } else {
            for (String command : plugin.boosters.get.getStringList("personal-settings.active")) {
                plugin.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvplevels_player}", player.getName()));
            }
        }
    }
}