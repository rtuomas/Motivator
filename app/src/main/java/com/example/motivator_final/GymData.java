package com.example.motivator_final;

import java.util.ArrayList;

/**
 * Luokka sisältää aina ajantasaisen listan kuntosaliliikkeistä sovellusta käynnistettäessä sekä uuden liikkeen lisäämisessä.
 * @author Tuomas Rajala
 * @version 2.0 3/2020
 */
public class GymData {
    private ArrayList<String> moveList, reversedList, gymMovement, reversedGymMovement;
    private static final GymData ourInstance = new GymData();
    private String newData, runMove;

    /**
     * Sisältää Singleton-mallin sekä palauttaa olion
     * @return palauttaa GymData olion.
     */
    public static GymData getInstance() {
        return ourInstance;
    }

    /**
     * Konstruktorissa luodaan liikelista
     */
    private GymData() {
        this.moveList = new ArrayList<>();
        this.gymMovement = new ArrayList<>();
    }

    /**
     * Metodi yhdistää parametreina saadut arvot ja lisää ne moveList:alle uutena liikkeenä.
     * @param movement liike, jonka käyttäjä on valinnut (maastaveto, penkki tai kyykky).
     * @param numOfSets sarjojen määrä
     * @param numOfReps toistojen määrä
     * @param weightKg kilomäärä
     * @param today päivämäärä
     */
    public void addNewMove(String movement, String numOfSets, String numOfReps, String weightKg, String today, String moveImage){
        newData = today + "    " + movement + ": " + numOfSets + "x" + numOfReps + " " + weightKg + "kg";
        this.moveList.add(newData);
        this.gymMovement.add(moveImage);
    }

    /**
     * Kun sovellus käynnistetään, moveListalle lisätään muistista saatu lista.
     * @param loadedData data, joka on saatu MainActivityssa SharedPreferenceistä.
     */
    public void addArray(ArrayList<String> loadedData){
        this.moveList = loadedData;
    }

    /**
     * Palauttaa koko listan.
     * @return ajantasainen lista
     */
    public ArrayList<String> getData(){
        return this.moveList;
    }

    /**
     * Palauttaa liikkelistan.
     * @return liikelista.
     */
    public ArrayList<String> getGymMovements(){
        return this.gymMovement;
    }

    /**
     *
     * Palauttaa käännetyltä gymMovement -listalta
     * @param i indeksiluku valitsee oikean alkion käännetyltä listalta.
     * @return arvo, joka on joko "squat", "bench" tai "deadlift"
     */
    public String getReversedGymMovement(int i){
        reversedGymMovement = new ArrayList<>();
        for(int j = this.moveList.size()-1 ; j >= 0; j--){
            reversedGymMovement.add(this.gymMovement.get(j));
        }
        return this.reversedGymMovement.get(i);
    }

    /**
     * Metodi kääntää ajantasaisen listan ja säilöö sen for-loopin avulla omaksi listaksi,
     * joka palautetaan StatsActivityn listView:lle
     * @return reversedList, käänteinen lista
     */
    public ArrayList<String> getReversedList(){
        reversedList = new ArrayList<>();
        for(int i = this.moveList.size()-1 ; i >= 0; i--){
            reversedList.add(this.moveList.get(i));
        }
        return reversedList;
    }

    /**
     * Pyyhkii ajataisaisen listan tyhjäksi.
     */
    public void clearAllData(){
        this.moveList.clear();
        this.gymMovement.clear();
    }

    /**
     * Ottaa parametrina alunperin muistista haetun listan ja syöttää sen liikelistaan.
     * @param getMovement liikelista
     */
    public void setMovement(ArrayList<String> getMovement) {
        this.gymMovement = getMovement;
    }
}
