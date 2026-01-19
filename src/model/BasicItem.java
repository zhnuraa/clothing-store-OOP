package model;

/**
 * Concrete implementation for a "generic" clothing item.
 *
 * Since ClothingItem is abstract (Week 6 requirement), we use BasicItem
 * whenever the user wants to add a non-specialized item.
 */
public class BasicItem extends ClothingItem {

    public BasicItem(int itemId, String name, String size, double price, String brand, int stockQuantity) {
        super(itemId, name, size, price, brand, stockQuantity);
    }

    public BasicItem() {
        super();
    }

    @Override
    public String getType() {
        return "BasicItem";
    }
}
