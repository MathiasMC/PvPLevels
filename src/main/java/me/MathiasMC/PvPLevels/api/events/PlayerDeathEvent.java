package me.MathiasMC.PvPLevels.api.events;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class PlayerDeathEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PvPLevels plugin;

    private boolean cancelled = false;

    private final Player player;

    private final Entity entity;

    private final PlayerConnect playerConnect;

    private long deaths;

    private List<String> commands = null;

    public PlayerDeathEvent(Player player, Entity entity, PlayerConnect playerConnect, long deaths) {
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

    public List<String> getCommands() {
        return this.commands;
    }

    public List<String> getDefaultCommands() {
        if (entity != null) {
            return plugin.getFileUtils().config.getStringList("deaths." + playerConnect.getGroup() + ".player");
        }
        return plugin.getFileUtils().config.getStringList("deaths." + playerConnect.getGroup() + ".other");
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public void execute() {
        playerConnect.setDeaths(deaths);
        plugin.getXPManager().sendCommands(player, commands);
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
