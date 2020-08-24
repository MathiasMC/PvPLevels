package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
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
            if (!plugin.config.get.getStringList("excluded").contains(killedUUID)) {
                final PlayerConnect playerConnect = plugin.get(killedUUID);
                check(killed, entity.getCustomName(), getEntityKiller(killed, playerConnect.getGroup()), "", false);
                if (world(killed, plugin.config.get, "deaths." + playerConnect.getGroup())) {
                    boolean run = false;
                    if (plugin.config.get.getBoolean("deaths." + playerConnect.getGroup() + ".only-player")) {
                        if (killer != null) {
                            run = true;
                        }
                    } else {
                        run = true;
                    }
                    if (run) {
                        playerConnect.setDeaths(playerConnect.getDeaths() + 1);
                        boolean isSet = false;
                        String source = killed.getLastDamageCause().getCause().toString();
                        if (plugin.language.get.contains("translate.cause." + source)) {
                            source = plugin.language.get.getString("translate.cause." + source);
                            isSet = true;
                        }
                        if (!isSet) {
                            String killerName = getKillerName(killed);
                            if (killerName.length() > 0) {
                                source = killerName;
                            }
                        }
                        boolean isPlayer = false;
                        if (killer != null) {
                            isPlayer = true;
                        }
                        if (isPlayer) {
                            for (String command : plugin.config.get.getStringList("deaths." + playerConnect.getGroup() + ".player")) {
                                plugin.getServer().dispatchCommand(plugin.consoleSender, plugin.replacePlaceholders(killed, command.replace("{source}", source)));
                            }
                        } else {
                            for (String command : plugin.config.get.getStringList("deaths." + playerConnect.getGroup() + ".other")) {
                                plugin.getServer().dispatchCommand(plugin.consoleSender, plugin.replacePlaceholders(killed, command.replace("{source}", source)));
                            }
                        }
                    }
                }
            }
        }
        if (killer != null && !plugin.config.get.getStringList("excluded").contains(killer.getUniqueId().toString())) {
            if (!plugin.killSessionManager.check(entity, killer) && !plugin.spawners.contains(entity.getUniqueId().toString())) {
                check(killer, entity.getCustomName(), entity.getType().toString().toLowerCase(), entity.getName(), true);
                if (entity instanceof Player) {
                    final PlayerConnect playerConnect = plugin.get(killer.getUniqueId().toString());
                    if (world(killer, plugin.config.get, "kills." + playerConnect.getGroup())) {
                        playerConnect.setKills(playerConnect.getKills() + 1);
                        final String path = "kills." + playerConnect.getGroup() + "." + playerConnect.getKills();
                        if (plugin.config.get.contains(path)) {
                            sendCommands(killer, path, plugin.config.get, "", 0, 0, 0, 0);
                        } else {
                            sendCommands(killer, "kills." + playerConnect.getGroup() + ".get", plugin.config.get, "", 0, 0, 0, 0);
                        }
                    }
                    if (world(killer, plugin.config.get, "killstreak." + playerConnect.getGroup())) {
                        final long killStreak = playerConnect.getKillstreak() + 1;
                        playerConnect.setKillstreak(killStreak);
                        if (killStreak > playerConnect.getKillstreak_top()) {
                            playerConnect.setKillstreak_top(killStreak);
                            final String path = "killstreak." + playerConnect.getGroup() + "." + playerConnect.getKillstreak() + ".top";
                            if (plugin.config.get.contains(path)) {
                                sendCommands(killer, path, plugin.config.get, "", 0, 0, 0, 0);
                                return;
                            }
                        }
                        final String path = "killstreak." + playerConnect.getGroup() + "." + playerConnect.getKillstreak() + ".get";
                        if (plugin.config.get.contains(path)) {
                            sendCommands(killer, path, plugin.config.get, "", 0, 0, 0, 0);
                            return;
                        }
                        sendCommands(killer, "killstreak." + playerConnect.getGroup() + ".get", plugin.config.get, "", 0, 0, 0, 0);
                    }
                }
            }
        }
    }

    public void check(final Player player, final String customName, final String xpType, String entityType, final boolean xpUP) {
        final String uuid = player.getUniqueId().toString();
        final PlayerConnect playerConnect = plugin.get(uuid);
        final String group = playerConnect.getGroup();
        String path = "xp." + group + "." + xpType;
        if (plugin.config.get.contains(path) && player.hasPermission("pvplevels.group." + group) && world(player, plugin.config.get, path)) {
            final String translate = entityType.toUpperCase().replace(" ", "_");
            if (plugin.language.get.contains("translate.entity." + translate)) {
                entityType = plugin.language.get.getString("translate.entity." + translate);
            }
            if (xpUP) {
                if (plugin.xpManager.isMax(playerConnect)) {
                    return;
                }
                int add = plugin.random(plugin.config.get.getInt(path + ".min"), plugin.config.get.getInt(path + ".max"));
                final String coloredName = ChatColor.stripColor(customName);
                if (plugin.config.get.contains(path + ".name." + coloredName)) {
                    add = plugin.random(plugin.config.get.getInt(path + ".name." + coloredName + ".min"), plugin.config.get.getInt(path + ".name." + coloredName + ".max"));
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
                getXP(player, playerConnect, add, entityType, getPath, item_boost, multiplier);
            } else {
                loseXP(player, playerConnect, path, entityType);
            }
        }
    }

    public void getXP(final Player player, final PlayerConnect playerConnect, final int add, final String entityType, final String path, final int item_boost, final double multiplier) {
        final long xp = playerConnect.getXp() + add;
        playerConnect.setXp(xp);
        if (!getLevel(player, playerConnect)) {
            sendCommands(player, plugin.levels.get.getString(playerConnect.getGroup() + ".execute") + ".xp." + path, plugin.execute.get, entityType, add, 0, item_boost, multiplier);
        }
    }

    public void loseXP(final Player player, final PlayerConnect playerConnect, final String path, final String entityType) {
        if (plugin.config.get.contains(path + ".xp-lose")) {
            final int lose = plugin.random(plugin.config.get.getInt(path + ".xp-lose.min"), plugin.config.get.getInt(path + ".xp-lose.max"));
            final long xp = playerConnect.getXp() - lose;
            boolean message = true;
            if (xp >= plugin.levels.get.getLong(playerConnect.getGroup() + "." + plugin.config.get.getLong("start-level") + ".xp")) {
                if (xp >= 0) {
                    playerConnect.setXp(xp);
                    if (playerConnect.getXp() < plugin.levels.get.getLong(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".xp")) {
                        message = loseLevel(playerConnect, player, entityType);
                    }
                }
                if (xp >= 0L && message) {
                    sendCommands(player, plugin.levels.get.getString(playerConnect.getGroup() + ".execute") + ".xp.lose", plugin.execute.get, entityType, 0, lose, 0, 0);
                }
            }
        }
    }

    private boolean loseLevel(final PlayerConnect playerConnect, final Player player, final String entityType) {
        if (playerConnect.getLevel() - 1 >= plugin.config.get.getLong("start-level")) {
            playerConnect.setLevel(playerConnect.getLevel() - 1);
            sendCommands(player, plugin.levels.get.getString(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".execute") + ".level.down", plugin.execute.get, entityType, 0, 0, 0, 0);
            return false;
        }
        return true;
    }

    public boolean getLevel(final Player player, final PlayerConnect playerConnect) {
        final Long nextLevel = playerConnect.getLevel() + 1;
        if (playerConnect.getXp() >= plugin.levels.get.getLong(playerConnect.getGroup() + "." + nextLevel + ".xp")) {
            sendCommands(player, plugin.levels.get.getString(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".execute") + ".level.up", plugin.execute.get, "", 0, 0, 0, 0);
            playerConnect.setLevel(nextLevel);
            return true;
        }
        return false;
    }

    public boolean isMax(final PlayerConnect playerConnect) {
        return !plugin.levels.get.contains(playerConnect.getGroup() + "." + (playerConnect.getLevel() + 1));
    }

    private boolean world(final Player player, final FileConfiguration fileConfiguration, final String path) {
        if (fileConfiguration.contains(path + ".worlds")) {
            final List<String> worlds = fileConfiguration.getStringList(path + ".worlds");
            return worlds.contains(player.getWorld().getName());
        }
        return true;
    }

    public void sendCommands(final Player killer, final String path, final FileConfiguration fileConfiguration, final String customName, final int add, final int lost, final int item_boost, final double multiplier) {
        if (path != null) {
            for (String command : fileConfiguration.getStringList(path)) {
                plugin.getServer().dispatchCommand(plugin.consoleSender, plugin.replacePlaceholders(killer, command.replace("{type}", customName).replace("{xp}", String.valueOf(add)).replace("{lost}", String.valueOf(lost)).replace("{item_boost}", String.valueOf(item_boost)).replace("{multiplier}", String.valueOf(multiplier))));
            }
        }
    }

    public String getKillerName(final Player killed) {
        final EntityDamageEvent entityDamageEvent = killed.getLastDamageCause();
        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            final Entity entity = ((EntityDamageByEntityEvent) entityDamageEvent).getDamager();
            String entityType = entity.getType().toString().toLowerCase();
            final String translate = entityType.toUpperCase().replace(" ", "_");
            if (plugin.language.get.contains("translate.entity." + translate)) {
                entityType = plugin.language.get.getString("translate.entity." + translate);
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
            if (!plugin.config.get.contains("xp." + group + "." + entityType)) {
                return "all";
            }
            if (entity.getCustomName() != null) {
                entityType = ChatColor.stripColor(entity.getCustomName().toLowerCase());
            }
        } else {
            if (entityDamageEvent != null) {
                entityType = entityDamageEvent.getCause().toString().toLowerCase();
            }
            if (!plugin.config.get.contains("xp." + group + "." + entityType)) {
                return "all";
            }
        }
        return entityType;
    }

    public int hasItem(final Player player, final String path) {
        if (!plugin.config.get.contains(path + ".items")) {
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
            for (String next : plugin.config.get.getConfigurationSection(path + ".items").getKeys(false)) {
                if (next.equalsIgnoreCase(itemStackName.toLowerCase())) {
                    if (checkItem(player, hand, path, next)) {
                        return plugin.random(plugin.config.get.getInt(path + ".items." + next + ".min"), plugin.config.get.getInt(path + ".items." + next + ".max"));
                    }
                    break;
                }
            }
        }
        if (offHand != null) {
            final String itemStackName = offHand.getType().name();
            for (String next : plugin.config.get.getConfigurationSection(path + ".items").getKeys(false)) {
                if (next.equalsIgnoreCase(itemStackName.toLowerCase())) {
                    if (checkItem(player, offHand, path, next)) {
                        return plugin.random(plugin.config.get.getInt(path + ".items." + next + ".min"), plugin.config.get.getInt(path + ".items." + next + ".max"));
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
        final String displayName = plugin.replacePlaceholders(player, ChatColor.translateAlternateColorCodes('&', plugin.config.get.getString(path + ".items." + next + ".name")));
        if (itemMeta.hasDisplayName()) {
            if (!itemMeta.getDisplayName().equalsIgnoreCase(displayName)) {
                check = false;
            }
        } else if (!displayName.isEmpty()) {
            check = false;
        }
        final List<String> lores = plugin.config.get.getStringList(path + ".items." + next + ".lores");
        if (itemMeta.hasLore()) {
            final List<String> list = new ArrayList<>();
            for (String lore : lores) {
                list.add(plugin.replacePlaceholders(player, ChatColor.translateAlternateColorCodes('&', lore)));
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
