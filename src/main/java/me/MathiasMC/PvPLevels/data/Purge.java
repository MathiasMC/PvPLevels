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
        boolean info = plugin.config.get.getBoolean("debug.purge");
        int interval = plugin.config.get.getInt("mysql.purge.interval");
        int startInterval = interval;
        if (plugin.config.get.getBoolean("mysql.purge.check-on-startup")) {
            startInterval = 1;
        }
        if (info) { plugin.textUtils.info("[Database Purge] Starting task for deleting old players runs every " + interval + " seconds"); }
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            long deleted = 0;
            for (String uuid : plugin.list()) {
                if (isOld(plugin.get(uuid).getTime())) {
                    plugin.database.delete(uuid);
                    if (plugin.config.get.contains("mysql.purge.commands")) {
                        for (String command : plugin.config.get.getStringList("mysql.purge.commands")) {
                            plugin.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvplevels_uuid}", uuid));
                        }
                    }
                    if (info) { plugin.textUtils.info("[Database Purge] Deleting player UUID: " + uuid); }
                    deleted++;
                }
            }
            if (info) { plugin.textUtils.info("[Database Purge] Checking all players ( " + deleted + " ) was deleted"); }
            }, startInterval * 20, interval * 20);
    }

    private boolean isOld(Timestamp date) {
        return ChronoUnit.DAYS.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()) > plugin.config.get.getInt("mysql.purge.inactive-days");
    }
}