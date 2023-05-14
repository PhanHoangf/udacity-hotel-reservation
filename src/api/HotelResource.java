package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

public class HotelResource {
    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) {
        return null;
    }

    public void CreateACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }
}
