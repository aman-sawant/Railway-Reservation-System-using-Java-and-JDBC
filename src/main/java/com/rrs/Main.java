package com.rrs;

import com.rrs.dao.DatabaseConnection;
import com.rrs.entities.Train;
import com.rrs.entities.User;
import com.rrs.services.AdminService;
import com.rrs.services.BookingService;
import com.rrs.services.TrainService;
import com.rrs.services.UserService;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Connection conn = DatabaseConnection.getConnection();
        UserService userService = new UserService(conn);
        TrainService trainService = new TrainService(conn);
        BookingService bookingService = new BookingService(conn);
        AdminService adminService = new AdminService(trainService, userService, bookingService);

        System.out.println("Welcome to the Railway Reservation System!");

        while (true) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. User Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");

            int mainChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainChoice) {
                case 1 -> {
                    System.out.print("Enter your username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();
                    User loggedIn = userService.loginUser(username, password);
                    if (loggedIn != null) {
                        System.out.println("User logged in successfully!");

                        boolean userMenuActive = true;
                        while (userMenuActive) {
                            System.out.println("\nUser Menu:");
                            System.out.println("1. View Available Trains");
                            System.out.println("2. Book a Ticket");
                            System.out.println("3. Cancel a Booking");
                            System.out.println("4. Logout");

                            int userChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            switch (userChoice) {
                                case 1 -> {
                                    List<Train> allTrains = trainService.getAllTrains();
                                    for (Train alltrain : allTrains) {
                                        System.out.println(alltrain);
                                    }
                                }
                                case 2 -> {
                                    System.out.print("Enter Train ID: ");
                                    int trainId = scanner.nextInt();
                                    System.out.print("Enter Number of Seats: ");
                                    int seats = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline

                                    if (bookingService.bookTicket(username, trainId, seats)) {
                                        System.out.println("Ticket booked successfully!");
                                    } else {
                                        System.out.println("Failed to book ticket. Please check availability.");
                                    }
                                }
                                case 3 -> {
                                    System.out.print("Enter Booking ID to cancel: ");
                                    int bookingId = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline

                                    if (bookingService.cancelBooking(bookingId)) {
                                        System.out.println("Booking cancelled successfully!");
                                    } else {
                                        System.out.println("Failed to cancel booking. Please check the Booking ID.");
                                    }
                                }
                                case 4 -> {
                                    System.out.println("Logging out...");
                                    userMenuActive = false;
                                }
                                default -> System.out.println("Invalid option. Please try again.");
                            }
                        }
                    } else {
                        System.out.println("Invalid login credentials. Please try again.");
                    }
                }
                case 2 -> {
                    System.out.print("Enter admin username: ");
                    String adminUsername = scanner.nextLine();
                    System.out.print("Enter admin password: ");
                    String adminPassword = scanner.nextLine();

                    if (adminService.loginAdmin(adminUsername, adminPassword)) {
                        System.out.println("Admin logged in successfully!");

                        boolean adminMenuActive = true;
                        while (adminMenuActive) {
                            System.out.println("\nAdmin Menu:");
                            System.out.println("1. Add Train");
                            System.out.println("2. Update Train");
                            System.out.println("3. Delete Train");
                            System.out.println("4. View All Trains");
                            System.out.println("5. Logout");

                            int adminChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            switch (adminChoice) {
                                case 1 -> {
                                    Train train = new Train();
                                    System.out.print("Enter Train Number: ");
                                    train.setTrainNumber(scanner.nextInt());
                                    System.out.print("Enter Train Name: ");
                                    train.setTrainName(scanner.nextLine());
                                    System.out.print("Enter Source: ");
                                    train.setSource(scanner.nextLine());
                                    System.out.print("Enter Destination: ");
                                    train.setDestination(scanner.nextLine());
                                    System.out.print("Enter Date of Departure (YYYY-MM-DD): ");
                                    train.setDateOfDeparture(LocalDate.parse(scanner.nextLine()));
                                    System.out.print("Enter Departure Time: ");
                                    train.setDepartureTime(scanner.nextLine());
                                    System.out.print("Enter Date of Arrival (YYYY-MM-DD): ");
                                    train.setDateOfArrival(LocalDate.parse(scanner.nextLine()));
                                    System.out.print("Enter Arrival Time: ");
                                    train.setArrivalTime(scanner.nextLine());
                                    System.out.print("Enter Total Seats: ");
                                    train.setTotalSeats(scanner.nextInt());
                                    System.out.print("Enter Fare: ");
                                    train.setFare(scanner.nextDouble());

                                    if (trainService.addTrain(train)) {
                                        System.out.println("Train added successfully!");
                                    } else {
                                        System.out.println("Failed to add train. Please try again.");
                                    }
                                }
                                case 2 -> {
                                    System.out.print("Enter Train ID to update: ");
                                    int trainId = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.print("Enter New Total Seats: ");
                                    int updatedSeats = scanner.nextInt();

                                    if (trainService.updateTrainFare(trainId, updatedSeats)) {
                                        System.out.println("Train updated successfully!");
                                    } else {
                                        System.out.println("Failed to update train. Please check the Train ID.");
                                    }
                                }
                                case 3 -> {
                                    System.out.print("Enter Train ID to delete: ");
                                    int trainId = scanner.nextInt();

                                    if (trainService.deleteTrain(trainId)) {
                                        System.out.println("Train deleted successfully!");
                                    } else {
                                        System.out.println("Failed to delete train. Please check the Train ID.");
                                    }
                                }
                                case 4 -> {
                                    List<Train> allTrains = trainService.getAllTrains();
                                    for (Train alltrain : allTrains) {
                                        System.out.println(alltrain);
                                    }
                                }
                                case 5 -> {
                                    System.out.println("Logging out...");
                                    adminMenuActive = false;
                                }
                                default -> System.out.println("Invalid option. Please try again.");
                            }
                        }
                    } else {
                        System.out.println("Invalid admin credentials. Please try again.");
                    }
                }
                case 3 -> {
                    System.out.println("Thank you for using the Railway Reservation System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
