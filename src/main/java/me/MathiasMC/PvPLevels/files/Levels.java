package me.MathiasMC.PvPLevels.files;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Levels {

    public FileConfiguration get;
    private File file;

    private final PvPLevels plugin;

    public Levels(final PvPLevels plugin) {
        this.plugin = plugin;
        file = new File(PvPLevels.call.getDataFolder(), "levels.yml");
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
        if (!get.contains("levels")) {
            for (int i = 1; i <= 10; i++) {
                ArrayList<String> list = new ArrayList<>();
                list.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &eYou are now level {pvplevels_level_to}");
                get.set("levels." + i + ".commands", list);
                ArrayList<String> list1 = new ArrayList<>();
                list1.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &eYou lost a level you are now {pvplevels_level}");
                get.set("levels." + i + ".lose-commands", list1);
            }
            get.set("levels.1.xp", 650);
            get.set("levels.2.xp", 1200);
            get.set("levels.3.xp", 1500);
            get.set("levels.4.xp", 1800);
            get.set("levels.5.xp", 2300);
            get.set("levels.6.xp", 2600);
            get.set("levels.7.xp", 2800);
            get.set("levels.8.xp", 3600);
            get.set("levels.9.xp", 4500);
            get.set("levels.10.xp", 5600);
            change = true;
        }
        if (change) {
            save();
            plugin.textUtils.info("levels.yml ( A change was made )");
        } else {
            plugin.textUtils.info("levels.yml ( Loaded )");
        }
    }
}
