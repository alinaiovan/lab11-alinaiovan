package ro.ubb.lab11.core.model.validators;
import ro.ubb.lab11.core.exceptions.ValidatorException;


public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
