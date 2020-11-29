package me.MathiasMC.PvPLevels.api.events;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLoseXPEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PvPLevels plugin;

    private boolean cancelled = false;

    private final Player player;

    private final Entity entity;

    private final PlayerConnect playerConnect;

    private long xp;

    public PlayerLoseXPEvent(final Player player, final Entity entity, final PlayerConnect playerConnect, final long xp) {
        this.plugin = PvPLevels.getInstance();
        this.player = player;
        this.entity = entity;
        this.playerConnect = playerConnect;
        this.xp = xp;
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

    public long getXp() {
        return this.xp;
    }

    public void setXp(final int xp) {
        this.xp = xp;
    }

    public void execute() {
        final long set = playerConnect.getXp() - xp;
        if (set < 0) {
            return;
        }
        playerConnect.setXp(set);
        playerConnect.setLost(xp);
        final boolean loseLevel = plugin.getXPManager().loseLevel(player, entity, playerConnect);
        if (!loseLevel) {
            if (!plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".override")) {
                plugin.getXPManager().sendCommands(player, plugin.getFileUtils().execute.getStringList(plugin.getFileUtils().levels.getString(playerConnect.getGroup() + ".execute") + ".xp.lose"));
            } else {
                plugin.getXPManager().sendCommands(player, plugin.getFileUtils().execute.getStringList(plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".override") + ".xp.lose"));
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