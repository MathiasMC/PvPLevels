package me.MathiasMC.PvPLevels.files;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class GUIFolder {

    private final PvPLevels plugin;

    final File admin;
    final File profiles;

    public GUIFolder(final PvPLevels plugin) {
        this.plugin = plugin;
        File folder = new File(plugin.getDataFolder() + File.separator + "gui");
        if (!folder.exists()) {
            folder.mkdir();
        }
        admin = new File(folder, "admin.yml");
        if (!admin.exists()) {
            try {
                admin.createNewFile();
                if (!plugin.versionID()) {
                    plugin.copy("gui/admin.yml", admin);
                } else {
                    plugin.copy("old/gui/admin.yml", admin);
                }
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        }
        profiles = new File(folder, "profiles.yml");
        if (!profiles.exists()) {
            try {
                profiles.createNewFile();
                if (!plugin.versionID()) {
                    plugin.copy("gui/profiles.yml", profiles);
                } else {
                    plugin.copy("old/gui/profiles.yml", profiles);
                }
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        }
        load();
    }

    public void load() {
        plugin.guiFiles.put("admin", YamlConfiguration.loadConfiguration(admin));
        plugin.guiFiles.put("profiles", YamlConfiguration.loadConfiguration(profiles));
    }
}