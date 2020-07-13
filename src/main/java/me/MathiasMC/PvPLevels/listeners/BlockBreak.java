package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreak implements Listener {

    private final PvPLevels plugin;

    public BlockBreak(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent e) {
        Location location = e.getBlock().getLocation();
        Player player = e.getPlayer();
        PlayerConnect playerConnect = plugin.get(player.getUniqueId().toString());
        if (!plugin.xpManager.isMaxLevel(player, playerConnect)) {
            if (!plugin.blocksList.contains(location)) {
                final Material material = e.getBlock().getType();
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (e.getBlock().getLocation().getBlock().getType().equals(Material.AIR)) {
                        plugin.xpManager.check(playerConnect, material.name().toLowerCase(), null, e.getPlayer(), true);
                    }
                }, 2L);
            } else {
                plugin.blocksList.remove(location);
            }
        }
        ItemStack itemStack;
        try {
            itemStack = player.getInventory().getItemInMainHand();
        } catch (NoSuchMethodError error) {
            itemStack = player.getInventory().getItemInHand();
        }
        if (itemStack.equals(plugin.wand)) {
            if (player.hasPermission("pvplevels.wand.use")) {
                String uuid = player.getUniqueId().toString();
                if (!plugin.wandPos1.containsKey(uuid)) {
                    Location blockLocation = e.getBlock().getLocation();
                    plugin.wandPos1.put(uuid, blockLocation);
                    for (String message : plugin.language.get.getStringList("player.pvplevels.wand.set.1")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvplevels_x}", String.valueOf(location.getBlockX())).replace("{pvplevels_y}", String.valueOf(location.getBlockY())).replace("{pvplevels_z}", String.valueOf(location.getBlockZ()))));
                    }
                } else {
                    Location blockLocation = e.getBlock().getLocation();
                    if (!plugin.wandPos1.get(uuid).equals(blockLocation)) {
                        if (!plugin.wandPos2.containsKey(uuid)) {
                            plugin.wandPos2.put(uuid, blockLocation);
                            for (String message : plugin.language.get.getStringList("player.pvplevels.wand.set.2")) {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvplevels_x}", String.valueOf(location.getBlockX())).replace("{pvplevels_y}", String.valueOf(location.getBlockY())).replace("{pvplevels_z}", String.valueOf(location.getBlockZ()))));
                            }
                        } else {
                            plugin.wandPos1.remove(uuid);
                            plugin.wandPos2.remove(uuid);
                            for (String message : plugin.language.get.getStringList("player.pvplevels.wand.set.clear")) {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else {
                        for (String message : plugin.language.get.getStringList("player.pvplevels.wand.set.same")) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                }
            } else {
                for (String message : plugin.language.get.getStringList("player.pvplevels.wand.set.permission")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
            e.setCancelled(true);
        }
    }
}