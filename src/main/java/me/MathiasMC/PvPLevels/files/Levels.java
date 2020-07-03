package me.MathiasMC.PvPLevels.files;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Levels {

    public FileConfiguration get;
    private final File file;

    private final PvPLevels plugin;

    public Levels(final PvPLevels plugin) {
        this.plugin = plugin;
        file = new File(PvPLevels.call.getDataFolder(), "levels.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                plugin.copy("levels.yml", file);
                plugin.textUtils.info("levels.yml ( A change was made )");
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        } else {
            plugin.textUtils.info("levels.yml ( Loaded )");
        }
        load();
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
}