/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package assignment2;

/**
 *
 * @author Theha
 */
public class Assignment2 {
    public static void main(String[] args) {
        
        Customer cust = new Customer(1, "Alice", "555-1234", "123 Main St");
        Pharmacist pharm = new Pharmacist(101, "Dr. Bob", "555-5678", "E1001", 75000, "LIC001");

        Medicine med1 = new Medicine("Paracetamol", 5.99, 100, LocalDate.of(2025, 12, 31));
        Medicine med2 = new Medicine("Insulin", 45.00, 20, LocalDate.of(2023, 1, 1)); // expired

        
        System.out.println("=== Person References ===");
        Person p1 = cust;
        Person p2 = pharm;
        p1.displayInfo();  // calls Customer's displayInfo
        p2.displayInfo();  // calls Pharmacist's displayInfo

        
        System.out.println("\n=== Interface Methods ===");
        Sellable s = med1;
        System.out.println("Price for 3 Paracetamol: $" + s.calculateTotalPrice(3));
        System.out.println("Price after 10% discount: $" + s.applyDiscount(10));

        Expirable e = med2;
        System.out.println("Is Insulin expired? " + e.isExpired());

       
        cust.addPrescription("Paracetamol 500mg");
        cust.addPrescription("Amoxicillin");
        System.out.println("\n=== Customer Prescriptions ===");
        System.out.println(cust.getName() + "'s prescriptions: " + cust.getPrescriptions());

    
        System.out.println("\n=== Pharmacist Verification ===");
        boolean valid = pharm.verifyPrescription("Paracetamol 500mg");
        System.out.println("Prescription valid? " + valid);

        
        System.out.println("\n=== Sale ===");
        int quantity = 2;
        if (!med1.isExpired() && med1.getStock() >= quantity) {
            double total = med1.calculateTotalPrice(quantity);
            System.out.println("Sold " + quantity + " " + med1.getName() + " for $" + total);
            med1.reduceStock(quantity);
            System.out.println("Remaining stock: " + med1.getStock());
        } else {
            System.out.println("Cannot sell: expired or insufficient stock.");
        }

        
        System.out.println("\n=== Constructor Chaining ===");
        System.out.println("All objects created using 'this' and 'super' in constructors.");
    }
}
