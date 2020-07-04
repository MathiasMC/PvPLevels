package me.MathiasMC.PvPLevels.files;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.gui.GUI;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUIFolder {

    private final PvPLevels plugin;

    ArrayList<File> files = new ArrayList<>();

    public GUIFolder(final PvPLevels plugin) {
        this.plugin = plugin;
        File folder = new File(plugin.getDataFolder() + File.separator + "gui");
        if (!folder.exists()) {
            folder.mkdir();
            File boosters = new File(folder, "boosters.yml");
            File globalboosters = new File(folder, "globalBoosters.yml");
            File personalboosters = new File(folder, "personalBoosters.yml");
            File profile = new File(folder, "profile.yml");
            File profileall = new File(folder, "profileAll.yml");
            File admin = new File(folder, "admin.yml");
            File shop = new File(folder, "shop.yml");
            File shop5 = new File(folder, "shop5.yml");
            File shop10 = new File(folder, "shop10.yml");
            try {
                boosters.createNewFile();
                globalboosters.createNewFile();
                personalboosters.createNewFile();
                profile.createNewFile();
                profileall.createNewFile();
                admin.createNewFile();
                shop.createNewFile();
                shop5.createNewFile();
                shop10.createNewFile();
                if (!plugin.versionID()) {
                    plugin.copy("gui/boosters.yml", boosters);
                    plugin.copy("gui/globalBoosters.yml", globalboosters);
                    plugin.copy("gui/personalBoosters.yml", personalboosters);
                    plugin.copy("gui/profile.yml", profile);
                    plugin.copy("gui/profileAll.yml", profileall);
                    plugin.copy("gui/admin.yml", admin);
                    plugin.copy("gui/shop.yml", shop);
                    plugin.copy("gui/shop5.yml", shop5);
                    plugin.copy("gui/shop10.yml", shop10);
                } else {
                    plugin.copy("old/gui/boosters.yml", boosters);
                    plugin.copy("old/gui/globalBoosters.yml", globalboosters);
                    plugin.copy("old/gui/personalBoosters.yml", personalboosters);
                    plugin.copy("old/gui/profile.yml", profile);
                    plugin.copy("old/gui/profileAll.yml", profileall);
                    plugin.copy("old/gui/admin.yml", admin);
                    plugin.copy("old/gui/shop.yml", shop);
                    plugin.copy("old/gui/shop5.yml", shop5);
                    plugin.copy("old/gui/shop10.yml", shop10);
                }
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        }
        for (File file : folder.listFiles()) {
            String fileName = file.getName();
            if (fileName.endsWith(".yml")) {
                files.add(file);
                plugin.guiList.put(fileName, new GUI(plugin, YamlConfiguration.loadConfiguration(file)));
                plugin.textUtils.info("[GUI] " + fileName + " ( Loaded )");
            } else {
                plugin.textUtils.error("[GUI] " + fileName + " ( Error Loading )");
            }
        }
    }

    public void load() {
        for (File file : files) {
            plugin.guiList.put(file.getName(), new GUI(plugin, YamlConfiguration.loadConfiguration(file)));
        }
    }
}
