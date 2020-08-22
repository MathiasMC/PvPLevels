package me.MathiasMC.PvPLevels;

import me.MathiasMC.PvPLevels.data.PlayerConnect;

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
}