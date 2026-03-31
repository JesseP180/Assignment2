package assignment2;

public interface Sellable {
    double calculateTotalPrice(int quantity);
    double applyDiscount(double percentage);
}