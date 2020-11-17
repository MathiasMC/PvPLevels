package me.MathiasMC.PvPLevels.data;

import me.MathiasMC.PvPLevels.PvPLevels;

import java.sql.Timestamp;
import java.util.*;

public class PlayerConnect {

    private final String uuid;

    private String group;

    private long kills;

    private long deaths;

    private long xp;

    private long level;

    private long killstreak;

    private long killstreakTop;

    private double multiplier;

    private int multiplierTime;

    private int multiplierTimeLeft;

    private Timestamp time;

    private int save;

    public PlayerConnect(String uuid) {
        this.uuid = uuid;
        final String[] data = PvPLevels.getInstance().database.getValues(uuid);
        this.group = data[0];
        this.kills = Long.parseLong(data[1]);
        this.deaths = Long.parseLong(data[2]);
        this.xp = Long.parseLong(data[3]);
        this.level = Long.parseLong(data[4]);
        this.killstreak = Long.parseLong(data[5]);
        this.killstreakTop = Long.parseLong(data[6]);
        final String[] split = data[7].split(" ");
        this.multiplier = Double.parseDouble(split[0]);
        this.multiplierTime = Integer.parseInt(split[1]);
        this.multiplierTimeLeft = Integer.parseInt(split[2]);
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

    public void setKillstreakTop(final Long killstreak_top) {
        this.killstreakTop = killstreak_top;
    }

    public void setMultiplier(final Double multiplier) {
        this.multiplier = multiplier;
    }

    public void setMultiplierTime(final Integer multiplier_time) {
        this.multiplierTime = multiplier_time;
    }

    public void setMultiplierTimeLeft(final Integer multiplier_time_left) {
        this.multiplierTimeLeft = multiplier_time_left;
    }

    public void setTime() {
        this.time = new Timestamp(new Date().getTime());
    }

    public void setSave(final int save) {
        this.save = save;
    }

    public String getGroup() {
        return this.group;
    }

    public long getKills() {
        return this.kills;
    }

    public long getDeaths() {
        return this.deaths;
    }

    public long getXp() {
        return this.xp;
    }

    public long getLevel() {
        return this.level;
    }

    public long getKillstreak() {
        return this.killstreak;
    }

    public long getKillstreakTop() {
        return this.killstreakTop;
    }

    public double getMultiplier() {
        return this.multiplier;
    }

    public int getMultiplierTime() {
        return this.multiplierTime;
    }

    public int getMultiplierTimeLeft() {
        return this.multiplierTimeLeft;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public int getSave() {
        return this.save;
    }

    public void save() {
        PvPLevels.getInstance().database.setValues(uuid, group, kills, deaths, xp, level, killstreak, killstreakTop, (multiplier + " " + multiplierTime + " " + multiplierTimeLeft), time);
    }
}