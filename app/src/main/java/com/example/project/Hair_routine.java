package com.example.project;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hair_routine extends AppCompatActivity {

    RadioGroup rgHairType, rgHairCondition;
    CheckBox cbAllergy;
    EditText etAllergyDetails;
    Button btnNext;
    ImageButton btnMic;

    private SpeechRecognizer recognizer;
    private Intent recognizerIntent;
    private TextToSpeech tts;

    private static final int REQ_AUDIO = 101;
    private static final int REQ_DIALOG_SR = 102; // fallback dialog
    private static final String UTTER_PROMPT = "tts_prompt";
    private final Handler ui = new Handler(Looper.getMainLooper());
    private boolean usingDialog = false; // are we in fallback dialog mode?

    // broader vocab
    private static final String[] ALLERGEN_VOCAB = {
            "sls","sles","sulfate","sulphate","sodium lauryl sulfate","sodium laureth sulfate",
            "ammonium lauryl sulfate","ammonium laureth sulfate",
            "silicone","silicones","dimethicone","amodimethicone","cyclopentasiloxane","cyclohexasiloxane",
            "trimethicone","phenyl trimethicone","siloxane","polysiloxane",
            "fragrance","parfum","perfume","linalool","limonene","citronellol","geraniol","coumarin","eugenol",
            "paraben","parabens","methylparaben","propylparaben","ethylparaben","butylparaben",
            "methylisothiazolinone","methylchloroisothiazolinone","phenoxyethanol","formaldehyde","dmdm hydantoin",
            "imidazolidinyl urea","diazolidinyl urea",
            "cocamidopropyl betaine","cocamide dea","cocamide mea",
            "shea","shea butter","butyrospermum parkii","coconut","coconut oil",
            "alcohol","denat","benzyl alcohol"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_routine);

        rgHairType = findViewById(R.id.rgHairType);
        rgHairCondition = findViewById(R.id.rgHairCondition);
        cbAllergy = findViewById(R.id.cbAllergy);
        etAllergyDetails = findViewById(R.id.etAllergyDetails);
        btnNext = findViewById(R.id.btnNext);
        btnMic = findViewById(R.id.btnMic);

        cbAllergy.setOnCheckedChangeListener((b, isChecked) ->
                etAllergyDetails.setVisibility(isChecked ? View.VISIBLE : View.GONE));

        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            // Many emulators will hit this — use dialog fallback directly
            Toast.makeText(this, "Inline speech not available; using fallback.", Toast.LENGTH_LONG).show();
        } else {
            recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(new SimpleListener());
        }

        // TTS
        tts = new TextToSpeech(this, s -> {
            if (s == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.getDefault());
                tts.setSpeechRate(1.0f);
                tts.setPitch(1.0f);
            }
        });
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override public void onStart(String id) {}
            @Override public void onDone(String id) {
                if (UTTER_PROMPT.equals(id) && !usingDialog) {
                    ui.post(Hair_routine.this::actuallyStartListening);
                }
            }
            @Override public void onError(String id) {
                if (UTTER_PROMPT.equals(id) && !usingDialog) {
                    ui.post(Hair_routine.this::actuallyStartListening);
                }
            }
        });

        // SR intent (inline)
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().toString());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 1000);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 800);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 800);

        btnMic.setOnClickListener(v -> {
            if (!ensureAudioPermission()) return;
            if (tts != null) tts.stop();
            // Prefer inline SR if available, otherwise go straight to dialog
            if (SpeechRecognizer.isRecognitionAvailable(this) && recognizer != null) {
                usingDialog = false;
                speakPromptThenListen(); // inline path
            } else {
                usingDialog = true;
                launchRecognizerDialog(); // fallback
            }
        });

        btnNext.setOnClickListener(view -> {
            String hairType = getSelectedRadioText(rgHairType);
            String hairCondition = getSelectedRadioText(rgHairCondition);
            String allergyDetails = cbAllergy.isChecked() ? etAllergyDetails.getText().toString() : "None";

            if (hairType == null || hairCondition == null || (cbAllergy.isChecked() && allergyDetails.isEmpty())) {
                Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, cosmetic_recommendation_h.class);
            intent.putExtra("hairType", hairType);
            intent.putExtra("hairCondition", hairCondition);
            intent.putExtra("hasAllergy", cbAllergy.isChecked());
            intent.putExtra("allergyDetails", allergyDetails);
            startActivity(intent);
        });
    }

    // ---------- permission ----------
    private boolean ensureAudioPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) return true;
        requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQ_AUDIO);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] perms, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, perms, grantResults);
        if (requestCode == REQ_AUDIO && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            btnMic.performClick();
        } else {
            Toast.makeText(this, "Microphone permission is required.", Toast.LENGTH_SHORT).show();
        }
    }

    // ---------- speaking + start listening ----------
    private void speakPromptThenListen() {
        if (tts == null) { actuallyStartListening(); return; }
        tts.speak("Tell me your hair type, condition, and allergens. For example: curly dry hair, avoid silicones and S L S.",
                TextToSpeech.QUEUE_FLUSH, null, UTTER_PROMPT);
    }

    private void actuallyStartListening() {
        if (recognizer == null) { launchRecognizerDialog(); return; }
        try {
            recognizer.cancel();
            recognizer.startListening(recognizerIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Inline SR failed, using fallback.", Toast.LENGTH_SHORT).show();
            launchRecognizerDialog();
        }
    }

    // ---------- fallback dialog ----------
    private void launchRecognizerDialog() {
        usingDialog = true;
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().toString());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say: curly dry hair, allergic to fragrance and S L S");
        try {
            startActivityForResult(i, REQ_DIALOG_SR);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No speech app found. Install Google app / Speech Services.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == REQ_DIALOG_SR && resultCode == RESULT_OK && data != null) {
            ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (list != null && !list.isEmpty()) {
                StringBuilder merged = new StringBuilder();
                int take = Math.min(3, list.size());
                for (int i = 0; i < take; i++) merged.append(list.get(i)).append(" ");
                handleSpeech(merged.toString().trim());
            } else {
                Toast.makeText(this, "No speech captured.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // ---------- SR listener ----------
    private class SimpleListener implements RecognitionListener {
        private final StringBuilder partialBuf = new StringBuilder();

        @Override public void onReadyForSpeech(Bundle params) {
            Toast.makeText(Hair_routine.this, "Listening…", Toast.LENGTH_SHORT).show();
        }

        @Override public void onBeginningOfSpeech() {}

        @Override public void onRmsChanged(float rmsdB) {
            // Quick visual: show mic level in the allergy field hint
            etAllergyDetails.setHint(String.format(Locale.getDefault(), "Listening… mic %.1f dB", rmsdB));
        }

        @Override public void onBufferReceived(byte[] buffer) {}
        @Override public void onEndOfSpeech() {}

        @Override public void onPartialResults(Bundle partialResults) {
            ArrayList<String> list = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (list != null && !list.isEmpty()) {
                String p = list.get(0);
                if (p != null && p.length() > partialBuf.length()) {
                    partialBuf.setLength(0);
                    partialBuf.append(p);
                }
            }
        }

        @Override public void onResults(Bundle results) {
            ArrayList<String> list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (list == null || list.isEmpty()) {
                Toast.makeText(Hair_routine.this, "No match. Switching to dialog…", Toast.LENGTH_SHORT).show();
                launchRecognizerDialog();
                return;
            }
            StringBuilder merged = new StringBuilder();
            int take = Math.min(3, list.size());
            for (int i = 0; i < take; i++) merged.append(list.get(i)).append(" ");
            String finalText = merged.toString().trim();
            if (finalText.isEmpty() && partialBuf.length() > 0) finalText = partialBuf.toString();

            Toast.makeText(Hair_routine.this, "Heard: " + finalText, Toast.LENGTH_SHORT).show();
            handleSpeech(finalText);
            partialBuf.setLength(0);
        }

        @Override public void onError(int error) {
            String msg;
            switch (error) {
                case SpeechRecognizer.ERROR_NO_MATCH:
                    msg = "No match. Switching to dialog…";
                    Toast.makeText(Hair_routine.this, msg, Toast.LENGTH_SHORT).show();
                    launchRecognizerDialog();
                    return;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    msg = "Recognizer busy, retrying…";
                    ui.postDelayed(() -> { try { recognizer.cancel(); } catch (Exception ignored) {} actuallyStartListening(); }, 250);
                    break;
                case SpeechRecognizer.ERROR_AUDIO: msg = "Audio recording error"; break;
                case SpeechRecognizer.ERROR_CLIENT: msg = "Client error (using dialog)"; launchRecognizerDialog(); return;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS: msg = "Mic permission missing"; break;
                case SpeechRecognizer.ERROR_NETWORK: msg = "Network error"; break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT: msg = "Network timeout"; break;
                case SpeechRecognizer.ERROR_SERVER: msg = "Server error"; break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT: msg = "Speech timeout"; break;
                default: msg = "Unknown error: " + error;
            }
            Toast.makeText(Hair_routine.this, msg, Toast.LENGTH_SHORT).show();
        }

        @Override public void onEvent(int eventType, Bundle params) {}
    }

    // ---------- NLP pipeline as before ----------
    private void handleSpeech(String speechRaw) {
        String speech = NLPUtils.normalize(speechRaw);

        String hairType = pickOne(speech, new String[][]{
                {"Curly","curly","curl"},
                {"Straight","straight"},
                {"Wavy","wavy","wave"},
                {"Coily","coily","kinky","coil"}
        });
        String hairCond = pickOne(speech, new String[][]{
                {"Dry","dry","dehydrated"},
                {"Oily","oily","greasy"},
                {"Normal","normal","balanced"},
                {"Sensitive","sensitive","itchy","irritated"}
        });

        if (hairType != null) selectRadio(rgHairType, hairType);
        if (hairCond != null) selectRadio(rgHairCondition, hairCond);

        ArrayList<String> picks = extractAllergensFromSpeech(speechRaw);
        if (!picks.isEmpty()) {
            cbAllergy.setChecked(true);
            etAllergyDetails.setVisibility(View.VISIBLE);
            String canonical = NLPUtils.canonicalizeAllergens(String.join(", ", picks));
            etAllergyDetails.setText(canonical);
            Toast.makeText(this, "Allergens: " + canonical, Toast.LENGTH_SHORT).show();
        }

        int budget = parseBudget(speech);

        String say = "I heard "
                + (hairType != null ? hairType + " " : "")
                + (hairCond != null ? hairCond + " " : "")
                + (picks.isEmpty() ? "with no allergens. " : "allergic to " + String.join(", ", picks) + ". ");
        if (budget > 0) say += "Budget under " + budget + ". ";
        say += "Tap next to see recommendations.";
        if (tts != null) tts.speak(say, TextToSpeech.QUEUE_FLUSH, null, "tts_info");
    }

    private int parseBudget(String text) {
        Matcher m = Pattern.compile("(under|below|budget|upto|up to)\\s*(\\d+)").matcher(text);
        if (m.find()) return Integer.parseInt(m.group(2));
        return -1;
    }

    private String pickOne(String text, String[][] options) {
        for (String[] group : options) {
            String label = group[0];
            for (int i = 1; i < group.length; i++) {
                if (text.contains(NLPUtils.normalize(group[i]))) return label;
            }
        }
        return null;
    }

    private void selectRadio(RadioGroup group, String labelText) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View v = group.getChildAt(i);
            if (v instanceof RadioButton) {
                RadioButton rb = (RadioButton) v;
                if (rb.getText().toString().equalsIgnoreCase(labelText)) {
                    rb.setChecked(true);
                    return;
                }
            }
        }
    }

    private String getSelectedRadioText(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            return radioButton.getText().toString();
        }
        return null;
    }

    // acronym collapse
    private String collapseAcronyms(String txt) {
        String t = txt.toLowerCase(Locale.ROOT);
        t = t.replaceAll("\\.", " ");
        t = t.replaceAll("\\bs\\s*l\\s*s\\b", "sls");
        t = t.replaceAll("\\bs\\s*l\\s*e\\s*s\\b", "sles");
        return t;
    }

    private ArrayList<String> extractAllergensFromSpeech(String speechRaw) {
        ArrayList<String> out = new ArrayList<>();
        if (speechRaw == null || speechRaw.trim().isEmpty()) return out;

        String collapsed = collapseAcronyms(speechRaw);
        String clean = collapsed.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9\\s,&-]", " ")
                .replaceAll("\\s+", " ").trim();

        String[] cuePatterns = new String[] {
                "(allergic to|allergy to|avoid|no|don'?t use|do not use|free of|without)\\s+([a-z0-9\\s,&-]+)"
        };
        ArrayList<String> phraseHits = new ArrayList<>();
        for (String pat : cuePatterns) {
            Matcher m = Pattern.compile(pat).matcher(clean);
            while (m.find()) phraseHits.add(m.group(2));
        }

        String scanText = phraseHits.isEmpty() ? clean : String.join(" ", phraseHits);
        String[] chunks = scanText.split("\\s*(?:,|\\band\\b|&)\\s*");

        List<String> vocab = Arrays.asList(ALLERGEN_VOCAB);
        List<String> scanStems = NLPUtils.normalizeLemmaStem(scanText);

        LinkedHashSet<String> found = new LinkedHashSet<>();
        for (String c : chunks) {
            String cc = c.trim();
            if (cc.length() < 2) continue;
            for (String v : vocab) {
                if (cc.contains(v)) { found.add(v); }
            }
        }
        for (String v : vocab) {
            List<String> vStems = NLPUtils.normalizeLemmaStem(v);
            for (String vs : vStems) {
                if (scanStems.contains(vs)) { found.add(v); break; }
            }
        }

        out.addAll(found);
        return out;
    }

    @Override protected void onPause() {
        super.onPause();
        if (recognizer != null) recognizer.cancel();
        if (tts != null) tts.stop();
    }

    @Override protected void onDestroy() {
        if (recognizer != null) recognizer.destroy();
        if (tts != null) { tts.stop(); tts.shutdown(); }
        super.onDestroy();
    }
}
