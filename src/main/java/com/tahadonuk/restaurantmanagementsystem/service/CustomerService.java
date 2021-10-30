package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Customer;
import com.tahadonuk.restaurantmanagementsystem.data.repository.CustomerRepository;
import com.tahadonuk.restaurantmanagementsystem.exception.NotFoundException;
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

    public Customer getById(long id) throws NotFoundException {
        if(isExists(id)) {
            return customerRepo.findById(id).get();
        }
        else throw new NotFoundException("No such order with given ID");
    }

    public void deleteCustomer(long id) throws NotFoundException {
        if(isExists(id)) {
            customerRepo.deleteById(id);
        } else throw new NotFoundException("No such order with given ID");
    }

    public List<Customer> getAll() {
        return customerRepo.findAll();
    }


    public boolean isExists(long id) {
        return customerRepo.existsById(id);
    }
}
