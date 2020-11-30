package me.MathiasMC.PvPLevels.api.events;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDeathEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PvPLevels plugin;

    private boolean cancelled = false;

    private final Player player;

    private final Entity entity;

    private final PlayerConnect playerConnect;

    private long deaths;

    public PlayerDeathEvent(final Player player, final Entity entity, final PlayerConnect playerConnect, final long deaths) {
        this.plugin = PvPLevels.getInstance();
        this.player = player;
        this.entity = entity;
        this.playerConnect = playerConnect;
        this.deaths = deaths;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public PlayerConnect getPlayerConnect() {
        return this.playerConnect;
    }

    public long getDeaths() {
        return this.deaths;
    }

    public void setDeaths(final long deaths) {
        this.deaths = deaths;
    }

    public void execute() {
        playerConnect.setDeaths(deaths);
        if (entity != null) {
            for (String command : plugin.getFileUtils().config.getStringList("deaths." + playerConnect.getGroup() + ".player")) {
                plugin.getServer().dispatchCommand(plugin.consoleSender, plugin.getPlaceholderManager().replacePlaceholders(player, false, command));
            }
        } else {
            for (String command : plugin.getFileUtils().config.getStringList("deaths." + playerConnect.getGroup() + ".other")) {
                plugin.getServer().dispatchCommand(plugin.consoleSender, plugin.getPlaceholderManager().replacePlaceholders(player, false, command));
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
