package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class CustomerControllerIT {
    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;


    @Test
    void testPatchByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomePatchrById(UUID.randomUUID(), any());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testPatchByIdFound(){
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO customerDTO = customerMapper.customertoCustomerDto(customer);
        final String patchedName = "PATCHED";
        customerDTO.setCustomerName(patchedName);
        ResponseEntity responseEntity = customerController.updateCustomePatchrById(customer.getId(), customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        Optional<Customer> patchedCustomer = customerRepository.findById(customer.getId());
        assertThat(patchedCustomer).isNotEmpty().map(Customer::getCustomerName).hasValue(patchedName);
    }



    @Test
    void testDeleteByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomerById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteByIdFound(){
        Customer customer = customerRepository.findAll().getFirst();
        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        Optional<Customer> updatedCustomer = customerRepository.findById(customer.getId());
        assertThat(updatedCustomer).isEmpty();
    }

    @Test
    void testUpdateByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateByIdFound(){
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO customerDTO = customerMapper.customertoCustomerDto(customer);
        final String updatedName = "UPDATED";
        customerDTO.setCustomerName(updatedName);
        ResponseEntity responseEntity = customerController.updateCustomerById(customer.getId(), customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        Optional<Customer> updatedCustomer = customerRepository.findById(customer.getId());
        assertThat(updatedCustomer).isNotEmpty().map(Customer::getCustomerName).hasValue(updatedName);
    }

    @Rollback
    @Transactional
    @Test
    void saveNewCustomer() {
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO customerDTO = customerMapper.customertoCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        final String customerName = "New Value";
        customerDTO.setCustomerName(customerName);

        ResponseEntity responseEntity = customerController.createCustomer(customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        assertThat(customerRepository.findById(savedUUID))
                .isNotEmpty().map(Customer::getCustomerName).hasValue(customerName);
    }

    @Test
    void testGetByIdNoFound() {
        assertThrows(NotFoundException.class, () -> {
            CustomerDTO dto = customerController.getCustomer(UUID.randomUUID());
        });
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO dto = customerController.getCustomer(customer.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void testListAll() {
        List<CustomerDTO> dtos = customerController.listCustomer();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testListAllEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> customerList = customerController.listCustomer();
        assertThat(customerList.size()).isEqualTo(0);
    }
}