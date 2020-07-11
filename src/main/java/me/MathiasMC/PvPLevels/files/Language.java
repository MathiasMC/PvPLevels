package me.MathiasMC.PvPLevels.files;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Language {

    public FileConfiguration get;
    private final File file;

    private final PvPLevels plugin;

    public Language(final PvPLevels plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), "language.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                plugin.copy("language.yml", file);
                plugin.textUtils.info("language.yml ( A change was made )");
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        } else {
            plugin.textUtils.info("language.yml ( Loaded )");
        }
        load();
        update();
    }

    public void load() {
        get = YamlConfiguration.loadConfiguration(file);
    }

    private void update() {
        boolean change = false;
        if (!get.contains("player.pvptop.killstreak")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lCurrent Top KillStreak&7]");
            list.add("&7[&a1&7] &e%pvplevels_top_1_killstreak_name% &6 > &b%pvplevels_top_1_killstreak%");
            list.add("&7[&a2&7] &e%pvplevels_top_2_killstreak_name% &6 > &b%pvplevels_top_2_killstreak%");
            list.add("&7[&a3&7] &e%pvplevels_top_3_killstreak_name% &6 > &b%pvplevels_top_3_killstreak%");
            list.add("&7[&a4&7] &e%pvplevels_top_4_killstreak_name% &6 > &b%pvplevels_top_4_killstreak%");
            list.add("&7[&a5&7] &e%pvplevels_top_5_killstreak_name% &6 > &b%pvplevels_top_5_killstreak%");
            list.add("&c&m---------------------------------------------");
            get.set("player.pvptop.killstreak.message", list);
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvptop.killstreak.permission", list1);
            change = true;
        }
        if (!get.contains("player.pvptop.killstreak_top")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lAll Time Top KillStreak&7]");
            list.add("&7[&a1&7] &e%pvplevels_top_1_killstreak_top_name% &6 > &b%pvplevels_top_1_killstreak_top%");
            list.add("&7[&a2&7] &e%pvplevels_top_2_killstreak_top_name% &6 > &b%pvplevels_top_2_killstreak_top%");
            list.add("&7[&a3&7] &e%pvplevels_top_3_killstreak_top_name% &6 > &b%pvplevels_top_3_killstreak_top%");
            list.add("&7[&a4&7] &e%pvplevels_top_4_killstreak_top_name% &6 > &b%pvplevels_top_4_killstreak_top%");
            list.add("&7[&a5&7] &e%pvplevels_top_5_killstreak_top_name% &6 > &b%pvplevels_top_5_killstreak_top%");
            list.add("&c&m---------------------------------------------");
            get.set("player.pvptop.killstreak_top.message", list);
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvptop.killstreak_top.permission", list1);
            change = true;
        }
        if (!get.contains("console.pvptop.killstreak")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lCurrent Top KillStreak&7]");
            list.add("&7[&a1&7] &e%pvplevels_top_1_killstreak_name% &6 > &b%pvplevels_top_1_killstreak%");
            list.add("&7[&a2&7] &e%pvplevels_top_2_killstreak_name% &6 > &b%pvplevels_top_2_killstreak%");
            list.add("&7[&a3&7] &e%pvplevels_top_3_killstreak_name% &6 > &b%pvplevels_top_3_killstreak%");
            list.add("&7[&a4&7] &e%pvplevels_top_4_killstreak_name% &6 > &b%pvplevels_top_4_killstreak%");
            list.add("&7[&a5&7] &e%pvplevels_top_5_killstreak_name% &6 > &b%pvplevels_top_5_killstreak%");
            list.add("&c&m---------------------------------------------");
            get.set("console.pvptop.killstreak.message", list);
            change = true;
        }
        if (!get.contains("console.pvptop.killstreak_top")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lAll Time Top KillStreak&7]");
            list.add("&7[&a1&7] &e%pvplevels_top_1_killstreak_top_name% &6 > &b%pvplevels_top_1_killstreak_top%");
            list.add("&7[&a2&7] &e%pvplevels_top_2_killstreak_top_name% &6 > &b%pvplevels_top_2_killstreak_top%");
            list.add("&7[&a3&7] &e%pvplevels_top_3_killstreak_top_name% &6 > &b%pvplevels_top_3_killstreak_top%");
            list.add("&7[&a4&7] &e%pvplevels_top_4_killstreak_top_name% &6 > &b%pvplevels_top_4_killstreak_top%");
            list.add("&7[&a5&7] &e%pvplevels_top_5_killstreak_top_name% &6 > &b%pvplevels_top_5_killstreak_top%");
            list.add("&c&m---------------------------------------------");
            get.set("console.pvptop.killstreak_top.message", list);
            change = true;
        }
        if (change) {
            try {
                get.save(file);
            } catch (Exception e) {
                plugin.textUtils.exception(e.getStackTrace(), e.getMessage());
            }
        }
    }
}