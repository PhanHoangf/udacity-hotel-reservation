package ui;

import api.AdminResource;
import api.HotelResource;
import model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
    private static boolean isAdminMenu = false;
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final AdminResource adminResource = AdminResource.getInstance();

    public static void setAdminMenu (boolean adminMenu) {
        isAdminMenu = adminMenu;
    }

    public static void main (String[] args) throws ParseException {
        initTestData();
        generateMainMenu();
        System.out.println( "//-------------------------------------//" );
    }

    private static void initTestData () throws ParseException {
        System.out.println( "OUT OUT" );
        Customer customer = new Customer( "Phan", "Hoang", "hoang@gmail.com" );
        Room room = new Room( "1", 1.5, RoomType.DOUBLE );
        Date checkInDate = new SimpleDateFormat( "MM/dd/yyyy" ).parse( "5/20/2023" );
        Date checkOutDate = new SimpleDateFormat( "MM/dd/yyyy" ).parse( "5/23/2023" );

        List<IRoom> rooms = new ArrayList<>();
        rooms.add( room );

        hotelResource.CreateACustomer( customer.getEmail(), customer.getFirstName(), customer.getLastName() );
        adminResource.addRoom( rooms );
        hotelResource.bookARoom( customer.getEmail(), room, checkInDate, checkOutDate );
    }

    public static void generateMainMenu () throws ParseException {
        int userInput;

        do {

            System.out.println( "//-------------------------------------//" );
            System.out.println( "                                         " );

            String option1 = "1. Find and reserve a room";
            String option2 = "2. See my reservations";
            String option3 = "3. Create an account";
            String option4 = "4. Admin";
            String option5 = "5. Exit";
            String[] mainMenuOptions = new String[]{ option1, option2, option3, option4, option5 };

            for ( String option : mainMenuOptions ) {
                System.out.println( option );
            }

            userInput = userInput();
            handleUserInputMainMenu( userInput );

        } while ( userInput != 5 );

    }

    private static void seeMyReservations () {
        Scanner userEmail = new Scanner( System.in );
        System.out.println( "Input your email: " );

        Collection<Reservation> myReservations = hotelResource.getCustomersReservations( userEmail.nextLine() );

        if ( myReservations.size() > 0 ) {
            for ( Reservation re : myReservations ) {
                System.out.println( re );
            }
        } else {
            System.out.println( "You dont have any reservations yet" );
        }
    }

    private static Customer createAccount () {
        Scanner input = new Scanner( System.in );

        System.out.println( "Input your email: " );
        String email = handleUserInputEmail();

        System.out.println( "Input your first name: " );
        String firstName = input.nextLine();

        System.out.println( "Input your last name: " );
        String lastName = input.nextLine();

        hotelResource.CreateACustomer( email, firstName, lastName );

        Customer newCustomer = hotelResource.getCustomer( email );
        System.out.println( "Create successful: " + newCustomer );
        return newCustomer;
    }


    private static void findAndReserveARoom () throws ParseException {
        Scanner input = new Scanner( System.in );

        System.out.println( "Date check in(MM/dd/yyyy): " );
        String strDateCheckin = input.nextLine();
        Date checkInDate = new SimpleDateFormat( "MM/dd/yyyy" ).parse( strDateCheckin );

        System.out.println( "Date check out(MM/dd/yyyy): " );
        String strDateCheckout = input.nextLine();
        Date checkOutDate = new SimpleDateFormat( "MM/dd/yyyy" ).parse( strDateCheckout );


        Collection<IRoom> rooms = hotelResource.findARoom( checkInDate, checkOutDate );
        Collection<IRoom> recommendedRoom = new ArrayList<>();

        if ( rooms.size() == 0 ) {
            System.out.println( "No room between your checkin and check out date" );
            Calendar calendar = Calendar.getInstance();
            calendar.setTime( checkInDate );

            int recommendedCiDate = calendar.get( Calendar.DATE ) + 7;
            String strRecommendCheckInDate = calendar.get( Calendar.MONTH ) + "/" + recommendedCiDate + "/" + calendar.get( Calendar.YEAR );
            Date recommendCheckInDate = new SimpleDateFormat( "MM/dd/yyyy" ).parse( strRecommendCheckInDate );

            calendar.setTime( checkOutDate );

            int recommendedCoDate = calendar.get( Calendar.DATE ) + 7;
            String strRecommendCheckOutDate = calendar.get( Calendar.MONTH ) + "/" + recommendedCoDate + "/" + calendar.get( Calendar.YEAR );
            Date recommendCheckOutDate = new SimpleDateFormat( "MM/dd/yyyy" ).parse( strRecommendCheckOutDate );

            recommendedRoom = hotelResource.findARoom( recommendCheckInDate, recommendCheckOutDate );
            String message;

            System.out.println( "Getting alternatives room....\n" );
            if ( recommendedRoom.size() == 0 ) {
                message = String.format( "There is no alternatives room for checkin date: %s, and checkout date: %s", recommendedCiDate, recommendedCoDate );
                System.out.println( message );
            } else {
                message = String.format( "Alternatives room for checkin date: %s, and checkout date: %s", recommendedCiDate, recommendedCoDate );
                System.out.println( message );
                bookRoom( recommendCheckInDate, recommendCheckOutDate, recommendedRoom );
            }
        } else {
            bookRoom( checkInDate, checkOutDate, rooms );
        }
    }

    private static void bookRoom (Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        Scanner input = new Scanner( System.in );
        IRoom room;
        String userChooseRoom;

        for ( IRoom available : rooms ) {
            System.out.println( available );
        }

        do {
            System.out.println( "Choose room no: " );
            userChooseRoom = input.nextLine();

            room = hotelResource.getRoom( userChooseRoom );
            if ( room == null ) {
                System.out.println( "Room does not exist. Select again!" );
            }
        } while ( room == null );

        System.out.println( "Email: " );
        String userEmail = handleUserInputEmail();

        Customer customer = hotelResource.getCustomer( userEmail );

        if ( customer == null ) {
            System.out.println( "You need to create account first!" );
            customer = createAccount();
        }

        Reservation reservation = hotelResource.bookARoom( customer.getEmail(), room, checkInDate, checkOutDate );

        System.out.println( "Reserve room successful: " + reservation );
    }

    private static String handleUserInputEmail () {
        Scanner input = new Scanner( System.in );
        String userEmail;
        do {
            userEmail = input.nextLine();
            if ( !Customer.validate( userEmail ) ) {
                System.out.println( "Invalid email, please try again (abc@mail.com):" );
            }
        } while ( !Customer.validate( userEmail ) );

        return userEmail;
    }

    private static Integer userInput () {
        Scanner userChoice = new Scanner( System.in );
        System.out.println( "Choose option:" );

        return Integer.parseInt( userChoice.nextLine() );
    }

    private static void handleUserInputMainMenu (Integer input) throws ParseException {
        if ( !isAdminMenu ) {
            switch ( input ) {
                case 1:
                    findAndReserveARoom();
                    break;
                case 2:
                    seeMyReservations();
                    break;
                case 3:
                    createAccount();
                    break;
                case 4:
                    setAdminMenu( true );
                    generateAdminMenu();
                    break;
                case 5:
                    break;
                default:
                    System.out.println( "Please choose options that are showed in the menu" );
                    break;
            }
        } else {
            switch ( input ) {
                case 1 -> getAllCustomers();
                case 2 -> getAllRooms();
                case 3 -> getAllReservation();
                case 4 -> addRoom();
                case 5 -> {
                    setAdminMenu( false );
                    generateMainMenu();
                }
                default -> System.out.println( "Please choose options that are showed in the menu" );
            }
        }
    }

    private static void generateAdminMenu () throws ParseException {
        int userInput;

        do {

            System.out.println( "//-------------------------------------//" );
            System.out.println( "                                         " );

            String option1 = "1. See all Customers";
            String option2 = "2. See all Rooms";
            String option3 = "3. See all Reservations";
            String option4 = "4. Add a room";
            String option5 = "5. Back to Main Menu";
            String[] mainMenuOptions = new String[]{ option1, option2, option3, option4, option5 };

            for ( String option : mainMenuOptions ) {
                System.out.println( option );
            }

            userInput = userInput();
            handleUserInputMainMenu( userInput );

        } while ( userInput != 5 );
    }

    //    ADMIN METHODS
    private static void getAllCustomers () {
        Collection<Customer> customers = adminResource.getAllCustomers();
        for ( Customer customer : customers ) {
            System.out.println( customer );
        }
    }

    private static void getAllRooms () {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        for ( IRoom room : rooms ) {
            System.out.println( room );
        }
    }

    private static void addRoom () {
        Scanner input = new Scanner( System.in );
        String roomNumber;
        String roomPrice;
        String roomType;

        System.out.println( "Input room number: " );
        roomNumber = input.nextLine();

        System.out.println( "Input room price: " );
        roomPrice = input.nextLine();

        System.out.println( "Input room type: " );
        roomType = input.nextLine();

        Room newRoom = new Room( roomNumber, Double.parseDouble( roomPrice ), RoomType.DOUBLE );

        List<IRoom> roomList = new ArrayList<>();
        roomList.add( newRoom );

        adminResource.addRoom( roomList );
    }

    private static void getAllReservation () {
        adminResource.displayAllReservations();
    }
}
