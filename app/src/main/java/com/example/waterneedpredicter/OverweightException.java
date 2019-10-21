package com.example.waterneedpredicter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OverweightException extends RuntimeException {

    public OverweightException(int maxWeightInGrams, int actualWeightInGrams) {
        super("Weight value is " + getWeightText(actualWeightInGrams) + ". Please insert a value below " + getWeightText(maxWeightInGrams) + ".");
    }

    private static String getWeightText(int weightInGrams) {
        return new BigDecimal(weightInGrams).divide(new BigDecimal(1000), RoundingMode.HALF_UP) + " kg";
    }
}
