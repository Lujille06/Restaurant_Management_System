import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Dish {
    private String name;
    private double price;

    Dish(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " - ₱" + String.format("%,.2f", price);
    }
}

class Customer {
    private String name;
    private String contact;

    Customer(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String getName() { return name; }
    public String getContact() { return contact; }
}

class Order {
    private Customer customer;
    private List<Dish> orderedDishes = new ArrayList<>();

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void addDish(Dish dish) {
        orderedDishes.add(dish);
    }

    public void displayOrderedDishes() {
        int count = 1;
        System.out.println("Ordered Dishes:");
        for (Dish d : orderedDishes) {
            System.out.println(count + ". " + d);
            count++;
        }
    }

    public double computeTotal() {
        double total = 0;
        for (Dish d : orderedDishes) {
            total += d.getPrice();
        }
        return total;
    }

    public void displaySummary() {
        System.out.println("--------------------------------------------");
        System.out.println("                 ORDER SUMMARY");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Contact: " + customer.getContact());
        System.out.println();

        displayOrderedDishes();

        System.out.println("\n--------------------------------------------");
        System.out.printf("Total Bill: ₱%,.2f%n", computeTotal());
    }
}

class Restaurant {
    private List<Dish> dishList = new ArrayList<>();
    private List<Customer> customerList = new ArrayList<>();
    private List<Order> orderList = new ArrayList<>();

    public void addDish(Dish dish) {
        dishList.add(dish);
        System.out.println("Dish added successfully!");
    }

    public void addCustomer(Customer customer) {
        customerList.add(customer);
        System.out.println("Customer added successfully!");
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }

    public List<Dish> getDishes() {
        return Collections.unmodifiableList(dishList);
    }

    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customerList);
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orderList);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Restaurant restaurant = new Restaurant();

        Order currentOrder = null;

        int operation;

        do {
            showMenu();
            operation = askInt(scanner, "Choose an option: ");

            switch (operation) {
                case 1:
                    addDish(scanner, restaurant);
                    break;

                case 2:
                    showDishMenu(restaurant.getDishes());
                    break;

                case 3:
                    addCustomer(scanner, restaurant);
                    break;

                case 4:
                    currentOrder = createOrder(scanner, restaurant);
                    break;

                case 5:
                    if (currentOrder == null) {
                        System.out.println("Create an order first!");
                    } else {
                        addDishToOrder(scanner, restaurant.getDishes(), currentOrder);
                    }
                    break;

                case 6:
                    if (currentOrder == null) {
                        System.out.println("No active order yet!");
                    } else {
                        currentOrder.displaySummary();
                    }
                    break;

                case 7:
                    System.out.println("Thank you for visiting!");
                    break;
            }
        } while (operation != 7);
    }

    private static void showMenu() {
        System.out.println("\n=== RESTAURANT MANAGEMENT SYSTEM ===");
        System.out.println("1. Add Dish to Menu");
        System.out.println("2. View Menu");
        System.out.println("3. Create Customer");
        System.out.println("4. Create Order");
        System.out.println("5. Add Dish to Order");
        System.out.println("6. View Order Summary");
        System.out.println("7. Exit");
    }

    private static int askInt(Scanner scanner, String msg) {
        System.out.print(msg);
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private static String askString(Scanner scanner, String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

    private static void addDish(Scanner scanner, Restaurant restaurant) {
        System.out.println("ADD DISH");
        String name = askString(scanner, "Enter dish name: ");
        double price = askInt(scanner, "Enter price: ");
        restaurant.addDish(new Dish(name, price));
    }

    private static void showDishMenu(List<Dish> dishes) {
        System.out.println("MENU:");
        int count = 1;
        for (Dish d : dishes) {
            System.out.println(count + ". " + d);
            count++;
        }
    }

    private static void addCustomer(Scanner scanner, Restaurant restaurant) {
        System.out.println("ADD CUSTOMER");
        String name = askString(scanner, "Enter customer name: ");
        String contact = askString(scanner, "Enter contact number: ");
        restaurant.addCustomer(new Customer(name, contact));
    }

    private static Order createOrder(Scanner scanner, Restaurant restaurant) {
        System.out.println("CREATE ORDER");
        List<Customer> customers = restaurant.getCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers yet!");
            return null;
        }

        System.out.println("Select Customer:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ". " + customers.get(i).getName());
        }

        int choice = askInt(scanner, "Enter choice: ");
        Customer selectedCustomer = customers.get(choice - 1);

        Order order = new Order();
        order.setCustomer(selectedCustomer);
        restaurant.addOrder(order);

        System.out.println("Order created for: " + selectedCustomer.getName());
        return order;
    }

    private static void addDishToOrder(Scanner scanner, List<Dish> dishes, Order order) {
        if (dishes.isEmpty()) {
            System.out.println("No dishes in the menu!");
            return;
        }

        System.out.println("Select Dish:");
        showDishMenu(dishes);

        int choice = askInt(scanner, "Enter choice: ");
        Dish selectedDish = dishes.get(choice - 1);

        order.addDish(selectedDish);
        System.out.println(selectedDish.getName() + " added to order!");
    }
}
