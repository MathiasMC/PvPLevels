package me.MathiasMC.PvPLevels.gui;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GUI implements InventoryHolder {

    protected Menu playerMenu;
    protected Inventory inventory;

    public abstract void click(InventoryClickEvent e);

    public abstract void setItems();

    public abstract FileConfiguration getFile();

    protected int page = 0;
    protected int index = 0;
    protected List<OfflinePlayer> players;

    public GUI(Menu playerMenu) {
        this.playerMenu = playerMenu;
    }

    public void open() {
        inventory = PvPLevels.getInstance().getServer().createInventory(this, getFile().getInt("settings.size"), ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getFile().getString("settings.name"))));
        this.setItems();
        playerMenu.getPlayer().openInventory(inventory);
        if (players.isEmpty()) {
            return;
        }
        final int perPage = getFile().getInt("settings.perpage");
        for (int i = 0; i < perPage; i++) {
            index = perPage * page + i;
            if (index >= players.size()) break;
            final OfflinePlayer offlinePlayer = players.get(index);
            if (offlinePlayer != null) {
                inventory.addItem(PvPLevels.getInstance().getItemStackManager().getPlayerHead(getFile(), offlinePlayer, offlinePlayer.getName(), "settings.player"));
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void setPlayers() {
        players = new ArrayList<>(PvPLevels.getInstance().getStatsManager().getTopMap(playerMenu.getSort(), playerMenu.getReverse()).keySet());
    }
}