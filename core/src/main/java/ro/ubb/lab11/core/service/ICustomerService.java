package ro.ubb.lab11.core.service;

import ro.ubb.lab11.core.model.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomers();
    Customer addCustomer(String fullName, String phoneNumber, String email);
    void deleteCustomer(Long id);
    Customer updateCustomer(Long id, String fullName, String phoneNumber, String email);
    List<Customer> findCustomersByFullName(String fullName);

}
