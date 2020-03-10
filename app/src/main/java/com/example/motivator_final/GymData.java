package com.example.motivator_final;

import java.util.ArrayList;
import java.util.Collections;

public class GymData {
    private ArrayList<String> moveList, reversedList;
    private static final GymData ourInstance = new GymData();
    private String newData;

    public static GymData getInstance() {
        return ourInstance;
    }

    private GymData() {
        moveList = new ArrayList<>();
        reversedList = new ArrayList<>();
    }


    public void addNewMove(String movement, String numOfSets, String numOfReps, String weightKg, String today){
        newData = today + "    " + movement + ": " + numOfSets + "x" + numOfReps + " " + weightKg + "kg";
        this.moveList.add(newData);
    }


    public void addArray(ArrayList<String> loadedData){
        this.moveList = loadedData;
    }


    public ArrayList<String> getData(){
        return this.moveList;
    }

    public ArrayList<String> getReversedData() {
        return this.moveList;
    }

    public void clearAllData(){
        this.moveList.clear();
    }

}
