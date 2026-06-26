package com.byteobe.delivery.controller;

import com.byteobe.delivery.dto.CustomerRequest;
import com.byteobe.delivery.dto.CustomerResponse;
import com.byteobe.delivery.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @GetMapping()
    public ResponseEntity<List<CustomerResponse>> getAll(){
        return ResponseEntity.ok(this.customerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(Long id){
        return this.customerService.getById(id).map( ResponseEntity::ok ).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody CustomerRequest  body){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.customerService.create(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @RequestBody  CustomerRequest body){
        return this.customerService.update(id, body).map( ResponseEntity::ok ).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(this.customerService.delete(id)){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();
    }

}
