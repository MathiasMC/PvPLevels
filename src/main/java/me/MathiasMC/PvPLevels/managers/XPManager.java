package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class XPManager {

    private final PvPLevels plugin;

    public XPManager(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    public void check(PlayerConnect playerConnect, String entityType, String entityName, Player killer, boolean xpUP) {
        if (plugin.config.get.contains("xp." + entityType)) {
            String group = plugin.systemManager.getGroup(killer, plugin.config.get, "xp." + entityType, true);
            if (group != null) {
                if (xpUP) {
                    getXP(playerConnect, killer, entityType, entityName, group);
                } else {
                    loseXP(playerConnect, killer, entityType, group);
                }
            }
        }
    }

    public void getXP(PlayerConnect playerConnect, Player killer, String entityType, String entityName, String group) {
        int add = plugin.random(plugin.config.get.getInt("xp." + entityType + "." + group + ".min"), plugin.config.get.getInt("xp." + entityType + "." + group + ".max"));
        Long xp = playerConnect.xp() + add;
        Long globalBooster = 0L;
        Long personalBooster = 0L;
        if (plugin.boostersManager.hasGlobalActive() && !plugin.boosters.get.getStringList("global-settings.disabled-xp").contains(entityType)) {
            Long boosted = Math.round(add * plugin.boostersManager.type());
            globalBooster = boosted - add;
            xp = (xp - add) + boosted;
            if (plugin.boosters.get.contains("global-settings.commands")) {
                plugin.systemManager.executeCommands(killer, plugin.boosters.get, "global-settings.commands", "commands", 0L);
            }
        }
        if (playerConnect.getPersonalBooster() != null && !plugin.boosters.get.getStringList("personal-settings.disabled-xp").contains(entityType)) {
            Long boosted = Math.round(add * playerConnect.getPersonalBooster());
            personalBooster = boosted - add;
            xp = (xp - add) + boosted;
            if (plugin.boosters.get.contains("personal-settings.commands")) {
                plugin.systemManager.executeCommands(killer, plugin.boosters.get, "personal-settings.commands", "commands", 0L);
            }
        }
        playerConnect.xp(xp);
        if (!getLevel(playerConnect, killer)) {
            String customName = plugin.config.get.getString("xp." + entityType + "." + group + ".name").replace("{pvplevels_player}", entityName);
            Long need = plugin.levels.get.getLong("levels." + (playerConnect.level() + 1) + ".xp") - xp;
            if (plugin.config.get.contains("xp." + entityType + "." + group + ".level-commands." + playerConnect.level())) {
                sendCommands(killer, "xp." + entityType + "." + group + ".level-commands." + playerConnect.level(), plugin.config.get, customName, add, need, 0, globalBooster, personalBooster, entityType);
                return;
            }
            sendCommands(killer, "xp." + entityType + "." + group + ".commands", plugin.config.get, customName, add, need, 0, globalBooster, personalBooster, entityType);
        }
    }

    public void loseXP(PlayerConnect playerConnect, Player killer, String entityType, String group) {
        if (plugin.config.get.contains("xp." + entityType + "." + group + ".xp-lose")) {
            boolean xpMessage = true;
            int lost = plugin.random(plugin.config.get.getInt("xp." + entityType + "." + group + ".xp-lose.min"), plugin.config.get.getInt("xp." + entityType + "." + group + ".xp-lose.max"));
            Long xp = playerConnect.xp() - lost;
            if (xp > 0L) {
                playerConnect.xp(xp);
                if (!clearXP() && playerConnect.xp() < plugin.levels.get.getLong("levels." + playerConnect.level() + ".xp")) {
                    xpMessage = loseLevel(playerConnect, playerConnect.level() - 1, killer, "xp." + entityType + "." + group + ".xp-lose.commands.level");
                }
            } else {
                Long level = playerConnect.level() - 1;
                xpMessage = loseLevel(playerConnect, level, killer, "xp." + entityType + "." + group + ".xp-lose.commands.level");
                if (clearXP()) {
                    playerConnect.xp(0L);
                } else {
                    playerConnect.xp(plugin.levels.get.getLong("levels." + level + ".xp"));
                }
            }
            if (xp >= 0L && xpMessage) {
                sendCommands(killer, "xp." + entityType + "." + group + ".xp-lose.commands.lose", plugin.config.get, "", 0, 0L, lost, 0L, 0L, "");
            }
        }
    }

    public boolean loseLevel(PlayerConnect playerConnect, Long level, Player player, String commandPath) {
        if (level >= 0) {
            playerConnect.level(level);
            sendCommands(player, commandPath, plugin.config.get, "", 0, 0L, 0, 0L, 0L, "");
            if (plugin.levels.get.contains("levels." + level + ".lose-commands")) {
                sendCommands(player, "levels." + level + ".lose-commands", plugin.levels.get, "", 0, 0L, 0, 0L, 0L, "");
            }
            return false;
        }
        return true;
    }

    public boolean getLevel(PlayerConnect playerConnect, Player player) {
        Long nextLevel = playerConnect.level() + 1;
        if (playerConnect.xp() >= plugin.levels.get.getLong("levels." + nextLevel + ".xp")) {
            playerConnect.level(nextLevel);
            if (clearXP()) {
                playerConnect.xp(0L);
            }
            sendCommands(player, "levels." + nextLevel + ".commands", plugin.levels.get, "", 0, 0L, 0, 0L, 0L, "");
            return true;
        }
        return false;
    }

    public boolean isMaxLevel(Player player, PlayerConnect playerConnect) {
        String group = plugin.systemManager.getGroup(player, plugin.config.get, "level-max", false);
        if (group != null) {
            return playerConnect.level() >= plugin.config.get.getInt("level-max." + group + ".max");
        }
        return false;
    }

    public boolean clearXP() {
        return plugin.config.get.getBoolean("levelup.xp-clear");
    }

    private void sendCommands(Player killer, String path, FileConfiguration fileConfiguration, String customName, int add, Long need, int lost, Long globalBooster, Long personalBooster, String entityType) {
        if (path != null) {
            for (String command : fileConfiguration.getStringList(path)) {
                PvPLevels.call.getServer().dispatchCommand(plugin.consoleCommandSender, plugin.PlaceholderReplace(killer, command.replace("{pvplevels_type}", customName).replace("{pvplevels_xp_get}", String.valueOf(add)).replace("{pvplevels_xp_needed}", String.valueOf(need)).replace("{pvplevels_xp_lost}", String.valueOf(lost))).replace("{pvplevels_booster_global_prefix}", plugin.boostersManager.globalPrefix(globalBooster, entityType)).replace("{pvplevels_booster_personal_prefix}", plugin.boostersManager.personalPrefix(killer.getUniqueId().toString(), personalBooster, entityType)));
            }
        }
    }
}