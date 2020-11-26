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

    private final File profilesFile;
    public FileConfiguration profiles;

    private final File adminFile;
    public FileConfiguration admin;

    public FileUtils(final PvPLevels plugin) {
        this.plugin = plugin;
        final File pluginFolder = getFolder(plugin.getDataFolder().getPath());
        final File guiFolder = getFolder(pluginFolder + File.separator + "gui");
        this.configFile = copyFile(pluginFolder, "config.yml", "config.yml");
        this.languageFile = copyFile(pluginFolder, "language.yml", "language.yml");
        this.levelsFile = copyFile(pluginFolder, "levels.yml", "levels.yml");
        this.executeFile = copyFile(pluginFolder, "execute.yml", "execute.yml");
        if (!plugin.versionID()) {
            this.profilesFile = copyFile(guiFolder, "profiles.yml", "gui/profiles.yml");
            this.adminFile = copyFile(guiFolder, "admin.yml", "gui/admin.yml");
        } else {
            this.profilesFile = copyFile(guiFolder, "profiles.yml", "old/gui/profiles.yml");
            this.adminFile = copyFile(guiFolder, "admin.yml", "old/gui/admin.yml");
        }
        loadConfig();
        loadLanguage();
        loadLevels();
        loadExecute();
        loadProfiles();
        loadAdmin();
        initialize();
    }

    private void initialize() {
        plugin.guiFiles.put("admin", YamlConfiguration.loadConfiguration(adminFile));
        plugin.guiFiles.put("profiles", YamlConfiguration.loadConfiguration(profilesFile));
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

    public void loadProfiles() {
        profiles = YamlConfiguration.loadConfiguration(profilesFile);
    }

    public void loadAdmin() {
        admin = YamlConfiguration.loadConfiguration(adminFile);
    }

    public void saveLevels() {
        try {
            levels.save(levelsFile);
        } catch (IOException e) {
            plugin.getTextUtils().exception(e.getStackTrace(), e.getMessage());
        }
    }

    public void saveExecute() {
        try {
            execute.save(executeFile);
        } catch (IOException e) {
            plugin.getTextUtils().exception(e.getStackTrace(), e.getMessage());
        }
    }

    public File getFolder(final String path) {
        final File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public File copyFile(final File folder, final String fileName, final String filePath) {
        final File file = new File(folder, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                try {
                    ByteStreams.copy(plugin.getResource(filePath), new FileOutputStream(file));
                } catch (NullPointerException e) {
                    plugin.getTextUtils().info("cant find: " + fileName);
                }
            } catch (IOException exception) {
                plugin.getTextUtils().error("Could not create file " + fileName);
            }
        }
        return file;
    }
}
