package com.example.waterneedpredicter;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

@Entity
public class HumanPerson {

    private static final int MAX_WEIGHT_IN_GRAMS = 200_000;
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int weightInGrams;
    private int yearBorn;
    private int monthBorn;
    private int dayOfMonthBorn;
    private boolean isPregnant;
    private boolean isBreastfeeding;

    HumanPerson(String name, int weightInGrams, int yearBorn, int monthBorn, int dayOfMonthBorn, boolean isPregnant, boolean isBreastfeeding) {
        if (weightInGrams > MAX_WEIGHT_IN_GRAMS) {
            throw new OverweightException(MAX_WEIGHT_IN_GRAMS, weightInGrams);
        }
        this.name = name;
        this.weightInGrams = weightInGrams;
        this.yearBorn = yearBorn;
        this.monthBorn = monthBorn;
        this.dayOfMonthBorn = dayOfMonthBorn;
        this.isPregnant = isPregnant;
        this.isBreastfeeding = isBreastfeeding;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int getWeightInGrams() {
        return weightInGrams;
    }

    public void setWeightInGrams(int weightInGrams) {
        this.weightInGrams = weightInGrams;
    }

    int getYearBorn() {
        return yearBorn;
    }

    void setYearBorn(int yearBorn) {
        this.yearBorn = yearBorn;
    }

    int getMonthBorn() {
        return monthBorn;
    }

    public void setMonthBorn(int monthBorn) {
        this.monthBorn = monthBorn;
    }

    int getDayOfMonthBorn() {
        return dayOfMonthBorn;
    }

    public void setDayOfMonthBorn(int dayOfMonthBorn) {
        this.dayOfMonthBorn = dayOfMonthBorn;
    }

    boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    boolean isBreastfeeding() {
        return isBreastfeeding;
    }

    public void setBreastfeeding(boolean breastfeeding) {
        isBreastfeeding = breastfeeding;
    }

    private Period calculateTimePassedSinceBirthday() {
        Calendar currentTime = Calendar.getInstance();
        LocalDate localDateOfBirthday = LocalDate.of(yearBorn, monthBorn, dayOfMonthBorn);
        LocalDate currentLocalDate = LocalDate.of(
                currentTime.get(Calendar.YEAR),
                // Somehow the Calender class counts months in a weird way: January is 0, February is 1. Here we correct that by incrementing.
                currentTime.get(Calendar.MONTH) + 1,
                currentTime.get(Calendar.DAY_OF_MONTH)
        );
        return Period.between(localDateOfBirthday, currentLocalDate);
    }

    private int calculateTimePassedSinceBirthdayInMonths() {
        Period timeGap = calculateTimePassedSinceBirthday();
        return timeGap.getYears() * 12 + timeGap.getMonths();
    }

    String getAgeRepresentation() {
        Period timeGap = calculateTimePassedSinceBirthday();
        if (timeGap.getYears() == 0) {
            return timeGap.getMonths() + " months";
        }
        return timeGap.getYears() + " years and " + timeGap.getMonths() + " months";
    }

    double predictWaterNeedInMl() {
        int months = calculateTimePassedSinceBirthdayInMonths();
        double kg = (double) getWeightInGrams() / 1000;
        // If a woman is pregnant or breastfeeding, her age is ignored.
        if (isBreastfeeding) {
            return 45 * kg;
        }
        if (isPregnant) {
            return 35 * kg;
        }
        // If a person is not pregnant or breastfeeding, his age determines the water need.
        if (months <= 4) {
            return 130 * kg;
        }
        if (months <= 12) {
            return 110 * kg;
        }
        if (months <= 12 * 4) {
            return 95 * kg;
        }
        if (months <= 12 * 7) {
            return 75 * kg;
        }
        if (months <= 12 * 10) {
            return 60 * kg;
        }
        if (months <= 12 * 13) {
            return 50 * kg;
        }
        if (months <= 12 * 19) {
            return 40 * kg;
        }
        if (months <= 12 * 51) {
            return 35 * kg;
        }
        return 30 * kg;
    }

    String getFormattedWeightInKg() {
        BigDecimal weightInKg = new BigDecimal(this.weightInGrams).divide(new BigDecimal(1000), RoundingMode.HALF_UP);
        return weightInKg.toString() + " Kg";
    }

    String getBirthdayString() {
        return yearBorn + "-" + monthBorn + "-" + dayOfMonthBorn;
    }
}
