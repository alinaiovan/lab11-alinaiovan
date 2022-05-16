package ro.ubb.lab11.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Customer;
import ro.ubb.lab11.web.dto.CustomerDTO;

@Component
public class CustomerConverter  extends BaseConverter<Long, Customer, CustomerDTO> {
    @Override
    public Customer convertDtoToModel(CustomerDTO dto) {
        var model = new Customer();
        model.setId(dto.getId());
        model.setFullName(dto.getFullName());
        model.setPhoneNumber(dto.getPhoneNumber());
        model.setEmail(dto.getEmail());
        return model;
    }

    @Override
    public CustomerDTO convertModelToDto(Customer customer) {
        var customerDTO = new CustomerDTO(customer.getFullName(), customer.getPhoneNumber(), customer.getEmail());
        customerDTO.setId(customer.getId());
        return customerDTO;
    }
}
