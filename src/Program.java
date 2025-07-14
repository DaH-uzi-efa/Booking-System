import db.BookingDBService;
import dto.Booking;
import dto.Resource;
import dto.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Program {
    private boolean running = true;
    private final BookingDBService bookingDBService;

    public Program() {
        bookingDBService = new BookingDBService();
    }

    public void runMenu() {
        try (Scanner reader = new Scanner(System.in)){
            while (running) {
                menuText();
                int input = numberInput(reader);
                menuMethod(input, reader);
            }
        }
    }

    private static void menuText() {
        System.out.println("=============");
        System.out.println("Menu");
        System.out.println("1: Register user");
        System.out.println("2: Show users");
        System.out.println("3: Add resource");
        System.out.println("4: Show resources");
        System.out.println("5: Make booking");
        System.out.println("6: Show bookings");
        System.out.println("7: Quit");
        System.out.println("=============");
    }

    private int numberInput(Scanner reader) {
        try {
            return Integer.parseInt(reader.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid choice.");
            return numberInput(reader);
        }
    }

    private void menuMethod(int input, Scanner reader) {
        switch (input) {
            case 1 -> registerUser(reader);
            case 2 -> showUsers();
            case 3 -> addResource(reader);
            case 4 -> showResources();
            case 5 -> createBooking(reader);
            case 6 -> showBookings();
            case 7 -> quit();
            default -> System.out.println("Please enter a valid choice.");
        }
    }

    private void registerUser(Scanner reader) {
        System.out.println("Enter username");
        String username = reader.nextLine();
        System.out.println("Enter Password");
        String password = reader.nextLine();
        bookingDBService.registerUser(username, password);
        System.out.println("User: "+username+" is registered");
    }

    private void showUsers() {
        System.out.println("Showing all the users");
        List<User> userList = bookingDBService.showUsers();
        for (User user : userList) {
            System.out.println(user.id() + ": " + user.username());
        }
    }

    private void addResource(Scanner reader) {
        System.out.println("Enter Name");
        String name = reader.nextLine();
        System.out.println("Enter Location");
        String location = reader.nextLine();
        bookingDBService.addResource(name, location);
        System.out.println("Resource: "+name+" is added at location: "+location);
    }

    private void showResources() {
        System.out.println("Showing all the resources");
        List<Resource> resourceList = bookingDBService.showResources();
        for (Resource resource : resourceList) {
            System.out.println(resource.id() + ": " + resource.name() + " - " + resource.location());
        }
    }

    private void createBooking(Scanner reader) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("Enter user id");
        int userId = Integer.parseInt(reader.nextLine());
        System.out.println("Enter resource id");
        int resourceId = Integer.parseInt(reader.nextLine());
        System.out.println("Enter start time (format: yyyy-MM-dd HH:mm)");
        LocalDateTime startTime = LocalDateTime.parse(reader.nextLine(), formatter);
        System.out.println("Enter end time (format: yyyy-MM-dd HH:mm)");
        LocalDateTime endTime = LocalDateTime.parse(reader.nextLine(), formatter);
        bookingDBService.createBooking(userId, resourceId, startTime, endTime);
        System.out.println("Booking created");
    }

    private void showBookings() {
        System.out.println("Showing all the bookings");
        List<Booking> bookingList = bookingDBService.showBookings();
        for (Booking booking : bookingList) {
            System.out.println(booking.id() + ": " +
                    booking.userId() + " - " +
                    booking.resourceId() + " - " +
                    booking.startTime() + " - " +
                    booking.endTime() + " - " +
                    booking.createdAt());
        }
    }

    private void quit() {
        System.out.println("Quitting");
        running = false;
    }
}
