package ui;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private boolean isAdminMenu = false;
    private static String option;
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final AdminResource adminResource = AdminResource.getInstance();

    public void setAdminMenu(boolean adminMenu) {
        isAdminMenu = adminMenu;
    }

    public static void main(String[] args) throws ParseException {
        generateMainMenu();
        System.out.println("//-------------------------------------//");
    }

    public static void generateMainMenu() throws ParseException {
        System.out.println("//-------------------------------------//");
        System.out.println("                                         ");

        String option1 = "1. Find and reserve a room";
        String option2 = "2. See my reservations";
        String option3 = "3. Create an account";
        String option4 = "4. Admin";
        String option5 = "5. Exit";
        String[] mainMenuOptions = new String[]{option1, option2, option3, option4, option5};

        for (String option : mainMenuOptions) {
            System.out.println(option);
        }
        handleUserInputMainMenu(userInput());
    }

    private static void seeMyReservations() {
        Scanner userEmail = new Scanner(System.in);
        System.out.println("Input your email: ");

        Collection<Reservation> myReservations = hotelResource.getCustomersReservations(userEmail.nextLine());

        if (myReservations.size() > 0) {
            for (Reservation re : myReservations) {
                System.out.println(re);
            }
        } else {
            System.out.println("You dont have any reservations yet");
        }
    }

    private static void createAccount() {
        Scanner input = new Scanner(System.in);

        System.out.println("Input your email: ");
        String email = input.nextLine();

        System.out.println("Input your first name: ");
        String firstName = input.nextLine();

        System.out.println("Input your last name: ");
        String lastName = input.nextLine();

        hotelResource.CreateACustomer(email, firstName, lastName);

        Customer newCustomer = hotelResource.getCustomer(email);
        System.out.println("Create successful: " + newCustomer);
    }

    private static void findAndReserveARoom() throws ParseException {
        Scanner input = new Scanner(System.in);

        System.out.println("Date check in(dd/MM/yyyy): ");
        String strDateCheckin = input.nextLine();
        Date checkInDate = new SimpleDateFormat("dd/MM/yyyy").parse(strDateCheckin);

        System.out.println("Date check out(dd/MM/yyyy): ");
        String strDateCheckout = input.nextLine();
        Date checkOutDate = new SimpleDateFormat("dd/MM/yyyy").parse(strDateCheckout);

        Collection<IRoom> rooms = hotelResource.findARoom(checkInDate, checkOutDate);

        if (rooms.size() > 0) {
            for (IRoom room : rooms) {
                System.out.println(room);
            }

            System.out.println("Choose room no: ");
            String userChooseRoom = input.nextLine();

            System.out.println("Email: ");
            String userEmail = input.nextLine();


            IRoom room = hotelResource.getRoom(userChooseRoom);
            Reservation reservation = hotelResource.bookARoom(userEmail, room, checkInDate, checkOutDate);

            System.out.println("Reserve room successful: " + reservation);

        } else {
            System.out.println("Dont have any room yet");
        }

//        TODO: book room
    }

    private static void generateAdminMenu() {
        System.out.println("//-------------------------------------//");
        System.out.println("                                         ");

        String option1 = "1. See all Customers";
        String option2 = "2. See all Rooms";
        String option3 = "3. See all Reservations";
        String option4 = "4. Add a room";
        String option5 = "5. Back to Main Menu";
        String[] mainMenuOptions = new String[]{option1, option2, option3, option4, option5};

        for (String option : mainMenuOptions) {
            System.out.println(option);
        }
    }

    private static Integer userInput() {
        Scanner userChoice = new Scanner(System.in);
        System.out.println("Choose option:");

        return Integer.parseInt(userChoice.nextLine());
    }

    private static void handleUserInputMainMenu(Integer input) throws ParseException {
        switch (input) {
            case 1:
                findAndReserveARoom();
                generateMainMenu();
                break;
            case 2:
                seeMyReservations();
                generateMainMenu();
                break;
            case 3:
                createAccount();
                generateMainMenu();
                break;
            case 4:
                generateAdminMenu();
                break;
            case 5:
                break;
            default:
                System.out.println("Please choose options that are showed in the menu");
                generateMainMenu();
        }
    }
}
