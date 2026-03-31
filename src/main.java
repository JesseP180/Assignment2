package assignment2;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.io.*;

public class Main {
    private static List<Medicine> medicines = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Pharmacist> pharmacists = new ArrayList<>();
    private static int nextSaleId = 1;
    private static Scanner scanner = new Scanner(System.in);

    // File names for persistence
    private static final String MEDICINES_FILE = "medicines.txt";
    private static final String CUSTOMERS_FILE = "customers.txt";
    private static final String PHARMACISTS_FILE = "pharmacists.txt";

    public static void main(String[] args) {
        // Load data from files if they exist
        loadData();

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
                    case 6: 
                        saveData();
                        exit = true;
                        break;
                    case 7:
                        // Exit without saving (discard changes)
                        exit = true;
                        break;
                    default: System.out.println("Invalid choice. Please enter 1-7.");
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
        System.out.println("6. Save and Exit");
        System.out.println("7. Exit without Saving");
        System.out.print("Enter your choice: ");
    }

    // ----------------------------------------------------------------
    // File I/O Methods
    // ----------------------------------------------------------------
    private static void saveData() {
        saveMedicines();
        saveCustomers();
        savePharmacists();
        System.out.println("Data saved successfully.");
    }

    private static void loadData() {
        loadMedicines();
        loadCustomers();
        loadPharmacists();
    }

    private static void saveMedicines() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEDICINES_FILE))) {
            for (Medicine m : medicines) {
                // Format: name|price|stock|expiryDate
                writer.write(m.getName() + "|" + m.getPrice() + "|" + m.getStock() + "|" + m.getExpiryDate());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving medicines: " + e.getMessage());
        }
    }

    private static void loadMedicines() {
        File file = new File(MEDICINES_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    int stock = Integer.parseInt(parts[2]);
                    LocalDate expiry = LocalDate.parse(parts[3]);
                    medicines.add(new Medicine(name, price, stock, expiry));
                }
            }
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            System.out.println("Error loading medicines: " + e.getMessage());
        }
    }

    private static void saveCustomers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer c : customers) {
                // Format: id|name|phone|address|prescriptions (comma-separated)
                StringBuilder presc = new StringBuilder();
                for (String p : c.getPrescriptions()) {
                    if (presc.length() > 0) presc.append(",");
                    presc.append(p);
                }
                writer.write(c.getId() + "|" + c.getName() + "|" + c.getPhone() + "|" + c.getAddress() + "|" + presc.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    private static void loadCustomers() {
        File file = new File(CUSTOMERS_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String phone = parts[2];
                    String address = parts[3];
                    Customer c = new Customer(id, name, phone, address);
                    // Load prescriptions if any
                    if (!parts[4].isEmpty()) {
                        String[] prescriptions = parts[4].split(",");
                        for (String p : prescriptions) {
                            c.addPrescription(p);
                        }
                    }
                    customers.add(c);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }

    private static void savePharmacists() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PHARMACISTS_FILE))) {
            for (Pharmacist p : pharmacists) {
                // Format: id|name|phone|employeeId|salary|licenseNumber
                writer.write(p.getId() + "|" + p.getName() + "|" + p.getPhone() + "|" + 
                             p.getEmployeeId() + "|" + p.getSalary() + "|" + p.getLicenseNumber());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving pharmacists: " + e.getMessage());
        }
    }

    private static void loadPharmacists() {
        File file = new File(PHARMACISTS_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String phone = parts[2];
                    String empId = parts[3];
                    double salary = Double.parseDouble(parts[4]);
                    String license = parts[5];
                    pharmacists.add(new Pharmacist(id, name, phone, empId, salary, license));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading pharmacists: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------------
    // Existing methods (addMedicine, addCustomer, addPharmacist, processSale, displayAll, initializeData)
    // ----------------------------------------------------------------
    // (They remain exactly as before, but I'll include them for completeness)

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
            System.out.println("Select customer:");
            for (int i = 0; i < customers.size(); i++) {
                System.out.println(i + ": " + customers.get(i).getName());
            }
            System.out.print("Enter customer index: ");
            int custIdx = Integer.parseInt(scanner.nextLine());
            Customer customer = customers.get(custIdx);

            System.out.println("Select pharmacist:");
            for (int i = 0; i < pharmacists.size(); i++) {
                System.out.println(i + ": " + pharmacists.get(i).getName());
            }
            System.out.print("Enter pharmacist index: ");
            int pharmIdx = Integer.parseInt(scanner.nextLine());
            Pharmacist pharmacist = pharmacists.get(pharmIdx);

            Sale sale = new Sale(nextSaleId++, customer, pharmacist);

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
        
    }
}