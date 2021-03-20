package com.lyon1.iot_weather_station.repository;

import com.lyon1.iot_weather_station.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
}