package com.krinotech.popularmovies.helper;

import java.text.NumberFormat;
import java.util.Currency;

public class CurrencyFormatHelper {

    public static String convertToUSCurrency(int amount) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.setMaximumFractionDigits(0);

        return numberFormat.format(amount);
    }
}
