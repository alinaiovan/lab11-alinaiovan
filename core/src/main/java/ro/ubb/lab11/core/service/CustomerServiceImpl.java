package ro.ubb.lab11.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.lab11.core.exceptions.AirlineException;
import ro.ubb.lab11.core.model.validators.CustomerValidator;
import ro.ubb.lab11.core.repository.ICustomerRepository;
import ro.ubb.lab11.core.repository.ITicketRepository;
import ro.ubb.lab11.core.model.Customer;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService{
    public static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerValidator validator;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private ITicketRepository ticketRepository;

    @Override
    public List<Customer> getAllCustomers() {
        logger.trace("getAllCustomers - method entered");
        List<Customer> customers = customerRepository.findAll();
        logger.trace("getAllCustomers: " + customers);
        return customers;
    }

    @Override
    public Customer addCustomer(String fullName, String phoneNumber, String email) {
        logger.trace("addCustomer - method entered - fullName: " + fullName + " - phoneNumber: " + phoneNumber + " - email: " + email);
        Customer newC = new Customer(fullName, phoneNumber, email);
        validator.validate(newC);
        var customer = customerRepository.save(newC);
        logger.trace("AddCustomer - method finished");
        return customer;
    }

    @Override
    public void deleteCustomer(Long id) {
        logger.trace("deleteCustomer - method entered - id: " + id);
        ticketRepository.findAll().stream()
                .filter(ticket -> ticket.getCustomerId().equals(id))
                .findAny()
                .ifPresent(ticket -> {
                    throw new AirlineException("The costumer has a ticket!");
                });

        customerRepository.findById(id).ifPresentOrElse((customer) -> customerRepository.deleteById(id), () ->{
            throw new AirlineException("Customer does not exist!");
        });
        logger.trace("deleteCustomer - method finished");
    }

    @Override
    @Transactional
    public Customer updateCustomer(Long id, String fullName, String phoneNumber, String email) {
        logger.trace("updateCustomer - method entered - id: " + id + " - fullName: " + fullName + " - phoneNumber: " + phoneNumber + " - email: " + email);
        Customer customer = new Customer(fullName, phoneNumber, email);
        customer.setId(id);
        validator.validate(customer);
        customerRepository.findById(id).ifPresentOrElse((customer1) -> {
            customer1.setFullName(fullName);
            customer1.setPhoneNumber(phoneNumber);
            customer1.setEmail(email);
        }, () -> {
            throw new AirlineException("Customer does not exist!");
        });
        logger.trace("updateCustomer - method finished");
        return customer;
    }

    @Override
    public List<Customer> findCustomersByFullName(String fullName) {
        logger.trace("findCustomersByFullName - method entered - fullName = {}", fullName);
        var result = customerRepository.findAllByFullName(fullName);
        logger.trace("findCustomersByFullName - method finished - result = {}", result);
        return result;
    }
}
