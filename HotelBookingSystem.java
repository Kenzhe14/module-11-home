import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class UIComponent {
    private UserManagementService userService;
    private HotelService hotelService;
    private BookingService bookingService;
    private Guest loggedInGuest;

    public UIComponent(UserManagementService userService, HotelService hotelService, BookingService bookingService) {
        this.userService = userService;
        this.hotelService = hotelService;
        this.bookingService = bookingService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Hotel Booking System!");

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Search Hotels");
            System.out.println("4. Book Room");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    userService.registerUser (name);
                    System.out.println("User  registered.");
                    break;
                case 2:
                    System.out.print("Enter name to login: ");
                    String loginName = scanner.nextLine();
                    loggedInGuest = userService.login(loginName);
                    if (loggedInGuest != null) {
                        System.out.println("Welcome, " + loggedInGuest.getName());
                    } else {
                        System.out.println("User  not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter city to search hotels: ");
                    String city = scanner.nextLine();
                    List<Hotel> hotels = hotelService.searchByCity(city);
                    if (hotels.isEmpty()) {
                        System.out.println("No hotels found.");
                    } else {
                        for (Hotel hotel : hotels) {
                            System.out.println("Hotel: " + hotel.getName() + ", Price: " + hotel.getPrice());
                        }
                    }
                    break;
                case 4:
                    if (loggedInGuest == null) {
                        System.out.println("You must be logged in to book a room.");
                        break;
                    }
                    System.out.print("Enter hotel name to book: ");
                    String hotelName = scanner.nextLine();
                    Hotel selectedHotel = hotelService.getHotelByName(hotelName);
                    if (selectedHotel != null) {
                        System.out.print("Enter booking date (yyyy-mm-dd): ");
                        String dateStr = scanner.nextLine();
                        Date date = new Date();
                        bookingService.bookRoom(loggedInGuest, selectedHotel, date);
                    } else {
                        System.out.println("Hotel not found.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting the system.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

class HotelService {
    private List<Hotel> hotels;

    public HotelService() {
        hotels = new ArrayList<>();
        hotels.add(new Hotel("Hotel A", "New York", 1));
        hotels.add(new Hotel("Hotel B", "Los Angeles", 2));
        hotels.add(new Hotel("Hotel C", "New York", 3));
    }

    public List<Hotel> searchByCity(String city) {
        List<Hotel> results = new ArrayList<>();
        for (Hotel hotel : hotels) {
            if (hotel.getCity().equalsIgnoreCase(city)) {
                results.add(hotel);
            }
        }
        return results;
    }

    public Hotel getHotelByName(String name) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equalsIgnoreCase(name)) {
                return hotel;
            }
        }
        return null;
    }
}

class Hotel {
    private String name;
    private String city;
    private int roomType;
    private int price;

    public Hotel(String name, String city, int roomType) {
        this.name = name;
        this.city = city;
        this.roomType = roomType;
        this.price = calculatePrice(roomType);
    }

    private int calculatePrice(int roomType) {
        switch (roomType) {
            case 1: return 500;
            case 2: return 1000;
            case 3: return 1500;
            default: return 0;
        }
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public int getPrice() {
        return price;
    }
}

class BookingService {
    private List<Booking> bookings;

    public BookingService() {
        bookings = new ArrayList<>();
    }

    public boolean isRoomAvailable(Hotel hotel, Date date) {
        return true;
    }

    public void bookRoom(Guest guest, Hotel hotel, Date date) {
        if (isRoomAvailable(hotel, date)) {
            bookings.add(new Booking(guest, hotel, date));
            System.out.println("Room booked successfully at " + hotel.getName());
        } else {
            System.out.println("Room is not available.");
        }
    }
}

class Booking {
    private Guest guest;
    private Hotel hotel;
    private Date date;

    public Booking(Guest guest, Hotel hotel, Date date) {
        this.guest = guest;
        this.hotel = hotel;
        this.date = date;
    }
}

class Guest {
    private String name;

    public Guest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class UserManagementService {
    private List<Guest> guests;

    public UserManagementService() {
        guests = new ArrayList<>();
    }

    public void registerUser (String name) {
        guests.add(new Guest(name));
    }

    public Guest login(String name) {
        for (Guest guest : guests) {
            if (guest.getName().equals(name)) {
                return guest;
            }
        }
        return null;
    }
}
public class HotelBookingSystem {

    public static void main(String[] args) {
        UserManagementService userService = new UserManagementService();
        HotelService hotelService = new HotelService();
        BookingService bookingService = new BookingService();
        UIComponent ui = new UIComponent(userService, hotelService, bookingService);
        ui.start();
    }
}