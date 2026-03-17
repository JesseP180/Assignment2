import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private String address;
    private List<String> prescriptions;

    public Customer(int id, String name, String phone, String address) {
        super(id, name, phone);
        this.address = address;
        this.prescriptions = new ArrayList<>();
    }

    public String getAddress() { return address; }

    public void addPrescription(String prescription) {
        prescriptions.add(prescription);
    }

    public List<String> getPrescriptions() { return prescriptions; }

    @Override
    public void displayInfo() {
        System.out.println("Customer ID: " + getId() + ", Name: " + getName() + ", Phone: " + getPhone());
        System.out.println("Address: " + address);
        System.out.println("Prescriptions: " + prescriptions);
    }
}