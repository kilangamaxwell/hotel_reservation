package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static final ReservationService INSTANCE = new ReservationService();
    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Collection<Reservation> uniqueReservations = new HashSet<>();

    private ReservationService(){}
    public static ReservationService getInstance(){
        return INSTANCE;
    }

    public final void addRoom(IRoom room)
    {
        if (rooms.containsKey(room.getRoomNumber())){
            throw new IllegalArgumentException("Error: This room already exists.");
        }
        rooms.put(room.getRoomNumber(), room);
    }

    public final IRoom getARoom(String roomId) {
        try {
            if (!rooms.containsKey(roomId)) {
                throw new NoSuchElementException("Room does not exist: " + roomId);
            }
        } catch (NoSuchElementException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return rooms.get(roomId);
    }

    public final Reservation reserveRoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {

        for (Reservation res : uniqueReservations) {
            boolean sameRoom = res.getRoom().equals(room);

            boolean datesOverlap =
                    checkInDate.before(res.getCheckOutDate()) &&
                            checkOutDate.after(res.getCheckInDate());

            if (sameRoom && datesOverlap) {
                throw new IllegalStateException("Invalid request: Room has already been reserved for the selected dates.");
            }
        }

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        uniqueReservations.add(reservation);
        return reservation;
    }

    public final Collection<IRoom> displayRecommendedRooms(Date checkIn, Date checkOut){
        checkIn = addDays(checkIn, 7);
        checkOut = addDays(checkOut, 7);
        Collection<IRoom> recommendedRooms = new HashSet<>();
        System.out.println("Recommended rooms: ");
        for (Reservation res : uniqueReservations) {
            recommendedRooms.addAll(findRooms(checkIn, checkOut));
            if (!recommendedRooms.isEmpty()){
                System.out.println("Room: " + res.getRoom().getRoomNumber()
                        + " Available from: " + checkIn + " to: " + checkOut);
            }
        }
        if (recommendedRooms.isEmpty()){
            throw new IllegalArgumentException("Error: No rooms available.");
        }
        return recommendedRooms;
    }

    public final Date addDays(Date date, int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    private boolean isAvailable(Date checkIn, Date checkOut, Date reserveCheckIn, Date reserveCheckOut){
        return checkIn.before(reserveCheckOut) && checkOut.after(reserveCheckIn);
    }

    public final Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new HashSet<>(rooms.values());
        for (Reservation reservation : uniqueReservations) {
            if (isAvailable(checkInDate, checkOutDate, reservation.getCheckInDate(),
                    reservation.getCheckOutDate())){
                availableRooms.remove(reservation.getRoom());
            }
        }
        return availableRooms;
    }

    public final Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> customerReservations = new HashSet<>();
        for (Reservation reservation : uniqueReservations) {
            if (customer.equals(reservation.getCustomer())){
                customerReservations.add(reservation);
            }
        }
        if (customerReservations.isEmpty()) {
            System.out.println("No reservations found for customer.");
        }
        return customerReservations;
    }

    public final Collection<IRoom> getAllRooms(){
        if (rooms.isEmpty()) {
            System.out.println("No rooms present.");
        }
        return rooms.values();
    }

    public final void printAllReservations() {
        for (Reservation reservation : uniqueReservations) {
            System.out.println(reservation);
        }
    }

}
