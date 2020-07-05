package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {

    private final PvPLevels plugin;

    public EntityDeath(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntity(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        if (entity.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            return;
        }
        plugin.entityManager.getXP(entity, entity.getKiller());
    }
}