package me.MathiasMC.PvPLevels;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.List;
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

    public Long coins(String uuid) {
        return PvPLevels.call.get(uuid).coins();
    }

    public void coins(String uuid, Long set) {
        PvPLevels.call.get(uuid).coins(set);
    }

    public String kdr(String uuid) {
        return PvPLevels.call.statsManager.kdr(uuid);
    }

    public String kill_factor(String uuid) {
        return PvPLevels.call.statsManager.kill_factor(uuid);
    }

    public String xp_progress(String uuid) {
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

    public boolean hasGlobalActive() {
        return PvPLevels.call.boostersManager.hasGlobalActive();
    }

    public OfflinePlayer getGlobalOfflinePlayer() {
        return PvPLevels.call.boostersManager.getGlobalOfflinePlayer();
    }

    public Double getGlobalBooster() {
        return PvPLevels.call.boostersManager.getGlobalBooster();
    }

    public int getGlobalSeconds() {
        return PvPLevels.call.boostersManager.getGlobalSeconds();
    }

    public String getTimeFormat(int seconds) {
        return PvPLevels.call.boostersManager.timeLeft(seconds);
    }

    public List<String> getGlobalQueue() {
        return PvPLevels.call.boostersManager.globalQueue();
    }

    public int getGlobalQueueNumber(String uuid) {
        return PvPLevels.call.boostersManager.queueNumber(uuid);
    }

    public int getGlobalQueueSize(String uuid) {
        return PvPLevels.call.boostersManager.isInQueueSize(uuid);
    }

    public String getGlobalNamePlaceholder() {
        return PvPLevels.call.boostersManager.getGlobalNamePlaceholder();
    }

    public String getGlobalPlaceholder() {
        return PvPLevels.call.boostersManager.getGlobalPlaceholder();
    }

    public String getGlobalTimePlaceholder() {
        return PvPLevels.call.boostersManager.getGlobalTimePlaceholder();
    }

    public String getGlobalTimeLeftPlaceholder() {
        return PvPLevels.call.boostersManager.getGlobalTimeLeftPlaceholder();
    }

    public String getGlobalTimePrefixPlaceholder() {
        return PvPLevels.call.boostersManager.getGlobalTimePrefixPlaceholder();
    }

    public String getGlobalTimeLeftPrefixPlaceholder() {
        return PvPLevels.call.boostersManager.getGlobalTimeLeftPrefixPlaceholder();
    }

    public String getPersonalPlaceholder(String uuid) {
        return PvPLevels.call.boostersManager.getPersonalPlaceholder(uuid);
    }

    public String getPersonalTimePlaceholder(String uuid) {
        return PvPLevels.call.boostersManager.getPersonalTimePlaceholder(uuid);
    }

    public String getPersonalTimeLeftPlaceholder(String uuid) {
        return PvPLevels.call.boostersManager.getPersonalTimeLeftPlaceholder(uuid);
    }

    public String getPersonalTimePrefixPlaceholder(String uuid) {
        return PvPLevels.call.boostersManager.getPersonalTimePrefixPlaceholder(uuid);
    }

    public String getPersonalTimeLeftPrefixPlaceholder(String uuid) {
        return PvPLevels.call.boostersManager.getPersonalTimeLeftPrefixPlaceholder(uuid);
    }
}