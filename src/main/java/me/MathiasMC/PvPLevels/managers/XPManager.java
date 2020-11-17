package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.api.Type;
import me.MathiasMC.PvPLevels.api.events.*;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class XPManager {

    private final PvPLevels plugin;

    public XPManager(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    public void entityCheck(final Entity entity, final Player killer) {
        if (entity instanceof Player) {
            final Player killed = (Player) entity;
            final String killedUUID = killed.getUniqueId().toString();
            if (!plugin.getFileUtils().config.getStringList("excluded").contains(killedUUID)) {
                final PlayerConnect playerConnect = plugin.getPlayerConnect(killedUUID);
                check(killed, entity.getCustomName(), getEntityKiller(killed, playerConnect.getGroup()), "", false, Type.ENTITY);
                PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent(killer, killed, playerConnect);
                if (playerDeathEvent.isCancelled()) {
                    return;
                }
                if (!playerDeathEvent.inWorld()) {
                    return;
                }
                playerDeathEvent.execute();
            }
        }
        if (killer != null) {
            final String killerUUID = killer.getUniqueId().toString();
            if (!plugin.getFileUtils().config.getStringList("excluded").contains(killerUUID)
                    && !plugin.getKillSessionManager().check(entity, killer)
                    && !plugin.spawners.contains(entity.getUniqueId().toString())
                    && !entity.getUniqueId().toString().equalsIgnoreCase(killerUUID)) {
                check(killer, entity.getCustomName(), entity.getType().toString().toLowerCase(), entity.getName(), true, Type.ENTITY);
                if (entity instanceof Player) {
                    final PlayerConnect playerConnect = plugin.getPlayerConnect(killerUUID);
                    if (world(killer, plugin.getFileUtils().config, "killstreak." + playerConnect.getGroup())) {
                        final long killStreak = playerConnect.getKillstreak() + 1;
                        playerConnect.setKillstreak(killStreak);
                        if (killStreak > playerConnect.getKillstreakTop()) {
                            playerConnect.setKillstreakTop(killStreak);
                            final String path = "killstreak." + playerConnect.getGroup() + "." + playerConnect.getKillstreak() + ".top";
                            if (plugin.getFileUtils().config.contains(path)) {
                                sendCommands(killer, path, plugin.getFileUtils().config, "", 0, 0, 0, 0);
                                return;
                            }
                        }
                        final String path = "killstreak." + playerConnect.getGroup() + "." + playerConnect.getKillstreak() + ".get";
                        if (plugin.getFileUtils().config.contains(path)) {
                            sendCommands(killer, path, plugin.getFileUtils().config, "", 0, 0, 0, 0);
                            return;
                        }
                        sendCommands(killer, "killstreak." + playerConnect.getGroup() + ".get", plugin.getFileUtils().config, "", 0, 0, 0, 0);
                    }
                    PlayerKillEvent playerKillEvent = new PlayerKillEvent(killer, (Player) entity, playerConnect, plugin.getPlayerConnect(entity.getUniqueId().toString()));
                    plugin.getServer().getPluginManager().callEvent(playerKillEvent);
                    if (playerKillEvent.isCancelled()) {
                        return;
                    }
                    if (!playerKillEvent.inWorld()) {
                        return;
                    }
                    playerKillEvent.execute();
                }
            }
        }
    }

    public void check(final Player player, final String customName, final String xpType, String entityType, final boolean xpUP, final Type type) {
        final String uuid = player.getUniqueId().toString();
        final PlayerConnect playerConnect = plugin.getPlayerConnect(uuid);
        final String group = playerConnect.getGroup();
        final String path = "xp." + group + "." + xpType;

        if (!plugin.getFileUtils().config.contains(path)) {
            return;
        }

        if (!player.hasPermission("pvplevels.group." + group) && world(player, plugin.getFileUtils().config, path)) {
            return;
        }

        final String translate = entityType.toUpperCase().replace(" ", "_");
        if (plugin.getFileUtils().language.contains("translate.entity." + translate)) {
            entityType = plugin.getFileUtils().language.getString("translate.entity." + translate);
        }
        if (xpUP) {
            if (plugin.getXPManager().isMaxLevel(playerConnect)) {
                return;
            }
            int add = plugin.getCalculateManager().randomNumber(plugin.getFileUtils().config.getInt(path + ".min"), plugin.getFileUtils().config.getInt(path + ".max"));
            if (customName != null) {
                String coloredName = plugin.getPlaceholderManager().replacePlaceholders(player, false, ChatColor.stripColor(customName));
                if (plugin.getFileUtils().config.contains(path + ".name." + coloredName)) {
                    add = plugin.getCalculateManager().randomNumber(plugin.getFileUtils().config.getInt(path + ".name." + coloredName + ".min"), plugin.getFileUtils().config.getInt(path + ".name." + coloredName + ".max"));
                }
            }
            int item_boost = hasItem(player, path);
            add = add + item_boost;
            String getPath = "get";
            if (item_boost != 0) {
                getPath = "item";
            }
            Double multiplier = playerConnect.getMultiplier();
            if (multiplier != 0D) {
                if (getPath.equalsIgnoreCase("item")) {
                    getPath = "both";
                } else {
                    getPath = "boost";
                }
                add = (int) (add * multiplier);
            } else {
                multiplier = 0D;
            }
            PlayerXPEvent playerXPEvent = new PlayerXPEvent(player, playerConnect, add, getPath, entityType, item_boost, multiplier, type);
            plugin.getServer().getPluginManager().callEvent(playerXPEvent);
            if (playerXPEvent.isCancelled()) {
                return;
            }
            playerXPEvent.execute();
            return;
        }
        if (!plugin.getFileUtils().config.contains(path + ".xp-lose")) {
            return;
        }

        final int lose = plugin.getCalculateManager().randomNumber(plugin.getFileUtils().config.getInt(path + ".xp-lose.min"), plugin.getFileUtils().config.getInt(path + ".xp-lose.max"));
        final long xp = playerConnect.getXp() - lose;

        if (xp < 0) {
            return;
        }

        if (xp < plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + plugin.getFileUtils().config.getLong("start-level") + ".xp")) {
            return;
        }

        final PlayerLoseXPEvent playerLoseXPEvent = new PlayerLoseXPEvent(player, playerConnect, lose, entityType);
        if (playerLoseXPEvent.isCancelled()) {
            return;
        }
        playerLoseXPEvent.execute();
    }

    public boolean loseLevel(final PlayerConnect playerConnect, final Player player, final String entityType) {
        if (playerConnect.getLevel() - 1 >= plugin.getFileUtils().config.getLong("start-level") && playerConnect.getXp() < plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".xp")) {
            PlayerLevelDownEvent playerLevelDownEvent = new PlayerLevelDownEvent(player, playerConnect, playerConnect.getLevel() - 1, entityType);
            plugin.getServer().getPluginManager().callEvent(playerLevelDownEvent);
            if (playerLevelDownEvent.isCancelled()) {
                return false;
            }
            playerLevelDownEvent.execute();
            return true;
        }
        return false;
    }

    public boolean getLevel(final Player player, final PlayerConnect playerConnect) {
        final long nextLevel = playerConnect.getLevel() + 1;
        if (playerConnect.getXp() >= plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + nextLevel + ".xp")) {
            PlayerLevelUPEvent playerLevelUPEvent = new PlayerLevelUPEvent(player, playerConnect, nextLevel);
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

    public boolean world(final Player player, final FileConfiguration fileConfiguration, final String path) {
        if (fileConfiguration.contains(path + ".worlds")) {
            final List<String> worlds = fileConfiguration.getStringList(path + ".worlds");
            return worlds.contains(player.getWorld().getName());
        }
        return true;
    }

    public void sendCommands(final Player killer, final String path, final FileConfiguration fileConfiguration, final String customName, final int add, final int lost, final int item_boost, final double multiplier) {
        if (path != null && fileConfiguration.contains(path)) {
            for (String command : fileConfiguration.getStringList(path)) {
                plugin.getServer().dispatchCommand(plugin.consoleSender, plugin.getPlaceholderManager().replacePlaceholders(killer, false, command.replace("{type}", customName).replace("{xp}", String.valueOf(add)).replace("{lost}", String.valueOf(lost)).replace("{item_boost}", String.valueOf(item_boost)).replace("{multiplier}", String.valueOf(multiplier))));
            }
        }
    }

    public String getKillerName(final Player killed) {
        final EntityDamageEvent entityDamageEvent = killed.getLastDamageCause();
        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            final Entity entity = ((EntityDamageByEntityEvent) entityDamageEvent).getDamager();
            String entityType = entity.getType().toString().toLowerCase();
            final String translate = entityType.toUpperCase().replace(" ", "_");
            if (plugin.getFileUtils().language.contains("translate.entity." + translate)) {
                entityType = plugin.getFileUtils().language.getString("translate.entity." + translate);
            }
            if (entityType.equalsIgnoreCase("player")) {
                return entity.getName();
            }
            return entityType;
        }
        return "";
    }

    public String getEntityKiller(final Player killed, final String group) {
        final EntityDamageEvent entityDamageEvent = killed.getLastDamageCause();
        String entityType = null;
        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            Entity entity = ((EntityDamageByEntityEvent) entityDamageEvent).getDamager();
            entityType = entity.getType().toString().toLowerCase();
            if (!plugin.getFileUtils().config.contains("xp." + group + "." + entityType)) {
                return "all";
            }
            if (entity.getCustomName() != null) {
                entityType = ChatColor.stripColor(entity.getCustomName().toLowerCase());
            }
        } else {
            if (entityDamageEvent != null) {
                entityType = entityDamageEvent.getCause().toString().toLowerCase();
            }
            if (!plugin.getFileUtils().config.contains("xp." + group + "." + entityType)) {
                return "all";
            }
        }
        return entityType;
    }

    public int hasItem(final Player player, final String path) {
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
                        return plugin.getCalculateManager().randomNumber(plugin.getFileUtils().config.getInt(path + ".items." + next + ".min"), plugin.getFileUtils().config.getInt(path + ".items." + next + ".max"));
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
                        return plugin.getCalculateManager().randomNumber(plugin.getFileUtils().config.getInt(path + ".items." + next + ".min"), plugin.getFileUtils().config.getInt(path + ".items." + next + ".max"));
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
