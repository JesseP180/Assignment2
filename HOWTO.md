# MedShop 

 Project Description
MedShop is a simple pharmacy management system that demonstrates core Object-Oriented Programming concepts in Java. It allows users to:
- Add medicines, customers, and pharmacists.
- Process sales by selecting items and quantities.
- Validate business rules (stock availability, expiry dates) using custom exceptions.
- Handle user input errors gracefully.

The project includes:
- **Abstract classes**: `Person`, `Employee`
- **Interfaces**: `Sellable`, `Expirable`
- **Concrete classes**: `Customer`, `Pharmacist`, `Medicine`, `Sale`
- **Custom exceptions**: `InsufficientStockException`, `ExpiredMedicineException`

## Exceptions Implemented

 1. Built-in Exceptions
 `NumberFormatException`: Caught when user enters non-numeric values for numeric fields (price, stock, ID, etc.).
 `DateTimeParseException`: Caught when an invalid date format is entered for medicine expiry.
 `IndexOutOfBoundsException`: Caught when user selects an invalid index from a list.

 2. Custom Exceptions
 `InsufficientStockException`: Thrown when trying to sell more units than available in stock.
 `ExpiredMedicineException`: Thrown when attempting to sell a medicine whose expiry date has passed.

 Exception Handling Strategy
 All user input is wrapped in `try-catch` blocks with specific exception handlers.
 Business logic in `Medicine.reduceStock()` and `Sale.addMedicine()` throws custom exceptions.
 A `finally` block is used after the main menu loop to close the `Scanner` resource.
 Each exception provides a clear error message, and the program continues without crashing.

