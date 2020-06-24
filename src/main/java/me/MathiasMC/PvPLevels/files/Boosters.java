package me.MathiasMC.PvPLevels.files;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Boosters {

    public FileConfiguration get;
    private File file;

    private final PvPLevels plugin;

    public Boosters(final PvPLevels plugin) {
        this.plugin = plugin;
        file = new File(PvPLevels.call.getDataFolder(), "boosters.yml");
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
        if (!get.contains("global-settings")) {
            get.set("global-settings.prefix", "&7( &6{pvplevels_booster_global_xp} &aBoost XP &7)");
            ArrayList<String> list = new ArrayList<>();
            list.add("pvplevels broadcast null &7[&bPvPLevels&7] &6{pvplevels_player} &estarted a global booster &6{pvplevels_booster_global_type} &efor &6{pvplevels_booster_global_time}");
            get.set("global-settings.start", list);
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("pvplevels broadcast null &7[&bPvPLevels&7] &6{pvplevels_player} &ebooster has ended");
            get.set("global-settings.stop.ended", list1);
            ArrayList<String> list11 = new ArrayList<>();
            list11.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &eGlobal queue added &6{pvplevels_booster_global_type} &efor &6{pvplevels_booster_global_time} &equeue: {pvplevels_booster_global_queue}");
            get.set("global-settings.queue.add", list11);
            ArrayList<String> list12 = new ArrayList<>();
            list12.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &eYou are already in the queue with the max of &6{pvplevels_booster_global_max}");
            get.set("global-settings.queue.max", list12);
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("pvplevels broadcast null &7[&bPvPLevels&7] &cThe global booster queue is now empty");
            get.set("global-settings.stop.queue", list2);
            get.set("global-settings.commands.default.delay", 5);
            get.set("global-settings.commands.default.permission", "pvplevels.boosters.global.default");
            ArrayList<String> list22 = new ArrayList<>();
            list22.add("world");
            get.set("global-settings.commands.default.worlds", list22);
            get.set("global-settings.commands.default.commands", new ArrayList<>());
            get.set("global-settings.max-queue", 1);
            get.set("global-settings.gui.none.NAME", "&6Global Booster");
            ArrayList<String> list3 = new ArrayList<>();
            list3.add("&a<&f&l------&bPower&f&l------&a>");
            list3.add("");
            list3.add("             &6{pvplevels_booster_type}");
            list3.add("");
            list3.add("&a<&f&l-----&bDuration&f&l-----&a>");
            list3.add("");
            list3.add("          &6{pvplevels_booster_time}");
            get.set("global-settings.gui.none.LORES", list3);
            if (plugin.versionID()) {
                get.set("global-settings.gui.none.MATERIAL", "265:0");
            } else {
                get.set("global-settings.gui.none.MATERIAL", "IRON_INGOT");
            }
            get.set("global-settings.gui.none.AMOUNT", 1);
            ArrayList<String> list4 = new ArrayList<>();
            list4.add("GLOW");
            list4.add("CLOSE");
            get.set("global-settings.gui.none.OPTIONS", list4);
            get.set("global-settings.gui.1.1.NAME", "&6Global Booster");
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("&a<&f&l------&bPower&f&l------&a>");
            list5.add("");
            list5.add("             &6{pvplevels_booster_type}");
            list5.add("");
            list5.add("&a<&f&l-----&bDuration&f&l-----&a>");
            list5.add("");
            list5.add("          &6{pvplevels_booster_time}");
            get.set("global-settings.gui.1.1.LORES", list5);
            if (plugin.versionID()) {
                get.set("global-settings.gui.1.1.MATERIAL", "263:0");
            } else {
                get.set("global-settings.gui.1.1.MATERIAL", "COAL");
            }
            get.set("global-settings.gui.1.1.AMOUNT", 1);
            ArrayList<String> list6 = new ArrayList<>();
            list6.add("GLOW");
            list6.add("CLOSE");
            get.set("global-settings.gui.1.1.OPTIONS", list6);
            change = true;
        }
        if (!get.contains("personal-settings")) {
            get.set("personal-settings.prefix", "&7( &6{pvplevels_booster_personal_xp} &aPersonal Boost XP &7)");

            ArrayList<String> list = new ArrayList<>();
            list.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &eStarted a personal booster &6{pvplevels_booster_personal_type} &efor &6{pvplevels_booster_personal_time}");
            get.set("personal-settings.start", list);

            ArrayList<String> list1 = new ArrayList<>();
            list1.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &eYour personal booster has ended");
            get.set("personal-settings.end", list1);

            ArrayList<String> list12 = new ArrayList<>();
            list12.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &eYou already have an active personal booster");
            get.set("personal-settings.active", list12);


            get.set("personal-settings.commands.default.delay", 5);
            get.set("personal-settings.commands.default.permission", "pvplevels.boosters.personal.default");
            ArrayList<String> list22 = new ArrayList<>();
            list22.add("world");
            get.set("personal-settings.commands.default.worlds", list22);
            get.set("personal-settings.commands.default.commands", new ArrayList<>());


            get.set("personal-settings.gui.none.NAME", "&6Personal Booster");
            ArrayList<String> list3 = new ArrayList<>();
            list3.add("&a<&f&l------&bPower&f&l------&a>");
            list3.add("");
            list3.add("             &6{pvplevels_booster_type}");
            list3.add("");
            list3.add("&a<&f&l-----&bDuration&f&l-----&a>");
            list3.add("");
            list3.add("          &6{pvplevels_booster_time}");
            get.set("personal-settings.gui.none.LORES", list3);
            if (plugin.versionID()) {
                get.set("personal-settings.gui.none.MATERIAL", "265:0");
            } else {
                get.set("personal-settings.gui.none.MATERIAL", "IRON_INGOT");
            }
            get.set("personal-settings.gui.none.AMOUNT", 1);
            ArrayList<String> list4 = new ArrayList<>();
            list4.add("GLOW");
            list4.add("CLOSE");
            get.set("personal-settings.gui.none.OPTIONS", list4);
            get.set("personal-settings.gui.1.1.NAME", "&6Personal Booster");
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("&a<&f&l------&bPower&f&l------&a>");
            list5.add("");
            list5.add("             &6{pvplevels_booster_type}");
            list5.add("");
            list5.add("&a<&f&l-----&bDuration&f&l-----&a>");
            list5.add("");
            list5.add("          &6{pvplevels_booster_time}");
            get.set("personal-settings.gui.1.1.LORES", list5);
            if (plugin.versionID()) {
                get.set("personal-settings.gui.1.1.MATERIAL", "263:0");
            } else {
                get.set("personal-settings.gui.1.1.MATERIAL", "COAL");
            }
            get.set("personal-settings.gui.1.1.AMOUNT", 1);
            ArrayList<String> list6 = new ArrayList<>();
            list6.add("GLOW");
            list6.add("CLOSE");
            get.set("personal-settings.gui.1.1.OPTIONS", list6);
            change = true;
        }
        if (!get.contains("global-queue")) {
            get.set("global-queue", new ArrayList<String>());
            change = true;
        }
        if (!get.contains("players")) {
            get.set("players", new ArrayList<String>());
            change = true;
        }
        if (change) {
            save();
            plugin.textUtils.info("boosters.yml ( A change was made )");
        } else {
            plugin.textUtils.info("boosters.yml ( Loaded )");
        }
    }
}
