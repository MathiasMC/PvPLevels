package me.MathiasMC.PvPLevels;

import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Set;

public class PvPLevelsAPI {

    public Long kills(String uuid) {
        return PvPLevels.call.get(uuid).kills();
    }

    public void kills(String uuid, Long set) {
        PvPLevels.call.get(uuid).kills(set);
    }

    public Long deaths(String uuid) {
        return PvPLevels.call.get(uuid).deaths();
    }

    public void deaths(String uuid, Long set) {
        PvPLevels.call.get(uuid).deaths(set);
    }

    public Long xp(String uuid) {
        return PvPLevels.call.get(uuid).xp();
    }

    public void xp(String uuid, Long set) {
        PvPLevels.call.get(uuid).xp(set);
    }

    public Long level(String uuid) {
        return PvPLevels.call.get(uuid).level();
    }

    public void level(String uuid, Long set) {
        PvPLevels.call.get(uuid).level(set);
    }

    public Long killstreak(String uuid) {
        return PvPLevels.call.get(uuid).killstreak();
    }

    public void killstreak(String uuid, Long set) {
        PvPLevels.call.get(uuid).killstreak(set);
    }

    public String kdr(String uuid) {
        return PvPLevels.call.statsManager.kdr(uuid);
    }

    public double xp_progress(String uuid) {
        return PvPLevels.call.statsManager.xp_progress(uuid);
    }

    public Long xp_required(String uuid) {
        return PvPLevels.call.statsManager.xp_required(uuid);
    }

    public String xp_progress_style(String uuid) {
        return PvPLevels.call.statsManager.xp_progress_style(uuid);
    }

    public String group(Player player) {
        return PvPLevels.call.statsManager.group(player);
    }

    public String group_to(Player player) {
        return PvPLevels.call.statsManager.group_to(player);
    }

    public void save(String uuid) {
        PvPLevels.call.get(uuid).save();
    }

    public Set<String> list() {
        return PvPLevels.call.list();
    }

    public String getTopValue(String type, int number, boolean key) {
        return PvPLevels.call.statsManager.getTopValue(type, number, key);
    }

    public LinkedHashMap getTopMap(String type) {
        return PvPLevels.call.statsManager.getTopMap(type);
    }

    public Double getPersonalBooster(String uuid) {
        return PvPLevels.call.get(uuid).getPersonalBooster();
    }
}