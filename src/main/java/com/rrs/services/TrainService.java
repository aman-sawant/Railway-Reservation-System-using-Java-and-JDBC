package com.rrs.services;

import com.rrs.entities.Train;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainService {
    private Connection conn;

    public TrainService(Connection conn) {
        this.conn = conn;
    }

    public TrainService() {}
    public boolean addTrain(Train train) {
        String query = "INSERT INTO trains (train_number,train_name, source, destination, date_of_departure, departure_time, date_of_arrival, arrival_time, total_seats, fare) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1,train.getTrainNumber());
            stmt.setString(2,train.getTrainName());
            stmt.setString(3, train.getSource());
            stmt.setString(5, train.getDestination());
            stmt.setDate(6, Date.valueOf(train.getDateOfDeparture()));
            stmt.setString(7, train.getDepartureTime());
            stmt.setDate(8, Date.valueOf(train.getDateOfArrival()));
            stmt.setString(9, train.getArrivalTime());
            stmt.setInt(9, train.getTotalSeats());
            stmt.setDouble(10, train.getFare());
            int rowsInserted = stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding train: " + e.getMessage());
        }
        return false;
    }

    public boolean updateTrain(Train train)  {
        String query = "UPDATE trains SET train_number = ?, train_name = ?, source = ?, destination = ?, date_of_departure = ?, departure_time = ?, date_of_arrival = ?, arrival_time = ?, total_seats = ?, fare = ? WHERE train_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, train.getTrainNumber());
            stmt.setString(2, train.getTrainName());
            stmt.setString(3, train.getSource());
            stmt.setString(4, train.getDestination());
            stmt.setDate(5, Date.valueOf(train.getDateOfDeparture()));
            stmt.setString(6, train.getDepartureTime());
            stmt.setDate(7, Date.valueOf(train.getDateOfArrival()));
            stmt.setString(8, train.getArrivalTime());
            stmt.setInt(9, train.getTotalSeats());
            stmt.setDouble(10, train.getFare());
            stmt.setInt(11, train.getTrainId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated>0;
        } catch (SQLException e) {
            System.err.println("Error updating booking: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteTrain(int trainId) {
        String query = "DELETE FROM trains WHERE train_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, trainId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted>0;
        } catch (SQLException e) {
            System.err.println("Error deleting train: " + e.getMessage());
        }
        return false;
    }

    public List<Train> searchTrains(String source, String destination, LocalDate dateOfDeparture) {
        String query = "SELECT * FROM trains WHERE source = ? AND destination = ? AND date_of_departure = ?";
        List<Train> trains = new ArrayList<>();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, source);
            preparedStatement.setString(2, destination);
            preparedStatement.setDate(3, Date.valueOf(dateOfDeparture));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Train train = mapResultSetToTrain(resultSet);
                    trains.add(train);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching trains: " + e.getMessage());
        }
        return trains;
    }

    public List<Train> getAllTrains() {
        String query = "SELECT * FROM trains";
        List<Train> trains = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Train train = mapResultSetToTrain(resultSet);
                trains.add(train);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching trains: " + e.getMessage());
        }

        return trains;
    }
    public boolean updateTrainFare(int trainId, double newFare) {
        String query = "UPDATE trains SET fare = ? WHERE train_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setDouble(1, newFare);
            preparedStatement.setInt(2, trainId);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating fare: " + e.getMessage());
        }
        return false;
    }

    private Train mapResultSetToTrain(ResultSet resultSet) throws SQLException {
        Train train = new Train();
        train.setTrainId(resultSet.getInt("train_id"));
        train.setTrainNumber(resultSet.getInt("train_number"));
        train.setTrainName(resultSet.getString("train_name"));
        train.setSource(resultSet.getString("source"));
        train.setDestination(resultSet.getString("destination"));
        train.setDateOfDeparture(resultSet.getDate("date_of_departure").toLocalDate());
        train.setDepartureTime(resultSet.getString("departure_time"));
        train.setDateOfArrival(resultSet.getDate("date_of_arrival").toLocalDate());
        train.setArrivalTime(resultSet.getString("arrival_time"));
        train.setTotalSeats(resultSet.getInt("total_seats"));
        train.setFare(resultSet.getDouble("fare"));
        return train;
    }
}
