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

public class AyurvedaLifestyleActivity extends AppCompatActivity {
    private RadioGroup radioGroupEnergy, radioGroupDiet, radioGroupStress;
    private Button buttonNext;
    private int vataCount, pittaCount, kaphaCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayurveda_lifestyle);
        vataCount = getIntent().getIntExtra("VataCount", 0);
        pittaCount = getIntent().getIntExtra("PittaCount", 0);
        kaphaCount = getIntent().getIntExtra("KaphaCount", 0);

        radioGroupEnergy = findViewById(R.id.radioGroupEnergy);
        radioGroupDiet = findViewById(R.id.radioGroupDiet);
        radioGroupStress = findViewById(R.id.radioGroupStress);
        buttonNext = findViewById(R.id.buttonNext1);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateCounts();
                Intent intent = new Intent(AyurvedaLifestyleActivity.this, GoalsActivity.class);
                intent.putExtra("VataCount", vataCount);
                intent.putExtra("PittaCount", pittaCount);
                intent.putExtra("KaphaCount", kaphaCount);
                startActivity(intent);
            }
        });
    }

    private void evaluateCounts() {
        // Check energy levels
        if (radioGroupEnergy.getCheckedRadioButtonId() != -1) {
            RadioButton selected = findViewById(radioGroupEnergy.getCheckedRadioButtonId());
            if (selected.getText().toString().contains("Vata")) vataCount++;
            if (selected.getText().toString().contains("Pitta")) pittaCount++;
            if (selected.getText().toString().contains("Kapha")) kaphaCount++;
        }

        // Check diet
        if (radioGroupDiet.getCheckedRadioButtonId() != -1) {
            RadioButton selected = findViewById(radioGroupDiet.getCheckedRadioButtonId());
            if (selected.getText().toString().contains("Vata")) vataCount++;
            if (selected.getText().toString().contains("Pitta")) pittaCount++;
            if (selected.getText().toString().contains("Kapha")) kaphaCount++;
        }

        // Check stress management
        if (radioGroupStress.getCheckedRadioButtonId() != -1) {
            RadioButton selected = findViewById(radioGroupStress.getCheckedRadioButtonId());
            if (selected.getText().toString().contains("Vata")) vataCount++;
            if (selected.getText().toString().contains("Pitta")) pittaCount++;
            if (selected.getText().toString().contains("Kapha")) kaphaCount++;
        }
    }
}