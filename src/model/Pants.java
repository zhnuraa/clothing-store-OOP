package model;

import exception.InvalidInputException;

public class Pants extends ClothingItem {

    public enum FitType { SLIM, REGULAR, OVERSIZED }

    private FitType fitType;
    private int waist;
    private int inseam;
    private String material;

    public Pants(int itemId, String name, String size, double price, String brand, int stockQuantity,
                 FitType fitType, int waist, int inseam, String material) {
        super(itemId, name, size, price, brand, stockQuantity);
        setFitType(fitType);
        setWaist(waist);
        setInseam(inseam);
        setMaterial(material);
    }

    public Pants() {
        super();
        this.fitType = FitType.REGULAR;
        this.waist = 32;
        this.inseam = 32;
        this.material = "Denim";
    }

    public FitType getFitType() { return fitType; }
    public int getWaist() { return waist; }
    public int getInseam() { return inseam; }
    public String getMaterial() { return material; }

    public void setFitType(FitType fitType) {
        if (fitType == null) {
            throw new InvalidInputException("Fit type cannot be null");
        }
        this.fitType = fitType;
    }

    public void setWaist(int waist) {
        if (waist <= 0) {
            throw new InvalidInputException("Waist must be positive");
        }
        this.waist = waist;
    }

    public void setInseam(int inseam) {
        if (inseam <= 0) {
            throw new InvalidInputException("Inseam must be positive");
        }
        this.inseam = inseam;
    }

    public void setMaterial(String material) {
        if (material == null || material.trim().isEmpty()) {
            throw new InvalidInputException("Material cannot be null or empty");
        }
        this.material = material.trim();
    }

    // Polymorphic behavior (overrides)
    @Override
    public String getType() {
        return "Pants";
    }

    @Override
    public String getCareInstructions() {
        if (material.equalsIgnoreCase("denim")) {
            return "Denim care: wash inside-out, cold water, air dry.";
        }
        return "Pants care: wash at 30C, do not bleach.";
    }

    @Override
    public String getDisplayInfo() {
        return super.getDisplayInfo() +
                ", fitType=" + fitType +
                ", waist=" + waist +
                ", inseam=" + inseam +
                ", material='" + material + "'";
    }

    // Child-only method (for instanceof + casting demo)
    public void sizeAdvice() {
        System.out.println("Pants advice: fit=" + fitType + ", waist=" + waist + ", inseam=" + inseam);
    }

    @Override
    public String toString() {
        return "Pants{" +
                "base=" + super.toString() +
                ", fitType=" + fitType +
                ", waist=" + waist +
                ", inseam=" + inseam +
                ", material='" + material + '\'' +
                '}';
    }
}
