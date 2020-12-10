package me.MathiasMC.PvPLevels.api.events;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class PlayerGetXPEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PvPLevels plugin;

    private boolean cancelled = false;

    private final Player player;

    private final Entity entity;

    private final PlayerConnect playerConnect;

    private long xp;

    private String key;

    private List<String> commands = null;

    public PlayerGetXPEvent(final Player player, final Entity entity, final PlayerConnect playerConnect, final long xp) {
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

    public String getKey() {
        return this.key;
    }

    public List<String> getCommands() {
        return this.commands;
    }

    public void setXp(final long xp) {
        this.xp = xp;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public void setCommands(final List<String> commands) {
        this.commands = commands;
    }

    public void execute() {
        if (!plugin.getXPManager().isMaxLevel(playerConnect)) {
            playerConnect.setXp(xp);
        }
        final boolean getLevel = plugin.getXPManager().getLevel(player, entity, playerConnect);
        if (!getLevel) {
            if (commands == null) {
                final String path = playerConnect.getGroup() + "." + playerConnect.getLevel() + ".override";
                if (!plugin.getFileUtils().levels.contains(path)) {
                    setCommands(plugin.getFileUtils().execute.getStringList(plugin.getFileUtils().levels.getString(playerConnect.getGroup() + ".execute") + ".xp." + key));
                } else {
                    setCommands(plugin.getFileUtils().execute.getStringList(plugin.getFileUtils().levels.getString(path) + ".xp." + key));
                }
            }
            plugin.getXPManager().sendCommands(player, commands);
        }
        if (playerConnect.getSave() >= plugin.getFileUtils().config.getInt("mysql.save")) {
            playerConnect.save();
            playerConnect.setSave(0);
            return;
        }
        playerConnect.setSave(playerConnect.getSave() + 1);
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
