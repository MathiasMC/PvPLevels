package me.MathiasMC.PvPLevels.files;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Boosters {

    public FileConfiguration get;
    private final File file;

    private final PvPLevels plugin;

    public Boosters(final PvPLevels plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), "boosters.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                if (!plugin.versionID()) {
                    plugin.copy("boosters.yml", file);
                } else {
                    plugin.copy("old/boosters.yml", file);
                }
                plugin.textUtils.info("boosters.yml ( A change was made )");
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        } else {
            plugin.textUtils.info("boosters.yml ( Loaded )");
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