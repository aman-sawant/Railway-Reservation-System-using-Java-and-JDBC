package com.rrs.entities;

import java.time.LocalDate;

public class Train {
    int trainId;
    int trainNumber;
    String trainName;
    String source;
    String destination;
    String departureTime;
    String arrivalTime;
    LocalDate dateOfDeparture;
    LocalDate dateOfArrival;
    int totalSeats;
    double fare;

    public Train(int trainId, int trainNumber, String trainName, String source, String destination, LocalDate dateOfDeparture, String departureTime, LocalDate dateOfArrival, String arrivalTime, int totalSeats, double fare) {
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.totalSeats = totalSeats;
        this.fare = fare;
    }

    public Train(int trainNumber, String trainName, String source, String destination, LocalDate dateOfDeparture, String departureTime, LocalDate dateOfArrival, String arrivalTime, int totalSeats, double fare) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.totalSeats = totalSeats;
        this.fare = fare;
    }

    public Train() {}


    public int getTrainId() {
        return trainId;
    }
    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public int getTrainNumber() {
        return trainNumber;
    }
    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }
    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDate getDateOfDeparture() {
        return dateOfDeparture;
    }
    public void setDateOfDeparture(LocalDate dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public LocalDate getDateOfArrival() {
        return dateOfArrival;
    }
    public void setDateOfArrival(LocalDate dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public int getTotalSeats() {
        return totalSeats;
    }
    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public double getFare() {
        return fare;
    }
    public void setFare(double fare) {
        this.fare = fare;
    }
}