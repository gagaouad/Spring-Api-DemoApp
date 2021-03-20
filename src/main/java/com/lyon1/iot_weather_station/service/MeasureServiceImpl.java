package com.lyon1.iot_weather_station.service;

import com.lyon1.iot_weather_station.exception.CustomException;
import com.lyon1.iot_weather_station.repository.MeasureRepository;
import com.lyon1.iot_weather_station.model.Device;
import com.lyon1.iot_weather_station.model.Measure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeasureServiceImpl implements MeasureService {

    private final MeasureRepository measureRepository;
    private final DeviceService deviceService;

    @Autowired
    public MeasureServiceImpl(MeasureRepository measureRepository, DeviceService deviceService) {
        this.measureRepository = measureRepository;
        this.deviceService = deviceService;
    }

    @Override
    public Measure findByIdMeasure(Long id) throws EntityNotFoundException {
        return measureRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Measure with id " + id + " not found")
        );
    }

    @Override
    public List<Measure> findByDeviceIdAndDateMeasure(String deviceId, String date) throws EntityNotFoundException {
        List<Measure> measures = measureRepository.findByDeviceIdAndDateOrderByTimeAsc(deviceId, date);
        measures = getFilteredByMIntervalMeasure(deviceId, measures);

        return measures;
    }

    @Override
    public List<Measure> findByDeviceIdAndDateAndTimeMeasure(String deviceId, String date, String start, String end)
            throws EntityNotFoundException {
        List<Measure> measures = measureRepository
                .findByDeviceIdAndDateAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByTimeAsc(
                        deviceId, date, start, end);
        measures = getFilteredByMIntervalMeasure(deviceId, measures);

        return measures;
    }

    private List<Measure> getFilteredByMIntervalMeasure(String deviceId, List<Measure> measures)
            throws EntityNotFoundException {
        if (measures.isEmpty())
            return measures;

        Device device = deviceService.findByIdDevice(deviceId);
        Integer minuteInterval = device.getMinuteInterval();

        List<Measure> filteredMeasures = new ArrayList<>();
        filteredMeasures.add(measures.get(0));

        String[] separated = measures.get(0).getTime().split(":");
        int lastMinute = (separated.length > 1 && separated[1] != null) ? Integer.parseInt(separated[1]) : 0;
        int currentMinute;
        for (int i = 1; i < measures.size(); i++) {
            separated = measures.get(i).getTime().split(":");

            currentMinute = (separated.length > 1 && separated[1] != null) ? Integer.parseInt(separated[1]) : 0;
            if (currentMinute < lastMinute) currentMinute += 60;

            if (((currentMinute - lastMinute) % minuteInterval) == 0) {
                filteredMeasures.add(measures.get(i));
                lastMinute = currentMinute;
            }
        }

        return filteredMeasures;
    }

    @Override
    public Measure saveMeasure(Measure measure) throws CustomException {
        System.out.println(measure);
        System.out.println(measure.getTemperature());
        System.out.println(measure.getDevice());
        boolean noTimeSet = measure.getTime() == null, noDateSet = measure.getDate() == null;
        if (noTimeSet || noDateSet) {
            LocalDateTime now = LocalDateTime.now();
            if (noTimeSet) measure.setTime(now.format(DateTimeFormatter.ofPattern("HH:mm")));
            if (noDateSet) measure.setDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        Measure measureSaved;
        try {
            measureSaved = measureRepository.save(measure);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(HttpStatus.CONFLICT, "Measure with date " + measure.getDate()
                    + " and time " + measure.getTime() + " already exists", e);
        }

        return measureSaved;
    }

    @Override
    public List<Measure> findAll() {
        return measureRepository.findAll();
    }
}