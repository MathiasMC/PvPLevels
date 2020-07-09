package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

public class EntityDeath implements Listener {

    private final PvPLevels plugin;

    public EntityDeath(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntity(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        if (entity == null) {
            return;
        }
        if (plugin.lastDamagers.containsKey(entity.getUniqueId().toString())) {
            Player target = plugin.getServer().getPlayer(UUID.fromString(plugin.lastDamagers.get(entity.getUniqueId().toString())));
            if (target != null && target.isOnline()) {
                plugin.entityManager.getXP(e.getEntity(), target);
            }
            plugin.lastDamagers.remove(entity.getUniqueId().toString());
            return;
        }
        plugin.entityManager.getXP(entity, entity.getKiller());
    }
}