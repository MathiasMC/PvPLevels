package me.MathiasMC.PvPLevels.api.events;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKillStreakEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PvPLevels plugin;

    private boolean cancelled = false;

    private final Player player;

    private final Player killed;

    private final PlayerConnect playerConnect;

    private long killstreak;

    public PlayerKillStreakEvent(final Player player, final Player killed, final PlayerConnect playerConnect) {
        this.plugin = PvPLevels.getInstance();
        this.player = player;
        this.killed = killed;
        this.playerConnect = playerConnect;
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

    public void setKillstreak(final long killstreak) {
        this.killstreak = killstreak;
    }

    public void execute() {
        final long killStreak = playerConnect.getKillstreak() + 1;
        playerConnect.setKillstreak(killStreak);
        String path = "killstreak." + playerConnect.getGroup() + ".get";
        final String getPath = "killstreak." + playerConnect.getGroup() + "." + playerConnect.getKillstreak() + ".get";
        if (killStreak > playerConnect.getKillstreakTop()) {
            final PlayerKillStreakTopEvent playerKillStreakTopEvent = new PlayerKillStreakTopEvent(player, killed, playerConnect);
            plugin.getServer().getPluginManager().callEvent(playerKillStreakTopEvent);
            if (!playerKillStreakTopEvent.isCancelled()) {
                path = "killstreak." + playerConnect.getGroup() + "." + playerConnect.getKillstreak() + ".top";
            }
        } else if (plugin.getFileUtils().config.contains(getPath)) {
            path = getPath;
        }
        plugin.getXPManager().sendCommands(player, plugin.getFileUtils().config.getStringList(path));
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
