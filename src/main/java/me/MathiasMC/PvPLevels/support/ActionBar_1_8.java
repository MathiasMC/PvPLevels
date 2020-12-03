package me.MathiasMC.PvPLevels.support;

import me.MathiasMC.PvPLevels.PvPLevels;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar_1_8 {

    private final PvPLevels plugin;

    public ActionBar_1_8(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    public void send(final Player player, final String text) {
        final PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(ChatColor.translateAlternateColorCodes('&', plugin.getPlaceholderManager().replacePlaceholders(player, false, text))), (byte)2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
