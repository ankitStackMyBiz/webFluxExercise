package com.example.webfluxdemo.basic.service;

import com.example.webfluxdemo.basic.dto.CustomerDto;
import com.example.webfluxdemo.basic.dao.CustomerDao;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

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
