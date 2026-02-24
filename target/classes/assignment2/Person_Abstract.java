public abstract class Person_Abstract {
    private String name;
    private String phone;
    private int id;

    public Person_Abstract(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public int getId() { return id; }

    public abstract void displayInfo();
}