package me.MathiasMC.PvPLevels.gui;

import org.bukkit.entity.Player;

public class Menu {

    private final Player player;

    private final String uuid;

    private String sort;

    private boolean reverse;

    public Menu(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId().toString();
        this.sort = "kills";
        this.reverse = true;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getSort() {
        return this.sort;
    }

    public boolean getReverse() {
        return this.reverse;
    }

    public void setSort(final String sort) {
        this.sort = sort;
    }

    public void setReverse(final boolean reverse) {
        this.reverse = reverse;
    }
}