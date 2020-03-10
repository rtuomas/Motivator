package com.example.motivator_final;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> moveList, dateArray;
    private ArrayList<Double> velocityArray;
    private TextView currentLanguage;
    private ArrayList<RunData.CustomDataEntry> runDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        GymData.getInstance().addArray(loadGymData()); //ekalla kerralla

        RunData.getInstance().addArray(loadVelocityData(), loadDateData());




        currentLanguage = findViewById(R.id.currentLanguage);
    }



    public void onStart(){
        super.onStart();
        loadLocale();
        currentLanguage.setText(getResources().getString(R.string.currentLanguage));
    }

    public void onDestroy(){
        super.onDestroy();


        putGymData(GymData.getInstance().getData());

        putVelocityData(RunData.getInstance().getVelocityArray());
        putDateData(RunData.getInstance().getDateArray());



    }


    public void onRestart(){
        super.onRestart();

        putGymData(GymData.getInstance().getData());

        putVelocityData(RunData.getInstance().getVelocityArray());
        putDateData(RunData.getInstance().getDateArray());


        //TODO
    }



    //BUTTONS, probably not anything interesting..

    public void gymButton(View view) {
        Intent gymIntent = new Intent(this, GymActivity.class);
        startActivity(gymIntent);
    }

    public void runButton(View view) {
        Intent runIntent = new Intent(this, RunActivity.class);
        startActivity(runIntent);
    }

    public void arrayButton(View view) {
        Intent arrayIntent = new Intent(this, StatsActivity.class);
        startActivity(arrayIntent);
    }

    public void settingsButton(View view){
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }






    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(
                config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }



    //------------------------------------------------------------------------------------------------------------------------


    //---------------------------------------------------------------------------------------------
/*
    public ArrayList<RunData.CustomDataEntry> loadRunData(){
        SharedPreferences sharedPreferences = getSharedPreferences("run data", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("run list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        runDataList = gson.fromJson(json, type);
        if (runDataList == null) {
            runDataList = new ArrayList<>();
        }
        return runDataList;
    }
    public void putRunData(List<RunData.CustomDataEntry> runDataList){

        SharedPreferences sharedPreferences = getSharedPreferences("run data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(runDataList);
        editor.putString("run list", json);
        editor.apply();
    }

 */

    //-----------------------------------------------



    public ArrayList<String> loadDateData(){
        SharedPreferences sharedPreferences = getSharedPreferences("date data", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("date list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        dateArray = gson.fromJson(json, type);
        if (dateArray == null) {
            dateArray = new ArrayList<>();
        }
        return dateArray;
    }
    public void putDateData(ArrayList<String> dateArray){

        SharedPreferences sharedPreferences = getSharedPreferences("date data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dateArray);
        editor.putString("date list", json);
        editor.apply();
    }

    public ArrayList<Double> loadVelocityData(){
        SharedPreferences sharedPreferences = getSharedPreferences("velocity data", MODE_PRIVATE);
        Gson velocity = new Gson();
        String json = sharedPreferences.getString("velocity list", null);
        Type type = new TypeToken<ArrayList<Double>>() {}.getType();
        velocityArray = velocity.fromJson(json, type);
        if (velocityArray == null) {
            velocityArray = new ArrayList<>();
        }
        return velocityArray;
    }
    public void putVelocityData(ArrayList<Double> velocityArray){

        SharedPreferences sharedPreferences = getSharedPreferences("velocity data", MODE_PRIVATE);
        SharedPreferences.Editor velocity1 = sharedPreferences.edit();
        Gson velocity = new Gson();
        String json = velocity.toJson(velocityArray);
        velocity1.putString("velocity list", json);
        velocity1.apply();
    }
    //----------------------------------------------------







    //---------------------------------------------------------------------------------------------











    public ArrayList<String> loadGymData() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        moveList = gson.fromJson(json, type);
        if (moveList == null) {
            moveList = new ArrayList<>();
        }
        return moveList;
    }


    public void putGymData(ArrayList<String> movementList){

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(movementList);
        editor.putString("task list", json);
        editor.apply();
    }






    public void bmiButton(View view){
        Intent bmiIntent = new Intent(this, BmiActivity.class);
        startActivity(bmiIntent);
    }
}
