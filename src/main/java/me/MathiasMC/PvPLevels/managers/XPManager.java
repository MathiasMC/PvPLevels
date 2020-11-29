package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.api.events.*;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.projectiles.ProjectileSource;

import java.util.ArrayList;
import java.util.List;

public class XPManager {

    private final PvPLevels plugin;

    public XPManager(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    public void entityCheck(final Entity entity, final Player killer) {
        final String entityUUID = entity.getUniqueId().toString();
        boolean entityPlayer = false;
        if (entity instanceof Player) {
            entityPlayer = true;
            final Player player = (Player) entity;
            final PlayerConnect playerConnect = plugin.getPlayerConnect(entityUUID);
            if (!player.hasPermission("pvplevels.group." + playerConnect.getGroup())) {
                if (plugin.isDebug()) { plugin.getTextUtils().debug("[XP] " + player.getName() + " does not have access to get xp " + "pvplevels.group." + playerConnect.getGroup()); }
                return;
            }
            if (plugin.getFileUtils().config.getStringList("excluded").contains(entityUUID)) {
                if (plugin.isDebug()) { plugin.getTextUtils().debug("[XP] " + killer.getName() + " cannot get stats currently, is in the excluded list."); }
                return;
            }
            loseXP(player, killer, playerConnect);
            getDeath(player, killer, playerConnect);
        }
        if (killer == null) {
            return;
        }
        final String killerUUID = killer.getUniqueId().toString();
        final PlayerConnect playerConnect = plugin.getPlayerConnect(killerUUID);
        if (!killer.hasPermission("pvplevels.group." + playerConnect.getGroup())) {
            if (plugin.isDebug()) { plugin.getTextUtils().debug("[XP] " + killer.getName() + " does not have access to get xp " + "pvplevels.group." + playerConnect.getGroup()); }
            return;
        }
        if (plugin.getFileUtils().config.getStringList("excluded").contains(killerUUID)) {
            if (plugin.isDebug()) { plugin.getTextUtils().debug("[XP] " + killer.getName() + " cannot get stats currently, is in the excluded list."); }
            return;
        }

        if (entityPlayer) {
            playerConnect.setXpType(entity.getName());
            if (plugin.getKillSessionManager().hasSession(killer, (Player) entity)) {
                if (plugin.isDebug()) {
                    plugin.getTextUtils().debug("[XP] " + killer.getName() + " are still in the kill session.");
                }
                return;
            }
        }

        getXP(killer, entity, null);
        if (entityPlayer) {
            final Player killed = (Player) entity;
            getKill(killer, killed, playerConnect);
            getKillStreak(killer, killed, playerConnect);
        }
    }

    public void getKillStreak(final Player player, final Player killed, final PlayerConnect playerConnect) {
        final PlayerKillStreakEvent playerKillStreakEvent = new PlayerKillStreakEvent(player, killed, playerConnect);
        plugin.getServer().getPluginManager().callEvent(playerKillStreakEvent);
        if (playerKillStreakEvent.isCancelled()) {
            return;
        }
        if (!isInWorld(player, playerConnect, "killstreak")) {
            return;
        }
        playerKillStreakEvent.execute();
    }

    public void getDeath(final Player player, final Entity entity, final PlayerConnect playerConnect) {
        if (plugin.getFileUtils().config.getBoolean("deaths." + playerConnect.getGroup() + ".only-player")) {
            if (entity == null) {
                return;
            }
        }
        final PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent(player, entity, playerConnect);
        plugin.getServer().getPluginManager().callEvent(playerDeathEvent);
        if (playerDeathEvent.isCancelled()) {
            return;
        }
        if (!isInWorld(player, playerConnect, "deaths")) {
            return;
        }
        playerDeathEvent.execute();
    }

    public void getKill(final Player player, final Player killed, final PlayerConnect playerConnect) {
        final PlayerKillEvent playerKillEvent = new PlayerKillEvent(player, killed, playerConnect);
        plugin.getServer().getPluginManager().callEvent(playerKillEvent);
        if (playerKillEvent.isCancelled()) {
            return;
        }
        if (!isInWorld(player, playerConnect, "kills")) {
            return;
        }
        playerKillEvent.execute();
    }

    public void getXP(final Player player, final Entity entity, final Material material) {
        final String uuid = player.getUniqueId().toString();
        final PlayerConnect playerConnect = plugin.getPlayerConnect(uuid);
        final String group = playerConnect.getGroup();
        String entityType;
        String pathKey;
        if (entity != null) {
            final String entityUUID = entity.getUniqueId().toString();
            if (plugin.spawners.contains(entityUUID)) {
                if (plugin.isDebug()) { plugin.getTextUtils().debug("[XP] " + player.getName() + " could not get xp from " + entity.getName()); }
                return;
            }
            if (uuid.equalsIgnoreCase(entityUUID)) {
                if (plugin.isDebug()) { plugin.getTextUtils().debug("[XP] " + uuid + " is the same as " + entityUUID); }
                return;
            }
            pathKey = entity.getType().toString().toLowerCase();
            entityType = entity.getName();
            final String get = entityType.toUpperCase().replace(" ", "_");
            if (plugin.getFileUtils().language.contains("translate.entity." + get)) {
                entityType = plugin.getFileUtils().language.getString("translate.entity." + get);
            }
        } else {
            final String name = material.name();
            pathKey = name.toLowerCase();
            entityType = name;
            if (plugin.getFileUtils().language.contains("translate.blocks." + name)) {
                entityType = plugin.getFileUtils().language.getString("translate.blocks." + name);
            }
        }
        final String path = "xp." + group + "." + pathKey;
        if (!plugin.getFileUtils().config.contains(path)) {
            if (plugin.isDebug()) { plugin.getTextUtils().debug("[XP] config.yml path " + path + " is not found."); }
            return;
        }
        if (!isInWorld(player, playerConnect, path)) {
            return;
        }
        long xp = plugin.getCalculateManager().randomNumber(plugin.getFileUtils().config.getLong(path + ".min"), plugin.getFileUtils().config.getLong(path + ".max"));
        if (entity != null) {
            final String customName = entity.getCustomName();
            if (customName != null) {
                String coloredName = plugin.getPlaceholderManager().replacePlaceholders(player, false, ChatColor.stripColor(customName));
                if (plugin.getFileUtils().config.contains(path + ".name." + coloredName)) {
                    xp = plugin.getCalculateManager().randomNumber(plugin.getFileUtils().config.getLong(path + ".name." + coloredName + ".min"), plugin.getFileUtils().config.getLong(path + ".name." + coloredName + ".max"));
                }
            }
        }
        final long item = hasItem(player, path);
        xp = xp + item;
        String key = "get";
        if (item != 0) {
            key = "item";
        }
        double multiplier = playerConnect.getMultiplier();
        if (multiplier != 0D) {
            if (key.equalsIgnoreCase("item")) {
                key = "both";
            } else {
                key = "boost";
            }
            xp = (int) (xp * multiplier);
        }
        final PlayerXPEvent playerXPEvent = new PlayerXPEvent(player, entity, playerConnect, xp, item, multiplier, entityType, key);
        plugin.getServer().getPluginManager().callEvent(playerXPEvent);
        if (playerXPEvent.isCancelled()) {
            return;
        }
        playerXPEvent.execute();
    }

    public void loseXP(final Player player, final Entity killer, final PlayerConnect playerConnect) {
        final String group = playerConnect.getGroup();
        final String path = "xp." + group + "." + getEntityKiller(player, group);
        if (!plugin.getFileUtils().config.contains(path + ".xp-lose")) {
            return;
        }
        final long xp = plugin.getCalculateManager().randomNumber(plugin.getFileUtils().config.getLong(path + ".xp-lose.min"), plugin.getFileUtils().config.getLong(path + ".xp-lose.max"));
        if (xp < plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + plugin.getStartLevel() + ".xp")) {
            return;
        }
        final PlayerLoseXPEvent playerLoseXPEvent = new PlayerLoseXPEvent(player, killer, playerConnect, xp);
        if (playerLoseXPEvent.isCancelled()) {
            return;
        }
        playerLoseXPEvent.execute();
    }

