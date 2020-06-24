package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {

    private final PvPLevels plugin;

    public EntityDeath(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntity(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        if (entity instanceof Player) {
            Player killed = (Player) entity;
            if (!plugin.config.get.getStringList("levelup.all-excluded").contains(killed.getUniqueId().toString())) {
                PlayerConnect playerConnect = plugin.get(killed.getUniqueId().toString());
                if (plugin.systemManager.world(killed, plugin.config.get, "deaths")) {
                    if (plugin.config.get.getBoolean("events.Deaths.only-players")) {
                        if (entity.getKiller() != null) {
                            death(killed, playerConnect);
                        }
                    } else {
                        death(killed, playerConnect);
                    }
                }
                String entityKiller = plugin.entityManager.getEntityKiller(killed);
                plugin.xpManager.check(playerConnect, entityKiller, entity.getName(), killed, false);
                if (plugin.config.get.getBoolean("events.KillStreaks")) {
                    if (entityKiller.equalsIgnoreCase("player")) {
                        entityKiller = e.getEntity().getKiller().getName();
                    } else {
                        entityKiller = "Death";
                    }
                    plugin.statsManager.clearKillStreak(playerConnect, killed, entityKiller);
                }
            }
        }
        String entityUUID = entity.getUniqueId().toString();
        if (entity.getKiller() != null && !plugin.config.get.getStringList("levelup.all-excluded").contains(e.getEntity().getKiller().getUniqueId().toString())) {
            Player killer = entity.getKiller();
            String entityName = plugin.entityManager.getEntityName(entity);
            PlayerConnect playerConnect = plugin.get(killer.getUniqueId().toString());
            if (entityName != null && !plugin.killSessionUtils.check(entity, killer) && !plugin.spawners.contains(entityUUID)) {
                if (!plugin.xpManager.isMaxLevel(killer, playerConnect)) {
                    plugin.xpManager.check(playerConnect, entityName, entity.getName(), killer, true);
                }
                if (entity instanceof Player) {
                    if (plugin.systemManager.world(killer, plugin.config.get, "kills")) {
                        playerConnect.kills(playerConnect.kills() + 1L);
                        if (plugin.config.get.getBoolean("events.Kills")) {
                            plugin.systemManager.executeCommands(killer, plugin.config.get, "kills", "commands", 0L);
                        }
                    }
                    if (plugin.config.get.getBoolean("events.KillStreaks")) {
                        playerConnect.killstreak(playerConnect.killstreak() + 1L);
                        plugin.systemManager.executeCommands(killer, plugin.config.get, "killstreaks", "commands", playerConnect.killstreak());
                    }
                    if (plugin.config.get.getBoolean("events.PlayerRewards")) {
                        plugin.systemManager.executeCommands(killer, plugin.config.get, "rewards", "commands", playerConnect.kills());
                    }
                }
            }
        }
        plugin.spawners.remove(entityUUID);
    }

    private void death(Player killed, PlayerConnect playerConnect) {
        playerConnect.deaths(playerConnect.deaths() + 1L);
        if (plugin.config.get.getBoolean("events.Deaths.use")) { plugin.systemManager.executeCommands(killed, plugin.config.get, "deaths", "commands", 0L); }
    }
}
