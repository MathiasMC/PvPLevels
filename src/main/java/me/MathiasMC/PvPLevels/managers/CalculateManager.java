package me.MathiasMC.PvPLevels.managers;

import java.util.concurrent.ThreadLocalRandom;

public class CalculateManager {

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

    public boolean isDouble(final String s) {
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

    public long randomNumber(final long min, final long max) {
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }
}
