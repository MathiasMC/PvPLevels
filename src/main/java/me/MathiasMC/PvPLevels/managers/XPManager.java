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

    public void entityCheck(final Player killer, final Entity entity) {
        final String entityUUID = entity.getUniqueId().toString();
        boolean entityPlayer = false;
        if (entity instanceof Player) {
            entityPlayer = true;
            final Player player = (Player) entity;
            final PlayerConnect playerConnect = plugin.getPlayerConnect(entityUUID);
            if (player.hasPermission("pvplevels.group." + playerConnect.getGroup())) {
                if (!plugin.getFileUtils().config.getStringList("excluded").contains(entityUUID)) {
                    loseXP(player, killer, playerConnect);
                    getDeath(player, killer, playerConnect);
                } else if (plugin.isDebug()) { plugin.getTextUtils().debug("[XP] " + killer.getName() + " cannot get stats currently, is in the excluded list."); }
            } else if (plugin.isDebug()) { plugin.getTextUtils().debug("[XP] " + player.getName() + " does not have access to get xp " + "pvplevels.group." + playerConnect.getGroup()); }
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
        if (!isWorld(player, "killstreak." + playerConnect.getGroup())) {
            return;
        }
        final PlayerKillStreakEvent playerKillStreakEvent = new PlayerKillStreakEvent(player, killed, playerConnect, playerConnect.getKillstreak() + 1);
        playerKillStreakEvent.setCommands(playerKillStreakEvent.getDefaultCommands());
        plugin.getServer().getPluginManager().callEvent(playerKillStreakEvent);
        if (playerKillStreakEvent.isCancelled()) {
            return;
        }
        playerKillStreakEvent.execute();
    }

    public void getDeath(final Player player, final Entity entity, final PlayerConnect playerConnect) {
        if (!isWorld(player, "deaths." + playerConnect.getGroup())) {
            return;
        }
        if (plugin.getFileUtils().config.getBoolean("deaths." + playerConnect.getGroup() + ".only-player")) {
            if (entity == null) {
                return;
            }
        }
        final PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent(player, entity, playerConnect, playerConnect.getDeaths() + 1);
        playerDeathEvent.setCommands(playerDeathEvent.getDefaultCommands());
        plugin.getServer().getPluginManager().callEvent(playerDeathEvent);
        if (playerDeathEvent.isCancelled()) {
            return;
        }
        playerDeathEvent.execute();
    }

    public void getKill(final Player player, final Player killed, final PlayerConnect playerConnect) {
        if (!isWorld(player, "kills." + playerConnect.getGroup())) {
            return;
        }
        final PlayerKillEvent playerKillEvent = new PlayerKillEvent(player, killed, playerConnect, playerConnect.getKills() + 1);
        playerKillEvent.setCommands(playerKillEvent.getDefaultCommands());
        plugin.getServer().getPluginManager().callEvent(playerKillEvent);
        if (playerKillEvent.isCancelled()) {
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
        if (!isWorld(player, path)) {
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
        final PlayerGetXPEvent playerGetXPEvent = new PlayerGetXPEvent(player, entity, playerConnect, playerConnect.getXp() + xp);
        playerGetXPEvent.setKey(key);
        playerConnect.setXpItem(item);
        playerConnect.setXpType(entityType);
        playerConnect.setXpLast(xp);
        playerGetXPEvent.setCommands(playerGetXPEvent.getDefaultCommands());
        plugin.getServer().getPluginManager().callEvent(playerGetXPEvent);
        if (playerGetXPEvent.isCancelled()) {
            return;
        }
        playerGetXPEvent.execute();
    }

    public void loseXP(final Player player, final Entity killer, final PlayerConnect playerConnect) {
        final String path = "xp." + playerConnect.getGroup() + "." + getSourceType(player);
        if (!plugin.getFileUtils().config.contains(path + ".xp-lose")) {
            return;
        }
        if (!isWorld(player, path + ".xp-lose")) {
            return;
        }
        final long xp = plugin.getCalculateManager().randomNumber(plugin.getFileUtils().config.getLong(path + ".xp-lose.min"), plugin.getFileUtils().config.getLong(path + ".xp-lose.max"));
        if (xp < plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + plugin.getStartLevel() + ".xp")) {
            return;
        }
        final PlayerLostXPEvent playerLostXPEvent = new PlayerLostXPEvent(player, killer, playerConnect, playerConnect.getXp() - xp);
        playerConnect.setXpLost(xp);
        playerLostXPEvent.setCommands(playerLostXPEvent.getDefaultCommands());
        plugin.getServer().getPluginManager().callEvent(playerLostXPEvent);
        if (playerLostXPEvent.isCancelled()) {
            return;
        }
        playerLostXPEvent.execute();
    }

    public boolean loseLevel(final Player player, final Entity entity, final PlayerConnect playerConnect) {
        if (playerConnect.getLevel() - 1 >= plugin.getStartLevel() && playerConnect.getXp() < plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".xp")) {
            final PlayerLevelDownEvent playerLevelDownEvent = new PlayerLevelDownEvent(player, entity, playerConnect, playerConnect.getLevel() - 1);
            playerLevelDownEvent.setCommands(playerLevelDownEvent.getDefaultCommands());
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
            playerLevelUPEvent.setCommands(playerLevelUPEvent.getDefaultCommands());
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

    public boolean isWorld(final Player player, final String key) {
        final FileConfiguration config = plugin.getFileUtils().config;
        if (config.contains(key + ".worlds")) {
            final boolean world = config.getStringList(key + ".worlds").contains(player.getWorld().getName());
            if (plugin.isDebug() && !world) { plugin.getTextUtils().debug("[XP] " + player.getName() + " world " + player.getWorld().getName() + " is not in " + key + ".worlds"); }
            return world;
        }
        return true;
    }

    public void sendCommands(final Player player, final List<String> list) {
        if (list == null) return;
        for (String command : list) {
            plugin.getServer().dispatchCommand(plugin.consoleSender, plugin.getPlaceholderManager().replacePlaceholders(player, false, command));
        }
    }

    public String getSource(final Player player) {
        String source = "VOID";
        final EntityDamageEvent entityDamageEvent = player.getLastDamageCause();
        if (entityDamageEvent != null) {
            source = entityDamageEvent.getCause().toString();
        }
        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            final Entity entity = ((EntityDamageByEntityEvent) entityDamageEvent).getDamager();
            final String type = getShooter(entity);
            if (type != null) return type;
            final String entityType = entity.getType().toString().toUpperCase().replace(" ", "_");
            if (plugin.getFileUtils().language.contains("translate.entity." + entityType)) {
                return plugin.getFileUtils().language.getString("translate.entity." + entityType);
            }
        }
        if (plugin.getFileUtils().language.contains("translate.cause." + source)) {
            return plugin.getFileUtils().language.getString("translate.cause." + source);
        }
        return source;
    }

    public String getShooter(final Entity entity) {
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
        } else if (entity instanceof Player) {
            return entity.getName();
        }
        return null;
    }

    private String getSourceType(final Player player) {
        final EntityDamageEvent entityDamageEvent = player.getLastDamageCause();
        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            Entity entity = ((EntityDamageByEntityEvent) entityDamageEvent).getDamager();
            if (getShooter(entity) != null) {
                return "player";
            } else {
                return entity.getType().toString().toLowerCase();
            }
        } else if (entityDamageEvent != null) {
            return entityDamageEvent.getCause().toString().toLowerCase();
        }
        if (plugin.getFileUtils().config.contains("xp." + plugin.getPlayerConnect(player.getUniqueId().toString()).getGroup() + ".all")) {
            return "all";
        }
        return "void";
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
