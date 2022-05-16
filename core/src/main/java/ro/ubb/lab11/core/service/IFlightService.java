package ro.ubb.lab11.core.service;

import ro.ubb.lab11.core.model.Flight;

import java.sql.Timestamp;
import java.util.List;

public interface IFlightService {
    List<Flight> getAllFlights();
    Flight addFlight(Timestamp departure, Timestamp arrival);
    void deleteFlight(Long id);
    Flight updateFlight(Long id, Timestamp departure, Timestamp arrival);
    List<Flight> findAllByDepartureAsc();
}
