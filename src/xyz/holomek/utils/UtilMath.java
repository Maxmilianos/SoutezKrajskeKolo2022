package xyz.holomek.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

public class UtilMath {

    public static Random random = new Random();

    public static boolean contains(int[] integers, Integer search) {
        for (Integer id : integers)
            if (id == search)
                return true;
        return false;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            if (Integer.valueOf(s) < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            if (Double.valueOf(s) < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
            if (Long.valueOf(s) < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static double trim(int degree, double d) {
        String format = "#.#";
        for (int i=1 ; i<degree ; i++)
            format += "#";

        return Double.valueOf(new DecimalFormat(format, new DecimalFormatSymbols(Locale.US)).format(d));
    }

    public static int random(int min, int max) {
        return min + random.nextInt(max - min);
    }

    public static int random(int max) {
        return random(0, max);
    }

}
