package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    private final PvPLevels plugin;

    public PlayerMove(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        final Player player = e.getPlayer();
        if (player.getLocation().getY() <= plugin.deathY && !player.isDead()) {
            player.setLastDamageCause(null);
            player.setHealth(0D);
            if (plugin.isRespawn) {
                player.spigot().respawn();
            }
        }
    }
}