package com.byteobe.delivery.controller;
import com.byteobe.delivery.dto.RestaurantRequest;
import com.byteobe.delivery.dto.RestaurantResponse;
import com.byteobe.delivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {


    private final RestaurantService restaurantService;
    


    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAll(@RequestParam(required = false) String  category){
        return ResponseEntity.ok(restaurantService.findAll(category));
    }


    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getByid(@PathVariable Long id){
        return restaurantService.getById(id).map( ResponseEntity::ok ).orElse( ResponseEntity.notFound().build() );
    }


    @PostMapping
    public ResponseEntity<RestaurantResponse> create(@RequestBody RestaurantRequest body){
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.create(body));
    }


    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> update(@PathVariable Long id, @RequestBody RestaurantRequest body){
        return restaurantService.update(
                id,body
        ).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(required = true) Long id){
       if(restaurantService.delete(id)){
           return ResponseEntity.noContent().build();
       }
       return ResponseEntity.notFound().build();
    }
}
