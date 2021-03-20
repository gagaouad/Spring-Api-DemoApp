package com.lyon1.iot_weather_station.controller;

import com.lyon1.iot_weather_station.service.UUIDService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/uuid")
@CrossOrigin
public class UUIDController {

    private final UUIDService uuidService;

    @Autowired
    public UUIDController(UUIDService uuidService) {
        this.uuidService = uuidService;
    }

    @ApiOperation(value = "Generate a version 1 UUID")
    @GetMapping("/generate_v1")
    ResponseEntity<UUID> generateVersion1UUID() {
        UUID uuidGenerated = uuidService.generateV1UUID();
        return new ResponseEntity<>(uuidGenerated, HttpStatus.OK);
    }
}