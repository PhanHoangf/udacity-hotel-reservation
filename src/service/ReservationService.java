package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    Collection<Reservation> reservations = new ArrayList<Reservation>();
    HashMap<String, IRoom> rooms = new HashMap<String, IRoom>();
    private static ReservationService instance = null;

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public Collection<IRoom> getRooms() {
        return rooms.values();
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        List<Reservation> bookedRoom = reservations.stream().filter(reservation1 -> Objects.equals(reservation.getRoom().getRoomNumber(), room.getRoomNumber())).toList();

        if (bookedRoom.size() > 0) {
            System.out.println("Room already");
        }

        reservations.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new ArrayList<IRoom>();
        List<String> unavailableRoomsNumber = new ArrayList<String>();
        for (Reservation re : reservations) {
            if (re.getCheckInDate().before(checkInDate) && re.getCheckOutDate().after(checkOutDate)
                    || re.getCheckInDate().after(checkInDate) && re.getCheckOutDate().after(checkOutDate)
                    || re.getCheckInDate().before(checkInDate) && re.getCheckOutDate().before(checkOutDate)
                    || re.getCheckInDate().after(checkInDate) && re.getCheckOutDate().before(checkOutDate)
            ) {
                String roomNumber = re.getRoom().getRoomNumber();
                unavailableRoomsNumber.add(roomNumber);
            }
        }

        for (IRoom room : rooms.values()) {
            for (String roomNumber : unavailableRoomsNumber) {
            }
        }
        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.stream().filter(reservation -> reservation.getCustomer().getEmail().equals(customer.getEmail())).toList();
    }

    public void printAllReservation() {
        for (Reservation re : reservations) {
            System.out.println(re);
        }
    }
}
