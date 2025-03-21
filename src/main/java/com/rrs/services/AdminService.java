package com.rrs.services;

import com.rrs.entities.Admin;
import com.rrs.entities.Booking;
import com.rrs.entities.Train;
import com.rrs.entities.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class AdminService {
    private Connection conn;
    private TrainService trainService;
    private UserService userService;
    private BookingService bookingService;

    public AdminService(Connection conn) {
        this.conn = conn;
    }

    public AdminService(TrainService trainService, UserService userService, BookingService bookingService) {
        this.trainService = trainService;
        this.userService = userService;
        this.bookingService = bookingService;
    }
    public void addTrain(Train train) {
        trainService.addTrain(train);
    }

    public void updateTrain(Train train) {
        trainService.updateTrain(train);
    }

    public void deleteTrain(int trainId) {
        trainService.deleteTrain(trainId);
    }

    public List<Train> viewAllTrains() {
        return trainService.getAllTrains();
    }

    // Delegate user management operations to UserService
    public List<User> viewAllUsers() {
        return userService.getAllUsers();
    }

    public void deleteUserById(int userId) {
        userService.deleteUserByUserID(userId);
    }

    public List<Booking> viewAllBookings() {
        return bookingService.getAllBookings();
    }

    public void cancelBooking(int bookingId) {
        bookingService.cancelBooking(bookingId);
    }

    public boolean addAdmin(Admin admin) {
        String query = "INSERT INTO admin (admin_name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, admin.getAdminName());
            preparedStatement.setString(2, admin.getEmail());
            preparedStatement.setString(3, admin.getPassword());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error adding admin: " + e.getMessage());
        }
        return false;
    }

    public boolean loginAdmin(String email, String password) {
        String query = "SELECT * FROM admin WHERE email = ? AND password = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            System.err.println("Error authenticating admin: " + e.getMessage());
        }
        return false;
    }

    public boolean updateAdmin(Admin admin) {
        String query = "UPDATE admin SET admin_name = ?, email = ?, password = ? WHERE admin_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, admin.getAdminName());
            preparedStatement.setString(2, admin.getEmail());
            preparedStatement.setString(3, admin.getPassword());
            preparedStatement.setInt(4, admin.getAdminId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating admin: " + e.getMessage());
        }
        return false;
    }

    // Delete admin by ID
    public boolean deleteAdmin(int adminId) {
        String query = "DELETE FROM admin WHERE admin_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, adminId);
            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting admin: " + e.getMessage());
        }
        return false;
    }

    // Fetch all admins
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM admin";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Admin admin = new Admin();
                admin.setAdminId(resultSet.getInt("admin_id"));
                admin.setAdminName(resultSet.getString("admin_name"));
                admin.setEmail(resultSet.getString("email"));
                admin.setPassword(resultSet.getString("password"));
                admins.add(admin);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching admins: " + e.getMessage());
        }
        return admins;
    }
}
