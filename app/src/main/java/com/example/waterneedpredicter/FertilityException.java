package com.example.waterneedpredicter;

public class FertilityException extends RuntimeException {

    public FertilityException(int minFertileAge, int actualAge) {
        super("Too young to be pregnant (" + actualAge + " years old). Please insert an age value greater or equal to " + minFertileAge + ".");
    }

}
