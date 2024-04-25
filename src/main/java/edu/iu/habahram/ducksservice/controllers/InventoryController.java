package edu.iu.habahram.ducksservice.controllers;

import edu.iu.habahram.ducksservice.model.Builder;
import edu.iu.habahram.ducksservice.model.Guitar;
import edu.iu.habahram.ducksservice.model.Type;
import edu.iu.habahram.ducksservice.model.Wood;
import edu.iu.habahram.ducksservice.repository.InventoryFileRepository;
import edu.iu.habahram.ducksservice.repository.InventoryRepository;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class InventoryController {

    private InventoryRepository inventoryRepository;
    private InventoryFileRepository inventoryFileRepository;

    public InventoryController(InventoryRepository inventoryRepository, InventoryFileRepository inventoryFileRepository){
        this.inventoryRepository = inventoryRepository;
        this.inventoryFileRepository = inventoryFileRepository;
    }
    @GetMapping("/filesearch")
    public List<Guitar> searchFile(@RequestParam(required = false) String serialNumber,
                               @RequestParam(required = false) Double price,
                               @RequestParam(required = false) String builder,
                               @RequestParam(required = false) String model,
                               @RequestParam(required = false) String type,
                               @RequestParam(required = false) String backWood,
                               @RequestParam(required = false) String topWood){

        Builder builderEnum;
        boolean builderIsDefined = false;
        if(builder != null){
            builderEnum = Builder.valueOf(builder.toUpperCase());
            builderIsDefined = true;
        }else {
            builderEnum = null;
        }

        Type typeEnum;
        boolean typeIsDefined = false;
        if(type != null){
            typeEnum = Type.valueOf(type.toUpperCase());
            typeIsDefined = true;
        }else {
            typeEnum = null;
        }

        Wood backWoodEnum;
        boolean backwoodIsDefined = false;
        if(backWood != null){
            backWoodEnum = Wood.valueOf(backWood.toUpperCase());
            backwoodIsDefined = true;
        }else {
            backWoodEnum = null;

        }

        Wood topWoodEnum;
        boolean topwoodIsDefined = false;
        if(topWood != null){
            topWoodEnum = Wood.valueOf(topWood.toUpperCase());
            topwoodIsDefined = true;
        }else{
            topWoodEnum = null;
        }

        Guitar guitar = new Guitar(serialNumber, price == null ? 0 : price, builderIsDefined ? builderEnum : null, model, typeIsDefined ? typeEnum : null, backwoodIsDefined ? backWoodEnum : null, topwoodIsDefined ? topWoodEnum : null);
        System.out.println("ran here");
        return inventoryFileRepository.search(guitar);
    }

    private String getTheCurrentLoggedInCustomer() {
        Object principal = SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        String username = "";
        if(principal instanceof Jwt) {
            username = ((Jwt) principal).getSubject();
        }
        return username;
    }

    @PostMapping
    public ResponseEntity<List<Guitar>> search(@RequestBody Guitar order) {
        String username = getTheCurrentLoggedInCustomer();
        System.out.println(username);
        if(username.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Example<Guitar> example = Example.of(order);
        List<Guitar> orders = (List<Guitar>) inventoryRepository.findAll(example);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

@PostMapping("/search2")
public ResponseEntity<Boolean> search2(@RequestBody Guitar searchGuitar) {
    System.out.println("found");
    System.out.println(searchGuitar);
    List<Guitar> allGuitars = (List<Guitar>) inventoryRepository.findAll();

    for (Guitar guitar : allGuitars) {
        boolean isMatch = true;

        if (searchGuitar.getBuilder() != null && !searchGuitar.getBuilder().equals(guitar.getBuilder())) {
            isMatch = false;
        }

        if (searchGuitar.getType() != null && !searchGuitar.getType().equals(guitar.getType())) {
            isMatch = false;
        }

        if (searchGuitar.getBackWood() != null && !searchGuitar.getBackWood().equals(guitar.getBackWood())) {
            isMatch = false;
        }

        if (searchGuitar.getTopWood() != null && !searchGuitar.getTopWood().equals(guitar.getTopWood())) {
            isMatch = false;
        }

        if (isMatch) {
            return ResponseEntity.ok(true);
        }
    }

    return ResponseEntity.ok(false);
}


    @PostMapping("/add")
    public boolean add(@RequestBody Guitar data){
        System.out.println("add attempted");
        System.out.println(data.getBackWood());
        inventoryRepository.save(data);
        return true;
    }

    @GetMapping("/find/{serialNumber}")
    public ResponseEntity<Guitar> find(@PathVariable String serialNumber) {
        try {
            Optional<Guitar> guitar = inventoryRepository.findBySerialNumber(serialNumber);
            if(!guitar.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .body(guitar.get());
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/search/{model}")
    public ResponseEntity<Guitar> searchByModel(@PathVariable String model) {
        try {
            Optional<Guitar> guitar = inventoryRepository.findByModel(model);
            if(!guitar.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .body(guitar.get());
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
