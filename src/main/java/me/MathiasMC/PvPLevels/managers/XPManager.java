package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.api.events.*;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

    public XPManager(PvPLevels plugin) {
        this.plugin = plugin;
    }

    public void entityCheck(Player killer, Entity entity) {
        String entityUUID = entity.getUniqueId().toString();
        boolean entityPlayer = false;
        if (entity instanceof Player) {
            entityPlayer = true;
            Player player = (Player) entity;
            PlayerConnect playerConnect = plugin.getPlayerConnect(entityUUID);
            if (!plugin.getFileUtils().config.getStringList("excluded").contains(entityUUID)) {
                loseXP(player, killer, playerConnect);
                getDeath(player, killer, playerConnect);
            }
        }
        if (killer == null) {
            return;
        }
        String killerUUID = killer.getUniqueId().toString();
        if (plugin.getFileUtils().config.getStringList("excluded").contains(killerUUID)) return;
        PlayerConnect playerConnect = plugin.getPlayerConnect(killerUUID);
        if (entityPlayer) {
            playerConnect.setXpType(entity.getName());
            if (plugin.getSessionManager().hasSession(killer, (Player) entity)) return;
        }
        getXP(killer, entity, null);
        if (entityPlayer) {
            Player killed = (Player) entity;
            getKill(killer, killed, playerConnect);
            getKillStreak(killer, killed, playerConnect);
        }
    }

    public void getKillStreak(Player player, Player killed, PlayerConnect playerConnect) {
        PlayerKillStreakEvent playerKillStreakEvent = new PlayerKillStreakEvent(player, killed, playerConnect, playerConnect.getKillstreak() + 1);
        playerKillStreakEvent.setCommands(playerKillStreakEvent.getDefaultCommands());
        plugin.getServer().getPluginManager().callEvent(playerKillStreakEvent);
        if (playerKillStreakEvent.isCancelled()) {
            return;
        }
        playerKillStreakEvent.execute();
    }

    public void getDeath(Player player, Entity entity, PlayerConnect playerConnect) {
        if (plugin.getFileUtils().config.getBoolean("deaths." + playerConnect.getGroup() + ".only-player")) {
            if (entity == null) {
                return;
            }
        }
        PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent(player, entity, playerConnect, playerConnect.getDeaths() + 1);
        playerDeathEvent.setCommands(playerDeathEvent.getDefaultCommands());
        plugin.getServer().getPluginManager().callEvent(playerDeathEvent);
        if (playerDeathEvent.isCancelled()) {
            return;
        }
        playerDeathEvent.execute();
    }

    public void getKill(Player player, Player killed, PlayerConnect playerConnect) {
        PlayerKillEvent playerKillEvent = new PlayerKillEvent(player, killed, playerConnect, playerConnect.getKills() + 1);
        playerKillEvent.setCommands(playerKillEvent.getDefaultCommands());
        plugin.getServer().getPluginManager().callEvent(playerKillEvent);
        if (playerKillEvent.isCancelled()) {
            return;
        }
        playerKillEvent.execute();
    }

    public void getXP(Player player, Entity entity, Material material) {
        String uuid = player.getUniqueId().toString();
        PlayerConnect playerConnect = plugin.getPlayerConnect(uuid);
        String group = playerConnect.getGroup();
        String entityType;
        String pathKey;
        if (entity != null) {
            String entityUUID = entity.getUniqueId().toString();
            if (plugin.spawners.contains(entityUUID)) return;
            if (uuid.equalsIgnoreCase(entityUUID)) return;
            pathKey = entity.getType().toString().toLowerCase();
            entityType = entity.getName();
            String get = entityType.toUpperCase().replace(" ", "_");
            if (plugin.getFileUtils().language.contains("translate.entity." + get)) {
                entityType = plugin.getFileUtils().language.getString("translate.entity." + get);
            }
        } else {
            String name = material.name();
            pathKey = name.toLowerCase();
            entityType = name;
            if (plugin.getFileUtils().language.contains("translate.blocks." + name)) {
                entityType = plugin.getFileUtils().language.getString("translate.blocks." + name);
            }
        }
        String path = "xp." + group + "." + pathKey;
        if (!plugin.getFileUtils().config.contains(path)) return;
        long xp = Utils.randomNumber(
                plugin.getFileUtils().config.getLong(path + "." + player.getWorld().getName() + ".min",
                        plugin.getFileUtils().config.getLong(path + ".min", 0)),
                plugin.getFileUtils().config.getLong(path + "." + player.getWorld().getName() + ".max",
                        plugin.getFileUtils().config.getLong(path + ".max", 0)));
        if (entity != null) {
            String customName = entity.getCustomName();
            if (customName != null) {
                String coloredName = Utils.replacePlaceholders(player, false, ChatColor.stripColor(customName));
                if (plugin.getFileUtils().config.contains(path + ".name." + coloredName)) {
                    xp = Utils.randomNumber(plugin.getFileUtils().config.getLong(path + ".name." + coloredName + ".min"), plugin.getFileUtils().config.getLong(path + ".name." + coloredName + ".max"));
                }
            }
        }
        long item = hasItem(player, path);
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
        PlayerGetXPEvent playerGetXPEvent = new PlayerGetXPEvent(player, entity, playerConnect, playerConnect.getXp() + xp);
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

    public void loseXP(Player player, Entity killer, PlayerConnect playerConnect) {
        String path = "xp." + playerConnect.getGroup() + "." + getSourceType(player);
        if (!plugin.getFileUtils().config.contains(path + ".xp-lose")) {
            return;
        }
        long xp = Utils.randomNumber(
                plugin.getFileUtils().config.getLong(path + ".xp-lose." + player.getWorld().getName() + ".min",
                        plugin.getFileUtils().config.getLong(path + ".xp-lose.min", 0)),
                plugin.getFileUtils().config.getLong(path + ".xp-lose." + player.getWorld().getName() + ".max",
                        plugin.getFileUtils().config.getLong(path + ".xp-lose.max", 0)));
        if (xp < plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + plugin.getStartLevel() + ".xp")) {
            return;
        }
        PlayerLostXPEvent playerLostXPEvent = new PlayerLostXPEvent(player, killer, playerConnect, playerConnect.getXp() - xp);
        playerConnect.setXpLost(xp);
        playerLostXPEvent.setCommands(playerLostXPEvent.getDefaultCommands());
        plugin.getServer().getPluginManager().callEvent(playerLostXPEvent);
        if (playerLostXPEvent.isCancelled()) {
            return;
        }
        playerLostXPEvent.execute();
    }

    public boolean loseLevel(Player player, Entity entity, PlayerConnect playerConnect) {
        if (playerConnect.getLevel() - 1 >= plugin.getStartLevel() && playerConnect.getXp() < plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".xp")) {
            PlayerLevelDownEvent playerLevelDownEvent = new PlayerLevelDownEvent(player, entity, playerConnect, playerConnect.getLevel() - 1);
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

    public boolean getLevel(Player player, Entity entity, PlayerConnect playerConnect) {
        if (isMaxLevel(playerConnect)) {
            return false;
        }
        long nextLevel = playerConnect.getLevel() + 1;
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

    public boolean isMaxLevel(PlayerConnect playerConnect) {
        return !plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + (playerConnect.getLevel() + 1));
    }

    public void sendCommands(Player player, List<String> list) {
        if (list == null) return;
        for (String command : list) {
            plugin.getServer().dispatchCommand(plugin.consoleSender, Utils.replacePlaceholders(player, false, command));
        }
    }

    public String getSource(Player player) {
        String source = "VOID";
        EntityDamageEvent entityDamageEvent = player.getLastDamageCause();
        if (entityDamageEvent != null) {
            source = entityDamageEvent.getCause().toString();
        }
        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            Entity entity = ((EntityDamageByEntityEvent) entityDamageEvent).getDamager();
            String type = getShooter(entity);
            if (type != null) return type;
            String entityType = entity.getType().toString().toUpperCase().replace(" ", "_");
            if (plugin.getFileUtils().language.contains("translate.entity." + entityType)) {
                return plugin.getFileUtils().language.getString("translate.entity." + entityType);
            }
        }
        if (plugin.getFileUtils().language.contains("translate.cause." + source)) {
            return plugin.getFileUtils().language.getString("translate.cause." + source);
        }
        return source;
    }

    public String getShooter(Entity entity) {
        ProjectileSource projectileSource = null;
        if (entity instanceof Arrow) {
            Arrow arrow = (Arrow) entity;
            projectileSource = arrow.getShooter();
        } else if (entity instanceof Snowball) {
            Snowball snowball = (Snowball) entity;
            projectileSource = snowball.getShooter();
        } else if (entity instanceof Egg) {
            Egg egg = (Egg) entity;
            projectileSource = egg.getShooter();
        }
        if (projectileSource instanceof Player) {
            return ((Player) projectileSource).getName();
        } else if (entity instanceof Player) {
            return entity.getName();
        }
        return null;
    }

    private String getSourceType(Player player) {
        EntityDamageEvent entityDamageEvent = player.getLastDamageCause();
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

    public long hasItem(Player player, String path) {
        if (!plugin.getFileUtils().config.contains(path + ".items")) {
            return 0;
        }
        PlayerInventory inventory = player.getInventory();
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
            String itemStackName = hand.getType().name();
            for (String next : plugin.getFileUtils().config.getConfigurationSection(path + ".items").getKeys(false)) {
                if (next.equalsIgnoreCase(itemStackName.toLowerCase())) {
                    if (checkItem(player, hand, path, next)) {
                        return Utils.randomNumber(plugin.getFileUtils().config.getLong(path + ".items." + next + ".min"), plugin.getFileUtils().config.getLong(path + ".items." + next + ".max"));
                    }
                    break;
                }
            }
        }
        if (offHand != null) {
            String itemStackName = offHand.getType().name();
            for (String next : plugin.getFileUtils().config.getConfigurationSection(path + ".items").getKeys(false)) {
                if (next.equalsIgnoreCase(itemStackName.toLowerCase())) {
                    if (checkItem(player, offHand, path, next)) {
                        return Utils.randomNumber(plugin.getFileUtils().config.getLong(path + ".items." + next + ".min"), plugin.getFileUtils().config.getLong(path + ".items." + next + ".max"));
                    }
                    break;
                }
            }
        }
        return 0;
    }

    private boolean checkItem(Player player, ItemStack itemStack, String path, String next) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        boolean check = true;
        String displayName = Utils.replacePlaceholders(player, false, ChatColor.translateAlternateColorCodes('&', plugin.getFileUtils().config.getString(path + ".items." + next + ".name")));
        if (itemMeta.hasDisplayName()) {
            if (!itemMeta.getDisplayName().equalsIgnoreCase(displayName)) {
                check = false;
            }
        } else if (!displayName.isEmpty()) {
            check = false;
        }
        List<String> lores = plugin.getFileUtils().config.getStringList(path + ".items." + next + ".lores");
        if (itemMeta.hasLore()) {
            List<String> list = new ArrayList<>();
            for (String lore : lores) {
                list.add(Utils.replacePlaceholders(player, false, ChatColor.translateAlternateColorCodes('&', lore)));
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
