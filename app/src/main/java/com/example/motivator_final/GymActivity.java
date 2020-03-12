package com.example.motivator_final;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Aktiviteetti sisältää valitun kuntosaliliikkeen (maastaveto, penkki, kyykky)
 * lisäksi käyttäjän valitseman sarja-, toisto- ja painomäärämän tallentamisen.
 * @author Tuomas Rajala
 * @version 2.1 3/2020
 */
public class GymActivity extends AppCompatActivity {
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private TextView warningText;
    private NumberPicker setsPicker, repsPicker, weightPicker;

    /**
     * onCreatedissa luodaan kolme NumberPicker yksikköä, jotka on tarkoitettu
     * toistojen, sarjojen ja painon määrittämiseen.
     * https://www.zoftino.com/android-number-picker-tutorial
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);

        warningText = findViewById(R.id.warningText);

        setsPicker = findViewById(R.id.setsPicker);
        setsPicker.setMinValue(1);                     //Asetetaan numerovalitsimeen (NumberPicker) minimiarvo...
        setsPicker.setMaxValue(5);                     //...sekä maksimiarvo.

        repsPicker = findViewById(R.id.repsPicker);
        repsPicker.setMinValue(1);
        repsPicker.setMaxValue(25);

        weightPicker = findViewById(R.id.weightPicker);
        String[] kilos = new String[40];                  //Painomäärän valitsemisessa arvo vaihtuu viiden kilon välein
        for (int i=0; i<kilos.length; i++)
            kilos[i] = Integer.toString(i*5+5);
        weightPicker.setDisplayedValues(kilos);
        weightPicker.setMaxValue(kilos.length-1);
        weightPicker.setMinValue(0);

        setsPicker.setWrapSelectorWheel(true);  // Päästään suoraan minimiarvosta maksimiarvoon sekä toisinpäin (jatkuva selaus)
        repsPicker.setWrapSelectorWheel(true);
        weightPicker.setWrapSelectorWheel(true);

        setsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldValue, int newValue){ //Liu'uttaessa NumberPickeriä aktiivisesti tarkistetaan päivitetty lukuarvo.
            }
        });
        repsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldValue, int newValue){
            }
        });

        weightPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldValue, int newValue){
            }
        });
    }

    /**
     * Metodi syöttää GymData-singleton listalle parametrina liikkeen, sarjan,
     * toiston sekä päivämäärän, jonka jälkeen palataan alkuvalikkoon.
     * @param v näkymä layoutissa
     */
    public void applyButton(View v){
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        int selectedRadioButton = radioGroup.getCheckedRadioButtonId();

        Date date = new Date();
        String dateToday = dateFormat.format(date);                    //Tallennetaan nykyinen päivä.

        String setsCount = Integer.toString(setsPicker.getValue());
        String repsCount = Integer.toString(repsPicker.getValue());
        String weightKg = Integer.toString(weightPicker.getValue()*5+5); //*5+5 jotta saataisiin arvo viiden lukuarvon välein.

            if(selectedRadioButton == R.id.squat){                           //Jos valittu liike on squat...
                GymData.getInstance().addNewMove(
                        getResources().getString(R.string.squat), setsCount, repsCount, weightKg, dateToday, "squat"); // ...syötetään saadut lukuarvot singleton listalle.
            } else if(selectedRadioButton == R.id.bench){
                GymData.getInstance().addNewMove(
                        getResources().getString(R.string.bench), setsCount, repsCount, weightKg, dateToday, "bench");
            } else if(selectedRadioButton == R.id.deadlift){
                GymData.getInstance().addNewMove(
                        getResources().getString(R.string.deadlift), setsCount, repsCount, weightKg, dateToday, "deadlift");
            }

        if(selectedRadioButton == -1){                          //Jos mitään liikettä ei ole valittu..
            warningText.setText(getResources().getString(R.string.gymWarning));     // ...ruudulle ilmestyy huomiotekstikenttä
        } else {
            this.finish();                                      //Palataan päävalikkoon.
        }
    }
}
