package me.MathiasMC.PvPLevels;

import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.managers.StatsManager;
import me.MathiasMC.PvPLevels.managers.XPManager;

import java.util.Set;

public class PvPLevelsAPI {

    public PlayerConnect getPlayerConnect(final String uuid) {
        return PvPLevels.call.getPlayerConnect(uuid);
    }

    public Set<String> listPlayerConnect() {
        return PvPLevels.call.listPlayerConnect();
    }

    public void unloadPlayerConnect(final String uuid) {
        PvPLevels.call.unloadPlayerConnect(uuid);
    }

    public StatsManager getStatsManager() {
        return PvPLevels.call.statsManager;
    }

    public XPManager getXPManager() {
        return PvPLevels.call.xpManager;
    }
}