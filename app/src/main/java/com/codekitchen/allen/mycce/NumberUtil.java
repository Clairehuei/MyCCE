package com.codekitchen.allen.mycce;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility class to easily get the formatted number.
 * @author Cloud
 */
public class NumberUtil {

    private static NumberFormat formatter;

    public static String formatPrice(double price) {
        formatter = DecimalFormat.getCurrencyInstance(Locale.CHINA);
        formatter.setMaximumFractionDigits(2);
        return formatter.format(price);
    }

    public static String formatNumber(double number) {
        formatter = DecimalFormat.getNumberInstance(Locale.CHINA);
        formatter.setMaximumFractionDigits(2);
        return formatter.format(number);
    }
}
