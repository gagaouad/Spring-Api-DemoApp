package com.lyon1.iot_weather_station.controller;

import com.lyon1.iot_weather_station.model.Device;
import com.lyon1.iot_weather_station.service.DeviceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/device")
@CrossOrigin
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @ApiOperation(value = "Find a Device by id")
    @GetMapping("/{id}")
    ResponseEntity<Device> findByIdDevice(@PathVariable("id") String id) {
        Device device = deviceService.findByIdDevice(id);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @ApiOperation(value = "Find all Device by User")
    @GetMapping(params = {"userId"})
    ResponseEntity<Set<Device>> findByUserIdDevice(@RequestParam("userId") Long userId) {
        Set<Device> devices = deviceService.findByUserIdDevice(userId);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @ApiOperation(value = "Check if a Device exists by id")
    @GetMapping("/exists/{id}")
    ResponseEntity<Boolean> existsByIdDevice(@PathVariable("id") String id) {
        boolean existsByIdDevice = deviceService.existsByIdDevice(id);
        return new ResponseEntity<>(existsByIdDevice, HttpStatus.OK);
    }

    @ApiOperation(value = "Save a Device")
    @PostMapping("")
    ResponseEntity<Device> saveDevice(@RequestBody Device device) {
        Device deviceSaved = deviceService.saveDevice(device);
        return new ResponseEntity<>(deviceSaved, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update turnOffRelay property of a Device by id")
    @PutMapping("/update/turn_off_relay/{id}")
    ResponseEntity<Device> updateTurnOffRelayByIdDevice(@PathVariable("id") String id, @RequestBody boolean turnOffRelay) {
        Device deviceUpdated = deviceService.updateTurnOffRelayByIdDevice(id, turnOffRelay);
        return new ResponseEntity<>(deviceUpdated, HttpStatus.OK);
    }

    @ApiOperation(value = "Update user property of a Device by id")
    @PutMapping("/update/user/{id}")
    ResponseEntity<Device> updateUserByIdDevice(@PathVariable("id") String id, @RequestBody Long userId) {
        Device deviceUpdated = deviceService.updateUserByIdDevice(id, userId);
        return new ResponseEntity<>(deviceUpdated, HttpStatus.OK);
    }

    @ApiOperation(value = "Release a Device by id")
    @PutMapping("/release/{id}")
    ResponseEntity<Device> releaseUserByIdDevice(@PathVariable("id") String id) {
        Device deviceUpdated = deviceService.releaseByIdDevice(id);
        return new ResponseEntity<>(deviceUpdated, HttpStatus.OK);
    }

    @ApiOperation(value = "Update a Device by id")
    @PutMapping("/{id}")
    ResponseEntity<Device> updateByIdDevice(@PathVariable("id") String id, @RequestBody Device deviceDetails) {
        Device deviceUpdated = deviceService.updateByIdDevice(id, deviceDetails);
        return new ResponseEntity<>(deviceUpdated, HttpStatus.OK);
    }
}
