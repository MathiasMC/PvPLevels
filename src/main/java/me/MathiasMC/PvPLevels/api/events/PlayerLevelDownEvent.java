package me.MathiasMC.PvPLevels.api.events;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class PlayerLevelDownEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PvPLevels plugin;

    private boolean cancelled = false;

    private final Player player;

    private final Entity entity;

    private final PlayerConnect playerConnect;

    private long level;

    private List<String> commands = null;

    public PlayerLevelDownEvent(final Player player, final Entity entity, final PlayerConnect playerConnect, final long level) {
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

    public List<String> getCommands() {
        return this.commands;
    }

    public List<String> getDefaultCommands() {
        final String path = playerConnect.getGroup() + "." + (level + 1) + ".override";
        if (!plugin.getFileUtils().levels.contains(path)) {
            return plugin.getFileUtils().execute.getStringList(plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + (level + 1) + ".execute") + ".level.down");
        }
        return plugin.getFileUtils().execute.getStringList(plugin.getFileUtils().levels.getString(path) + ".level.down");
    }

    public void setLevel(final long level) {
        this.level = level;
    }

    public void setCommands(final List<String> commands) {
        this.commands = commands;
    }

    public void setXp() {
        playerConnect.setXp(plugin.getFileUtils().levels.getLong(playerConnect.getGroup() + "." + level + ".xp"));
    }

    public void execute() {
        playerConnect.setLevel(level);
        playerConnect.save();
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
