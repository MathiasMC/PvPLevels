package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final PvPLevels plugin;

    public PlayerJoin(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        final String uuid = player.getUniqueId().toString();
        final PlayerConnect playerConnect = plugin.getPlayerConnect(uuid);
        if (!plugin.getFileUtils().config.getBoolean("multiplier-quit", true)) return;
        if (playerConnect.getMultiplier() != 0) {
            plugin.getXPManager().sendCommands(player, plugin.getFileUtils().language.getStringList("multiplier.join"));
            plugin.multipliers.add(uuid);
        }
    }
}
