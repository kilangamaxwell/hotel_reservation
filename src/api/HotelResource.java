package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public final Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public final void createCustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public final IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    public final Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate,Date checkOutDate){
        return reservationService.reserveRoom(this.getCustomer(customerEmail), room,
                                                checkInDate, checkOutDate);
    }

    public final Collection<Reservation> getCustomersReservations(String customerEmail){
        return reservationService.getCustomersReservation(this.getCustomer(customerEmail));
    }

    public final Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return reservationService.findRooms(checkIn, checkOut);
    }

    public final Collection<IRoom> displayRecommendedRooms(Date checkIn, Date checkOut) {
        return reservationService.displayRecommendedRooms(checkIn, checkOut);
    }

    public final boolean isValidEmail(String email){
        return customerService.verifyEmail(email);
    }

    public final Date addDays(Date date, int days) {
        return reservationService.addDays(date, days);
    }
}
