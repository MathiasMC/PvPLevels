package me.MathiasMC.PvPLevels.api.events;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLevelUPEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PvPLevels plugin;

    private boolean cancelled = false;

    private final Player player;

    private final Entity entity;

    private final PlayerConnect playerConnect;

    private long level;

    public PlayerLevelUPEvent(final Player player, final Entity entity, final PlayerConnect playerConnect, final long level) {
        this.plugin = PvPLevels.getInstance();
        this.player = player;
        this.entity = entity;
        this.playerConnect = playerConnect;
        this.level = level;
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

    public long getLevel() {
        return this.level;
    }

    public void setLevel(final long level) {
        this.level = level;
    }

    public void execute() {
        if (!plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".override")) {
            plugin.getXPManager().sendCommands(player, plugin.getFileUtils().execute.getStringList(plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + level + ".execute") + ".level.up"));
        } else {
            plugin.getXPManager().sendCommands(player, plugin.getFileUtils().execute.getStringList(plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".override") + ".level.up"));
        }
        playerConnect.setLevel(level);
        playerConnect.save();
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
