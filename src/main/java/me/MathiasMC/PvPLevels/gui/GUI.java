package me.MathiasMC.PvPLevels.gui;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GUI implements InventoryHolder {

    private final PvPLevels plugin;

    private Inventory inventory;

    public FileConfiguration fileConfiguration;

    public GUI(final PvPLevels plugin, FileConfiguration fileConfiguration) {
        this.plugin = plugin;
        this.fileConfiguration = fileConfiguration;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void open(Player player) {
        if (fileConfiguration.contains("settings.require")) {
            if (fileConfiguration.contains("settings.require.level")) {
                PlayerConnect playerConnect = plugin.get(player.getUniqueId().toString());
                if (playerConnect.level() < fileConfiguration.getLong("settings.require.level.amount")) {
                    for (String command : fileConfiguration.getStringList("settings.require.level.required")) {
                        plugin.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvplevels_player}", player.getName()));
                    }
                    return;
                }
            }
        }
        loadGUI(player);
    }

    private void loadGUI(Player player) {
        inventory = plugin.getServer().createInventory(this, fileConfiguration.getInt("settings.size"), ChatColor.translateAlternateColorCodes('&', fileConfiguration.getString("settings.name")));
        BukkitRunnable r = new BukkitRunnable() {
            public void run() {
                index(player);
            }
        };
        r.runTaskAsynchronously(plugin);
        player.openInventory(inventory);
    }


    private void index(Player player) {
        for (String key : fileConfiguration.getConfigurationSection("").getKeys(false)) {
            if (!key.equalsIgnoreCase("settings")) {
                inventory.setItem(fileConfiguration.getInt(key + ".POSITION"), getItem(fileConfiguration.getString(key + ".MATERIAL"), fileConfiguration.getInt(key + ".AMOUNT"), fileConfiguration.getString(key + ".NAME"), fileConfiguration.getStringList(key + ".LORES"), player, key, fileConfiguration, "", 0));
            }
        }
        if (fileConfiguration.contains("settings.global-boosters")) {
            boostersGUI(player, "global-settings", "global-boosters", "global");
        } else if (fileConfiguration.contains("settings.personal-boosters")) {
            boostersGUI(player, "personal-settings", "personal-boosters", "personal");
        } else if (fileConfiguration.contains("settings.profile-all")) {
            ArrayList<ItemStack> profiles = new ArrayList<>();
            ArrayList<Integer> perPage = new ArrayList<>();
            if (!plugin.guiPageID.containsKey(player.getUniqueId().toString())) {
                plugin.guiPageID.put(player.getUniqueId().toString(), 1);
            }
            if (plugin.guiPageID.get(player.getUniqueId().toString()) > 1) {
                inventory.setItem(fileConfiguration.getInt("settings.profile-all.back.POSITION"), getItem(fileConfiguration.getString("settings.profile-all.back.MATERIAL"), fileConfiguration.getInt("settings.profile-all.back.AMOUNT"), fileConfiguration.getString("settings.profile-all.back.NAME"), fileConfiguration.getStringList("settings.profile-all.back.LORES"), player, "settings.profile-all.back", fileConfiguration, "", 0));
            }
            if (!plugin.guiPageSort.containsKey(player.getUniqueId().toString())) {
                plugin.guiPageSort.put(player.getUniqueId().toString(), fileConfiguration.getString("settings.profile-all.default-sort"));
            }
            List<String> map = new ArrayList<String>(plugin.statsManager.getTopMap(plugin.guiPageSort.get(player.getUniqueId().toString())).keySet());
            for (String s : map) {
                OfflinePlayer offlinePlayer = PvPLevels.call.getServer().getOfflinePlayer(UUID.fromString(s));
                ItemStack itemStack = plugin.getID(fileConfiguration.getString("settings.profile-all.MATERIAL"), fileConfiguration.getInt("settings.profile-all.AMOUNT"));
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.OfflinePlaceholderReplace(offlinePlayer, fileConfiguration.getString("settings.profile-all.NAME"))));
                ArrayList<String> list = new ArrayList<>();
                for (String lores : fileConfiguration.getStringList("settings.profile-all.LORES")) {
                    list.add(ChatColor.translateAlternateColorCodes('&', plugin.OfflinePlaceholderReplace(offlinePlayer, lores)));
                }
                skullMeta.setLore(list);
                skullMeta.setOwner(offlinePlayer.getName());
                itemStack.setItemMeta(skullMeta);
                profiles.add(itemStack);
            }
            ArrayList<Integer> ac = calulatePosition(plugin.guiPageID.get(player.getUniqueId().toString()), fileConfiguration.getInt("settings.profile-all.PAGE"), fileConfiguration.getInt("settings.profile-all.INDEX"), profiles, perPage);
            if (ac.size() >= fileConfiguration.getInt("settings.profile-all.PAGE")) {
                inventory.setItem(fileConfiguration.getInt("settings.profile-all.next.POSITION"), getItem(fileConfiguration.getString("settings.profile-all.next.MATERIAL"), fileConfiguration.getInt("settings.profile-all.next.AMOUNT"), fileConfiguration.getString("settings.profile-all.next.NAME"), fileConfiguration.getStringList("settings.profile-all.next.LORES"), player, "settings.profile-all.next", fileConfiguration, "", 0));
            }
        }
    }

    private ArrayList<Integer> calulatePosition(int id, int pageIndex, int indexSize, ArrayList<ItemStack> itemList, ArrayList<Integer> perPage) {
        int index = id * pageIndex - pageIndex;
        int endIndex = Math.min(index + pageIndex, itemList.size());
        for (; index < endIndex; index++) {
            perPage.add(index);
        }
        int ItemCount = 0;
        ArrayList<Integer> ac = new ArrayList<>();
        for (int ii = indexSize; ii < inventory.getSize(); ii++) {
            if (inventory.getItem(ii) == null) {
                if (ItemCount < itemList.size()) {
                    try {
                        inventory.setItem(ii, itemList.get(perPage.get(ItemCount)));
                        ItemCount++;
                        if (!ac.contains(ii))
                            ac.add(ii);
                    } catch (Exception e) {
                    }
                }
            }
        }
        return ac;
    }

    private void boostersGUI(Player player, String path, String settings, String key) {
        List<String> list = plugin.boosters.get.getStringList("players." + player.getUniqueId().toString() + "." + key);
        if (!list.isEmpty()) {
            ArrayList<ItemStack> boosters = new ArrayList<>();
            for (String booster : list) {
                String[] split = booster.split(" ");
                if (plugin.boosters.get.contains(path + ".gui." + booster.split(" ")[0])) {
                    boosters.add(getItem(plugin.boosters.get.getString(path + ".gui." + split[0] + ".MATERIAL"), plugin.boosters.get.getInt(path + ".gui." + split[0] + ".AMOUNT"), plugin.boosters.get.getString(path + ".gui." + split[0] + ".NAME"), plugin.boosters.get.getStringList(path + ".gui." + split[0] + ".LORES"), player, path + ".gui." + split[0], plugin.boosters.get, split[0], Integer.parseInt(split[1])));
                } else {
                    boosters.add(getItem(plugin.boosters.get.getString(path + ".gui.none.MATERIAL"), plugin.boosters.get.getInt(path + ".gui.none.AMOUNT"), plugin.boosters.get.getString(path + ".gui.none.NAME"), plugin.boosters.get.getStringList(path + ".gui.none.LORES"), player, path + ".gui.none", plugin.boosters.get, split[0], Integer.parseInt(split[1])));
                }
            }
            ArrayList<Integer> perPage = new ArrayList<>();
            ArrayList<Integer> ac = calulatePosition(1, fileConfiguration.getInt("settings." + settings + ".PAGE"), fileConfiguration.getInt("settings." + settings + ".INDEX"), boosters, perPage);
            List<String> ad = plugin.boosters.get.getStringList("players." + player.getUniqueId().toString() + "." + key);
            List<String> newList = new ArrayList<>();
            int iii;
            for (iii = 0; iii < ad.size(); iii++) {
                newList.add(ad.get(iii).split(" ")[0] + " " + ad.get(iii).split(" ")[1]);
            }
            for (iii = 0; iii < ac.size(); iii++) {
                newList.set(perPage.get(iii), ad.get(iii).split(" ")[0] + " " + ad.get(iii).split(" ")[1] + " " + ac.get(iii));
            }
            if (!ad.equals(newList)) {
                plugin.boosters.get.set("players." + player.getUniqueId().toString() + "." + key, newList);
                plugin.boosters.save();
            }
        }
    }

    private ItemStack getItem(final String material, final int amount, final String name, final List<String> lore, final Player player, final String key, FileConfiguration fileConfiguration, String type, int time) {
        ItemStack itemStack = plugin.getID(material, amount);
        if (fileConfiguration.contains(key + ".OPTIONS") && fileConfiguration.getStringList(key + ".OPTIONS").contains("PLAYERSKULL")) {
            setItemMeta(itemStack, player, name, lore, key, type, time);
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            skullMeta.setOwner(player.getName());
            itemStack.setItemMeta(skullMeta);
            return itemStack;
    } else if (itemStack != null) {
            setItemMeta(itemStack, player, name, lore, key, type, time);
            return itemStack;
        } else {
            plugin.textUtils.error("Cannot find gui item");
            return null;
        }
    }

    public void setItemMeta(ItemStack itemStack, Player player, String name, List<String> lore, String key, String type, int time) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (fileConfiguration.contains(key + ".OPTIONS") && fileConfiguration.getStringList(key + ".OPTIONS").contains("GLOW")) {
            itemStack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 0);
            itemMeta = itemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', PvPLevels.call.PlaceholderReplace(player, name.replace("{pvplevels_booster_type}", type).replace("{pvplevels_booster_time}", plugin.boostersManager.timeLeft(time)))));
        ArrayList<String> list = new ArrayList<>();
        for (String lores : lore) {
            list.add(ChatColor.translateAlternateColorCodes('&', PvPLevels.call.PlaceholderReplace(player, lores.replace("{pvplevels_booster_type}", type).replace("{pvplevels_booster_time}", plugin.boostersManager.timeLeft(time)))));
        }
        itemMeta.setLore(list);
        itemStack.setItemMeta(itemMeta);
    }
}