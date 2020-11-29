package me.MathiasMC.PvPLevels.api;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.managers.*;
import me.MathiasMC.PvPLevels.utils.FileUtils;

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

    public KillSessionManager getKillSessionManager() {
        return plugin.getKillSessionManager();
    }

    public StatsManager getStatsManager() {
        return plugin.getStatsManager();
    }

    public PlaceholderManager getPlaceholderManager() {
        return plugin.getPlaceholderManager();
    }

    public CalculateManager getCalculateManager() {
        return plugin.getCalculateManager();
    }

    public long getStartLevel() {
        return plugin.getStartLevel();
    }

    public boolean isDebug() {
        return plugin.isDebug();
    }

    public static PvPLevelsAPI getInstance() {
        if (instance == null) {
            instance = new PvPLevelsAPI();
        }
        return instance;
    }
}