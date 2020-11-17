package me.MathiasMC.PvPLevels.gui;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminGUI extends GUI {

    private final PvPLevels plugin = PvPLevels.getInstance();
    private final FileConfiguration file;
    private final Player player = playerMenu.getPlayer();

    public AdminGUI(Menu menu) {
        super(menu);
        file = plugin.guiFiles.get("admin");
        setPlayers();
    }

    @Override
    public FileConfiguration getFile() {
        return this.file;
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
            return;
        }

        if (!e.isShiftClick() && !e.isRightClick()) {
            return;
        }

        if (!player.hasPermission("pvplevels.admin.admin")) {
            return;
        }

        final ItemMeta itemMeta = e.getCurrentItem().getItemMeta();

        if (itemMeta == null) {
            return;
        }

        if (!itemMeta.hasLore()) {
            return;
        }

        final String uuid = getUUID(itemMeta.getLore());

        if (uuid == null) {
            return;
        }

        final OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(UUID.fromString(uuid));
        if (offlinePlayer != null) {
            final String targetUUID = offlinePlayer.getUniqueId().toString();
            plugin.database.delete(targetUUID);
            plugin.unloadPlayerConnect(uuid);
            if (offlinePlayer.isOnline()) {
                for (String command : plugin.getFileUtils().language.getStringList("admin.online")) {
                    plugin.getServer().dispatchCommand(plugin.consoleSender, command.replace("{target}", offlinePlayer.getName()).replace("{player}", player.getName()));
                }
            }
            if (plugin.getFileUtils().config.contains("mysql.purge.commands")) {
                for (String command : plugin.getFileUtils().config.getStringList("mysql.purge.commands")) {
                    plugin.getServer().dispatchCommand(plugin.consoleSender, command.replace("{player}", offlinePlayer.getName()).replace("{uuid}", targetUUID));
                }
            }
            for (String command : plugin.getFileUtils().language.getStringList("admin.deleted")) {
                plugin.getServer().dispatchCommand(plugin.consoleSender, command.replace("{target}", offlinePlayer.getName()).replace("{player}", player.getName()));
            }
            setPlayers();
            super.open();
        }
    }

    private String getUUID(final List<String> lores) {
        for (String lore : lores) {
            final Matcher matcher = Pattern.compile("\\[([^]]+)]").matcher(lore);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    @Override
    public void setItems() {
        plugin.getItemStackManager().setupGUI(inventory, file, player);
    }
}
