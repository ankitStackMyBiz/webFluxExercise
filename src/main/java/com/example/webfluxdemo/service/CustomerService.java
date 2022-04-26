package com.example.webfluxdemo.service;

import com.example.webfluxdemo.dao.CustomerDao;
import com.example.webfluxdemo.dto.CustomerDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class CustomerService {

    private CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<CustomerDto> getAllCustomers(){
        return customerDao.getCustomerList();
    }

    public Flux<CustomerDto> getAllCustomersStream(){
        return customerDao.getCustomerListStream();
    }
}
