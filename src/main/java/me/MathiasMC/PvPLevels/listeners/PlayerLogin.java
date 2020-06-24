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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLogin(PlayerLoginEvent e) {
        String uuid = e.getPlayer().getUniqueId().toString();
        plugin.database.insert(uuid);
        if (!plugin.list().contains(uuid)) {
            plugin.load(uuid);
        }
    }
}