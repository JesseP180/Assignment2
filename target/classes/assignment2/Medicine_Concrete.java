import java.time.LocalDate;

public class Medicine_Concrete implements Sellable, Expirable {
    private String name;
    private double price;
    private int stock;
    private LocalDate expiryDate;

    public Medicine(String name, double price, int stock, LocalDate expiryDate) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.expiryDate = expiryDate;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public LocalDate getExpiryDate() { return expiryDate; }

    public void reduceStock(int quantity) {
        if (quantity <= stock) {
            stock -= quantity;
        } else {
            System.out.println("Not enough stock!");
        }
    }

    @Override
    public double calculateTotalPrice(int quantity) {
        return price * quantity;
    }

    @Override
    public double applyDiscount(double percentage) {
        return price * (1 - percentage / 100);
    }

    @Override
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }
}