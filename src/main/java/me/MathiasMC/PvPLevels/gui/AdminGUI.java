package me.MathiasMC.PvPLevels.gui;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminGUI extends GUI {

    private final PvPLevels plugin = PvPLevels.call;
    private final FileConfiguration file;
    private final Player player = playerMenu.getPlayer();
    private final int maxItems;

    public AdminGUI(Menu menu) {
        super(menu);
        file = plugin.guiFiles.get("admin");
        setPlayers();
        maxItems = file.getInt("settings.perpage");
    }

    @Override
    public String getName() {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(file.getString("settings.name")));
    }

    @Override
    public int getSize() {
        return file.getInt("settings.size");
    }

    @Override
    public void click(InventoryClickEvent e) {
        final int slot = e.getSlot();
        if (file.contains(String.valueOf(slot))) {
            final List<String> list = file.getStringList(slot + ".OPTIONS");
            if (list.contains("NEXT")) {
                if (!((index + 1) >= players.size())) {
                    page = page + 1;
                }
            } else if (list.contains("BACK")) {
                if (page > 0) {
                    page = page - 1;
                }
            } else if (list.contains("SORT_KILLS")) {
                playerMenu.setSort("kills");
            } else if (list.contains("SORT_DEATHS")) {
                playerMenu.setSort("deaths");
            } else if (list.contains("SORT_XP")) {
                playerMenu.setSort("xp");
            } else if (list.contains("SORT_LEVEL")) {
                playerMenu.setSort("level");
            } else if (list.contains("SORT_KDR")) {
                playerMenu.setSort("kdr");
            } else if (list.contains("SORT_KILLFACTOR")) {
                playerMenu.setSort("killfactor");
            } else if (list.contains("SORT_KILLSTREAK")) {
                playerMenu.setSort("killstreak");
            } else if (list.contains("SORT_KILLSTREAK_TOP")) {
                playerMenu.setSort("killstreak_top");
            } else if (list.contains("SORT_LASTSEEN")) {
                playerMenu.setSort("lastseen");
            } else if (list.contains("SORT_REVERSE")) {
                playerMenu.setReverse(!playerMenu.getReverse());
            }
            setPlayers();
            super.open();
        } else {
            if (e.isShiftClick() && e.isRightClick() && player.hasPermission("pvplevels.admin.pvpadmin")) {
                if (e.getCurrentItem().getItemMeta().hasLore()) {
                    for (String lore : e.getCurrentItem().getItemMeta().getLore()) {
                        final Matcher matcher = Pattern.compile("\\[([^]]+)]").matcher(lore);
                        if (matcher.find()) {
                            final OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(UUID.fromString(matcher.group(1)));
                            if (offlinePlayer != null) {
                                final String targetUUID = offlinePlayer.getUniqueId().toString();
                                if (offlinePlayer.isOnline()) {
                                    for (String command : plugin.language.get.getStringList("pvpadmin.online")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleSender, command.replace("{target}", offlinePlayer.getName()).replace("{player}", player.getName()));
                                    }
                                }
                                plugin.unloadPlayerConnect(targetUUID);
                                plugin.database.delete(targetUUID);
                                if (plugin.config.get.contains("mysql.purge.commands")) {
                                    for (String command : plugin.config.get.getStringList("mysql.purge.commands")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleSender, command.replace("{pvplevels_player}", offlinePlayer.getName()).replace("{pvplevels_uuid}", targetUUID));
                                    }
                                }
                                for (String command : plugin.language.get.getStringList("pvpadmin.deleted")) {
                                    plugin.getServer().dispatchCommand(plugin.consoleSender, command.replace("{target}", offlinePlayer.getName()).replace("{player}", player.getName()));
                                }
                                setPlayers();
                                super.open();
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setItems() {
        plugin.guiManager.setGUIItemStack(inventory, file, player);
        if (!players.isEmpty()) {
            for (int i = 0; i < maxItems; i++) {
                index = maxItems * page + i;
                if (index >= players.size()) break;
                final OfflinePlayer offlinePlayer = players.get(index);
                if (offlinePlayer != null) {
                    inventory.addItem(plugin.guiManager.getPlayerHead(file, offlinePlayer, offlinePlayer.getName(), "settings.player"));
                }
            }
        }
    }
}
