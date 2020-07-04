package me.MathiasMC.PvPLevels.data;

import me.MathiasMC.PvPLevels.PvPLevels;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class PlayerConnect {

    private final String playeruuid;

    private Long kills;

    private Long deaths;

    private Long xp;

    private Long level;

    private Long killstreak;

    private Long coins;

    private Double personalBooster;

    private int personalBoosterTime = 0;

    private int personalBoosterTime_left = 0;

    private int taskID;

    private Timestamp time;

    public PlayerConnect(String uuid) {
        playeruuid = uuid;
        String[] data = PvPLevels.call.database.getValues(uuid);
        kills = Long.parseLong(data[0]);
        deaths = Long.parseLong(data[1]);
        xp = Long.parseLong(data[2]);
        level = Long.parseLong(data[3]);
        killstreak = Long.parseLong(data[4]);
        coins = Long.parseLong(data[5]);
        time = Timestamp.valueOf(data[6]);
        loadTimer();
    }

    public Long kills() {
        return kills;
    }

    public Long deaths() {
        return deaths;
    }

    public Long xp() {
        return xp;
    }

    public Long level() {
        return level;
    }

    public Long killstreak() {
        return killstreak;
    }

    public Long coins() {
        return coins;
    }

    public void kills(Long set) {
        kills = set;
    }

    public void deaths(Long set) {
        deaths = set;
    }

    public void xp(Long set) {
        xp = set;
    }

    public void level(Long set) {
        level = set;
    }

    public void setTime() {
        time = new Timestamp(new Date().getTime());
    }

    public Timestamp getTime() {
        return time;
    }

    public void killstreak(Long set) {
        killstreak = set;
    }

    public void coins(Long set) {
        coins = set;
    }

    public void save() {
        PvPLevels.call.database.setValues(playeruuid, kills, deaths, xp, level, killstreak, coins, time);
    }

    public Double getPersonalBooster() {
        return personalBooster;
    }

    public int getPersonalBoosterTime() {
        return personalBoosterTime;
    }

    public int getPersonalBoosterTime_left() {
        return personalBoosterTime_left;
    }

    public void timer(int timeAmount, Double personalBooster) {
        this.personalBooster = personalBooster;
        this.personalBoosterTime = timeAmount;
        this.taskID = PvPLevels.call.getServer().getScheduler().scheduleSyncRepeatingTask(PvPLevels.call, new Runnable(){
            int timeRemaining = timeAmount;
            public void run(){
                personalBoosterTime_left = timeRemaining;
                if (timeRemaining <= 0) {
                    endBoost();
                    PvPLevels.call.boosters.get.set("players." + playeruuid + ".personal-active", null);
                    PvPLevels.call.boosters.save();
                }
                timeRemaining--;
            }
        }, 0L, 20L);
    }

    private void endBoost() {
        PvPLevels.call.getServer().getScheduler().cancelTask(this.taskID);
        this.personalBooster = null;
        this.personalBoosterTime = 0;
        this.personalBoosterTime_left = 0;
        for (String command : PvPLevels.call.boosters.get.getStringList("personal-settings.end")) {
            PvPLevels.call.getServer().dispatchCommand(PvPLevels.call.consoleCommandSender, command.replace("{pvplevels_player}", PvPLevels.call.getServer().getOfflinePlayer(UUID.fromString(playeruuid)).getName()));
        }
    }

    public void loadTimer() {
        String path = "players." + playeruuid + ".personal-active";
        if (PvPLevels.call.boosters.get.contains(path)) {
            String[] get = PvPLevels.call.boosters.get.getString(path).split(" ");
            timer(Integer.parseInt(get[1]), Double.valueOf(get[0]));
        }
    }
}