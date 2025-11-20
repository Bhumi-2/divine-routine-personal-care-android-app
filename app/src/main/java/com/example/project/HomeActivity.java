package com.example.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnSkinType = findViewById(R.id.btn_skin_type);
        Button btnHairType = findViewById(R.id.btn_hair_type);
        Button btnProductRec = findViewById(R.id.btn_pr); // ✅ New Button

        // Navigate to Skin Type Routine activity
        btnSkinType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity4.class);
                startActivity(intent);
            }
        });

        // Navigate to Hair Type Routine activity
        btnHairType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity5.class);
                startActivity(intent);
            }
        });

        // ✅ Open Product Recommendation URL in browser
        btnProductRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://skinhaircare-aehihnwwbeefjcf4pbxesi.streamlit.app/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}

