public class Pharmacist extends Employee {
    private String licenseNumber;

    public Pharmacist(int id, String name, String phone, String employeeId, double salary, String licenseNumber) {
        super(id, name, phone, employeeId, salary);
        this.licenseNumber = licenseNumber;   // using 'this'
    }

    public String getLicenseNumber() { return licenseNumber; }

    public boolean verifyPrescription(String medicineName) {
        return medicineName != null && !medicineName.isEmpty();
    }

    @Override
    public void displayInfo() {
        super.displayInfo();  
        System.out.println("License: " + licenseNumber + " (Pharmacist)");
    }
}