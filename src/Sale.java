import java.time.LocalDate;
import java.util.*;

public class Sale {
    private int saleId;
    private Customer customer;
    private Pharmacist pharmacist;
    private Map<Medicine, Integer> medicinesSold;
    private double totalAmount;
    private LocalDate saleDate;

    public Sale(int saleId, Customer customer, Pharmacist pharmacist) {
        this.saleId = saleId;
        this.customer = customer;
        this.pharmacist = pharmacist;
        this.medicinesSold = new HashMap<>();
        this.saleDate = LocalDate.now();
        this.totalAmount = 0.0;
    }

    public void addMedicine(Medicine medicine, int quantity) 
            throws ExpiredMedicineException, InsufficientStockException {
        if (medicine.isExpired()) {
            throw new ExpiredMedicineException(medicine.getName() + " is expired and cannot be sold.");
        }
        medicine.reduceStock(quantity);  
        medicinesSold.put(medicine, quantity);
        totalAmount += medicine.calculateTotalPrice(quantity);
    }

    public double calculateTotalAmount() {
        return totalAmount;
    }

    public void generateReceipt() {
        System.out.println("\n===== Receipt =====");
        System.out.println("Sale ID: " + saleId);
        System.out.println("Date: " + saleDate);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Pharmacist: " + pharmacist.getName());
        System.out.println("Items:");
        for (Map.Entry<Medicine, Integer> entry : medicinesSold.entrySet()) {
            Medicine m = entry.getKey();
            int qty = entry.getValue();
            System.out.printf("  %s x%d @ $%.2f = $%.2f%n", m.getName(), qty, m.getPrice(), m.getPrice() * qty);
        }
        System.out.printf("Total: $%.2f%n", totalAmount);
        System.out.println("===================");
    }
}