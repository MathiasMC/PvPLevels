package me.MathiasMC.PvPLevels.api.events;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerXPEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PvPLevels plugin;

    private boolean cancelled = false;

    private final Player player;

    private final Entity entity;

    private final PlayerConnect playerConnect;

    private long xp;

    private final long item;

    private final double multiplier;

    private String entityType;

    private String key;

    public PlayerXPEvent(final Player player, final Entity entity, final PlayerConnect playerConnect, final long xp, final long item, final double multiplier, final String entityType, final String key) {
        this.plugin = PvPLevels.getInstance();
        this.player = player;
        this.entity = entity;
        this.playerConnect = playerConnect;
        this.xp = xp;
        this.item = item;
        this.multiplier = multiplier;
        this.entityType = entityType;
        this.key = key;
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

    public long getItem() {
        return this.item;
    }

    public double getMultiplier() {
        return this.multiplier;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public String getKey() {
        return this.key;
    }

    public void setXp(final long xp) {
        this.xp = xp;
    }

    public void setEntityType(final String entityType) {
        this.entityType = entityType;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public void execute() {
        if (!plugin.getXPManager().isMaxLevel(playerConnect)) {
            playerConnect.setXp(playerConnect.getXp() + xp);
        }
        playerConnect.setXpLast(xp);
        playerConnect.setItem(item);
        playerConnect.setXpType(entityType);
        final boolean getLevel = plugin.getXPManager().getLevel(player, entity, playerConnect);
        if (!getLevel) {
            if (!plugin.getFileUtils().levels.contains(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".override")) {
                plugin.getXPManager().sendCommands(player, plugin.getFileUtils().execute.getStringList(plugin.getFileUtils().levels.getString(playerConnect.getGroup() + ".execute") + ".xp." + key));
            } else {
                plugin.getXPManager().sendCommands(player, plugin.getFileUtils().execute.getStringList(plugin.getFileUtils().levels.getString(playerConnect.getGroup() + "." + playerConnect.getLevel() + ".override") + ".xp." + key));
            }
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
