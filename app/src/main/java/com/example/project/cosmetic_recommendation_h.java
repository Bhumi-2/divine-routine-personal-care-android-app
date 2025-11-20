package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cosmetic_recommendation_h extends AppCompatActivity {

    ListView listView;
    ProductAdapter adapter;
    List<Product> cosmeticProducts;
    TextView tvTotalPrice;
    Button btnCustomRoutine;

    String hairType, hairCondition, allergyDetails;
    boolean hasAllergy;

    private TextToSpeech tts2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_recommendation_h);

        listView = findViewById(R.id.lvCosmeticProducts);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnCustomRoutine = findViewById(R.id.btnCustomRoutine);

        hairType = getIntent().getStringExtra("hairType");
        hairCondition = getIntent().getStringExtra("hairCondition");
        hasAllergy = getIntent().getBooleanExtra("hasAllergy", false);
        allergyDetails = getIntent().getStringExtra("allergyDetails"); // e.g., "fragrance, sls"

        btnCustomRoutine.setOnClickListener(v -> {
            Intent intent = new Intent(cosmetic_recommendation_h.this, Custom_hair_routine.class);
            intent.putExtra("hairType", hairType);
            intent.putExtra("hairCondition", hairCondition);
            intent.putExtra("hasAllergy", hasAllergy);
            intent.putExtra("allergyDetails", allergyDetails);
            startActivity(intent);
        });

        cosmeticProducts = new ArrayList<>();
        setupCosmeticProducts();
        recommendProducts();     // does lemma+stem allergen filtering
        calculateTotalPrice();

        // --- Init TTS and read ALL recommendations ---
        if (adapter != null && adapter.getCount() > 0) {
            tts2 = new TextToSpeech(this, status -> {
                if (status == TextToSpeech.SUCCESS) {
                    tts2.setLanguage(Locale.getDefault());
                    tts2.setSpeechRate(1.0f);
                    tts2.setPitch(1.0f);
                    speakAllRecommendations(); // reads the full list, one by one
                }
            });
        }
    }

    private void setupCosmeticProducts() {
        cosmeticProducts.add(new Product("Moisturizing Shampoo", "Ideal for dry hair", 10,
                "Water, Sodium Laureth Sulfate, Cocamidopropyl Betaine, Fragrance", "Sodium Laureth Sulfate"));
        cosmeticProducts.add(new Product("Volume Conditioner", "Adds volume to thin hair", 12,
                "Water, Polyquaternium-7, Dimethicone, Fragrance", "Dimethicone"));
        cosmeticProducts.add(new Product("Clarifying Shampoo", "Removes excess oil and buildup", 8,
                "Water, Sodium Lauryl Sulfate, Citric Acid, Fragrance", "Sodium Lauryl Sulfate"));
        cosmeticProducts.add(new Product("Hypoallergenic Conditioner", "Gentle for sensitive scalps", 15,
                "Water, Cetyl Alcohol, Stearyl Alcohol, No added fragrance", "None"));
        cosmeticProducts.add(new Product("Hydrating Hair Mask", "Provides moisture for dry, wavy hair", 13,
                "Water, Shea Butter, Glycerin, Fragrance", "Shea Butter"));
        cosmeticProducts.add(new Product("Lightweight Conditioner", "Perfect for oily hair, adds shine", 11,
                "Water, Polyquaternium-10, Fragrance, Silicone-free", "None"));
        cosmeticProducts.add(new Product("Intensive Moisture Treatment", "For extremely dry, coily hair", 16,
                "Water, Coconut Oil, Shea Butter, Fragrance", "Coconut Oil"));
        cosmeticProducts.add(new Product("Gentle Care Shampoo", "Suitable for sensitive and coily hair", 14,
                "Water, Sodium Cocoyl Isethionate, No parabens, fragrance-free", "None"));
    }

    private void recommendProducts() {
        List<Product> recommendedProducts = new ArrayList<>();

        // Canonicalize/normalize user allergens (sls/sles/sulphates → sulfate, perfumes → fragrance, …)
        String allergyCsv = hasAllergy ? NLPUtils.canonicalizeAllergens(allergyDetails) : "";

        for (Product product : cosmeticProducts) {
            // Lemma+stem allergen check with "-free" negation handling
            boolean containsAllergen = NLPUtils.productContainsAllergen(product.getIngredients(), allergyCsv);
            if (containsAllergen) continue;

            // Hair-type/condition rules (with reason text)
            if ("Curly".equals(hairType)) {
                if ("Dry".equals(hairCondition) && product.getName().equals("Moisturizing Shampoo")) {
                    product.setReason("Matches Curly/Dry & safe vs your allergens.");
                    recommendedProducts.add(product);
                } else if ("Normal".equals(hairCondition) && product.getName().equals("Volume Conditioner")) {
                    product.setReason("Matches Curly/Normal & safe vs your allergens.");
                    recommendedProducts.add(product);
                }
            } else if ("Straight".equals(hairType)) {
                if ("Oily".equals(hairCondition) && product.getName().equals("Clarifying Shampoo")) {
                    product.setReason("Matches Straight/Oily & safe vs your allergens.");
                    recommendedProducts.add(product);
                } else if ("Sensitive".equals(hairCondition) && product.getName().equals("Hypoallergenic Conditioner")) {
                    product.setReason("Matches Straight/Sensitive & safe vs your allergens.");
                    recommendedProducts.add(product);
                }
            } else if ("Wavy".equals(hairType)) {
                if ("Dry".equals(hairCondition) && product.getName().equals("Hydrating Hair Mask")) {
                    product.setReason("Matches Wavy/Dry & safe vs your allergens.");
                    recommendedProducts.add(product);
                } else if ("Oily".equals(hairCondition) && product.getName().equals("Lightweight Conditioner")) {
                    product.setReason("Matches Wavy/Oily & safe vs your allergens.");
                    recommendedProducts.add(product);
                }
            } else if ("Coily".equals(hairType)) {
                if ("Dry".equals(hairCondition) && product.getName().equals("Intensive Moisture Treatment")) {
                    product.setReason("Matches Coily/Dry & safe vs your allergens.");
                    recommendedProducts.add(product);
                } else if ("Sensitive".equals(hairCondition) && product.getName().equals("Gentle Care Shampoo")) {
                    product.setReason("Matches Coily/Sensitive & safe vs your allergens.");
                    recommendedProducts.add(product);
                }
            }
        }

        // Fallback: if rule-based didn’t hit, return allergen-safe items
        if (recommendedProducts.isEmpty()) {
            for (Product product : cosmeticProducts) {
                if (!NLPUtils.productContainsAllergen(product.getIngredients(), allergyCsv)) {
                    product.setReason("Safe vs your allergens; closest match available.");
                    recommendedProducts.add(product);
                }
            }
        }

        adapter = new ProductAdapter(this, recommendedProducts);
        listView.setAdapter(adapter);
    }

    private void calculateTotalPrice() {
        double total = 0;
        if (adapter != null && adapter.getProducts() != null) {
            for (Product product : adapter.getProducts()) {
                total += product.getPrice();
            }
        }
        tvTotalPrice.setText("Total: Rs." + total);
    }

    // ---------- TTS: read ALL recommendations ----------

    private void speakAllRecommendations() {
        if (tts2 == null || adapter == null || adapter.getProducts() == null) return;
        List<Product> items = adapter.getProducts();
        if (items.isEmpty()) return;

        // Intro
        tts2.speak("Here are your recommendations.", TextToSpeech.QUEUE_FLUSH, null, "rec_intro");

        // Queue each item (TextToSpeech will speak them sequentially)
        for (int i = 0; i < items.size(); i++) {
            Product p = items.get(i);
            String why = (p.getReason() != null && !p.getReason().isEmpty())
                    ? p.getReason()
                    : p.getDescription();

            String line = String.format(
                    Locale.getDefault(),
                    "%d. %s. %s. Price, rupees %s.",
                    i + 1,
                    p.getName(),
                    why,
                    formatPrice(p.getPrice())
            );
            tts2.speak(line, TextToSpeech.QUEUE_ADD, null, "rec_" + i);
        }

        // Optional closing
        tts2.speak("Say repeat recommendations to hear them again.", TextToSpeech.QUEUE_ADD, null, "rec_outro");
    }

    private String formatPrice(double price) {
        // Avoid long decimals when prices are integers
        if (Math.abs(price - Math.rint(price)) < 1e-6) {
            return String.format(Locale.getDefault(), "%.0f", price);
        }
        return String.format(Locale.getDefault(), "%.2f", price);
    }

    @Override protected void onDestroy() {
        if (tts2 != null) { tts2.stop(); tts2.shutdown(); }
        super.onDestroy();
    }
}
