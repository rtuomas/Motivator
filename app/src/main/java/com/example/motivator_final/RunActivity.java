package com.example.motivator_final;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This activity is meant to illustrate the layout, which gathers the data on the completion of running sessions
 *
 *
 * @author  Maksim Ilmast and Tuomas Rajala
 * @version 1.0
 */
public class RunActivity extends AppCompatActivity {
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private EditText time, distance;
    private TextView runWarning;

    /**
     * This is the onCreate method, which sets configuration for the layout basically.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        time = findViewById(R.id.time);
        distance = findViewById(R.id.distance);
        runWarning = findViewById(R.id.runWarning);

    }

    /**
     * This is an onClick method, which collects the data from inputs on the frontend,
     * parses the data as well as makes the data corresponding and hands out it to the data manipulating instance.
     */
    public void applyButton(View view) {
        Date date = new Date();
        String dateToday = dateFormat.format(date);



        if ((Integer.parseInt(distance.getText().toString()) > 0  &&  Integer.parseInt(time.getText().toString()) > 0)
            && (!(distance.getText().toString().isEmpty() && !(time.getText().toString().isEmpty())))) {  //Makes sure that input is not zero.
            int distanceMeters = Integer.parseInt(distance.getText().toString());
            int timeMinutes = Integer.parseInt(time.getText().toString());
            Double velocity = (distanceMeters/1000.0)/(timeMinutes/60.0);    //Converting meters/min to km/h

            RunData.getInstance().addNewRun(velocity, dateToday);
            this.finish();

        } else {
            runWarning.setText(getResources().getString(R.string.runWarning));
        }
    }
}
