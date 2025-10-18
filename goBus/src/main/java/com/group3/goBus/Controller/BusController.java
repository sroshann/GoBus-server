package com.group3.goBus.Controller;

import com.group3.goBus.Configurations.JWT;
import com.group3.goBus.Model.Bus;
import com.group3.goBus.Model.RouteRequest;
import com.group3.goBus.Repository.BusRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bus")
public class BusController {

    @Autowired
    private BusRepository busRepository;

    @PostMapping("/add")
    public String addBus(@RequestBody Bus bus, HttpServletRequest request) {
        try {

            String token = null;
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("Token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            if (token == null) return "Unauthorized: No token found!";

            String username = JWT.validateToken(token);
            if (username == null) return "Unauthorized: Invalid or expired token!";

            if (busRepository.findByBusNumber(bus.getBusNumber()) != null) return "Bus number already exists!";
            busRepository.save(bus);
            return "Bus details added successfully!";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving bus details: " + e.getMessage();
        }
    }

    @PostMapping("/searchRoute")
    public Object getBusesByRoute(@RequestBody RouteRequest requestBody, HttpServletRequest request) {
        try {
            // Extract JWT token from cookie
            String token = null;
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("Token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
            if (token == null) return "Unauthorized: No token found!";

            String username = JWT.validateToken(token);
            if (username == null) return "Unauthorized: Invalid or expired token!";

            // Convert both input stops to lowercase for case-insensitive match
            String departureStop = requestBody.getDepartureStop().trim().toLowerCase();
            String arrivalStop = requestBody.getArrivalStop().trim().toLowerCase();

            List<Bus> buses = busRepository.findAll();

            List<Object> result = buses.stream().map(bus -> {
                        List<Bus.Route> routes = bus.getRoutes();
                        int depIndex = -1;
                        int arrIndex = -1;

                        // Find indexes using lowercase comparison
                        for (int i = 0; i < routes.size(); i++) {
                            String stop = routes.get(i).getStop().trim().toLowerCase();
                            if (stop.equals(departureStop)) depIndex = i;
                            if (stop.equals(arrivalStop)) arrIndex = i;
                        }

                        // Check only forward direction (departure before arrival)
                        if (depIndex != -1 && arrIndex != -1 && depIndex < arrIndex) {
                            List<Bus.Route> segment = routes.subList(depIndex, arrIndex + 1);

                            Map<String, Object> map = new HashMap<>();
                            map.put("busName", bus.getBusName());
                            map.put("busNumber", bus.getBusNumber());
                            map.put("status", bus.isStatus());
                            map.put("owner", bus.getOwner());
                            map.put("routeSegment", segment);

                            return map;
                        }

                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // If no forward route found, check for reversed route buses
            if (result.isEmpty()) {
                List<Object> reversed = buses.stream().map(bus -> {
                            List<Bus.Route> routes = bus.getRoutes();
                            int depIndex = -1;
                            int arrIndex = -1;

                            for (int i = 0; i < routes.size(); i++) {
                                String stop = routes.get(i).getStop().trim().toLowerCase();
                                if (stop.equals(arrivalStop)) depIndex = i;  // reverse direction
                                if (stop.equals(departureStop)) arrIndex = i;
                            }

                            if (depIndex != -1 && arrIndex != -1 && depIndex < arrIndex) {
                                List<Bus.Route> segment = routes.subList(depIndex, arrIndex + 1);

                                Map<String, Object> map = new HashMap<>();
                                map.put("busName", bus.getBusName());
                                map.put("busNumber", bus.getBusNumber());
                                map.put("status", bus.isStatus());
                                map.put("owner", bus.getOwner());
                                map.put("routeSegment", segment);

                                return map;
                            }

                            return null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                if (!reversed.isEmpty()) return reversed;
            }

            return result.isEmpty() ? "No matching route found" : result;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching buses: " + e.getMessage();
        }
    }

    @GetMapping("/allBuses")
    public Object getAllBuses(HttpServletRequest request) {
        try {

            String token = null;
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("Token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
            if (token == null) return "Unauthorized: No token found!";

            String username = JWT.validateToken(token);
            if (username == null) return "Unauthorized: Invalid or expired token!";

            List<Bus> buses = busRepository.findAll();

            return buses;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching buses: " + e.getMessage();
        }
    }

}
