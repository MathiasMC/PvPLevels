package me.MathiasMC.PvPLevels.files;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
        update();
    }

    public void load() {
        get = YamlConfiguration.loadConfiguration(file);
    }

    private void update() {
        boolean change = false;
        if (!get.contains("player.pvpadmin.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvpadmin <player>");
            get.set("player.pvpadmin.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpadmin.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvpadmin <player>");
            get.set("console.pvpadmin.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpadmin.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvpadmin.online", list);
            change = true;
        }
        if (!get.contains("console.pvpadmin.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvpadmin.online", list);
            change = true;
        }
        if (!get.contains("player.pvpadmin.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvpadmin.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpadmin.target.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvpadmin.target.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpadmin.commands")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("pvplevels gui open admin.yml {pvplevels_player}");
            get.set("player.pvpadmin.commands", list);
            change = true;
        }
        if (!get.contains("console.pvpadmin.commands")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("pvplevels gui open admin.yml {pvplevels_player}");
            get.set("console.pvpadmin.commands", list);
            change = true;
        }
        if (!get.contains("player.pvpadmin.deleted-commands")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("pvplevels message {pvplevels_player} &7[&bPvPLevels&7] &eDeleted player: {pvplevels_target}");
            list.add("pvplevels gui open admin.yml {pvplevels_player}");
            get.set("player.pvpadmin.deleted-commands", list);
            change = true;
        }
        if (!get.contains("player.pvpadmin.you")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou cannot delete your own profile!");
            get.set("player.pvpadmin.you", list);
            change = true;
        }
        if (change) {
            try {
                get.save(file);
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        }
    }
}