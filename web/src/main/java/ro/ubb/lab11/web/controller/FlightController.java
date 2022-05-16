package ro.ubb.lab11.web.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.lab11.core.model.Flight;
import ro.ubb.lab11.core.service.IFlightService;
import ro.ubb.lab11.web.converter.FlightConverter;
import ro.ubb.lab11.web.dto.FlightDTO;
import ro.ubb.lab11.web.dto.FlightsDTO;

import java.util.List;

@RestController
public class FlightController {
    public static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private IFlightService flightService;

    @Autowired
    FlightConverter flightConverter;

    @RequestMapping(value = "/flights")
    FlightsDTO getFlightsFromRepository() {
        logger.trace("getAllFlights - method entered");
        List<Flight> flights = flightService.getAllFlights();
        FlightsDTO flightsDTO = new FlightsDTO(flightConverter.convertModelsToDTOs(flights));
        logger.trace("getAllFlights: " + flights);
        return flightsDTO;
    }

    @RequestMapping(value = "/flights", method = RequestMethod.POST)
    FlightDTO addFlight(@RequestBody FlightDTO flightDTO) {
        logger.trace("addFlight - method entered - FlightDTO: " + flightDTO);
        var flight = flightConverter.convertDtoToModel(flightDTO);
        var result = flightService.addFlight(flight.getDeparture(), flight.getArrival());
        var resultModel = flightConverter.convertModelToDto(result);
        logger.trace("addFlight - Flight added");
        return resultModel;
    }

    @RequestMapping(value = "/flights/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/flights/{id}", method = RequestMethod.PUT)
    FlightDTO updateFlight(@PathVariable Long id, @RequestBody FlightDTO flightDTO) {
        logger.trace("updateFlight - method entered - FlightDTO: " + flightDTO);
        var flight = flightConverter.convertDtoToModel(flightDTO);
        var result = flightService.updateFlight(id, flight.getDeparture(), flight.getArrival());
        var resultModel = flightConverter.convertModelToDto(result);
        logger.trace("updateFlight - Flight updated");
        return resultModel;
    }

    @RequestMapping(value = "/flights/sort/departure")
    FlightsDTO getFlightsSortedByDeparture() {
        logger.trace("getFlightsSortedByDeparture - method entered");
        List<Flight> flights = flightService.findAllByDepartureAsc();
        FlightsDTO flightsDTO = new FlightsDTO(flightConverter.convertModelsToDTOs(flights));
        logger.trace("getFlightsSortedByDeparture: " + flights);
        return flightsDTO;
    }

}
