package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ayurveda_recommendation extends AppCompatActivity {
    ListView listView;
    ProductAdapter adapter;
    List<Product> ayurvedaProducts;
    TextView tvTotalPrice;
    Button btnCustomRoutine_a;

    // Variables to hold intent data
    String doshaType;
    String hairTexture;
    boolean hasAllergy;
    String allergyDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayurveda_recommendation);

        listView = findViewById(R.id.lvAyurvedaProducts);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnCustomRoutine_a = findViewById(R.id.btnCustomRoutine_a);

        // Get data from the intent
        Intent intent = getIntent();
        doshaType = intent.getStringExtra("doshaType");
        hairTexture = intent.getStringExtra("hairTexture");
        hasAllergy = intent.getBooleanExtra("hasAllergy", false);
        allergyDetails = intent.getStringExtra("allergyDetails");

        // Set up button click listener to navigate to CustomRoutineActivity
        btnCustomRoutine_a.setOnClickListener(v -> {
            Intent i=new Intent(ayurveda_recommendation.this, Custom_hair_routine_a.class);
            i.putExtra("hairTexture", hairTexture);
            i.putExtra("doshaType", doshaType);
            startActivity(i);
        });

        // Initialize the product list
        ayurvedaProducts = new ArrayList<>();
        setupAyurvedaProducts();

        // Initialize the adapter with an empty list to avoid null errors
        adapter = new ProductAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

        // Automatically recommend products
        recommendProducts();

        // Calculate total price
        calculateTotalPrice();
    }

    private void setupAyurvedaProducts() {
        // Add products
        ayurvedaProducts.add(new Product("Bhringraj Oil", "Prevents hair loss and promotes growth.", 12, "Bhringraj extract, Sesame oil", "Sesame"));
        ayurvedaProducts.add(new Product("Amla Hair Pack", "Adds shine and nourishes roots.", 15, "Amla powder, Yogurt", "Dairy"));
        ayurvedaProducts.add(new Product("Neem & Tulsi Shampoo", "Purifies scalp, ideal for sensitive scalps.", 10, "Neem extract, Tulsi extract", "None"));
        ayurvedaProducts.add(new Product("Shikakai Hair Cleanser", "Strengthens hair roots and prevents breakage.", 8, "Shikakai powder, Amla extract", "None"));
        ayurvedaProducts.add(new Product("Henna Hair Mask", "Conditions hair and adds natural tint.", 7, "Henna powder, Coffee", "None"));
        ayurvedaProducts.add(new Product("Brahmi Hair Oil", "Boosts hair volume and soothes scalp.", 14, "Brahmi extract, Coconut oil", "None"));
        ayurvedaProducts.add(new Product("Fenugreek Hair Mask", "Restores hair health and adds shine.", 9, "Fenugreek seeds, Coconut milk", "None"));
        ayurvedaProducts.add(new Product("Coconut & Hibiscus Oil", "Strengthens hair and reduces frizz.", 11, "Coconut oil, Hibiscus extract", "Coconut"));
    }

    private void recommendProducts() {
        List<Product> recommendedProducts = new ArrayList<>();

        String[] allergens = allergyDetails == null || allergyDetails.isEmpty() ? new String[0] : allergyDetails.split(",\\s*");

        for (Product product : ayurvedaProducts) {
            boolean containsAllergen = false;

            for (String allergen : allergens) {
                if (product.getIngredients().toLowerCase().contains(allergen.toLowerCase())) {
                    containsAllergen = true;
                    break;
                }
            }

            if (!containsAllergen) {
                if ((doshaType.equals("Vata") && hairTexture.equals("Fine") && product.getName().equals("Brahmi Hair Oil")) ||
                        (doshaType.equals("Pitta") && hairTexture.equals("Medium") && product.getName().equals("Neem & Tulsi Shampoo")) ||
                        (doshaType.equals("Kapha") && hairTexture.equals("Coarse") && product.getName().equals("Coconut & Hibiscus Oil"))) {
                    recommendedProducts.add(product);
                }
            }
        }

        if (recommendedProducts.isEmpty()) {
            for (Product product : ayurvedaProducts) {
                boolean containsAllergen = false;

                for (String allergen : allergens) {
                    if (product.getIngredients().toLowerCase().contains(allergen.toLowerCase())) {
                        containsAllergen = true;
                        break;
                    }
                }

                if (!containsAllergen) {
                    recommendedProducts.add(product);
                }
            }
        }

        adapter = new ProductAdapter(this, recommendedProducts);
        listView.setAdapter(adapter);
        calculateTotalPrice();
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
}
