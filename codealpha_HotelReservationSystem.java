import java.util.*;
import java.io.*;

class Room {
    int roomNumber;
    String category;
    double price;
    boolean isAvailable;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isAvailable = true;
    }
}

class Reservation {
    String customerName;
    int roomNumber;
    String category;
    double amountPaid;

    Reservation(String customerName, int roomNumber, String category, double amountPaid) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.amountPaid = amountPaid;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initializeRooms();
        loadReservations();

        int choice;
        do {
            System.out.println("\n===== Hotel Reservation System =====");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Reservations");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewAvailableRooms();
                case 2 -> bookRoom();
                case 3 -> cancelReservation();
                case 4 -> viewReservations();
                case 0 -> saveReservations();
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);

        System.out.println("Thank you for using the system!");
    }

    static void initializeRooms() {
        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));
        rooms.add(new Room(301, "Suite", 4000));
    }

    static void viewAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");
        for (Room r : rooms) {
            if (r.isAvailable) {
                System.out.println("Room " + r.roomNumber +
                        " | " + r.category +
                        " | ₹" + r.price);
            }
        }
    }

    static void bookRoom() {
        viewAvailableRooms();
        System.out.print("Enter room number to book: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        for (Room r : rooms) {
            if (r.roomNumber == roomNo && r.isAvailable) {

                System.out.print("Enter customer name: ");
                String name = sc.nextLine();

                System.out.println("Amount to pay: ₹" + r.price);
                System.out.print("Confirm payment (yes/no): ");
                String pay = sc.nextLine();

                if (pay.equalsIgnoreCase("yes")) {
                    r.isAvailable = false;
                    reservations.add(new Reservation(name, roomNo, r.category, r.price));
                    System.out.println("Booking successful!");
                    return;
                } else {
                    System.out.println("Payment cancelled.");
                    return;
                }
            }
        }
        System.out.println("Room not available.");
    }

    static void cancelReservation() {
        System.out.print("Enter room number to cancel: ");
        int roomNo = sc.nextInt();

        Iterator<Reservation> it = reservations.iterator();
        while (it.hasNext()) {
            Reservation res = it.next();
            if (res.roomNumber == roomNo) {
                it.remove();
                for (Room r : rooms) {
                    if (r.roomNumber == roomNo)
                        r.isAvailable = true;
                }
                System.out.println("Reservation cancelled successfully.");
                return;
            }
        }
        System.out.println("Reservation not found.");
    }

    static void viewReservations() {
        System.out.println("\n--- Booking Details ---");
        for (Reservation r : reservations) {
            System.out.println("Customer: " + r.customerName +
                    " | Room: " + r.roomNumber +
                    " | Category: " + r.category +
                    " | Paid: ₹" + r.amountPaid);
        }
    }

    static void saveReservations() {
        try (PrintWriter pw = new PrintWriter("reservations.txt")) {
            for (Reservation r : reservations) {
                pw.println(r.customerName + "," + r.roomNumber + "," + r.category + "," + r.amountPaid);
            }
        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    static void loadReservations() {
        File file = new File("reservations.txt");
        if (!file.exists())
            return;

        try (Scanner fs = new Scanner(file)) {
            while (fs.hasNextLine()) {
                String[] data = fs.nextLine().split(",");
                Reservation r = new Reservation(
                        data[0],
                        Integer.parseInt(data[1]),
                        data[2],
                        Double.parseDouble(data[3])
                );
                reservations.add(r);

                for (Room room : rooms) {
                    if (room.roomNumber == r.roomNumber)
                        room.isAvailable = false;
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading reservations.");
        }
    }
}
