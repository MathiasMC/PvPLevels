package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
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
        final String uuid = e.getPlayer().getUniqueId().toString();
        if (!plugin.listPlayerConnect().contains(uuid)) {
            return;
        }
        final PlayerConnect playerConnect = plugin.getPlayerConnect(uuid);
        playerConnect.setTime();
        playerConnect.save();
    }
}