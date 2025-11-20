package com.example.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RoutineTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_type);
        findViewById(R.id.btnDayRoutine).setOnClickListener(view -> {
            Intent intent = new Intent(this, SkinGoalActivity.class);
            intent.putExtra("routine", "day");
            startActivity(intent);
        });

        findViewById(R.id.btnNightRoutine).setOnClickListener(view -> {
            Intent intent = new Intent(this, SkinGoalActivity.class);
            intent.putExtra("routine", "night");
            startActivity(intent);
        });
    }
}