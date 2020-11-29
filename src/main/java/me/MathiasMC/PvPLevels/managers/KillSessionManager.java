package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KillSessionManager {

    private final PvPLevels plugin;

    public KillSessionManager(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    private final HashMap<String, ArrayList<String>> killsession = new HashMap<>();

    private final Map<String, String> killsessiontime = new HashMap<>();

    public boolean hasSession(final Player killer, final Player entity) {
        if (plugin.getFileUtils().config.getBoolean("kill-session.use")) {
                boolean returning = false;
                boolean check = false;
                Player killed = (Player) entity;
                String attacker = killer.getUniqueId().toString();
                if (!killsession.containsKey(attacker)) {
                    killsession.put(attacker, new ArrayList<>(Collections.singletonList(killed.getUniqueId() + ";1")));
                } else {
                    for (int i = 0; i < killsession.get(attacker).size(); i++) {
                        if (killed.getUniqueId().toString().equalsIgnoreCase(killsession.get(attacker).get(i).split(";")[0])) {
                            String uuid = killsession.get(attacker).get(i).split(";")[0];
                            int nameamount = Integer.parseInt(killsession.get(attacker).get(i).split(";")[1]);
                            int SessionInt = plugin.getFileUtils().config.getInt("kill-session.amount");
                            if (killed.getUniqueId().toString().equalsIgnoreCase(uuid))
                                if (nameamount >= SessionInt) {
                                    returning = true;
                                    task(killed, attacker, killer);
                                } else {
                                    killsession.get(attacker).set(i, uuid + ";" + (nameamount + 1));
                                }
                            check = false;
                            break;
                        }
                        check = true;
                    }
                }
                if (check) {
                    killsession.get(attacker).add(killed.getUniqueId().toString() + ";1");
                }
                return returning;
        }
        return false;
    }

    private void task(final Player killed, final String attacker, final Entity killer) {
        if (!killsessiontime.containsKey(killed.getUniqueId().toString() + "=" + attacker)) {
            int id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                if (killsessiontime.containsKey(killed.getUniqueId().toString() + "=" + attacker)) {
                    String[] split = killsessiontime.get(killed.getUniqueId().toString() + "=" + attacker).split("=");
                    int time = Integer.parseInt(split[1]);
                    if (time > -1) {
                        killsessiontime.put(killed.getUniqueId().toString() + "=" + attacker, Integer.valueOf(split[0]) + "=" + (time - 1));
                    }
                    if (time == 0) {
                        plugin.getServer().getScheduler().cancelTask(Integer.parseInt(split[0]));
                        killsessiontime.remove(killed.getUniqueId().toString() + "=" + attacker);
                        killsession.remove(attacker);
                        sendMessage(killer, "remove");
                    }
                }
            },0L, 20L);
            killsessiontime.put(killed.getUniqueId().toString() + "=" + attacker, id + "=" + plugin.getFileUtils().config.getInt("kill-session.time"));
            sendMessage(killer, "get");
        } else {
            sendMessage(killer, "abuse");
        }
    }

    private void sendMessage(final Entity killer, final String path) {
        if (killer instanceof Player) {
            plugin.getXPManager().sendCommands((Player) killer, plugin.getFileUtils().config.getStringList("kill-session." + path));
        }
    }
}