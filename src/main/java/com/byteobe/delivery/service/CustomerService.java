package com.byteobe.delivery.service;

import com.byteobe.delivery.dto.CustomerRequest;
import com.byteobe.delivery.dto.CustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CustomerService {

    private final List<CustomerResponse> customers = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<CustomerResponse> getAll(){
        return customers;
    }

    public Optional<CustomerResponse> getById(Long id){
       return customers.stream().filter( (customerResponse -> customerResponse.id().equals(id)) ).findFirst();
    }


    public  CustomerResponse create(CustomerRequest data){
        CustomerResponse customer = new CustomerResponse(
                idCounter.getAndIncrement(),
                data.name(),
                data.email(),
                data.phone(),
                data.address()
        );
        customers.add(customer);
        return customer;
    }

    public  Optional<CustomerResponse> update(Long id, CustomerRequest  data){
        for ( int i = 0; i<customers.size(); i++){
            if(customers.get(i).id().equals(id)){
                CustomerResponse customerUpdate = new CustomerResponse(
                        id,
                        data.name(),
                        data.email(),
                        data.phone(),
                        data.address()
                );
                customers.set(i, customerUpdate);
                return Optional.of(customerUpdate);
            }
        }
        return Optional.empty();
    }

    public boolean delete(Long id){
            return customers.removeIf( (customerResponse -> customerResponse.id().equals(id)) );
    }
}
