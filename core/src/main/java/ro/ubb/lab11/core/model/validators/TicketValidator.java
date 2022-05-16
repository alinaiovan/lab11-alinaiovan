package ro.ubb.lab11.core.model.validators;

import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Pair;
import ro.ubb.lab11.core.model.Ticket;
import ro.ubb.lab11.core.exceptions.ValidatorException;
import java.util.stream.Stream;

@Component
public class TicketValidator implements Validator<Ticket>{
    @Override
    public void validate(Ticket ticket) throws ValidatorException {
        Stream.of(new Pair<>(ticket.getCustomerId() <= 0, "CustomerId should be positive."),
                        new Pair<>(ticket.getFlightId() <= 0, "FlightId should be positive."),
                        new Pair<>(ticket.getPrice() <= 0, "Price should be positive."))
                .filter(Pair::getLeft)
                .forEach((b) -> {
                    throw new ValidatorException(b.getRight());
                });
    }
}
