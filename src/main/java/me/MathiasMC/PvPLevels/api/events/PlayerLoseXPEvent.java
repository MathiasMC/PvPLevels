package me.MathiasMC.PvPLevels.api.events;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLoseXPEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PvPLevels plugin;

    private boolean cancelled = false;

    private final Player player;

    private final PlayerConnect playerConnect;

    private int lose;

    private String entityType;

    public PlayerLoseXPEvent(Player player, PlayerConnect playerConnect, int lose, String entityType) {
        this.plugin = PvPLevels.getInstance();
        this.player = player;
        this.playerConnect = playerConnect;
        this.lose = lose;
        this.entityType = entityType;
    }

    public Player getPlayer() {
        return this.player;
    }

    public PlayerConnect getPlayerConnect() {
        return this.playerConnect;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public void execute() {
        playerConnect.setXp(playerConnect.getXp() - lose);
        final boolean loseLevel = plugin.getXPManager().loseLevel(playerConnect, player, entityType);
        if (!loseLevel) {
            plugin.getXPManager().sendCommands(player, plugin.getFileUtils().levels.getString(playerConnect.getGroup() + ".execute") + ".xp.lose", plugin.getFileUtils().execute, entityType, 0, lose, 0, 0);
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