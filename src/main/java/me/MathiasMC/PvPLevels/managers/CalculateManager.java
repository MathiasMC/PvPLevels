package me.MathiasMC.PvPLevels.managers;

import me.MathiasMC.PvPLevels.PvPLevels;

import java.util.concurrent.ThreadLocalRandom;

public class CalculateManager {

    private final PvPLevels plugin;

    public CalculateManager(final PvPLevels plugin) {
        this.plugin = plugin;
    }

    public boolean isInt(final String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean isLong(final String s) {
        try {
            Long.parseLong(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean isString(final String text) {
        return text.matches("^[a-zA-Z]*$");
    }

    public int randomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
