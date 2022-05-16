package ro.ubb.lab11.core.model.validators;



import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Customer;
import ro.ubb.lab11.core.model.Pair;
import ro.ubb.lab11.core.exceptions.ValidatorException;

import java.util.stream.Stream;

@Component
public class CustomerValidator implements Validator<Customer>{
    @Override
    public void validate(Customer customer) throws ValidatorException {
        Stream.of(new Pair<>(customer.getFullName().isEmpty(), "Customer name must not be empty"),
                        new Pair<>(customer.getPhoneNumber().isEmpty(), "Phone number must not pe empty"),
                        new Pair<>(customer.getEmail().isEmpty(), "Email must not pe empty"))
                .filter(Pair::getLeft)
                .forEach((b) -> {
                    throw new ValidatorException(b.getRight());
                });
    }
}
