package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LifestyleActivity extends AppCompatActivity {
    RadioGroup radioGroupAge, radioGroupClimate, radioGroupLifestyle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifestyle);
        radioGroupAge = findViewById(R.id.radioGroupAge);
        radioGroupClimate = findViewById(R.id.radioGroupClimate);
        radioGroupLifestyle = findViewById(R.id.radioGroupLifestyle);
        String skinType = getIntent().getStringExtra("skinType");

        findViewById(R.id.btnNext).setOnClickListener(view -> {
            // Collect selected options
            int ageId = radioGroupAge.getCheckedRadioButtonId();
            int climateId = radioGroupClimate.getCheckedRadioButtonId();
            int lifestyleId = radioGroupLifestyle.getCheckedRadioButtonId();

            // Pass selected options to the RecommendationActivity
            Intent intent = new Intent(LifestyleActivity.this, SkincareRoutineActivity.class);
            intent.putExtra("ageId", ageId);
            intent.putExtra("skinType", skinType);
            startActivity(intent);
        });
    }
}
