package model;

import exception.InvalidInputException;

/**
 * Week 6 refactor:
 * - Parent class becomes abstract
 * - Validation uses exceptions (no System.out.println in model)
 */
public abstract class ClothingItem {
    protected int itemId;
    protected String name;
    protected String size;
    protected double price;
    protected String brand;
    protected int stockQuantity;

    public ClothingItem(int itemId, String name, String size, double price, String brand, int stockQuantity) {
        setItemId(itemId);
        setName(name);
        setSize(size);
        setPrice(price);
        setBrand(brand);
        setStockQuantity(stockQuantity);
    }

    public ClothingItem() {
        // Default constructor is still allowed for convenience.
        // Use safe defaults.
        this.itemId = 1;
        this.name = "Unknown Item";
        this.size = "N/A";
        this.price = 0.0;
        this.brand = "No Brand";
        this.stockQuantity = 0;
    }

    // Getters
    public int getItemId() { return itemId; }
    public String getName() { return name; }
    public String getSize() { return size; }
    public double getPrice() { return price; }
    public String getBrand() { return brand; }
    public int getStockQuantity() { return stockQuantity; }

    // Setters (validation)
    public void setItemId(int itemId) {
        if (itemId <= 0) {
            throw new InvalidInputException("Item ID must be positive");
        }
        this.itemId = itemId;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public void setSize(String size) {
        if (size == null || size.trim().isEmpty()) {
            throw new InvalidInputException("Size cannot be null or empty");
        }
        this.size = size.trim();
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new InvalidInputException("Price cannot be negative");
        }
        this.price = price;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new InvalidInputException("Brand cannot be null or empty");
        }
        this.brand = brand.trim();
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new InvalidInputException("Stock quantity cannot be negative");
        }
        this.stockQuantity = stockQuantity;
    }

    // Week 3 store logic
    public boolean isPremium() {
        return price > 35000.0;
    }

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public boolean reduceStock(int amount) {
        if (amount <= 0) {
            throw new InvalidInputException("Amount must be positive");
        }
        if (amount > stockQuantity) {
            return false;
        }
        stockQuantity -= amount;
        return true;
    }

    public void increaseStock(int amount) {
        if (amount <= 0) {
            throw new InvalidInputException("Amount must be positive");
        }
        stockQuantity += amount;
    }

    // ===== Week 4 polymorphism (children override these) =====
    // Week 6 requirement: at least one abstract method in the parent class.
    public abstract String getType();

    public String getCareInstructions() {
        return "Standard care: wash at 30C, do not bleach.";
    }

    public String getDisplayInfo() {
        return "[" + getType() + "] " +
                "id=" + itemId +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", stock=" + stockQuantity;
    }

    @Override
    public String toString() {
        return "ClothingItem{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
