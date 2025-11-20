package com.example.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SkincareRoutineActivity extends AppCompatActivity {

    private TableLayout tableLayoutRoutine;
    private Button btnRecommendations; // Button to view recommendations
    private ArrayList<String> routineSteps; // Store routine steps
    private TextView articleLink1, articleLink2, articleLink3, articleLink4; // Article links

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skincare_routine);

        tableLayoutRoutine = findViewById(R.id.tableLayoutRoutine);
        btnRecommendations = findViewById(R.id.btnRecommendations); // Initialize the button
        articleLink1 = findViewById(R.id.articleLink11);
        articleLink2 = findViewById(R.id.articleLink22);
        articleLink3 = findViewById(R.id.articleLink33);
        articleLink4 = findViewById(R.id.articleLink44);

        // Get data passed from LifestyleActivity
        Intent intent = getIntent();
        int ageId = intent.getIntExtra("ageId", -1);
        String skinType = intent.getStringExtra("skinType");

        // Determine skincare routine based on skin type and age group
        routineSteps = new ArrayList<>();
        String[] steps = getRoutineForSkinTypeAndAge(skinType, ageId);
        for (String step : steps) {
            routineSteps.add(step);
            TableRow row = new TableRow(this);
            TextView stepView = new TextView(this);
            stepView.setText(step);
            stepView.setTextSize(16);
            stepView.setPadding(16, 16, 16, 16);
            row.addView(stepView);
            tableLayoutRoutine.addView(row);
        }

        // Set article links based on skin type
        setArticleLinks(skinType);

        // Set onClickListener for the Recommendations button
        btnRecommendations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the RecommendationActivity with skincare routine and data
                Intent recommendationIntent = new Intent(SkincareRoutineActivity.this, RecommendationActivity.class);
                recommendationIntent.putStringArrayListExtra("routineSteps", routineSteps);
                recommendationIntent.putExtra("skinType", skinType);
                recommendationIntent.putExtra("ageId", ageId);
                startActivity(recommendationIntent);
            }
        });
    }

    // Method to get skincare routine based on skin type and age
    private String[] getRoutineForSkinTypeAndAge(String skinType, int ageId) {
        String[] routine;

        switch (skinType) {
            case "Normal":
                routine = new String[]{
                        "Cleanse: Use a gentle, fragrance-free cleanser",
                        "Tone: Alcohol-free toner to balance skin's pH",
                        "Treat: Apply targeted serum for any concerns",
                        "Moisturize: Lightweight, oil-free moisturizer",
                        "Protect: Broad-spectrum sunscreen SPF 30+"
                };
                break;
            case "Oily":
                routine = new String[]{
                        "Cleanse: Use a foaming cleanser to remove excess oil",
                        "Tone: Alcohol-free toner to balance skin's pH",
                        "Treat: Salicylic acid treatment for acne",
                        "Moisturize: Lightweight, oil-free moisturizer",
                        "Protect: Oil-free sunscreen SPF 30+"
                };
                break;
            case "Dry":
                routine = new String[]{
                        "Cleanse: Creamy cleanser to retain moisture",
                        "Tone: Alcohol-free toner",
                        "Treat: Hydrating serum for dry patches",
                        "Moisturize: Rich, emollient moisturizer",
                        "Protect: Broad-spectrum sunscreen SPF 30+"
                };
                break;
            case "Combination":
                routine = new String[]{
                        "Cleanse: Foaming cleanser for T-zone, creamy for cheeks",
                        "Tone: Alcohol-free toner",
                        "Treat: Spot treatment for T-zone acne, hydrating serum for cheeks",
                        "Moisturize: Lightweight for T-zone, rich for cheeks",
                        "Protect: Broad-spectrum sunscreen SPF 30+"
                };
                break;
            case "Sensitive":
                routine = new String[]{
                        "Cleanse: Gentle, fragrance-free cleanser",
                        "Tone: Skip if skin is very sensitive",
                        "Treat: Consult dermatologist for gentle treatment",
                        "Moisturize: Fragrance-free moisturizer",
                        "Protect: Fragrance-free sunscreen SPF 30+"
                };
                break;
            default:
                routine = new String[]{"No routine available"};
                break;
        }

        // Add anti-aging cream step if age group is over 30
        if (ageId == R.id.radioAge30To40 || ageId == R.id.radioAgeOver40) {
            String[] extendedRoutine = new String[routine.length + 1];
            System.arraycopy(routine, 0, extendedRoutine, 0, routine.length);
            extendedRoutine[routine.length] = "Anti-aging: Apply anti-aging cream";
            routine = extendedRoutine;
        }

        return routine;
    }

    // Method to set article links based on skin type
    private void setArticleLinks(String skinType) {
        String[] articles;

        switch (skinType) {
            case "Normal":
                articles = new String[]{
                        "https://example.com/normal-skin-care-article1",
                        "https://example.com/normal-skin-care-article2",
                        "https://example.com/normal-skin-care-article3",
                        "https://example.com/normal-skin-care-article4"
                };
                break;
            case "Oily":
                articles = new String[]{
                        "https://example.com/oily-skin-care-article1",
                        "https://example.com/oily-skin-care-article2",
                        "https://example.com/oily-skin-care-article3",
                        "https://example.com/oily-skin-care-article4"
                };
                break;
            case "Dry":
                articles = new String[]{
                        "https://example.com/dry-skin-care-article1",
                        "https://example.com/dry-skin-care-article2",
                        "https://example.com/dry-skin-care-article3",
                        "https://example.com/dry-skin-care-article4"
                };
                break;
            case "Combination":
                articles = new String[]{
                        "https://example.com/combination-skin-care-article1",
                        "https://example.com/combination-skin-care-article2",
                        "https://example.com/combination-skin-care-article3",
                        "https://example.com/combination-skin-care-article4"
                };
                break;
            case "Sensitive":
                articles = new String[]{
                        "https://example.com/sensitive-skin-care-article1",
                        "https://example.com/sensitive-skin-care-article2",
                        "https://example.com/sensitive-skin-care-article3",
                        "https://example.com/sensitive-skin-care-article4"
                };
                break;
            default:
                articles = new String[0];
                break;
        }

        TextView[] articleLinks = {articleLink1, articleLink2, articleLink3, articleLink4};

        for (int i = 0; i < articles.length; i++) {
            final String url = articles[i];
            articleLinks[i].setText("Read Article " + (i + 1)); // Display a label instead of the URL
            articleLinks[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            });
        }
    }
}
