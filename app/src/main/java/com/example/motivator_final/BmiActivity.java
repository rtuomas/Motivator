package com.example.motivator_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BmiActivity extends AppCompatActivity {
    private EditText height, weight;
    Button bmiButton;
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

    public void bmiButton(View view){
        bmiCalculator = new BmiCalculator();
        int height1 = Integer.parseInt(height.getText().toString());
        int weight1 = Integer.parseInt(weight.getText().toString());

        String bmi = bmiCalculator.getBmi(height1, weight1);
        bmiShow.setText(bmi);
    }
}
