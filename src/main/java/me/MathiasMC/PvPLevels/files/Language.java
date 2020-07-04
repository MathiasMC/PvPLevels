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
        if (!get.contains("player.pvplevels.coins.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.coins.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.coins.set.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.coins.set.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.coins.add.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.coins.add.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.coins.remove.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.coins.remove.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.coins.set.set")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &e{pvplevels_player} coins is now set to {pvplevels_coins_amount}");
            get.set("player.pvplevels.coins.set.set", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.coins.add.added")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eAdded {pvplevels_coins_amount} coins to {pvplevels_player}");
            get.set("player.pvplevels.coins.add.added", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.coins.remove.removed")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eRemoved {pvplevels_coins_amount} coins from {pvplevels_player}");
            get.set("player.pvplevels.coins.remove.removed", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.coins.set")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eYour coins is now set to {pvplevels_coins_amount}");
            get.set("console.pvplevels.coins.set", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.coins.added")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eAdded {pvplevels_coins_amount} coins!");
            get.set("console.pvplevels.coins.added", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.coins.removed")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eRemoved {pvplevels_coins_amount} coins!");
            get.set("console.pvplevels.coins.removed", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.coins.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels coins set/add/remove <player> <amount>");
            get.set("player.pvplevels.coins.usage", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.coins.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels coins set/add/remove <player> <amount>");
            get.set("console.pvplevels.coins.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.coins.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvplevels.coins.online", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.coins.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvplevels.coins.online", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.coins.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a number!");
            get.set("player.pvplevels.coins.number", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.coins.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a number!");
            get.set("console.pvplevels.coins.number", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.coins.0")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe amount must be over 0!");
            get.set("player.pvplevels.coins.0", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.coins.0")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe amount must be over 0!");
            get.set("console.pvplevels.coins.0", list);
            change = true;
        }
        if (!get.contains("player.pvpshop.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvpshop <player>");
            get.set("player.pvpshop.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpshop.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvpshop <player>");
            get.set("console.pvpshop.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpshop.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvpshop.online", list);
            change = true;
        }
        if (!get.contains("console.pvpshop.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvpshop.online", list);
            change = true;
        }
        if (!get.contains("player.pvpshop.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvpshop.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpshop.target.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvpshop.target.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpshop.commands")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("pvplevels gui open shop.yml {pvplevels_player}");
            get.set("player.pvpshop.commands", list);
            change = true;
        }
        if (!get.contains("console.pvpshop.commands")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("pvplevels gui open shop.yml {pvplevels_player}");
            get.set("console.pvpshop.commands", list);
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