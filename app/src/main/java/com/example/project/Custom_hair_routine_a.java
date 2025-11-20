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

public class Custom_hair_routine_a extends AppCompatActivity {

    TextView tvRoutineDetails;
    String doshaType, hairTexture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_hair_routine2);
        tvRoutineDetails = findViewById(R.id.tvRoutineDetails);

        // Get data from intent
        Intent intent = getIntent();
        doshaType = intent.getStringExtra("doshaType");
        hairTexture = intent.getStringExtra("hairTexture");
        Button btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(v -> shareRoutine());
        Button btnhome = findViewById(R.id.btnhome);
        btnhome.setOnClickListener(v -> {
            Intent i= new Intent(Custom_hair_routine_a.this, HomeActivity.class);
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
        routine.append("Dosha Type: ").append(doshaType).append("\n")
                .append("Hair Texture: ").append(hairTexture).append("\n");

        // Recommended products and URLs based on dosha type and hair texture
        List<String> recommendations = new ArrayList<>();
        Map<String, String> urlMap = new HashMap<>();

        // Define recommendations for each combination of dosha type and hair texture
        if (doshaType.equals("Vata")) {
            if (hairTexture.equals("Fine")) {
                recommendations.add("- Use 'Amla & Shikakai Shampoo'.\n");
                recommendations.add("- Use 'Hibiscus Conditioner'.\n");
                recommendations.add("- Apply 'Herbal Hair Oil' (Bhringraj or Amla) for nourishment.\n");
                recommendations.add("- Finish with 'Fenugreek Hair Mask' for extra hydration.\n");
                recommendations.add("- Tip: Massage your scalp with warm oil to promote blood circulation.\n");
                urlMap.put("Vata-Fine", "https://saatwika.in/ayurvedic-hair-care-routine-for-healthy-hair/");
            } else if (hairTexture.equals("Medium")) {
                recommendations.add("- Use 'Coconut & Aloe Vera Shampoo'.\n");
                recommendations.add("- Use 'Neem Conditioner' for scalp health.\n");
                recommendations.add("- Apply 'Brahmi Hair Oil' for calming and strengthening.\n");
                recommendations.add("- Finish with 'Multani Mitti Hair Pack' for nourishment.\n");
                recommendations.add("- Tip: Incorporate yoga for overall well-being and scalp health.\n");
                urlMap.put("Vata-Medium", "https://www.healthline.com/health/beauty-skin-care/indian-home-remedies-for-hair-growth");
            } else if (hairTexture.equals("Coarse")) {
                recommendations.add("- Use 'Fenugreek & Coconut Shampoo'.\n");
                recommendations.add("- Use 'Aloe Vera Deep Conditioner'.\n");
                recommendations.add("- Apply 'Castor Oil' for deep conditioning.\n");
                recommendations.add("- Finish with 'Methi (Fenugreek) Hair Mask' for added moisture.\n");
                recommendations.add("- Tip: Drink plenty of water to keep your hair hydrated from within.\n");
                urlMap.put("Vata-Coarse", "https://www.nirvahealth.com/blog/ayurvedic-hair-care-routine-steps");
            }
        } else if (doshaType.equals("Pitta")) {
            if (hairTexture.equals("Fine")) {
                recommendations.add("- Use 'Rosemary & Aloe Shampoo'.\n");
                recommendations.add("- Use 'Mint Conditioner' for cooling effects.\n");
                recommendations.add("- Apply 'Coconut Oil' for hydration and shine.\n");
                recommendations.add("- Finish with 'Neem Hair Pack' for soothing the scalp.\n");
                recommendations.add("- Tip: Avoid excessive sun exposure to protect your scalp.\n");
                urlMap.put("Pitta-Fine", "https://theayurvedaco.com/blogs/hair-care/ayurveda-hair-care-regime-your-way-to-get-healthy-shiny-voluminous-hair?srsltid=AfmBOopOdDEitIEdkY9IjX1yIGrnhS05Q50kr_d0nCGUHCFzIWCrd-j1");
            } else if (hairTexture.equals("Medium")) {
                recommendations.add("- Use 'Brahmi Shampoo' for strength.\n");
                recommendations.add("- Use 'Amla Conditioner' for nourishment.\n");
                recommendations.add("- Apply 'Lavender Oil' for relaxation and scalp health.\n");
                recommendations.add("- Finish with 'Herbal Hair Mask' for moisture.\n");
                recommendations.add("- Tip: Regularly use cool water to rinse your hair for a soothing effect.\n");
                urlMap.put("Pitta-Medium", "https://ayutherapy.com/news/ayurvedic-hair-care-tips/");
            } else if (hairTexture.equals("Coarse")) {
                recommendations.add("- Use 'Neem & Aloe Shampoo'.\n");
                recommendations.add("- Use 'Coconut Milk Conditioner'.\n");
                recommendations.add("- Apply 'Bhringraj Oil' for revitalization.\n");
                recommendations.add("- Finish with 'Methi Hair Mask' for deep conditioning.\n");
                recommendations.add("- Tip: Keep your stress levels in check through meditation.\n");
                urlMap.put("Pitta-Coarse", "https://ayutherapy.com/news/ayurvedic-hair-care-tips/");
            }
        } else if (doshaType.equals("Kapha")) {
            if (hairTexture.equals("Fine")) {
                recommendations.add("- Use 'Lemon & Neem Shampoo'.\n");
                recommendations.add("- Use 'Brahmi Conditioner' for strength.\n");
                recommendations.add("- Apply 'Rosemary Oil' for stimulation.\n");
                recommendations.add("- Finish with 'Clay Hair Mask' to absorb excess oil.\n");
                recommendations.add("- Tip: Regularly exfoliate your scalp to keep it clean.\n");
                urlMap.put("Kapha-Fine", "https://www.anahataorganic.com/blogs/new-blog/ayurvedic-haircare-as-per-your-dosha-vata-pitta-kapha-hair-types-remedies-for-imbalance-of-doshas?srsltid=AfmBOoqpoI1tYbJKO4htI6A4w9mZZAEytRY4Bahz-KqFnmEIaXtwQUhS");
            } else if (hairTexture.equals("Medium")) {
                recommendations.add("- Use 'Hibiscus Shampoo' for volume.\n");
                recommendations.add("- Use 'Amla & Neem Conditioner' for scalp health.\n");
                recommendations.add("- Apply 'Lightweight Herbal Oil' for moisture.\n");
                recommendations.add("- Finish with 'Fenugreek Pack' for nourishment.\n");
                recommendations.add("- Tip: Avoid heavy foods for better hair health.\n");
                urlMap.put("Kapha-Medium", "https://vijayanmastersayurveda.com/blog/ayurvedic-hair-care-routine?srsltid=AfmBOoqzAzR5LXEQL9RUmLgKk7ubzijwf1490u4YxkxSz0-zCWDpdQ82");
            } else if (hairTexture.equals("Coarse")) {
                recommendations.add("- Use 'Clarifying Shampoo' with herbal extracts.\n");
                recommendations.add("- Use 'Moisturizing Conditioner' for hydration.\n");
                recommendations.add("- Apply 'Heavy Oil Blend' for nourishment.\n");
                recommendations.add("- Finish with 'Aloe Vera Hair Mask' for softness.\n");
                recommendations.add("- Tip: Incorporate warming spices in your diet for improved circulation.\n");
                urlMap.put("Kapha-Coarse", "https://saatwika.in/ayurvedic-hair-care-routine-for-healthy-hair/");
            }
        }

        SpannableStringBuilder completeRoutine = new SpannableStringBuilder();
        for (String recommendation : recommendations) {
            completeRoutine.append(recommendation);
        }

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
