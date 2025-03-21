package com.rrs.services;

import com.rrs.entities.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private Connection conn;
    public BookingService(Connection conn) {
        this.conn = conn;
    }
    public boolean addBooking(Booking booking) {
        String query = "INSERT INTO bookings (user_id, train_id, booking_date, travel_date, total_fare, seats_booked) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getTrainId());
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.setDate(4, Date.valueOf(booking.getTravelDate()));
            stmt.setDouble(5, booking.getTotalFare());
            stmt.setInt(6, booking.getNumSeats());
            int rowInserted = stmt.executeUpdate();
            return rowInserted>0;
        } catch (SQLException e) {
            System.err.println("Error adding trains: "+e.getMessage());
        }
        return false;
    }

    public BookingService() {}

    public List<Booking> getBookingsByUserId(int userId) {
        String query = "SELECT * FROM bookings WHERE user_id = ?";
        List<Booking> bookings = new ArrayList<>();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Booking booking = mapResultSetToBooking(resultSet);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings by user ID: " + e.getMessage());
        }

        return bookings;
    }

    public List<Booking> getAllBookings() {
        String query = "SELECT * FROM bookings";
        List<Booking> bookings = new ArrayList<>();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Booking booking = mapResultSetToBooking(resultSet);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings: " + e.getMessage());
        }

        return bookings;
    }

    public List<Booking> getBookingsByTrainId(int trainId) {
        String query = "SELECT * FROM bookings WHERE train_id = ?";
        List<Booking> bookings = new ArrayList<>();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, trainId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Booking booking = mapResultSetToBooking(resultSet);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings by train ID: " + e.getMessage());
        }

        return bookings;
    }

    public boolean cancelBooking(int bookingId) {
        String query = "DELETE FROM bookings WHERE booking_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, bookingId);
            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted>0;
        } catch (SQLException e) {
            System.err.println("Error deleting booking: " + e.getMessage());
        }
        return false;
    }

    private Booking mapResultSetToBooking(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(resultSet.getInt("booking_id"));
        booking.setUserId(resultSet.getInt("user_id"));
        booking.setTrainId(resultSet.getInt("train_id"));
        booking.setBookingDate(resultSet.getDate("booking_date").toLocalDate());
        booking.setTravelDate(resultSet.getDate("travel_date").toLocalDate());
        booking.setTotalFare(resultSet.getDouble("total_fare"));
        booking.setNumSeats(resultSet.getInt("seats_booked"));
        return booking;
    }

    public boolean bookTicket(String username, int trainId, int seatsToBook) {
        PreparedStatement checkTrainStatement = null;
        PreparedStatement bookTicketStatement = null;
        PreparedStatement updateSeatsStatement = null;
        ResultSet trainResultSet = null;

        try {
            // Check train availability
            String checkTrainQuery = "SELECT totalSeats, fare FROM trains WHERE trainId = ?";
            checkTrainStatement = conn.prepareStatement(checkTrainQuery);
            checkTrainStatement.setInt(1, trainId);
            trainResultSet = checkTrainStatement.executeQuery();

            if (trainResultSet.next()) {
                int availableSeats = trainResultSet.getInt("totalSeats");
                double fare = trainResultSet.getDouble("fare");

                if (availableSeats >= seatsToBook) {
                    double totalFare = seatsToBook * fare;
                    String bookTicketQuery = "INSERT INTO bookings (username, trainId, seatsBooked, totalFare) VALUES (?, ?, ?, ?)";
                    bookTicketStatement = conn.prepareStatement(bookTicketQuery);
                    bookTicketStatement.setString(1, username);
                    bookTicketStatement.setInt(2, trainId);
                    bookTicketStatement.setInt(3, seatsToBook);
                    bookTicketStatement.setDouble(4, totalFare);
                    bookTicketStatement.executeUpdate();


                    String updateSeatsQuery = "UPDATE trains SET totalSeats = totalSeats - ? WHERE trainId = ?";
                    updateSeatsStatement = conn.prepareStatement(updateSeatsQuery);
                    updateSeatsStatement.setInt(1, seatsToBook);
                    updateSeatsStatement.setInt(2, trainId);
                    updateSeatsStatement.executeUpdate();

                    System.out.println("Ticket booked successfully! Total Fare: " + totalFare);
                    return true;
                } else {
                    System.out.println("Insufficient seats available.");
                }
            } else {
                System.out.println("Train not found.");
            }

        } catch (SQLException e) {
            System.err.println("Error while booking ticket.");
        }
        return false;
    }
}
