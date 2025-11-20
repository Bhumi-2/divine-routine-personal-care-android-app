package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Main Activity Class
public class RecommendationActivity extends AppCompatActivity {

    private Map<String, List<Recommendation>> recommendationMap = new HashMap<>();
    private List<Recommendation> recommendations = new ArrayList<>(); // Store generated recommendations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        initializeRecommendations();

        // Get user input from the previous activity
        Intent intent = getIntent();
        String skinType = intent.getStringExtra("skinType");
        int ageId = intent.getIntExtra("ageId", -1);

        // Generate recommendations
        recommendations = generateRecommendations(skinType, ageId);

        // Display recommendations
        displayRecommendations(recommendations);

        // Set up the back button
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to HomeActivity
                Intent homeIntent = new Intent(RecommendationActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish(); // Optional: finish current activity
            }
        });

        // Set up the share button
        Button shareButton = findViewById(R.id.shareButtonn);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareRecommendations();
            }
        });
    }

    // Method to initialize the map with all possible recommendations
    private void initializeRecommendations() {
        String[] skinTypes = {"Normal", "Oily", "Dry", "Combination", "Sensitive"};
        String[] ageGroups = {"Under20", "20to30", "30to40", "Over40"};

        for (String skinType : skinTypes) {
            for (String ageGroup : ageGroups) {
                List<Recommendation> recommendations = generateRecommendationsForKey(skinType, ageGroup);
                recommendationMap.put(skinType + "_" + ageGroup, recommendations);
            }
        }
    }

    // Generate recommendations based on the combination of parameters
    private List<Recommendation> generateRecommendationsForKey(String skinType, String ageGroup) {
        List<Recommendation> recommendations = new ArrayList<>();

        // Recommendations for Normal Skin
        if (skinType.equals("Normal")) {
            recommendations.add(new Recommendation("Gentle Fragrance-Free Cleanser", R.drawable.gentle_fragrance_free_cleanser));
            recommendations.add(new Recommendation("Alcohol-Free Toner", R.drawable.alcohol_free_toner));
            recommendations.add(new Recommendation("Hydrating Serum", R.drawable.hydrating_serumm));
            recommendations.add(new Recommendation("Lightweight Oil-Free Moisturizer", R.drawable.lightweight_moisturizer));
            recommendations.add(new Recommendation("Broad-Spectrum Sunscreen SPF 30", R.drawable.spf_30_sunscreen));
        }
        // Recommendations for Oily Skin
        else if (skinType.equals("Oily")) {
            recommendations.add(new Recommendation("Gentle Foaming Cleanser", R.drawable.gentle_foaming_cleanser));
            recommendations.add(new Recommendation("Alcohol-Free Toner", R.drawable.alcohol_free_toner));
            recommendations.add(new Recommendation("Salicylic Acid Treatment", R.drawable.salicylic_acid_treatment));
            recommendations.add(new Recommendation("Lightweight Oil-Free Moisturizer", R.drawable.lightweight_oil_free_moisturizer));
            recommendations.add(new Recommendation("Oil-Free Sunscreen SPF 30", R.drawable.oil_free_sunscreen));
        }
        // Recommendations for Dry Skin
        else if (skinType.equals("Dry")) {
            recommendations.add(new Recommendation("Gentle Creamy Cleanser", R.drawable.gentle_foaming_cleanser));
            recommendations.add(new Recommendation("Alcohol-Free Toner", R.drawable.alcohol_free_toner));
            recommendations.add(new Recommendation("Hydrating Serum", R.drawable.hydrating_serumm));
            recommendations.add(new Recommendation("Rich Emollient Moisturizer", R.drawable.lightweight_moisturizer));
            recommendations.add(new Recommendation("Broad-Spectrum Sunscreen SPF 30", R.drawable.spf_30_sunscreen));
        }
        // Recommendations for Combination Skin
        else if (skinType.equals("Combination")) {
            recommendations.add(new Recommendation("Gentle Foaming Cleanser", R.drawable.gentle_foaming_cleanser));
            recommendations.add(new Recommendation("Alcohol-Free Toner", R.drawable.alcohol_free_toner));
            recommendations.add(new Recommendation("Spot Treatment", R.drawable.spot_treatment));
            recommendations.add(new Recommendation("Lightweight Oil-Free Moisturizer", R.drawable.lightweight_oil_free_moisturizer));
            recommendations.add(new Recommendation("Broad-Spectrum Sunscreen SPF 30", R.drawable.spf_30_sunscreen));
        }
        // Recommendations for Sensitive Skin
        else if (skinType.equals("Sensitive")) {
            recommendations.add(new Recommendation("Gentle Fragrance-Free Cleanser", R.drawable.gentle_fragrance_free_cleanser));
            recommendations.add(new Recommendation("Alcohol-Free Toner (Optional)", R.drawable.alcohol_free_toner));
            recommendations.add(new Recommendation("Fragrance-Free Moisturizer", R.drawable.fragrance_free_moisturizer));
            recommendations.add(new Recommendation("Broad-Spectrum Sunscreen SPF 30", R.drawable.spf_30_sunscreen));
        }

        // Add anti-aging cream recommendation if age group is Over30
        if (ageGroup.equals("30to40") || ageGroup.equals("Over40")) {
            recommendations.add(new Recommendation("Anti-aging Cream", R.drawable.anti_aging_cream));
        }

        return recommendations;
    }

    // Generate and retrieve recommendations
    private List<Recommendation> generateRecommendations(String skinType, int ageId) {
        String ageGroup = (ageId == R.id.radioAgeUnder20) ? "Under20" :
                (ageId == R.id.radioAge20To30) ? "20to30" :
                        (ageId == R.id.radioAge30To40) ? "30to40" : "Over40";

        return recommendationMap.getOrDefault(skinType + "_" + ageGroup, new ArrayList<>()); // Return empty list if no match
    }

    // Method to display recommendations
    private void displayRecommendations(List<Recommendation> recommendations) {
        LinearLayout recommendationList = findViewById(R.id.recommendationList);
        recommendationList.removeAllViews(); // Clear previous views

        if (recommendations.isEmpty()) {
            TextView noRecommendations = new TextView(this);
            noRecommendations.setText("No recommendations available.");
            recommendationList.addView(noRecommendations);
        } else {
            for (Recommendation recommendation : recommendations) {
                // Create a new LinearLayout for each recommendation
                LinearLayout recommendationLayout = new LinearLayout(this);
                recommendationLayout.setOrientation(LinearLayout.HORIZONTAL);
                recommendationLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                // Create an ImageView for the product image
                ImageView productImage = new ImageView(this);
                productImage.setImageResource(recommendation.getProductImage());
                productImage.setLayoutParams(new LinearLayout.LayoutParams(100, 100)); // Size in pixels
                productImage.setAdjustViewBounds(true);

                // Create a TextView for the product name
                TextView productName = new TextView(this);
                productName.setText(recommendation.getProductName());
                productName.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                // Add the ImageView and TextView to the LinearLayout
                recommendationLayout.addView(productImage);
                recommendationLayout.addView(productName);

                // Add the recommendation layout to the main list
                recommendationList.addView(recommendationLayout);
            }
        }
    }

    // Method to share recommendations as text
    private void shareRecommendations() {
        StringBuilder shareText = new StringBuilder("Skincare Recommendations:\n\n");

        for (Recommendation recommendation : recommendations) {
            shareText.append("- ").append(recommendation.getProductName()).append("\n");
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText.toString());
        shareIntent.setType("text/plain");

        startActivity(Intent.createChooser(shareIntent, "Share recommendations via"));
    }
}

// Recommendation Class
class Recommendation {
    private String productName;
    private int productImage;

    public Recommendation(String productName, int productImage) {
        this.productName = productName;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductImage() {
        return productImage;
    }
}



