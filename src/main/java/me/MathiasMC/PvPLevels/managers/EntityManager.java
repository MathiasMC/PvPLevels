package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Set;

public class EntityManager {

    private final PvPLevels plugin;

    public EntityManager(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    public String getEntityName(Entity entity) {
        String entityType = entity.getType().toString().toLowerCase();
        String entityTypeCustom = null;
        if (entity.getCustomName() != null)
            entityTypeCustom = ChatColor.stripColor(entity.getCustomName().toLowerCase());
        Set<String> set = plugin.config.get.getConfigurationSection("xp").getKeys(false);
        if (set.contains(entityType) || set.contains(entityTypeCustom)) {
            String entityTypeFixed = entityType;
            if (entityTypeCustom != null)
                entityTypeFixed = entityTypeCustom;
            return entityTypeFixed;
        }
        return null;
    }

    public String getEntityKiller(Player killed) {
        EntityDamageEvent entityDamageEvent = killed.getLastDamageCause();
        String entityType = "";
        if (!plugin.config.get.getConfigurationSection("xp").getKeys(false).contains("all")) {
            if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
                Entity entity = ((EntityDamageByEntityEvent)entityDamageEvent).getDamager();
                entityType = entity.getType().toString().toLowerCase();
                if (entity.getCustomName() != null)
                    entityType = ChatColor.stripColor(entity.getCustomName().toLowerCase());
            } else {
                entityType = entityDamageEvent.getCause().toString().toLowerCase();
            }
        } else {
            entityType = "all";
        }
        return entityType;
    }
}
