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
public class
CustomerController {
    public static final String  CUSTOMER_PATH = "/api/v1/customer";
    public static final String  CUSTOMER_PATH_PATH_ID = CUSTOMER_PATH +  "/{customerId}";
    CustomerService customerService;

    @PatchMapping(CUSTOMER_PATH_PATH_ID)
    ResponseEntity updateCustomePatchrById(@PathVariable UUID customerId, @RequestBody Customer customer) {
        customerService.patchCustomer(customerId, customer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_PATH_ID)
    ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomerById(customerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CUSTOMER_PATH_PATH_ID)
    ResponseEntity updateCustomerById(@PathVariable UUID customerId, @RequestBody Customer customer) {
        customerService.updateCustomerById(customerId, customer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(CUSTOMER_PATH)
    ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(CUSTOMER_PATH)
    List<Customer> listCustomer() {
        return customerService.listCustomers();
    }

    @GetMapping(CUSTOMER_PATH_PATH_ID)
    Customer getCustomer(@PathVariable("customerId")UUID customerId) {
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }
}
