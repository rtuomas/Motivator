package com.example.motivator_final;

/**
 * Luokan tarkoitus on laskea painoindeksi.
 * @version 1.1 3/2020
 * @author Tuuni Mustonen
 */
public class BmiCalculator {

    public BmiCalculator(){
    }

    /**
     * Palauttaa pituudesta ja painosta laskettu painoindeksi.
     * @param height käyttäjän syöttämä pituus.
     * @param weight käyttäjän syöttämä paino.
     * @return double pituudesta ja painosta laskettu painoindeksi.
     */
    public double getBmi(int height, int weight){
        double heightM = height/100.0;                  //Muutetaan ensin pituus senttimetreistä metreiksi.
        double bmi = weight / (heightM*heightM);        //Painoindeksi lasketaan paino (kg) jaettuna pituuden (m) neliöllä.
        return bmi;
    }
}
