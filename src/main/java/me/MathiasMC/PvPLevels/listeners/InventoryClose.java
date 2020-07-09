package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryClose implements Listener {

    private final PvPLevels plugin;

    public InventoryClose(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof GUI) {
            Player player = (Player) e.getPlayer();
            player.updateInventory();
            PvPLevels.call.getServer().getScheduler().runTaskLater(plugin, player::updateInventory, 3L);
        }
    }
}
