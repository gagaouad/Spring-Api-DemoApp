package com.lyon1.iot_weather_station.service;

import com.lyon1.iot_weather_station.exception.CustomException;
import com.lyon1.iot_weather_station.model.Device;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

public interface DeviceService {

    Device findByIdDevice(String id) throws EntityNotFoundException;

    Set<Device> findByUserIdDevice(Long userId) throws EntityNotFoundException;

    boolean existsByIdDevice(String id);

    boolean isPairedByIdDevice(String id) throws EntityNotFoundException;

    Device saveDevice(Device device);

    Device updateTurnOffRelayByIdDevice(String id, boolean turnOffRelay) throws EntityNotFoundException;

    Device updateUserByIdDevice(String id, Long userId) throws EntityNotFoundException, CustomException;

    Device releaseByIdDevice(String id) throws EntityNotFoundException;

    Device updateByIdDevice(String id, Device deviceDetails) throws EntityNotFoundException;
}