package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Objects;

public class GUIManager {

    private final PvPLevels plugin;

    public GUIManager(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    public void setGUIItemStack(final Inventory inventory, final FileConfiguration file, final Player player) {
        for (String key : Objects.requireNonNull(file.getConfigurationSection("")).getKeys(false)) {
            if (!key.equalsIgnoreCase("settings")) {
                if (file.contains(key + ".OPTIONS") && file.getStringList(key + ".OPTIONS").contains("PLAYER_HEAD")) {
                    inventory.setItem(Integer.parseInt(key), getPlayerHead(file, player, player.getName(), key));
                } else {
                    final String material = file.getString(key + ".MATERIAL");
                    ItemStack itemStack = plugin.getID(material, file.getInt(key + ".AMOUNT"));
                    final ItemMeta itemMeta = itemStack.getItemMeta();
                    if (itemMeta == null) {
                        return;
                    }
                    itemMeta.setDisplayName(plugin.replacePlaceholders(player, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(file.getString(key + ".NAME")))));
                    final ArrayList<String> list = new ArrayList<>();
                    for (String lores : file.getStringList(key + ".LORES")) {
                        list.add(plugin.replacePlaceholders(player, ChatColor.translateAlternateColorCodes('&', lores)));
                    }
                    itemMeta.setLore(list);
                    itemStack.setItemMeta(itemMeta);
                    glow(itemStack, file, key + ".OPTIONS");
                    inventory.setItem(Integer.parseInt(key), itemStack);
                }
            }
        }
    }

    public ItemStack getPlayerHead(final FileConfiguration file, final OfflinePlayer offlinePlayer, final String name, final String path) {
        final ItemStack itemStack = plugin.getID(file.getString(path + ".MATERIAL"), file.getInt(path + ".AMOUNT"));
        final SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        itemMeta.setOwner(name);
        itemMeta.setDisplayName(plugin.replacePlaceholders(offlinePlayer, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(file.getString(path + ".NAME")))));
        final ArrayList<String> list = new ArrayList<>();
        for (String lores : file.getStringList(path + ".LORES")) {
            list.add(plugin.replacePlaceholders(offlinePlayer, ChatColor.translateAlternateColorCodes('&', lores)));
        }
        itemMeta.setLore(list);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void glow(final ItemStack itemStack, final FileConfiguration file, final String path) {
        if (file.contains(path) && file.getStringList(path).contains("GLOW")) {
            itemStack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 0);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta == null) {
                return;
            }
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemStack.setItemMeta(itemMeta);
        }
    }
}
