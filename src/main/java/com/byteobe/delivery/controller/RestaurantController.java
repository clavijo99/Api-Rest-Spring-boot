package com.byteobe.delivery.controller;


import com.byteobe.delivery.dto.RestaurantRequest;
import com.byteobe.delivery.dto.RestaurantResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final ArrayList<RestaurantResponse> restaurants = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);



    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAll(@RequestParam(required = false) String  category){

        List<RestaurantResponse> result = restaurants;

        if(category != null && !category.isBlank()){
            result = restaurants.stream().filter( (r) -> r.category().equalsIgnoreCase(category) ).toList();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getByid(@PathVariable Long id){
        return restaurants.stream().filter( (r) -> r.id().equals(id) ).findFirst().map( ResponseEntity::ok ).orElse( ResponseEntity.notFound().build() );
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> create(@RequestBody RestaurantRequest body){
        RestaurantResponse restaurant = new RestaurantResponse(
                idCounter.getAndIncrement(),
                body.name(),
                body.address(),
                body.phone(),
                body.category(),
                true
        );

        restaurants.add(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> update(@PathVariable Long id, @RequestBody RestaurantRequest body){
        for(int i = 0 ; i<restaurants.size(); i++){
            if(restaurants.get(i).id().equals(id)){
                RestaurantResponse restaurant = new  RestaurantResponse(
                        id,
                        body.name(),
                        body.address(),
                        body.phone(),
                        body.category(),
                        true
                );

                restaurants.set(i, restaurant);
                return ResponseEntity.ok(restaurant);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(required = true) Long id){
        boolean removeRestaurant = restaurants.removeIf( (r) -> r.id().equals(id) );
        if (removeRestaurant)  {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
