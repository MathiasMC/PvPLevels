package me.MathiasMC.PvPLevels.api.events;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class PlayerKillEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PvPLevels plugin;

    private boolean cancelled = false;

    private final Player player;

    private final Player killed;

    private final PlayerConnect playerConnect;

    private long kills;

    private List<String> commands = null;

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

    public void setCommands(final List<String> commands) {
        this.commands = commands;
    }

    public void execute() {
        playerConnect.setKills(kills);
        final String path = "kills." + playerConnect.getGroup() + "." + kills;
        if (commands == null) {
            if (plugin.getFileUtils().config.contains(path)) {
                setCommands(plugin.getFileUtils().config.getStringList(path));
            } else {
                setCommands(plugin.getFileUtils().config.getStringList("kills." + playerConnect.getGroup() + ".get"));
            }
        }
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