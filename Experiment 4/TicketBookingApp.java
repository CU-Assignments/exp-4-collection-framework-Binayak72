import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class TicketBookingSystem {
    private final List<Integer> seats = new ArrayList<>();

    public TicketBookingSystem(int totalSeats) {
        for (int i = 1; i <= totalSeats; i++) {
            seats.add(i);
        }
    }

    public synchronized boolean bookSeat(String name, int seatNumber) {
        if (seats.contains(seatNumber)) {
            seats.remove(Integer.valueOf(seatNumber));
            System.out.println(name + " booked seat number " + seatNumber);
            return true;
        } else {
            System.out.println(name + " tried to book seat number " + seatNumber + ", but it's already booked or invalid.");
            return false;
        }
    }

    public synchronized void displayAvailableSeats() {
        System.out.println("Available seats: " + seats);
    }
}

class BookingThread extends Thread {
    private final TicketBookingSystem bookingSystem;
    private final String customerName;
    private final Scanner scanner;

    public BookingThread(TicketBookingSystem bookingSystem, String customerName, Scanner scanner) {
        this.bookingSystem = bookingSystem;
        this.customerName = customerName;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        synchronized (scanner) {
            bookingSystem.displayAvailableSeats();
            System.out.print(customerName + ", enter the seat number you want to book: ");
            int seatNumber = scanner.nextInt();
            bookingSystem.bookSeat(customerName, seatNumber);
        }
    }
}

public class TicketBookingApp {
    public static void main(String[] args) {
        int totalSeats = 10;
        TicketBookingSystem bookingSystem = new TicketBookingSystem(totalSeats);
        Scanner scanner = new Scanner(System.in);

        // Creating threads for VIP and regular customers
        BookingThread vip1 = new BookingThread(bookingSystem, "VIP Customer 1", scanner);
        BookingThread vip2 = new BookingThread(bookingSystem, "VIP Customer 2", scanner);
        BookingThread regular1 = new BookingThread(bookingSystem, "Regular Customer 1", scanner);
        BookingThread regular2 = new BookingThread(bookingSystem, "Regular Customer 2", scanner);

        // Setting priorities: Higher for VIPs
        vip1.setPriority(Thread.MAX_PRIORITY);
        vip2.setPriority(Thread.MAX_PRIORITY);
        regular1.setPriority(Thread.MIN_PRIORITY);
        regular2.setPriority(Thread.MIN_PRIORITY);

        // Starting threads
        vip1.start();
        vip2.start();
        regular1.start();
        regular2.start();

        try {
            vip1.join();
            vip2.join();
            regular1.join();
            regular2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scanner.close();
        System.out.println("All bookings are complete.");
    }
}
