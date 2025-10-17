package com.group3.goBus.Model;

public class RouteRequest {
    private String departureStop;
    private String arrivalStop;

    // Getters & setters
    public String getDepartureStop() { return departureStop; }
    public void setDepartureStop(String departureStop) { this.departureStop = departureStop; }

    public String getArrivalStop() { return arrivalStop; }
    public void setArrivalStop(String arrivalStop) { this.arrivalStop = arrivalStop; }
}