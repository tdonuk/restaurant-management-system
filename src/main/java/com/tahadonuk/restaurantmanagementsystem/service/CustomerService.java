package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Customer;
import com.tahadonuk.restaurantmanagementsystem.data.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepo;

    public void saveCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    public boolean isExists(long id) {
        return customerRepo.existsById(id);
    }

    public Customer getById(long id) {
        if(isExists(id)) {
            return customerRepo.findById(id).get();
        }
        else return null;
    }

    public void deleteCustomer(long id) {
        customerRepo.deleteById(id);
    }

    public List<Customer> getAll() {
        return customerRepo.findAll();
    }
}
