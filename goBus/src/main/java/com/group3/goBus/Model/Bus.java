package com.group3.goBus.Model;

import java.util.List;

public class Bus {

    private String busName;
    private String busNumber;
    private boolean status;

    private Owner owner;
    private List<Route> routes;

    public static class Owner {
        private String name;
        private String address;
        private String mobileNumber;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getMobileNumber() { return mobileNumber; }
        public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    }

    public static class Route {

        private String stop;
        private String arrivalTime;
        private String departureTime;

        public String getStop() { return stop; }
        public void setStop(String stop) { this.stop = stop; }

        public String getArrivalTime() { return arrivalTime; }
        public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

        public String getDepartureTime() { return departureTime; }
        public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    }

    // Getters & setters for Bus
    public String getBusName() { return busName; }
    public void setBusName(String busName) { this.busName = busName; }

    public String getBusNumber() { return busNumber; }
    public void setBusNumber(String busNumber) { this.busNumber = busNumber; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public Owner getOwner() { return owner; }
    public void setOwner(Owner owner) { this.owner = owner; }

    public List<Route> getRoutes() { return routes; }
    public void setRoutes(List<Route> routes) { this.routes = routes; }

}
