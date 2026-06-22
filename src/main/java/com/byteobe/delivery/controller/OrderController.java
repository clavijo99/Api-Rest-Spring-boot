package com.byteobe.delivery.controller;

import com.byteobe.delivery.dto.OrderRequest;
import com.byteobe.delivery.dto.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final ArrayList<OrderResponse> orders = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @GetMapping
    public ResponseEntity<List<OrderResponse>> get(){
        return ResponseEntity.ok(orders);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id){
        return orders.stream().filter( (o) ->  o.id().equals(id) ).findFirst().map( ResponseEntity::ok ).orElse( ResponseEntity.notFound().build() );
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @RequestBody OrderRequest  body
            ){
        final OrderResponse order = new OrderResponse(
                idCounter.getAndIncrement(),
                body.restaurantId(),
                body.items(),
                body.total(),
                "PENDING",
                LocalDateTime.now()
        );
        orders.add(order);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable Long id, @RequestBody OrderRequest body){
        for( int i = 0; i<orders.size(); i++){
            if(orders.get(i).id().equals(id)) {
                OrderResponse order = new OrderResponse(
                        id,
                        body.restaurantId(),
                        body.items(),
                        body.total(),
                        body.status(),
                        orders.get(i).createAt()
                );
                orders.set(i, order);
                return ResponseEntity.ok(order);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean remove = orders.removeIf( (orderResponse -> orderResponse.id().equals(id)) );
        if(remove){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
