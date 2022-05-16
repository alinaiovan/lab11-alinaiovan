package ro.ubb.lab11.core.model.validators;

import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Luggage;
import ro.ubb.lab11.core.model.Pair;
import ro.ubb.lab11.core.exceptions.ValidatorException;

import java.util.stream.Stream;

@Component
public class LuggageValidator implements Validator<Luggage>{
    @Override
    public void validate(Luggage luggage) throws ValidatorException {
        Stream.of(new Pair<>(luggage.getTicketId() <= 0, "TicketId should be positive."),
                        new Pair<>(luggage.getPrice() <= 0, "Price should be positive."),
                        new Pair<>(luggage.getType().isEmpty(), "Luggage first name must not be empty"))
                .filter(Pair::getLeft)
                .forEach((b) -> {
                    throw new ValidatorException(b.getRight());
                });
    }
}
