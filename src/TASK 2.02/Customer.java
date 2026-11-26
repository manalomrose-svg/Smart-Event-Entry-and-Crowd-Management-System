import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer {
    private static final int MAX_AGE = 150;

    private final String name;
    private final String address;
    private final int age;
    private final String contactNumber;

    public Customer(String name, String address, int age, String contactNumber) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        }
        if (age > MAX_AGE) {
            throw new IllegalArgumentException("Age cannot be greater than " + MAX_AGE + ".");
        }
        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact number cannot be null or empty.");
        }
        if (!contactNumber.matches("[0-9+\\-\\s]{7,15}")) {
            throw new IllegalArgumentException(
                "Contact number must be 7-15 characters and contain only digits, '+', '-' or spaces.");
        }

        this.name = name.trim();
        this.address = address.trim();
        this.age = age;
        this.contactNumber = contactNumber.trim();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String provideCustomerDetails() {
        return name + " | " + address + " | " + age + " | " + contactNumber;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Customer> customers = new ArrayList<>();
        boolean running = true;

        while (running) {
            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. Add a customer");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    Customer customer = null;

                    while (customer == null) {
                        try {
                            System.out.print("Enter name: ");
                            String name = scanner.nextLine();

                            System.out.print("Enter address: ");
                            String address = scanner.nextLine();

                            System.out.print("Enter age: ");
                            int age = Integer.parseInt(scanner.nextLine().trim());

                            System.out.print("Enter contact number: ");
                            String contactNumber = scanner.nextLine();

                            customer = new Customer(name, address, age, contactNumber);
                        } catch (NumberFormatException e) {
                            System.out.println("Refused: Age must be a whole number.\n");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Refused: " + e.getMessage() + "\n");
                        }
                    }

                    customers.add(customer);
                    System.out.println("\nCustomer accepted:");
                    System.out.println(customer.provideCustomerDetails());
                    break;

                case "2":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }

        System.out.println("\n===== CUSTOMERS ADDED (" + customers.size() + ") =====");
        for (Customer c : customers) {
            System.out.println(c.provideCustomerDetails());
        }
        System.out.println("Goodbye!");
        scanner.close();
    }
}