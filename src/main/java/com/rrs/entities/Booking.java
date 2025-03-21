package com.rrs.entities;

import java.time.LocalDate;

public class Booking {
    private int bookingId;
    private int userId;
    private int trainId;
    private LocalDate bookingDate;
    private LocalDate travelDate;
    private int numSeats;
    private double totalFare;
    public Booking(int bookingId, int userId, int trainId, LocalDate bookingDate, int numSeats, double totalFare) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.trainId = trainId;
        this.bookingDate = bookingDate;
        this.numSeats = numSeats;
        this.totalFare = totalFare;
    }

    public Booking(int userId, int trainId, LocalDate bookingDate, int numSeats, double totalFare) {
        this.userId = userId;
        this.trainId = trainId;
        this.bookingDate = bookingDate;
        this.numSeats = numSeats;
        this.totalFare = totalFare;
    }

    public Booking() {}

    public int getBookingId() {
        return bookingId;
    }
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTrainId() {
        return trainId;
    }
    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }
    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(double totalFare) {
        this.totalFare = totalFare;
    }
}
