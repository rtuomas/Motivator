package com.example.motivator_final;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

/**
 * Asetusvalikossa voidaan vaihtaa sovelluksen kieltä, poistaa kaikki tallennetut tiedot sekä
 * ohjaa tietosuojaseloste-aktiviteettiin
 * @author Tuuni Mustonen, Tuomas Rajala
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Aktiviteetin käynnistyessä ladataan kieli
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadLocale();
        setContentView(R.layout.activity_settings);

    }

    /**
     * Nappi tietosuojaseloste sivulle.
     */
    public void privacyPolicyButton(View view){
        Intent privacyIntent = new Intent(this, PrivacyActivity.class);
        startActivity(privacyIntent);
    }

    /**
     * Nappi kielivalikkoon.
     */
    public void languageButton(View view){
        showChangeLanguageDialog();
    }

    /**
     * Tämä nappi poistaa varmistusikkunan jälkeen kaiken tallennetun datan liikkeistä sekä juoksutapahtumista.
     */
    public void clearData(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.clearData));
        builder.setPositiveButton(getResources().getString(R.string.clearDataYes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                GymData.getInstance().clearAllData();    //Tarkoitus on siis poistaa Singleton oliossa olevat tiedot...
                RunData.getInstance().clearAllData();    //... ja koska ne ovat poistuneet, puhelin tallentaa tyhjän listan MainActivityssä.
                dialog.dismiss();                        //Sulkee varmistusikkunan.
                finish();                                //Palautuu MainActivityyn.
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.clearDataNo), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();     //Jos ei haluttukaan poistaa kaikkea tietoa, sulkeutuu varmistusikkuna normaalisti.
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Tässä metodissa luodaan listamuotoinen valikko, josta käyttäjä pystyy valitsemaan yhden kielen jota haluaa käyttää.
     * Jokainen kieli on numeroitu, jolloin if lausekkeella pystytään toteuttaa kielen valinta.
     *
     * @author Tuuni Mustonen
     */
    private void showChangeLanguageDialog() {
        final String[] listItems = {"suomi", "Russian", "English", "svenska", "norsk"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        mBuilder.setTitle(getResources().getString(R.string.language));
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    setLocale("fi");
                    recreate();
                }
                else if (i == 1){
                    setLocale("ru");
                    recreate();
                }
                else if (i == 2){
                    setLocale("en");
                    recreate();
                }
                else if (i == 3){
                    setLocale("sv");
                    recreate();
                }
                else if (i == 4){
                    setLocale("no");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }


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
}
