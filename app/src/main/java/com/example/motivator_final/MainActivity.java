package com.example.motivator_final;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Tämä aktiviteetti toimii alkuvalikkona kun sovellus käynnistetään.
 *
 * @author Tuomas Rajala, Maksim Ilmast, Tuuni Mustonen
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    private ArrayList<Double> velocityArray;
    private TextView currentLanguage;
    private ArrayList<String> gymAndDateList;

    /**
     * onCreated metodissa tuodaan käynnistyshetkellä SharedPreferenceistä data saliliikkeistä sekä juoksuista.
     * Tämä data viedään parametrina GymDatan sekä RunDatan singletonluokassa sijaitseviin listoihin.
     * Myös kieli valitaan joko oletuskohtaisesti tai aikaisemmin valittuna.
     *
     * @author Tuomas Rajala, Maksim Ilmast
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();                                  // haetaan kieli SharedPreferenceistä
        setContentView(R.layout.activity_main);

        // Idea on hakea parametrien (avaimet) avulla oikea lista puhelimen muistista ja syöttää ne omiin singleton olioihin.
        // Yritin yhdistää myös loadVelocityData() metodin loadGymOrDate() metodien kanssa, mutta ongelmaksi osottautui listan määäritelmä
        // ArrayList<?>().
        // Samalla määritetään avainarvot parametreihin.
        GymData.getInstance().addArray(loadStringData("Gym", "Gym"));
        GymData.getInstance().setMovement(loadStringData("GymMove", "GymMove"));
        RunData.getInstance().addArray(loadVelocityData("Velocity", "Velocity"),
                loadStringData("Date", "Date"));


        currentLanguage = findViewById(R.id.currentLanguage);
    }

    /**
     * Alkuvalikossa haetaan valittu kieli, ja näytetään se oikeassa yläkulmassa.
     * @author Tuomas Rajala
     */
    public void onStart(){
        super.onStart();
        loadLocale();
        currentLanguage.setText(getResources().getString(R.string.currentLanguage));
    }

    /**
     * Kun sovellus suljetaan, haetaan GymDatan sekä RunDatan singletoneista ajantasainen lista,
     * joka tallennetaan yhden saman metodin avulla.
     * @author Tuomas Rajala
     */
    public void onDestroy(){
        super.onDestroy();
        putAllData(GymData.getInstance().getData(), "Gym", "Gym");
        putAllData(RunData.getInstance().getDateArray(), "Date", "Date");
        putAllData(RunData.getInstance().getVelocityArray(), "Velocity", "Velocity");
        putAllData(GymData.getInstance().getGymMovements(), "GymMove", "GymMove");
    }

    /**
     * Kun käyttäjä palaa aktiviteettiin, tallennetaan myös ajantasainen lista SharedPreferenciin.
     * @author Tuomas Rajala
     */
    public void onRestart(){
        super.onRestart();
        putAllData(GymData.getInstance().getData(), "Gym", "Gym");
        putAllData(RunData.getInstance().getDateArray(), "Date", "Date");
        putAllData(RunData.getInstance().getVelocityArray(), "Velocity", "Velocity");
        putAllData(GymData.getInstance().getGymMovements(), "GymMove", "GymMove");
    }

//--------------------------------------------------------------------------------------------------
    //Alhaalla nappeja eri aktiviteetteihin, ei varmaan mitään kiinnostavaa...

    /**
     * GymButtonia painettaessa siirrytään GymActivityyn.
     */
    public void gymButton(View view) {
        Intent gymIntent = new Intent(this, GymActivity.class);
        startActivity(gymIntent);
    }

    /**
     * RunButtonia painettaessa siirrytään RunActivityyn.
     */
    public void runButton(View view) {
        Intent runIntent = new Intent(this, RunActivity.class);
        startActivity(runIntent);
    }

    /**
     * ArrayButtonia painettaessa siirrytään StatsActivityyn.
     */
    public void arrayButton(View view) {
        Intent arrayIntent = new Intent(this, StatsActivity.class);
        startActivity(arrayIntent);
    }

    /**
     * SettingsButtonia painettaessa siirrytään SettingsActivityyn.
     */
    public void settingsButton(View view){
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    /**
     * BmiButtonia painettaessa siirrytään BmiActivityyn.
     */
    public void bmiButton(View view){
        Intent bmiIntent = new Intent(this, BmiActivity.class);
        startActivity(bmiIntent);
    }

//---------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------

    /**
     * Asettaa parametrina saadun kielen valituksi kieleksi sekä tallentaa sen myöhempää varten.
     * @param lang kieli, johon on tallennettu loadLocalesta saatu arvo.
     * @author Tuuni Mustonen
     * https://proandroiddev.com/change-language-programmatically-at-runtime-on-android-5e6bc15c758
     */
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

    /**
     * Hakee Shared Preferenceihin tallennetun kielen ja syöttää se setLocale metodiin.
     * @author Tuuni Mustonen
     * https://proandroiddev.com/change-language-programmatically-at-runtime-on-android-5e6bc15c758
     */
    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }

//-----------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------

    /**
     * Noutaa SharedPreferenceihin Gsonin avulla tallennetun nopeuslistan.
     * @return nopeuslista Double-muodossa
     * @author Tuomas Rajala
     * https://stackoverflow.com/questions/14981233/android-arraylist-of-custom-objects-save-to-sharedpreferences-serializable
     */
    public ArrayList<Double> loadVelocityData(String getSharedKey, String putKeyJson){
        SharedPreferences sharedPreferences = getSharedPreferences(getSharedKey, MODE_PRIVATE);
        Gson velocity = new Gson();
        String json = sharedPreferences.getString(putKeyJson, null);
        Type type = new TypeToken<ArrayList<Double>>() {}.getType();
        velocityArray = velocity.fromJson(json, type);
        if (velocityArray == null) {
            velocityArray = new ArrayList<>();
        }
        return velocityArray;
    }

    /**
     * Noutaa SharedPreferenceihin Gsonin ja parametrien avulla tallennetun listan.
     * @param getSharedKey määrittää mikä lista haetaan muistista
     * @param putKeyJson määrittää mikä lista haetaan muistista
     * @return palauttaa parametrien määritelmien perusteella oikean listan.
     * @author Tuomas Rajala
     * https://stackoverflow.com/questions/14981233/android-arraylist-of-custom-objects-save-to-sharedpreferences-serializable
     */
    public ArrayList<String> loadStringData(String getSharedKey, String putKeyJson){
        gymAndDateList = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences(getSharedKey, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(putKeyJson, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        gymAndDateList = gson.fromJson(json, type);
        if (gymAndDateList == null) {
            gymAndDateList = new ArrayList<>();
        }
        return gymAndDateList;
    }

    /**
     * Tämä metodi tallentaa muistiin datan mistä listasta hyvänsä minkä se saa parametrina.
     * Sain yhdistettyö jokaisen eri putData metodin yhteiseksi metodiksi.
     * Se mihin tieto tallennetaan määrätään parametrien kautta.
     * @param arrayList Tyyppiä String tai Double riippuen tapauksesta.
     *                  Saadaan singleton listalta alunperin.
     * @param sharedKey Avain oikeaan "lokeroon" sharedPreferenceissä
     * @param editorKey Avain oikeaan editoriin
     * @author Tuomas Rajala
     */
    public void putAllData(ArrayList<?> arrayList, String sharedKey, String editorKey){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedKey, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString(editorKey, json);
        editor.apply();
    }
}
