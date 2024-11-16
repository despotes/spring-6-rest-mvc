package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CustomerControllerTest {

    @Autowired
    CustomerController controller;

    @Test
    void getCustomer() {
        List<Customer> customers = controller.listCustomer();
        Customer customer1 = customers.getFirst();
        controller.getCustomer(customer1.getId());
        System.out.println(customer1);
    }

    @Test
    void listCustomer() {
        List<Customer> customers = controller.listCustomer();
        System.out.println(customers.toString());
    }
}