package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ReservationService {
    Collection<Reservation> reservations = new ArrayList<Reservation>();

    private static final ReservationService instance = new ReservationService();

    private ReservationService() {
    }

    public ReservationService getInstance() {
        return instance;
    }

    public void addRoom(IRoom room) {

    }

    public IRoom getARoom(String roomId) {
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        return null;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        return null;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return null;
    }

    public void printAllReservation() {

    }
}
