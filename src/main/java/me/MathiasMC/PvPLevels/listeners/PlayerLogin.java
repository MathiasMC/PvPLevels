package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLogin implements Listener {

    private final PvPLevels plugin;

    public PlayerLogin(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onLogin(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        final String uuid = player.getUniqueId().toString();
        plugin.database.insert(uuid);
        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            plugin.updatePlayerConnect(uuid);
            }, plugin.getConfig().getInt("mysql.update") * 20L);
        if (!plugin.getFileUtils().config.contains("groups")) return;
        for (String group : plugin.getFileUtils().config.getConfigurationSection("groups").getKeys(false)) {
            String permission = plugin.getFileUtils().config.getString("groups." + group);
            if (player.hasPermission(permission)) {
                PlayerConnect playerConnect = plugin.getPlayerConnect(player.getUniqueId().toString());
                if (playerConnect.getGroup().equals(group)) return;
                playerConnect.setGroup(group);
                break;
            }
        }
    }
}