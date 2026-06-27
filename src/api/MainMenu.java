package api;

import model.IRoom;
import model.Reservation;
//import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static final HotelResource hotelResource = new HotelResource();
    private static final Scanner scanner = new Scanner(System.in);

    public static void launch(){
        String selection = "";

        while(!selection.equals("5")) {
            System.out.println("\n*** MAIN MENU ***");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.print("Select your choice: ");

            selection = scanner.nextLine();

           switch(selection) {
               case "1":
                   findAndReserveARoom();
                   break;
               case "2":
                   seeMyReservations();
                   break;
               case "3":
                   createAnAccount();
                   break;
               case "4":
                   accessAdminMenu();
                   break;
               case "5":
                   exitApplication();
                   break;
               default:
                   System.out.println("Invalid selection. Try Again.");
            }
        }
    }

    private static void exitApplication() {
        System.out.println("Exiting Application...");
        System.exit(0);
    }

    private static void accessAdminMenu() {
        AdminMenu.launch();
    }

    private static void createAnAccount() {
        String email = null;
        int retries = 3;
        while (email == null || !hotelResource.isValidEmail(email) && retries < 3) {
            retries -= 1;
            try {
                System.out.println("Enter your email. You have " + retries + " attempts left: ");
                email = scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.print(e.getLocalizedMessage());
            }
        }

        String firstName = null;
        while (firstName == null) {
            System.out.println("Enter your first name: ");
            firstName = scanner.nextLine();
        }

        String lastName = null;
        while (lastName == null) {
            System.out.println("Enter your last name: ");
            lastName = scanner.nextLine();
        }

        try {
            hotelResource.createCustomer(email, firstName, lastName);
        } catch (IllegalStateException e) {
            System.out.print(e.getLocalizedMessage());
        }
        System.out.println("New Account created.");
    }

    private static void seeMyReservations() {
        String emailAddress = null;
        while (!hotelResource.isValidEmail(emailAddress)) {
            try{
            System.out.println("Please provide your email address: ");
            emailAddress = scanner.nextLine();
            } catch (IllegalStateException e) {
                System.out.print(e.getLocalizedMessage());
            }
        }

        Collection<Reservation> reservations = hotelResource.getCustomersReservations(emailAddress);
        if(reservations.isEmpty()) {
            System.out.println("There are no reservations found for this account.");
        }
        for (Reservation reservation : reservations){
            System.out.println(reservation);
        }

    }

    private static void reserveRoom(Date checkInDate, Date checkOutDate, Collection<IRoom> rooms){
        IRoom room = null;
        while (!rooms.contains(room)) {
            System.out.println("Please select your desired room number: ");
            String roomNumber = scanner.nextLine();

            room = hotelResource.getRoom(roomNumber);

            if (room == null || !rooms.contains(room)) {
                System.out.println("Try again.");
            }

        }

        String customerEmail = null;
        int retries = 0;
        while (!hotelResource.isValidEmail(customerEmail)) {
            retries += 1;
            System.out.println("Enter your email address: ");
            String emailInput = scanner.nextLine();

            if (hotelResource.isValidEmail(emailInput) && hotelResource.getCustomer(emailInput) != null) {
                customerEmail = emailInput;
            } else {
                System.out.println("Invalid or unknown email. Attempt: " + retries + " Try again.");
            }
            if (retries > 3) {
                System.out.println("Exceeded maximum number of attempts.");
                return;
            }
        }

        try{
            Reservation reservation = hotelResource.bookARoom(
                    customerEmail,
                    room,
                    checkInDate,
                    checkOutDate
            );
            System.out.println("Room successfully reserved:");
            System.out.println(reservation);
        } catch (IllegalStateException e){
            System.out.print(e.getLocalizedMessage());
        }
    }

    private static Date formatDate(String dateQuery) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        dateFormatter.setLenient(false);

        Date date = null;
        Date today = new Date();

        while (date == null || date.before(today)) {
            System.out.println(dateQuery);
            String input = scanner.nextLine();

            try {
                date = dateFormatter.parse(input);
                if (date.before(today)){
                    System.out.println("Invalid: Cannot book an expired date.");
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Try again.");
            }
        }
        return date;
    }
    private static void findAndReserveARoom() {
        String checkInQuery = "Enter your desired check-in date (MM/dd/yyyy): ";
        String checkOutQuery = "Enter your desired check-out date (MM/dd/yyyy): ";

        Date checkInDate = formatDate(checkInQuery);
        Date checkOutDate = formatDate(checkOutQuery);

        while (checkInDate.after(checkOutDate)){
            System.out.println("Invalid: Check-in date must occur before check-out date. Try Again");
            checkInDate = formatDate(checkInQuery);
            checkOutDate = formatDate(checkOutQuery);
        }

        Collection<IRoom> rooms = hotelResource.findARoom(checkInDate, checkOutDate);

        if (rooms.isEmpty()) {
            try {
                System.out.println("There are no available rooms for those dates.");
                rooms = hotelResource.displayRecommendedRooms(checkInDate, checkOutDate);
                String response;

                do {
                    System.out.println("Are you satisfied with the new dates? [Y/N]");
                    response = scanner.nextLine();
                    if (response.equalsIgnoreCase("N")) {
                        System.out.println("Ending operation...");
                        return;
                    }
                } while (!response.equalsIgnoreCase("Y"));

                checkInDate = hotelResource.addDays(checkInDate, 7);
                checkOutDate = hotelResource.addDays(checkOutDate, 7);
                reserveRoom(checkInDate, checkOutDate, rooms);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getLocalizedMessage());
                System.out.println("Ending operation...");
            }
        } else {
            System.out.println("Available Rooms:");
            rooms.forEach(System.out::println);
            reserveRoom(checkInDate, checkOutDate, rooms);
        }
    }
}
