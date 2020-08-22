package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    private final PvPLevels plugin;

    public BlockPlace(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace(BlockPlaceEvent e) {
        final String uuid = e.getPlayer().getUniqueId().toString();
        if (plugin.list().contains(uuid)) {
            if (plugin.config.get.contains("xp." + plugin.get(uuid).getGroup() + "." + e.getBlock().getType().name().toLowerCase())) {
                plugin.blocksList.add(e.getBlock().getLocation());
            }
        }
    }
}