package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    CustomerService customerService;

    @DeleteMapping("{customerId}")
    ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomerById(customerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{customerId}")
    ResponseEntity updateCustomerById(@PathVariable UUID customerId, @RequestBody Customer customer) {
        customerService.updateCustomerById(customerId, customer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping( method = RequestMethod.GET)
    List<Customer> listCustomer() {
        return customerService.listCustomers();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    Customer getCustomer(@PathVariable("customerId")UUID customerId) {
        return customerService.getCustomerById(customerId);
    }
}
