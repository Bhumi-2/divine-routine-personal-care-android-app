package com.example.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AyurvedaSkincareRoutine extends AppCompatActivity {
    private TextView textViewDosha, textViewRoutine;
    private TextView articleLink1, articleLink2, articleLink3, articleLink4;
    private Button buttonNextDosha;
    private int vataCount, pittaCount, kaphaCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayurveda_skincare_routine);

        vataCount = getIntent().getIntExtra("VataCount", 0);
        pittaCount = getIntent().getIntExtra("PittaCount", 0);
        kaphaCount = getIntent().getIntExtra("KaphaCount", 0);

        textViewDosha = findViewById(R.id.textViewDosha);
        textViewRoutine = findViewById(R.id.textViewRoutine);
        buttonNextDosha = findViewById(R.id.buttonNextDosha);

        articleLink1 = findViewById(R.id.articleLink1);
        articleLink2 = findViewById(R.id.articleLink2);
        articleLink3 = findViewById(R.id.articleLink3);
        articleLink4 = findViewById(R.id.articleLink4);

        String dosha = determineDosha();
        textViewDosha.setText("Your Dosha is: " + dosha);
        textViewRoutine.setText(getPersonalizedRoutine(dosha));

        setArticleLinks(dosha);

        buttonNextDosha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AyurvedaSkincareRoutine.this, AyurvedaRecommedation.class);
                intent.putExtra("skinType", dosha);
                startActivity(intent);
            }
        });
    }

    private String determineDosha() {
        if (vataCount > pittaCount && vataCount > kaphaCount) {
            return "Vata";
        } else if (pittaCount > vataCount && pittaCount > kaphaCount) {
            return "Pitta";
        } else {
            return "Kapha";
        }
    }

    private String getPersonalizedRoutine(String dosha) {
        switch (dosha) {
            case "Vata":
                return "Routine: Cleanse with a gentle, creamy cleanser infused with lavender.\n" +
                        "Tone with a rosewater mist.\n" +
                        "Moisturize with sesame oil.\n" +
                        "Apply a hydrating face mask with aloe vera.";
            case "Pitta":
                return "Routine: Cleanse with a gentle, foaming cleanser infused with rose.\n" +
                        "Tone with a rosewater mist.\n" +
                        "Moisturize with coconut oil.\n" +
                        "Apply a cooling face mask with sandalwood.";
            case "Kapha":
                return "Routine: Cleanse with a gentle, foaming cleanser infused with neem.\n" +
                        "Tone with a rosewater mist.\n" +
                        "Moisturize with almond oil.\n" +
                        "Apply a purifying face mask with clay.";
            default:
                return "Routine: Balance your skincare based on your Dosha.";
        }
    }

    private void setArticleLinks(String dosha) {
        // Set URLs based on dosha type
        String[] articles;
        switch (dosha) {
            case "Vata":
                articles = new String[]{
                        "https://www.medicalnewstoday.com/articles/vata-dosha",
                        "https://rasa-ayurveda.com/blogs/lifestyle-support-blog/vata-skin-care",
                        "https://rasasara.com/skin-type/",
                        "https://urbanveda.com/blogs/blog/vata-dosha"
                };
                break;
            case "Pitta":
                articles = new String[]{
                        "https://rasa-ayurveda.com/blogs/lifestyle-support-blog/pitta-skin-care",
                        "https://www.refreshbotanicals.com/blogs/skin-care/your-guide-to-pitta-skin-type-combination-of-fire-water",
                        "https://www.keralaayurveda.us/courses/blog/ayurvedic-skin-care-routine-for-the-summer/",
                        "https://www.jiva.com/blog/a-beginners-skin-care-guide-for-pitta-skin-type"
                };
                break;
            case "Kapha":
                articles = new String[]{
                        "https://rasa-ayurveda.com/blogs/lifestyle-support-blog/kapha-skin-care",
                        "https://urbanveda.com/blogs/blog/kapha-dosha",
                        "https://www.refreshbotanicals.com/blogs/skin-care/kapha-skin-type-nature-problems-and-the-3-remedies",
                        "https://mapi.com/blogs/articles/know-the-skin-you-re-in-soaptherapy?srsltid=AfmBOordYbwcuUNeO4b0tjDg-cbLW-vvL6cq7D95TX3-sabFl4h7mtwx"
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
    }}