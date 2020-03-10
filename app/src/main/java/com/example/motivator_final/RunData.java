package com.example.motivator_final;

import android.util.Log;

import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;

public class RunData {
    private List<CustomDataEntry> runs;
    private static final RunData ourInstance = new RunData();
    private ArrayList<Integer> distanceArray, timeArray;
    private ArrayList<Double> velocityArray;
    private ArrayList<String> dateArray;
    private int newData;
    private String date;
    private Double velocity;

    public static RunData getInstance() {
        return ourInstance;
    }

    private RunData() {
        // test data




        runs = new ArrayList<>();
        distanceArray = new ArrayList<>();
        timeArray = new ArrayList<>();
        dateArray = new ArrayList<>();
        velocityArray = new ArrayList<>();
        //runsArray = new ArrayList<>();

        //runs.add(new CustomDataEntry("10.03.2020", 0.0));
        //runs.add(new CustomDataEntry("26.02.2020", 2080, 11));
        //runs.add(new CustomDataEntry("27.02.2020", 2050, 8));




    }

    public List<CustomDataEntry> getRuns() {
        return this.runs;
    }


    public class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String date, Double velocity) {
            super(date, velocity);
            //setValue("time", time);
        }
    }
/*
    public class RunRecord {
        public String date;
        public double velocity;
        public RunRecord(String date, double velocity) {
            this.date = date;
            this.velocity = velocity;
        }
    }

 */

    public void addNewRun(double velocity, String dateToday){

        this.velocityArray.add(velocity);
        this.dateArray.add(dateToday);



        ArrayList<CustomDataEntry> clonedRuns = new ArrayList<>(this.runs);
        clonedRuns.add(new CustomDataEntry(dateToday, velocity));
        this.runs = new ArrayList<CustomDataEntry>(clonedRuns);

        //this.velocityArray.add(velocity);
        //this.dateArray.add(dateToday);

        //this.runs.add(new CustomDataEntry(dateToday, velocity));
    }

    public void addArray(ArrayList<Double> velocityArray, ArrayList<String> dateArray){

        this.velocityArray = velocityArray;
        this.dateArray = dateArray;




        if (this.velocityArray.isEmpty() || this.dateArray.isEmpty()) {
            Log.d("EMPTY OR NOT", "EMPTY");
        }
        else {
            Log.d("EMPTY OR NOT", "NOT EMPTY");
        }

        ArrayList<CustomDataEntry> clonedRuns = new ArrayList<>();

        for(int i = 0; i < velocityArray.size(); i++){
            clonedRuns.add(new CustomDataEntry(this.dateArray.get(i), this.velocityArray.get(i)));
            Log.d("EMPTY OR NOT", "LOOPING");
        }

        this.runs = new ArrayList<CustomDataEntry>(clonedRuns);

        //ArrayList<CustomDataEntry> clonedRuns = new ArrayList<>();
/*
        for(Double vel : this.velocityArray) {
            Log.d("VELOCITYARRAY", String.valueOf(vel));
        }

        clonedRuns.add(new CustomDataEntry(this.date, this.velocity));
        //this.runs = new ArrayList<CustomDataEntry>(clonedRuns);

 */


    }





/*
    public void addNewRun(int distanceMeters, int timeMinutes,  String dateToday){

        this.distanceArray.add(distanceMeters);
        this.timeArray.add(timeMinutes);
        this.dateArray.add(dateToday);

        this.runs.add(new CustomDataEntry(dateToday, distanceMeters, timeMinutes));

    }

    public void loadInternalData(){
        for(int i = 0; i < distanceArray.size(); i++){
            this.runs.add(new CustomDataEntry(
                    this.dateArray.get(i), this.distanceArray.get(i), this.timeArray.get(i)));
        }

    }

 */

    //-----------------------------------------------------

    public ArrayList<Double> getVelocityArray(){
        return this.velocityArray;
    }

    public ArrayList<String> getDateArray(){
        return this.dateArray;
    }

    //-----------------------------------------------------

    public void clearAllData(){
        this.velocityArray.clear();
        this.dateArray.clear();
    }

}
