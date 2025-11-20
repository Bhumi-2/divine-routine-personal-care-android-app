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
import java.util.List;

public class AyurvedaRecommedation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayurveda_recommedation);

        Intent intent = getIntent();
        String skinType = intent.getStringExtra("skinType"); // Vata, Pitta, Kapha

        // Generate recommendations based on skin type
        List<Recommendations> recommendations = generateAyurvedaRecommendations(skinType);

        // Display recommendations
        displayRecommendations(recommendations);

        // Set up the back button
        Button backButton = findViewById(R.id.backButtonn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to HomeActivity
                Intent homeIntent = new Intent(AyurvedaRecommedation.this, HomeActivity.class);
                startActivity(homeIntent);
                finish(); // Optional: finish current activity
            }
        });

        // Set up the share button
        Button shareButton = findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareRecommendations(recommendations);
            }
        });
    }

    // Generate recommendations based on Ayurvedic skin type
    private List<Recommendations> generateAyurvedaRecommendations(String skinType) {
        List<Recommendations> recommendations = new ArrayList<>();

        switch (skinType) {
            case "Vata":
                recommendations.add(new Recommendations("Lavender Cleanser", R.drawable.lavender_cleanser));
                recommendations.add(new Recommendations("Rosewater Mist", R.drawable.rosewater_mist));
                recommendations.add(new Recommendations("Sesame Oil", R.drawable.sesame_oil));
                recommendations.add(new Recommendations("Aloe Vera Face Mask", R.drawable.aloe_vera_mask));
                break;

            case "Pitta":
                recommendations.add(new Recommendations("Rose Cleanser", R.drawable.rose_cleanser));
                recommendations.add(new Recommendations("Rosewater Mist", R.drawable.rosewater_mist));
                recommendations.add(new Recommendations("Coconut Oil", R.drawable.coconut_oil));
                recommendations.add(new Recommendations("Sandalwood Face Mask", R.drawable.sandalwood_mask));
                break;

            case "Kapha":
                recommendations.add(new Recommendations("Neem Cleanser", R.drawable.neem_cleanser));
                recommendations.add(new Recommendations("Rosewater Mist", R.drawable.rosewater_mist));
                recommendations.add(new Recommendations("Almond Oil", R.drawable.almond_oil));
                recommendations.add(new Recommendations("Clay Face Mask", R.drawable.clay_mask));
                break;

            default:
                recommendations.add(new Recommendations("No recommendations available for this skin type.", 0));
                break;
        }

        return recommendations;
    }

    // Method to display recommendations
    private void displayRecommendations(List<Recommendations> recommendations) {
        LinearLayout recommendationList = findViewById(R.id.recommendationListt);
        recommendationList.removeAllViews(); // Clear previous views

        for (Recommendations recommendation : recommendations) {
            // Create a new LinearLayout for each recommendation
            LinearLayout recommendationLayout = new LinearLayout(this);
            recommendationLayout.setOrientation(LinearLayout.HORIZONTAL);
            recommendationLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            // Create an ImageView for the product image
            ImageView productImage = new ImageView(this);
            if (recommendation.getProductImage() != 0) {
                productImage.setImageResource(recommendation.getProductImage());
            } else {
                productImage.setVisibility(View.GONE); // Hide if there's no image
            }
            productImage.setLayoutParams(new LinearLayout.LayoutParams(100, 100)); // Set image size
            productImage.setAdjustViewBounds(true);

            // Create a TextView for the product description
            TextView productDescription = new TextView(this);
            productDescription.setText(recommendation.getProductDescriptions());
            productDescription.setTextSize(16);
            productDescription.setPadding(16, 16, 16, 16);
            productDescription.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            // Add the ImageView and TextView to the recommendation layout
            recommendationLayout.addView(productImage);
            recommendationLayout.addView(productDescription);

            // Add the recommendation layout to the main list
            recommendationList.addView(recommendationLayout);
        }
    }

    // Method to share recommendations
    private void shareRecommendations(List<Recommendations> recommendations) {
        StringBuilder shareContent = new StringBuilder("My Ayurvedic Skincare Routine:\n");

        for (Recommendations recommendation : recommendations) {
            shareContent.append(recommendation.getProductDescriptions()).append("\n");
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent.toString());
        startActivity(Intent.createChooser(shareIntent, "Share your routine with"));
    }
}

// Recommendations Class
class Recommendations {
    private String productDescriptions;
    private int productImages;

    public Recommendations(String productDescription, int productImage) {
        this.productDescriptions = productDescription;
        this.productImages = productImage;
    }

    public String getProductDescriptions() {
        return productDescriptions;
    }

    public int getProductImage() {
        return productImages;
    }
}

