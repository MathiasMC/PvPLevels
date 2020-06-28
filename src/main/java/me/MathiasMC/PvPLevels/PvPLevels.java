package me.MathiasMC.PvPLevels;

import me.MathiasMC.PvPLevels.commands.*;
import me.MathiasMC.PvPLevels.data.Database;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.files.*;
import me.MathiasMC.PvPLevels.gui.GUI;
import me.MathiasMC.PvPLevels.listeners.*;
import me.MathiasMC.PvPLevels.managers.*;
import me.MathiasMC.PvPLevels.placeholders.PlaceholderAPI;
import me.MathiasMC.PvPLevels.utils.KillSessionUtils;
import me.MathiasMC.PvPLevels.utils.Metrics;
import me.MathiasMC.PvPLevels.utils.TextUtils;
import me.MathiasMC.PvPLevels.utils.UpdateUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.*;

public class PvPLevels extends JavaPlugin {

    public static PvPLevels call;

    public Config config;
    public Language language;
    public Levels levels;
    public Boosters boosters;
    public TextUtils textUtils;
    public XPManager xpManager;
    public StatsManager statsManager;
    public SystemManager systemManager;
    public EntityManager entityManager;
    public KillSessionUtils killSessionUtils;
    public BoostersManager boostersManager;
    public Database database;
    public final ConsoleCommandSender consoleCommandSender = Bukkit.getServer().getConsoleSender();
    private final Map<String, PlayerConnect> playerConnect = new HashMap<>();
    public final ArrayList<Location> blocksList = new ArrayList<>();
    public final HashSet<String> spawners = new HashSet<>();
    public final HashMap<String, GUI> guiList = new HashMap<>();
    public HashMap<String, Integer> guiPageID = new HashMap<>();

