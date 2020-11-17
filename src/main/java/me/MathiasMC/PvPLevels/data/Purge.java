package me.MathiasMC.PvPLevels.data;

import me.MathiasMC.PvPLevels.PvPLevels;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Purge {

    private final PvPLevels plugin;

    public Purge(final PvPLevels plugin) {
        this.plugin = plugin;
        final int interval = plugin.getFileUtils().config.getInt("mysql.purge.interval");
        int startInterval = interval;
        if (plugin.getFileUtils().config.getBoolean("mysql.purge.check-on-startup")) {
            startInterval = 1;
        }
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (String uuid : plugin.listPlayerConnect()) {
                if (isOld(plugin.getPlayerConnect(uuid).getTime())) {
                    plugin.database.delete(uuid);
                    if (plugin.getFileUtils().config.contains("mysql.purge.commands")) {
                        for (String command : plugin.getFileUtils().config.getStringList("mysql.purge.commands")) {
                            plugin.getServer().dispatchCommand(plugin.consoleSender, command.replace("{uuid}", uuid));
                        }
                    }
                }
            }
            }, startInterval * 20, interval * 20);
    }

    private boolean isOld(Timestamp date) {
        return ChronoUnit.DAYS.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()) > plugin.getFileUtils().config.getInt("mysql.purge.inactive-days");
    }
}