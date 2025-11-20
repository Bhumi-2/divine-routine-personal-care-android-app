package com.example.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Custom_hair_routine extends AppCompatActivity {

    TextView tvRoutineDetails;
    String hairType, hairCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_hair_routine);

        tvRoutineDetails = findViewById(R.id.tvRoutineDetails);

        // Get data from intent
        Intent intent = getIntent();
        hairType = intent.getStringExtra("hairType");
        hairCondition = intent.getStringExtra("hairCondition");
        Button btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(v -> shareRoutine());
        Button btnhome = findViewById(R.id.btnhome);
        btnhome.setOnClickListener(v -> {
            Intent i= new Intent(Custom_hair_routine.this, HomeActivity.class);
            startActivity(i);
        });
        // Display a customized routine based on the data
        displayRoutine();
    }
    private void shareRoutine() {
        String routineText = tvRoutineDetails.getText().toString();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, routineText);

        startActivity(Intent.createChooser(shareIntent, "Share routine via"));
    }
    private void displayRoutine() {
        StringBuilder routine = new StringBuilder();
        routine.append("Hair Type: ").append(hairType).append("\n")
                .append("Hair Condition: ").append(hairCondition).append("\n");

        // Recommended products and URLs based on hair type and condition
        List<String> recommendations = new ArrayList<>();
        Map<String, String> urlMap = new HashMap<>();

        // Define chemical-based recommendations for each combination of hair type and condition
        if (hairType.equals("Curly")) {
            if (hairCondition.equals("Dry")) {
                recommendations.add("- Use a 'Moisturizing Shampoo' to cleanse and hydrate your curls.\n");
                recommendations.add("- Follow with a 'Moisturizing Conditioner' to nourish and soften your hair.\n");
                recommendations.add("- Apply a 'Deep Conditioning Mask' once a week for extra hydration.\n");
                recommendations.add("- Use a 'Leave-In Conditioner' to keep your curls defined and moisturized.\n");
                recommendations.add("- Finish with a 'Curl Defining Cream' for hold and definition.\n");
                recommendations.add("- Tip: Limit heat styling to prevent moisture loss and maintain curl integrity.\n");
                urlMap.put("Curly-Dry", "https://www.aad.org/public/everyday-care/hair-scalp-care/hair/healthy-hair-tips");
            } else if (hairCondition.equals("Normal")) {
                recommendations.add("- Use a 'Volume Shampoo' to add lift and bounce to your curls.\n");
                recommendations.add("- Follow with a 'Leave-In Conditioner' for hydration and detangling.\n");
                recommendations.add("- Apply a 'Curl Enhancing Gel' to define and hold your curl pattern.\n");
                recommendations.add("- Use a 'Nourishing Hair Cream' for additional moisture.\n");
                recommendations.add("- Finish with a 'Smoothing Serum' to add shine.\n");
                recommendations.add("- Tip: Regular trims help prevent split ends and keep curls healthy.\n");
                urlMap.put("Curly-Normal", "https://lovebeautyandplanet.in/blogs/hair/hair-care-tips-for-women-with-scanty-hair?srsltid=AfmBOorhdvGDwVA7qK8ncXFJqnLkXUhkYslhDcqeprpq-vjsOowpIyC_");
            } else if (hairCondition.equals("Oily")) {
                recommendations.add("- Use a 'Clarifying Shampoo' to remove excess oil and buildup.\n");
                recommendations.add("- Follow with an 'Oil Control Conditioner' that won’t weigh down your hair.\n");
                recommendations.add("- Apply a 'Lightweight Styling Gel' to keep curls defined.\n");
                recommendations.add("- Use 'Dry Shampoo' in between washes to absorb excess oil.\n");
                recommendations.add("- Finish with a 'Purifying Hair Mask' once a month for a deep cleanse.\n");
                recommendations.add("- Tip: Avoid heavy products to prevent oiliness.\n");
                urlMap.put("Curly-Oily", "https://lovebeautyandplanet.in/blogs/hair/healthy-hair-habits-to-follow?srsltid=AfmBOopJVB-hnz9VbvxrwqzX-cDv49NCRpub8u7M3zw064johV4R6X9L");
            } else if (hairCondition.equals("Sensitive")) {
                recommendations.add("- Use a 'Sensitive Scalp Shampoo' to gently cleanse.\n");
                recommendations.add("- Follow with a 'Gentle Conditioner' to hydrate without irritation.\n");
                recommendations.add("- Apply a 'Calming Hair Treatment' for added moisture.\n");
                recommendations.add("- Use a 'Fragrance-Free Styling Cream' to define curls without allergens.\n");
                recommendations.add("- Finish with a 'Moisture-Rich Leave-In Conditioner'.\n");
                recommendations.add("- Tip: Patch-test new products to ensure they don’t irritate your scalp.\n");
                urlMap.put("Curly-Sensitive", "https://myhautelife.com/beauty/the-ultimate-hair-care-guide/");
            }
        } else if (hairType.equals("Straight")) {
            if (hairCondition.equals("Dry")) {
                recommendations.add("- Use a 'Hydrating Shampoo' to restore moisture.\n");
                recommendations.add("- Follow with a 'Moisturizing Conditioner' for softness.\n");
                recommendations.add("- Apply a 'Deep Conditioning Mask' weekly for intensive care.\n");
                recommendations.add("- Use a 'Leave-In Treatment' for extra hydration.\n");
                recommendations.add("- Finish with a 'Smoothing Serum' for shine and frizz control.\n");
                recommendations.add("- Tip: Reduce heat styling to maintain moisture balance.\n");
                urlMap.put("Straight-Dry", "https://www.healthline.com/health/beauty-skin-care/hair-care-routine");
            } else if (hairCondition.equals("Normal")) {
                recommendations.add("- Use a 'Smoothing Shampoo' for sleekness.\n");
                recommendations.add("- Follow with a 'Daily Conditioner' to keep hair manageable.\n");
                recommendations.add("- Apply a 'Lightweight Styling Cream' for smoothness.\n");
                recommendations.add("- Use a 'Hair Serum' for added shine.\n");
                recommendations.add("- Finish with a 'Heat Protectant' if styling with heat.\n");
                recommendations.add("- Tip: Schedule regular trims to keep hair healthy.\n");
                urlMap.put("Straight-Normal", "https://www.naturabrasil.fr/en-us/hair-beauty/straight-straightened-hair/comprehensive-care-guide?srsltid=AfmBOorJ1InYyqL8Pko7ADEVHdlwk2uHceE6-zHhYEanAD6nxlQlSFU4");
            } else if (hairCondition.equals("Oily")) {
                recommendations.add("- Use a 'Purifying Shampoo' to remove excess oil.\n");
                recommendations.add("- Follow with a 'Lightweight Conditioner' to prevent buildup.\n");
                recommendations.add("- Apply a 'Dry Shampoo' as needed between washes.\n");
                recommendations.add("- Use a 'Volumizing Mousse' for lift.\n");
                recommendations.add("- Finish with a 'Texturizing Spray' for added dimension.\n");
                recommendations.add("- Tip: Avoid heavy creams that can weigh down your hair.\n");
                urlMap.put("Straight-Oily", "https://ibacosmetics.com/blogs/iba-blogs/6-tips-to-get-straight-and-silky-hair?srsltid=AfmBOoo7dT02w-Q0ZqpaG2WlWInRy16mBGn-iq0z4KEe9LgrzuZfqRQH");
            } else if (hairCondition.equals("Sensitive")) {
                recommendations.add("- Use a 'Gentle Shampoo' to avoid irritation.\n");
                recommendations.add("- Follow with a 'Calming Conditioner' for sensitive scalps.\n");
                recommendations.add("- Apply a 'Soothing Hair Treatment' for hydration.\n");
                recommendations.add("- Use a 'Fragrance-Free Styling Gel' for definition.\n");
                recommendations.add("- Finish with a 'Leave-In Conditioner' for moisture.\n");
                recommendations.add("- Tip: Opt for products free from harsh chemicals.\n");
                urlMap.put("Straight-Sensitive", "https://www.hairfinity.com/blogs/news/8-care-tips-for-naturally-straight-hair?srsltid=AfmBOopD1L6Mkj0JuYYjqwlaAxRFMT0Gx2l3_gOYzME8iadRI6s4C14F");
            }
        } else if (hairType.equals("Wavy")) {
            if (hairCondition.equals("Dry")) {
                recommendations.add("- Use a 'Nourishing Shampoo' to hydrate your waves.\n");
                recommendations.add("- Follow with a 'Hydrating Conditioner' to soften and define.\n");
                recommendations.add("- Apply a 'Deep Conditioning Treatment' weekly.\n");
                recommendations.add("- Use a 'Moisturizing Styling Cream' for definition.\n");
                recommendations.add("- Finish with a 'Hair Oil' for shine.\n");
                recommendations.add("- Tip: Protect your waves from heat styling to maintain moisture.\n");
                urlMap.put("Wavy-Dry", "https://www.lorealparis.co.in/beauty-magazine/hair-care-blogs/dry-hair-care-routine");
            } else if (hairCondition.equals("Normal")) {
                recommendations.add("- Use a 'Wave Defining Shampoo' to enhance your natural waves.\n");
                recommendations.add("- Follow with a 'Wave Control Conditioner' for added definition.\n");
                recommendations.add("- Apply a 'Curl Cream' for light hold and bounce.\n");
                recommendations.add("- Use a 'Lightweight Styling Gel' for extra definition.\n");
                recommendations.add("- Finish with a 'Finishing Spray' for hold.\n");
                recommendations.add("- Tip: Avoid over-washing to maintain natural moisture balance.\n");
                urlMap.put("Wavy-Normal", "https://lovebeautyandplanet.in/blogs/hair/wavy-hair-routine-for-beginners");
            } else if (hairCondition.equals("Oily")) {
                recommendations.add("- Use a 'Deep Cleansing Shampoo' to remove excess oil.\n");
                recommendations.add("- Follow with a 'Balance Conditioner' to maintain moisture.\n");
                recommendations.add("- Apply a 'Volumizing Mousse' for lift without weight.\n");
                recommendations.add("- Use a 'Texturizing Spray' for added body.\n");
                recommendations.add("- Finish with a 'Dry Shampoo' for in-between washes.\n");
                recommendations.add("- Tip: Use products labeled 'oil-free' to avoid buildup.\n");
                urlMap.put("Wavy-Oily", "https://in.sugarcosmetics.com/blog/how-to-take-care-of-wavy-hair?srsltid=AfmBOophSkoOXiCKr0hr1rKFL-lTPfCG9q1sXRr6ObH0iE8y2HRYNmRF");
            } else if (hairCondition.equals("Sensitive")) {
                recommendations.add("- Use a 'Gentle Shampoo' for mild cleansing.\n");
                recommendations.add("- Follow with a 'Hydrating Conditioner' for moisture.\n");
                recommendations.add("- Apply a 'Soothing Hair Treatment' for sensitive scalps.\n");
                recommendations.add("- Use a 'Fragrance-Free Styling Cream' to avoid irritation.\n");
                recommendations.add("- Finish with a 'Moisturizing Leave-In Conditioner'.\n");
                recommendations.add("- Tip: Look for hypoallergenic products to minimize sensitivity.\n");
                urlMap.put("Wavy-Sensitive", "https://www.raisingmemories.com/2021/04/how-to-take-care-of-wavy-hair.html");
            }
        } else if (hairType.equals("Coily")) {
            if (hairCondition.equals("Dry")) {
                recommendations.add("- Use a 'Moisturizing Shampoo' to hydrate your coils.\n");
                recommendations.add("- Follow with a 'Deep Conditioning Treatment' for extra moisture.\n");
                recommendations.add("- Apply a 'Leave-In Conditioner' to keep coils soft.\n");
                recommendations.add("- Use a 'Hair Butter' to seal in moisture.\n");
                recommendations.add("- Finish with a 'Curl Defining Cream' for definition.\n");
                recommendations.add("- Tip: Protective styles can help retain moisture and minimize breakage.\n");
                urlMap.put("Coily-Dry", "https://lovebeautyandplanet.in/blogs/hair/how-to-take-care-of-coily-hair?srsltid=AfmBOor2GuOsCCy5exUf2O95i-g0zYI1clbiJjd5A7ORTi_h3RcCwdm-");
            } else if (hairCondition.equals("Normal")) {
                recommendations.add("- Use a 'Hydrating Shampoo' to maintain moisture.\n");
                recommendations.add("- Follow with a 'Moisturizing Conditioner' for softness.\n");
                recommendations.add("- Apply a 'Curl Cream' for definition and hold.\n");
                recommendations.add("- Use a 'Lightweight Oil' for shine and moisture.\n");
                recommendations.add("- Finish with a 'Styling Gel' for hold.\n");
                recommendations.add("- Tip: Regularly trim ends to maintain healthy coils.\n");
                urlMap.put("Coily-Normal", "https://gisou.com/blogs/blog/routine-for-coily-hair");
            } else if (hairCondition.equals("Oily")) {
                recommendations.add("- Use a 'Clarifying Shampoo' to remove buildup.\n");
                recommendations.add("- Follow with a 'Lightweight Conditioner' to avoid weighing down coils.\n");
                recommendations.add("- Apply a 'Dry Shampoo' for oil absorption.\n");
                recommendations.add("- Use a 'Volumizing Mousse' for lift.\n");
                recommendations.add("- Finish with a 'Texturizing Spray' for body.\n");
                recommendations.add("- Tip: Avoid heavy creams to prevent greasiness.\n");
                urlMap.put("Coily-Oily", "https://www.lorealparisusa.com/beauty-magazine/hair-care/dry-hair/what-is-coily-hair");
            } else if (hairCondition.equals("Sensitive")) {
                recommendations.add("- Use a 'Gentle Shampoo' for sensitive scalps.\n");
                recommendations.add("- Follow with a 'Moisturizing Conditioner' for hydration.\n");
                recommendations.add("- Apply a 'Soothing Leave-In Treatment' for moisture.\n");
                recommendations.add("- Use a 'Fragrance-Free Styling Cream' to avoid irritation.\n");
                recommendations.add("- Finish with a 'Hydrating Hair Oil'.\n");
                recommendations.add("- Tip: Always patch-test products before full use to avoid reactions.\n");
                urlMap.put("Coily-Sensitive", "https://mamaearth.in/blog/coily-hair/?srsltid=AfmBOoqYXOnL9V5uBPSaT9DmHrcIHhEjbDyCskmo5umLwoYx-m4MVKep");
            }
        }


        // Display the recommendations
        SpannableStringBuilder completeRoutine = new SpannableStringBuilder();
        for (String recommendation : recommendations) {
            completeRoutine.append(recommendation);
        }

        // Add a link below the recommendations for the last recommendation URL
        if (!recommendations.isEmpty()) {
            String lastUrl = urlMap.values().iterator().next(); // Get the first URL from the map
            completeRoutine.append("\nFor more tips, click on the link here: ").append(lastUrl).append("\n");

            makeLinkClickable(completeRoutine, completeRoutine.length() - lastUrl.length(), completeRoutine.length(), lastUrl);
        }

        tvRoutineDetails.setText(completeRoutine);
        tvRoutineDetails.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void makeLinkClickable(SpannableStringBuilder spannableStringBuilder, int start, int end, String url) {
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        };
        spannableStringBuilder.setSpan(clickableSpan, start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
