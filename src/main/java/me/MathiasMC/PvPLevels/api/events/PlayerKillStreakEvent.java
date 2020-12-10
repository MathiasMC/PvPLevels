package me.MathiasMC.PvPLevels.api.events;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class PlayerKillStreakEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PvPLevels plugin;

    private boolean cancelled = false;

    private final Player player;

    private final Player killed;

    private final PlayerConnect playerConnect;

    private long killstreak;

    private List<String> commands = null;

    public PlayerKillStreakEvent(final Player player, final Player killed, final PlayerConnect playerConnect, final long killstreak) {
        this.plugin = PvPLevels.getInstance();
        this.player = player;
        this.killed = killed;
        this.playerConnect = playerConnect;
        this.killstreak = killstreak;
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

    public long getKillstreak() {
        return this.killstreak;
    }

    public List<String> getCommands() {
        return this.commands;
    }

    public void setKillstreak(final long killstreak) {
        this.killstreak = killstreak;
    }

    public void setCommands(final List<String> commands) {
        this.commands = commands;
    }

    public void execute() {
        playerConnect.setKillstreak(killstreak);
        if (commands == null) {
            if (killstreak > playerConnect.getKillstreakTop()) {
                final PlayerKillStreakTopEvent playerKillStreakTopEvent = new PlayerKillStreakTopEvent(player, killed, playerConnect, killstreak);
                plugin.getServer().getPluginManager().callEvent(playerKillStreakTopEvent);
                if (playerKillStreakTopEvent.isCancelled()) {
                    return;
                }
                playerKillStreakTopEvent.execute();
                return;
            }
            final String path = "killstreak." + playerConnect.getGroup() + "." + killstreak + ".get";
            if (plugin.getFileUtils().config.contains(path)) {
                setCommands(plugin.getFileUtils().config.getStringList(path));
            } else {
                setCommands(plugin.getFileUtils().config.getStringList("killstreak." + playerConnect.getGroup() + ".get"));
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
