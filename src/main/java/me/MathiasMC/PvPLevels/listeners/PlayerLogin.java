package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
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
        String uuid = e.getPlayer().getUniqueId().toString();
        String name = e.getPlayer().getName();
        plugin.database.insert(uuid, name);
        if (!plugin.list().contains(uuid)) {
            plugin.load(uuid);
        }
        PlayerConnect playerConnect = plugin.get(uuid);
        if (!playerConnect.name().equalsIgnoreCase(name)) {
            playerConnect.name(name);
        }
    }
}