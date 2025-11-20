package com.example.project;

import java.text.Normalizer;
import java.util.*;

public class NLPUtils {
    private static final SimplePorterStemmer STEM = new SimplePorterStemmer();

    private static final Map<String,String> LEMMA = new HashMap<>();
    static {
        LEMMA.put("sulphates","sulfate"); LEMMA.put("sulfates","sulfate");
        LEMMA.put("sles","sulfate"); LEMMA.put("sls","sulfate");
        LEMMA.put("fragrances","fragrance"); LEMMA.put("perfumes","perfume");
        LEMMA.put("irritation","irritate"); LEMMA.put("irritated","irritate");
        LEMMA.put("silicones","silicone"); LEMMA.put("parabens","paraben");
    }

    public static String normalize(String s){
        if (s == null) return "";
        return Normalizer.normalize(s, Normalizer.Form.NFKC)
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9%\\s-]", " ")
                .replaceAll("\\s+", " ").trim();
    }

    public static List<String> tokenize(String text){
        String n = normalize(text);
        if (n.isEmpty()) return Collections.emptyList();
        return Arrays.asList(n.split("\\s+"));
    }

    public static String lemmatizeToken(String tok){
        String t = tok.toLowerCase(Locale.ROOT);
        return LEMMA.getOrDefault(t, t);
    }

    public static String stemToken(String tok){
        return STEM.stemToken(tok);
    }

    /** Normalize -> lemma -> stem (returns stems) */
    public static List<String> normalizeLemmaStem(String text){
        List<String> out = new ArrayList<>();
        for (String t : tokenize(text)) {
            out.add(stemToken(lemmatizeToken(t)));
        }
        return out;
    }

    // ---------- ADD THESE HELPERS BELOW ----------

    /** Map common variants so "sls/sles/sulphates/perfumes" normalize nicely. */
    public static String canonicalizeAllergens(String csv){
        if (csv == null) return "";
        String n = normalize(csv);
        n = n.replace("sles", "sulfate")
                .replace("sls", "sulfate")
                .replace("sulphate", "sulfate")
                .replace("sulphates", "sulfate")
                .replace("perfume", "fragrance")
                .replace("perfumes", "fragrance")
                .replace("fragrances", "fragrance");
        return n;
    }

    /**
     * Returns true if product INCI contains any user allergen (after lemma+stem),
     * while ignoring common "-free" negations for that family (e.g., "fragrance-free").
     */
    public static boolean productContainsAllergen(String inci, String userAllergensCsv) {
        if (inci == null) return false;
        String inciNorm = normalize(inci);

        // Remove common "free of" phrases so they don't trigger a false positive
        String[] safePhrases = {
                "fragrance-free","fragrance free",
                "silicone-free","silicone free",
                "sulfate-free","sulfate free",
                "no fragrance","no silicones","no silicone","no sulfates","no sulfate"
        };
        for (String sp : safePhrases) {
            if (inciNorm.contains(sp)) {
                inciNorm = inciNorm.replace(sp, " ");
            }
        }

        // Build a set of stems for the product's INCI
        HashSet<String> stemInci = new HashSet<>(normalizeLemmaStem(inciNorm));

        if (userAllergensCsv == null || userAllergensCsv.trim().isEmpty()) return false;
        String[] userAll = userAllergensCsv.split(",\\s*");

        for (String a : userAll) {
            for (String stem : normalizeLemmaStem(a)) {
                if (stemInci.contains(stem)) return true;
            }
        }
        return false;
    }
}
