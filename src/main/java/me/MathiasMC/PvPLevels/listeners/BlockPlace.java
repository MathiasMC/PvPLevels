package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    private final PvPLevels plugin;

    public BlockPlace(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (plugin.config.get.contains("xp." + e.getBlock().getType().name().toLowerCase())) {
            plugin.blocksList.add(e.getBlock().getLocation());
        }
    }
}
