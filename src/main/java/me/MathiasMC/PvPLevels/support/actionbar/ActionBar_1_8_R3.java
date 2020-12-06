package me.MathiasMC.PvPLevels.support.actionbar;

import me.MathiasMC.PvPLevels.PvPLevels;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar_1_8_R3 extends ActionBar {

    public ActionBar_1_8_R3(final PvPLevels plugin) {
        super(plugin);
    }

    public void sendMessage(final Player player, final String message) {
        final PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(player, false, message))), (byte)2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
