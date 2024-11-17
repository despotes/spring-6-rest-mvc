package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Optional<CustomerDTO> getCustomerById(UUID id);
    List<CustomerDTO> listCustomers();

    CustomerDTO saveCustomer(CustomerDTO customer);

    void updateCustomerById(UUID customerId, CustomerDTO customer);

    void deleteCustomerById(UUID customerId);

    void patchCustomer(UUID customerId, CustomerDTO customer);
}
