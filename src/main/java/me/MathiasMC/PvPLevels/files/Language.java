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
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        }
        load();
        update();
    }

    public void load() {
        get = YamlConfiguration.loadConfiguration(file);
    }

    private void update() {
        boolean change = false;
        plugin.textUtils.fileHeader(get);
        if (!get.contains("player.pvplevels.command.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.command.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.command.unknown")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUnknown sub command &f{pvplevels_command}");
            get.set("player.pvplevels.command.unknown", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.command.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7&l> &bPvPLevels created by &eMathiasMC");
            list.add("&7&l> &bVersion: &e{pvplevels_version}");
            list.add("&7&l> &f/pvplevels help for list of commands");
            list.add("&7&l> &2Any ideas for the plugin or need help?");
            list.add("&7&l> &2Contact me on spigot");
            list.add("&c&m---------------------------------------------");
            get.set("player.pvplevels.command.message", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.command.unknown")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUnknown sub command &f{pvplevels_command}");
            get.set("console.pvplevels.command.unknown", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.command.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7&l> &bPvPLevels created by &eMathiasMC");
            list.add("&7&l> &bVersion: &e{pvplevels_version}");
            list.add("&7&l> &f/pvplevels help for list of commands");
            list.add("&7&l> &2Any ideas for the plugin or need help?");
            list.add("&7&l> &2Contact me on spigot");
            list.add("&c&m---------------------------------------------");
            get.set("console.pvplevels.command.message", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.help.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.help.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.help.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7&l> &f/pvplevels set/add/remove kills/deaths/xp/level <player> <amount>");
            list.add("&7&l> &f/pvplevels boosters give <player> <global-personal> <booster 0.0> <time>");
            list.add("&7&l> &f/pvplevels gui open <fileName> <player>");
            list.add("&7&l> &f/pvplevels player get <xpType> <player>");
            list.add("&7&l> &f/pvplevels player lose <xpType> <player>");
            list.add("&7&l> &f/pvplevels actionbar <player> <text>");
            list.add("&7&l> &f/pvpboosters");
            list.add("&7&l> &f/pvpprofile");
            list.add("&7&l> &f/pvptop kills/deaths/xp/level");
            list.add("&7&l> &f/pvpstats <player>");
            list.add("&7&l> &f/pvplevels message <player> <text>");
            list.add("&7&l> &f/pvplevels broadcast <null/permission> <text>");
            list.add("&7&l> &f/pvplevels reload");
            list.add("&7&l> &f/pvplevels save");
            list.add("&c&m---------------------------------------------");
            get.set("player.pvplevels.help.message", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.help.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7&l> &f/pvplevels set/add/remove kills/deaths/xp/level <player> <amount>");
            list.add("&7&l> &f/pvplevels boosters give <player> <global-personal> <booster 0.0> <time>");
            list.add("&7&l> &f/pvplevels gui open <fileName> <player>");
            list.add("&7&l> &f/pvplevels player get <xpType> <player>");
            list.add("&7&l> &f/pvplevels player lose <xpType> <player>");
            list.add("&7&l> &f/pvplevels actionbar <player> <text>");
            list.add("&7&l> &f/pvpboosters");
            list.add("&7&l> &f/pvpprofile");
            list.add("&7&l> &f/pvptop kills/deaths/xp/level <player>");
            list.add("&7&l> &f/pvpstats <player>");
            list.add("&7&l> &f/pvplevels generate <levels>");
            list.add("&7&l> &f/pvplevels message <player> <text>");
            list.add("&7&l> &f/pvplevels broadcast <null/permission> <text>");
            list.add("&7&l> &f/pvplevels reload");
            list.add("&7&l> &f/pvplevels save");
            list.add("&c&m---------------------------------------------");
            get.set("console.pvplevels.help.message", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.reload.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.reload.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.reload.reloaded")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &aReloaded all configs!");
            get.set("player.pvplevels.reload.reloaded", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.reload.reloaded")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &aReloaded all configs!");
            get.set("console.pvplevels.reload.reloaded", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.broadcast.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.broadcast.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.broadcast.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels broadcast <null/permission> <text>");
            get.set("player.pvplevels.broadcast.usage", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.broadcast.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels broadcast <null/permission> <text>");
            get.set("console.pvplevels.broadcast.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.message.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.message.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.message.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels message <player> <text>");
            get.set("player.pvplevels.message.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.message.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvplevels.message.online", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.message.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels message <player> <text>");
            get.set("console.pvplevels.message.usage", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.message.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvplevels.message.online", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.save.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.save.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.save.saved")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &aSaved cached data to the database!");
            get.set("player.pvplevels.save.saved", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.save.saved")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &aSaved cached data to the database!");
            get.set("console.pvplevels.save.saved", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.set.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.set.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.add.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.add.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.remove.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.remove.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.set.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels set kills/deaths/xp/level <player> <set>");
            get.set("player.pvplevels.set.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.add.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels add kills/deaths/xp/level <player> <add>");
            get.set("player.pvplevels.add.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.remove.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels remove kills/deaths/xp/level <player> <remove>");
            get.set("player.pvplevels.remove.usage", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.set.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels set kills/deaths/xp/level <player> <set>");
            get.set("console.pvplevels.set.usage", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.add.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels add kills/deaths/xp/level <player> <add>");
            get.set("console.pvplevels.add.usage", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.remove.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels remove kills/deaths/xp/level <player> <remove>");
            get.set("console.pvplevels.remove.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.set.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvplevels.set.online", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.add.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvplevels.add.online", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.remove.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvplevels.remove.online", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.set.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvplevels.set.online", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.add.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvplevels.add.online", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.remove.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvplevels.remove.online", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.set.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a number!");
            get.set("player.pvplevels.set.number", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.add.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a number!");
            get.set("player.pvplevels.add.number", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.remove.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a number!");
            get.set("player.pvplevels.remove.number", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.set.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a number!");
            get.set("console.pvplevels.set.number", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.add.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a number!");
            get.set("console.pvplevels.add.number", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.remove.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a number!");
            get.set("console.pvplevels.remove.number", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.set.kills")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eChanged {pvplevels_player} kills to {pvplevels_set}!");
            get.set("player.pvplevels.set.kills", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.set.deaths")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eChanged {pvplevels_player} deaths to {pvplevels_set}!");
            get.set("player.pvplevels.set.deaths", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.set.xp")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eChanged {pvplevels_player} xp to {pvplevels_set}!");
            get.set("player.pvplevels.set.xp", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.set.level")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eChanged {pvplevels_player} level to {pvplevels_set}!");
            get.set("player.pvplevels.set.level", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.set.kills")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eYour kills is now changed to {pvplevels_set}!");
            get.set("console.pvplevels.set.kills", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.set.deaths")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eYour deaths is now changed to {pvplevels_set}!");
            get.set("console.pvplevels.set.deaths", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.set.xp")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eYour xp is now changed to {pvplevels_set}!");
            get.set("console.pvplevels.set.xp", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.set.level")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eYour level is now changed to {pvplevels_set}!");
            get.set("console.pvplevels.set.level", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.add.kills")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eAdded {pvplevels_add} kills to {pvplevels_player}!");
            get.set("player.pvplevels.add.kills", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.add.deaths")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eAdded {pvplevels_add} deaths to {pvplevels_player}!");
            get.set("player.pvplevels.add.deaths", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.add.xp")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eAdded {pvplevels_add} xp to {pvplevels_player}!");
            get.set("player.pvplevels.add.xp", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.add.level")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eAdded {pvplevels_add} level to {pvplevels_player}!");
            get.set("player.pvplevels.add.level", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.add.kills")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eAdded {pvplevels_add} kills!");
            get.set("console.pvplevels.add.kills", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.add.deaths")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eAdded {pvplevels_add} deaths!");
            get.set("console.pvplevels.add.deaths", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.add.xp")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eAdded {pvplevels_add} xp!");
            get.set("console.pvplevels.add.xp", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.add.level")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eAdded {pvplevels_add} level!");
            get.set("console.pvplevels.add.level", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.remove.kills")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eRemoved {pvplevels_remove} kills from {pvplevels_player}!");
            get.set("player.pvplevels.remove.kills", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.remove.deaths")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eRemoved {pvplevels_remove} deaths from {pvplevels_player}!");
            get.set("player.pvplevels.remove.deaths", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.remove.xp")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eRemoved {pvplevels_remove} xp from {pvplevels_player}!");
            get.set("player.pvplevels.remove.xp", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.remove.level")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eRemoved {pvplevels_remove} level from {pvplevels_player}!");
            get.set("player.pvplevels.remove.level", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.remove.kills")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eRemoved {pvplevels_remove} kills!");
            get.set("console.pvplevels.remove.kills", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.remove.deaths")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eRemoved {pvplevels_remove} deaths!");
            get.set("console.pvplevels.remove.deaths", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.remove.xp")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eRemoved {pvplevels_remove} xp!");
            get.set("console.pvplevels.remove.xp", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.remove.level")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eRemoved {pvplevels_remove} level!");
            get.set("console.pvplevels.remove.level", list);
            change = true;
        }
        if (!get.contains("player.pvpstats.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvpstats <player>");
            get.set("player.pvpstats.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpstats.you.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvpstats.you.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpstats.you.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lPvPStats&7]");
            list.add("&7&l> &eName: &6{pvplevels_player}");
            list.add("&7&l> &eKills: &6{pvplevels_kills}");
            list.add("&7&l> &eDeaths: &6{pvplevels_deaths}");
            list.add("&7&l> &eXP: &6{pvplevels_xp}");
            list.add("&7&l> &eLevel: &6{pvplevels_level}");
            list.add("&7&l> &eKDR: &6{pvplevels_kdr}");
            list.add("&7&l> &eRequired XP: &6{pvplevels_xp_required}");
            list.add("&7&l> &eProgress: {pvplevels_xp_progress_style} &6{pvplevels_xp_progress}%");
            list.add("&7&l> &eGroup: &6{pvplevels_group}/{pvplevels_group_to}");
            list.add("&c&m---------------------------------------------");
            get.set("player.pvpstats.you.message", list);
            change = true;
        }
        if (!get.contains("player.pvpstats.target.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvpstats.target.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpstats.target.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvpstats.target.online", list);
            change = true;
        }
        if (!get.contains("player.pvpstats.target.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lPvPStats&7]");
            list.add("&7&l> &eName: &6{pvplevels_player}");
            list.add("&7&l> &eKills: &6{pvplevels_kills}");
            list.add("&7&l> &eDeaths: &6{pvplevels_deaths}");
            list.add("&7&l> &eXP: &6{pvplevels_xp}");
            list.add("&7&l> &eLevel: &6{pvplevels_level}");
            list.add("&7&l> &eKDR: &6{pvplevels_kdr}");
            list.add("&7&l> &eRequired XP: &6{pvplevels_xp_required}");
            list.add("&7&l> &eProgress: {pvplevels_xp_progress_style} &6{pvplevels_xp_progress}%");
            list.add("&7&l> &eGroup: &6{pvplevels_group}/{pvplevels_group_to}");
            list.add("&c&m---------------------------------------------");
            get.set("player.pvpstats.target.message", list);
            change = true;
        }
        if (!get.contains("console.pvpstats.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvpstats <player>");
            get.set("console.pvpstats.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpstats.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvpstats.online", list);
            change = true;
        }
        if (!get.contains("console.pvpstats.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lPvPStats&7]");
            list.add("&7&l> &eName: &6{pvplevels_player}");
            list.add("&7&l> &eKills: &6{pvplevels_kills}");
            list.add("&7&l> &eDeaths: &6{pvplevels_deaths}");
            list.add("&7&l> &eXP: &6{pvplevels_xp}");
            list.add("&7&l> &eRequired XP: &6{pvplevels_xp_required}");
            list.add("&7&l> &eProgress: {pvplevels_xp_progress_style}");
            list.add("&7&l> &eLevel: &6{pvplevels_level}");
            list.add("&7&l> &eKDR: &6{pvplevels_kdr}");
            list.add("&7&l> &eKillStreak: &6{pvplevels_killstreak}");
            list.add("&c&m---------------------------------------------");
            get.set("console.pvpstats.message", list);
            change = true;
        }
        if (!get.contains("player.pvptop.kills.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lTop Kills&7]");
            list.add("&7[&a1&7] &e%pvplevels_top_1_kills_name% &6 > &b%pvplevels_top_1_kills%");
            list.add("&7[&a2&7] &e%pvplevels_top_2_kills_name% &6 > &b%pvplevels_top_2_kills%");
            list.add("&7[&a3&7] &e%pvplevels_top_3_kills_name% &6 > &b%pvplevels_top_3_kills%");
            list.add("&7[&a4&7] &e%pvplevels_top_4_kills_name% &6 > &b%pvplevels_top_4_kills%");
            list.add("&7[&a5&7] &e%pvplevels_top_5_kills_name% &6 > &b%pvplevels_top_5_kills%");
            list.add("&c&m---------------------------------------------");
            get.set("player.pvptop.kills.message", list);
            change = true;
        }
        if (!get.contains("player.pvptop.deaths.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lTop Deaths&7]");
            list.add("&7[&a1&7] &e%pvplevels_top_1_deaths_name% &6 > &b%pvplevels_top_1_deaths%");
            list.add("&7[&a2&7] &e%pvplevels_top_2_deaths_name% &6 > &b%pvplevels_top_2_deaths%");
            list.add("&7[&a3&7] &e%pvplevels_top_3_deaths_name% &6 > &b%pvplevels_top_3_deaths%");
            list.add("&7[&a4&7] &e%pvplevels_top_4_deaths_name% &6 > &b%pvplevels_top_4_deaths%");
            list.add("&7[&a5&7] &e%pvplevels_top_5_deaths_name% &6 > &b%pvplevels_top_5_deaths%");
            list.add("&c&m---------------------------------------------");
            get.set("player.pvptop.deaths.message", list);
            change = true;
        }
        if (!get.contains("player.pvptop.xp.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lTop XP&7]");
            list.add("&7[&a1&7] &e%pvplevels_top_1_xp_name% &6 > &b%pvplevels_top_1_xp%");
            list.add("&7[&a2&7] &e%pvplevels_top_2_xp_name% &6 > &b%pvplevels_top_2_xp%");
            list.add("&7[&a3&7] &e%pvplevels_top_3_xp_name% &6 > &b%pvplevels_top_3_xp%");
            list.add("&7[&a4&7] &e%pvplevels_top_4_xp_name% &6 > &b%pvplevels_top_4_xp%");
            list.add("&7[&a5&7] &e%pvplevels_top_5_xp_name% &6 > &b%pvplevels_top_5_xp%");
            list.add("&c&m---------------------------------------------");
            get.set("player.pvptop.xp.message", list);
            change = true;
        }
        if (!get.contains("player.pvptop.level.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lTop Level&7]");
            list.add("&7[&a1&7] &e%pvplevels_top_1_level_name% &6 > &b%pvplevels_top_1_level%");
            list.add("&7[&a2&7] &e%pvplevels_top_2_level_name% &6 > &b%pvplevels_top_2_level%");
            list.add("&7[&a3&7] &e%pvplevels_top_3_level_name% &6 > &b%pvplevels_top_3_level%");
            list.add("&7[&a4&7] &e%pvplevels_top_4_level_name% &6 > &b%pvplevels_top_4_level%");
            list.add("&7[&a5&7] &e%pvplevels_top_5_level_name% &6 > &b%pvplevels_top_5_level%");
            list.add("&c&m---------------------------------------------");
            get.set("player.pvptop.level.message", list);
            change = true;
        }
        if (!get.contains("console.pvptop.kills.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lTop Kills&7]");
            list.add("&7[&a1&7] &e%pvplevels_top_1_kills_name% &6 > &b%pvplevels_top_1_kills%");
            list.add("&7[&a2&7] &e%pvplevels_top_2_kills_name% &6 > &b%pvplevels_top_2_kills%");
            list.add("&7[&a3&7] &e%pvplevels_top_3_kills_name% &6 > &b%pvplevels_top_3_kills%");
            list.add("&7[&a4&7] &e%pvplevels_top_4_kills_name% &6 > &b%pvplevels_top_4_kills%");
            list.add("&7[&a5&7] &e%pvplevels_top_5_kills_name% &6 > &b%pvplevels_top_5_kills%");
            list.add("&c&m---------------------------------------------");
            get.set("console.pvptop.kills.message", list);
            change = true;
        }
        if (!get.contains("console.pvptop.deaths.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lTop Deaths&7]");
            list.add("&7[&a1&7] &e%pvplevels_top_1_deaths_name% &6 > &b%pvplevels_top_1_deaths%");
            list.add("&7[&a2&7] &e%pvplevels_top_2_deaths_name% &6 > &b%pvplevels_top_2_deaths%");
            list.add("&7[&a3&7] &e%pvplevels_top_3_deaths_name% &6 > &b%pvplevels_top_3_deaths%");
            list.add("&7[&a4&7] &e%pvplevels_top_4_deaths_name% &6 > &b%pvplevels_top_4_deaths%");
            list.add("&7[&a5&7] &e%pvplevels_top_5_deaths_name% &6 > &b%pvplevels_top_5_deaths%");
            list.add("&c&m---------------------------------------------");
            get.set("console.pvptop.deaths.message", list);
            change = true;
        }
        if (!get.contains("console.pvptop.xp.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lTop XP&7]");
            list.add("&7[&a1&7] &e%pvplevels_top_1_xp_name% &6 > &b%pvplevels_top_1_xp%");
            list.add("&7[&a2&7] &e%pvplevels_top_2_xp_name% &6 > &b%pvplevels_top_2_xp%");
            list.add("&7[&a3&7] &e%pvplevels_top_3_xp_name% &6 > &b%pvplevels_top_3_xp%");
            list.add("&7[&a4&7] &e%pvplevels_top_4_xp_name% &6 > &b%pvplevels_top_4_xp%");
            list.add("&7[&a5&7] &e%pvplevels_top_5_xp_name% &6 > &b%pvplevels_top_5_xp%");
            list.add("&c&m---------------------------------------------");
            get.set("console.pvptop.xp.message", list);
            change = true;
        }
        if (!get.contains("console.pvptop.level.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7[&b&lTop Level&7]");
            list.add("&7[&a1&7] &e%pvplevels_top_1_level_name% &6 > &b%pvplevels_top_1_level%");
            list.add("&7[&a2&7] &e%pvplevels_top_2_level_name% &6 > &b%pvplevels_top_2_level%");
            list.add("&7[&a3&7] &e%pvplevels_top_3_level_name% &6 > &b%pvplevels_top_3_level%");
            list.add("&7[&a4&7] &e%pvplevels_top_4_level_name% &6 > &b%pvplevels_top_4_level%");
            list.add("&7[&a5&7] &e%pvplevels_top_5_level_name% &6 > &b%pvplevels_top_5_level%");
            list.add("&c&m---------------------------------------------");
            get.set("console.pvptop.level.message", list);
            change = true;
        }
        if (!get.contains("player.pvptop.kills.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvptop.kills.permission", list);
            change = true;
        }
        if (!get.contains("player.pvptop.deaths.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvptop.deaths.permission", list);
            change = true;
        }
        if (!get.contains("player.pvptop.xp.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvptop.xp.permission", list);
            change = true;
        }
        if (!get.contains("player.pvptop.level.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvptop.level.permission", list);
            change = true;
        }
        if (!get.contains("player.pvptop.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvptop kills/deaths/xp/level");
            get.set("player.pvptop.usage", list);
            change = true;
        }
        if (!get.contains("player.pvptop.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvptop.permission", list);
            change = true;
        }
        if (!get.contains("console.pvptop.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvptop kills/deaths/xp/level <player>");
            get.set("console.pvptop.usage", list);
            change = true;
        }
        if (!get.contains("console.pvptop.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvptop.online", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.generate.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels generate <levels>");
            get.set("console.pvplevels.generate.usage", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.generate.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a number!");
            get.set("console.pvplevels.generate.number", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.generate.disable")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe generator is disabled");
            get.set("console.pvplevels.generate.disable", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.generate.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eGenerated {pvplevels_levels} levels!");
            get.set("console.pvplevels.generate.message", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.boosters.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels boosters give");
            get.set("player.pvplevels.boosters.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.boosters.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.boosters.permission", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.boosters.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels boosters give");
            get.set("console.pvplevels.boosters.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.boosters.give.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels boosters give <player> <global-personal> <booster 0.0> <time>");
            get.set("player.pvplevels.boosters.give.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.boosters.give.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.boosters.give.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.boosters.give.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a number!");
            get.set("player.pvplevels.boosters.give.number", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.boosters.give.booster")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a valid booster!");
            get.set("player.pvplevels.boosters.give.booster", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.boosters.give.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvplevels.boosters.give.online", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.boosters.give.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eYou have given {pvplevels_player_target} &ea global booster &6{pvplevels_booster_global_type} &efor &6{pvplevels_booster_global_time}");
            get.set("player.pvplevels.boosters.give.message", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.boosters.give.target")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eYou have got a global booster &6{pvplevels_booster_global_type} &efor &6{pvplevels_booster_global_time}");
            get.set("player.pvplevels.boosters.give.target", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.boosters.give.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels boosters give <player> <global-personal> <booster 0.0> <time>");
            get.set("console.pvplevels.boosters.give.usage", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.boosters.give.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a number!");
            get.set("console.pvplevels.boosters.give.number", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.boosters.give.booster")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a valid booster!");
            get.set("console.pvplevels.boosters.give.booster", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.boosters.give.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvplevels.boosters.give.online", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.boosters.give.target")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eYou have got a global booster &6{pvplevels_booster_global_type} &efor &6{pvplevels_booster_global_time}");
            get.set("console.pvplevels.boosters.give.target", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.boosters.personal.give.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a number!");
            get.set("player.pvplevels.boosters.personal.give.number", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.boosters.personal.give.booster")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a valid booster!");
            get.set("player.pvplevels.boosters.personal.give.booster", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.boosters.personal.give.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eYou have given {pvplevels_player_target} &ea personal booster &6{pvplevels_booster_personal_type} &efor &6{pvplevels_booster_personal_time}");
            get.set("player.pvplevels.boosters.personal.give.message", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.boosters.personal.give.target")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eYou have got a personal booster &6{pvplevels_booster_personal_type} &efor &6{pvplevels_booster_personal_time}");
            get.set("player.pvplevels.boosters.personal.give.target", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.boosters.personal.give.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a number!");
            get.set("console.pvplevels.boosters.personal.give.number", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.boosters.personal.give.booster")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cNot a valid booster!");
            get.set("console.pvplevels.boosters.personal.give.booster", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.boosters.personal.give.target")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &eYou have got a personal booster &6{pvplevels_booster_personal_type} &efor &6{pvplevels_booster_personal_time}");
            get.set("console.pvplevels.boosters.personal.give.target", list);
            change = true;
        }
        if (!get.contains("player.pvpboosters.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvpboosters <player>");
            get.set("player.pvpboosters.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpboosters.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvpboosters <player>");
            get.set("console.pvpboosters.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpboosters.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvpboosters.online", list);
            change = true;
        }
        if (!get.contains("console.pvpboosters.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvpboosters.online", list);
            change = true;
        }
        if (!get.contains("player.pvpboosters.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvpboosters.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpboosters.target.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvpboosters.target.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpboosters.commands")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("pvplevels gui open boosters.yml {pvplevels_player}");
            get.set("player.pvpboosters.commands", list);
            change = true;
        }
        if (!get.contains("console.pvpboosters.commands")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("pvplevels gui open boosters.yml {pvplevels_player}");
            get.set("console.pvpboosters.commands", list);
            change = true;
        }
        if (!get.contains("player.pvpprofile.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvpprofile <player>");
            get.set("player.pvpprofile.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpprofile.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvpprofile <player>");
            get.set("console.pvpprofile.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpprofile.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvpprofile.online", list);
            change = true;
        }
        if (!get.contains("console.pvpprofile.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvpprofile.online", list);
            change = true;
        }
        if (!get.contains("player.pvpprofile.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvpprofile.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpprofile.target.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvpprofile.target.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpprofile.commands")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("pvplevels gui open profile.yml {pvplevels_player}");
            get.set("player.pvpprofile.commands", list);
            change = true;
        }
        if (!get.contains("console.pvpprofile.commands")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("pvplevels gui open profile.yml {pvplevels_player}");
            get.set("console.pvpprofile.commands", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.gui.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels gui open");
            get.set("player.pvplevels.gui.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.gui.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.gui.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.gui.open.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.gui.open.permission", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.gui.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels gui open");
            get.set("console.pvplevels.gui.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.gui.open.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels gui open <fileName> <player>");
            get.set("player.pvplevels.gui.open.usage", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.gui.open.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels gui open <fileName> <player>");
            get.set("console.pvplevels.gui.open.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.gui.open.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvplevels.gui.open.online", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.gui.open.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvplevels.gui.open.online", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.gui.open.found")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe file {pvplevels_gui_file} is not found");
            get.set("player.pvplevels.gui.open.found", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.gui.open.found")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe file {pvplevels_gui_file} is not found");
            get.set("console.pvplevels.gui.open.found", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.player.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels player get, lose");
            get.set("player.pvplevels.player.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.player.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.player.permission", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.player.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels player get, lose");
            get.set("console.pvplevels.player.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.player.get.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels player get <xpType> <player>");
            get.set("player.pvplevels.player.get.usage", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.player.get.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels player get <xpType> <player>");
            get.set("console.pvplevels.player.get.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.player.get.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.player.get.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.player.get.config")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cCannot find {pvplevels_xp_type} in the xp section");
            get.set("player.pvplevels.player.get.config", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.player.get.config")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cCannot find {pvplevels_xp_type} in the xp section");
            get.set("console.pvplevels.player.get.config", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.player.get.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvplevels.player.get.online", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.player.get.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvplevels.player.get.online", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.player.lose.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels player lose <xpType> <player>");
            get.set("player.pvplevels.player.lose.usage", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.player.lose.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels player lose <xpType> <player>");
            get.set("console.pvplevels.player.lose.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.player.lose.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.player.lose.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.player.lose.config")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cCannot find {pvplevels_xp_type} in the xp section");
            get.set("player.pvplevels.player.lose.config", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.player.lose.config")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cCannot find {pvplevels_xp_type} in the xp section");
            get.set("console.pvplevels.player.lose.config", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.player.lose.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvplevels.player.lose.online", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.player.lose.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvplevels.player.lose.online", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.set.kills-cannot")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cKills cannot be under 0!");
            get.set("player.pvplevels.set.kills-cannot", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.set.kills-cannot")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cKills cannot be under 0!");
            get.set("console.pvplevels.set.kills-cannot", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.set.deaths-cannot")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cDeaths cannot be under 0!");
            get.set("player.pvplevels.set.deaths-cannot", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.set.deaths-cannot")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cDeaths cannot be under 0!");
            get.set("console.pvplevels.set.deaths-cannot", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.set.xp-add-cannot")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cCannot add xp higher than max level!");
            get.set("player.pvplevels.set.xp-add-cannot", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.set.xp-add-cannot")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cCannot add xp higher than max level!");
            get.set("console.pvplevels.set.xp-add-cannot", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.set.xp-remove-cannot")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cCannot remove more xp already at the lowest level and xp!");
            get.set("player.pvplevels.set.xp-remove-cannot", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.set.xp-remove-cannot")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cCannot remove more xp already at the lowest level and xp!");
            get.set("console.pvplevels.set.xp-remove-cannot", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.set.xp-set-cannot")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cCannot set xp higher than max level!");
            get.set("player.pvplevels.set.xp-set-cannot", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.set.xp-set-cannot")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cCannot set xp higher than max level!");
            get.set("console.pvplevels.set.xp-set-cannot", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.set.level-cannot")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cCannot find that level!");
            get.set("player.pvplevels.set.level-cannot", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.set.level-cannot")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cCannot find that level!");
            get.set("console.pvplevels.set.level-cannot", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.actionbar.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels actionbar <player> <text>");
            get.set("player.pvplevels.actionbar.usage", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.actionbar.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cYou dont have access to use this command!");
            get.set("player.pvplevels.actionbar.permission", list);
            change = true;
        }
        if (!get.contains("player.pvplevels.actionbar.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("player.pvplevels.actionbar.online", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.actionbar.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cUsage: /pvplevels actionbar <player> <text>");
            get.set("console.pvplevels.actionbar.usage", list);
            change = true;
        }
        if (!get.contains("console.pvplevels.actionbar.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&bPvPLevels&7] &cThe player is not online!");
            get.set("console.pvplevels.actionbar.online", list);
            change = true;
        }
        if (change) {
            try {
                get.save(file);
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
            plugin.textUtils.info("language.yml ( A change was made )");
        } else {
            plugin.textUtils.info("language.yml ( Loaded )");
        }
    }
}
