package com.example.motivator_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GymActivity extends AppCompatActivity {
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private ArrayList<String> moveList;
    private TextView warningText;
    private NumberPicker setsPicker, repsPicker, weightPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);

        warningText = findViewById(R.id.warningText);

        setsPicker = findViewById(R.id.setsPicker);
        setsPicker.setMinValue(1);
        setsPicker.setMaxValue(5);

        repsPicker = findViewById(R.id.repsPicker);
        repsPicker.setMinValue(1);
        repsPicker.setMaxValue(25);

        weightPicker = findViewById(R.id.weightPicker);
        String[] numbers = new String[40];
        for (int i=0; i<numbers.length; i++)
            numbers[i] = Integer.toString(i*5+5);
        weightPicker.setDisplayedValues(numbers);
        weightPicker.setMaxValue(numbers.length-1);
        weightPicker.setMinValue(0);

        setsPicker.setWrapSelectorWheel(true);
        repsPicker.setWrapSelectorWheel(true);
        weightPicker.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        setsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){

            }
        });
        repsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){

            }
        });
        weightPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                int newValue = newVal * 5 + 5;
            }
        });
    }



////--------------------------------------------------------------------



    public void applyButton(View v){
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        int selectedRadioButton = radioGroup.getCheckedRadioButtonId();



        Date date = new Date();
        String dateToday = dateFormat.format(date);

        String setsCount = Integer.toString(setsPicker.getValue());
        String repsCount = Integer.toString(repsPicker.getValue());
        String weightKg = Integer.toString(weightPicker.getValue()*5+5); //times 5 plus 5 because .getValue() gives only index



        if(selectedRadioButton == R.id.squat){
            GymData.getInstance().addNewMove(
                    getResources().getString(R.string.squat), setsCount, repsCount, weightKg, dateToday);
        } else if(selectedRadioButton == R.id.bench){
            GymData.getInstance().addNewMove(
                    getResources().getString(R.string.bench), setsCount, repsCount, weightKg, dateToday);
        } else if(selectedRadioButton == R.id.deadlift){
            GymData.getInstance().addNewMove(
                    getResources().getString(R.string.deadlift), setsCount, repsCount, weightKg, dateToday);
        }

        if(selectedRadioButton == -1){
            warningText.setText(getResources().getString(R.string.warningText));
        } else {
            this.finish();
        }
    }
}
