package me.MathiasMC.PvPLevels.api.events;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDeathEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PvPLevels plugin;

    private boolean cancelled = false;

    private final Player killer;

    private final Player player;

    private final PlayerConnect playerConnect;

    public PlayerDeathEvent(Player killer, Player player, PlayerConnect playerConnect) {
        this.plugin = PvPLevels.getInstance();
        this.killer = killer;
        this.player = player;
        this.playerConnect = playerConnect;
    }

    public Player getKiller() {
        return this.killer;
    }

    public Player getPlayer() {
        return this.player;
    }

    public PlayerConnect getPlayerConnect() {
        return this.playerConnect;
    }

    public boolean inWorld() {
        return plugin.getXPManager().world(player, plugin.getFileUtils().config, "deaths." + playerConnect.getGroup());
    }

    public void execute() {
        if (plugin.getFileUtils().config.getBoolean("deaths." + playerConnect.getGroup() + ".only-player")) {
            if (killer == null) {
                return;
            }
        }
        playerConnect.setDeaths(playerConnect.getDeaths() + 1);
        String source = player.getLastDamageCause().getCause().toString();
        if (plugin.getFileUtils().language.contains("translate.cause." + source)) {
            source = plugin.getFileUtils().language.getString("translate.cause." + source);
        } else {
            final String killerName = plugin.getXPManager().getKillerName(player);
            if (killerName.length() > 0) {
                source = killerName;
            }
        }
        if (killer != null) {
            for (String command : plugin.getFileUtils().config.getStringList("deaths." + playerConnect.getGroup() + ".player")) {
                plugin.getServer().dispatchCommand(plugin.consoleSender, plugin.getPlaceholderManager().replacePlaceholders(player, false, command.replace("{source}", source)));
            }
        } else {
            for (String command : plugin.getFileUtils().config.getStringList("deaths." + playerConnect.getGroup() + ".other")) {
                plugin.getServer().dispatchCommand(plugin.consoleSender, plugin.getPlaceholderManager().replacePlaceholders(player, false, command.replace("{source}", source)));
            }
        }
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean set) {
        cancelled = set;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
