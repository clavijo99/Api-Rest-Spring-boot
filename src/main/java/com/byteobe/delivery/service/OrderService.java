package com.byteobe.delivery.service;

import com.byteobe.delivery.dto.OrderRequest;
import com.byteobe.delivery.dto.OrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final ArrayList<OrderResponse> orders = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);


    public List<OrderResponse> get(){
        return orders;
    }


    public Optional<OrderResponse> getById(@PathVariable Long id){
        return orders.stream().filter( (o) ->  o.id().equals(id) ).findFirst();
    }


    public OrderResponse create(
            @RequestBody OrderRequest body
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
        return order;
    }


    public Optional<OrderResponse> update(@PathVariable Long id, @RequestBody OrderRequest body){
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
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }


    public boolean delete(@PathVariable Long id){
        return orders.removeIf( (orderResponse -> orderResponse.id().equals(id)) );
    }

}
