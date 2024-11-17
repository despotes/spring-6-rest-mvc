package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer getCustomerById(UUID id);
    List<Customer> listCustomers();

    Customer saveCustomer(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);
}
