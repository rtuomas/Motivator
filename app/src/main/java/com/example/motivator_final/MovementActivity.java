package com.example.motivator_final;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Aktiviteetti käynnistyy kun StatsActivityn listViewistä painaa liikettä.
 * @author Tuomas Rajala
 * @version 1.0
 */
public class MovementActivity extends AppCompatActivity {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);

        Bundle b = getIntent().getExtras();
        int i = b.getInt(StatsActivity.MOVEMENT, 0);

        image = findViewById(R.id.imageMove);

        //Tämän osion olisi voinut järkevämminkin toteuttaa, mutta tein sen viimeisenä iltana
        // joten pyrin simppeliin ratkaisuun.
        if(GymData.getInstance().getReversedGymMovement(i).equals("squat")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.squat));
        } else if(GymData.getInstance().getReversedGymMovement(i).equals("bench")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.chest));
        } else if (GymData.getInstance().getReversedGymMovement(i).equals("deadlift")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.deadlift));
        }

        ((TextView)findViewById(R.id.movementText)).setText(
                GymData.getInstance().getReversedGymMovement(i));
    }
}
