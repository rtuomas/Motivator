package com.example.motivator_final;

import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles data saving and retrieving operations as well as the following distribution to the chart making segments
 *
 *
 * @author  Maksim Ilmast and Tuomas Rajala
 * @version 1.0
 */

public class RunData {
    private List<CustomDataEntry> runs;
    private static final RunData ourInstance = new RunData();
    private ArrayList<Integer> distanceArray, timeArray;
    private ArrayList<Double> velocityArray;
    private ArrayList<String> dateArray;

    /**
     * getInstance is an implementation of the Singleton pattern.
     * @return The function bellow returns the instance of the class.
     */
    public static RunData getInstance() {
        return ourInstance;
    }

    private RunData() {
        runs = new ArrayList<>();
        distanceArray = new ArrayList<>();
        timeArray = new ArrayList<>();
        dateArray = new ArrayList<>();
        velocityArray = new ArrayList<>();
    }

    /**
     * This method returns the entire data about running sessions, that the user has ever recorded.
     * @return Data about running sessions.
     */
    public List<CustomDataEntry> getRuns() {
        return this.runs;
    }

    public class CustomDataEntry extends ValueDataEntry {
        /**
         * This class provides a relevant data type for gathering the records about running sessions to yield them further to the chart
         * @param date The date, when the exercise was completed.
         * @param velocity User's speed divided on time of the running session
         */
        CustomDataEntry(String date, Double velocity) {
            super(date, velocity);
        }
    }

    /**
     * This method adds the recently received data to the unifying data structure.
     * @param dateToday The date, when the exercise was completed.
     * @param velocity User's speed divided on time of the running session
     */
    public void addNewRun(double velocity, String dateToday){
        this.velocityArray.add(velocity);
        this.dateArray.add(dateToday);
        ArrayList<CustomDataEntry> clonedRuns = new ArrayList<>(this.runs);
        clonedRuns.add(new CustomDataEntry(dateToday, velocity));
        this.runs = new ArrayList<CustomDataEntry>(clonedRuns);
    }

    /**
     * This method adds the recently received data to the unifying data structure.
     * @param dateArray The array of dates, when the exercise was completed.
     * @param velocityArray User's speed divided on time of the running session containing array.
     */
    public void addArray(ArrayList<Double> velocityArray, ArrayList<String> dateArray){
        this.velocityArray = velocityArray;
        this.dateArray = dateArray;
        ArrayList<CustomDataEntry> clonedRuns = new ArrayList<>();
        for(int i = 0; i < this.velocityArray.size(); i++){
            clonedRuns.add(new CustomDataEntry(this.dateArray.get(i), this.velocityArray.get(i)));
        }
        this.runs = new ArrayList<CustomDataEntry>(clonedRuns);
    }

    /**
     * This method returns the data on user's speed divided on time of the running session containing array.
     * @return Data on user´s velocity
     */
    public ArrayList<Double> getVelocityArray(){
        return this.velocityArray;
    }

    /**
     * This method returns data on when the exercises was completed.
     * @return Data on dates of the running sessions
     */
    public ArrayList<String> getDateArray(){
        return this.dateArray;
    }

    /**
     * This method deletes all the saved data.
     */
    public void clearAllData(){
        this.velocityArray.clear();
        this.dateArray.clear();
        this.runs.clear();
    }
}