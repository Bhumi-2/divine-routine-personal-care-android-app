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

public class SkinTypeActivity extends AppCompatActivity {
    private RadioGroup radioGroupSkinType; // Declare RadioGroup
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_type);
        radioGroupSkinType = findViewById(R.id.radioGroupSkinType);
        btnNext = findViewById(R.id.btnNext);

        // Set an OnClickListener for the button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected radio button's ID
                int selectedId = radioGroupSkinType.getCheckedRadioButtonId();

                if (selectedId != -1) { // Check if any option is selected
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String selectedSkinType = selectedRadioButton.getText().toString(); // Get the text of the selected RadioButton

                    // Create an Intent to start the next activity
                    Intent intent = new Intent(SkinTypeActivity.this, LifestyleActivity.class);
                    intent.putExtra("skinType", selectedSkinType); // Pass the selected skin type
                    startActivity(intent); // Start the next activity
                } else {
                    // Optionally, show a message if no option is selected
                    // Toast.makeText(SkinTypeActivity.this, "Please select a skin type", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}