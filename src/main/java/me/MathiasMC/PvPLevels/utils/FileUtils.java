package me.MathiasMC.PvPLevels.utils;

import com.google.common.io.ByteStreams;
import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    private final PvPLevels plugin;

    private final File configFile;
    public FileConfiguration config;

    private final File languageFile;
    public FileConfiguration language;

    private final File levelsFile;
    public FileConfiguration levels;

    private final File executeFile;
    public FileConfiguration execute;

    public FileUtils(final PvPLevels plugin) {
        this.plugin = plugin;
        final File pluginFolder = getFolder(plugin.getDataFolder().getPath());
        this.configFile = copyFile(pluginFolder, "config.yml");
        this.languageFile = copyFile(pluginFolder, "language.yml");
        this.levelsFile = copyFile(pluginFolder, "levels.yml");
        this.executeFile = copyFile(pluginFolder, "execute.yml");
        loadConfig();
        loadLanguage();
        loadLevels();
        loadExecute();
    }

    public void loadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void loadLanguage() {
        language = YamlConfiguration.loadConfiguration(languageFile);
    }

    public void loadLevels() {
        levels = YamlConfiguration.loadConfiguration(levelsFile);
    }

    public void loadExecute() {
        execute = YamlConfiguration.loadConfiguration(executeFile);
    }

    public void saveLevels() {
        try {
            levels.save(levelsFile);
        } catch (IOException e) {
            Utils.exception(e.getStackTrace(), e.getMessage());
        }
    }

    public void saveExecute() {
        try {
            execute.save(executeFile);
        } catch (IOException e) {
            Utils.exception(e.getStackTrace(), e.getMessage());
        }
    }

    public File getFolder(final String path) {
        final File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public File copyFile(final File folder, final String fileName) {
        final File file = new File(folder, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                try {
                    ByteStreams.copy(plugin.getResource(fileName), new FileOutputStream(file));
                } catch (NullPointerException e) {
                    Utils.info("cant find: " + fileName);
                }
            } catch (IOException exception) {
                Utils.error("Could not create file " + fileName);
            }
        }
        return file;
    }
}
