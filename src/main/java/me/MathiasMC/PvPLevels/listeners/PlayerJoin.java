package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final PvPLevels plugin;

    public PlayerJoin(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        final String uuid = player.getUniqueId().toString();
        if (plugin.list().contains(uuid)) {
            final PlayerConnect playerConnect = plugin.get(uuid);
            if (playerConnect.getMultiplier() != 0) {
                player.sendMessage("your multiplier has started again you joined");
                plugin.multipliers.add(player);
            }
        }
    }
}
