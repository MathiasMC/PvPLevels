package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
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

    public void getXP(final Entity entity, final Player killer) {
        if (entity instanceof Player) {
            Player killed = (Player) entity;
            String killedUUID = killed.getUniqueId().toString();
            if (!plugin.config.get.getStringList("levelup.all-excluded").contains(killedUUID)) {
                PlayerConnect playerConnect = plugin.get(killedUUID);
                if (plugin.systemManager.world(killed, plugin.config.get, "deaths")) {
                    if (plugin.config.get.getBoolean("events.Deaths.only-players")) {
                        death(killed, playerConnect);
                    } else {
                        death(killed, playerConnect);
                    }
                }
                String entityKiller = plugin.entityManager.getEntityKiller(killed);
                plugin.xpManager.check(playerConnect, entityKiller, entity.getName(), killed, false);
                if (entityKiller != null) {
                    if (plugin.config.get.getBoolean("events.KillStreaks")) {
                        if (entityKiller.equalsIgnoreCase("player")) {
                            if (killer != null) {
                                entityKiller = killer.getName();
                            }
                        } else {
                            entityKiller = "Death";
                        }
                        plugin.statsManager.clearKillStreak(playerConnect, killed, entityKiller);
                    }
                }
            }
        }
        String entityUUID = entity.getUniqueId().toString();
        if (killer != null && !plugin.config.get.getStringList("levelup.all-excluded").contains(killer.getUniqueId().toString())) {
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
                        long currentKillStreak = playerConnect.killstreak() + 1L;
                        playerConnect.killstreak(currentKillStreak);
                        if (currentKillStreak > playerConnect.killstreak_top()) {
                            playerConnect.killstreak_top(currentKillStreak);
                        }
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
        String entityType = null;
        if (!plugin.config.get.getConfigurationSection("xp").getKeys(false).contains("all")) {
            if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
                Entity entity = ((EntityDamageByEntityEvent)entityDamageEvent).getDamager();
                entityType = entity.getType().toString().toLowerCase();
                if (entity.getCustomName() != null)
                    entityType = ChatColor.stripColor(entity.getCustomName().toLowerCase());
            } else {
                if (entityDamageEvent != null) {
                    entityType = entityDamageEvent.getCause().toString().toLowerCase();
                }
            }
        } else {
            entityType = "all";
        }
        return entityType;
    }
}
