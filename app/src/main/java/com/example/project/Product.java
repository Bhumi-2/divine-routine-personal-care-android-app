package com.example.project;

public class Product {
    private String name;
    private String description;
    private double price;
    private String ingredients;

    // Optional fields
    private String allergens;   // e.g., "SLS", "Fragrance", "None"
    private int imageResId;     // if you later show images (0 = none)
    private String reason;      // why recommended / included (nullable)

    // ---- Constructors ----
    public Product(String name, String description, double price, String ingredients) {
        this(name, description, price, ingredients, null, 0, null);
    }

    public Product(String name, String description, double price, String ingredients, String allergens) {
        this(name, description, price, ingredients, allergens, 0, null);
    }

    public Product(String name, String description, double price, String ingredients, String allergens, int imageResId) {
        this(name, description, price, ingredients, allergens, imageResId, null);
    }

    public Product(String name, String description, double price, String ingredients,
                   String allergens, int imageResId, String reason) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ingredients = ingredients;
        this.allergens = allergens;
        this.imageResId = imageResId;
        this.reason = reason;
    }

    // ---- Getters ----
    public String getName()        { return name; }
    public String getDescription() { return description; }
    public double getPrice()       { return price; }
    public String getIngredients() { return ingredients; }
    public String getAllergens()   { return allergens; }
    public int getImageResId()     { return imageResId; }
    public String getReason()      { return reason; }

    // ---- Setters ----
    public void setName(String name)                 { this.name = name; }
    public void setDescription(String description)   { this.description = description; }
    public void setPrice(double price)               { this.price = price; }
    public void setIngredients(String ingredients)   { this.ingredients = ingredients; }
    public void setAllergens(String allergens)       { this.allergens = allergens; }
    public void setImageResId(int imageResId)        { this.imageResId = imageResId; }
    public void setReason(String reason)             { this.reason = reason; }

    @Override
    public String toString() {
        return "Product{name='" + name + '\'' +
                ", price=" + price +
                ", allergens='" + allergens + '\'' +
                '}';
    }
}
