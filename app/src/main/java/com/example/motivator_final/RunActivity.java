package com.example.motivator_final;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RunActivity extends AppCompatActivity {
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private EditText time, distance;
    private TextView testView;
    private ArrayList<String> runList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        time = findViewById(R.id.time);
        distance = findViewById(R.id.distance);

    }


    //MAKSIMIN VERSIOSTA

    public void applyButton(View view){
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        int selectedRadioButton = radioGroup.getCheckedRadioButtonId();

        Date date = new Date();
        String dateToday = dateFormat.format(date);


        int distanceMeters = Integer.parseInt(distance.getText().toString());
        int timeMinutes = Integer.parseInt(time.getText().toString());
        Double velocity = distanceMeters/(timeMinutes*60)*3.6;


        RunData.getInstance().addNewRun(velocity, dateToday);
            //GymData.getInstance().addNewMove(
            //        getResources().getString(R.string.bench), setsCount, repsCount, weightKg, dateToday);

        this.finish();

    }
}
