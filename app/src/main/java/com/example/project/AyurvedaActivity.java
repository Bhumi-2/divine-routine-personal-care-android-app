package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AyurvedaActivity extends AppCompatActivity {
    private RadioGroup radioGroupTexture, radioGroupTemperature, radioGroupClimate, radioGroupSensitivity;
    private Button buttonNext;
    private int vataCount = 0, pittaCount = 0, kaphaCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayurveda);
        radioGroupTexture = findViewById(R.id.radioGroupTexture);
        radioGroupTemperature = findViewById(R.id.radioGroupTemperature);
        radioGroupClimate = findViewById(R.id.radioGroupClimate);
        radioGroupSensitivity = findViewById(R.id.radioGroupSensitivity);
        buttonNext = findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateCounts();
                Intent intent = new Intent(AyurvedaActivity.this,AyurvedaLifestyleActivity.class);
                intent.putExtra("VataCount", vataCount);
                intent.putExtra("PittaCount", pittaCount);
                intent.putExtra("KaphaCount", kaphaCount);
                startActivity(intent);
            }
        });
    }

    private void evaluateCounts() {
        // Check skin texture
        if (radioGroupTexture.getCheckedRadioButtonId() != -1) {
            RadioButton selected = findViewById(radioGroupTexture.getCheckedRadioButtonId());
            if (selected.getText().toString().contains("Vata")) vataCount++;
            if (selected.getText().toString().contains("Pitta")) pittaCount++;
            if (selected.getText().toString().contains("Kapha")) kaphaCount++;
        }

        // Check skin temperature
        if (radioGroupTemperature.getCheckedRadioButtonId() != -1) {
            RadioButton selected = findViewById(radioGroupTemperature.getCheckedRadioButtonId());
            if (selected.getText().toString().contains("Vata")) vataCount++;
            if (selected.getText().toString().contains("Pitta")) pittaCount++;
            if (selected.getText().toString().contains("Kapha")) kaphaCount++;
        }

        // Check climate reaction
        if (radioGroupClimate.getCheckedRadioButtonId() != -1) {
            RadioButton selected = findViewById(radioGroupClimate.getCheckedRadioButtonId());
            if (selected.getText().toString().contains("Vata")) vataCount++;
            if (selected.getText().toString().contains("Pitta")) pittaCount++;
            if (selected.getText().toString().contains("Kapha")) kaphaCount++;
        }

        // Check sensitivity
        if (radioGroupSensitivity.getCheckedRadioButtonId() != -1) {
            RadioButton selected = findViewById(radioGroupSensitivity.getCheckedRadioButtonId());
            if (selected.getText().toString().contains("Vata")) vataCount++;
            if (selected.getText().toString().contains("Pitta")) pittaCount++;
            if (selected.getText().toString().contains("Kapha")) kaphaCount++;
        }
    }
}