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

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        final LivingEntity livingEntity = e.getEntity();
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER && plugin.getFileUtils().config.getStringList("spawners").contains(livingEntity.getName().toLowerCase())) {
            plugin.spawners.add(livingEntity.getUniqueId().toString());
        }
    }
}