    public boolean loseLevel(final Player player, final Entity entity, final PlayerConnect playerConnect) {
        if (playerConnect.getLevel() - 1 >= plugin.getStartLevel() && playerConnect.getXp() < plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".xp")) {
            final PlayerLevelDownEvent playerLevelDownEvent = new PlayerLevelDownEvent(player, entity, playerConnect, playerConnect.getLevel() - 1);
            plugin.getServer().getPluginManager().callEvent(playerLevelDownEvent);
            if (playerLevelDownEvent.isCancelled()) {
                return false;
            }
            playerLevelDownEvent.execute();
            return true;
        }
        return false;
    }

    public boolean getLevel(final Player player, final Entity entity, final PlayerConnect playerConnect) {
        if (isMaxLevel(playerConnect)) {
            return false;
        }
        final long nextLevel = playerConnect.getLevel() + 1;
        if (playerConnect.getXp() >= plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + nextLevel + ".xp")) {
            PlayerLevelUPEvent playerLevelUPEvent = new PlayerLevelUPEvent(player, entity, playerConnect, nextLevel);
            plugin.getServer().getPluginManager().callEvent(playerLevelUPEvent);
            if (playerLevelUPEvent.isCancelled()) {
                return false;
            }
            playerLevelUPEvent.execute();
            return true;
        }
        return false;
    }

    public boolean isMaxLevel(final PlayerConnect playerConnect) {
        return !plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + (playerConnect.getLevel() + 1));
    }

    public boolean isInWorld(final Player player, final PlayerConnect playerConnect, final String key) {
        final FileConfiguration config = plugin.getFileUtils().config;
        final String group = playerConnect.getGroup();
        if (config.contains(key + "." + group + ".worlds")) {
            final List<String> worlds = config.getStringList(key + "." + group + ".worlds");
            if (worlds.contains(player.getWorld().getName())) {
                return true;
            }
            if (plugin.isDebug()) { plugin.getTextUtils().debug("[XP] " + player.getName() + " world " + player.getWorld().getName() + " is not in " + key + "." + group + ".worlds"); }
            return false;
        }
        return true;
    }

    public void sendCommands(final Player player, final List<String> list) {
        for (String command : list) {
            plugin.getServer().dispatchCommand(plugin.consoleSender, plugin.getPlaceholderManager().replacePlaceholders(player, false, command));
        }
    }

    public String getKillerName(final Player killed) {
        final EntityDamageEvent entityDamageEvent = killed.getLastDamageCause();
        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            final Entity entity = ((EntityDamageByEntityEvent) entityDamageEvent).getDamager();
            final String type = getType(entity);
            if (type != null) {
                return type;
            }
            if (entity instanceof Player) {
                return entity.getName();
            } else {
                String entityType = entity.getType().toString().toUpperCase().replace(" ", "_");
                if (plugin.getFileUtils().language.contains("translate.entity." + entityType)) {
                    entityType = plugin.getFileUtils().language.getString("translate.entity." + entityType);
                }
                return entityType;
            }
        } else {
            if (entityDamageEvent == null) {
                return null;
            }
            final String translate = entityDamageEvent.getCause().toString().toUpperCase().replace(" ", "_");
            if (plugin.getFileUtils().language.contains("translate.cause." + translate)) {
                return plugin.getFileUtils().language.getString("translate.cause." + translate);
            }
        }
        return null;
    }

    public String getType(final Entity entity) {
        ProjectileSource projectileSource = null;
        if (entity instanceof Arrow) {
            final Arrow arrow = (Arrow) entity;
            projectileSource = arrow.getShooter();
        } else if (entity instanceof Snowball) {
            final Snowball snowball = (Snowball) entity;
            projectileSource = snowball.getShooter();
        } else if (entity instanceof Egg) {
            final Egg egg = (Egg) entity;
            projectileSource = egg.getShooter();
        }
        if (projectileSource instanceof Player) {
            return ((Player) projectileSource).getName();
        }
        return null;
    }

    private String getEntityKiller(final Player killed, final String group) {
        final EntityDamageEvent entityDamageEvent = killed.getLastDamageCause();
        String entityType = null;
        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            Entity entity = ((EntityDamageByEntityEvent) entityDamageEvent).getDamager();
            entityType = entity.getType().toString().toLowerCase();
            final String type = getType(entity);
            if (type != null) {
                entityType = "player";
            }
        } else {
            if (entityDamageEvent != null) {
                entityType = entityDamageEvent.getCause().toString().toLowerCase();
            }
        }
        if (!plugin.getFileUtils().config.contains("xp." + group + "." + entityType)) {
            return "all";
        }
        return entityType;
    }

    public long hasItem(final Player player, final String path) {
        if (!plugin.getFileUtils().config.contains(path + ".items")) {
            return 0;
        }
        final PlayerInventory inventory = player.getInventory();
        ItemStack hand;
        ItemStack offHand;
        try {
            hand = inventory.getItemInMainHand();
            offHand = inventory.getItemInOffHand();
        } catch (NoSuchMethodError error) {
            hand = inventory.getItemInHand();
            offHand = hand.clone();
        }
        if (hand != null) {
            final String itemStackName = hand.getType().name();
            for (String next : plugin.getFileUtils().config.getConfigurationSection(path + ".items").getKeys(false)) {
                if (next.equalsIgnoreCase(itemStackName.toLowerCase())) {
                    if (checkItem(player, hand, path, next)) {
                        return plugin.getCalculateManager().randomNumber(plugin.getFileUtils().config.getLong(path + ".items." + next + ".min"), plugin.getFileUtils().config.getLong(path + ".items." + next + ".max"));
                    }
                    break;
                }
            }
        }
        if (offHand != null) {
            final String itemStackName = offHand.getType().name();
            for (String next : plugin.getFileUtils().config.getConfigurationSection(path + ".items").getKeys(false)) {
                if (next.equalsIgnoreCase(itemStackName.toLowerCase())) {
                    if (checkItem(player, offHand, path, next)) {
                        return plugin.getCalculateManager().randomNumber(plugin.getFileUtils().config.getLong(path + ".items." + next + ".min"), plugin.getFileUtils().config.getLong(path + ".items." + next + ".max"));
                    }
                    break;
                }
            }
        }
        return 0;
    }

    private boolean checkItem(final Player player, final ItemStack itemStack, final String path, final String next) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        boolean check = true;
        final String displayName = plugin.getPlaceholderManager().replacePlaceholders(player, false, ChatColor.translateAlternateColorCodes('&', plugin.getFileUtils().config.getString(path + ".items." + next + ".name")));
        if (itemMeta.hasDisplayName()) {
            if (!itemMeta.getDisplayName().equalsIgnoreCase(displayName)) {
                check = false;
            }
        } else if (!displayName.isEmpty()) {
            check = false;
        }
        final List<String> lores = plugin.getFileUtils().config.getStringList(path + ".items." + next + ".lores");
        if (itemMeta.hasLore()) {
            final List<String> list = new ArrayList<>();
            for (String lore : lores) {
                list.add(plugin.getPlaceholderManager().replacePlaceholders(player, false, ChatColor.translateAlternateColorCodes('&', lore)));
            }
            if (!itemMeta.getLore().equals(list)) {
                check = false;
            }
        } else if (!lores.isEmpty()) {
            check = false;
        }
        return check;
    }
}
