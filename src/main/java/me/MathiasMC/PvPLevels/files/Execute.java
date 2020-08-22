package me.MathiasMC.PvPLevels.files;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Execute {

    public FileConfiguration get;
    private final File file;

    public Execute(final PvPLevels plugin) {
        file = new File(plugin.getDataFolder(), "execute.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                plugin.copy("execute.yml", file);
                plugin.textUtils.info("execute.yml ( A change was made )");
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        } else {
            plugin.textUtils.info("execute.yml ( Loaded )");
        }
        load();
    }

    public void load() {
        get = YamlConfiguration.loadConfiguration(file);
    }
}
