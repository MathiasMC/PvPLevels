package me.MathiasMC.PvPLevels.support.actionbar;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.managers.ActionBarManager;
import me.MathiasMC.PvPLevels.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionBar extends ActionBarManager {

    public ActionBar(final PvPLevels plugin) {
        super(plugin);
    }

    public void sendMessage(final Player player, final String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', Utils.replacePlaceholders(player, false, message))));
    }
}
