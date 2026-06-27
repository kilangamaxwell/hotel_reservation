package ui;

import api.AdminResource;
import model.*;

import java.util.*;

public class AdminMenu {
    private static final AdminResource adminResource = new AdminResource();
    private static final Scanner scanner = new Scanner(System.in);

    public static void launch(){
        String selection = "";

        while(!selection.equals("5")) {
            System.out.println("\n*** ADMIN MENU ***");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select your choice: ");

            selection = scanner.nextLine();

            switch(selection) {
                case "1":
                    seeAllCustomers();
                    break;
                case "2":
                    seeAllRooms();
                    break;
                case "3":
                    seeAllReservations();
                    break;
                case "4":
                    addARoom();
                    break;
                case "5":
                    System.out.println("Back to main menu");
                    return;
                default:
                    System.out.println("Invalid selection. Try Again.");
            }
        }
    }

    private static void addARoom() {
        Scanner scanner = new Scanner(System.in);
        boolean isDigit = false;
        double price = 0.0;
        String roomNumber = "000";

        // Validate roomNumber
        while (!isDigit) {
            System.out.print("Please enter room number: ");
            roomNumber = scanner.nextLine();
            try {
                int checkNumber = Integer.parseInt(roomNumber);
                isDigit = true;
                if (checkNumber < 0) {
                    throw new IllegalArgumentException("Invalid format: Room number cannot be negative.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid room number.");
            } catch (IllegalArgumentException e){
                e.getLocalizedMessage();
            }
        }

        // Validate price
        boolean isPositive = false;
        while (!isPositive) {
            System.out.print("Please enter the price: ");
            try {
                price = Double.parseDouble(scanner.nextLine());
                if (price < 0.0) {
                    throw new IllegalArgumentException("Invalid price: Price cannot be negative.");
                }
                isPositive = true;
            } catch (IllegalArgumentException e) {
                e.getLocalizedMessage();
            }
        }

        //Validate roomType
        int type = 0;
        RoomType roomType = RoomType.SINGLE;
        boolean isRoomType = false;
        while (!isRoomType) {
            try {
                System.out.print("Please enter a room type (1 = Single, 2 = Double): ");
                type = Integer.parseInt(scanner.nextLine());
                if (type == 2) {
                    roomType = RoomType.DOUBLE;
                    isRoomType = true;
                }
                else if (type == 1) {
                    isRoomType = true;
                } else {
                    throw new IllegalArgumentException("Invalid room type.");
                }
            } catch (IllegalArgumentException e){
                e.getLocalizedMessage();
            }
        }

        IRoom room;

        try {
            if (price > 0.0) {
                room = new Room(roomNumber, price, roomType);
                adminResource.addRoom(Collections.singletonList(room));
            } else {
                room = new FreeRoom(roomNumber, roomType);
                adminResource.addRoom(Collections.singletonList(room));
            }
            System.out.println("Room has been added successfully.");
        } catch (IllegalArgumentException e){
            System.out.println(e.getLocalizedMessage());
        }
    }

    private static void seeAllReservations() {
        System.out.println("Displaying all reservations...");
        adminResource.printAllReservations();
    }

    private static void seeAllRooms() {
        System.out.println("Displaying all rooms...");
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            for (IRoom room : rooms) {
                System.out.println(room);
            }
        }
    }

    private static void seeAllCustomers() {
        System.out.println("Displaying all customers...");
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }
}
