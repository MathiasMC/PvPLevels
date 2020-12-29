package me.MathiasMC.PvPLevels.api;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.listeners.*;
import me.MathiasMC.PvPLevels.managers.*;
import me.MathiasMC.PvPLevels.utils.FileUtils;
import org.bukkit.event.HandlerList;

import java.util.Set;

public class PvPLevelsAPI {

    private static PvPLevelsAPI instance;

    private final PvPLevels plugin;

    public PvPLevelsAPI() {
        this.plugin = PvPLevels.getInstance();
    }

    public PlayerConnect getPlayerConnect(final String uuid) {
        return plugin.getPlayerConnect(uuid);
    }

    public void unloadPlayerConnect(final String uuid) {
        plugin.unloadPlayerConnect(uuid);
    }

    public void updatePlayerConnect(final String uuid) {
        unloadPlayerConnect(uuid);
        getPlayerConnect(uuid);
    }

    public void removePlayerConnect(final String uuid) {
        plugin.removePlayerConnect(uuid);
    }

    public void deletePlayerConnect(final String uuid) {
        plugin.database.delete(uuid);
    }

    public Set<String> listPlayerConnect() {
        return plugin.listPlayerConnect();
    }

    public FileUtils getFileUtils() {
        return plugin.getFileUtils();
    }

    public XPManager getXPManager() {
        return plugin.getXPManager();
    }

    public ActionBarManager getActionBarManager() {
        return plugin.getActionBarManager();
    }

    public StatsManager getStatsManager() {
        return plugin.getStatsManager();
    }

    public long getStartLevel() {
        return plugin.getStartLevel();
    }

    public boolean isDebug() {
        return plugin.isDebug();
    }

    public void setDebug(final boolean debug) {
        plugin.setDebug(debug);
    }

    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    public void unregisterAll() {
        unregisterPlayerJoin();
        unregisterEntityDeath();
        unregisterEntityDamageByEntity();
        unregisterCreatureSpawn();
        unregisterBlockBreak();
        unregisterBlockPlace();
        unregisterPlayerMove();
    }

    public void unregisterPlayerJoin() {
        final PlayerJoin listener = plugin.getPlayerJoin();
        if (listener == null) return;
        HandlerList.unregisterAll(listener);
    }

    public void unregisterEntityDeath() {
        final EntityDeath listener = plugin.getEntityDeath();
        if (listener == null) return;
        HandlerList.unregisterAll(listener);
    }

    public void unregisterEntityDamageByEntity() {
        final EntityDamageByEntity listener = plugin.getEntityDamageByEntity();
        if (listener == null) return;
        HandlerList.unregisterAll(listener);
    }

    public void unregisterCreatureSpawn() {
        CreatureSpawn listener = plugin.getCreatureSpawn();
        if (listener == null) return;
        HandlerList.unregisterAll(listener);
    }

    public void unregisterBlockBreak() {
        final BlockBreak listener = plugin.getBlockBreak();
        if (listener == null) return;
        HandlerList.unregisterAll(listener);
    }

    public void unregisterBlockPlace() {
        final BlockPlace listener = plugin.getBlockPlace();
        if (listener == null) return;
        HandlerList.unregisterAll(listener);
    }

    public void unregisterPlayerMove() {
        final PlayerMove listener = plugin.getPlayerMove();
        if (listener == null) return;
        HandlerList.unregisterAll(listener);
    }

    public static PvPLevelsAPI getInstance() {
        if (instance == null) {
            instance = new PvPLevelsAPI();
        }
        return instance;
    }
}