package com.lyon1.iot_weather_station.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyon1.iot_weather_station.model.User;
import com.lyon1.iot_weather_station.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Find a User by id")
    @GetMapping("/{id}")
    ResponseEntity<User> findByIdUser(@PathVariable("id") Long id) {
        User user = userService.findByIdUser(id);
        return ResponseEntity.ok().body(user);
    }

    @ApiOperation(value = "Find a User by email and password")
    @PostMapping("/login")
    ResponseEntity<User> findByEmailAndPasswordUser(@RequestBody User user) {
        User userFound = userService.findByEmailAndPasswordUser(user.getEmail(), user.getPassword());
        return ResponseEntity.ok().body(userFound);
    }

    @ApiOperation(value = "Save a User")
    @PostMapping("")
    ResponseEntity<User> saveUser(@RequestBody User user) {
        User userSaved = userService.saveUser(user);
        return new ResponseEntity<>(userSaved,HttpStatus.CREATED);
    }

    @ApiOperation(value = "Returns all users in database")
    @GetMapping("/all")
    ResponseEntity<String> findAllUsers() throws JsonProcessingException {
        List<User> users = userService.findAll();
        ObjectMapper mapper = new ObjectMapper();
        return new ResponseEntity<>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users),HttpStatus.OK);
    }
}
