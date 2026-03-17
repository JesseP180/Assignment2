public abstract class Employee extends Person {
    private String employeeId;
    private double salary;

    public Employee(int id, String name, String phone, String employeeId, double salary) {
        super(id, name, phone);  
        this.employeeId = employeeId;
        this.salary = salary;
    }

    public String getEmployeeId() { return employeeId; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public void displayInfo() {
        System.out.println("Employee ID: " + employeeId + ", Name: " + getName() + ", Phone: " + getPhone());
    }
}