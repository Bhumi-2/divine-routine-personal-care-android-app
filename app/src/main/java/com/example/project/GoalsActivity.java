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

public class GoalsActivity extends AppCompatActivity {
    private RadioGroup radioGroupConcerns, radioGroupGoals, radioGroupNaturalRemedies;
    private Button buttonNext;
    private int vataCount, pittaCount, kaphaCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        vataCount = getIntent().getIntExtra("VataCount", 0);
        pittaCount = getIntent().getIntExtra("PittaCount", 0);
        kaphaCount = getIntent().getIntExtra("KaphaCount", 0);

        radioGroupConcerns = findViewById(R.id.radioGroupConcerns);
        radioGroupGoals = findViewById(R.id.radioGroupGoals);
        radioGroupNaturalRemedies = findViewById(R.id.radioGroupNaturalRemedies);
        buttonNext = findViewById(R.id.buttonNext2);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateCounts();
                Intent intent = new Intent(GoalsActivity.this,AyurvedaSkincareRoutine.class);
                intent.putExtra("VataCount", vataCount);
                intent.putExtra("PittaCount", pittaCount);
                intent.putExtra("KaphaCount", kaphaCount);
                startActivity(intent);
            }
        });
    }

    private void evaluateCounts() {
        // Check skincare concerns
        if (radioGroupConcerns.getCheckedRadioButtonId() != -1) {
            RadioButton selected = findViewById(radioGroupConcerns.getCheckedRadioButtonId());
            if (selected.getText().toString().contains("Vata")) vataCount++;
            if (selected.getText().toString().contains("Pitta")) pittaCount++;
            if (selected.getText().toString().contains("Kapha")) kaphaCount++;
        }

        // Check skincare goals
        if (radioGroupGoals.getCheckedRadioButtonId() != -1) {
            RadioButton selected = findViewById(radioGroupGoals.getCheckedRadioButtonId());
            if (selected.getText().toString().contains("Vata")) vataCount++;
            if (selected.getText().toString().contains("Pitta")) pittaCount++;
            if (selected.getText().toString().contains("Kapha")) kaphaCount++;
        }

        // Check natural remedies preference
        if (radioGroupNaturalRemedies.getCheckedRadioButtonId() != -1) {
            RadioButton selected = findViewById(radioGroupNaturalRemedies.getCheckedRadioButtonId());
            if (selected.getText().toString().contains("Vata")) vataCount++;
            if (selected.getText().toString().contains("Pitta")) pittaCount++;
            if (selected.getText().toString().contains("Kapha")) kaphaCount++;
        }
    }
}