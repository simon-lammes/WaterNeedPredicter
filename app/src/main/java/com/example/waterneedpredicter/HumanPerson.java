package com.example.waterneedpredicter;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

@Entity
public class HumanPerson {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int weightInGrams;
    private int yearBorn;
    private int monthBorn;
    private int dayOfMonthBorn;

    HumanPerson(String name, int weightInGrams, int yearBorn, int monthBorn, int dayOfMonthBorn) {
        this.name = name;
        this.weightInGrams = weightInGrams;
        this.yearBorn = yearBorn;
        this.monthBorn = monthBorn;
        this.dayOfMonthBorn = dayOfMonthBorn;
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

    int calculateTimePassedSinceBirthdayInMonths() {
        Calendar currentTime = Calendar.getInstance();
        LocalDate localDateOfBirthday = LocalDate.of(yearBorn, monthBorn, dayOfMonthBorn);
        LocalDate currentLocalDate = LocalDate.of(
                currentTime.get(Calendar.YEAR),
                // Somehow the Calender class counts months in a weird way: January is 0, February is 1. Here we correct that by incrementing.
                currentTime.get(Calendar.MONTH) + 1,
                currentTime.get(Calendar.DAY_OF_MONTH)
        );
        Period timeGap = Period.between(localDateOfBirthday, currentLocalDate);
        return timeGap.getYears() * 12 + timeGap.getMonths();
    }

    int predictWaterNeedInMl() {
        return 4_200;
    }

    String getFormattedWeightInKg() {
        BigDecimal weightInKg = new BigDecimal(this.weightInGrams).divide(new BigDecimal(1000));
        return weightInKg.toString() + " Kg";
    }

    String getBirthdayString() {
        return yearBorn + "-" + monthBorn + "-" + dayOfMonthBorn;
    }
}
