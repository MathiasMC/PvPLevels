package me.MathiasMC.PvPLevels;

import me.MathiasMC.PvPLevels.commands.*;
import me.MathiasMC.PvPLevels.data.Database;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.data.Purge;
import me.MathiasMC.PvPLevels.gui.Menu;
import me.MathiasMC.PvPLevels.listeners.*;
import me.MathiasMC.PvPLevels.managers.*;
import me.MathiasMC.PvPLevels.support.PlaceholderAPI;
import me.MathiasMC.PvPLevels.utils.FileUtils;
import me.MathiasMC.PvPLevels.utils.Metrics;
import me.MathiasMC.PvPLevels.utils.TextUtils;
import me.MathiasMC.PvPLevels.utils.UpdateUtils;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.sql.SQLException;
import java.util.*;

public class PvPLevels extends JavaPlugin {

    private static PvPLevels call;

    public final ConsoleCommandSender consoleSender = Bukkit.getServer().getConsoleSender();

    public Database database;

    private TextUtils textUtils;

    private FileUtils fileUtils;

    private ItemStackManager itemStackManager;
    private CalculateManager calculateManager;
    private PlaceholderManager placeholderManager;
    private KillSessionManager killSessionManager;
    private StatsManager statsManager;
    private XPManager xpManager;

    private final Map<String, PlayerConnect> playerConnect = new HashMap<>();
    private final HashMap<Player, Menu> playerMenu = new HashMap<>();

    public final HashMap<String, FileConfiguration> guiFiles = new HashMap<>();

    public final HashSet<String> spawners = new HashSet<>();

    public final ArrayList<Location> blocksList = new ArrayList<>();

    public final Map<String, String> lastDamagers = new HashMap<>();

    public final HashSet<OfflinePlayer> multipliers = new HashSet<>();

    public String generateGroup = null;

    public long generateAmount = 0;

    public boolean isGenerate = false;

    private final ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private final ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");

    public void onEnable() {
        call = this;

        textUtils = new TextUtils(this);

        fileUtils = new FileUtils(this);

        database = new Database(this);

        itemStackManager = new ItemStackManager(this);
        placeholderManager = new PlaceholderManager(this);
        killSessionManager = new KillSessionManager(this);
        statsManager = new StatsManager(this);
        xpManager = new XPManager(this);
        calculateManager = new CalculateManager(this);

        if (database.set()) {
            getServer().getPluginManager().registerEvents(new PlayerLogin(this), this);
            getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
            getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
            getServer().getPluginManager().registerEvents(new InventoryClick(), this);
            getServer().getPluginManager().registerEvents(new EntityDeath(this), this);
            getServer().getPluginManager().registerEvents(new CreatureSpawn(this), this);
            getServer().getPluginManager().registerEvents(new BlockBreak(this), this);
            getServer().getPluginManager().registerEvents(new BlockPlace(this), this);
            getServer().getPluginManager().registerEvents(new EntityDamageByEntity(this), this);
            getCommand("pvplevels").setExecutor(new PvPLevels_Command(this));
            getCommand("pvplevels").setTabCompleter(new PvPLevels_TabComplete(this));
            int pluginId = 1174;
            final Metrics metrics = new Metrics(this, pluginId);
            metrics.addCustomChart(new Metrics.SimplePie("lite", () -> "No"));
            if (fileUtils.config.getBoolean("update-check")) {
                new UpdateUtils(this, 20807).getVersion(version -> {
                    if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                        textUtils.info("You are using the latest version of PvPLevels (" + getDescription().getVersion() + ")");
                    } else {
                        textUtils.warning("Version: " + version + " has been released! you are currently using version: " + getDescription().getVersion());
                    }
                });
            }
            if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
                new PlaceholderAPI(this).register();
                textUtils.info("PlaceholderAPI (found)");
            }

            if (fileUtils.config.contains("mysql.purge")) {
                new Purge(this);
            }
            if (isLevelsValid()) {
                textUtils.info("Validator ( passed )");
            }

            getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                for (OfflinePlayer offlinePlayer : multipliers) {
                    if (offlinePlayer.isOnline()) {
                        final PlayerConnect playerConnect = getPlayerConnect(offlinePlayer.getUniqueId().toString());
                        int left = playerConnect.getMultiplierTimeLeft();
                        if (left > 0) {
                            left--;
                            playerConnect.setMultiplierTimeLeft(left);
                            return;
                        }
                        for (String message : fileUtils.language.getStringList("multiplier.lost")) {
                            getServer().dispatchCommand(consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", offlinePlayer.getName()).replace("{multiplier}", String.valueOf(playerConnect.getMultiplier()))));
                        }
                        playerConnect.setMultiplier(0D);
                        playerConnect.setMultiplierTime(0);
                        playerConnect.setMultiplierTimeLeft(0);
                        playerConnect.save();
                    }
                    multipliers.remove(offlinePlayer);
                }
            }, 20, 20);

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

    public static PvPLevels getInstance() {
        return call;
    }

    public FileUtils getFileUtils() {
        return this.fileUtils;
    }

    public TextUtils getTextUtils() {
        return this.textUtils;
    }

    public XPManager getXPManager() {
        return this.xpManager;
    }

    public KillSessionManager getKillSessionManager() {
        return this.killSessionManager;
    }

    public StatsManager getStatsManager() {
        return this.statsManager;
    }

    public PlaceholderManager getPlaceholderManager() {
        return this.placeholderManager;
    }

    public CalculateManager getCalculateManager() {
        return this.calculateManager;
    }

    public ItemStackManager getItemStackManager() {
        return this.itemStackManager;
    }

    public void unloadPlayerConnect(final String uuid) {
        playerConnect.remove(uuid);
    }

    public void updatePlayerConnect(final String uuid) {
        unloadPlayerConnect(uuid);
        getPlayerConnect(uuid);
    }

    public ScriptEngine getScriptEngine() {
        return this.scriptEngine;
    }

    public PlayerConnect getPlayerConnect(final String uuid) {
        if (playerConnect.containsKey(uuid)) {
            return playerConnect.get(uuid);
        }
        final PlayerConnect playerConnect = new PlayerConnect(uuid);
        this.playerConnect.put(uuid, playerConnect);
        return playerConnect;
    }

    public Set<String> listPlayerConnect() {
        return playerConnect.keySet();
    }

    public Menu getPlayerMenu(final Player player) {
        Menu playerMenu;
        if (!this.playerMenu.containsKey(player)) {
            playerMenu = new Menu(player);
            this.playerMenu.put(player, playerMenu);
            return playerMenu;
        } else {
            return this.playerMenu.get(player);
        }
    }

    public boolean versionID() {
        if (getServer().getVersion().contains("1.8")) { return true; }
        if (getServer().getVersion().contains("1.9")) { return true; }
        if (getServer().getVersion().contains("1.10")) { return true; }
        if (getServer().getVersion().contains("1.11")) { return true; }
        return getServer().getVersion().contains("1.12");
    }

    public boolean isLevelsValid() {
        if (!fileUtils.config.getBoolean("level-validator")) {
            return true;
        }
        boolean valid = true;
        for (String group : fileUtils.levels.getConfigurationSection("").getKeys(false)) {
            final ArrayList<Long> list = new ArrayList<>();
            if (!fileUtils.levels.contains(group + "." + fileUtils.config.getLong("start-level"))) {
                textUtils.error("Validator ( not passed ) (group ( " + group + " ) (level ( " + fileUtils.config.getLong("start-level") + " ) is not found )");
                valid = false;
            }
            if (fileUtils.levels.getLong(group + "." + fileUtils.config.getLong("start-level") + ".xp") != 0) {
                textUtils.error("Validator ( not passed ) (group ( " + group + " ) (level ( " + fileUtils.config.getLong("start-level") + " ) xp is not 0 )");
                valid = false;
            }
            if (!fileUtils.levels.contains(group + ".execute")) {
                textUtils.error("Validator ( not passed ) (group ( " + group + " ) is missing execute )");
                valid = false;
            }
            for (String level : fileUtils.levels.getConfigurationSection(group).getKeys(false)) {
                if (!level.equalsIgnoreCase("execute")) {
                    if (!calculateManager.isLong(level)) {
                        textUtils.error("Validator ( not passed ) ( " + group + " ) (level ( " + level + " ) not a number )");
                        valid = false;
                    }
                    if (!fileUtils.levels.contains(group + "." + level + ".prefix")) {
                        textUtils.error("Validator ( not passed ) ( " + group + " ) (level ( " + level + " ) is missing prefix )");
                        valid = false;
                    }
                    if (!fileUtils.levels.contains(group + "." + level + ".suffix")) {
                        textUtils.error("Validator ( not passed ) ( " + group + " ) (level ( " + level + " ) is missing suffix )");
                        valid = false;
                    }
                    if (!fileUtils.levels.contains(group + "." + level + ".group")) {
                        textUtils.error("Validator ( not passed ) ( " + group + " ) (level ( " + level + " ) is missing group )");
                        valid = false;
                    }
                    if (!fileUtils.levels.contains(group + "." + level + ".execute")) {
                        textUtils.error("Validator ( not passed ) ( " + group + " ) (level ( " + level + " ) is missing execute )");
                        valid = false;
                    }
                    if (!fileUtils.execute.contains(fileUtils.levels.getString(group + "." + level + ".execute"))) {
                        textUtils.error("Validator ( not passed ) ( " + group + " ) (execute group ( " + fileUtils.levels.getString(group + "." + level + ".execute") + " ) is not found in execute.yml)");
                        valid = false;
                    }
                    if (!fileUtils.levels.contains(group + "." + level + ".xp")) {
                        textUtils.error("Validator ( not passed ) ( " + group + " ) (level ( " + level + " ) is missing xp )");
                        valid = false;
                    }
                    list.add(fileUtils.levels.getLong(group + "." + level + ".xp"));
                }
            }
        }
        return valid;
    }
}