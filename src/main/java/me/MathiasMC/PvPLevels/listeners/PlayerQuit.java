package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private final PvPLevels plugin;

    public PlayerQuit(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        String uuid = player.getUniqueId().toString();
        if (plugin.list().contains(uuid)) {
            plugin.get(uuid).setTime();
            if (plugin.config.get.getBoolean("events.KillStreaks") && plugin.config.get.getBoolean("unload-players.killstreak")) { plugin.statsManager.clearKillStreak(plugin.get(uuid), player, "Quit"); }
            if (plugin.config.get.getBoolean("unload-players.quit")) {
                plugin.unload(uuid);
            } else {
                plugin.get(uuid).save();
            }
        }
        plugin.guiPageID.remove(uuid);
        plugin.guiPageSort.remove(uuid);
        plugin.guiPageSortReverse.remove(uuid);
        plugin.lastDamagers.remove(uuid);
    }
}