package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.entity.Player;

public abstract class ActionBarManager {

    public final PvPLevels plugin;

    public ActionBarManager(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    public abstract void sendMessage(final Player player, final String message);
}
