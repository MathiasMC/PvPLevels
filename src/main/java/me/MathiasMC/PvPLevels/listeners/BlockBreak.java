package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    private final PvPLevels plugin;

    public BlockBreak(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent e) {
        final Location location = e.getBlock().getLocation();
        final Player player = e.getPlayer();
        final PlayerConnect playerConnect = plugin.getPlayerConnect(player.getUniqueId().toString());
        if (!plugin.getXPManager().isMaxLevel(playerConnect)) {
            if (!plugin.blocksList.contains(location)) {
                final Material material = e.getBlock().getType();
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (e.getBlock().getLocation().getBlock().getType().equals(Material.AIR)) {
                        plugin.getXPManager().getXP(player, null, material);
                    }
                }, 2L);
            } else {
                plugin.blocksList.remove(location);
            }
        }
    }
}
