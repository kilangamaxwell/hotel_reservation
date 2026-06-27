package api;

import model.Customer;
import model.FreeRoom;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public final Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public final void addRoom(List<IRoom> rooms){
        for(IRoom room : rooms){
            reservationService.addRoom(room);
        }
    }

    public final Collection<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }

    public final Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public final void printAllReservations(){
        reservationService.printAllReservations();
    }
}
