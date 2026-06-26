package com.byteobe.delivery.controller;

import com.byteobe.delivery.dto.OrderRequest;
import com.byteobe.delivery.dto.OrderResponse;
import com.byteobe.delivery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    final OrderService orderService;


    @GetMapping
    public ResponseEntity<List<OrderResponse>> get(){
        return ResponseEntity.ok(this.orderService.get());
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id){
        return this.orderService.getById(id).map( ResponseEntity::ok ).orElse( ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @RequestBody OrderRequest  body
            ){
        return ResponseEntity.ok(this.orderService.create(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable Long id, @RequestBody OrderRequest body){
       return  this.orderService.update(id,body).map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
       if(this.orderService.delete(id)){
           return ResponseEntity.noContent().build();
       }
       return ResponseEntity.notFound().build();
    }

}
