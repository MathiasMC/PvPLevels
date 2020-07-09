package me.MathiasMC.PvPLevels.files;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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
    }

    public void load() {
        get = YamlConfiguration.loadConfiguration(file);
    }
}