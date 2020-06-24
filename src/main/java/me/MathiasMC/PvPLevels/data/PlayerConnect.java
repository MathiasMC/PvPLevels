package me.MathiasMC.PvPLevels.data;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerConnect {

    private String playeruuid;

    private Long kills;

    private Long deaths;

    private Long xp;

    private Long level;

    private Long killstreak;

    private Double personalBooster;

    private int taskID;

    public PlayerConnect(String uuid) {
        playeruuid = uuid;
        Long[] data = PvPLevels.call.database.getValues(uuid);
        kills = data[0];
        deaths = data[1];
        xp = data[2];
        level = data[3];
        killstreak = 0L;
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

    public void killstreak(Long set) {
        killstreak = set;
    }

    public void save() {
        PvPLevels.call.database.setValues(playeruuid, kills, deaths, xp, level);
    }

    public Double getPersonalBooster() {
        return personalBooster;
    }

    public void timer(int timeAmount, Double personalBooster) {
        this.personalBooster = personalBooster;
        this.taskID = PvPLevels.call.getServer().getScheduler().scheduleSyncRepeatingTask(PvPLevels.call, new Runnable(){
            int timeRemaining = timeAmount;
            public void run(){
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
        for (String command : PvPLevels.call.boosters.get.getStringList("personal-settings.end")) {
            PvPLevels.call.getServer().dispatchCommand(PvPLevels.call.consoleCommandSender, command.replace("{pvplevels_player}", PvPLevels.call.getServer().getOfflinePlayer(UUID.fromString(playeruuid)).getName()));
        }
    }

    public void loadTimer() {
        String path = "players." + playeruuid + ".personal-active";
        if (PvPLevels.call.boosters.get.contains(path)) {
            String[] get = PvPLevels.call.boosters.get.getString(path).split(" ");
            timer(Integer.valueOf(get[1]), Double.valueOf(get[0]));
        }
    }
}