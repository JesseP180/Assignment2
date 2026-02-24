public abstract class Employee_Abstract extends Person {
    private String employeeId;
    private double salary;

    public Employee_Abstract(int id, String name, String phone, String employeeId, double salary) {
        super(id, name, phone);  // calling parent constructor
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