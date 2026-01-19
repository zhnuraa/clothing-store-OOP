package model;

/**
 * Additional interface (Week 6 requirement).
 *
 * Anything that can provide a discount (e.g., VIP customers) implements this.
 */
public interface Discountable {
    /**
     * @return discount rate in range [0..1], e.g., 0.1 means 10% off
     */
    double getDiscountRate();

    /**
     * Applies the discount to the given amount.
     */
    default double applyDiscount(double amount) {
        return amount * (1.0 - getDiscountRate());
    }
}
