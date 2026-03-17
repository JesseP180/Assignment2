import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    private static List<Medicine> medicines = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Pharmacist> pharmacists = new ArrayList<>();
    private static int nextSaleId = 1;
    private static Scanner scanner = new Scanner(System.in);

    public static void Main(String[] args) {
        initializeData();

        boolean exit = false;
        while (!exit) {
            printMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: addMedicine(); break;
                    case 2: addCustomer(); break;
                    case 3: addPharmacist(); break;
                    case 4: processSale(); break;
                    case 5: displayAll(); break;
                    case 6: exit = true; break;
                    default: System.out.println("Invalid choice. Please enter 1-6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input error: Please enter a number.");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            } finally {
                System.out.println(); 
            }
        }
        scanner.close();
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("===== MED SHOP MENU =====");
        System.out.println("1. Add Medicine");
        System.out.println("2. Add Customer");
        System.out.println("3. Add Pharmacist");
        System.out.println("4. Process Sale");
        System.out.println("5. Display All Records");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addMedicine() {
        try {
            System.out.print("Medicine name: ");
            String name = scanner.nextLine();
            System.out.print("Price: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Stock quantity: ");
            int stock = Integer.parseInt(scanner.nextLine());
            System.out.print("Expiry date (yyyy-mm-dd): ");
            LocalDate expiry = LocalDate.parse(scanner.nextLine());

            Medicine m = new Medicine(name, price, stock, expiry);
            medicines.add(m);
            System.out.println("Medicine added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter numeric values for price and stock.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-mm-dd.");
        }
    }

    private static void addCustomer() {
        try {
            System.out.print("Customer ID (integer): ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Phone: ");
            String phone = scanner.nextLine();
            System.out.print("Address: ");
            String address = scanner.nextLine();

            Customer c = new Customer(id, name, phone, address);
            customers.add(c);
            System.out.println("Customer added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter an integer.");
        }
    }

    private static void addPharmacist() {
        try {
            System.out.print("Pharmacist ID (integer): ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Phone: ");
            String phone = scanner.nextLine();
            System.out.print("Employee ID: ");
            String empId = scanner.nextLine();
            System.out.print("Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());
            System.out.print("License Number: ");
            String license = scanner.nextLine();

            Pharmacist p = new Pharmacist(id, name, phone, empId, salary, license);
            pharmacists.add(p);
            System.out.println("Pharmacist added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter numeric values for ID and salary.");
        }
    }

    private static void processSale() {
        if (customers.isEmpty() || pharmacists.isEmpty() || medicines.isEmpty()) {
            System.out.println("Cannot process sale. Ensure at least one customer, one pharmacist, and one medicine exist.");
            return;
        }

        try {
            // Select customer
            System.out.println("Select customer:");
            for (int i = 0; i < customers.size(); i++) {
                System.out.println(i + ": " + customers.get(i).getName());
            }
            System.out.print("Enter customer index: ");
            int custIdx = Integer.parseInt(scanner.nextLine());
            Customer customer = customers.get(custIdx);

            // Select pharmacist
            System.out.println("Select pharmacist:");
            for (int i = 0; i < pharmacists.size(); i++) {
                System.out.println(i + ": " + pharmacists.get(i).getName());
            }
            System.out.print("Enter pharmacist index: ");
            int pharmIdx = Integer.parseInt(scanner.nextLine());
            Pharmacist pharmacist = pharmacists.get(pharmIdx);

            // Create sale
            Sale sale = new Sale(nextSaleId++, customer, pharmacist);

            // Add medicines to sale
            boolean adding = true;
            while (adding) {
                System.out.println("Available medicines:");
                for (int i = 0; i < medicines.size(); i++) {
                    Medicine m = medicines.get(i);
                    System.out.printf("%d: %s (Stock: %d, Price: $%.2f, Expires: %s)%n",
                            i, m.getName(), m.getStock(), m.getPrice(), m.getExpiryDate());
                }
                System.out.print("Enter medicine index to add (or -1 to finish): ");
                int medIdx = Integer.parseInt(scanner.nextLine());
                if (medIdx == -1) {
                    adding = false;
                    break;
                }
                Medicine med = medicines.get(medIdx);

                System.out.print("Enter quantity: ");
                int qty = Integer.parseInt(scanner.nextLine());

                sale.addMedicine(med, qty);
                System.out.println("Added " + qty + " x " + med.getName());
            }

            sale.generateReceipt();

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index selected.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        } catch (ExpiredMedicineException e) {
            System.out.println("Cannot sell: " + e.getMessage());
        } catch (InsufficientStockException e) {
            System.out.println("Stock error: " + e.getMessage());
        }
    }

    private static void displayAll() {
        System.out.println("\n--- Medicines ---");
        for (Medicine m : medicines) {
            System.out.printf("%s | Price: $%.2f | Stock: %d | Expiry: %s%n",
                    m.getName(), m.getPrice(), m.getStock(), m.getExpiryDate());
        }
        System.out.println("\n--- Customers ---");
        for (Customer c : customers) {
            c.displayInfo();
        }
        System.out.println("\n--- Pharmacists ---");
        for (Pharmacist p : pharmacists) {
            p.displayInfo();
        }
    }

    private static void initializeData() {
        medicines.add(new Medicine("Paracetamol", 5.99, 100, LocalDate.of(2025, 12, 31)));
        medicines.add(new Medicine("Amoxicillin", 12.50, 50, LocalDate.of(2024, 6, 30)));
        medicines.add(new Medicine("Insulin", 45.00, 20, LocalDate.of(2023, 1, 1))); // expired

        customers.add(new Customer(1, "Alice", "555-1234", "123 Main St"));
        customers.add(new Customer(2, "Bob", "555-5678", "456 Oak Ave"));

        pharmacists.add(new Pharmacist(101, "Dr. Smith", "555-8765", "E1001", 75000, "LIC001"));
    }
}