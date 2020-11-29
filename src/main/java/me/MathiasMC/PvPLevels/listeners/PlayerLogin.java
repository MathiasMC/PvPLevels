package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLogin implements Listener {

    private final PvPLevels plugin;

    public PlayerLogin(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onLogin(PlayerLoginEvent e) {
        final String uuid = e.getPlayer().getUniqueId().toString();
        plugin.database.insert(uuid);
        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            plugin.updatePlayerConnect(uuid);
            }, plugin.getConfig().getInt("mysql.update") * 20L);
    }
}