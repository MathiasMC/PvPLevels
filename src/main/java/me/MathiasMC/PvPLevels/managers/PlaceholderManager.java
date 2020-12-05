package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlaceholderManager {

    private final PvPLevels plugin;

    public PlaceholderManager(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    public long[] getDurability(final ItemStack itemStack) {
        if (itemStack != null && isValid(itemStack.getType().name())) {
            short max = itemStack.getType().getMaxDurability();
            return new long[]{max - itemStack.getDurability(), max};
        }
        return new long[]{0, 0};
    }

    private boolean isValid(final String name) {
        return name.endsWith("_HELMET")
                || name.endsWith("_CHESTPLATE")
                || name.endsWith("_LEGGINGS")
                || name.endsWith("_BOOTS")
                || name.endsWith("_SWORD")
                || name.endsWith("_PICKAXE")
                || name.endsWith("_AXE")
                || name.endsWith("_SHOVEL")
                || name.endsWith("SHIELD");
    }

    public ItemStack getHandItemStack(final Player player, final boolean main) {
        ItemStack itemStack;
        try {
            if (main) {
                itemStack = player.getInventory().getItemInMainHand();
            } else {
                itemStack = player.getInventory().getItemInOffHand();
            }
        } catch (NoSuchMethodError error) {
            itemStack = player.getInventory().getItemInHand();
        }
        return itemStack;
    }

    public String replacePlaceholders(final OfflinePlayer offlinePlayer, boolean onlyPlaceholderAPI, String message) {
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            message = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(offlinePlayer, message);
        }
        if (onlyPlaceholderAPI) {
            return message;
        }
        final String uuid = offlinePlayer.getUniqueId().toString();
        final PlayerConnect playerConnect = plugin.getPlayerConnect(uuid);
        if (message.contains("{source}")) {
            String source = "VOID";
            if (offlinePlayer.isOnline()) {
                final Player player = (Player) offlinePlayer;
                final String killerName = plugin.getXPManager().getKillerName(player);
                if (player.getLastDamageCause() != null) {
                    source = player.getLastDamageCause().getCause().toString();
                    if (killerName != null) {
                        source = killerName;
                    }
                }
                if (plugin.getFileUtils().language.contains("translate.cause." + source)) {
                    source = plugin.getFileUtils().language.getString("translate.cause." + source);
                }
            }
            message = message.replace("{source}", source);
        }
        return message
                .replace("{player}", offlinePlayer.getName())
                .replace("{uuid}", uuid)
                .replace("{level_group}", plugin.getStatsManager().getGroup(offlinePlayer))
                .replace("{level_prefix}", plugin.getStatsManager().getPrefix(offlinePlayer))
                .replace("{level_suffix}", plugin.getStatsManager().getSuffix(offlinePlayer))
                .replace("{kills}", String.valueOf(playerConnect.getKills()))
                .replace("{deaths}", String.valueOf(playerConnect.getDeaths()))
                .replace("{xp}", String.valueOf(playerConnect.getXp()))
                .replace("{level}", String.valueOf(playerConnect.getLevel()))
                .replace("{level_next}", String.valueOf(playerConnect.getLevel() + 1))
                .replace("{kdr}", plugin.getStatsManager().getKDR(playerConnect))
                .replace("{kill_factor}", plugin.getStatsManager().getKillFactor(playerConnect))
                .replace("{killstreak}", String.valueOf(playerConnect.getKillstreak()))
                .replace("{killstreak_top}", String.valueOf(playerConnect.getKillstreakTop()))
                .replace("{xp_required}", String.valueOf(plugin.getStatsManager().getXPRequired(playerConnect, false)))
                .replace("{xp_need}", String.valueOf(plugin.getStatsManager().getXPNeeded(playerConnect)))
                .replace("{xp_progress}", String.valueOf(plugin.getStatsManager().getXPProgress(playerConnect)))
                .replace("{xp_progress_style}", String.valueOf(plugin.getStatsManager().getXPProgressStyle(playerConnect, "xp-progress-style")))
                .replace("{xp_progress_style_2}", String.valueOf(plugin.getStatsManager().getXPProgressStyle(playerConnect, "xp-progress-style-2")))
                .replace("{time}", plugin.getStatsManager().getTime(System.currentTimeMillis() - playerConnect.getTime().getTime()))
                .replace("{group}", playerConnect.getGroup())
                .replace("{xp_type}", plugin.getStatsManager().getType(playerConnect))
                .replace("{xp_get}", plugin.getStatsManager().getGet(playerConnect))
                .replace("{xp_lost}", plugin.getStatsManager().getLost(playerConnect))
                .replace("{xp_item}", plugin.getStatsManager().getItem(playerConnect))
                .replace("{xp_multiplier}", plugin.getStatsManager().getMultiplier(playerConnect))
                .replace("{xp_multiplier_time}", plugin.getStatsManager().getMultiplierTime(playerConnect))
                .replace("{xp_multiplier_time_left}", plugin.getStatsManager().getMultiplierTimeLeft(playerConnect));
    }
}