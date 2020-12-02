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

    private long multiplierTime;

    private long multiplierTimeLeft;

    private Timestamp time;

    private long save;

    private String xpType = "";

    private long xpLast = 0;

    private long xpLost = 0;

    private long xpItem = 0;

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

    public long getMultiplierTime() {
        return this.multiplierTime;
    }

    public long getMultiplierTimeLeft() {
        return this.multiplierTimeLeft;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public String getXpType() {
        return this.xpType;
    }

    public long getXpLast() {
        return this.xpLast;
    }

    public long getXpLost() {
        return this.xpLost;
    }

    public long getXpItem() {
        return this.xpItem;
    }

    public long getSave() {
        return this.save;
    }

    public void setGroup(final String group) {
        this.group = group;
    }

    public void setKills(final long kills) {
        this.kills = kills;
    }

    public void setDeaths(final long deaths) {
        this.deaths = deaths;
    }

    public void setXp(final long xp) {
        this.xp = xp;
    }

    public void setLevel(final long level) {
        this.level = level;
    }

    public void setKillstreak(final long killstreak) {
        this.killstreak = killstreak;
    }

    public void setKillstreakTop(final long killstreak_top) {
        this.killstreakTop = killstreak_top;
    }

    public void setMultiplier(final double multiplier) {
        this.multiplier = multiplier;
    }

    public void setMultiplierTime(final long multiplierTime) {
        this.multiplierTime = multiplierTime;
    }

    public void setMultiplierTimeLeft(final long multiplierTimeLeft) {
        this.multiplierTimeLeft = multiplierTimeLeft;
    }

    public void setTime() {
        this.time = new Timestamp(new Date().getTime());
    }

    public void setSave(final long save) {
        this.save = save;
    }

    public void setXpType(final String xpType) {
        this.xpType = xpType;
    }

    public void setXpLast(final long xpLast) {
        this.xpLast = xpLast;
    }

    public void setXpLost(final long xpLost) {
        this.xpLost = xpLost;
    }

    public void setXpItem(final long xpItem) {
        this.xpItem = xpItem;
    }

    public void save() {
        PvPLevels.getInstance().database.setValues(uuid, group, kills, deaths, xp, level, killstreak, killstreakTop, (multiplier + " " + multiplierTime + " " + multiplierTimeLeft), time);
    }
}