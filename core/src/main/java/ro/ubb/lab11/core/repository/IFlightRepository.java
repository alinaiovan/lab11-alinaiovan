package ro.ubb.lab11.core.repository;

import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Flight;

import java.util.List;

public interface IFlightRepository extends IRepository<Flight, Long>{
    List<Flight> findAllByOrderByDepartureAsc();
}
