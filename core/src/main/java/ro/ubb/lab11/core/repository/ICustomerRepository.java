package ro.ubb.lab11.core.repository;
import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Customer;

import java.util.List;

public interface ICustomerRepository extends IRepository<Customer, Long>{
    List<Customer> findAllByFullName(String fullName);
}
