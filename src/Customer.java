public class Customer {
    private final String name;
    private final String address;
    private final int age;
    private final String contactNumber;

    public Customer(String name, String address, int age, String contactNumber) {
        if (age < 0) throw new IllegalArgumentException("Age cannot be negative.");
        this.name = name;
        this.address = address;
        this.age = age;
        this.contactNumber = contactNumber;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public int getAge() { return age; }
    public String getContactNumber() { return contactNumber; }
    public String provideCustomerDetails() {
        return name + " | " + address + " | " + age + " | " + contactNumber;
    }
}
