package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    private final PvPLevels plugin;

    public BlockBreak(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        Location location = e.getBlock().getLocation();
        if (!plugin.blocksList.contains(location)) {
            final Material material = e.getBlock().getType();
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    if (e.getBlock().getLocation().getBlock().getType().equals(Material.AIR)) {
                        plugin.xpManager.check(plugin.get(e.getPlayer().getUniqueId().toString()), material.name().toLowerCase(), "", e.getPlayer(), true);
                    }
                }
            }, 2L);
        } else {
            plugin.blocksList.remove(location);
        }
    }
}