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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntity(EntityDeathEvent e) {
        final LivingEntity entity = e.getEntity();
        final String uuid = entity.getUniqueId().toString();
        if (plugin.lastDamagers.containsKey(uuid)) {
            final Player target = plugin.getServer().getPlayer(UUID.fromString(plugin.lastDamagers.get(uuid)));
            if (target != null && target.isOnline()) {
                plugin.getXPManager().entityCheck(e.getEntity(), target);
            }
            plugin.lastDamagers.remove(uuid);
            return;
        }
        plugin.getXPManager().entityCheck(entity, entity.getKiller());
    }
}