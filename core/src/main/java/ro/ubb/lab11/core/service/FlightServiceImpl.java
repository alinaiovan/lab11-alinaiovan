package ro.ubb.lab11.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.lab11.core.repository.IFlightRepository;
import ro.ubb.lab11.core.repository.ITicketRepository;
import ro.ubb.lab11.core.exceptions.AirlineException;
import ro.ubb.lab11.core.model.Flight;
import ro.ubb.lab11.core.model.validators.FlightValidator;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FlightServiceImpl implements IFlightService{
    public static final Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private FlightValidator validator;

    @Autowired
    private ITicketRepository ticketRepository;

    @Override
    public List<Flight> getAllFlights() {
        logger.trace("getAllFlights - method entered");
        List<Flight> flights = flightRepository.findAll();
        logger.trace("getAllFlights: " + flights);
        return flights;
    }

    @Override
    public Flight addFlight(Timestamp departure, Timestamp arrival) {
        logger.trace("addFlight - method entered - departure: " + departure + " - arrival: " + arrival);
        Flight flight = new Flight(departure, arrival);
        validator.validate(flight);
        var result = flightRepository.save(flight);
        logger.trace("addFlight - method finished");
        return result;
    }

    @Override
    public void deleteFlight(Long id) {
        logger.trace("deleteFlight - method entered - id: " + id);
        ticketRepository.findAll().stream()
                .filter(ticket -> ticket.getFlightId().equals(id))
                .findAny()
                .ifPresent(ticket -> {
                    throw new AirlineException("The flight has a ticket!");
                });
        flightRepository.findById(id).ifPresentOrElse((flight) ->flightRepository.deleteById(id),() -> {
            throw new AirlineException("Flight does not exist");
        });

        logger.trace("deleteFlight - method finished");
    }

    @Override
    @Transactional
    public Flight updateFlight(Long id, Timestamp departure, Timestamp arrival) {
        logger.trace("updateFlight - method entered - id: " + id + " - departure: " + departure + " - arrival: " + arrival);
        Flight flight = new Flight(departure, arrival);
        flight.setId(id);
        validator.validate(flight);
        flightRepository.findById(id).ifPresentOrElse((flight1) -> {
            flight1.setDeparture(departure);
            flight1.setArrival(arrival);
        }, () -> {
            throw new AirlineException("Flight does not exist!");
        });
        logger.trace("updateFlight - method finished");
        return flight;
    }

    @Override
    public List<Flight> findAllByDepartureAsc() {
        logger.trace("findAllByDepartureAsc - method entered");
        var flights = flightRepository.findAllByOrderByDepartureAsc();
        logger.trace("findAllByDepartureAsc - result = {}", flights);
        return flights;
    }
}
