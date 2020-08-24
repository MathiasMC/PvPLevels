package me.MathiasMC.PvPLevels.data;

import me.MathiasMC.PvPLevels.PvPLevels;

import java.sql.Timestamp;
import java.util.*;

public class PlayerConnect {

    private final String uuid;

    private String group;

    private Long kills;

    private Long deaths;

    private Long xp;

    private Long level;

    private Long killstreak;

    private Long killstreak_top;

    private Double multiplier;

    private Integer multiplier_time;

    private Integer multiplier_time_left;

    private Timestamp time;

    public PlayerConnect(String uuid) {
        this.uuid = uuid;
        final String[] data = PvPLevels.call.database.getValues(uuid);
        this.group = data[0];
        this.kills = Long.parseLong(data[1]);
        this.deaths = Long.parseLong(data[2]);
        this.xp = Long.parseLong(data[3]);
        this.level = Long.parseLong(data[4]);
        this.killstreak = Long.parseLong(data[5]);
        this.killstreak_top = Long.parseLong(data[6]);
        final String[] split = data[7].split(" ");
        if (split.length == 3) {
            multiplier = Double.parseDouble(split[0]);
            multiplier_time = Integer.parseInt(split[1]);
            multiplier_time_left = Integer.parseInt(split[2]);
        } else {
            multiplier = Double.parseDouble(split[0]);
            multiplier_time = Integer.parseInt(split[1]);
            multiplier_time_left = 0;
            save();
        }
        this.time = Timestamp.valueOf(data[8]);
    }

    public void setGroup(final String group) {
        this.group = group;
    }

    public void setKills(final Long kills) {
        this.kills = kills;
    }

    public void setDeaths(final Long deaths) {
        this.deaths = deaths;
    }

    public void setXp(final Long xp) {
        this.xp = xp;
    }

    public void setLevel(final Long level) {
        this.level = level;
    }

    public void setKillstreak(final Long killstreak) {
        this.killstreak = killstreak;
    }

    public void setKillstreak_top(final Long killstreak_top) {
        this.killstreak_top = killstreak_top;
    }

    public void setMultiplier(final Double multiplier) {
        this.multiplier = multiplier;
    }

    public void setMultiplier_time(final Integer multiplier_time) {
        this.multiplier_time = multiplier_time;
    }

    public void setMultiplier_time_left(final Integer multiplier_time_left) {
        this.multiplier_time_left = multiplier_time_left;
    }

    public void setTime() {
        this.time = new Timestamp(new Date().getTime());
    }

    public String getGroup() {
        return group;
    }

    public Long getKills() {
        return kills;
    }

    public Long getDeaths() {
        return deaths;
    }

    public Long getXp() {
        return xp;
    }

    public Long getLevel() {
        return level;
    }

    public Long getKillstreak() {
        return killstreak;
    }

    public Long getKillstreak_top() {
        return killstreak_top;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public Integer getMultiplier_time() {
        return multiplier_time;
    }

    public Integer getMultiplier_time_left() {
        return multiplier_time_left;
    }

    public Timestamp getTime() {
        return time;
    }

    public void save() {
        PvPLevels.call.database.setValues(uuid, group, kills, deaths, xp, level, killstreak, killstreak_top, (multiplier + " " + multiplier_time + " " + multiplier_time_left), time);
    }
}