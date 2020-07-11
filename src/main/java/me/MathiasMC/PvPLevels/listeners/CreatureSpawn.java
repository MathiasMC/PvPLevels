package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawn implements Listener {

    private final PvPLevels plugin;

    public CreatureSpawn(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        LivingEntity livingEntity = e.getEntity();
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER && plugin.config.get.contains("spawners.xp") && plugin.config.get.getStringList("spawners.xp").contains(plugin.entityManager.getEntityName(livingEntity))) {
            plugin.spawners.add(livingEntity.getUniqueId().toString());
        }
    }
}