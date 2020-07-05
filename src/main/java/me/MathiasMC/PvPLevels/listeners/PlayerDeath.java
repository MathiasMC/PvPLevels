package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public class PlayerDeath implements Listener {

    private final PvPLevels plugin;

    public PlayerDeath(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        String uuid = player.getUniqueId().toString();
        if (player.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.VOID) && plugin.lastDamagers.containsKey(uuid)) {
            Player target = plugin.getServer().getPlayer(UUID.fromString(plugin.lastDamagers.get(uuid)));
            if (target != null && target.isOnline()) {
                plugin.entityManager.getXP(e.getEntity(), target);
            }
        }
        plugin.lastDamagers.remove(uuid);
    }
}