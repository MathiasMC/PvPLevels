package me.MathiasMC.PvPLevels;

import com.google.common.io.ByteStreams;
import me.MathiasMC.PvPLevels.commands.*;
import me.MathiasMC.PvPLevels.data.Database;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.data.Purge;
import me.MathiasMC.PvPLevels.files.*;
import me.MathiasMC.PvPLevels.gui.Menu;
import me.MathiasMC.PvPLevels.listeners.*;
import me.MathiasMC.PvPLevels.managers.*;
import me.MathiasMC.PvPLevels.placeholders.PlaceholderAPI;
import me.MathiasMC.PvPLevels.utils.Metrics;
import me.MathiasMC.PvPLevels.utils.TextUtils;
import me.MathiasMC.PvPLevels.utils.UpdateUtils;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PvPLevels extends JavaPlugin {

    public static PvPLevels call;

    public final ConsoleCommandSender consoleSender = Bukkit.getServer().getConsoleSender();

    public Database database;

    public TextUtils textUtils;
    public Config config;
    public Language language;
    public Levels levels;
    public Execute execute;

    public GUIFolder guiFolder;

    public GUIManager guiManager;
    public PlaceholderManager placeholderManager;
    public KillSessionManager killSessionManager;
    public StatsManager statsManager;
    public XPManager xpManager;

    private final Map<String, PlayerConnect> playerConnect = new HashMap<>();

    private final HashMap<Player, Menu> playerMenu = new HashMap<>();

    public final HashMap<String, FileConfiguration> guiFiles = new HashMap<>();

    public final HashSet<String> spawners = new HashSet<>();

    public final ArrayList<Location> blocksList = new ArrayList<>();

    public final Map<String, String> lastDamagers = new HashMap<>();

    public final HashSet<OfflinePlayer> multipliers = new HashSet<>();

    public void onEnable() {
        call = this;
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        textUtils = new TextUtils(this);
        config = new Config(this);
        language = new Language(this);
        levels = new Levels(this);
        execute = new Execute(this);

        guiFolder = new GUIFolder(this);

        guiManager = new GUIManager(this);
        placeholderManager = new PlaceholderManager(this);
        killSessionManager = new KillSessionManager(this);
        statsManager = new StatsManager(this);
        xpManager = new XPManager(this);

        database = new Database(this);
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
            metrics.addCustomChart(new Metrics.SimplePie("levels", () -> String.valueOf(levels.get.getConfigurationSection("").getKeys(false).size())));
            if (config.get.getBoolean("update-check")) {
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
            if (config.get.contains("mysql.purge")) {
                new Purge(this);
            }
            if (isLevelsValid()) {
                textUtils.info("Validator ( passed )");
            }

            if (config.get.getBoolean("save.use")) {
                saveSchedule();
            }

            getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                for (OfflinePlayer offlinePlayer : multipliers) {
                    if (offlinePlayer.isOnline()) {
                        final PlayerConnect playerConnect = getPlayerConnect(offlinePlayer.getUniqueId().toString());
                        int left = playerConnect.getMultiplier_time_left();
                        if (left > 0) {
                            left--;
                            playerConnect.setMultiplier_time_left(left);
                            return;
                        }
                        for (String message : language.get.getStringList("multiplier.lost")) {
                            getServer().dispatchCommand(consoleSender, ChatColor.translateAlternateColorCodes('&', message.replace("{player}", offlinePlayer.getName()).replace("{multiplier}", String.valueOf(playerConnect.getMultiplier()))));
                        }
                        playerConnect.setMultiplier(0D);
                        playerConnect.setMultiplier_time(0);
                        playerConnect.setMultiplier_time_left(0);
                        multipliers.remove(offlinePlayer);
                    } else {
                        multipliers.remove(offlinePlayer);
                    }
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

    public void unloadPlayerConnect(final String uuid) {
        final PlayerConnect data = playerConnect.remove(uuid);
        if (data != null) {
            data.save();
        }
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

    private void saveSchedule() {
        final int interval = config.get.getInt("save.interval");
        textUtils.info("Saving to the database every ( " + interval + " ) minutes");
        getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            for (String uuid : listPlayerConnect()) {
                getPlayerConnect(uuid).save();
            }
        }, interval * 1200, interval * 1200);
    }

    public String replacePlaceholders(final OfflinePlayer player, String message) {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            message = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, message);
        }
        final String uuid = player.getUniqueId().toString();
            final PlayerConnect playerConnect = getPlayerConnect(uuid);
            message = internalReplace(playerConnect, message)
                    .replace("{level_group}", statsManager.group(playerConnect))
                    .replace("{level_prefix}", statsManager.prefix(playerConnect))
                    .replace("{level_suffix}", statsManager.suffix(playerConnect))
            ;
        message = message
                .replace("{player}", player.getName())
                .replace("{uuid}", uuid);
        return message;
    }

    public String internalReplace(final PlayerConnect playerConnect, String message) {
        return message.replace("{kills}", String.valueOf(playerConnect.getKills()))
                .replace("{deaths}", String.valueOf(playerConnect.getDeaths()))
                .replace("{xp}", String.valueOf(playerConnect.getXp()))
                .replace("{level}", String.valueOf(playerConnect.getLevel()))
                .replace("{level_next}", String.valueOf(playerConnect.getLevel() + 1))
                .replace("{kdr}", statsManager.kdr(playerConnect))
                .replace("{kill_factor}", statsManager.kill_factor(playerConnect))
                .replace("{killstreak}", String.valueOf(playerConnect.getKillstreak()))
                .replace("{killstreak_top}", String.valueOf(playerConnect.getKillstreak_top()))
                .replace("{xp_required}", String.valueOf(statsManager.xp_required(playerConnect, false)))
                .replace("{xp_need}", String.valueOf(statsManager.xp_need(playerConnect)))
                .replace("{xp_progress}", String.valueOf(statsManager.xp_progress(playerConnect)))
                .replace("{xp_progress_style}", String.valueOf(statsManager.xp_progress_style(playerConnect, "xp-progress-style")))
                .replace("{xp_progress_style_2}", String.valueOf(statsManager.xp_progress_style(playerConnect, "xp-progress-style-2")))
                .replace("{date}", statsManager.time("date", playerConnect.getTime().getTime()))
                .replace("{time}", statsManager.time("time", playerConnect.getTime().getTime()))
                .replace("{group}", playerConnect.getGroup())
                ;
    }

    public boolean versionID() {
        if (getServer().getVersion().contains("1.8")) { return true; }
        if (getServer().getVersion().contains("1.9")) { return true; }
        if (getServer().getVersion().contains("1.10")) { return true; }
        if (getServer().getVersion().contains("1.11")) { return true; }
        return getServer().getVersion().contains("1.12");
    }

    public boolean isInt(final String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean isLong(final String s) {
        try {
            Long.parseLong(s);
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

    public boolean isString(final String text) {
        return text.matches("^[a-zA-Z]*$");
    }

    public ItemStack getID(final String bb, final int amount) {
        if (versionID()) {
            try {
                final String[] parts = bb.split(":");
                final int matId = Integer.parseInt(parts[0]);
                if (parts.length == 2) {
                    final short data = Short.parseShort(parts[1]);
                    return new ItemStack(Material.getMaterial(matId), amount, data);
                }
                return new ItemStack(Material.getMaterial(matId));
            } catch (Exception e) {
                return null;
            }
        } else {
            try {
                return new ItemStack(Material.getMaterial(bb), amount);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public void copy(final String filename, final File file) {
        try {
            ByteStreams.copy(getResource(filename), new FileOutputStream(file));
        } catch (IOException exception) {
            textUtils.exception(exception.getStackTrace(), exception.getMessage());
        }
    }

    public boolean isLevelsValid() {
        boolean valid = true;
        for (String group : levels.get.getConfigurationSection("").getKeys(false)) {
            final ArrayList<Long> list = new ArrayList<>();
            if (!levels.get.contains(group + "." + config.get.getLong("start-level"))) {
                textUtils.error("Validator ( not passed ) (group ( " + group + " ) (level ( " + config.get.getLong("start-level") + " ) is not found )");
                valid = false;
            }
            if (levels.get.getLong(group + "." + config.get.getLong("start-level") + ".xp") != 0) {
                textUtils.error("Validator ( not passed ) (group ( " + group + " ) (level ( " + config.get.getLong("start-level") + " ) xp is not 0 )");
                valid = false;
            }
            if (!levels.get.contains(group + ".execute")) {
                textUtils.error("Validator ( not passed ) (group ( " + group + " ) is missing execute )");
                valid = false;
            }
            for (String level : levels.get.getConfigurationSection(group).getKeys(false)) {
                if (!level.equalsIgnoreCase("execute")) {
                    if (!isLong(level)) {
                        textUtils.error("Validator ( not passed ) ( " + group + " ) (level ( " + level + " ) not a number )");
                        valid = false;
                    }
                    if (!levels.get.contains(group + "." + level + ".prefix")) {
                        textUtils.error("Validator ( not passed ) ( " + group + " ) (level ( " + level + " ) is missing prefix )");
                        valid = false;
                    }
                    if (!levels.get.contains(group + "." + level + ".suffix")) {
                        textUtils.error("Validator ( not passed ) ( " + group + " ) (level ( " + level + " ) is missing suffix )");
                        valid = false;
                    }
                    if (!levels.get.contains(group + "." + level + ".group")) {
                        textUtils.error("Validator ( not passed ) ( " + group + " ) (level ( " + level + " ) is missing group )");
                        valid = false;
                    }
                    if (!levels.get.contains(group + "." + level + ".execute")) {
                        textUtils.error("Validator ( not passed ) ( " + group + " ) (level ( " + level + " ) is missing execute )");
                        valid = false;
                    }
                    if (!execute.get.contains(levels.get.getString(group + "." + level + ".execute"))) {
                        textUtils.error("Validator ( not passed ) ( " + group + " ) (execute group ( " + levels.get.getString(group + "." + level + ".execute") + " ) is not found in execute.yml)");
                        valid = false;
                    }
                    if (!levels.get.contains(group + "." + level + ".xp")) {
                        textUtils.error("Validator ( not passed ) ( " + group + " ) (level ( " + level + " ) is missing xp )");
                        valid = false;
                    }
                    list.add(levels.get.getLong(group + "." + level + ".xp"));
                }
            }
            for (int i = 1; i < list.size(); i++) {
                long last = list.get(i - 1);
                long current = list.get(i);
                if (last >= current) {
                    textUtils.error("Validator ( not passed ) ( " + group + " ) ( xp about level ( " + (i - 1) + " ) is not setup correct )");
                    valid = false;
                }
            }
        }
        return valid;
    }
}