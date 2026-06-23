package com.byteobe.delivery.service;

import com.byteobe.delivery.dto.RestaurantRequest;
import com.byteobe.delivery.dto.RestaurantResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RestaurantService {

    private  final List<RestaurantResponse> restaurants = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);


    public List<RestaurantResponse> findAll(String category){
        if(category!= null && !category.isBlank()){
            return restaurants.stream().filter( (restaurantResponse ->  restaurantResponse.category().equalsIgnoreCase(category) ) ).toList();
        }
        return List.copyOf(restaurants);
    }

    public Optional<RestaurantResponse> getById(Long id){
        return restaurants.stream().filter( (restaurantResponse -> restaurantResponse.id().equals(id)) ).findFirst();
    }

    public  RestaurantResponse  create(RestaurantRequest data){
        RestaurantResponse restaurantResponse = new RestaurantResponse(
                idCounter.getAndIncrement(),
                data.name(), data.address(), data.phone(), data.category(), true
        );
        restaurants.add(restaurantResponse);
        return restaurantResponse;
    }

    public Optional<RestaurantResponse> update( Long id,  RestaurantRequest data){
        for(int i = 0; i<restaurants.size(); i++ ){
            if(restaurants.get(i).id().equals(id)){
                RestaurantResponse restaurantResponse = new RestaurantResponse(
                        id,
                        data.name(),
                        data.address(),
                        data.phone(),
                        data.category(),
                        true
                );
                restaurants.set(i, restaurantResponse);
                return  Optional.of(restaurantResponse);
            }
        }
        return Optional.empty();
    }

    public boolean delete(Long id){
        return restaurants.removeIf( (restaurantResponse -> restaurantResponse.id().equals(id)) );
    }

}
