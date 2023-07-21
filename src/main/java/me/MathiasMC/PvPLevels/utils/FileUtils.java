package me.MathiasMC.PvPLevels.utils;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;

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

    public FileUtils(PvPLevels plugin) {
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

    public File getFolder(String path) {
        File file = new File(path);
        file.mkdir();
        return file;
    }

    public File copyFile(File folder, String fileName) {
        File file = new File(folder, fileName);
        try {
            if (file.createNewFile()) {
                InputStream input = plugin.getResource(fileName);
                OutputStream output = Files.newOutputStream(file.toPath());
                byte[] buffer = new byte[8192];
                int length;
                while ((length = input.read(buffer)) != -1) {
                    output.write(buffer, 0, length);
                }
                input.close();
                output.close();
            }
        } catch (IOException exception) {
            Utils.error("Could not create file " + fileName);
        }
        return file;
    }
}
