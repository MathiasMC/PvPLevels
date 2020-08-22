package me.MathiasMC.PvPLevels.gui;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class GUI implements InventoryHolder {

    protected Menu playerMenu;
    protected Inventory inventory;

    public abstract String getName();
    public abstract int getSize();
    public abstract void click(InventoryClickEvent e);
    public abstract void setItems();

    protected int page = 0;
    protected int index = 0;
    protected List<OfflinePlayer> players;

    public GUI(Menu playerMenu) {
        this.playerMenu = playerMenu;
    }

    public void open() {
        inventory = PvPLevels.call.getServer().createInventory(this, getSize(), getName());
        this.setItems();
        playerMenu.getPlayer().openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void setPlayers() {
        players = new ArrayList<>(PvPLevels.call.statsManager.getTopMap(playerMenu.getSort(), playerMenu.getReverse()).keySet());
    }
}