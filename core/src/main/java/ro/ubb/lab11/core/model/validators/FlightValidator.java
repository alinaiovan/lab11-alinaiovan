package ro.ubb.lab11.core.model.validators;

import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Flight;
import ro.ubb.lab11.core.model.Pair;
import ro.ubb.lab11.core.exceptions.ValidatorException;

import java.util.stream.Stream;

@Component
public class FlightValidator implements Validator<Flight>{
    @Override
    public void validate(Flight flight) throws ValidatorException {
        Stream.of(new Pair<>(flight.getDeparture().after(flight.getArrival()), "Departure time must be before Arrival time"))
                .filter(Pair::getLeft)
                .forEach((b) -> {
                    throw new ValidatorException(b.getRight());
                });
    }
}
