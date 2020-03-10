package com.example.motivator_final;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private ArrayList<String> moveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadLocale();
        setContentView(R.layout.activity_settings);

    }


    public void privacyPolicyButton(View view){
        Intent privacyIntent = new Intent(this, PrivacyActivity.class);
        startActivity(privacyIntent);
    }

    public void languageButton(View view){
        showChangeLanguageDialog();
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    private void showChangeLanguageDialog() {
        final String[] listItems = {"suomi", "русский", "English"};
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

                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
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






    public void clearData(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getResources().getString(R.string.clearData));
        builder.setPositiveButton(getResources().getString(R.string.clearDataYes), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                GymData.getInstance().clearAllData(); //Clears the data inside GymData singleton class
                RunData.getInstance().clearAllData();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.clearDataNo), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }




    public ArrayList<String> loadData() {
        moveList = GymData.getInstance().getData();
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






}