    public void onEnable() {
        call = this;
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        textUtils = new TextUtils(this);
        xpManager = new XPManager(this);
        statsManager = new StatsManager(this);
        systemManager = new SystemManager(this);
        entityManager = new EntityManager(this);
        killSessionUtils = new KillSessionUtils(this);
        boostersManager = new BoostersManager(this);
        config = new Config(this);
        language = new Language(this);
        levels = new Levels(this);
        boosters = new Boosters(this);
        database = new Database(this);
        new GUIFolder(this);
        if (database.set()) {
            textUtils.info("Database ( Connected )");
            if (config.get.getBoolean("load-players.reload")) { database.loadOnline(); }
            if (config.get.getBoolean("load-players.all")) { database.loadALL(); }
            getServer().getPluginManager().registerEvents(new EntityDeath(this), this);
            getServer().getPluginManager().registerEvents(new PlayerLogin(this), this);
            getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
            getServer().getPluginManager().registerEvents(new InventoryClick(this), this);
            getServer().getPluginManager().registerEvents(new InventoryClose(this), this);
            if (config.get.getBoolean("events.CreatureSpawn")) { getServer().getPluginManager().registerEvents(new CreatureSpawn(this), this); }
            if (config.get.getBoolean("events.PlayerJoin")) { getServer().getPluginManager().registerEvents(new PlayerJoin(this), this); }
            if (config.get.getBoolean("events.PlayerRespawn")) { getServer().getPluginManager().registerEvents(new PlayerRespawn(this), this); }
            if (config.get.getBoolean("events.BlockPlace")) { getServer().getPluginManager().registerEvents(new BlockPlace(this), this); }
            if (config.get.getBoolean("events.BlockBreak")) { getServer().getPluginManager().registerEvents(new BlockBreak(this), this); }
            getCommand("pvplevels").setExecutor(new PvPLevels_Command(this));
            getCommand("pvpstats").setExecutor(new PvPStats_Command(this));
            getCommand("pvptop").setExecutor(new PvPTop_Command(this));
            getCommand("pvpboosters").setExecutor(new PvPBoosters_Command(this));
            getCommand("pvpprofile").setExecutor(new PvPProfile_Command(this));
            placeholders();
            if (config.get.getBoolean("update-check")) {
                new UpdateUtils(this, 20807).getVersion(version -> {
                    if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                        textUtils.info("You are using the latest version of PvPLevels (" + PvPLevels.call.getDescription().getVersion() + ")");
                    } else {
                        textUtils.warning("Version: " + version + " has been released! you are currently using version: " + PvPLevels.call.getDescription().getVersion());
                    }
                });
            }
            if (config.get.getBoolean("save.use")) { systemManager.saveSchedule(); }
            int pluginId = 1174;
            Metrics metrics = new Metrics(this, pluginId);
            metrics.addCustomChart(new Metrics.SimplePie("levels", () -> String.valueOf(levels.get.getConfigurationSection("levels").getKeys(false).size())));
        } else {
            textUtils.error("Disabling plugin cannot connect to database");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public void onDisable() {
        try {
            database.close();
        } catch (SQLException exception) {
            textUtils.exception(exception.getStackTrace(), exception.getMessage());
        }
        call = null;
    }

    public void load(String uuid) {
        PlayerConnect data = new PlayerConnect(uuid);
        playerConnect.put(uuid, data);
    }

    public void unload(String uuid) {
        PlayerConnect data = playerConnect.remove(uuid);
        if (data != null) {
            data.save();
        }
    }

    public PlayerConnect get(String uuid) {
        return playerConnect.get(uuid);
    }

    public Set<String> list() {
        return playerConnect.keySet();
    }

    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private void placeholders() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPI(this).register();
            textUtils.info("PlaceholderAPI (found) adding placeholders");
        }
    }

    public String PlaceholderReplace(Player player, String message) {
        message = replacePlaceholders(message, player.getUniqueId().toString(), player.getName(), statsManager.group(player), statsManager.group_to(player));
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            message = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, message);
        }
        return message;
    }

    public String OfflinePlaceholderReplace(OfflinePlayer offlinePlayer, String message) {
        message = replacePlaceholders(message, offlinePlayer.getUniqueId().toString(), offlinePlayer.getName(), "", "");
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            message = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(offlinePlayer, message);
        }
        return message;
    }

    private String replacePlaceholders(String message, String uuid, String name, String group, String group_to) {
        PlayerConnect playerConnect = get(uuid);
        message = message
                .replace("{pvplevels_player}", name)
                .replace("{pvplevels_kills}", String.valueOf(playerConnect.kills()))
                .replace("{pvplevels_deaths}", String.valueOf(playerConnect.deaths()))
                .replace("{pvplevels_xp}", String.valueOf(playerConnect.xp()))
                .replace("{pvplevels_xp_required}", String.valueOf(statsManager.xp_required(uuid)))
                .replace("{pvplevels_xp_progress}", String.valueOf(statsManager.xp_progress(uuid)))
                .replace("{pvplevels_xp_progress_style}", String.valueOf(statsManager.xp_progress_style(uuid)))
                .replace("{pvplevels_level}", String.valueOf(playerConnect.level()))
                .replace("{pvplevels_level_to}", String.valueOf(playerConnect.level() + 1))
                .replace("{pvplevels_kdr}", statsManager.kdr(uuid))
                .replace("{pvplevels_kill_factor}", statsManager.kill_factor(uuid))
                .replace("{pvplevels_group}", group)
                .replace("{pvplevels_group_to}", group_to)
                .replace("{pvplevels_killstreak}", String.valueOf(playerConnect.killstreak()));
        return message;
    }

    public boolean versionID() {
        if (getServer().getVersion().contains("1.8")) { return true; }
        if (getServer().getVersion().contains("1.9")) { return true; }
        if (getServer().getVersion().contains("1.10")) { return true; }
        if (getServer().getVersion().contains("1.11")) { return true; }
        if (getServer().getVersion().contains("1.12")) { return true; }
        return false;
    }
}