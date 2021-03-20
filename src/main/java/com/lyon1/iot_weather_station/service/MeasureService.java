package com.lyon1.iot_weather_station.service;

import com.lyon1.iot_weather_station.exception.CustomException;
import com.lyon1.iot_weather_station.model.Measure;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface MeasureService {

    Measure findByIdMeasure(Long id) throws EntityNotFoundException;

    List<Measure> findByDeviceIdAndDateMeasure(String deviceId, String date) throws EntityNotFoundException;

    List<Measure> findByDeviceIdAndDateAndTimeMeasure(String deviceId, String date, String start, String end)
            throws EntityNotFoundException;

    Measure saveMeasure(Measure measure) throws CustomException;

    List<Measure> findAll();
}