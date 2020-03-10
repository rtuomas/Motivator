package com.example.motivator_final;

public class BmiCalculator {
    private int height, weight;

    public BmiCalculator(){

    }

    public String getBmi(int height1, int weight1){
        double heightM = height1/100.0;
        String bmi = Double.toString(weight1 / (heightM*heightM));
        return bmi;
    }

}
