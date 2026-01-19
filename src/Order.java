package model;

import exception.InvalidInputException;
import java.util.ArrayList;

public class Order {

    public enum Status { PENDING, COMPLETED, CANCELLED }

    private static class Line {
        private ClothingItem item;
        private int quantity;

        Line(ClothingItem item, int quantity) {
            this.item = item;
            if (item == null) {
                throw new InvalidInputException("Item cannot be null");
            }
            if (quantity <= 0) {
                throw new InvalidInputException("Quantity must be positive");
            }
            this.quantity = quantity;
        }

        ClothingItem getItem() { return item; }
        int getQuantity() { return quantity; }

        void addQuantity(int add) {
            if (add > 0) quantity += add;
        }

        double getLineTotal() {
            return item.getPrice() * quantity;
        }

        @Override
        public String toString() {
            return "Line{itemId=" + item.getItemId() +
                    ", name='" + item.getName() + '\'' +
                    ", qty=" + quantity +
                    ", unitPrice=" + item.getPrice() +
                    ", lineTotal=" + getLineTotal() + "}";
        }
    }

    private int orderId;
    private Customer customer;
    private ArrayList<Line> lines;
    private Status status;

    public Order(int orderId, Customer customer) {
        setOrderId(orderId);
        setCustomer(customer);
        this.lines = new ArrayList<Line>();
        this.status = Status.PENDING;
    }

    public Order() {
        this.orderId = 1;
        this.customer = null;
        this.lines = new ArrayList<Line>();
        this.status = Status.PENDING;
    }

    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public Status getStatus() { return status; }

    public void setOrderId(int orderId) {
        if (orderId <= 0) {
            throw new InvalidInputException("Order ID must be positive");
        }
        this.orderId = orderId;
    }

    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new InvalidInputException("Customer cannot be null");
        }
        this.customer = customer;
    }

    public boolean isPending() {
        return status == Status.PENDING;
    }

    public double calculateTotal() {
        double sum = 0.0;
        for (int i = 0; i < lines.size(); i++) {
            sum += lines.get(i).getLineTotal();
        }
        return sum;
    }

    public boolean addItem(ClothingItem item, int quantity) {
        if (status != Status.PENDING) {
            throw new IllegalStateException("Cannot add items. Order is not PENDING");
        }
        if (customer == null) {
            throw new IllegalStateException("Cannot add items without customer");
        }
        if (item == null) {
            throw new InvalidInputException("Item cannot be null");
        }
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be positive");
        }

        // First reduce from stock (real store behavior)
        boolean reduced = item.reduceStock(quantity);
        if (!reduced) {
            return false;
        }

        // If the item is already in the order, just increase quantity
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            if (line.getItem().getItemId() == item.getItemId()) {
                line.addQuantity(quantity);
                return true;
            }
        }

        // Otherwise add a new line
        lines.add(new Line(item, quantity));
        return true;
    }

    public void complete() {
        if (status != Status.PENDING) {
            throw new IllegalStateException("Order cannot be completed. Current status: " + status);
        }
        if (customer == null) {
            throw new IllegalStateException("Cannot complete order without customer");
        }
        if (lines.isEmpty()) {
            throw new IllegalStateException("Cannot complete empty order");
        }
        status = Status.COMPLETED;
    }

    public void cancel() {
        if (status != Status.PENDING) {
            throw new IllegalStateException("Order cannot be cancelled. Current status: " + status);
        }

        // Return stock back
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            line.getItem().increaseStock(line.getQuantity());
        }

        status = Status.CANCELLED;
    }

    // Print order lines without exposing the internal list
    public void printLines() {
        if (lines.isEmpty()) {
            System.out.println("   (empty order)");
            return;
        }
        for (int i = 0; i < lines.size(); i++) {
            System.out.println("   - " + lines.get(i));
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customer=" + (customer == null ? "null" : customer.getName()) +
                ", linesCount=" + lines.size() +
                ", total=" + calculateTotal() +
                ", status=" + status +
                '}';
    }
}
