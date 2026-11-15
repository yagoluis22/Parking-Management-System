import java.util.ArrayList;
import java.util.Scanner;

class ParkingSpace {
    int number;
    String plate = "";

    ParkingSpace(int number) { this.number = number; }
    boolean isAvailable() { return plate.isEmpty(); }
}

class ParkingLot {
    private final ArrayList<ParkingSpace> spaces = new ArrayList<>();
    private final double hourlyRate = 5.00;

    ParkingLot(int capacity) {
        for (int i = 1; i <= capacity; i++) spaces.add(new ParkingSpace(i));
    }

    void parkVehicle(String plate) {
        for (ParkingSpace s : spaces) {
            if (s.plate.equals(plate)) {
                System.out.println("Vehicle is already parked.");
                return;
            }
        }
        for (ParkingSpace s : spaces) {
            if (s.isAvailable()) {
                s.plate = plate;
                System.out.println("Vehicle " + plate + " parked in space " + s.number + ".");
                return;
            }
        }
        System.out.println("Parking lot is full.");
    }

    void removeVehicle(String plate, int hours) {
        if (hours < 1) {
            System.out.println("Hours must be at least 1.");
            return;
        }
        for (ParkingSpace s : spaces) {
            if (s.plate.equals(plate)) {
                s.plate = "";
                System.out.printf("Vehicle %s exited. Fee: $%.2f%n", plate, hours * hourlyRate);
                return;
            }
        }
        System.out.println("Vehicle not found.");
    }

    void showStatus() {
        int occupied = 0;
        for (ParkingSpace s : spaces) {
            if (!s.isAvailable()) occupied++;
            System.out.println("Space " + s.number + ": "
                    + (s.isAvailable() ? "Available" : s.plate));
        }
        System.out.println("Occupied: " + occupied
                + " | Available: " + (spaces.size() - occupied));
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ParkingLot lot = new ParkingLot(10);
        int choice = 0;

        do {
            System.out.println("\nPARKING MANAGEMENT SYSTEM");
            System.out.println("1. Park vehicle\n2. Remove vehicle");
            System.out.print("3. Show parking status\n4. Exit\nChoose: ");
            if (!input.hasNextInt()) break;
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter licence plate: ");
                    lot.parkVehicle(input.next());
                    break;
                case 2:
                    System.out.print("Enter licence plate: ");
                    String plate = input.next();
                    System.out.print("Enter hours parked: ");
                    if (!input.hasNextInt()) return;
                    lot.removeVehicle(plate, input.nextInt());
                    break;
                case 3:
                    lot.showStatus();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (choice != 4);
        input.close();
    }
}
