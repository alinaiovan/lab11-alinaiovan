package ro.ubb.lab11.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.lab11.core.model.Customer;
import ro.ubb.lab11.core.service.ICustomerService;
import ro.ubb.lab11.web.converter.CustomerConverter;
import ro.ubb.lab11.web.dto.CustomerDTO;
import ro.ubb.lab11.web.dto.CustomersDTO;

import java.util.List;

@RestController
public class CustomerController {
    public static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private ICustomerService customerService;
    @Autowired
    CustomerConverter customerConverter;

    @RequestMapping(value = "/customers")
    CustomersDTO getCustomersFromRepository() {
        logger.trace("getAllCustomers - method entered");
        List<Customer> customers = customerService.getAllCustomers();
        CustomersDTO customersDTO = new CustomersDTO(customerConverter.convertModelsToDTOs(customers));
        logger.trace("getAllCustomers: " + customers);
        return customersDTO;
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) {
        logger.trace("addCustomer - method entered - customerDTO: " + customerDTO);
        var customer = customerConverter.convertDtoToModel(customerDTO);
        var result = customerService.addCustomer(customer.getFullName(), customer.getPhoneNumber(), customer.getEmail());
        var resultModel = customerConverter.convertModelToDto(result);
        logger.trace("addCustomer - customer added");
        return resultModel;
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        logger.trace("updateCustomer - method entered - customerDTO: " + customerDTO);
        var customer = customerConverter.convertDtoToModel(customerDTO);
        var result = customerService.updateCustomer(id, customer.getFullName(), customer.getPhoneNumber(), customer.getEmail());
        var resultModel = customerConverter.convertModelToDto(result);
        logger.trace("updateCustomer - customer updated");
        return resultModel;
    }

    @RequestMapping(value = "/customers/filter/{fullName}")
    CustomersDTO findCustomerByFullName(@PathVariable String fullName) {
        logger.trace("findCustomerByFullName - method entered - fullName = {}", fullName);
        List<Customer> customers = customerService.findCustomersByFullName(fullName);
        CustomersDTO customersDTO = new CustomersDTO(customerConverter.convertModelsToDTOs(customers));
        logger.trace("getAllCustomers: " + customers);
        return customersDTO;
    }
}
