package me.MathiasMC.PvPLevels.listeners;

import me.MathiasMC.PvPLevels.gui.GUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        final InventoryHolder inventoryHolder = e.getInventory().getHolder();
        if (inventoryHolder instanceof GUI) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) { return; }
            final GUI gui = (GUI) inventoryHolder;
            gui.click(e);
        }
    }
}