package com.example.project;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Import the Log class
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Ayurveda_hair extends AppCompatActivity {

    RadioGroup rgDoshaType, rgHairTexture;
    CheckBox cbAyurvedaAllergy;
    EditText etAyurvedaAllergyDetails;
    Button btnAyurvedaNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayurveda_hair);

        rgDoshaType = findViewById(R.id.rgDoshaType);
        rgHairTexture = findViewById(R.id.rgHairTexture);
        cbAyurvedaAllergy = findViewById(R.id.cbAyurvedaAllergy);
        etAyurvedaAllergyDetails = findViewById(R.id.etAllergyDetails);
        btnAyurvedaNext = findViewById(R.id.btnAyurvedaNext);

        // Show the allergy detail input only if the allergy checkbox is checked
        cbAyurvedaAllergy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            etAyurvedaAllergyDetails.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        btnAyurvedaNext.setOnClickListener(view -> {
            // Get selected answers for each question
            String doshaType = getSelectedRadioText(rgDoshaType);
            String hairTexture = getSelectedRadioText(rgHairTexture);
            String ayurvedaAllergyDetails = cbAyurvedaAllergy.isChecked() ? etAyurvedaAllergyDetails.getText().toString() : "None";

            if (doshaType == null || hairTexture == null || (cbAyurvedaAllergy.isChecked() && ayurvedaAllergyDetails.isEmpty())) {
                Toast.makeText(Ayurveda_hair.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(Ayurveda_hair.this, ayurveda_recommendation.class);
            intent.putExtra("doshaType", doshaType);
            intent.putExtra("hairTexture", hairTexture);
            intent.putExtra("hasAllergy", cbAyurvedaAllergy.isChecked());
            intent.putExtra("allergyDetails", ayurvedaAllergyDetails); // Pass actual allergy details
            startActivity(intent);

            // Log to confirm transition
            Log.d("Ayurveda_hair", "Transitioning to ayurveda_recommendation with doshaType: " + doshaType + " and hairTexture: " + hairTexture);
        });
    }

    private String getSelectedRadioText(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            return radioButton.getText().toString();
        }
        return null;
    }
}
