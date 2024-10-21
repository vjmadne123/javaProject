package HotelManagement.com;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Room {
    private String roomNumber;
    private String roomType;
    private double price;
    private boolean isBooked;

    public Room(String roomNumber, String roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isBooked = false;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookRoom() {
        isBooked = true;
    }

    public void cancelBooking() {
        isBooked = false;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + roomType + ") - $" + price + " - " + (isBooked ? "Booked" : "Available");
    }
}

class Customer {
    private String customerId;
    private String name;
    private String phone;

    public Customer(String customerId, String name, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ID: " + customerId + ", Name: " + name + ", Phone: " + phone;
    }
}

class Booking {
    private static int bookingCount = 1;
    private int bookingId;
    private Customer customer;
    private Room room;

    public Booking(Customer customer, Room room) {
        this.bookingId = bookingCount++;
        this.customer = customer;
        this.room = room;
    }

    public int getBookingId() {
        return bookingId;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId + ", Customer: " + customer.getName() + ", Room: " + room.getRoomNumber();
    }
}

class HotelManagementSystem {
    private List<Room> rooms;
    private List<Customer> customers;
    private List<Booking> bookings;

    public HotelManagementSystem() {
        rooms = new ArrayList<>();
        customers = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void addRoom(String roomNumber, String roomType, double price) {
        rooms.add(new Room(roomNumber, roomType, price));
        System.out.println("Room added successfully.");
    }

    public void viewRooms() {
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    public void addCustomer(String customerId, String name, String phone) {
        customers.add(new Customer(customerId, name, phone));
        System.out.println("Customer added successfully.");
    }

    public void viewCustomers() {
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    public void bookRoom(String customerId, String roomNumber) {
        Customer customer = customers.stream()
                .filter(c -> c.getCustomerId().equals(customerId))
                .findFirst()
                .orElse(null);
        Room room = rooms.stream()
                .filter(r -> r.getRoomNumber().equals(roomNumber))
                .findFirst()
                .orElse(null);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }
        if (room.isBooked()) {
            System.out.println("Room is already booked.");
            return;
        }

        room.bookRoom();
        Booking booking = new Booking(customer, room);
        bookings.add(booking);
        System.out.println("Room booked successfully.");
    }

    public void checkIn(int bookingId) {
        Booking booking = bookings.stream()
                .filter(b -> b.getBookingId() == bookingId)
                .findFirst()
                .orElse(null);
        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }
        System.out.println("Checked in: " + booking);
    }

    public void checkOut(int bookingId) {
        Booking booking = bookings.stream()
                .filter(b -> b.getBookingId() == bookingId)
                .findFirst()
                .orElse(null);
        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        booking.getRoom().cancelBooking();
        bookings.remove(booking);
        System.out.println("Checked out: " + booking);
    }

    public void viewBookings() {
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    public void reportAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            if (!room.isBooked()) {
                System.out.println(room);
            }
        }
    }
}

public class HotelManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelManagementSystem system = new HotelManagementSystem();

        while (true) {
            System.out.println("\nHotel Management System");
            System.out.println("1. Add Room");
            System.out.println("2. View Rooms");
            System.out.println("3. Add Customer");
            System.out.println("4. View Customers");
            System.out.println("5. Book Room");
            System.out.println("6. Check In");
            System.out.println("7. Check Out");
            System.out.println("8. View Bookings");
            System.out.println("9. Report Available Rooms");
            System.out.println("10. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter room number: ");
                    String roomNumber = scanner.nextLine();
                    System.out.print("Enter room type: ");
                    String roomType = scanner.nextLine();
                    System.out.print("Enter room price: ");
                    double price = scanner.nextDouble();
                    system.addRoom(roomNumber, roomType, price);
                    break;

                case 2:
                    system.viewRooms();
                    break;

                case 3:
                    System.out.print("Enter customer ID: ");
                    String customerId = scanner.nextLine();
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter customer phone: ");
                    String phone = scanner.nextLine();
                    system.addCustomer(customerId, name, phone);
                    break;

                case 4:
                    system.viewCustomers();
                    break;

                case 5:
                    System.out.print("Enter customer ID: ");
                    String custId = scanner.nextLine();
                    System.out.print("Enter room number: ");
                    String rmNumber = scanner.nextLine();
                    system.bookRoom(custId, rmNumber);
                    break;

                case 6:
                    System.out.print("Enter booking ID to check in: ");
                    int bookingIdCheckIn = scanner.nextInt();
                    system.checkIn(bookingIdCheckIn);
                    break;

                case 7:
                    System.out.print("Enter booking ID to check out: ");
                    int bookingIdCheckOut = scanner.nextInt();
                    system.checkOut(bookingIdCheckOut);
                    break;

                case 8:
                    system.viewBookings();
                    break;

                case 9:
                    system.reportAvailableRooms();
                    break;

                case 10:
                    System.out.println("Exiting the system.");
                    scanner.close();
                    return;


                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}




