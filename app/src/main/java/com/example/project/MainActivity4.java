package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity4 extends AppCompatActivity {
    Button btnAyurveda, btnCosmetic, btnCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        btnAyurveda = findViewById(R.id.btnAyurveda);
        btnCosmetic = findViewById(R.id.btnCosmetic);
        btnCamera=findViewById(R.id.btnf);

        btnCosmetic.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity4.this, RoutineTypeActivity.class);
            intent.putExtra("type", "cosmetic");
            startActivity(intent);
        });

        btnAyurveda.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity4.this, AyurvedaActivity.class);
            startActivity(intent);
        });
        btnCamera.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity4.this, Before_after_s.class);
            startActivity(intent);
        });
    }
}