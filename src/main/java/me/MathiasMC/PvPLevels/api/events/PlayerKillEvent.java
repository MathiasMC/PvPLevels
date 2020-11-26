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

    private final Player killer;

    private final Player player;

    private final PlayerConnect killerPlayerConnect;

    private final PlayerConnect playerConnect;

    public PlayerKillEvent(Player killer, Player player, PlayerConnect killerPlayerConnect, PlayerConnect playerConnect) {
        this.plugin = PvPLevels.getInstance();
        this.killer = killer;
        this.player = player;
        this.killerPlayerConnect = killerPlayerConnect;
        this.playerConnect = playerConnect;
    }

    public Player getKiller() {
        return this.killer;
    }

    public Player getPlayer() {
        return this.player;
    }

    public PlayerConnect getKillerPlayerConnect() {
        return this.killerPlayerConnect;
    }

    public PlayerConnect getPlayerConnect() {
        return this.playerConnect;
    }

    public boolean inWorld() {
        return plugin.getXPManager().world(killer, plugin.getFileUtils().config, "kills." + killerPlayerConnect.getGroup());
    }

    public void execute() {
        killerPlayerConnect.setKills(killerPlayerConnect.getKills() + 1);
        final String path = "kills." + killerPlayerConnect.getGroup() + "." + killerPlayerConnect.getKills();
        if (plugin.getFileUtils().config.contains(path)) {
            plugin.getXPManager().sendCommands(killer, path, plugin.getFileUtils().config, "", 0, 0, 0, 0);
        } else {
            plugin.getXPManager().sendCommands(killer, "kills." + killerPlayerConnect.getGroup() + ".get", plugin.getFileUtils().config, "", 0, 0, 0, 0);
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