package com.example.anthony.flightbookerfinal;

/*
Group 28
Anthony Kwan
Kushal Parmar
Bill Gray Quitalig
Kartihan Srisaravanapavan
 */

public class Itinerary {
    private String flightNumner;
    private String departure;
    private String arrival;
    private String airline;
    private String origin;
    private String destination;

    private double cost;
    private double duration;





    public Itinerary(){}

    public Itinerary(String flightNumner, String departure, String arrival,
                     String airline, String origin, String destination, double cost, double duration) {
        this.flightNumner = flightNumner;
        this.departure = departure;
        this.arrival = arrival;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;

        this.cost = cost;
        this.duration= duration;
    }

    public String getFlightNumner() {
        return flightNumner;
    }

    public void setFlightNumner(String flightNumner) {
        this.flightNumner = flightNumner;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }






}
