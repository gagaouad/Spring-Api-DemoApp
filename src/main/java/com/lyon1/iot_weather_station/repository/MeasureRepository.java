package com.lyon1.iot_weather_station.repository;

import com.lyon1.iot_weather_station.model.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasureRepository extends JpaRepository<Measure, Long> {
    List<Measure> findByDeviceIdAndDateOrderByTimeAsc(String deviceId, String date);

    List<Measure> findByDeviceIdAndDateAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByTimeAsc(
            String deviceId, String date, String start, String end);
}