package me.MathiasMC.PvPLevels.files;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Config {

    public FileConfiguration get;
    private final File file;

    private final PvPLevels plugin;

    public Config(final PvPLevels plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        }
        load();
        update();
    }

    public void load() {
        get = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            get.save(file);
        } catch (IOException exception) {
            plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
        }
    }

    private void update() {
        boolean change = false;
        plugin.textUtils.fileHeader(get);
        if (!get.contains("update-check")) {
            get.set("update-check", true);
            change = true;
        }
        if (!get.contains("debug.purge")) {
            get.set("debug.database", false);
            get.set("debug.save", false);
            get.set("debug.purge", false);
            change = true;
        }
        if (!get.contains("mysql.purge")) {
            if (!get.contains("mysql")) {
                get.set("mysql.use", false);
                get.set("mysql.host", "localhost");
                get.set("mysql.port", 3306);
                get.set("mysql.database", "database");
                get.set("mysql.username", "username");
                get.set("mysql.password", "password");
            }
            get.set("mysql.purge.use", true);
            get.set("mysql.purge.interval", 7200);
            get.set("mysql.purge.check-on-startup", true);
            get.set("mysql.purge.inactive-days", 30);
            get.set("mysql.purge.commands", new ArrayList<String>());
            change = true;
        }
        if (!get.contains("load-players.all")) {
            get.set("load-players.all", true);
            change = true;
        }
        if (!get.contains("load-players.reload")) {
            get.set("load-players.reload", true);
            change = true;
        }
        if (!get.contains("unload-players.killstreak")) {
            if (!get.contains("unload-players.quit")) {
                get.set("unload-players.quit", false);
            }
            get.set("unload-players.killstreak", false);
            change = true;
        }
        if (!get.contains("save")) {
            get.set("save.use", true);
            get.set("save.interval", 60);
            change = true;
        }
        if (!get.contains("levelup.xp-clear")) {
            get.set("levelup.xp-clear", true);
            ArrayList<String> list = new ArrayList<>();
            list.add("uuid");
            get.set("levelup.all-excluded", list);
            change = true;
        }
        if (!get.contains("events.Deaths.use")) {
            get.set("events.CreatureSpawn", true);
            get.set("events.PlayerJoin", true);
            get.set("events.PlayerRespawn", true);
            get.set("events.BlockPlace", true);
            get.set("events.BlockBreak", true);
            get.set("events.Kills", true);
            get.set("events.Deaths.use", true);
            get.set("events.Deaths.only-players", true);
            get.set("events.KillStreaks", true);
            get.set("events.PlayerRewards", true);
            change = true;
        }
        if (!get.contains("generate")) {
            get.set("generate.disable", false);
            ArrayList<String> list = new ArrayList<>();
            list.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &eYou are now level {pvplevels_level_to}");
            get.set("generate.commands", list);
            get.set("generate.xp.static", 120);
            get.set("generate.xp.min", 50);
            get.set("generate.xp.max", 400);
            get.set("generate.random.use", true);
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("give {pvplevels_player} diamond");
            list1.add("give {pvplevels_player} emerald");
            list1.add("give {pvplevels_player} gold");
            list1.add("give {pvplevels_player} iron");
            list1.add("give {pvplevels_player} coal");
            get.set("generate.random.commands", list1);
            get.set("generate.random.min", 1);
            get.set("generate.random.max", 35);
            change = true;
        }
        if (!get.contains("placeholders.time")) {
            if (!get.contains("placeholders.global-booster")) {
                get.set("placeholders.PlaceholderAPI", true);
                get.set("placeholders.prefix.default.permission", "pvplevels.prefix.default");
                get.set("placeholders.prefix.default.list.0", "&e[&7Default&e] &f[&a{pvplevels_level}&f] &e[&7{pvplevels_group}&e]");
                get.set("placeholders.prefix.default.none", "&e[&6Default&e] &f[&a{pvplevels_level}&f] &e[&7{pvplevels_group}&e]");
                get.set("placeholders.prefix.vip.permission", "pvplevels.group.vip");
                get.set("placeholders.prefix.vip.list.0", "&e[&7VIP&e] &f[&a{pvplevels_level}&f] &e[&7{pvplevels_group}&e]");
                get.set("placeholders.prefix.vip.none", "&e[&6VIP&e] &f[&a{pvplevels_level}&f] &e[&7{pvplevels_group}&e]");
                get.set("placeholders.global-booster.none", "&cNone");
                get.set("placeholders.global-booster.name", "&cNone");
                get.set("placeholders.global-booster.time", "&cNone");
                get.set("placeholders.global-booster.time-left", "&cNone");
                get.set("placeholders.global-booster.time-prefix", "&cNone");
                get.set("placeholders.global-booster.time-left-prefix", "&cNone");
                get.set("placeholders.personal-booster.none", "&cNone");
                get.set("placeholders.personal-booster.time", "&cNone");
                get.set("placeholders.personal-booster.time-left", "&cNone");
                get.set("placeholders.personal-booster.time-prefix", "&cNone");
                get.set("placeholders.personal-booster.time-left-prefix", "&cNone");
            }
            get.set("placeholders.time.format", "HH.mm.ss");
            get.set("placeholders.time.zone", "Europe/Copenhagen");
            get.set("placeholders.date.format", "dd.MM.yyyy");
            get.set("placeholders.date.zone", "Europe/Copenhagen");
            change = true;
        }
        if (!get.contains("level-max")) {
            get.set("level-max.default.permission", "pvplevels.max.level.default");
            get.set("level-max.default.max", 10);
            change = true;
        }
        if (!get.contains("kill-session.commands.get")) {
            if (!get.contains("kill-session")) {
                get.set("kill-session.use", false);
                get.set("kill-session.amount", 4);
                get.set("kill-session.time", 150);
            }
            ArrayList<String> list = new ArrayList<>();
            list.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &eYou have been put in a kill session for killing {pvplevels_killed} {pvplevels_amount} times");
            get.set("kill-session.commands.get", list);
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("kick {pvplevels_player} You cannot kill the same player so many times");
            get.set("kill-session.commands.abuse", list1);
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &eYou have been removed from the kill session");
            get.set("kill-session.commands.remove", list2);
            change = true;
        }
        if (!get.contains("pvptop.killstreak")) {
            if (!get.contains("pvptop.kills")) {
                get.set("pvptop.kills.name", "&cNone");
                get.set("pvptop.kills.value", "&cNone");
                ArrayList<String> list = new ArrayList<>();
                list.add("uuid");
                get.set("pvptop.kills.excluded", list);
                get.set("pvptop.deaths.name", "&cNone");
                get.set("pvptop.deaths.value", "&cNone");
                ArrayList<String> list2 = new ArrayList<>();
                list2.add("uuid");
                get.set("pvptop.deaths.excluded", list2);
                get.set("pvptop.xp.name", "&cNone");
                get.set("pvptop.xp.value", "&cNone");
                ArrayList<String> list3 = new ArrayList<>();
                list3.add("uuid");
                get.set("pvptop.xp.excluded", list3);
                get.set("pvptop.level.name", "&cNone");
                get.set("pvptop.level.value", "&cNone");
                ArrayList<String> list4 = new ArrayList<>();
                list4.add("uuid");
                get.set("pvptop.level.excluded", list4);
            }
            get.set("pvptop.killstreak.name", "&cNone");
            get.set("pvptop.killstreak.value", "&cNone");
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("uuid");
            get.set("pvptop.killstreak.excluded", list5);
            change = true;
        }
        if (!get.contains("spawners")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("zombie");
            list.add("skeleton");
            get.set("spawners.xp", list);
            change = true;
        }
        if (!get.contains("xp-progress-style")) {
            get.set("xp-progress-style.xp.symbol", "\\u2586");
            get.set("xp-progress-style.xp.color", "&2");
            get.set("xp-progress-style.none.symbol", "\\u2588");
            get.set("xp-progress-style.none.color", "&c");
            get.set("xp-progress-style.amount", 10);
            change = true;
        }
        if (!get.contains("groups")) {
            get.set("groups.list.default.permission", "pvplevels.group.default");
            get.set("groups.list.default.list.0", "Noob");
            get.set("groups.list.default.list.1", "Bad");
            get.set("groups.list.default.list.2", "Better");
            get.set("groups.list.default.list.3", "Good");
            get.set("groups.list.default.list.4", "Pro");
            get.set("groups.list.default.list.5", "Super");
            get.set("groups.list.default.none", "None");
            get.set("groups.none", "None");
            change = true;
        }
        if (!get.contains("xp")) {
            get.set("xp.player.default.name", "{pvplevels_player}");
            get.set("xp.player.default.min", 3);
            get.set("xp.player.default.max", 8);
            get.set("xp.player.default.permission", "pvplevels.xp.player.default");
            ArrayList<String> list = new ArrayList<>();
            list.add("world");
            get.set("xp.player.default.worlds", list);
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &c{pvplevels_type} &e+&6{pvplevels_xp_get} &eXP &6{pvplevels_xp_needed} &eto level &c{pvplevels_level_to} {pvplevels_booster_global_prefix} {pvplevels_booster_personal_prefix}");
            get.set("xp.player.default.commands", list2);
            get.set("xp.zombie.default.name", "Zombie");
            get.set("xp.zombie.default.min", 2);
            get.set("xp.zombie.default.max", 4);
            get.set("xp.zombie.default.permission", "pvplevels.xp.zombie.default");
            ArrayList<String> list3 = new ArrayList<>();
            list3.add("world");
            get.set("xp.zombie.default.worlds", list3);
            ArrayList<String> list4 = new ArrayList<>();
            list4.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &c{pvplevels_type} &e+&6{pvplevels_xp_get} &eXP &6{pvplevels_xp_needed} &eto level &c{pvplevels_level_to} {pvplevels_booster_global_prefix} {pvplevels_booster_personal_prefix}");
            get.set("xp.zombie.default.commands", list4);
            get.set("xp.zombie.default.xp-lose.min", 5);
            get.set("xp.zombie.default.xp-lose.max", 20);
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &cYou have got to a lower level :( &7( &6{pvplevels_level} &7)");
            get.set("xp.zombie.default.xp-lose.commands.level", list5);
            ArrayList<String> list6 = new ArrayList<>();
            list6.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &cYou have lost &6{pvplevels_xp_lost} &cXP");
            get.set("xp.zombie.default.xp-lose.commands.lose", list6);
            get.set("xp.skeleton.default.name", "Skeleton");
            get.set("xp.skeleton.default.min", 3);
            get.set("xp.skeleton.default.max", 6);
            get.set("xp.skeleton.default.permission", "pvplevels.xp.skeleton.default");
            ArrayList<String> list7 = new ArrayList<>();
            list7.add("world");
            get.set("xp.skeleton.default.worlds", list7);
            ArrayList<String> list8 = new ArrayList<>();
            list8.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &c{pvplevels_type} &e+&6{pvplevels_xp_get} &eXP &6{pvplevels_xp_needed} &eto level &c{pvplevels_level_to} {pvplevels_booster_global_prefix} {pvplevels_booster_personal_prefix}");
            get.set("xp.skeleton.default.commands", list8);
            get.set("xp.coal_ore.default.name", "Coal Ore");
            get.set("xp.coal_ore.default.min", 1);
            get.set("xp.coal_ore.default.max", 2);
            get.set("xp.coal_ore.default.permission", "pvplevels.xp.coal.ore.default");
            ArrayList<String> list9 = new ArrayList<>();
            list9.add("world");
            get.set("xp.coal_ore.default.worlds", list9);
            ArrayList<String> list10 = new ArrayList<>();
            list10.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &c{pvplevels_type} &e+&6{pvplevels_xp_get} &eXP &6{pvplevels_xp_needed} &eto level &c{pvplevels_level_to} {pvplevels_booster_global_prefix} {pvplevels_booster_personal_prefix}");
            get.set("xp.coal_ore.default.commands", list10);
            get.set("xp.iron_ore.default.name", "Iron Ore");
            get.set("xp.iron_ore.default.min", 1);
            get.set("xp.iron_ore.default.max", 4);
            get.set("xp.iron_ore.default.permission", "pvplevels.xp.iron.ore.default");
            ArrayList<String> list11 = new ArrayList<>();
            list11.add("world");
            get.set("xp.iron_ore.default.worlds", list11);
            ArrayList<String> list12 = new ArrayList<>();
            list12.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &c{pvplevels_type} &e+&6{pvplevels_xp_get} &eXP &6{pvplevels_xp_needed} &eto level &c{pvplevels_level_to} {pvplevels_booster_global_prefix} {pvplevels_booster_personal_prefix}");
            get.set("xp.iron_ore.default.commands", list12);
        }
        if (!get.contains("kills")) {
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("world");
            get.set("kills.worlds", list1);
            get.set("kills.default.delay", 0);
            get.set("kills.default.permission", "pvplevels.kill.commands");
            ArrayList<String> list = new ArrayList<>();
            list.add("world");
            get.set("kills.default.worlds", list);
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &e+1 kill");
            get.set("kills.default.commands", list2);
            change = true;
        }
        if (!get.contains("deaths")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("world");
            get.set("deaths.worlds", list);
            get.set("deaths.default.delay", 0);
            get.set("deaths.default.permission", "pvplevels.death.commands");
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("world");
            get.set("deaths.default.worlds", list1);
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &e+1 death");
            get.set("deaths.default.commands", list2);
            change = true;
        }
        if (!get.contains("rewards")) {
            get.set("rewards.default.delay", 0);
            get.set("rewards.default.permission", "pvplevels.rewards.kills.default");
            ArrayList<String> list = new ArrayList<>();
            list.add("world");
            get.set("rewards.default.worlds", list);
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &eYou have got a reward because you have 5 kills!");
            get.set("rewards.default.5.commands", list2);
            change = true;
        }
        if (!get.contains("join")) {
            get.set("join.default.delay", 5);
            get.set("join.default.permission", "pvplevels.join.default");
            ArrayList<String> list = new ArrayList<>();
            list.add("world");
            get.set("join.default.worlds", list);
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &e{pvplevels_player} Welcome back!");
            get.set("join.default.commands", list2);
            change = true;
        }
        if (!get.contains("join-first")) {
            get.set("join-first.default.delay", 5);
            get.set("join-first.default.permission", "pvplevels.join.default");
            ArrayList<String> list = new ArrayList<>();
            list.add("world");
            get.set("join-first.default.worlds", list);
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &e{pvplevels_player} Welcome!");
            get.set("join-first.default.commands", list2);
            change = true;
        }
        if (!get.contains("respawn")) {
            get.set("respawn.default.delay", 5);
            get.set("respawn.default.permission", "pvplevels.respawn.default");
            ArrayList<String> list = new ArrayList<>();
            list.add("world");
            get.set("respawn.default.worlds", list);
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &eYou are back alive");
            get.set("respawn.default.commands", list2);
            change = true;
        }
        if (!get.contains("killstreaks")) {
            get.set("killstreaks.default.delay", 0);
            get.set("killstreaks.default.permission", "pvplevels.killstreak.default");
            ArrayList<String> list = new ArrayList<>();
            list.add("world");
            get.set("killstreaks.default.worlds", list);
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &eYou have a killstreak of 5!");
            get.set("killstreaks.default.5.commands", list2);
            ArrayList<String> list3 = new ArrayList<>();
            list3.add("pvplevels broadcast null &7[&bPvPLevels&7] &6{pvplevels_type} &eended &6{pvplevels_player} &ekillstreak of &6{pvplevels_killstreak_lost}");
            get.set("killstreaks.default.5.lose-commands", list3);
            change = true;
        }
        if (change) {
            save();
            plugin.textUtils.info("config.yml ( A change was made )");
        } else {
            plugin.textUtils.info("config.yml ( Loaded )");
        }
    }
}
