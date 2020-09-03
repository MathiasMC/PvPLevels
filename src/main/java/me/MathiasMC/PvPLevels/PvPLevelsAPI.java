package me.MathiasMC.PvPLevels;

import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.managers.StatsManager;
import me.MathiasMC.PvPLevels.managers.XPManager;

import java.util.Set;

public class PvPLevelsAPI {

    public PlayerConnect getPlayerConnect(final String uuid) {
        return PvPLevels.call.get(uuid);
    }

    public Set<String> getPlayerConnect() {
        return PvPLevels.call.list();
    }

    public void loadPlayerConnect(final String uuid) {
        PvPLevels.call.load(uuid);
    }

    public void unloadPlayerConnect(final String uuid) {
        PvPLevels.call.unload(uuid);
    }

    public StatsManager getStatsManager() {
        return PvPLevels.call.statsManager;
    }

    public XPManager getXPManager() {
        return PvPLevels.call.xpManager;
    }
}