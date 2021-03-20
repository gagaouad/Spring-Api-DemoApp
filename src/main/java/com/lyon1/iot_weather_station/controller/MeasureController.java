package com.lyon1.iot_weather_station.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyon1.iot_weather_station.model.Measure;
import com.lyon1.iot_weather_station.service.MeasureService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measure")
@CrossOrigin
public class MeasureController {

    private final MeasureService measureService;

    @Autowired
    public MeasureController(MeasureService measureService) {
        this.measureService = measureService;
    }

    @ApiOperation(value = "Find a Measure by id")
    @GetMapping("/{id}")
    ResponseEntity<Measure> findByIdMeasure(@PathVariable("id") Long id) {
        Measure measure = measureService.findByIdMeasure(id);
        return new ResponseEntity<>(measure, HttpStatus.OK);
    }

    @ApiOperation(value = "Find Measure by deviceId and date")
    @GetMapping(params = {"deviceId", "date"})
    ResponseEntity<List<Measure>> findByDeviceIdAndDateMeasure(@RequestParam("deviceId") String deviceId, @RequestParam("date") String date) {
        List<Measure> measures = measureService.findByDeviceIdAndDateMeasure(deviceId, date);
        return new ResponseEntity<>(measures, HttpStatus.OK);
    }

    @ApiOperation(value = "Find Measure by deviceId, date and time")
    @GetMapping(params = {"deviceId", "date", "start", "end"})
    ResponseEntity<List<Measure>> findByDeviceIdAndDateAndTimeMeasure(@RequestParam("deviceId") String deviceId,
                                                                      @RequestParam("date") String date,
                                                                      @RequestParam("start") String start,
                                                                      @RequestParam("end") String end) {
        List<Measure> measures = measureService.findByDeviceIdAndDateAndTimeMeasure(deviceId, date, start, end);
        return new ResponseEntity<>(measures, HttpStatus.OK);
    }

    @ApiOperation(value = "Save a Measure")
    @PostMapping("")
    ResponseEntity<Measure> saveMeasure(@RequestBody Measure measure) {
        Measure measureSaved = measureService.saveMeasure(measure);
        return new ResponseEntity<>(measureSaved, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Return the last saved temperature")
    @GetMapping("/last")
    ResponseEntity<String> getLastTempreature() {
        List<Measure> measures = measureService.findAll();
        Measure lastMeasure = measures.get(measures.size() - 1);
        String temperature = lastMeasure.getTemperature().toString();
        return new ResponseEntity<>(temperature, HttpStatus.OK);
    }
}
