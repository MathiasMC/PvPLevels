package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlaceholderManager {

    private final PvPLevels plugin;

    public PlaceholderManager(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    public long[] getDurability(final ItemStack itemStack) {
        if (itemStack != null && isValid(itemStack.getType().name())) {
            short max = itemStack.getType().getMaxDurability();
            return new long[] {max - itemStack.getDurability(), max};
        }
        return new long[] {0, 0};
    }

    private boolean isValid(final String name) {
        if (name.endsWith("_HELMET")
                || name.endsWith("_CHESTPLATE")
                || name.endsWith("_LEGGINGS")
                || name.endsWith("_BOOTS")
                || name.endsWith("_SWORD")
                || name.endsWith("_PICKAXE")
                || name.endsWith("_AXE")
                || name.endsWith("_SHOVEL")
                || name.endsWith("SHIELD")) {
            return true;
        }
        return false;
    }

    public ItemStack getHandItemStack(final Player player, final boolean main) {
        ItemStack itemStack;
        try {
            if (main) {
                itemStack = player.getInventory().getItemInMainHand();
            } else {
                itemStack = player.getInventory().getItemInOffHand();
            }
        } catch (NoSuchMethodError error) {
            itemStack = player.getInventory().getItemInHand();
        }
        return itemStack;
    }
}