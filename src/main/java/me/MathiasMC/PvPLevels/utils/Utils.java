package me.MathiasMC.PvPLevels.utils;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private static String prefix() {
        return "[" + PvPLevels.getInstance().getDescription().getName() + "]";
    }

    public static void info(String text) {
        Bukkit.getLogger().info(prefix() + " " + text);
    }

    public static void warning(String text) {
        Bukkit.getLogger().warning(prefix() + " " + text);
    }

    public static void error(String text) {
        Bukkit.getLogger().severe(prefix() + " " + text);
    }

    public static void debug(String text) {
        if (!PvPLevels.getInstance().isDebug()) return;
        Bukkit.getLogger().info(prefix() + " [DEBUG] " + text);
    }

    public static void exception(StackTraceElement[] stackTraceElement, String text) {
        info("(!) " + prefix() + " has being encountered an error, pasting below for support (!)");
        for (StackTraceElement traceElement : stackTraceElement) {
            error(traceElement.toString());
        }
        info("Message: " + text);
        info(prefix() + " version: " + PvPLevels.getInstance().getDescription().getVersion());
        info("Please report this error to me on spigot");
        info("(!) " + prefix() + " (!)");
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isString(String text) {
        return text.matches("^[a-zA-Z]*$");
    }

    public static long randomNumber(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

    public static long[] getDurability(ItemStack itemStack) {
        if (itemStack != null && isValid(itemStack.getType().name())) {
            short max = itemStack.getType().getMaxDurability();
            return new long[]{max - itemStack.getDurability(), max};
        }
        return new long[]{0, 0};
    }

    private static boolean isValid(String name) {
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

    public static ItemStack getHandItemStack(Player player, boolean main) {
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

    public static String replacePlaceholders(OfflinePlayer offlinePlayer, boolean onlyPlaceholderAPI, String message) {
        if (PvPLevels.getInstance().getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            message = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(offlinePlayer, message);
        }
        if (onlyPlaceholderAPI) {
            return message;
        }
        String uuid = offlinePlayer.getUniqueId().toString();
        PlayerConnect playerConnect = PvPLevels.getInstance().getPlayerConnect(uuid);
        if (message.contains("{source}") && offlinePlayer.isOnline()) {
            message = message.replace("{source}", PvPLevels.getInstance().getXPManager().getSource((Player) offlinePlayer));
        }
        return message
                .replace("{player}", offlinePlayer.getName())
                .replace("{uuid}", uuid)
                .replace("{level_group}", PvPLevels.getInstance().getStatsManager().getGroup(playerConnect))
                .replace("{level_prefix}", PvPLevels.getInstance().getStatsManager().getPrefix(playerConnect))
                .replace("{level_suffix}", PvPLevels.getInstance().getStatsManager().getSuffix(playerConnect))
                .replace("{kills}", String.valueOf(playerConnect.getKills()))
                .replace("{deaths}", String.valueOf(playerConnect.getDeaths()))
                .replace("{xp}", String.valueOf(playerConnect.getXp()))
                .replace("{level}", String.valueOf(playerConnect.getLevel()))
                .replace("{level_next}", String.valueOf(playerConnect.getLevel() + 1))
                .replace("{kdr}", PvPLevels.getInstance().getStatsManager().getKDR(playerConnect))
                .replace("{kill_factor}", PvPLevels.getInstance().getStatsManager().getKillFactor(playerConnect))
                .replace("{killstreak}", String.valueOf(playerConnect.getKillstreak()))
                .replace("{killstreak_top}", String.valueOf(playerConnect.getKillstreakTop()))
                .replace("{xp_required}", String.valueOf(PvPLevels.getInstance().getStatsManager().getXPRequired(playerConnect, false)))
                .replace("{xp_need}", String.valueOf(PvPLevels.getInstance().getStatsManager().getXPNeeded(playerConnect)))
                .replace("{xp_progress}", String.valueOf(PvPLevels.getInstance().getStatsManager().getXPProgress(playerConnect)))
                .replace("{xp_progress_style}", String.valueOf(PvPLevels.getInstance().getStatsManager().getXPProgressStyle(playerConnect, "xp-progress-style")))
                .replace("{xp_progress_style_2}", String.valueOf(PvPLevels.getInstance().getStatsManager().getXPProgressStyle(playerConnect, "xp-progress-style-2")))
                .replace("{time}", PvPLevels.getInstance().getStatsManager().getTime(System.currentTimeMillis() - playerConnect.getTime().getTime()))
                .replace("{group}", playerConnect.getGroup())
                .replace("{xp_type}", PvPLevels.getInstance().getStatsManager().getType(playerConnect))
                .replace("{xp_get}", PvPLevels.getInstance().getStatsManager().getGet(playerConnect))
                .replace("{xp_lost}", PvPLevels.getInstance().getStatsManager().getLost(playerConnect))
                .replace("{xp_item}", PvPLevels.getInstance().getStatsManager().getItem(playerConnect))
                .replace("{xp_multiplier}", PvPLevels.getInstance().getStatsManager().getMultiplier(playerConnect))
                .replace("{xp_multiplier_time}", PvPLevels.getInstance().getStatsManager().getMultiplierTime(playerConnect))
                .replace("{xp_multiplier_time_left}", PvPLevels.getInstance().getStatsManager().getMultiplierTimeLeft(playerConnect));
    }
}
