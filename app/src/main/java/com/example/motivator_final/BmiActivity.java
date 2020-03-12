package com.example.motivator_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Tämä aktiviteetti esittää painoindeksin.
 * @author Tuuni Mustonen
 * @version 1.2 3/2020
 */
public class BmiActivity extends AppCompatActivity {
    private EditText height, weight;
    private Button bmiButton;
    private BmiCalculator bmiCalculator;
    private TextView bmiShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        height = findViewById(R.id.heightId);
        weight = findViewById(R.id.weightId);
        bmiButton = findViewById(R.id.bmiApplyButton);
        bmiShow = findViewById(R.id.bmiShow);

    }

    /**
     * Kun nappia on painettu, haetaan käyttäjän syöttämät lukuarvot (pituus ja paino)
     * ja tallennetaan ne Integer tyyppisiin muuttujiin. Nämä muuttujat syötetään parametrina
     * bmiCalculator-olioon, joka laskee ja palauttaa painoindeksin arvon. Tätä arvoa verrataan
     * sitä vastaavaan painoluokkaan. Lopuksi tulos syötetään ruutuun yhden desimaalin tarkkuudella.
     * @param view näkymä layoutissa (nappi).
     */
    public void bmiButton(View view){
        bmiCalculator = new BmiCalculator();
        int height1 = Integer.parseInt(height.getText().toString());
        int weight1 = Integer.parseInt(weight.getText().toString());
        String bmiLabel;

        double bmi = Math.round(bmiCalculator.getBmi(height1, weight1)*10/10.0); // Haetaan bmiCalculator oliosta saatu painoindeksiarvo...
                                                                                 // ...ja pyöristetään saatu arvo yhteen desimaaliin.
        if(bmi < 15){
            bmiLabel = getResources().getString(R.string.bmi0);
        } else if (bmi < 16){
            bmiLabel = getResources().getString(R.string.bmi1);                                   //Verrataan saatua bmi-arvoa sitä vastaavaan painoluokkaan.
        } else if (bmi < 18.5){
            bmiLabel = getResources().getString(R.string.bmi2);
        } else if (bmi < 25){
            bmiLabel = getResources().getString(R.string.bmi3);
        } else if (bmi < 30){
            bmiLabel = getResources().getString(R.string.bmi4);
        } else if (bmi < 35){
            bmiLabel = getResources().getString(R.string.bmi5);
        } else if (bmi < 40){
            bmiLabel = getResources().getString(R.string.bmi6);
        } else {
            bmiLabel = getResources().getString(R.string.bmi7);
        }

        bmiLabel = bmi + "\n" + bmiLabel;  //Yhdistetään painoindeksi sekä sitä vastaava painoluokka
        bmiShow.setText(bmiLabel);         //Esitetään tulos ruudulla.

    }
}
