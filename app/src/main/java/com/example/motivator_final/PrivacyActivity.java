package com.example.motivator_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Tämä aktiviteetti näyttää tietosuojaselosteen.
 * @author Tuuni Mustonen
 * @version 1.0
 */
public class PrivacyActivity extends AppCompatActivity {

    /**
     * Metodi syöttää käynnistyessä tekstikenttää tietosuojaselosteen.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        TextView mMessageWindow = (TextView) findViewById(R.id.messageWindow);
        mMessageWindow.setText(getResources().getString(R.string.privacyPolicy));
    }
}
