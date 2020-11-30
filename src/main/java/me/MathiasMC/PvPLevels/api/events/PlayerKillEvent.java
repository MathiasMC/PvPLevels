package me.MathiasMC.PvPLevels.api.events;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKillEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PvPLevels plugin;

    private boolean cancelled = false;

    private final Player player;

    private final Player killed;

    private final PlayerConnect playerConnect;

    private long kills;

    public PlayerKillEvent(final Player player, final Player killed, final PlayerConnect playerConnect, final long kills) {
        this.plugin = PvPLevels.getInstance();
        this.player = player;
        this.killed = killed;
        this.playerConnect = playerConnect;
        this.kills = kills;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Player getKilled() {
        return this.killed;
    }

    public PlayerConnect getPlayerConnect() {
        return this.playerConnect;
    }

    public long getKills() {
        return this.kills;
    }

    public void setKills(final long kills) {
        this.kills = kills;
    }

    public void execute() {
        playerConnect.setKills(kills);
        final String path = "kills." + playerConnect.getGroup() + "." + kills;
        if (plugin.getFileUtils().config.contains(path)) {
            plugin.getXPManager().sendCommands(player, plugin.getFileUtils().config.getStringList(path));
        } else {
            plugin.getXPManager().sendCommands(player, plugin.getFileUtils().config.getStringList("kills." + playerConnect.getGroup() + ".get"));
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