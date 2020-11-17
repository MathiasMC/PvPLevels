package me.MathiasMC.PvPLevels.api.events;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.api.Type;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerXPEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PvPLevels plugin;

    private boolean cancelled = false;

    private final Player player;

    private final PlayerConnect playerConnect;

    private int add;

    private String pathType;

    private String entityType;

    private int itemBoost;

    private double multiplier;

    private Type type;

    public PlayerXPEvent(Player player, PlayerConnect playerConnect, int add, String pathType, String entityType, int itemBoost, double multiplier, Type type) {
        this.plugin = PvPLevels.getInstance();
        this.player = player;
        this.playerConnect = playerConnect;
        this.add = add;
        this.pathType = pathType;
        this.entityType = entityType;
        this.itemBoost = itemBoost;
        this.multiplier = multiplier;
        this.type = type;
    }

    public Player getPlayer() {
        return this.player;
    }

    public PlayerConnect getPlayerConnect() {
        return this.playerConnect;
    }

    public int getAdd() {
        return this.add;
    }

    public String getPathType() {
        return this.pathType;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public int getItemBoost() {
        return this.itemBoost;
    }

    public double getMultiplier() {
        return this.multiplier;
    }

    public Type getType() {
        return this.type;
    }

    public void execute() {
        final long xp = playerConnect.getXp() + add;
        playerConnect.setXp(xp);
        final boolean getLevel = plugin.getXPManager().getLevel(player, playerConnect);
        if (!getLevel) {
            plugin.getXPManager().sendCommands(player, plugin.getFileUtils().levels.getString(playerConnect.getGroup() + ".execute") + ".xp." + pathType, plugin.getFileUtils().execute, entityType, add, 0, itemBoost, multiplier);
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
